import aplicacao.*;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.janelas.JanelaGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.Rede;
import dao.DaoMySQL;
import dao.DaoSQLServer;
import oshi.SystemInfo;
import slack.BotSlack;
import usuario.Funcionario;
import usuario.Representante;
import metodo.Log;
import java.io.IOException;
import java.util.*;;

//import teste.bot.BotSlack;

public class App {
    static Log logs = new Log();
    static Hardware hardwareDisco = new Hardware();

    public static void main(String[] args) {
        // ENTRADA DE DADOS
        Scanner in = new Scanner(System.in);
        Scanner inText = new Scanner(System.in);

        // LOOCA
        Rede rede = new Rede(new SystemInfo());
        Processador processador = new Processador();
        Memoria memoria = new Memoria();
        DiscoGrupo grupoDeDiscos = new DiscoGrupo();
        List<Volume> volumes = grupoDeDiscos.getVolumes();
        JanelaGrupo grupoDeJanelas = new JanelaGrupo(new SystemInfo());

        // CONEXÕES PARA OS BANCOS MYSQL E SQLSERVER
        DaoMySQL daoMySQL = new DaoMySQL();
        DaoSQLServer daoSQLServer = new DaoSQLServer();                      // LEMBRAR DISSO AQUI

        Integer opcaoEscolhida = -1;
        Integer opcaoEscolhida2 = -2;

        System.out.println("\u001b[32mMONITORAMENTO NOCT.U --MATHEUS\u001b[0m");


        do {
            System.out.print("\u001b[33mINSIRA SEU E-MAIL: \u001b[0m");
            String email = inText.nextLine();
            System.out.print("\u001b[33mINSIRA SUA SENHA: \u001b[0m");
            String senha = inText.nextLine();

            // VALIDANDO SE POSSUI CADASTRO NO BANCO
            Funcionario func = daoSQLServer.exibirUsuario(email).get(0);
            if (func.getEmail().equals(email) && func.getSenha().equals(senha)) {
                System.out.println("\n----- Bem vindo - %s -----".formatted(func.getNome()));

                // ADICIONANDO COMPUTADOR
                Computador computador = new Computador(rede.getParametros().getHostName(), func.getFkEmpresa(), 1, null, 1);
                if (daoSQLServer.exibirComputadorCadastrado(computador.getNome()).size() > 0) {
                    System.out.println("\u001b[32mCOMPUTADOR PRONTO\u001b[0m");

                } else {
                    System.out.println("""
                            \nCriando computador...
                            Associe esse computador a uma das empresas abaixo
                            ID | EMPRESA | MATRIZ""");
                    List<EmpresaLocataria> empresas = daoSQLServer.exibirEmpresasLocatarias(func.getFkEmpresa());
                    for (int i = 0; i < empresas.size(); i++) {
                        EmpresaLocataria empresaDaVez = empresas.get(i);
                        String empresaMatriz;
                        Integer contador = i + 1;
                        if (empresaDaVez.getFkMatriz() != null) {
                            empresaMatriz = daoSQLServer.exibirEmpresasLocatariasMatriz(contador).getNome();
                        } else {
                            empresaMatriz = "-";
                        }
                        System.out.println("""
                                %d) %s %s""".formatted(contador, empresaDaVez.getNome(), empresaMatriz));
                    }
                    System.out.print("ID: ");
                    Integer alocarComputador = in.nextInt();
                    computador.setFkEmpresaLocataria(alocarComputador);
                    daoSQLServer.adicionarComputador(computador);
                }

                // ADICIONANDO CPU, MEMORIA, DISCO, E JANELA
                Hardware hardwareCPU = new Hardware(processador.getNome(), 100.0, 1);
                Hardware hardwareMemoria = new Hardware("RAM", memoria.getTotal().doubleValue(), 2);

                Hardware hardwareJanelas = new Hardware("Janelas", 50.0, 4);
                if (daoSQLServer.exibirHardwareCadastrado(hardwareCPU).size() < 1) {
                    System.out.println("Cadastrando CPU...");
                    daoSQLServer.adicionarHardwareSemEspecificidade(hardwareCPU);
                } else {
                    System.out.println("VAMOS INICIAR");
                }

                if (daoSQLServer.exibirHardwareCadastrado(hardwareMemoria).size() < 1) {
                    System.out.println("Cadastrando Memoria...");
                    daoSQLServer.adicionarHardwareSemEspecificidade(hardwareMemoria);
                }

                for (Volume v : volumes) {
                    hardwareDisco = new Hardware(v.getNome(), v.getTotal().doubleValue(), 3);
                    if (daoSQLServer.exibirHardwareCadastrado(hardwareDisco).size() < 1) {
                        System.out.println("Cadastrando Disco...");
                        daoSQLServer.adicionarHardwareSemEspecificidade(hardwareDisco);
                    }
                }

                if (daoSQLServer.exibirHardwareCadastrado(hardwareJanelas).size() < 1) {
                    System.out.println("Cadastrando Janelas...");
                    daoSQLServer.adicionarHardwareSemEspecificidade(hardwareJanelas);
                }

                Integer idComputador = daoSQLServer.exibirIdComputadorPeloNomeComputador(computador.getNome()).get(0).getIdComputador();
                if (daoSQLServer.exibirComponentesCadastradosPorComputador(idComputador).size() < 4) {
                    Integer idHardwareCPU = daoSQLServer.exibirIdHardwarePeloIdComputador(1).get(0).getIdHardware();
                    Integer idHardwareMemoria = daoSQLServer.exibirIdHardwarePeloIdComputador(2).get(0).getIdHardware();
                    Integer idHardwareDisco = daoSQLServer.exibirIdHardwarePeloIdComputador(3).get(0).getIdHardware();
                    Integer idHardwareJanela = daoSQLServer.exibirIdHardwarePeloIdComputador(4).get(0).getIdHardware();

                    System.out.println("Montando setup com CPU...");
                    Componente componenteCPU = new Componente(idComputador, idHardwareCPU);
                    daoSQLServer.adicionarComponente(componenteCPU);

                    System.out.println("Montando setup com RAM...");
                    Componente componenteRAM = new Componente(idComputador, idHardwareMemoria);
                    daoSQLServer.adicionarComponente(componenteRAM);

                    System.out.println("Montando setup com Disco...");
                    Componente componenteDisco = new Componente(idComputador, idHardwareDisco);
                    daoSQLServer.adicionarComponente(componenteDisco);

                    System.out.println("Montando setup com Janela...");
                    Componente componenteJanela = new Componente(idComputador, idHardwareJanela);
                    daoSQLServer.adicionarComponente(componenteJanela);
                }

                if (daoSQLServer.exibirComputadorAtual(computador.getNome()).getFkStatus().equals(1)) {
                    System.out.println("\u001b[34mINICIANDO\u001b[0m");


                    List<Parametro> parametros = (daoSQLServer.exibirParametrosDoModeloComputador(computador.getFkModeloComputador()));
                    Double valorInicial;
                    Double valorFinal;
                    Double Range;

                    // CRIA UM TEMPORIZADOR COM INTERVALO DE X SEGUNDOS.
                    Timer timer = new Timer();

                    // CRIA UMA TAREFA PARA SER EXECUTADA REPETIDAMENTE.
                    TimerTask tarefa = new TimerTask() {
                        @Override
                        public void run() {
                            Double valorInicial;
                            Double valorFinal;
                            Double range;
                            Double alertaVermelhoAbaixo;
                            Double alertaAmareloAbaixo;
                            Double alertaAmareloAcima;
                            Double alertaVermelhoAcima;
                            Double valorAtual;


                            List<Hardware> hardwaresDoComputador = daoSQLServer.exibirHardwaresPeloIDComputador(idComputador);
                            Double proporcaoMemoria = hardwaresDoComputador.get(1).getCapacidade() / 100;
                            Double proporcaoDisco = hardwaresDoComputador.get(2).getCapacidade() / 100;
                            Double valorEmPorcentagem;

                            Parametro parametroAtual = parametros.get(1);
                            valorInicial = parametroAtual.getMin();
                            valorFinal = parametroAtual.getMax();
                            range = valorFinal - valorInicial;
                            alertaVermelhoAbaixo = valorInicial + (range * 0.125);
                            alertaAmareloAbaixo = valorInicial + (range * 0.25);
                            alertaAmareloAcima = valorInicial + (range * 0.75);
                            alertaVermelhoAcima = valorInicial + (range * 0.875);

                            Integer idComputador = daoSQLServer.exibirIdComputadorPeloNomeComputador(computador.getNome()).get(0).getIdComputador();
                            Integer idHardwareCPU = daoSQLServer.descobrirIdHardware(hardwareCPU.getNome(), hardwareCPU.getCapacidade()).get(0).getIdHardware();
                            Integer idComponenteCPU = daoSQLServer.exibirIdComponentePeloIdComputadorEIdHardware(idComputador, idHardwareCPU).get(0).getIdComponente();

                            Long valorProcessador = processador.getUso().longValue();
                            Captura cap01 = new Captura(valorProcessador.doubleValue(), idComputador, idHardwareCPU, idComponenteCPU);
                            valorAtual = cap01.getValor();
                            daoMySQL.adicionarCaptura(cap01);
                            daoSQLServer.adicionarCaptura(cap01);
                            if (valorAtual <= alertaVermelhoAbaixo) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("CPU - ABAIXO DO LIMITE", idCaptura, 1);
                                daoSQLServer.adicionarAlerta(alerta);
                            } else if (valorAtual <= alertaAmareloAbaixo) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("CPU - PERTO DO LIMITE BAIXO", idCaptura, 2);
                                daoSQLServer.adicionarAlerta(alerta);
                            } else if (valorAtual >= alertaAmareloAcima && valorAtual < alertaVermelhoAcima) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("CPU - PERTO DO LIMITE ACIMA", idCaptura, 2);
                                daoSQLServer.adicionarAlerta(alerta);
                            } else if (valorAtual >= alertaVermelhoAcima) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("CPU - ACIMA DO LIMITE ", idCaptura, 1);
                                daoSQLServer.adicionarAlerta(alerta);
                            }
                            Integer idHardwareMemoria = daoSQLServer.descobrirIdHardware(hardwareMemoria.getNome(), hardwareMemoria.getCapacidade()).get(0).getIdHardware();
                            Integer idComponenteMemoria = daoSQLServer.exibirIdComponentePeloIdComputadorEIdHardware(idComputador, idHardwareMemoria).get(0).getIdComponente();
                            Long valorMemoria = memoria.getEmUso();
                            Captura cap02 = new Captura(valorMemoria.doubleValue(), idComputador, idHardwareMemoria, idComponenteMemoria);
                            daoMySQL.adicionarCaptura(cap02);
                            daoSQLServer.adicionarCaptura(cap02);
                            valorAtual = cap02.getValor();

                            valorEmPorcentagem = valorAtual / proporcaoMemoria;
                            if (valorEmPorcentagem <= alertaVermelhoAbaixo) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("RAM - ABAIXO DO LIMITE", idCaptura, 1);
                                daoSQLServer.adicionarAlerta(alerta);
                            } else if (valorEmPorcentagem <= alertaAmareloAbaixo) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("RAM - PERTO DO LIMITE BAIXO", idCaptura, 2);
                                daoSQLServer.adicionarAlerta(alerta);
                            } else if (valorEmPorcentagem >= alertaAmareloAcima && valorEmPorcentagem < alertaVermelhoAcima) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("RAM - PERTO DO LIMITE ACIMA", idCaptura, 2);
                                daoSQLServer.adicionarAlerta(alerta);
                            } else if (valorEmPorcentagem >= alertaVermelhoAcima) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("RAM - ACIMA DO LIMITE ", idCaptura, 1);
                                daoSQLServer.adicionarAlerta(alerta);
                            }

                            Captura cap03 = null;
                            Integer idHardwareDisco = daoSQLServer.descobrirIdHardware(hardwareDisco.getNome(), hardwareDisco.getCapacidade()).get(0).getIdHardware();
                            Integer idComponenteDisco = daoSQLServer.exibirIdComponentePeloIdComputadorEIdHardware(idComputador, idHardwareDisco).get(0).getIdComponente();
                            for (Volume v : volumes) {
                                Long valorDisco = v.getTotal() - v.getDisponivel();
                                cap03 = new Captura(valorDisco.doubleValue(), idComputador, idHardwareDisco, idComponenteDisco);
                                daoMySQL.adicionarCaptura(cap03);
                                daoSQLServer.adicionarCaptura(cap03);
                                valorAtual = cap03.getValor();
                                valorEmPorcentagem = valorAtual / proporcaoDisco;
                                if (valorEmPorcentagem <= alertaVermelhoAbaixo) {
                                    Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                    Alerta alerta = new Alerta("DISCO - ABAIXO DO LIMITE", idCaptura, 1);
                                    daoSQLServer.adicionarAlerta(alerta);
                                } else if (valorEmPorcentagem <= alertaAmareloAbaixo) {
                                    Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                    Alerta alerta = new Alerta("DISCO - PERTO DO LIMITE BAIXO", idCaptura, 2);
                                    daoSQLServer.adicionarAlerta(alerta);
                                } else if (valorEmPorcentagem >= alertaAmareloAcima && valorEmPorcentagem < alertaVermelhoAcima) {
                                    Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                    Alerta alerta = new Alerta("DISCO - PERTO DO LIMITE ACIMA", idCaptura, 2);
                                    daoSQLServer.adicionarAlerta(alerta);
                                } else if (valorEmPorcentagem >= alertaVermelhoAcima) {
                                    Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                    Alerta alerta = new Alerta("DISCO - ACIMA DO LIMITE ", idCaptura, 1);
                                    daoSQLServer.adicionarAlerta(alerta);
                                }
                            }

                            Integer idHardwareJanela = daoSQLServer.descobrirIdHardware(hardwareJanelas.getNome(), hardwareJanelas.getCapacidade()).get(0).getIdHardware();
                            Integer idComponenteJanela = daoSQLServer.exibirIdComponentePeloIdComputadorEIdHardware(idComputador, idHardwareJanela).get(0).getIdComponente();
                            Long valorJanela = grupoDeJanelas.getTotalJanelas().longValue();
                            Captura cap04 = new Captura(valorJanela.doubleValue(), idComputador, idHardwareJanela, idComponenteJanela);
                            daoMySQL.adicionarCaptura(cap04);
                            daoSQLServer.adicionarCaptura(cap04);
                            valorAtual = cap04.getValor();
                            if (valorAtual <= alertaVermelhoAbaixo) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("JANELA - ABAIXO DO LIMITE", idCaptura, 1);
                                daoSQLServer.adicionarAlerta(alerta);
                            } else if (valorAtual <= alertaAmareloAbaixo) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("JANELA - PERTO DO LIMITE BAIXO", idCaptura, 2);
                                daoSQLServer.adicionarAlerta(alerta);
                            } else if (valorAtual >= alertaAmareloAcima && valorAtual < alertaVermelhoAcima) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("JANELA - PERTO DO LIMITE ACIMA", idCaptura, 2);
                                daoSQLServer.adicionarAlerta(alerta);
                            } else if (valorAtual >= alertaVermelhoAcima) {
                                Integer idCaptura = daoSQLServer.exibirIdCaptura().get(0).getIdCaptura();
                                Alerta alerta = new Alerta("JANELA - ACIMA DO LIMITE ", idCaptura, 1);
                                daoSQLServer.adicionarAlerta(alerta);
                            }

                            try {
                                logs.gerarLog(cap01.getValor(), cap02.getValor(), cap03.getValor(), computador.getNome());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                logs.adicionarMotivo(hardwareCPU.getEspecificidade());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    };
                    // TEMPORIZADOR PARA A TAREFA.
                    timer.scheduleAtFixedRate(tarefa, 5, 5000);

                    Componente componenteCPU = new Componente();
                    Componente componenteRAM = new Componente();
                    Componente componenteDisco = new Componente();
                    Componente componenteJanela = new Componente();
                    // Notificações para slack
                    verificarLimiteEEnviarNotificacao("CPU", componenteCPU.getFkHardware());
                    verificarLimiteEEnviarNotificacao("RAM", componenteRAM.getFkHardware());
                    verificarLimiteEEnviarNotificacao("Disco", componenteDisco.getFkHardware());
                    verificarLimiteEEnviarNotificacao("Quantidade janelas", componenteJanela.getFkHardware());


                } else {
                    System.out.println("\nA captura desse computador esta desativada!!");
                }


                Integer opcaoUsuario = -1;
                do {
                    if (func.getFkTipoUsuario().equals(1)) {
                        // DIRECIONANDO PARA ÁREA DE REPRESENTANTE
                        Representante rep = new Representante(func.getNome(), func.getEmail(), func.getSenha(), func.getFkTipoUsuario(), func.getFkEmpresa(), func.getFkEmpresa(), func.getFkStatus());

                        System.out.println("""
                                \nVocê está como representante
                                1) Visualizar RAM
                                  0) Sair""");
                        opcaoEscolhida2 = in.nextInt();

                        switch (opcaoEscolhida2) {
                            case 1:
                                System.out.println("Visualizando dados de CPU...");
                                rep.visualizarRAM(idComputador);
                                break;

                            case 0:
                                System.out.println("Saindo...");
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Escolha uma opção válida!");
                                break;
                        }
                    } else {
                        // DIRECIONANDO PARA ÁREA DE FUNCIONÁRIO



                        System.out.println("""
                                1) Visualizar RAM
                                0) Sair""");

                        opcaoEscolhida2 = in.nextInt();
                        switch (opcaoEscolhida2) {
                            case 1:
                                System.out.println("Visualizando dados de RAM...");
                                func.visualizarRAM(idComputador);
                                break;
                            case 0:
                                System.out.println("Saindo...");
                                System.exit(0);
                                break;
                        }
                    }
                } while (opcaoUsuario != 0);
            } else {
                System.out.println("Email e/ou senha incorretos!");
            }
        } while (!opcaoEscolhida.equals(1));
    }

    private static void verificarLimiteEEnviarNotificacao(String componente, Integer fkTipoHardware) {
        if (componente.equals("CPU") || componente.equals("Janelas Abertas")) {
            // Notificar o usuário no Java
//            System.out.println("Alerta de limite no Jar");
        }
        // Enviar notificação por Slack
        try {
            BotSlack botSlack = new BotSlack();
            botSlack.mensagemHardware(componente);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao enviar notificação no Slack", e);
        }
    }
}


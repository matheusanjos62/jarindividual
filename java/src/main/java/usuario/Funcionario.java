package usuario;

import aplicacao.Captura;
import com.github.britooo.looca.api.core.Looca;
import dao.DaoMySQL;
import dao.DaoSQLServer;

import java.util.ArrayList;
import java.util.List;


public class Funcionario {
    Looca looca = new Looca();
    protected String nome;
    protected String email;
    protected String senha;
    protected Integer fkTipoUsuario;
    protected Integer fkEmpresaLocadora;
    protected Integer fkEmpresa;
    protected Integer fkStatus;
    protected List<Captura> capturas;


    public Funcionario() {
    }

    public Funcionario(String nome, String email, String senha, Integer fkTipoUsuario, Integer fkEmpresaLocadora, Integer fkEmpresa, Integer fkStatus) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.fkTipoUsuario = fkTipoUsuario;
        this.fkEmpresaLocadora = fkEmpresaLocadora;
        this.fkEmpresa = fkEmpresa;
        this.fkStatus = fkStatus;
        this.capturas = new ArrayList<>();
    }

    public void visualizarCPU(Integer idComputador) {
        DaoSQLServer dao = new DaoSQLServer();
        capturas = dao.exibirCapturasDeUmTipo(1, idComputador);

        System.out.println("TIPO | Valor | Data da captura");
        for (Captura c : capturas) {
            System.out.println("CPU    %.2f    %s".formatted(c.getValor(), c.getDtCaptura()));
        }
    }

    public void visualizarRAM(Integer idComputador) {
        DaoSQLServer dao = new DaoSQLServer();
        capturas = dao.exibirCapturasDeUmTipo(2, idComputador);

        System.out.println("TIPO | Valor | Data da captura");
        for (Captura c : capturas) {
            Double conversaoGB = c.getValor() / 1e9;
            Double valorTotalRAM = looca.getMemoria().getTotal() / 1e9;

            Double testePorcentagem = (conversaoGB / valorTotalRAM) * 100;


            System.out.println("RAM    %.0f%%    %s".formatted( testePorcentagem, c.getDtCaptura()));
        }
    }

    public void visualizarDisco(Integer idComputador) {
        DaoSQLServer dao = new DaoSQLServer();
        capturas = dao.exibirCapturasDeUmTipo(3, idComputador);

        System.out.println("TIPO | Valor | Data da captura");
        for (Captura c : capturas) {
            Double conversaoGB = c.getValor() / 1024 / 1024 / 1024;
            System.out.println("DIS    %.2f    %s".formatted(conversaoGB, c.getDtCaptura()));
        }
    }

    public void visualizarJanelas(Integer idComputador) {
        DaoSQLServer dao = new DaoSQLServer();
        capturas = dao.exibirCapturasDeUmTipo(4, idComputador);
        System.out.println("TIPO | Valor | Data da captura");
        for (Captura c : capturas) {
            System.out.println("JAN    %.2f    %s".formatted(c.getValor(), c.getDtCaptura()));
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getFkTipoUsuario() {
        return fkTipoUsuario;
    }

    public void setFkTipoUsuario(Integer fkTipoUsuario) {
        this.fkTipoUsuario = fkTipoUsuario;
    }

    public Integer getFkEmpresaLocadora() {
        return fkEmpresaLocadora;
    }

    public void setFkEmpresaLocadora(Integer fkEmpresaLocadora) {
        this.fkEmpresaLocadora = fkEmpresaLocadora;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public Integer getFkStatus() {
        return fkStatus;
    }

    public void setFkStatus(Integer fkStatus) {
        this.fkStatus = fkStatus;
    }

    @Override
    public String toString() {
        return """
                \nnome: %s
                email: %s
                senha: %s
                fkTipoUsuario: %d
                fkEmpresaLocadora: %d
                fkEmpresa: %d
                fkStatus: %d""".formatted(nome, email, senha, fkTipoUsuario, fkEmpresaLocadora, fkEmpresa, fkStatus);
    }
}

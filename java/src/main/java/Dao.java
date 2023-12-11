//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import usuario.Funcionario;
//import usuario.Representante;
//
//import java.util.List;
//
//public class Dao {
//    Conexao conexao = new Conexao();
//    JdbcTemplate con = conexao.getConexaoDoBanco();
//
//    public Dao() {
//        this.conexao = conexao;
//        this.con = con;
//    }
//
//    // INSERT NO BANCO PARA CAPTURAR E EXIBIR DADOS EM TEMPO REAL
//    public Funcionario exibirUsuario(String email) {
//        // ISSO AQUI BUSCA 1 ELEMENTO (COM PERSONALIZAÇÃO)
//        Funcionario funcionarioDoBanco = con.queryForObject("SELECT nome, email, senha, fkTipoUsuario, fkEmpresaLocadora, fkEmpresa, fkStatus FROM usuario WHERE email = ?", new BeanPropertyRowMapper<>(Funcionario.class), email);
//        return funcionarioDoBanco;
//    }
//
//    public Representante definirAdmin(String email) {
//        // ISSO AQUI BUSCA 1 ELEMENTO (COM PERSONALIZAÇÃO)
//        Representante usuarioDoBanco = con.queryForObject("SELECT nome, email, senha, fkTipoUsuario, fkEmpresaLocadora, fkEmpresa, fkStatus FROM usuario WHERE email = ?", new BeanPropertyRowMapper<>(Representante.class), email);
//        return usuarioDoBanco;
//    }
//
//    public Funcionario definirFuncionario(String email) {
//        // ISSO AQUI BUSCA 1 ELEMENTO (COM PERSONALIZAÇÃO)
//        Funcionario usuarioDoBanco = con.queryForObject("SELECT nome, email, senha, fkTipoUsuario, fkEmpresaLocadora, fkEmpresa, fkStatus FROM usuario WHERE email = ?", new BeanPropertyRowMapper<>(Funcionario.class), email);
//        return usuarioDoBanco;
//    }
//
//    public List<Funcionario> exibirFuncionario(String email) {
//        // SEMPRE FAZER ESSE BLOCO DE CODIGO PARA PRINTAR NA TELA E GUARDAR NO VETOR "personagensDoBanco"
//        List<Funcionario> capturaDoBanco = con.query("SELECT * FROM usuario WHERE email = ?", new BeanPropertyRowMapper<>(Funcionario.class), email);
//        return capturaDoBanco;
//    }
//
//
//    public List<Usuario> exibirUsuarios(String email) {
//        // ISSO AQUI BUSCA VÁRIOS ELEMENTOS (SEM PERSONALIZAÇÃO)
//        List<Usuario> usuariosDoBanco = con.query("SELECT nome, email, senha, fkTipoUsuario, fkEmpresaLocadora, fkEmpresa, fkStatus FROM usuario WHERE email = ?", new BeanPropertyRowMapper<>(Usuario.class), email);
//        return usuariosDoBanco;
//    }
//
//
//
//
//
//
//
////
////    public void adicionarHardware(Hardware hardware) {
////        con.update("INSERT INTO hardware (nome, capacidade, fkTipoHardware) VALUES (?, ?, ?)", hardware.getNome(), hardware.getCapacidade(), hardware.getFkModeloHardware());
////    }
////
////    public void adicionarHardwareComEspecificidade(Hardware hardware) {
////        con.update("INSERT INTO hardware (nome, especificidade, capacidade, fkTipoHardware) VALUES (?, ?, ?, ?)", hardware.getNome(), hardware.getEspecificidade(), hardware.getCapacidade(), hardware.getFkModeloHardware());
////    }
////
////    public void adicionarComponenteCPU(Componente componente) {
////        con.update("INSERT INTO componente (fkComputador, fkHardware, fkParametro) VALUES (?, ?, ?)", componente.getFkComputador(), componente.getFkHardware(), 1);
////    }
////    public void adicionarComponenteRAM(Componente componente) {
////        con.update("INSERT INTO componente (fkComputador, fkHardware, fkParametro) VALUES (?, ?, ?)", componente.getFkComputador(), componente.getFkHardware(), 2);
////    }
////
////    public void adicionarComponenteDisco(Componente componente) {
////        con.update("INSERT INTO componente (fkComputador, fkHardware, fkParametro) VALUES (?, ?, ?)", componente.getFkComputador(), componente.getFkHardware(), 3);
////    }
////
////    public void adicionarComponenteJanela(Componente componente) {
////        con.update("INSERT INTO componente (fkComputador, fkHardware, fkParametro) VALUES (?, ?, ?)", componente.getFkComputador(), componente.getFkHardware(), 4);
////    }
//
//}

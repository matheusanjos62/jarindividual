package aplicacao;

public class EmpresaLocataria {
    private String nome;
    private Integer fkMatriz;
    private Integer fkEmpresa;
    private Integer fkStatus;

    public EmpresaLocataria() {
    }

    public EmpresaLocataria(String nome, Integer fkMatriz, Integer fkEmpresa, Integer fkStatus) {
        this.nome = nome;
        this.fkMatriz = fkMatriz;
        this.fkEmpresa = fkEmpresa;
        this.fkStatus = fkStatus;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getFkMatriz() {
        return fkMatriz;
    }

    public void setFkMatriz(Integer fkMatriz) {
        this.fkMatriz = fkMatriz;
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
                fkMatriz: %d
                fkEmpresa: %d
                fkStatus: %d""".formatted(nome, fkMatriz, fkEmpresa, fkStatus);
    }
}

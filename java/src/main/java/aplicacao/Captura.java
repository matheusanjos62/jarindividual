package aplicacao;

public class Captura {
    private Integer idCaptura;
    private Double valor;
    private String descricao;
    private String dtCaptura;
    private Integer fkComputador;
    private Integer fkHardware;
    private Integer fkComponente;

    public Captura() {
    }

    public Captura(Integer idCaptura) {
        this.idCaptura = idCaptura;
    }

    public Captura(Integer idCaptura, Integer fkComputador) {
        this.idCaptura = idCaptura;
        this.fkComputador = fkComputador;
    }

    public Captura(Double valor, Integer fkComputador, Integer fkHardware, Integer fkComponente) {
        this.valor = valor;
        this.fkComputador = fkComputador;
        this.fkHardware = fkHardware;
        this.fkComponente = fkComponente;
    }

    public Captura(Double valor, String descricao, String dtCaputra, Integer fkComputador, Integer fkHardware, Integer fkComponente) {
        this.valor = valor;
        this.descricao = descricao;
        this.dtCaptura = dtCaputra;
        this.fkComputador = fkComputador;
        this.fkHardware = fkHardware;
        this.fkComponente = fkComponente;
    }

    public Integer getIdCaptura() {
        return idCaptura;
    }

    public void setIdCaptura(Integer idCaptura) {
        this.idCaptura = idCaptura;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDtCaptura() {
        return dtCaptura;
    }

    public void setDtCaptura(String dtCaputra) {
        this.dtCaptura = dtCaputra;
    }

    public Integer getFkComputador() {
        return fkComputador;
    }

    public void setFkComputador(Integer fkComputador) {
        this.fkComputador = fkComputador;
    }

    public Integer getFkHardware() {
        return fkHardware;
    }

    public void setFkHardware(Integer fkHardware) {
        this.fkHardware = fkHardware;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    @Override
    public String toString() {
        return """
                \nidCaptura: %d
                valor: %.2f
                descricao: %s
                dtCaptura: %s
                fkComputador: %d
                fkHardware: %d
                fkComponente: %d""".formatted(idCaptura, valor, descricao, dtCaptura, fkComputador, fkHardware, fkComponente);
    }
}

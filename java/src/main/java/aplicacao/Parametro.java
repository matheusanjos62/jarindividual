package aplicacao;

import dao.DaoMySQL;

import java.util.List;

public class Parametro {
    private Double min;
    private Double max;
    private Integer fkUnidadeMedida;
    private Integer fkTipoHardware;
    private Integer fkModeloComputador;

    public Parametro() {
    }

    public Parametro(Double min, Double max, Integer fkUnidadeMedida, Integer fkTipoHardware, Integer fkModeloComputador) {
        this.min = min;
        this.max = max;
        this.fkUnidadeMedida = fkUnidadeMedida;
        this.fkTipoHardware = fkTipoHardware;
        this.fkModeloComputador = fkModeloComputador;
    }

//    public void seiLaOq() {
//        DaoMySQL daoMySQL = new DaoMySQL()
//        Double valorInicial;
//        Double valorFinal;
//        Double range;
//        Double alertaVermelhoAbaixo;
//        Double alertaAmareloAbaixo;
//        Double alertaAmareloAcima;
//        Double alertaVermelhoAcima;
//        Double valorAtual;
//
//        List<Parametro> parametros = (daoMySQL.exibirParametrosDoModeloComputador(computador.getFkModeloComputador()));
//        valorInicial = parametroAtual.getMin();
//        valorFinal = parametroAtual.getMax();
//        range = valorFinal - valorInicial;
//        alertaVermelhoAbaixo = valorInicial + (range * 0.125);
//        alertaAmareloAbaixo = valorInicial + (range * 0.25);
//        alertaAmareloAcima = valorInicial + (range * 0.75);
//        alertaVermelhoAcima = valorInicial + (range * 0.875);
//    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Integer getFkUnidadeMedida() {
        return fkUnidadeMedida;
    }

    public void setFkUnidadeMedida(Integer fkUnidadeMedida) {
        this.fkUnidadeMedida = fkUnidadeMedida;
    }

    public Integer getFkTipoHardware() {
        return fkTipoHardware;
    }

    public void setFkTipoHardware(Integer fkTipoHardware) {
        this.fkTipoHardware = fkTipoHardware;
    }

    public Integer getFkModeloComputador() {
        return fkModeloComputador;
    }

    public void setFkModeloComputador(Integer fkModeloComputador) {
        this.fkModeloComputador = fkModeloComputador;
    }

    @Override
    public String toString() {
        return """
                \nmin: %s
                max: %s
                fkUnidadeMedida: %d
                fkTipoHardware: %d
                fkModeloComputador: %d""".formatted(min, max, fkUnidadeMedida, fkTipoHardware, fkModeloComputador);
    }
}

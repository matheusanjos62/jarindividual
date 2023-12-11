package aplicacao;

public class Alerta {
    private String titulo;
    private Integer fkCaptura;
    private Integer fkTipoAlerta;

    public Alerta() {
    }

    public Alerta(String titulo, Integer fkCaptura, Integer fkTipoAlerta) {
        this.titulo = titulo;
        this.fkCaptura = fkCaptura;
        this.fkTipoAlerta = fkTipoAlerta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getFkCaptura() {
        return fkCaptura;
    }

    public void setFkCaptura(Integer fkCaptura) {
        this.fkCaptura = fkCaptura;
    }

    public Integer getFkTipoAlerta() {
        return fkTipoAlerta;
    }

    public void setFkTipoAlerta(Integer fkTipoAlerta) {
        this.fkTipoAlerta = fkTipoAlerta;
    }

    @Override
    public String toString() {
        return """
                \ntitulo: %s
                fkCaptura: %d
                fkTipoAlerta: %d""".formatted(titulo, fkCaptura, fkTipoAlerta);
    }
}

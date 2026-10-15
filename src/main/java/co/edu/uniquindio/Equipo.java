package co.edu.uniquindio;

public abstract class Equipo {
    private String codigo;
    private String marca;
    private String modelo;
    private CondicionEquipo condicion;

    public Equipo(String codigo, String marca, String modelo, CondicionEquipo condicion) {
        this.codigo = codigo;
        this.marca = marca;
        this.modelo = modelo;
        this.condicion = condicion;
    }

    public abstract double calcularCosto(int horas);

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + marca + " " + modelo + " (" + condicion + ")";
    }
}

package co.edu.uniquindio;

public class Equipo {
    private String codigo;
    private String marca;
    private String modelo;
    private String condicion; // disponible, en mantenimiento, fuera de servicio

    public Equipo(String codigo, String marca, String modelo, String condicion) {
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

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    @Override
    public String toString() {
        return "[" + codigo + "] " + marca + " " + modelo + " (" + condicion + ")";
    }
}

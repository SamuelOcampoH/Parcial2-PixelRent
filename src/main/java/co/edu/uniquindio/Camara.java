package co.edu.uniquindio;

public class Camara extends Equipo {

    private String resolucion;
    private boolean tieneEstabilizador;

    public Camara(String codigo, String marca, String modelo, CondicionEquipo condicion, String resolucion, boolean tieneEstabilizador) {
        super(codigo, marca, modelo, condicion);
        this.resolucion = resolucion;
        this.tieneEstabilizador = tieneEstabilizador;
    }
    @Override
    public double calcularCosto(int horas) {
        double costo = 12000.0 * horas;
        if (tieneEstabilizador) {
            costo += 15000.0;
        }
        return costo;
    }
    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public boolean isTieneEstabilizador() {
        return tieneEstabilizador;
    }

    public void setTieneEstabilizador(boolean tieneEstabilizador) {
        this.tieneEstabilizador = tieneEstabilizador;
    }

    @Override
    public String toString() {
        return super.toString() + " - Camara " + resolucion + (tieneEstabilizador ? " (Con estabilizador)" : "");
    }
}

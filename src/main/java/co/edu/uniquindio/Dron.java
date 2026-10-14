package co.edu.uniquindio;

public class Dron extends Equipo {
    private int autonomiaMinutos;
    private boolean tieneBateriaAdicional;

    public Dron(String codigo, String marca, String modelo, CondicionEquipo condicion, int autonomiaMinutos, boolean tieneBateriaAdicional) {
        super(codigo, marca, modelo, condicion);
        this.autonomiaMinutos = autonomiaMinutos;
        this.tieneBateriaAdicional = tieneBateriaAdicional;
    }

    @Override
    public double calcularCosto(int horas) {
        double costo = 18000.0 * horas;
        if (tieneBateriaAdicional) {
            costo += 20000.0;
        }
        return costo;
    }

    public int getAutonomiaMinutos() {
        return autonomiaMinutos;
    }

    public void setAutonomiaMinutos(int autonomiaMinutos) {
        this.autonomiaMinutos = autonomiaMinutos;
    }

    public boolean isTieneBateriaAdicional() {
        return tieneBateriaAdicional;
    }

    public void setTieneBateriaAdicional(boolean tieneBateriaAdicional) {
        this.tieneBateriaAdicional = tieneBateriaAdicional;
    }

    @Override
    public String toString() {
        return super.toString() + " - Dron " + autonomiaMinutos + " min" + (tieneBateriaAdicional ? " (Bat. Extra)" : "");
    }
}
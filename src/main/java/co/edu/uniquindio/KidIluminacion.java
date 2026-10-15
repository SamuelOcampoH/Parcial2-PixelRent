package co.edu.uniquindio;

public class KidIluminacion extends Equipo {
    private int cantidadLuces;
    private boolean tieneControlInalambrico;

    public KidIluminacion(String codigo, String marca, String modelo, CondicionEquipo condicion, int cantidadLuces, boolean tieneControlInalambrico) {
        super(codigo, marca, modelo, condicion);
        this.cantidadLuces = cantidadLuces;
        this.tieneControlInalambrico = tieneControlInalambrico;
    }

    @Override
    public double calcularCosto(int horas) {
        double costo = 10000.0 * horas;
        if (tieneControlInalambrico) {
            costo += 12000.0;
        }
        return costo;
    }

    public int getCantidadLuces() {
        return cantidadLuces;
    }

    public void setCantidadLuces(int cantidadLuces) {
        this.cantidadLuces = cantidadLuces;
    }

    public boolean isTieneControlInalambrico() {
        return tieneControlInalambrico;
    }

    public void setTieneControlInalambrico(boolean tieneControlInalambrico) {
        this.tieneControlInalambrico = tieneControlInalambrico;
    }

    @Override
    public String toString() {
        return super.toString() + " - Kit " + cantidadLuces + " luces" + (tieneControlInalambrico ? " (Con control)" : "");
    }
}

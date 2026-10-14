
package co.edu.uniquindio;

public class DetalleReserva {
    private Equipo equipo;
    private int horas;

    public DetalleReserva(Equipo equipo, int horas) {
        this.equipo = equipo;
        this.horas = horas;
    }

    public double calcularSubtotal() {
        if (equipo != null) {
            return equipo.calcularCosto(horas);
        }
        return 0.0;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    @Override
    public String toString() {
        return equipo.toString() + " | " + horas + " horas -> Subtotal: $" + calcularSubtotal();
    }
}
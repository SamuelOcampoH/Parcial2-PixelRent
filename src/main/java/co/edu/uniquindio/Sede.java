package co.edu.uniquindio;

public class Sede {
    private String codigo;
    private String nombre;
    private String direccion;
    private Equipo[] listaEquipos;

    public Sede(String codigo, String nombre, String direccion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.listaEquipos = new Equipo[20];
    }

    public boolean agregarEquipo(Equipo equipo) {
        if (equipo == null) {
            return false;
        }
        for (int i = 0; i < listaEquipos.length; i++) {
            if (listaEquipos[i] != null && listaEquipos[i].getCodigo().equalsIgnoreCase(equipo.getCodigo())) {
                return false;
            }
        }
        for (int i = 0; i < listaEquipos.length; i++) {
            if (listaEquipos[i] == null) {
                listaEquipos[i] = equipo;
                return true;
            }
        }
        return false;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Equipo[] getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(Equipo[] listaEquipos) {
        this.listaEquipos = listaEquipos;
    }

    @Override
    public String toString() {
        return "Sede [" + codigo + "] " + nombre + " - " + direccion;
    }
}


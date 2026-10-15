package co.edu.uniquindio;

public class PixelRent {
    private Sede[] listaSedes;
    private Cliente[] listaClientes;
    private Operador[] listaOperadores;
    private Equipo[] listaEquipos;
    private Reserva[] listaReservas;

    public PixelRent() {
        this.listaSedes = new Sede[10];
        this.listaClientes = new Cliente[30];
        this.listaOperadores = new Operador[15];
        this.listaEquipos = new Equipo[50];
        this.listaReservas = new Reserva[50];
    }

    // ================= CRUD Y VALIDACIÓN DE DUPLICIDAD =================
    public boolean registrarCliente(Cliente cliente) {
        if (cliente == null || buscarCliente(cliente.getIdentificacion()) != null) {
            return false;
        }
        for (int i = 0; i < listaClientes.length; i++) {
            if (listaClientes[i] == null) {
                listaClientes[i] = cliente;
                return true;
            }
        }
        return false;
    }

    public Cliente buscarCliente(String identificacion) {
        for (int i = 0; i < listaClientes.length; i++) {
            if (listaClientes[i] != null && listaClientes[i].getIdentificacion().equalsIgnoreCase(identificacion)) {
                return listaClientes[i];
            }
        }
        return null;
    }

    public boolean registrarOperador(Operador operador) {
        if (operador == null || buscarOperador(operador.getIdentificacion()) != null) {
            return false;
        }
        for (int i = 0; i < listaOperadores.length; i++) {
            if (listaOperadores[i] == null) {
                listaOperadores[i] = operador;
                return true;
            }
        }
        return false;
    }

    public Operador buscarOperador(String identificacion) {
        for (int i = 0; i < listaOperadores.length; i++) {
            if (listaOperadores[i] != null && listaOperadores[i].getIdentificacion().equalsIgnoreCase(identificacion)) {
                return listaOperadores[i];
            }
        }
        return null;
    }

    public boolean registrarEquipo(Equipo equipo) {
        if (equipo == null || buscarEquipo(equipo.getCodigo()) != null) {
            return false;
        }
        for (int i = 0; i < listaEquipos.length; i++) {
            if (listaEquipos[i] == null) {
                listaEquipos[i] = equipo;
                return true;
            }
        }
        return false;
    }

    public Equipo buscarEquipo(String codigo) {
        for (int i = 0; i < listaEquipos.length; i++) {
            if (listaEquipos[i] != null && listaEquipos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return listaEquipos[i];
            }
        }
        return null;
    }

    public boolean registrarReserva(Reserva reserva) {
        if (reserva == null || buscarReserva(reserva.getCodigo()) != null) {
            return false;
        }
        for (int i = 0; i < listaReservas.length; i++) {
            if (listaReservas[i] == null) {
                listaReservas[i] = reserva;
                return true;
            }
        }
        return false;
    }

    public Reserva buscarReserva(String codigo) {
        for (int i = 0; i < listaReservas.length; i++) {
            if (listaReservas[i] != null && listaReservas[i].getCodigo().equalsIgnoreCase(codigo)) {
                return listaReservas[i];
            }
        }
        return null;
    }

    // ================= FUNCIONALIDADES DE RESERVA Y REGLAS DE NEGOCIO =================
    public boolean agregarEquipoAReserva(String codReserva, String codEquipo, int horas) {
        Reserva res = buscarReserva(codReserva);
        Equipo eq = buscarEquipo(codEquipo);

        if (res == null || eq == null || horas <= 0) {
            return false;
        }
        // Solo agregar si la condición es disponible
        if (!eq.getCondicion().equalsIgnoreCase("disponible")) {
            return false;
        }
        DetalleReserva detalle = new DetalleReserva(eq, horas);
        return res.agregarDetalle(detalle);
    }

    public boolean asignarOperadorAReserva(String codReserva, String idOperador) {
        Reserva res = buscarReserva(codReserva);
        Operador op = buscarOperador(idOperador);
        if (res == null || op == null) {
            return false;
        }
        res.setOperador(op);
        return true;
    }

    public boolean confirmarReserva(String codReserva) {
        Reserva res = buscarReserva(codReserva);
        if (res == null) {
            return false;
        }
        // 1. Bloquear confirmación si fue cancelada o finalizada previamente
        if (res.getEstado().equalsIgnoreCase("cancelada") || res.getEstado().equalsIgnoreCase("finalizada")) {
            return false;
        }
        // 2. Debe contener al menos un equipo
        if (res.cantidadEquipos() == 0) {
            return false;
        }
        // 3. Si incluye dron, obligatorio operador asignado
        if (res.tieneDron() && res.getOperador() == null) {
            return false;
        }
        // 4. Ningún equipo puede estar en otra reserva confirmada en la misma fecha y turno
        DetalleReserva[] detallesActuales = res.getListaDetalles();
        for (int i = 0; i < detallesActuales.length; i++) {
            if (detallesActuales[i] != null) {
                String codEq = detallesActuales[i].getEquipo().getCodigo();
                if (equipoOcupadoEnOtraReserva(codEq, res.getFecha(), res.getTurno(), res.getCodigo())) {
                    return false;
                }
            }
        }

        res.setEstado("confirmada");
        return true;
    }

    private boolean equipoOcupadoEnOtraReserva(String codEquipo, String fecha, String turno, String codReservaExcluir) {
        for (int i = 0; i < listaReservas.length; i++) {
            Reserva r = listaReservas[i];
            if (r != null && r.getEstado().equalsIgnoreCase("confirmada")
                    && !r.getCodigo().equalsIgnoreCase(codReservaExcluir)
                    && r.getFecha().equalsIgnoreCase(fecha)
                    && r.getTurno().equalsIgnoreCase(turno)) {

                DetalleReserva[] dets = r.getListaDetalles();
                for (int j = 0; j < dets.length; j++) {
                    if (dets[j] != null && dets[j].getEquipo().getCodigo().equalsIgnoreCase(codEquipo)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean cambiarEstadoReserva(String codReserva, String nuevoEstado) {
        Reserva res = buscarReserva(codReserva);
        if (res == null) {
            return false;
        }
        if (nuevoEstado.equalsIgnoreCase("confirmada")) {
            return confirmarReserva(codReserva);
        } else if (nuevoEstado.equalsIgnoreCase("cancelada") || nuevoEstado.equalsIgnoreCase("finalizada")) {
            res.setEstado(nuevoEstado.toLowerCase());
            return true;
        }
        return false;
    }

    // ================= CONSULTAS Y REPORTES =================
    public Equipo[] consultarEquiposDisponibles(String fecha, String turno) {
        Equipo[] disponibles = new Equipo[listaEquipos.length];
        int pos = 0;

        for (int i = 0; i < listaEquipos.length; i++) {
            Equipo eq = listaEquipos[i];
            if (eq != null && eq.getCondicion().equalsIgnoreCase("disponible")) {
                if (!equipoOcupadoEnOtraReserva(eq.getCodigo(), fecha, turno, "")) {
                    disponibles[pos] = eq;
                    pos++;
                }
            }
        }
        return disponibles;
    }

    public Reserva[] consultarReservasConfirmadasCliente(String identificacion) {
        Reserva[] encontradas = new Reserva[listaReservas.length];
        int pos = 0;

        for (int i = 0; i < listaReservas.length; i++) {
            Reserva r = listaReservas[i];
            if (r != null && r.getEstado().equalsIgnoreCase("confirmada")
                    && r.getCliente() != null
                    && r.getCliente().getIdentificacion().equalsIgnoreCase(identificacion)) {
                encontradas[pos] = r;
                pos++;
            }
        }
        return encontradas;
    }

    public double consultarValorTotalReserva(String codigo) {
        Reserva r = buscarReserva(codigo);
        if (r != null) {
            return r.calcularTotal();
        }
        return -1.0;
    }

    // ================= GETTERS Y SETTERS =================
    public Sede[] getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(Sede[] listaSedes) {
        this.listaSedes = listaSedes;
    }

    public Cliente[] getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(Cliente[] listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Operador[] getListaOperadores() {
        return listaOperadores;
    }

    public void setListaOperadores(Operador[] listaOperadores) {
        this.listaOperadores = listaOperadores;
    }

    public Equipo[] getListaEquipos() {
        return listaEquipos;
    }

    public void setListaEquipos(Equipo[] listaEquipos) {
        this.listaEquipos = listaEquipos;
    }

    public Reserva[] getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(Reserva[] listaReservas) {
        this.listaReservas = listaReservas;
    }
}


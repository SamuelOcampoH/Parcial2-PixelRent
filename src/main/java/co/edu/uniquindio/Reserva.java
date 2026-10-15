package co.edu.uniquindio;

    public class Reserva {
        private String codigo;
        private String fecha;
        private String turno;
        private String estado;
        private Cliente cliente;
        private Operador operador;
        private DetalleReserva[] listaDetalles;

        public Reserva(String codigo, String fecha, String turno, Cliente cliente) {
            this.codigo = codigo;
            this.fecha = fecha;
            this.turno = turno;
            this.estado = "solicitada";
            this.cliente = cliente;
            this.operador = null;
            this.listaDetalles = new DetalleReserva[10];
        }


        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getTurno() {
            return turno;
        }

        public void setTurno(String turno) {
            this.turno = turno;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public Cliente getCliente() {
            return cliente;
        }

        public void setCliente(Cliente cliente) {
            this.cliente = cliente;
        }

        public Operador getOperador() {
            return operador;
        }

        public void setOperador(Operador operador) {
            this.operador = operador;
        }

        public DetalleReserva[] getListaDetalles() {
            return listaDetalles;
        }

        public void setListaDetalles(DetalleReserva[] listaDetalles) {
            this.listaDetalles = listaDetalles;
        }

        @Override
        public String toString() {
            String info = "Reserva [" + codigo + "] | Fecha: " + fecha + " | Turno: " + turno + " | Estado: " + estado + "\n"
                    + "Cliente: " + (cliente != null ? cliente.getNombreCompleto() : "N/A") + "\n"
                    + "Operador: " + (operador != null ? operador.getNombreCompleto() : "Ninguno") + "\n"
                    + "Equipos reservados:\n";
            for (int i = 0; i < listaDetalles.length; i++) {
                if (listaDetalles[i] != null) {
                    info += " - " + listaDetalles[i].toString() + "\n";
                }
            }
            info += "Total Reserva: $" + calcularTotal();
            return info;
        }
        public boolean agregarDetalle(DetalleReserva detalle) {
            if (detalle == null || detalle.getEquipo() == null) {
                return false;
            }
            for (int i = 0; i < listaDetalles.length; i++) {
                if (listaDetalles[i] != null && listaDetalles[i].getEquipo().getCodigo().equalsIgnoreCase(detalle.getEquipo().getCodigo())) {
                    return false;
                }
            }
            for (int i = 0; i < listaDetalles.length; i++) {
                if (listaDetalles[i] == null) {
                    listaDetalles[i] = detalle;
                    return true;
                }
            }
            return false;
        }

        public boolean tieneDron() {
            for (int i = 0; i < listaDetalles.length; i++) {
                if (listaDetalles[i] != null && listaDetalles[i].getEquipo() instanceof Dron) {
                    return true;
                }
            }
            return false;
        }

        public int cantidadEquipos() {
            int contador = 0;
            for (int i = 0; i < listaDetalles.length; i++) {
                if (listaDetalles[i] != null) {
                    contador++;
                }
            }
            return contador;
        }

        public double calcularTotal() {
            double total = 0.0;
            for (int i = 0; i < listaDetalles.length; i++) {
                if (listaDetalles[i] != null) {
                    total += listaDetalles[i].calcularSubtotal();
                }
            }
            return total;
        }
    }


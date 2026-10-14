package co.edu.uniquindio;

    public class Cliente {
        private String nombreCompleto;
        private String identificacion;
        private int telefono;
        private String correoElectronico;

        public Cliente(String nombreCompleto, String identificacion, int telefono, String correoElectronico) {
            this.nombreCompleto = nombreCompleto;
            this.identificacion = identificacion;
            this.telefono = telefono;
            this.correoElectronico = correoElectronico;
        }

        public String getNombreCompleto() {
            return nombreCompleto;
        }

        public void setNombreCompleto(String nombreCompleto) {
            this.nombreCompleto = nombreCompleto;
        }

        public String getIdentificacion() {
            return identificacion;
        }

        public void setIdentificacion(String identificacion) {
            this.identificacion = identificacion;
        }

        public int getTelefono() {
            return telefono;
        }

        public void setTelefono(int telefono) {
            this.telefono = telefono;
        }

        public String getCorreoElectronico() {
            return correoElectronico;
        }

        public void setCorreoElectronico(String correoElectronico) {
            this.correoElectronico = correoElectronico;
        }

        @Override
        public String toString() {
            return "Cliente: " + nombreCompleto + " | ID: " + identificacion + " | Tel: " + telefono + " | Email: " + correoElectronico;
        }
    }


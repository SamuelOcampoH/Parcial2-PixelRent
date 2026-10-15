package co.edu.uniquindio;

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        PixelRent empresa = new PixelRent();
        precargarDatosPrueba(empresa);

        int opcion = 0;
        do {
            String menu = "=== SISTEMA DE GESTION PIXELRENT ===\n"
                    + "1. Registrar Cliente\n"
                    + "2. Registrar Equipo (Camara / Dron / Iluminacion)\n"
                    + "3. Registrar Operador\n"
                    + "4. Crear Reserva (Solicitada)\n"
                    + "5. Agregar Equipo a Reserva\n"
                    + "6. Asignar Operador a Reserva\n"
                    + "7. Confirmar Reserva\n"
                    + "8. Consultar Equipos Disponibles (Fecha y Turno)\n"
                    + "9. Consultar Reservas Confirmadas de un Cliente\n"
                    + "10. Consultar Valor Total y Detalle de Reserva\n"
                    + "11. Salir\n\n"
                    + "Seleccione una opcion:";

            String entrada = JOptionPane.showInputDialog(null, menu, "PixelRent", JOptionPane.QUESTION_MESSAGE);
            if (entrada == null) {
                break;
            }

            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1 -> registrarCliente(empresa);
                case 2 -> registrarEquipo(empresa);
                case 3 -> registrarOperador(empresa);
                case 4 -> crearReserva(empresa);
                case 5 -> agregarEquipoReserva(empresa);
                case 6 -> asignarOperadorReserva(empresa);
                case 7 -> confirmarReserva(empresa);
                case 8 -> consultarEquiposDisponibles(empresa);
                case 9 -> consultarReservasCliente(empresa);
                case 10 -> consultarDetalleReserva(empresa);
                case 11 -> JOptionPane.showMessageDialog(null, "Saliendo del sistema PixelRent...", "PixelRent", JOptionPane.INFORMATION_MESSAGE);
                default -> JOptionPane.showMessageDialog(null, "Opcion invalida, intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (opcion != 11);
    }

    // ================= MÉTODOS DE CAPTURA =================
    private static void registrarCliente(PixelRent empresa) {
        try {
            String id = JOptionPane.showInputDialog("Ingrese identificacion del cliente:");
            String nombre = JOptionPane.showInputDialog("Ingrese nombre completo:");
            int tel = Integer.parseInt(JOptionPane.showInputDialog("Ingrese telefono (solo numeros):"));
            String correo = JOptionPane.showInputDialog("Ingrese correo electronico:");

            Cliente nuevo = new Cliente(nombre, id, tel, correo);
            if (empresa.registrarCliente(nuevo)) {
                JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error: El cliente ya existe o la lista esta llena.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Datos invalidos (recuerde ingresar numeros en el telefono).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void registrarEquipo(PixelRent empresa) {
        String[] tipos = {"Camara", "Dron", "Kit Iluminacion"};
        int sel = JOptionPane.showOptionDialog(null, "Seleccione el tipo de equipo:", "PixelRent",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

        if (sel == -1) return;

        String cod = JOptionPane.showInputDialog("Codigo del equipo:");
        String marca = JOptionPane.showInputDialog("Marca:");
        String modelo = JOptionPane.showInputDialog("Modelo:");
        CondicionEquipo condicion = CondicionEquipo.DISPONIBLE;

        Equipo nuevo = null;
        if (sel == 0) {
            String res = JOptionPane.showInputDialog("Resolucion maxima (ej: 4K, 8K):");
            int est = JOptionPane.showConfirmDialog(null, "¿Posee estabilizador?", "Camara", JOptionPane.YES_NO_OPTION);
            nuevo = new Camara(cod, marca, modelo, condicion, res, est == JOptionPane.YES_OPTION);
        } else if (sel == 1) {
            int aut = Integer.parseInt(JOptionPane.showInputDialog("Autonomia de vuelo (minutos):"));
            int bat = JOptionPane.showConfirmDialog(null, "¿Tiene bateria adicional?", "Dron", JOptionPane.YES_NO_OPTION);
            nuevo = new Dron(cod, marca, modelo, condicion, aut, bat == JOptionPane.YES_OPTION);
        } else if (sel == 2) {
            int luces = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de luces:"));
            int ctrl = JOptionPane.showConfirmDialog(null, "¿Tiene control inalambrico?", "Iluminacion", JOptionPane.YES_NO_OPTION);
            // Si dejaste la clase como KidIluminacion pon new KidIluminacion(...); si la renombraste a KitIluminacion pon new KitIluminacion(...)
            nuevo = new KidIluminacion(cod, marca, modelo, condicion, luces, ctrl == JOptionPane.YES_OPTION);
        }

        if (nuevo != null && empresa.registrarEquipo(nuevo)) {
            JOptionPane.showMessageDialog(null, "Equipo registrado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Error: Codigo repetido o memoria llena.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void registrarOperador(PixelRent empresa) {
        try {
            String id = JOptionPane.showInputDialog("Identificacion del operador:");
            String nombre = JOptionPane.showInputDialog("Nombre completo:");
            String cert = JOptionPane.showInputDialog("Certificacion tecnica:");
            int exp = Integer.parseInt(JOptionPane.showInputDialog("Anios de experiencia:"));

            Operador op = new Operador(id, nombre, cert, exp);
            if (empresa.registrarOperador(op)) {
                JOptionPane.showMessageDialog(null, "Operador registrado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar operador (repetido o lleno).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Datos invalidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void crearReserva(PixelRent empresa) {
        try {
            String cod = JOptionPane.showInputDialog("Codigo unico de la reserva:");
            String idCli = JOptionPane.showInputDialog("Identificacion del cliente solicitante:");
            Cliente cli = empresa.buscarCliente(idCli);

            if (cli == null) {
                JOptionPane.showMessageDialog(null, "Cliente no encontrado. Debe registrarlo primero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String fecha = JOptionPane.showInputDialog("Fecha requerida (DD/MM/AAAA):");
            Turno[] turnos = Turno.values();
            int turnosSel = JOptionPane.showOptionDialog(null, "Seleccione turno:", "Turno",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, turnos, turnos[0]);
            if (turnosSel == -1) return;

            Reserva res = new Reserva(cod, fecha, turnos[turnosSel], cli);
            if (empresa.registrarReserva(res)) {
                JOptionPane.showMessageDialog(null, "Reserva creada exitosamente en estado SOLICITADA.");
            } else {
                JOptionPane.showMessageDialog(null, "Error: Codigo de reserva repetido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Datos incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void agregarEquipoReserva(PixelRent empresa) {
        try {
            String codRes = JOptionPane.showInputDialog("Codigo de la reserva:");
            String codEq = JOptionPane.showInputDialog("Codigo del equipo a agregar:");
            int horas = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de horas de uso:"));

            if (empresa.agregarEquipoAReserva(codRes, codEq, horas)) {
                JOptionPane.showMessageDialog(null, "Equipo agregado a la reserva exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo agregar el equipo (no existe, no disponible o ya esta en la reserva).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void asignarOperadorReserva(PixelRent empresa) {
        String codRes = JOptionPane.showInputDialog("Codigo de la reserva:");
        String idOp = JOptionPane.showInputDialog("Identificacion del operador:");

        if (empresa.asignarOperadorAReserva(codRes, idOp)) {
            JOptionPane.showMessageDialog(null, "Operador asignado exitosamente a la reserva.");
        } else {
            JOptionPane.showMessageDialog(null, "Error: Reserva u operador no encontrados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void confirmarReserva(PixelRent empresa) {
        String codRes = JOptionPane.showInputDialog("Codigo de la reserva a confirmar:");
        if (empresa.confirmarReserva(codRes)) {
            JOptionPane.showMessageDialog(null, "¡Reserva CONFIRMADA con exito!");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo confirmar la reserva.\n"
                    + "Reglas a cumplir:\n"
                    + "1. Debe contener al menos un equipo.\n"
                    + "2. Si incluye un dron, requiere operador asignado obligatoriamente.\n"
                    + "3. Ningun equipo puede estar en otra reserva confirmada en la misma fecha y turno.\n"
                    + "4. No puede confirmarse si estaba cancelada o finalizada.", "Validacion Fallida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static void consultarEquiposDisponibles(PixelRent empresa) {
        String fecha = JOptionPane.showInputDialog("Ingrese fecha a consultar (DD/MM/AAAA):");
        Turno[] turnos = Turno.values();
        int turnosSel = JOptionPane.showOptionDialog(null, "Seleccione turno:", "Turno",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, turnos, turnos[0]);
        if (turnosSel == -1) return;

        Equipo[] disponibles = empresa.consultarEquiposDisponibles(fecha, turnos[turnosSel]);
        String texto = "Equipos disponibles para " + fecha + " (" + turnos[turnosSel] + "):\n\n";
        boolean hay = false;
        for (int i = 0; i < disponibles.length; i++) {
            if (disponibles[i] != null) {
                texto += "- " + disponibles[i].toString() + "\n";
                hay = true;
            }
        }
        if (!hay) texto += "No hay equipos disponibles para esa fecha y turno.";
        JOptionPane.showMessageDialog(null, texto, "Equipos Disponibles", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void consultarReservasCliente(PixelRent empresa) {
        String id = JOptionPane.showInputDialog("Ingrese identificacion del cliente:");
        Reserva[] confirmadas = empresa.consultarReservasConfirmadasCliente(id);

        String texto = "Reservas CONFIRMADAS para el cliente ID " + id + ":\n\n";
        boolean hay = false;
        for (int i = 0; i < confirmadas.length; i++) {
            if (confirmadas[i] != null) {
                texto += confirmadas[i].toString() + "\n--------------------------------\n";
                hay = true;
            }
        }
        if (!hay) texto += "El cliente no registra reservas en estado CONFIRMADA.";
        JOptionPane.showMessageDialog(null, texto, "Reservas Confirmadas", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void consultarDetalleReserva(PixelRent empresa) {
        String cod = JOptionPane.showInputDialog("Ingrese codigo de la reserva:");
        Reserva r = empresa.buscarReserva(cod);
        if (r != null) {
            JOptionPane.showMessageDialog(null, r.toString(), "Detalle de Reserva", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Reserva no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ================= DATOS DE PRUEBA PRECARGADOS =================
    private static void precargarDatosPrueba(PixelRent empresa) {
        // Clientes
        empresa.registrarCliente(new Cliente("Juan Perez", "101", 7451234, "juan@gmail.com"));
        empresa.registrarCliente(new Cliente("Maria Lopez", "102", 7469876, "maria@gmail.com"));

        // Operadores
        empresa.registrarOperador(new Operador("OP01", "Carlos DronePilot", "Piloto Profesional UAS", 5));
        empresa.registrarOperador(new Operador("OP02", "Ana Luces", "Especialista Iluminacion", 3));

        // Equipos
        empresa.registrarEquipo(new Camara("CAM01", "Sony", "Alpha 7 IV", CondicionEquipo.DISPONIBLE, "4K", true));
        empresa.registrarEquipo(new Camara("CAM02", "Canon", "EOS R5", CondicionEquipo.DISPONIBLE, "8K", false));
        empresa.registrarEquipo(new Dron("DRON01", "DJI", "Mavic 3 Pro", CondicionEquipo.DISPONIBLE, 46, true));
        // Como en tu árbol de archivos tu clase se llama KidIluminacion, se instancia como KidIluminacion:
        empresa.registrarEquipo(new KidIluminacion("KIT01", "Aputure", "Amaran 200d", CondicionEquipo.DISPONIBLE, 3, true));
        empresa.registrarEquipo(new KidIluminacion("KIT02", "Godox", "SL60W", CondicionEquipo.EN_MANTENIMIENTO, 2, false));
    }
}
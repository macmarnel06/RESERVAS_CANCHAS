import java.util.ArrayList;

public class Controlador {
    private SistemaReservas sistema;
    private VistaReservas vista;
    
    public ControladorReservas(SistemaReservas sistema, VistaReservas vista) {
        this.sistema = sistema;
        this.vista = vista;
    }
    
    public void iniciar() {
        int opcion;
        do {
            vista.mostrarUsuarioActual(sistema.getUsuarioActual());
            opcion = vista.mostrarMenu();
            procesarOpcion(opcion);
        } while (opcion != 10);
    }
    
    public void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                iniciarSesion();
                break;
            case 2:
                registrarUsuario();
                break;
            case 3:
                if (verificarAccesoEmpleado()) registrarCancha();
                break;
            case 4:
                if (verificarAccesoOrganizador()) solicitarReserva();
                break;
            case 5:
                mostrarReservasCancha();
                break;
            case 6:
                if (verificarAccesoOrganizador()) cancelarReserva();
                break;
            case 7:
                if (verificarAccesoEmpleado()) mostrarListaEspera();
                break;
            case 8:
                if (verificarAccesoEmpleado()) mostrarEstadisticas();
                break;
            case 9:
                cerrarSesion();
                break;
            case 10:
                vista.mostrarMensaje("¡Hasta pronto!");
                break;
            default:
                vista.mostrarMensaje("Opción no válida.");
        }
    }
    
    private void iniciarSesion() {
        String email = vista.solicitarEmail();
        Usuario usuario = sistema.autenticarUsuario(email);
        if (usuario != null) {
            vista.mostrarMensaje("¡Bienvenido " + usuario.getNombre() + "!");
        } else {
            vista.mostrarMensaje("Usuario no encontrado.");
        }
    }
    
    private void registrarUsuario() {
        Usuario usuario = vista.solicitarDatosUsuario();
        if (sistema.registrarUsuario(usuario)) {
            vista.mostrarMensaje("Usuario registrado exitosamente.");
        } else {
            vista.mostrarMensaje("Error: El email ya está registrado.");
        }
    }
    
    private void cerrarSesion() {
        sistema.cerrarSesion();
        vista.mostrarMensaje("Sesión cerrada exitosamente.");
    }
    
    private void registrarCancha() {
        Cancha cancha = vista.solicitarDatosCancha();
        if (cancha != null) {
            sistema.agregarCancha(cancha);
            vista.mostrarMensaje("Cancha registrada exitosamente.");
        }
    }
    
    private void solicitarReserva() {
        Reserva reserva = vista.solicitarDatosReserva();
        int id = sistema.solicitarReserva(reserva);
        
        if (id == -1) {
            vista.mostrarMensaje("La reserva no cumple con las reglas del complejo.");
        } else if (reserva.isAsignada()) {
            vista.mostrarMensaje("Reserva confirmada. ID: " + id);
        } else {
            vista.mostrarMensaje("Reserva en lista de espera. ID: " + id);
        }
    }
    
    private void mostrarReservasCancha() {
        int numeroCancha = vista.solicitarNumeroCancha();
        ArrayList<Reserva> reservas = sistema.obtenerReservasPorCancha(numeroCancha);
        vista.mostrarReservas(reservas);
    }
    
    private void cancelarReserva() {
        int id = vista.solicitarIdReserva();
        if (sistema.cancelarReserva(id)) {
            vista.mostrarMensaje("Reserva cancelada exitosamente.");
        } else {
            vista.mostrarMensaje("No se encontró la reserva con ID: " + id);
        }
    }
    
    private void mostrarListaEspera() {
        vista.mostrarReservas(sistema.getListaEspera());
    }
    
    private void mostrarEstadisticas() {
        String estadisticas = sistema.mostrarEstadisticas();
        vista.mostrarEstadisticas(estadisticas);
    }
    
    private boolean verificarAccesoOrganizador() {
        if (sistema.getUsuarioActual() == null) {
            vista.mostrarMensaje("Debe iniciar sesión primero.");
            return false;
        }
        if (!sistema.getUsuarioActual().esOrganizador()) {
            vista.mostrarMensaje("Acceso denegado. Solo organizadores pueden realizar esta acción.");
            return false;
        }
        return true;
    }
    
    private boolean verificarAccesoEmpleado() {
        if (sistema.getUsuarioActual() == null) {
            vista.mostrarMensaje("Debe iniciar sesión primero.");
            return false;
        }
        if (!sistema.getUsuarioActual().esEmpleado()) {
            vista.mostrarMensaje("Acceso denegado. Solo empleados pueden realizar esta acción.");
            return false;
        }
        return true;
    }
}
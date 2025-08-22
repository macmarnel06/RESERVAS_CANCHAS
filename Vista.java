import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Vista {
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;
    
    public VistaReservas() {
        scanner = new Scanner(System.in);
        dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
    
    public int mostrarMenu() {
        System.out.println("\n=== SISTEMA DE RESERVAS DE CANCHAS ===");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrar nuevo usuario");
        System.out.println("3. Registrar nueva cancha");
        System.out.println("4. Solicitar reserva");
        System.out.println("5. Mostrar reservas por cancha");
        System.out.println("6. Cancelar reserva");
        System.out.println("7. Mostrar lista de espera");
        System.out.println("8. Mostrar estadísticas");
        System.out.println("9. Cerrar sesión");
        System.out.println("10. Salir");
        System.out.print("Seleccione una opción: ");
        
        return scanner.nextInt();
    }
    
    public String solicitarEmail() {
        scanner.nextLine();
        System.out.print("Ingrese su email: ");
        return scanner.nextLine();
    }
    
    public Usuario solicitarDatosUsuario() {
        scanner.nextLine();
        System.out.println("\n--- Registro de Nuevo Usuario ---");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Establecer tipo (organizador/empleado): ");
        String estab_tipo = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        
        return new Usuario(nombre, estab_tipo, email, telefono);
    }
    
    public Cancha solicitarDatosCancha() {
        System.out.println("\n--- Registrar Nueva Cancha ---");
        System.out.print("Número: ");
        int numero = scanner.nextInt();
        
        scanner.nextLine();
        System.out.print("Tipo (Fútbol/Baloncesto/Tenis): ");
        String tipo = scanner.nextLine();
        
        System.out.print("Capacidad: ");
        int capacidad = scanner.nextInt();
        
        System.out.print("Costo por hora: ");
        double costo = scanner.nextDouble();
        
        return new Cancha(numero, tipo, capacidad, costo);
    }
    
    public Reserva solicitarDatosReserva() {
        System.out.println("\n--- Solicitar Reserva ---");
        scanner.nextLine();
        
        System.out.print("Responsable: ");
        String responsable = scanner.nextLine();
        
        System.out.print("Nombre del evento: ");
        String nombreEvento = scanner.nextLine();
        
        System.out.print("Tipo de evento (entrenamiento/campeonato/amistoso): ");
        String tipoEvento = scanner.nextLine();
        
        System.out.print("Fecha y hora de inicio (yyyy-MM-dd HH:mm): ");
        String inicioStr = scanner.nextLine();
        LocalDateTime inicio = LocalDateTime.parse(inicioStr, dateFormatter);
        
        System.out.print("Fecha y hora de fin (yyyy-MM-dd HH:mm): ");
        String finStr = scanner.nextLine();
        LocalDateTime fin = LocalDateTime.parse(finStr, dateFormatter);
        
        System.out.print("¿Depósito pagado? (si/no): ");
        boolean depositoPagado = scanner.nextLine().equalsIgnoreCase("si");
        
        return new Reserva(responsable, nombreEvento, tipoEvento, inicio, fin, depositoPagado);
    }
    
    public int solicitarNumeroCancha() {
        System.out.print("Número de cancha: ");
        return scanner.nextInt();
    }
    
    public int solicitarIdReserva() {
        System.out.print("ID de reserva a cancelar: ");
        return scanner.nextInt();
    }
    
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    public void mostrarReservas(List<Reserva> reservas) {
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas para mostrar.");
            return;
        }
        
        System.out.println("\n--- Reservas ---");
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }
    
    public void mostrarEstadisticas(String estadisticas) {
        System.out.println("\n--- Estadísticas ---");
        System.out.println(estab_tipo);
    }
    
    public void mostrarUsuarioActual(Usuario usuario) {
        if (usuario != null) {
            System.out.println("\nUsuario actual: " + usuario.getNombre() + " (" + usuario.getEstabTipo() + ")");
        }
    }
}
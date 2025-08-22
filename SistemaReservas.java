import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SistemaReservas {
    private ArrayList<Cancha> canchas;
    private ArrayList<Reserva> reservas;
    private ArrayList<Reserva> listaEspera;
    private ArrayList<Usuario> usuarios;
    private int nextReservaId;
    private Usuario usuarioActual;
    
    public SistemaReservas() {
        canchas = new ArrayList<>();
        reservas = new ArrayList<>();
        listaEspera = new ArrayList<>();
        usuarios = new ArrayList<>();
        nextReservaId = 1;
        usuarioActual = null;
        
        usuarios.add(new Usuario("Admin Principal", "empleado", "admin@complejo.com", "123-456-7890"));
        usuarios.add(new Usuario("Carlos Organizador", "organizador", "carlos@eventos.com", "555-1234"));
    }
    
    public void agregarCancha(Cancha cancha) {
        canchas.add(cancha);
    }
    
    public int solicitarReserva(Reserva reserva) {
        reserva.setId(nextReservaId++);
        
        if (!reserva.cumpleReglas()) {
            return -1;
        }
        
        reservas.add(reserva);
        asignarCanchas();
        
        return reserva.getId();
    }
    
    public void asignarCanchas() {
        for (Reserva reserva : reservas) {
            if (!reserva.isAsignada()) {
                for (Cancha cancha : canchas) {
                    if (cancha.getTipo().equals(reserva.getTipoEvento()) && 
                        cancha.estaDisponible(reserva.getFechaHoraInicio(), reserva.getFechaHoraFin())) {
                        reserva.asignarCancha(cancha);
                        cancha.agregarReserva(reserva);
                        break;
                    }
                }
                
                if (!reserva.isAsignada()) {
                    listaEspera.add(reserva);
                }
            }
        }
        
        listaEspera.removeIf(Reserva::isAsignada);
    }
    
    public boolean cancelarReserva(int id) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == id) {
                reserva.cancelar();
                reservas.remove(reserva);
                listaEspera.remove(reserva);
                asignarCanchas();
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<Reserva> obtenerReservasPorCancha(int numeroCancha) {
        for (Cancha cancha : canchas) {
            if (cancha.getNumero() == numeroCancha) {
                return cancha.getReservas();
            }
        }
        return new ArrayList<>();
    }
    
    public String mostrarEstadisticas() {
        StringBuilder estadisticas = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        Map<String, Integer> eventosPorMes = new HashMap<>();
        Map<Integer, Integer> demandaCanchas = new HashMap<>();
        
        for (Cancha cancha : canchas) {
            demandaCanchas.put(cancha.getNumero(), 0);
        }
        
        for (Reserva reserva : reservas) {
            if (reserva.isAsignada()) {
                String mes = reserva.getFechaHoraInicio().format(formatter);
                eventosPorMes.put(mes, eventosPorMes.getOrDefault(mes, 0) + 1);
                
                int canchaNum = reserva.getCancha().getNumero();
                demandaCanchas.put(canchaNum, demandaCanchas.get(canchaNum) + 1);
            }
        }
        
        int maxDemanda = 0;
        int canchaMasDemandada = 0;
        for (Map.Entry<Integer, Integer> entry : demandaCanchas.entrySet()) {
            if (entry.getValue() > maxDemanda) {
                maxDemanda = entry.getValue();
                canchaMasDemandada = entry.getKey();
            }
        }
        
        estadisticas.append("Estadísticas Mensuales:\n");
        for (Map.Entry<String, Integer> entry : eventosPorMes.entrySet()) {
            estadisticas.append("Mes: ").append(entry.getKey())
                       .append(", Eventos: ").append(entry.getValue()).append("\n");
        }
        
        estadisticas.append("\nCancha con mayor demanda: #").append(canchaMasDemandada)
                   .append(" con ").append(maxDemanda).append(" reservas.\n");
        
        return estadisticas.toString();
    }
    
    public Usuario autenticarUsuario(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                usuarioActual = usuario;
                return usuario;
            }
        }
        return null;
    }
    
    public void cerrarSesion() {
        usuarioActual = null;
    }
    
    public boolean registrarUsuario(Usuario usuario) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(usuario.getEmail())) {
                return false;
            }
        }
        usuarios.add(usuario);
        return true;
    }
    
    public ArrayList<Cancha> getCanchas() { return canchas; }
    public Usuario getUsuarioActual() { return usuarioActual; }
    public ArrayList<Reserva> getListaEspera() { return listaEspera; }
}
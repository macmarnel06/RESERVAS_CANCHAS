import java.time.LocalDateTime;
import java.util.ArrayList;

public class Cancha {
    private int numero;
    private String tipo;
    private int capacidad;
    private double costoPorHora;
    private ArrayList<Reserva> reservas;
    
    public Cancha(int numero, String tipo, int capacidad, double costoPorHora) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.costoPorHora = costoPorHora;
        this.reservas = new ArrayList<>();
    }
    
    public boolean estaDisponible(LocalDateTime inicio, LocalDateTime fin) {
        for (Reserva reserva : reservas) {
            if (reservasSolapan(reserva.getFechaHoraInicio(), reserva.getFechaHoraFin(), inicio, fin)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean reservasSolapan(LocalDateTime inicio1, LocalDateTime fin1, 
                                   LocalDateTime inicio2, LocalDateTime fin2) {
        return inicio1.isBefore(fin2) && inicio2.isBefore(fin1);
    }
    
    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }
    
    public void cancelarReserva(Reserva reserva) {
        reservas.remove(reserva);
    }
    
    public ArrayList<Reserva> getReservas() { return reservas; }
    public int getNumero() { return numero; }
    public String getTipo() { return tipo; }
    public int getCapacidad() { return capacidad; }
    public double getCostoPorHora() { return costoPorHora; }
    
    @Override
    public String toString() {
        return tipo + " #" + numero + " (Capacidad: " + capacidad + ", Costo: $" + costoPorHora + "/hora)";
    }
}
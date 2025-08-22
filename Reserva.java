import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reserva {
    private int id;
    private String responsable;
    private String nombreEvento;
    private String tipoEvento;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private boolean depositoPagado;
    private boolean asignada;
    private Cancha cancha;
    
    public Reserva(String responsable, String nombreEvento, String tipoEvento, 
                  LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, boolean depositoPagado) {
        this.responsable = responsable;
        this.nombreEvento = nombreEvento;
        this.tipoEvento = tipoEvento;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.depositoPagado = depositoPagado;
        this.asignada = false;
    }
    
    public boolean cumpleReglas() {
        LocalTime inicio = fechaHoraInicio.toLocalTime();
        LocalTime fin = fechaHoraFin.toLocalTime();
        LocalTime horarioApertura = LocalTime.of(8, 0);
        LocalTime horarioCierre = LocalTime.of(22, 0);
        
        if (inicio.isBefore(horarioApertura) || fin.isAfter(horarioCierre)) {
            return false;
        }
        
        if (!depositoPagado) {
            return false;
        }
        
        long horas = java.time.Duration.between(fechaHoraInicio, fechaHoraFin).toHours();
        if (horas < 1) {
            return false;
        }
        
        return true;
    }
    
    public void asignarCancha(Cancha cancha) {
        this.cancha = cancha;
        this.asignada = true;
    }
    
    public void cancelar() {
        if (asignada && cancha != null) {
            cancha.cancelarReserva(this);
        }
        asignada = false;
        cancha = null;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getResponsable() { return responsable; }
    public String getNombreEvento() { return nombreEvento; }
    public String getTipoEvento() { return tipoEvento; }
    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }
    public boolean isDepositoPagado() { return depositoPagado; }
    public boolean isAsignada() { return asignada; }
    public Cancha getCancha() { return cancha; }
    
    @Override
    public String toString() {
        String estado = asignada ? "Asignada a cancha #" + cancha.getNumero() : "En lista de espera";
        return "Reserva #" + id + ": " + nombreEvento + " (" + tipoEvento + ") - " + 
               fechaHoraInicio + " a " + fechaHoraFin + " - " + estado;
    }
}
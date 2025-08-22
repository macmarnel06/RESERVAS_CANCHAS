public class Main {
    public static void main(String[] args) {
        SistemaReservas sistema = new SistemaReservas();
        VistaReservas vista = new VistaReservas();
        ControladorReservas controlador = new ControladorReservas(sistema, vista);
        
        sistema.agregarCancha(new Cancha(1, "Fútbol", 22, 50.0));
        sistema.agregarCancha(new Cancha(2, "Baloncesto", 10, 30.0));
        sistema.agregarCancha(new Cancha(3, "Tenis", 4, 40.0));
        sistema.agregarCancha(new Cancha(4, "Fútbol", 16, 45.0));
        
        controlador.iniciar();
    }
}
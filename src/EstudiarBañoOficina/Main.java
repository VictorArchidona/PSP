package EstudiarBañoOficina;

public class Main {

    public static void main(String[] args) {
        BañoCompartido baño = new BañoCompartido();

        // Crear varios empleados (hilos) que intentarán usar el baño
        Thread empleado1 = new Thread(new Empleado("Empleado 1", baño));
        Thread empleado2 = new Thread(new Empleado("Empleado 2", baño));
        Thread empleado3 = new Thread(new Empleado("Empleado 3", baño));
        Thread empleado4 = new Thread(new Empleado("Empleado 4", baño));

        // Iniciar los hilos
        empleado1.start();
        empleado2.start();
        empleado3.start();
        empleado4.start();
    }
}

public class Main {
    public static void main(String[] args) {
        // 1. Crear los datos compartidos
        SharedData datos = new SharedData();

        // 2. Crear el sistema de control (Monitor/Semáforos)
        SistemaControl sistema = new SistemaControl(datos);

        System.out.println("--- INICIO DEL SISTEMA DE CONTROL AÉREO ---");

        // 3. Crear y lanzar los CONTROLADORES (Consumidores)
        // Ponemos 2 controladores (Torres de control)
        Thread c1 = new Thread(new Controladores("Torre-A", sistema));
        Thread c2 = new Thread(new Controladores("Torre-B", sistema));
        c1.start();
        c2.start();

        // 4. Crear y lanzar las AERONAVES (Productores)
        // Creamos 4 aviones distintos
        Thread a1 = new Thread(new Aeronaves("IBERIA", sistema));
        Thread a2 = new Thread(new Aeronaves("RYANAIR", sistema));
        Thread a3 = new Thread(new Aeronaves("VUELING", sistema));
        Thread a4 = new Thread(new Aeronaves("LUFTHANSA", sistema));

        a1.start();
        a2.start();
        a3.start();
        a4.start();
    }
}
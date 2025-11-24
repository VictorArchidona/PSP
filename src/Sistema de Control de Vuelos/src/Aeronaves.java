import java.util.Random;

public class Aeronaves implements Runnable {

    private String idAeronave;
    private SistemaControl sistemaControl;
    private Random random = new Random();

    public Aeronaves(String idAeronave, SistemaControl sistemaControl) {
        this.idAeronave = idAeronave;
        this.sistemaControl = sistemaControl;
    }

    // (Getters y Setters igual que tenías...)

    @Override
    public void run() {
        try {
            // Simulamos que esta aeronave genera por ejemplo 3 solicitudes
            for (int i = 1; i <= 3; i++) {

                // 1. Simular tiempo de creación (requisito del examen)
                simular_creacion_solicitud();

                // 2. Crear el nombre único del vuelo
                String solicitud = "Vuelo-" + idAeronave + "-" + i;

                // 3. Añadir a la pila
                sistemaControl.añadir_solicitud(solicitud);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Método auxiliar que pedía el enunciado
    private void simular_creacion_solicitud() throws InterruptedException {
        // Tarda entre 0.5 y 2 segundos en pedir pista
        Thread.sleep(500 + random.nextInt(1500));
    }
}
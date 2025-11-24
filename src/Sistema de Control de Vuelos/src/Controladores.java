public class Controladores implements Runnable { // <-- ¡IMPORTANTE! implements Runnable

    private String idConsumidor;
    private SistemaControl sistemaControl; // <-- Necesita acceso al sistema

    // Modificamos el constructor para recibir el sistema
    public Controladores(String idConsumidor, SistemaControl sistemaControl) {
        this.idConsumidor = idConsumidor;
        this.sistemaControl = sistemaControl;
    }

    @Override
    public void run() {
        try {
            // El controlador trabaja indefinidamente (o un bucle largo)
            while (true) {
                // 1. Gestionar (Esperar semáforos + Aterrizar)
                sistemaControl.gestionar_aterrizaje(idConsumidor);

                // 2. Finalizar (Liberar Pista y Combustible R2/R3)
                // ¡Esto es vital! Si no lo llamas, los semáforos nunca se liberan
                sistemaControl.finalizar_proceso(idConsumidor);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Getters y Setters...

    public String getIdConsumidor() {
        return idConsumidor;
    }

    public void setIdConsumidor(String idConsumidor) {
        this.idConsumidor = idConsumidor;
    }
}
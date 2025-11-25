import java.util.Random;

// IMPORTANTE: Debe implementar Runnable para ser un hilo
public class CamionesProductores implements Runnable {

    private String idCamion;
    private Almacen almacen;

    public CamionesProductores(String idCamion, Almacen almacen) {
        this.idCamion = idCamion;
        this.almacen = almacen;
    }

    @Override
    public void run() {
        try {
            // Simulamos que el camión trae 10 cajas
            for (int i = 1; i <= 10; i++) {

                // Simula tiempo de viaje (rápido)
                Thread.sleep(500);

                // Entrega la caja (el número 'i' sirve como ID de caja)
                almacen.entregarCaja(i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
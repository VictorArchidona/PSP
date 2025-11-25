// IMPORTANTE: Debe implementar Runnable
public class OperariosConsumidores implements Runnable {

    private String idOperario;
    private Almacen almacen;

    public OperariosConsumidores(String idOperario, Almacen almacen) {
        this.idOperario = idOperario;
        this.almacen = almacen;
    }

    @Override
    public void run() {
        try {
            // El operario intenta recoger 10 cajas
            for (int i = 1; i <= 10; i++) {

                // El operario es más lento (1000ms), así llenaremos el almacén
                Thread.sleep(1000);

                almacen.recogerCaja();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
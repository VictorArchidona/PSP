package Examen;

/**
 * Gestiona el elixir del jugador con generación automática en segundo plano.
 * El elixir se genera automáticamente cada 2 segundos hasta un máximo de 10.
 */
public class Elixir implements Runnable {

    //Inicializa el maximo de elixir a 10
    private static final int MAX_ELIXIR = 10;

    //Intervalo de generación de elixir en milisegundos
    private static final int INTERVALO_GENERACION_MS = 2000;

    //Cantidad actual de elixir
    private int cantidad;

    //Indica si el generador está activo
    private volatile boolean activo;

    // Hilo que genera elixir en segundo plano
    private Thread hiloGenerador;

    /**
     * Constructor de la clase Elixir, inicializa la cantidad a 0 y el generador como inactivo.
     */
    public Elixir() {
        this.cantidad = 0;
        this.activo = false;
    }

    /**
     * Inicia la generación automática de elixir en segundo plano.
     */
    public void iniciar() {
        if (!activo) {
            activo = true;
            hiloGenerador = new Thread(this, "GeneradorElixir");
            hiloGenerador.setDaemon(true);
            hiloGenerador.start();
        }
    }

    /**
     * Detiene la generación automática de elixir.
     */
    public void stop() {
        activo = false;
        if (hiloGenerador != null) {
            hiloGenerador.interrupt();
        }
    }

    /**
     * Metodo run del hilo que genera elixir cada 2 segundos hasta el maximo que es 10
     */
    @Override
    public void run() {
        while (activo) {
            try {
                Thread.sleep(INTERVALO_GENERACION_MS);
                // NO se imprime nada por consola según el enunciado
                synchronized (this) {
                    if (cantidad < MAX_ELIXIR) {
                        cantidad++;
                    }
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * Obtiene la cantidad actual de elixir.
     */
    public synchronized int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de elixir (usado para consumir).
     */
    public synchronized void setCantidad(int cantidad) {
        if (cantidad < 0) {
            this.cantidad = 0;
        } else if (cantidad > MAX_ELIXIR) {
            this.cantidad = MAX_ELIXIR;
        } else {
            this.cantidad = cantidad;
        }
    }
}

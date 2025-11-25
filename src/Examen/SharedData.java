package Examen;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * SharedData usando Semaphores para sincronización entre hilos.
 */
public class SharedData {

    // Mazo de cartas como deque para acceso LIFO
    private final Deque<Carta> mazo;

    //Objeto de la clase Elixir
    private final Elixir elixir;

    //Semaforos para la sincronización
    private final Semaphore deckMutex = new Semaphore(1); // Mutex para el mazo
    private final Semaphore elixirMutex = new Semaphore(1); // Mutex para el elixir
    private final Semaphore cardsAvailable = new Semaphore(0); // Cartas disponibles para robar

    /**
     * Constructor de SharedData
     * @param elixir objeto Elixir compartido
     * @param tableroInicial lista inicial de cartas para el mazo
     */
    public SharedData(Elixir elixir, List<Carta> tableroInicial) {
        this.elixir = Objects.requireNonNull(elixir, "Elixir no puede ser null");
        this.mazo = new ArrayDeque<>();
        if (tableroInicial != null) {
            for (Carta c : tableroInicial) {
                this.mazo.addLast(c);
            }
            cardsAvailable.release(this.mazo.size());
        }
    }

    /**
     * Roba la carta superior de forma no bloqueante.
     * Devuelve null si no hay cartas.
     */
    public Carta robar() throws InterruptedException {
        if (!cardsAvailable.tryAcquire()) {
            return null;
        }
        deckMutex.acquire();
        try {
            return mazo.pollFirst();
        } finally {
            deckMutex.release();
        }
    }

    /**
     * Roba la carta superior de forma bloqueante con timeout.
     * Devuelve null si no hay cartas en el tiempo especificado.
     * @param timeout tiempo máximo a esperar
     * @param unit unidad de tiempo
     */
    public Carta robarBloqueante(long timeout, TimeUnit unit) throws InterruptedException {
        if (!cardsAvailable.tryAcquire(timeout, unit)) {
            return null;
        }
        deckMutex.acquire();
        try {
            return mazo.pollFirst();
        } finally {
            deckMutex.release();
        }
    }

    /**
     * Añade una carta al fondo del mazo
     * @param carta
     */
    public void añadirAlFondo(Carta carta) {
        if (carta == null) return;
        deckMutex.acquireUninterruptibly();
        try {
            mazo.addLast(carta);
            cardsAvailable.release();
        } finally {
            deckMutex.release();
        }
    }

    /**
     * Intenta consumir el elixir de forma atómica.
     */
    public boolean intentarConsumirElixir(int coste) {
        if (coste < 0) return false;
        elixirMutex.acquireUninterruptibly();
        try {
            int actual = elixir.getCantidad();
            if (actual >= coste) {
                elixir.setCantidad(actual - coste);
                return true;
            }
            return false;
        } finally {
            elixirMutex.release();
        }
    }

    /**
     * Consulta atómica de la cantidad actual de elixir.
     */
    public int getElixir() {
        elixirMutex.acquireUninterruptibly();
        try {
            return elixir.getCantidad();
        } finally {
            elixirMutex.release();
        }
    }

    /**
     * Consulta el tamaño actual del mazo
     * @return
     */
    public int tamañoMazo() {
        deckMutex.acquireUninterruptibly();
        try {
            return mazo.size();
        } finally {
            deckMutex.release();
        }
    }

    /**
     * Consulta si el mazo esta vacio
     * @return
     */
    public boolean mazoVacio() {
        deckMutex.acquireUninterruptibly();
        try {
            return mazo.isEmpty();
        } finally {
            deckMutex.release();
        }
    }

    /**
     * Inicia la generacion de elixir
     */
    public void detenerElixir() {
        elixir.stop();
    }
}

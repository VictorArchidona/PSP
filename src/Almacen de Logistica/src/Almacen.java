import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Almacen {

    // Recurso compartido
    private ArrayList<Integer> pila = new ArrayList<>();

    // SEMÁFOROS
    private Semaphore mutex;
    private Semaphore stock;
    private Semaphore capacidad;

    public Almacen() {
        // --- CORRECCIÓN CRÍTICA ---
        // El Mutex debe empezar en 1 (La llave está libre al principio)
        this.mutex = new Semaphore(1);

        // Empieza con 0 cajas
        this.stock = new Semaphore(0);

        // Empieza con 5 huecos libres
        this.capacidad = new Semaphore(5);
    }

    // MÉTODO DEL PRODUCTOR (CAMIÓN)
    public void entregarCaja(int idCaja) {
        try {
            // 1. Esperar a que haya hueco (Si hay 5 cajas, espera aquí)
            capacidad.acquire();

            // 2. Entrar en zona crítica (coger la llave del baño)
            mutex.acquire();

            // --- SECCIÓN CRÍTICA ---
            pila.add(idCaja);
            System.out.println("➕ CAMIÓN: Entregó caja " + idCaja + "  \t[Stock: " + pila.size() + "]");
            // -----------------------

            // 3. Salir de zona crítica (devolver llave)
            mutex.release();

            // 4. Avisar al Operario que hay una caja nueva disponible
            stock.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // MÉTODO DEL CONSUMIDOR (OPERARIO)
    public void recogerCaja() {
        try {
            // 1. Esperar a que haya stock (Si hay 0 cajas, espera aquí)
            stock.acquire();

            // 2. Entrar en zona crítica
            mutex.acquire();

            // --- SECCIÓN CRÍTICA ---
            // LIFO: Calculamos el índice del último y lo sacamos
            int indiceDelUltimo = pila.size() - 1;
            int cajaSacada = pila.remove(indiceDelUltimo);

            System.out.println("➖ OPERARIO: Se llevó caja " + cajaSacada + " \t[Stock: " + pila.size() + "]");
            // -----------------------

            // 3. Salir de zona crítica
            mutex.release();

            // 4. Avisar al Camión que se ha liberado un hueco
            capacidad.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
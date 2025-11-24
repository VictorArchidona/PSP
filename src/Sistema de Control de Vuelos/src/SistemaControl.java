import java.util.concurrent.Semaphore;

public class SistemaControl {

    private SharedData sharedData;

    // SEMÁFOROS (Idealmente deberían estar en SharedData, pero los dejo aquí para que veas la lógica)

    // R1: Controla que haya items en la pila (Consumidor espera si es 0)
    private Semaphore sem_hay_solicitudes;

    // R2: Controla max 3 aviones críticos a la vez
    private Semaphore sem_combustible;

    // R3: Exclusión mutua de la pista (solo 1)
    private Semaphore sem_pista;

    // Controla que la pila no pase de 8 (Productor espera si es 0)
    private Semaphore sem_espacio_pila;

    // Mutex para proteger el ArrayList
    private Semaphore mutex;

    public SistemaControl(SharedData sharedData) {
        this.sharedData = sharedData;
        // Inicialización correcta según lógica
        this.sem_hay_solicitudes = new Semaphore(0); // Empieza en 0 (pila vacía)
        this.sem_combustible = new Semaphore(3);     // Max 3 críticos
        this.sem_pista = new Semaphore(1);           // Solo 1 pista
        this.sem_espacio_pila = new Semaphore(8);    // Max 8 huecos
        this.mutex = new Semaphore(1);               // Mutex binario
    }

    // --------------------------------------------------------------
    // MÉTODO DEL PRODUCTOR (AERONAVE)
    // --------------------------------------------------------------
    public void añadir_solicitud(String idVuelo) throws InterruptedException {
        // 1. Esperar si la pila está llena (Max 8)
        sem_espacio_pila.acquire();

        // 2. Entrar en exclusión mutua para tocar la lista
        mutex.acquire();

        // CRÍTICO: Añadir a la lista
        sharedData.getPila().add(idVuelo);
        System.out.println("Aeronave " + idVuelo + " añadida a la cola de espera.");

        // 3. Salir de exclusión mutua
        mutex.release();

        // 4. AVISAR al Consumidor que hay una solicitud nueva (R1)
        // Esto despertará al controlador si estaba esperando
        sem_hay_solicitudes.release();
    }

    // --------------------------------------------------------------
    // MÉTODO DEL CONSUMIDOR (CONTROLADOR)
    // --------------------------------------------------------------
    public void gestionar_aterrizaje(String idControlador) throws InterruptedException {

        // --- FASE 1: PEDIR TODOS LOS PERMISOS (R1, R2, R3) ---

        // R1: ¿Hay cartas en la pila? (Si es 0, espera)
        sem_hay_solicitudes.acquire();

        // R2: ¿Hay hueco de combustible crítico? (Si hay 3 ocupados, espera)
        sem_combustible.acquire();

        // R3: ¿Está la pista libre? (Si hay alguien aterrizando, espera)
        sem_pista.acquire();

        // --- FASE 2: PROCESAR (YA TIENE TODOS LOS PERMISOS) ---

        String vueloAterrizando = "";

        // Entramos a la pila protegida para sacar el papel (LIFO)
        mutex.acquire();
        if (!sharedData.getPila().isEmpty()) {
            // LIFO: Sacamos el ÚLTIMO elemento
            int ultimoIndex = sharedData.getPila().size() - 1;
            vueloAterrizando = sharedData.getPila().remove(ultimoIndex);
        }
        mutex.release();

        // Dejamos un hueco libre en la capacidad de la pila (para que el Productor pueda meter más)
        sem_espacio_pila.release();

        // Simular el aterrizaje (AQUÍ es donde se tiene la pista ocupada)
        System.out.println(">> Controlador " + idControlador + " ATERRIZANDO vuelo: " + vueloAterrizando);
        Thread.sleep(2000); // Tiempo en pista
    }

    // --------------------------------------------------------------
    // MÉTODO PARA FINALIZAR (LIBERAR RECURSOS)
    // --------------------------------------------------------------
    public void finalizar_proceso(String idControlador) {
        // El avión ya salió de la pista y ya terminó su emergencia
        System.out.println("<< Controlador " + idControlador + " finalizó gestión. Pista LIBRE.");

        // Liberar Pista (R3) -> Otro controlador puede entrar
        sem_pista.release();

        // Liberar Combustible (R2) -> Otro avión crítico puede empezar a gestionarse
        sem_combustible.release();
    }
}
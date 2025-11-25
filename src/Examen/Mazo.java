package Examen;

import Examen.Carta;

import java.util.concurrent.Semaphore;

/**
 * Mazo como estructura de pila (LIFO) con soporte para concurrencia.
 */
public class Mazo {

    // Primer nodo de la pila
    private Node head;

    // tamaño de la pila
    private int tamaño;

    // capacidad de la pila
    private int capacidad;

    // Semáforos para la concurrencia
    private final Semaphore mutex;                // exclusión mutua
    private final Semaphore espaciosDisponibles;  // espacios libres
    private final Semaphore elementosDisponibles; // elementos disponibles

    public Mazo() {
        this.head = null;
        this.tamaño = 0;
        this.capacidad = 8;

        this.mutex = new Semaphore(1);
        this.espaciosDisponibles = new Semaphore(capacidad);
        this.elementosDisponibles = new Semaphore(0);
    }

    /**
     * Obtiene el nodo cabeza de la pila.
     * @return
     */
    public Node getHead() {
        return head;
    }

    /**
     * Establece el nodo cabeza de la pila
     * @param head
     */
    public void setHead(Node head) {
        this.head = head;
    }

    /**
     * Obtiene el tamaño completo del mazo
     * @return
     */
    public int getSize() {
        return tamaño;
    }

    /**
     * Establece el tamaño del mazo
     * @param size
     */
    public void setSize(int size) {
        this.tamaño = size;
    }

    /**
     * Comprueba si la pila esta llena de cartas
     * @return
     */
    private boolean estaLlena() {
        return tamaño == capacidad;
    }

    /**
     * Comprueba si la pila esta vacia de cartas
     * @return
     */
    private boolean estaVacia() {
        return tamaño == 0;
    }

    /**
     * Inserta una carta en la cima de la pila (push).
     * @return true si se insertó, false si la pila está llena
     */
    private boolean insertar(Carta carta) {
        if (estaLlena()) {
            return false;
        }
        Node nuevoNodo = new Node(carta);
        nuevoNodo.next = head;
        head = nuevoNodo;
        tamaño++;
        return true;
    }

    /**
     * Extrae la carta del tope de la pila (pop).
     * @return la carta extraída o null si está vacía
     */
    private Carta extraer() {
        if (estaVacia()) {
            return null;
        }
        Carta carta = head.carta;
        head = head.next;
        tamaño--;
        return carta;
    }

    /**
     * Inserción con concurrencia: espera espacio disponible y protege la sección crítica.
     */
    public void insertarConcurrencia(Carta carta) throws InterruptedException {
        espaciosDisponibles.acquire(); // esperar espacio
        mutex.acquire();
        try {
            insertar(carta);
        } finally {
            mutex.release();
            elementosDisponibles.release(); // indicar nuevo elemento
        }
    }

    /**
     * Extracción con concurrencia: espera elemento disponible y protege la sección crítica.
     * @return la carta extraída
     */
    public Carta extraerConcurrencia() throws InterruptedException {
        elementosDisponibles.acquire(); // esperar elemento
        mutex.acquire();
        try {
            return extraer();
        } finally {
            mutex.release();
            espaciosDisponibles.release(); // indicar espacio libre
        }
    }

    /**
     * Devuelve la carta del tope sin extraerla, o null si está vacía.
     */
    private Carta frente() {
        if (estaVacia()) {
            return null;
        }
        return head.carta;
    }

    // Nodo interno para la pila
    private static class Node {
        private final Carta carta;
        private Node next;

        Node(Carta carta) {
            this.carta = carta;
            this.next = null;
        }
    }
}

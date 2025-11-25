package Examen;

/**
 * Esta clase representa los nodos de una pila enlazada simple
 * @author Victor
 */
public class Node {

    //Contenido del nodo
    private Carta carta;

    //Referencia al siguiente nodo en la lista
    private Node next;

    /**
     * Constructor por defecto que crea un nodo vacío.
     */
    public Node() {}

    /**
     * Constructor que crea un nodo con el contenido especificado.
     *
     * @param carta Valor entero a almacenar en el nodo
     */
    public Node(Carta carta) {
        this.carta = carta;
        this.next = null;
    }

    /**
     * Obtiene el contenido almacenado en el nodo.
     *
     * @return Valor entero del contenido del nodo
     */
    public  Carta getCarta() {
        return carta;
    }

    /**
     * Establece el contenido del nodo.
     *
     * @param carta Nuevo valor entero a almacenar
     */
    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    /**
     * Obtiene la referencia al siguiente nodo.
     *
     * @return Siguiente nodo en la lista, o null si es el último
     */
    public Node getNext() {
        return next;
    }

    /**
     * Establece la referencia al siguiente nodo.
     *
     * @param next Nodo que seguirá a este en la lista
     */
    public void setNext(Node next) {
        this.next = next;
    }

    /**
     * Devuelve una representación en cadena del nodo.
     *
     * @return Cadena con formato "Content = [valor]"
     */
    @Override
    public String toString() {

        return "Content = " + this.carta;
    }
}

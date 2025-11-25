package Examen;

/**
 * Representa una carta del juego con nombre y coste en elixir.
 */
public class Carta {
    private final String nombre;
    private final int coste;

    /**
     * Constructor de la carta.
     * @param nombre Nombre del personaje
     * @param coste Coste en elixir (entre 1 y 9)
     */
    public Carta(String nombre, int coste) {
        this.nombre = nombre;
        this.coste = coste;
    }

    /**
     * Obtiene el nombre de la carta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el coste en elixir de la carta.
     */
    public int getCoste() {
        return coste;
    }

    /**
     * Representación en texto de la carta (solo el nombre).
     */
    @Override
    public String toString() {
        return nombre;
    }
}

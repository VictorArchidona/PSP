package Examen;

import java.util.List;
import java.util.Objects;

/**
 * Mano del jugador implementada con un array de 4 elementos.
 * El array siempre contiene 4 cartas visibles salvo que el mazo se quede vacío.
 */
public class Mano_del_Jugador {

    // Tamaño fijo de la mano
    private static final int TAMANO = 4;

    // Array que contiene las cartas de la mano
    private final Carta[] mano = new Carta[TAMANO];

    /**
     * Constructor que inicializa la mano con cartas del mazo.
     * @param mazo
     */
    public Mano_del_Jugador(List<Carta> mazo) {
        Objects.requireNonNull(mazo, "El mazo no puede ser null");
        rellenarDesdeMazo(mazo);
    }

    /**
     * Rellena la mano desde el mazo hasta tener 4 cartas o hasta que el mazo se quede vacío.
     * @param mazo
     */
    private void rellenarDesdeMazo(List<Carta> mazo) {
        for (int i = 0; i < TAMANO && !mazo.isEmpty(); i++) {
            if (mano[i] == null) {
                mano[i] = mazo.remove(0);
            }
        }
    }

    /**
     * Juega la carta en la posición indicada (0..3).
     * La carta se elimina de la mano y se repone desde el mazo si hay cartas.
     * @param indice posición 0..3
     * @param mazo lista que actúa como mazo (se quitan cartas del inicio)
     * @return la carta jugada
     * @throws IndexOutOfBoundsException si indice fuera de 0..3
     * @throws IllegalStateException si no hay carta en esa posición
     */
    public Carta jugar(int indice, List<Carta> mazo) {
        if (indice < 0 || indice >= TAMANO) {
            throw new IndexOutOfBoundsException("Índice debe estar entre 0 y 3");
        }
        if (mano[indice] == null) {
            throw new IllegalStateException("No hay carta en la posición " + indice);
        }
        Carta jugada = mano[indice];
        mano[indice] = null;
        rellenarDesdeMazo(mazo);
        return jugada;
    }

    /**
     * Obtiene la carta en la posicion indicada (0..3) sin jugarla
     * @param indice
     * @return
     */
    public Carta getCarta(int indice) {
        if (indice < 0 || indice >= TAMANO) {
            throw new IndexOutOfBoundsException("Índice debe estar entre 0 y 3");
        }
        return mano[indice];
    }

    /**
     * Obtiene una copia del array de la mano
     * @return
     */
    public Carta[] getMano() {
        return mano.clone();
    }

    /**
     * Representacion en cadena de la mano
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Mano: [");
        for (int i = 0; i < TAMANO; i++) {
            sb.append(i).append(": ");
            sb.append(mano[i] != null ? mano[i].getClass().getSimpleName() + "(" + (mano[i].toString()) + ")" : "vacío");
            if (i < TAMANO - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

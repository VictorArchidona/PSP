package Examen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fábrica para crear mazos de cartas de Clash Royale.
 */
public class FabricaCartas {

    /**
     * Crea un mazo estándar con personajes de Clash Royale.
     * @return lista de cartas barajadas
     */
    public static List<Carta> crearMazoEstandar() {
        List<Carta> mazo = new ArrayList<>();

        // Cartas baratas (1-3 elixir)
        mazo.add(new Carta("Espíritu de Hielo", 1));
        mazo.add(new Carta("Espíritu de Fuego", 1));
        mazo.add(new Carta("Duendes", 2));
        mazo.add(new Carta("Arqueras", 3));
        mazo.add(new Carta("Caballero", 3));

        // Cartas medias (4-5 elixir)
        mazo.add(new Carta("Mini P.E.K.K.A", 4));
        mazo.add(new Carta("Valquiria", 4));
        mazo.add(new Carta("Mago", 5));
        mazo.add(new Carta("Bruja", 5));
        mazo.add(new Carta("Gigante", 5));

        // Cartas caras (6-8 elixir)
        mazo.add(new Carta("Montapuercos", 4));
        mazo.add(new Carta("P.E.K.K.A", 7));
        mazo.add(new Carta("Gólem", 8));
        mazo.add(new Carta("Megacaballero", 7));
        mazo.add(new Carta("Globo Bombastico", 5));

        // Barajar antes de devolver
        Collections.shuffle(mazo);

        return mazo;
    }

    /**
     * Crea un mazo personalizado con las cartas especificadas.
     * @param nombresYCostes pares de (nombre, coste)
     * @param barajar si se debe barajar el mazo
     * @return lista de cartas
     */
    public static List<Carta> crearMazoPersonalizado(Object[][] nombresYCostes, boolean barajar) {
        List<Carta> mazo = new ArrayList<>();
        for (Object[] datos : nombresYCostes) {
            mazo.add(new Carta((String) datos[0], (Integer) datos[1]));
        }
        if (barajar) {
            Collections.shuffle(mazo);
        }
        return mazo;
    }
}

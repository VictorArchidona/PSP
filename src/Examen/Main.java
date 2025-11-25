package Examen;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

/**
 * Bucle principal del simulador de Clash Royale.
 * Gestiona el flujo de juego: inicialización, visualización, petición de acción y lógica de jugada.
 */
public class Main {

    public static void main(String[] args) {
        // Crear el mazo barajado usando la fábrica
        List<Carta> baraja = FabricaCartas.crearMazoEstandar();

        // Crear el elixir e iniciarlo
        Elixir elixir = new Elixir();
        elixir.iniciar();

        // Crear la mano del jugador (toma las 4 primeras cartas de la baraja)
        Mano_del_Jugador mano = new Mano_del_Jugador(baraja);

        Scanner sc = new Scanner(System.in);

        try {
            juego(baraja, mano, elixir, sc);
        } finally {
            // Detener el generador de elixir y cerrar el scanner
            elixir.stop();
            sc.close();
        }
    }

    /**
     * Bucle principal del juego. Gestiona la interacción con el jugador, visualización y lógica de cada jugada
     * @param baraja
     * @param mano
     * @param elixir
     * @param sc
     */
    private static void juego(List<Carta> baraja, Mano_del_Jugador mano, Elixir elixir, Scanner sc) {
        while (true) {
            // Visualización: mostrar elixir y mano
            System.out.println("\n--- Estado ---");
            System.out.println("Elixir: " + elixir.getCantidad() + "/10");
            for (int i = 0; i < 4; i++) {
                Carta c = mano.getCarta(i);
                if (c != null) {
                    System.out.printf("%d: %s (coste: %s)%n", i, c.toString(), costeDe(c));
                } else {
                    System.out.printf("%d: [vacío]%n", i);
                }
            }

            // Petición de acción
            System.out.print("Introduce índice de la carta a jugar (0-3) o 'q' para salir: ");
            String linea = sc.nextLine().trim();
            if (linea.equalsIgnoreCase("q")) {
                System.out.println("Saliendo del juego...");
                break;
            }

            // Parsear índice
            int indice;
            try {
                indice = Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Introduce un número entre 0 y 3, o 'q' para salir.");
                continue;
            }

            // Validar índice y obtener carta
            if (indice < 0 || indice >= 4) {
                System.out.println("Índice fuera de rango. Elija 0, 1, 2 o 3.");
                continue;
            }

            // Obtener carta en la posición indicada
            Carta carta = mano.getCarta(indice);
            if (carta == null) {
                System.out.println("No hay carta en esa posición.");
                continue;
            }

            // Obtener coste de la carta
            int coste = obtenerCoste(carta);
            int disponible = elixir.getCantidad();

            // Comprobar si hay elixir suficiente
            if (disponible >= coste) {
                // Consumir elixir y jugar
                elixir.setCantidad(disponible - coste);
                try {
                    Carta jugada = mano.jugar(indice, baraja);
                    System.out.println("Carta jugada: " + (jugada != null ? jugada.toString() : "null"));
                } catch (Exception e) {
                    System.out.println("Error al jugar la carta: " + e.getMessage());
                }
            } else {
                System.out.println("Recursos insuficientes. Necesitas " + coste + " elixir y tienes " + disponible);
                System.out.println("Espera a que se genere más elixir...");
            }

            // Comprobar fin de partida: mazo vacío y todas las posiciones de la mano vacías
            boolean todasVacias = true;
            for (int i = 0; i < 4; i++) {
                if (mano.getCarta(i) != null) {
                    todasVacias = false;
                    break;
                }
            }
            if (todasVacias && baraja.isEmpty()) {
                System.out.println("\n¡Fin del juego! No quedan más cartas.");
                break;
            }
        }
    }

    /**
     * Obtiene el coste de la carta mediante reflexión.
     * Si no existe el método getCoste() o falla, devuelve 0.
     */
    private static int obtenerCoste(Carta c) {
        try {
            Method m = c.getClass().getMethod("getCoste");
            Object res = m.invoke(c);
            if (res instanceof Number) {
                return ((Number) res).intValue();
            } else {
                return Integer.parseInt(String.valueOf(res));
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Formatea el coste para mostrar en consola.
     */
    private static String costeDe(Carta c) {
        try {
            Method m = c.getClass().getMethod("getCoste");
            Object res = m.invoke(c);
            return String.valueOf(res);
        } catch (Exception e) {
            return "?";
        }
    }
}

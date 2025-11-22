package Pizzeria_NonStop;

/**
 * Se crea la clase Main que es la que operará con todas las clases de la pizzeria, utilizará las clases:
 * Cocinero, Repartidor y Pizza de forma concurrente y ordenada.
 * @author Victor
 */
public class Main {

    public static void main(String[] args) {

        //Se crea el objeto mostrador para ejecutar su metodo run y asi hacer fuincionar el codigo
        Mostrador mostrador = new Mostrador(2);

        //Se crea el objeto cocinero para utilizar sus atributos y metodos
        Thread cocinero = new Thread(new Cocinero(1, mostrador));
        Thread cocinero1 = new Thread(new Cocinero(1, mostrador));


        //Se crea el objeto Hilo repartidor para ejecutar su metodo run y asi hacer fuincionar el codigo
        Thread repartidores = new Thread(new  Repartidores(1, mostrador));
        Thread repartidores1 = new Thread(new  Repartidores(1, mostrador));

        //Se ejecutan los cocineros
        cocinero.start();
        cocinero1.start();

        //Se ejecutan los repartidores
        repartidores.start();
        repartidores1.start();
    }
}

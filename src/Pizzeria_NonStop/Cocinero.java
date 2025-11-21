package Pizzeria_NonStop;

import java.util.Random;

/**
 * Esta clase se usara como hilo para preparar las pizzas, se usaran hilos y semaforos para poder hacer su taea
 * @author Victor
 */
public class Cocinero implements Runnable{

    //Se le asigna un id al cocinero para identificarlo
    private int idCocinero;

    //Se instancia el objeto mostrador para poder operar con el
    private Mostrador mostrador;

    /**
     * Se crea el constructor de Cocinero para crear los objetos
     * @param idCocinero
     * @param m
     */
    public  Cocinero(int idCocinero, Mostrador m){

        this.idCocinero = idCocinero; //Identificador del cocinero
        mostrador = m; //Objeto mostrador
    }

    /**
     * Este metodo realiza las acciones del cocinero, crea una pizza con un identificador
     * crea la pizza con una aleatoriedad de tipos de pizza.
     */
    @Override
    public void run() {

        while(true) {

            try {
                //Simula el tiempo de cocinado de pizzas
                Thread.sleep(3000);

                //Este objeto se crea para poder elegir tipo de pizzas al azar
                Random random = new Random();

                //Obtiene todos los valores del Enum
                Pizza.tipoPizza[] tiposPizza = Pizza.tipoPizza.values();

                //Elige una pizza al azar
                Pizza.tipoPizza tipoAleatorio = tiposPizza[random.nextInt(tiposPizza.length)];

                //Crea el objeto pizza
                Pizza pizzaNueva = new Pizza(0, tipoAleatorio);

                System.out.println("El Cocinero " + idCocinero + " ha creado una pizza");

            } catch (InterruptedException e) {

                throw new RuntimeException(e);
            }

        }
    }
}

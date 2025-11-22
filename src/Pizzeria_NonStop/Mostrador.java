package Pizzeria_NonStop;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Esta clase se usara como clase compartida entre cocinero y repartidor, aqui se almacenaran las pizzas y se
 * recogeran y se crearan nuevas para que el cocinero cree pizzas y el repartidor se las lleve
 *
 * @author Victor
 */
public class Mostrador {

    //Implementacion de semaforos, este semaforo sirve para que nadie toque el arraylist (solo 1)
    private Semaphore mutex;

    //Cantidad de pizzas que se pueden dejar como maximo
    private Semaphore semHuecos;

    //Cantidad de pizzas que se han dejado
    private Semaphore semPizzas;

    //Almacenamiento de pizzas
    ArrayList<Pizza> pizzas;

    /**
     * Constructor para crear el objeto Mostrador
     * @param capacidadMaxima
     */
    public Mostrador(int capacidadMaxima) {

        //Se inicializa el mutex para poder bloquear y seguir el proceso de los hilos
        mutex = new Semaphore(1);
        semPizzas = new Semaphore(0);
        semHuecos = new Semaphore(capacidadMaxima);
        this.pizzas = new ArrayList<>();
    }

    /**
     * Metodo principal en el que se realizaran todas las acciones del mostrador
     * @param pizza
     */
    public void añadirPizza(Pizza pizza) throws InterruptedException {

        System.out.println("Añadiendo una pizza...");

        //Hay sitios en el mostrador (Si no hay el hilo se bloquea)
        semHuecos.acquire();

        //Pide permiso para modificar la lista (Zona critica)
        mutex.acquire();

        //Añade la pizza a la lista de pizzas en el mostrador
        añadirPizzaLista(pizza);
        System.out.println("Se añadió: "+pizza.toString() + " Total: "+ pizzas.size());

        //Devuelve el permiso de modificar
        mutex.release();

        //Incrementa semPizzas para avisar de que hay una pizza nueva
        semPizzas.release();

    }

    /**
     * Elimina la pizza de la lista de pizzas
     *
     */
    public Pizza eliminarPizza() throws InterruptedException {

        //Creamos el objeto Random para sacar valores aleatorios
        Random random = new Random();

        //Se inicializa el objeto pizza para mas tarde almacenar su valor y utilizarlo
        Pizza pizza;

        System.out.println("Eliminando una pizza...");

        //Si se coge una pizza, se libera un hueco
        semPizzas.acquire();

        //Pide permiso para entrar a la lista (Zona Critica)
        mutex.acquire();

            //--Logica aleatoria--
            //Genero un numero aleatorio entre 0 y el numero del tamaño de la lista -1
            int numeroPizzaAleatoria = random.nextInt(pizzas.size());

            //Recoge la pizza eliminada y la saca de la lista
            pizza = pizzas.remove(numeroPizzaAleatoria);

        //devuelve el permiso de modificar la lista
        mutex.release();

        //Dice que hay un hueco para una pizza nueva
        semHuecos.release();

        return pizza;
    }

    /**
     * Añade una pizza a la lista de pizzas
     * @param pizza
     */
    public void añadirPizzaLista(Pizza pizza){

        //Añade la pizza pasada por parametro a la lista de pizzas
        this.pizzas.add(pizza);
    }



}



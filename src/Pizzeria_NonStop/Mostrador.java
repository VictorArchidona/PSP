package Pizzeria_NonStop;

import java.util.ArrayList;
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
     * @param pizza
     */
    public void eliminarPizza(Pizza pizza) throws InterruptedException {

        System.out.println("Eliminando una pizza...");

        //Si se coge una pizza, se libera un hueco
        semPizzas.acquire();

        //Pide permiso para entrar a la lista (Zona Critica)
        mutex.acquire();

        //Elimina la pizza de la lista
        pizzas.remove(pizza);

        //devuelve el permiso de modificar la lista
        mutex.release();

        //Dice que hay un hueco para una pizza nueva
        semHuecos.release();

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

/**
 *
 *
 *🍕 EJERCICIO: La Pizzería Concurrente "Non-Stop"
 * 📝 Descripción
 * Se debe simular el funcionamiento de una pizzería de alta demanda utilizando Programación Concurrente en Java.
 * La pizzería tiene un espacio físico limitado en el mostrador para dejar las pizzas preparadas.
 *
 * El sistema debe coordinar a múltiples empleados (hilos) que trabajan simultáneamente sin que ocurran errores de datos
 * (condiciones de carrera) ni bloqueos infinitos (deadlocks).
 *
 * ⚙️ Requisitos Técnicos
 * Modelo de Datos:
 *
 * Debe existir una clase Pizza que tenga un id y un Tipo (usando un Enum: Margarita, Pepperoni, etc.).
 *
 * Recurso Compartido (Mostrador):
 *
 * Actúa como un buffer o almacén intermedio.
 *
 * Utiliza una estructura de datos (ej. ArrayList) para guardar las pizzas.
 *
 * Capacidad Limitada: Solo caben 5 pizzas (configurable).
 *
 * Sincronización: Debe usar Semáforos para controlar:
 *
 * Que no se intente añadir si está lleno.
 *
 * Que no se intente retirar si está vacío.
 *
 * Que dos hilos no toquen la lista al mismo tiempo (Exclusión Mutua / Mutex).
 *
 * Productores (Cocinero):
 *
 * Hilos que crean pizzas indefinidamente.
 *
 * Tardan un tiempo aleatorio en cocinar.
 *
 * Si el mostrador está lleno, deben esperar pacientemente.
 *
 * Consumidores (Repartidor):
 *
 * Hilos que recogen pizzas indefinidamente.
 *
 * Tardan un tiempo aleatorio en repartir.
 *
 * Si no hay pizzas, deben esperar a que salga una del horno.
 *
 * Ejecución (Main):
 *
 * Debe iniciar el mostrador, lanzar varios cocineros y varios repartidores al mismo tiempo.
 *
 *
 *
 *🗺️ TU MAPA DE PROGRESO
 * Aquí es donde te encuentras ahora mismo. ¡Ya has pasado el ecuador del ejercicio!
 *
 * ✅ 1. Clase TipoPizza (Enum): Definida.
 *
 * ✅ 2. Clase Pizza: Definida con sus atributos.
 *
 * ✅ 3. Clase Cocinero (Runnable): Lógica creada (bucle, generar pizza, llamar al mostrador).
 *
 * 🚧 4. Clase Mostrador: <-- ESTÁS AQUÍ
 *
 * ✅ Atributos y Semáforos definidos.
 *
 * ✅ Método depositarPizza() (Productores) TERMINADO.
 *
 * ❌ Método retirarPizza() (Consumidores) PENDIENTE.
 *
 * ✅ 5. Clase Repartidor (Runnable): Aún no creada (será el espejo del Cocinero).
 *
 * ❌ 6. Clase Main: Aún no creada (donde arranca todo).
 *
 *
 *
 */

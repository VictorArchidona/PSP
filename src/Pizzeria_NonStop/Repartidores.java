package Pizzeria_NonStop;

/**
 * Esta clase se encarga de recoger las pizzas que hayan disponibles en el mostrador
 * @author Victor
 */
public class Repartidores implements Runnable{

    //Se asigna un id al repartidor para poder identificarlo
    private int idRepartidor;

    //Se instncia un objeto Mostrador para poder utilizarlo
    private Mostrador mostrador;

    /**
     * Constructor Repartidor para crear este objeto
     * @param idRepartidor
     * @param mostrador
     */
    public Repartidores(int idRepartidor,  Mostrador mostrador) {

        this.idRepartidor = idRepartidor;
        this.mostrador = mostrador;
    }

    /**
     *
     *
     */
    @Override
    public void run() {

        while(true) {
            try {

                //Simula la recogida de pizzas
                Thread.sleep(2000);

                //Se recoge la pizza del mostrador.
                Pizza pizzaParaEntregar = mostrador.eliminarPizza();

                System.out.println("Llevando la pizza el repartidor: " + idRepartidor);

                Thread.sleep(2000);

                System.out.println("Pizza entregada con éxito");


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

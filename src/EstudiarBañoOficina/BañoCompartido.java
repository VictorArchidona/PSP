package EstudiarBañoOficina;

import java.util.concurrent.Semaphore;

/**
 * Esta clase representa el baño de una oficina, es un recurso compartido entre empleados (hilos) que
 * tendre que manejar para que no se pisen los empleados al usar el baño, y vayan de 1 en 1 o de 2 en 2.
 *
 * @author Victor
 */
public class BañoCompartido {

    //Este semaforo controla si el baño está ocupado o no.
    private Semaphore semaforoOcupado;

    /**
     * Pre: ---
     * Post: Crea un nuevo baño compartido.
     */
    public BañoCompartido() {
        this.semaforoOcupado = new Semaphore(2); //Permite que dos empleados entren al baño simultaneamente
    }

    /**
     * Pre: ---
     * Post: El empleado intenta entrar al baño. Si el baño está ocupado, el empleado esperará hasta que esté libre.
     */
    public void entrarAlBaño(){

        try {

            String nombreHilo = Thread.currentThread().getName();

            semaforoOcupado.acquire();
            System.out.print("El empleado" + nombreHilo + " ha empezado a mear \n");
            Thread.sleep(5000); // Simula el tiempo que tarda en usar el baño
            System.out.print("El empleado" + nombreHilo + " ha terminado de mear \n");
            semaforoOcupado.release();

        } catch (InterruptedException e) {
            System.out.print("Error al intentar entrar al baño: " + e.getMessage() + "\n");
            return;
        }

    }
}

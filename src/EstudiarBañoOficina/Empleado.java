package EstudiarBañoOficina;

/**
 * Esta clase representa un empleado de una oficina que puede necesitar usar el baño compartido.
 * Cada empleado será representado por un hilo que intentará acceder al baño según las reglas
 * definidas en la clase BañoCompartido.
 *
 * @author Victor
 */
public class Empleado implements Runnable{

    //Esta variable indica si el empleado ya ha terminado de ir al baño
    private boolean terminado;
    private BañoCompartido baño;

    //Nombre que se le asigna al empleado
    private String nombre;

    public Empleado(String nombre, BañoCompartido baño) {
        this.baño = baño;
        this.nombre = nombre;
        this.terminado = false;
    }

    /**
     *  Pre: ---
     *  Post: Crea un nuevo empleado con el nombre especificado, hace que si tiene que ir al baño vaya con un orden.
     */
    @Override
    public void run() {

        System.out.print("Empleado "+ nombre + " ha ido al baño \n");
        baño.entrarAlBaño();
        System.out.print("El empleado" + nombre + " ha terminado \n");
    }

    /**
     * Pre: ---
     * Post: Devuelve el nombre del empleado.
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Pre: ---
     * Post: Establece el nombre del empleado.
     * @param nombre Es el nombre del empleado a establecer..
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Pre: ---
     * Post: Devuelve el baño compartido asociado al empleado.
     * @return
     */
    public BañoCompartido getBaño() {
        return baño;
    }

    /**
     * Pre: ---
     * Post: Establece el baño compartido asociado al empleado.
     * @param baño
     */
    public void setBaño(BañoCompartido baño) {
        this.baño = baño;
    }
}

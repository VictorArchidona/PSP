import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Esta clase es la clase compartida la cual contendrá sus atributos y metodos que gestionen un sistema
 * de control aéreo de un aeropuerto. Debe gestionar solicitudes de múltiples aeronaves de forma concurrente
 * garantizando la seguridad y el orden mediante el uso estricto de semaforos
 * @author Victor
 */

public class SharedData {

    //Se crea la lista para simular las solicitudes de los controladores
    private ArrayList<String> pila;

    /**
     * Se inicializa el constructor con su atributo pila para que pueda ser utilizado
     */
    public SharedData(){

        this.pila = new ArrayList<>();

    }

    /**
     * Se recoge la pila para ser utilizada por otras clases
     * @return
     */
    public ArrayList<String> getPila() {
        return pila;
    }
}

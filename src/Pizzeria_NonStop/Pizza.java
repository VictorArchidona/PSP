package Pizzeria_NonStop;

/**
 * Esta clase representa los tipos de pizza que pueden almacenarse en el mostrador
 */
public class Pizza {

    //Se le asigna un numero para darle un valor unico
    private int id;

    //Se le asigna el tipo de pizza que es
    private tipoPizza tipo;

    /**
     * Se crea el objeto de tipo pizza con unos tipos de pizza y un valor unico
     * @param id
     * @param tipo
     */
    public Pizza(int id, tipoPizza tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    /**
     * Se le asignan los unicos tipos de pizza que pueden haber
     */
    public enum tipoPizza {

        Margarita,
        Pepperoni,
        Cuatro_Quesos,
        Hawaiana,
        Barbacoa;
    }

    /**
     * Se obtiene el tipo de pizza que es
     * @return
     */
    public tipoPizza getTipo() {
        return tipo;
    }

    /**
     * Se asigna el tipo de pizza
     * @param tipo
     */
    public void setTipo(tipoPizza tipo) {
        this.tipo = tipo;
    }

    /**
     * Metodo toString() clave para que al imprimir salga bonito en consola
     */
    @Override
    public String toString() {
        return "Pizza: " + "id: " + id + ", tipo: " + tipo;
    }
}

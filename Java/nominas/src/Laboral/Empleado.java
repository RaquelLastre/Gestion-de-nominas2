package Laboral;

public class Empleado extends Persona {

    private int categoria;
    public int anyos;

    public Empleado(String nombre, String dni, String sexo, int categoria, int anyos) throws DatosNoCorrectosException {
        super(nombre, dni, sexo);
        if (categoria < 1 || categoria > 10 || anyos < 0 || nombre == null || nombre == "" || dni == null || dni == ""
                || sexo == null || sexo == "") {
            throw new DatosNoCorrectosException();
        }
        this.categoria = categoria;
        this.anyos = anyos;
    }

    public Empleado(String nombre, String sexo, String dni) throws DatosNoCorrectosException {
        super(nombre, sexo, dni);
        if (nombre == null || nombre == "" || dni == null || dni == "" || sexo == null || sexo == "") {
            throw new DatosNoCorrectosException();
        }
        categoria = 1;
        anyos = 0;
    }

    /**
     * @param categoria
     *                  Permite cambiar la categoria
     */
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    /**
     * @return int
     *         Devuelve el numero de categoria
     */
    public int getCategoria() {
        return categoria;
    }

    public void incrAnyo() {
        anyos++;
    }

    /**
     * Imprime los datos del empleado
     */
    public void imprime() {
        System.out.println(
                "Nombre: " + nombre + ", DNI: " + dni + ",sexo: " + sexo + ", categoria: " + categoria + ", años:"
                        + anyos);
    }

}

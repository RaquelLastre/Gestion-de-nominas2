package Laboral;

import java.util.List;

import Ficheros.GestorFicherosEmpleados;

public class CalculaNominas {
    /**
     * @param args
     */
    public static void main(String[] args) {

        Empleado jc = null;
        Empleado al = null;
        try {
            jc = new Empleado("James Cosling", "32000032G", "M", 4, 7);
            al = new Empleado("Ada Lovelace", "32000031R", "F");
        } catch (Exception e) {
            System.out.println("Datos no correctos");
        }

        escribe(jc, al);

        al.incrAnyo();
        jc.setCategoria(9);

        escribe(jc, al);

        List<Empleado> listaEmpleados = new GestorFicherosEmpleados().leerFicheroEmpleados();
        Empleado e1 = listaEmpleados.get(0);
        Empleado e2 = listaEmpleados.get(1);
        escribe(e1, e2);

    }

    /**
     * @param emp
     * @param emp2
     */
    private static void escribe(Empleado emp, Empleado emp2) {
        Nomina n = new Nomina();
        System.out.println(emp.nombre + "->  DNI: " + emp.dni + ", sexo: " + emp.sexo
                + ", categoria: " + emp.getCategoria() + ", años: " + emp.anyos + ", sueldo: " + n.sueldo(emp) +
                ". " + emp2.nombre + "->  DNI: " + emp2.dni + ", sexo: " + emp2.sexo);
    }

}

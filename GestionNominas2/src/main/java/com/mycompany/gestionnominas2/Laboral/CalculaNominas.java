/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionnominas2.Laboral;

import com.mycompany.gestionnominas2.Ficheros.GestorFicherosEmpleados;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Raquel L A
 */
public class CalculaNominas {
    public static void main(String[] args) {

        List<Empleado> listaEmpleados = new GestorFicherosEmpleados().leerFicheroEmpleados();
        
        for (int i = 0; i < listaEmpleados.size(); i++) {
            Empleado e = listaEmpleados.get(i);
            escribe(e);
        }
        GestorFicherosEmpleados gf = new GestorFicherosEmpleados();
        gf.escribirFicheroEmpleados(listaEmpleados);
        gf.altaEmpleados();

        int opcion = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("""
                               Escribe tu opcion 
                                1: Mostrar toda la informacion
                                2: Mostrar salario de un empleado por DNI 
                                3:Modificar datos de un empleado
                                4: Recalcular y actualizar sueldo de un empleado 
                                5: Recalcular y actualizar sueldo de todos los empleados
                                6: Realizar copia de seguridad de la base de datos""");
            opcion = sc.nextInt();
        } while (opcion < 1 || opcion > 6);
        sc.close();
        switch (opcion) {
            case 0:

                break;

            default:
                break;
        }

    }

    /**
     * @param emp
     */

    private static void escribe(Empleado emp) {
        Nomina n = new Nomina();

        System.out.println(emp.nombre + "->  DNI: " + emp.dni + ", sexo: " + emp.sexo + ", categoria: "
                + emp.getCategoria() + ", años: " + emp.anyos + ", sueldo: " + n.sueldo(emp));
    }
}

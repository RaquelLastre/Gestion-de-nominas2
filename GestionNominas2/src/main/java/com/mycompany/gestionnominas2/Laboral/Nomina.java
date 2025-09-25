/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionnominas2.Laboral;

/**
 *
 * @author Raquel L A
 */
public class Nomina {
        private static final int SUELDO_BASE[] = { 50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000,
            230000 };

    /**
     * @param emp
     * @return int
     */
    public int sueldo(Empleado emp) {
        return SUELDO_BASE[emp.getCategoria() - 1] + 5000 * emp.anyos;
    }
}

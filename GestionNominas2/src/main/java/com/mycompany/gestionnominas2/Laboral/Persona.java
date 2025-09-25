/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionnominas2.Laboral;

/**
 *
 * @author Raquel L A
 */
public class Persona {
        public String nombre;
    public String dni;
    public String sexo;

    public Persona(String nombre, String dni, String sexo) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
    }

    public Persona(String nombre, String sexo) {
        this.nombre = nombre;
        this.sexo = sexo;
    }

    /**
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    public void imprime() {
        System.out.println("Nombre: " + nombre + ", DNI: " + dni);
    }
}

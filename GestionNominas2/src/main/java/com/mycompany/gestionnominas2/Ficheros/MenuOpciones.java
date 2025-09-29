/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionnominas2.Ficheros;

import com.mycompany.gestionnominas2.Laboral.DatosNoCorrectosException;
import com.mycompany.gestionnominas2.Laboral.Empleado;
import com.mycompany.gestionnominas2.Laboral.Nomina;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 *
 * @author Raquel L A
 */
public class MenuOpciones {
    public static void mostrarTodaLaInformacion() {

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement("select * from empleados");
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String sexo = rs.getString("sexo");
                Integer categoria = rs.getInt("categoria");
                Integer anyos = rs.getInt("anos");

                System.out
                        .println("ID: " + id + ", Nombre: " + nombre + ", DNI: " + dni + ", Sexo: " + sexo
                                + ", Categoria: " + categoria + ", Años: " + anyos);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void mostrarSueldoConDNI(String DNI) {

        String sentencia = "select salario from nominas where dni =?";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sentencia);
                ResultSet rs = ps.executeQuery()) {

            ps.setString(1, DNI);

            if (rs.next()) {
                double salario = rs.getDouble("salario");
                System.out.println("Salario: " + salario);
            } else {
                System.out.println("No existe el DNI: " + DNI);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void recalcularSueldo(String dni)
    {
        String insert = "update nominas set sueldo = ? WHERE dni =?;";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(insert);) {
            
            Nomina n = new Nomina();
            int sueldo = n.sueldo(new Empleado(null, null, dni));
            
            ps.setInt(1, sueldo);
            ps.setString(2, dni);
                
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }    
    
    public static void submenu() throws DatosNoCorrectosException{
        int opcion = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("""
                               Campo para modificar 
                                1: nombre
                                2: sexo 
                                3: categoria
                                4: años
                               """);
            opcion = sc.nextInt();
        } while (opcion < 1 || opcion > 4);
        String cadena = "";
        Scanner scs = new Scanner(System.in);
        System.out.println("Escribe el dni del empleado a modificar");
        String dni = scs.nextLine();
        String sentencia = "";
        String hueco = "";
        int numhueco = 0;
        switch (opcion) {
            case 1:
                System.out.println("Nombre nuevo: ");
                hueco = scs.nextLine();
                sentencia = "update empleados set nombre = ? where dni = ?;";
                break;
            case 2:
                System.out.println("Sexo nuevo: ");
                hueco = scs.nextLine();
                sentencia = "update empleados set sexo = ? where dni = ?;";
                break;
            case 3:
                System.out.println("Categoria nueva: ");
                numhueco = sc.nextInt();
                sentencia = "update empleados set categoria = ? where dni = ?;";
                recalcularSueldo(dni);
                break;
            case 4:
                System.out.println("Años nuevos: ");
                numhueco = sc.nextInt();
                sentencia = "update empleados set anos = ? where dni = ?;";
                recalcularSueldo(dni);
                break;
            default:
                break;
        }
                
        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sentencia);) {

            if(opcion == 1||opcion==2){
                 ps.setString(1, hueco);
            }else{
                ps.setInt(1, numhueco);
            }
           
            ps.setString(2, dni);
                
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void recalcularSueltoTodos(){
        
        String select = "select nombre, sexo, dni from empleados;";
        String insert = "update nominas set sueldo = ? WHERE dni =?;";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(select);
                ResultSet rs = ps.executeQuery();
                PreparedStatement psInsert = con.prepareStatement(insert)) {
            
            Nomina n = new Nomina();
            
            while (rs.next()) {                
                Empleado e = new Empleado(rs.getString("nombre"), rs.getString("sexo"), rs.getString("dni"));
                int sueldo = n.sueldo(e);
                
                psInsert.setInt(1, sueldo);
                psInsert.setString(2, e.dni);
                psInsert.executeUpdate();
            }  
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void copiaSeguridad(){
        String ruta = "../Data/CopiaSeguridad.txt";
        String select = "select e.nombre, e.dni, e.sexo, e.categoria, e.anos, n.sueldo from empleados e join nominas n on e.dni = n.dni";
        
        try(Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(select);
                ResultSet rs = ps.executeQuery();
                BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            
            while (rs.next()) {  
                String cadena = "";
                cadena +="Nombre: "+ rs.getString("nombre");
                cadena +=", DNi: "+ rs.getString("dni");
                cadena +=", Sexo: "+ rs.getString("sexo");
                cadena +=", Categoria: "+ rs.getInt("categoria");
                cadena +=", Años: "+ rs.getInt("anos");
                cadena +=", Sueldo: "+ rs.getInt("sueldo");
                bw.write(cadena);
                bw.newLine();
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}

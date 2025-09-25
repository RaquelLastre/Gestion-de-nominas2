/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionnominas2.Ficheros;

import com.mycompany.gestionnominas2.Laboral.Empleado;
import com.mycompany.gestionnominas2.Laboral.Nomina;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Raquel L A
 */
public class MenuOpciones {
    public void mostrarTodaLaInformacion() {

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

    public void mostrarSueldoConDNI(String DNI) {

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
    
    public void recalcularSueldo(String dni)
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
    
    public void recalcularSueltoTodos(){
        
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
    
    public void copiaSeguridad(){
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

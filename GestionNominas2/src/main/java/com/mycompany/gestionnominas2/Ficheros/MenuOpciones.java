/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionnominas2.Ficheros;

import com.mycompany.gestionnominas2.Laboral.DatosNoCorrectosException;
import com.mycompany.gestionnominas2.Laboral.Empleado;
import com.mycompany.gestionnominas2.Laboral.Nomina;
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
                PreparedStatement ps = con.prepareStatement(sentencia);) {

            ps.setString(1, DNI);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    double salario = rs.getDouble("salario");
                    System.out.println("Salario: " + salario);
                } else {
                    System.out.println("No existe el DNI: " + DNI);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void recalcularSueldo(String dni) {
        String cadena = "select nombre, sexo, categoria, anos from empleados where dni = ?";

        Empleado empl = null;

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement ps = conn.prepareStatement(cadena);) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                empl = new Empleado(rs.getString("nombre"), dni, rs.getString("sexo"), rs.getInt("categoria"),
                        rs.getInt("anos"));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String insert = "update nominas set salario = ? WHERE dni = ?;";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(insert);) {

            Nomina n = new Nomina();
            int sueldo = n.sueldo(empl);

            ps.setInt(1, sueldo);
            ps.setString(2, dni);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void submenu() throws DatosNoCorrectosException {
        int opcion = 0;
        Scanner scSub = new Scanner(System.in);
        do {
            System.out.println("""
                    Campo para modificar
                     1: nombre
                     2: sexo
                     3: categoria
                     4: años
                    """);
            opcion = scSub.nextInt();
        } while (opcion < 1 || opcion > 4);
        Scanner scsSub = new Scanner(System.in);
        System.out.println("Escribe el dni del empleado a modificar");
        String dni = scsSub.nextLine();
        String sentencia = "";
        String hueco = "";
        int numhueco = 0;
        switch (opcion) {
            case 1:
                System.out.println("Nombre nuevo: ");
                hueco = scsSub.nextLine();
                sentencia = "update empleados set nombre = ? where dni = ?;";
                break;
            case 2:
                System.out.println("Sexo nuevo(F/M): ");
                hueco = scsSub.nextLine();
                sentencia = "update empleados set sexo = ? where dni = ?;";
                break;
            case 3:
                System.out.println("Categoria nueva(1-10): ");
                numhueco = scSub.nextInt();
                sentencia = "update empleados set categoria = ? where dni = ?;";
                recalcularSueldo(dni);
                break;
            case 4:
                System.out.println("Años nuevos: ");
                numhueco = scSub.nextInt();
                sentencia = "update empleados set anos = ? where dni = ?;";
                recalcularSueldo(dni);
                break;
            default:
                break;
        }

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(sentencia);) {

            if (opcion == 1 || opcion == 2) {
                ps.setString(1, hueco);
            } else {
                ps.setInt(1, numhueco);
            }

            ps.setString(2, dni);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void recalcularSueltoTodos() {

        String select = "select nombre, sexo, dni, categoria, anos from empleados;";
        String insert = "update nominas set salario = ? WHERE dni =?;";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(select);
                ResultSet rs = ps.executeQuery();
                PreparedStatement psInsert = con.prepareStatement(insert)) {

            Nomina n = new Nomina();

            while (rs.next()) {
                Empleado e = new Empleado(rs.getString("nombre"), rs.getString("sexo"), rs.getString("dni"),
                        rs.getInt("categoria"), rs.getInt("anos"));
                int sueldo = n.sueldo(e);

                psInsert.setInt(1, sueldo);
                psInsert.setString(2, e.dni);
                psInsert.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void copiaSeguridad() {
        String ruta = "src\\main\\java\\com\\mycompany\\gestionnominas2\\Data\\CopiaSeguridad.txt";
        String select = "select e.nombre, e.dni, e.sexo, e.categoria, e.anos, n.salario from empleados e join nominas n on e.dni = n.dni";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(select);
                ResultSet rs = ps.executeQuery();
                BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {

            while (rs.next()) {
                String cadena = "";
                cadena += "Nombre: " + rs.getString("nombre");
                cadena += ", DNi: " + rs.getString("dni");
                cadena += ", Sexo: " + rs.getString("sexo");
                cadena += ", Categoria: " + rs.getInt("categoria");
                cadena += ", Años: " + rs.getInt("anos");
                cadena += ", Sueldo: " + rs.getInt("salario");
                bw.write(cadena);
                bw.newLine();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

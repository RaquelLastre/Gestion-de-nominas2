/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionnominas2.Ficheros;

import com.mycompany.gestionnominas2.Laboral.Empleado;
import com.mycompany.gestionnominas2.Laboral.Nomina;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Raquel L A
 */
public class GestorFicherosEmpleados {
    public List<Empleado> leerFicheroEmpleados() {

        List<Empleado> listaEmpleados = new ArrayList<>();
        String ruta = "src\\main\\java\\com\\mycompany\\gestionnominas2\\Data\\empleados.txt";
        String separador = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea = " ";
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(separador);
                String dni = partes[0].trim();
                String nombre = partes[1].trim();
                String sexo = partes[2].trim();
                Integer categoria = Integer.parseInt(partes[3].trim());
                Integer anyos = Integer.parseInt(partes[4].trim());

                try {
                    Empleado e = new Empleado(nombre, dni, sexo, categoria, anyos);
                    listaEmpleados.add(e);
                } catch (Exception e) {
                    System.out.println("Error al crear empleado: " + e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.out.println("Error al crear empleado: " + e.getMessage());
            e.printStackTrace();
        }

        return listaEmpleados;
    }

    public void escribirFicheroEmpleados(List<Empleado> empleados) {

        String ruta = "src\\main\\java\\com\\mycompany\\gestionnominas2\\Data\\salarios.dat";

        List<Empleado> listaEmpleados = empleados;

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(ruta))) {
            for (int i = 0; i < listaEmpleados.size(); i++) {
                Nomina n = new Nomina();
                dos.writeUTF(listaEmpleados.get(i).dni);
                dos.writeInt(n.sueldo(listaEmpleados.get(i)));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void altaEmpleados() {
        String insert = "insert into empleados (nombre, dni, sexo, categoria, anos) values (?, ?, ?, ?, ?)";
        String insertNomina = "insert into nominas (dni, salario) values (?, ?)";
        String ruta = "src\\main\\java\\com\\mycompany\\gestionnominas2\\Data\\empleadosNuevos.txt";

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(insert);
                PreparedStatement psn = con.prepareStatement(insertNomina);
                BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String cadena;

            while ((cadena = br.readLine()) != null) {
                String[] datos = cadena.split(";");

                Empleado e = new Empleado(datos[0].trim(), datos[1].trim(), datos[2].trim(),
                        Integer.parseInt(datos[3].trim()), Integer.parseInt(datos[4].trim()));

                ps.setString(1, e.nombre);
                ps.setString(2, e.dni);
                ps.setString(3, e.sexo);
                ps.setInt(4, e.getCategoria());
                ps.setInt(5, e.anyos);

                Nomina n = new Nomina();
                psn.setString(1, e.dni);
                psn.setDouble(2, n.sueldo(e));

                ps.executeUpdate();
                psn.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void altaEmpleados(String rutaArchivo) {
        String insert = "insert into empleados (nombre, dni, sexo, categoria, anyos) values (?, ?, ?, ?, ?)";
        String ruta = rutaArchivo;

        try (Connection con = DBUtils.getConnection();
                PreparedStatement ps = con.prepareStatement(insert);
                BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String cadena;

            if ((cadena = br.readLine()) != null) {
                String[] datos = cadena.split(";");

                Empleado e = new Empleado(datos[0].trim(), datos[1].trim(), datos[2].trim(),
                        Integer.parseInt(datos[3].trim()), Integer.parseInt(datos[3].trim()));

                ps.setString(1, e.nombre);
                ps.setString(2, e.dni);
                ps.setString(3, e.sexo);
                ps.setInt(4, e.getCategoria());
                ps.setInt(5, e.anyos);

                Nomina n = new Nomina();
                ps.setDouble(6, n.sueldo(e));

                ps.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

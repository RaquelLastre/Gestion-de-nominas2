package Ficheros;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Laboral.Empleado;

public class GestorFicherosEmpleados {

    public List<Empleado> leerFicheroEmpleados() {

        List<Empleado> listaEmpleados = new ArrayList<>();
        String ruta = "Ficheros/empleados.txt";
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
}

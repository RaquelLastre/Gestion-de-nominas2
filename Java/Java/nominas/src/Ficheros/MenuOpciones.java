package Ficheros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}

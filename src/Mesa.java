
import java.sql.*;

public class Mesa {

    int numero, capacidad;
    String estadom;
    String error = "";

    public Mesa() {

    }

    public Mesa(int numero, String estadom, int capacidad) {
        this.numero = numero;
        this.estadom = estadom;
        this.capacidad = capacidad;
    }

    public int insertar(Connection con) {
        int resultado = 0;
        try {
            String sql = "INSERT INTO mesas (numero, estadom, capacidad) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, numero);
            ps.setString(2, estadom);
            ps.setInt(3, capacidad);
            return ps.executeUpdate();

        } catch (Exception e) {
            if (e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062) {
                error = "el número de mesa ya existe";
            } else {
                error = e.getMessage();
            }
            System.out.println("Error: " + e.getMessage());
            return resultado;
        }
    }

    public int modificar(Connection con) {
        int resultado = 0;
        try {
            String sql = "UPDATE mesas SET estadom = ?, capacidad = ? WHERE numero = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, estadom);
            ps.setInt(2, capacidad);
            ps.setInt(3, numero);
            resultado = ps.executeUpdate();
            if (resultado == 0) {
                error = "el número de mesa no existe";
            }
            return resultado;

        } catch (Exception e) {
            error = e.getMessage();
            System.out.println("Error: " + e.getMessage());
            return resultado;
        }
    }

    public int borrar(Connection con) {
        int resultado = 0;
        try {
            String sql = "DELETE FROM mesas WHERE numero = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, numero);
            resultado = ps.executeUpdate();
            if (resultado == 0) {
                error = "el número de mesa no existe";
            }
            return resultado;

        } catch (Exception e) {
            if (e instanceof SQLException && ((SQLException) e).getErrorCode() == 1451) {
                error = "la mesa tiene órdenes asociadas";
            } else {
                error = e.getMessage();
            }
            System.out.println("Error: " + e.getMessage());
            return resultado;
        }
    }

}

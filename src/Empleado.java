
import java.sql.*;

public class Empleado {

    String codigo, nombre, puesto, turno, nom_usr, nip, estadoe;
    String error = "";

    public Empleado() {

    }

    public Empleado(String codigo, String nombre, String puesto, String turno,
            String nom_usr, String nip, String estadoe) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.puesto = puesto;
        this.turno = turno;
        this.nom_usr = nom_usr;
        this.nip = nip;
        this.estadoe = estadoe;
    }

    public int insertar(Connection con) {
        int resultado = 0;
        try {
            String sql = "INSERT INTO empleados (codigo, nombre, puesto, turno, nom_usr, nip, estadoe) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setString(3, puesto);
            ps.setString(4, turno);
            ps.setString(5, nom_usr);
            ps.setString(6, nip);
            ps.setString(7, estadoe);
            return ps.executeUpdate();

        } catch (Exception e) {
            if (e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062) {
                error = "el código o el usuario ya existe";
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
            String sql = "UPDATE empleados SET nombre = ?, puesto = ?, turno = ?, nom_usr = ?, nip = ?, estadoe = ? WHERE codigo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, puesto);
            ps.setString(3, turno);
            ps.setString(4, nom_usr);
            ps.setString(5, nip);
            ps.setString(6, estadoe);
            ps.setString(7, codigo);
            resultado = ps.executeUpdate();
            if (resultado == 0) {
                error = "el código no existe";
            }
            return resultado;

        } catch (Exception e) {
            if (e instanceof SQLException && ((SQLException) e).getErrorCode() == 1062) {
                error = "el usuario ya existe";
            } else {
                error = e.getMessage();
            }
            System.out.println("Error: " + e.getMessage());
            return resultado;
        }
    }

    public int borrar(Connection con) {
        int resultado = 0;
        try {
            String sql = "DELETE FROM empleados WHERE codigo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            resultado = ps.executeUpdate();
            if (resultado == 0) {
                error = "el código no existe";
            }
            return resultado;

        } catch (Exception e) {
            if (e instanceof SQLException && ((SQLException) e).getErrorCode() == 1451) {
                error = "el empleado tiene órdenes asociadas";
            } else {
                error = e.getMessage();
            }
            System.out.println("Error: " + e.getMessage());
            return resultado;
        }
    }

    // Control de acceso: busca el usuario y el NIP, si existe y está activo
    // llena el código, el nombre y el puesto del empleado
    public int acceder(Connection con) {
        int resultado = 0;
        try {
            String sql = "SELECT codigo, nombre, puesto FROM empleados WHERE nom_usr = ? AND nip = ? AND estadoe = 'Activo'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nom_usr);
            ps.setString(2, nip);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                codigo = rs.getString("codigo");
                nombre = rs.getString("nombre");
                puesto = rs.getString("puesto");
                resultado = 1;
            } else {
                error = "usuario o NIP incorrectos";
            }
            return resultado;

        } catch (Exception e) {
            error = e.getMessage();
            System.out.println("Error: " + e.getMessage());
            return resultado;
        }
    }

}

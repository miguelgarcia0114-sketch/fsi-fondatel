
import java.io.FileInputStream;
import java.sql.*;
import javax.swing.ImageIcon;

public class Alimento {

    int idalimentos;
    String nombrea, descripcion, categoria, estadoa;
    float precio;
    ImageIcon foto;
    String error = "";

    public Alimento() {

    }

    public Alimento(int idalimentos, String nombrea, String descripcion, float precio,
            ImageIcon foto, String categoria, String estadoa) {
        this.idalimentos = idalimentos;
        this.nombrea = nombrea;
        this.descripcion = descripcion;
        this.precio = precio;
        this.foto = foto;
        this.categoria = categoria;
        this.estadoa = estadoa;
    }

    public int insertar(Conexion cnx) {
        int resultado = 0;
        try {
            String sql = "INSERT INTO alimentos (nombrea, descripcion, precio, foto, categoria, estadoa) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cnx.getCon().prepareStatement(sql);
            FileInputStream fis = cnx.ImagenBytes(foto);
            ps.setString(1, nombrea);
            ps.setString(2, descripcion);
            ps.setFloat(3, precio);
            ps.setBinaryStream(4, fis, fis.available());
            ps.setString(5, categoria);
            ps.setString(6, estadoa);
            resultado = ps.executeUpdate();
            fis.close();
            return resultado;

        } catch (Exception e) {
            error = e.getMessage();
            System.out.println("Error: " + e.getMessage());
            return resultado;
        }
    }

    // Si no se eligió una foto nueva (foto == null) se conserva la que ya tiene
    public int modificar(Conexion cnx) {
        int resultado = 0;
        try {
            PreparedStatement ps;
            if (foto == null) {
                String sql = "UPDATE alimentos SET nombrea = ?, descripcion = ?, precio = ?, categoria = ?, estadoa = ? WHERE idalimentos = ?";
                ps = cnx.getCon().prepareStatement(sql);
                ps.setString(1, nombrea);
                ps.setString(2, descripcion);
                ps.setFloat(3, precio);
                ps.setString(4, categoria);
                ps.setString(5, estadoa);
                ps.setInt(6, idalimentos);
                resultado = ps.executeUpdate();
            } else {
                String sql = "UPDATE alimentos SET nombrea = ?, descripcion = ?, precio = ?, foto = ?, categoria = ?, estadoa = ? WHERE idalimentos = ?";
                ps = cnx.getCon().prepareStatement(sql);
                FileInputStream fis = cnx.ImagenBytes(foto);
                ps.setString(1, nombrea);
                ps.setString(2, descripcion);
                ps.setFloat(3, precio);
                ps.setBinaryStream(4, fis, fis.available());
                ps.setString(5, categoria);
                ps.setString(6, estadoa);
                ps.setInt(7, idalimentos);
                resultado = ps.executeUpdate();
                fis.close();
            }
            if (resultado == 0) {
                error = "el alimento no existe";
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
            String sql = "DELETE FROM alimentos WHERE idalimentos = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idalimentos);
            resultado = ps.executeUpdate();
            if (resultado == 0) {
                error = "el alimento no existe";
            }
            return resultado;

        } catch (Exception e) {
            if (e instanceof SQLException && ((SQLException) e).getErrorCode() == 1451) {
                error = "el alimento tiene órdenes asociadas";
            } else {
                error = e.getMessage();
            }
            System.out.println("Error: " + e.getMessage());
            return resultado;
        }
    }

}

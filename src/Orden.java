
import java.sql.*;

public class Orden {

    int folio, silla, mesas_numero, alimentos_idalimentos;
    String fecha_hora, observaciones, estadoo, clientes_codigoc;
    String empleados_codigo_mesero, empleados_codigo_cocinero;
    String error = "";

    public Orden() {

    }

    public Orden(int folio, String fecha_hora, String observaciones, String estadoo, int silla,
            String clientes_codigoc, int mesas_numero, int alimentos_idalimentos,
            String empleados_codigo_mesero, String empleados_codigo_cocinero) {
        this.folio = folio;
        this.fecha_hora = fecha_hora;
        this.observaciones = observaciones;
        this.estadoo = estadoo;
        this.silla = silla;
        this.clientes_codigoc = clientes_codigoc;
        this.mesas_numero = mesas_numero;
        this.alimentos_idalimentos = alimentos_idalimentos;
        this.empleados_codigo_mesero = empleados_codigo_mesero;
        this.empleados_codigo_cocinero = empleados_codigo_cocinero;
    }

    // Estados: Ordenado, Preparando, Entregado, Devuelto, Cancelar, Pagado
    // Regresa true si la orden puede pasar del estado actual al nuevo
    public static boolean puedeCambiar(String actual, String nuevo) {
        if (nuevo.equals("Preparando")) {
            return actual.equals("Ordenado");
        }
        if (nuevo.equals("Entregado")) {
            return actual.equals("Preparando");
        }
        if (nuevo.equals("Devuelto")) {
            return actual.equals("Entregado");
        }
        if (nuevo.equals("Cancelar")) {
            return actual.equals("Ordenado") || actual.equals("Preparando");
        }
        if (nuevo.equals("Pagado")) {
            return actual.equals("Entregado");
        }
        return false;
    }

    public int insertar(Connection con) {
        int resultado = 0;
        try {
            String sql = "INSERT INTO ordenes (fecha_hora, observaciones, estadoo, silla, clientes_codigoc, mesas_numero, alimentos_idalimentos, empleados_codigo_mesero) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fecha_hora);
            ps.setString(2, observaciones);
            ps.setString(3, estadoo);
            ps.setInt(4, silla);
            ps.setString(5, clientes_codigoc);
            ps.setInt(6, mesas_numero);
            ps.setInt(7, alimentos_idalimentos);
            ps.setString(8, empleados_codigo_mesero);
            return ps.executeUpdate();

        } catch (Exception e) {
            error = e.getMessage();
            System.out.println("Error: " + e.getMessage());
            return resultado;
        }
    }

    public int modificar(Connection con) {
        int resultado = 0;
        try {
            String sql = "UPDATE ordenes SET observaciones = ?, silla = ?, clientes_codigoc = ?, mesas_numero = ?, alimentos_idalimentos = ? WHERE folio = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, observaciones);
            ps.setInt(2, silla);
            ps.setString(3, clientes_codigoc);
            ps.setInt(4, mesas_numero);
            ps.setInt(5, alimentos_idalimentos);
            ps.setInt(6, folio);
            resultado = ps.executeUpdate();
            if (resultado == 0) {
                error = "el folio no existe";
            }
            return resultado;

        } catch (Exception e) {
            error = e.getMessage();
            System.out.println("Error: " + e.getMessage());
            return resultado;
        }
    }

    // Cambia el estado de la orden. Si trae el código del cocinero también se lo asigna
    public int cambiarEstado(Connection con) {
        int resultado = 0;
        try {
            PreparedStatement ps;
            if (empleados_codigo_cocinero == null) {
                String sql = "UPDATE ordenes SET estadoo = ? WHERE folio = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, estadoo);
                ps.setInt(2, folio);
            } else {
                String sql = "UPDATE ordenes SET estadoo = ?, empleados_codigo_cocinero = ? WHERE folio = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, estadoo);
                ps.setString(2, empleados_codigo_cocinero);
                ps.setInt(3, folio);
            }
            resultado = ps.executeUpdate();
            if (resultado == 0) {
                error = "el folio no existe";
            }
            return resultado;

        } catch (Exception e) {
            error = e.getMessage();
            System.out.println("Error: " + e.getMessage());
            return resultado;
        }
    }

}

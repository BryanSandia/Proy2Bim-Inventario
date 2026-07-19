/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ASUS
 */
public class MantenimientoBDImpl implements IMantenimientoBD {

    private static final String URL = "jdbc:sqlite:db/lab_inventario.db";

    private Connection obtenerConexion() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        crearTabla(conn); // Verifica o crea la tabla al conectar
        return conn;
    }

    private void crearTabla(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Mantenimiento ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "codigo_activo TEXT NOT NULL, "
                + "fecha TEXT NOT NULL, "
                + "tipo TEXT NOT NULL, "
                + "costo REAL NOT NULL, "
                + "tecnico_responsable TEXT NOT NULL, "
                + "observaciones TEXT"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error al inicializar la tabla Mantenimiento: " + e.getMessage());
        }
    }

    @Override
    public void registrar(String codigoActivo, String fecha, String tipo, double costo, String tecnico) {
        String sql = "INSERT INTO Mantenimiento (codigo_activo, fecha, tipo, costo, tecnico_responsable) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = obtenerConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, codigoActivo);
            pstmt.setString(2, fecha);
            pstmt.setString(3, tipo);
            pstmt.setDouble(4, costo);
            pstmt.setString(5, tecnico);

            pstmt.executeUpdate();
            System.out.println(" Historial de mantenimiento guardado en la base de datos.");

        } catch (SQLException e) {
            System.out.println("Error al insertar mantenimiento en DAO: " + e.getMessage());
        }
    }
}

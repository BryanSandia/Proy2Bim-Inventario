/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Activo;
import Modelo.Hardware;
import Modelo.Mobiliario;
import Modelo.Periferico;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActivoDBImpl implements IActivoBD {

    private static final String URL = "jdbc:sqlite:db/lab_inventario.db";

    private Connection obtenerConexion() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        crearTabla(conn);
        return conn;
    }

    // Crea la tabla si no existe al levantar la conexión
    private void crearTabla(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Activo ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "codigo TEXT UNIQUE NOT NULL, "
                + "nombre TEXT NOT NULL, "
                + "descripcion TEXT, "
                + "costo_adquisicion REAL, "
                + "fecha_adquisicion TEXT, "
                + "estado TEXT DEFAULT 'DISPONIBLE', "
                + "fecha_ultimo_mantenimiento TEXT, "
                + "fecha_proximo_mantenimiento TEXT, "
                + "costo_acumulado_mant REAL DEFAULT 0.0, "
                + "cantidad_mantenimientos INTEGER DEFAULT 0, "
                + "tipo_activo TEXT NOT NULL, "
                + "especificaciones_hardware TEXT, "
                + "capacidad_mobiliario INTEGER, "
                + "id_laboratorio INTEGER"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error al inicializar la tabla Activo: " + e.getMessage());
        }
    }

    @Override
    public void crear(Activo activo) {
        String sql = "INSERT INTO Activo (codigo, nombre, descripcion, costo_adquisicion, fecha_adquisicion, "
                + "estado, tipo_activo, especificaciones_hardware, capacidad_mobiliario, id_laboratorio) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = obtenerConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activo.getCodigo());
            pstmt.setString(2, activo.getNombre());
            pstmt.setString(3, activo.getDescripcion());
            pstmt.setDouble(4, activo.getCostoAdquisicion());
            pstmt.setString(5, activo.getFechaAdquisicion().toString());
            pstmt.setString(6, activo.getEstado());
            pstmt.setString(7, activo.getTipoActivo());

            if (activo instanceof Hardware) {
                pstmt.setString(8, ((Hardware) activo).getEspecificaciones());
                pstmt.setNull(9, Types.INTEGER);
            } else if (activo instanceof Mobiliario) {
                pstmt.setNull(8, Types.VARCHAR);
                pstmt.setInt(9, ((Mobiliario) activo).getCapacidad());
            } else {
                pstmt.setNull(8, Types.VARCHAR);
                pstmt.setNull(9, Types.INTEGER);
            }

            pstmt.setInt(10, 1); // ID de laboratorio temporal para testeo

            pstmt.executeUpdate();
            System.out.println(" Activo insertado polimórficamente usando conexión interna.");

        } catch (SQLException e) {
            System.out.println("Error al insertar activo en DAO: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Activo activo) {
        String sql = "UPDATE Activo SET estado = ?, fecha_ultimo_mantenimiento = ?, "
                + "fecha_proximo_mantenimiento = ?, costo_acumulado_mant = ?, "
                + "cantidad_mantenimientos = ? WHERE codigo = ?";

        try (Connection conn = obtenerConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activo.getEstado());
            pstmt.setString(2, activo.getFechaUltimoMantenimiento() != null ? activo.getFechaUltimoMantenimiento().toString() : null);
            pstmt.setString(3, activo.getFechaProximoMantenimiento() != null ? activo.getFechaProximoMantenimiento().toString() : null);
            pstmt.setDouble(4, activo.getCostoAcumuladoMantenimiento());
            pstmt.setInt(5, activo.getCantidadMantenimientos());
            pstmt.setString(6, activo.getCodigo());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar activo en DAO: " + e.getMessage());
        }
    }

    @Override
    public Activo buscarPorCodigo(String codigo) {
        return null;
    }

    @Override
    public List<Activo> listarTodos() {
        return new ArrayList<>();
    }

    @Override
    public void eliminar(String codigo) {
    }

}

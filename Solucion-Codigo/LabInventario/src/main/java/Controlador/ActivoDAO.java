package Controlador;

import Modelo.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActivoDAO {

    private final Connection conexion;

    public ActivoDAO(Connection conexion) {
        this.conexion = conexion;
        crearTablaSiNoExiste();
    }

    public void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS Activo ("
                + "codigo TEXT PRIMARY KEY, "
                + "tipo_activo TEXT NOT NULL, "
                + "nombre TEXT NOT NULL, "
                + "descripcion TEXT, "
                + "costo_adquisicion REAL NOT NULL, "
                + "fecha_adquisicion TEXT NOT NULL, "
                + "estado TEXT DEFAULT 'DISPONIBLE', "
                + "id_laboratorio TEXT DEFAULT 'LAB-DEFAULT', "
                + "frecuencia_mantenimiento INTEGER, "
                + "ultimo_encargado_mant TEXT, "
                + "observaciones_mant TEXT, "
                + "numero_equipos REAL, "
                + "fecha_renovacion TEXT"
                + ");";
        try (java.sql.Statement stmt = conexion.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println("Error al verificar tabla: " + e.getMessage());
        }
    }

    // Método para guardar Hardware
    public void crear(Modelo.Hardware hw, String idLab, String idUsuario) throws Exception {
        String sql = "INSERT INTO Activo (codigo, tipo_activo, nombre, descripcion, costo_adquisicion, fecha_adquisicion, estado, id_laboratorio, frecuencia_mantenimiento) VALUES (?, 'HARDWARE', ?, ?, ?, ?, 'DISPONIBLE', ?, ?)";

        java.sql.PreparedStatement pstmt = conexion.prepareStatement(sql);
        pstmt.setString(1, hw.getCodigo());
        pstmt.setString(2, hw.getNombre());
        pstmt.setString(3, hw.getDescripcion());
        pstmt.setDouble(4, hw.getCostoAdquisicion());
        pstmt.setString(5, hw.getFechaAdquisicion().toString());
        pstmt.setString(6, idLab);
        pstmt.setInt(7, hw.getFrecuenciaMantenimiento());

        pstmt.executeUpdate();
        pstmt.close();
    }

    // Método para guardar Software, Licencias y Suscripciones en la misma tabla
    public void crear(Modelo.Software sw, String idLab, String idUsuario) throws Exception {
        String sql = "INSERT INTO Activo (codigo, tipo_activo, nombre, descripcion, costo_adquisicion, fecha_adquisicion, estado, id_laboratorio, numero_equipos, fecha_renovacion) VALUES (?, ?, ?, ?, ?, ?, 'DISPONIBLE', ?, ?, ?)";

        java.sql.PreparedStatement pstmt = conexion.prepareStatement(sql);
        pstmt.setString(1, sw.getCodigo());

        // Determinamos el tipo exacto
        String tipo = "SOFTWARE";
        if (sw instanceof Modelo.Licencia) {
            tipo = "LICENCIA";
        } else if (sw instanceof Modelo.Subscripcion) {
            tipo = "SUBSCRIPCION";
        }

        pstmt.setString(2, tipo);
        pstmt.setString(3, sw.getNombre());
        pstmt.setString(4, sw.getDescripcion());
        pstmt.setDouble(5, sw.getCostoAdquisicion());
        pstmt.setString(6, sw.getFechaAdquisicion().toString());
        pstmt.setString(7, idLab);
        pstmt.setDouble(8, sw.getNumeroEquipos());
        pstmt.setString(9, sw.getFechaRenovacion().toString());

        pstmt.executeUpdate();
        pstmt.close();
    }

    public void actualizar(Activo activo, String codigoLab, String cedDocente) {
        // CORRECCIÓN: Nombres de columnas exactamente iguales a la tabla
        String sql = "UPDATE Activo SET "
                + "nombre=?, descripcion=?, tipo_activo=?, costo_adquisicion=?, fecha_adquisicion=?, "
                + "estado=?, frecuencia_mantenimiento=?, fecha_renovacion=?, numero_equipos=?, "
                + "id_laboratorio=? "
                + "WHERE codigo=?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, activo.getNombre());
            ps.setString(2, activo.getDescripcion());
            ps.setDouble(4, activo.getCostoAdquisicion());
            ps.setString(5, activo.getFechaAdquisicion().toString());
            ps.setString(6, activo.getEstado());
            ps.setString(10, codigoLab);
            ps.setString(11, activo.getCodigo());

            if (activo instanceof Hardware) {
                Hardware hw = (Hardware) activo;
                ps.setString(3, "HARDWARE");
                ps.setInt(7, hw.getFrecuenciaMantenimiento());
                ps.setNull(8, Types.VARCHAR);
                ps.setNull(9, Types.DOUBLE);
            } else if (activo instanceof Software) {
                Software sw = (Software) activo;
                ps.setString(3, sw.getTipoActivo());
                ps.setNull(7, Types.INTEGER);
                ps.setString(8, sw.getFechaRenovacion().toString());
                ps.setDouble(9, sw.getNumeroEquipos());
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar activo: " + e.getMessage());
        }
    }

    public void eliminar(String codigo) {
        String sql = "DELETE FROM Activo WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar activo: " + e.getMessage());
        }
    }

    public Activo buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM Activo WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearDesdeResultSet(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar activo: " + e.getMessage());
        }
    }

    public List<Activo> listarTodos() {
        List<Activo> lista = new ArrayList<>();
        // CORRECCIÓN: usamos tipo_activo en lugar de tipo
        String sql = "SELECT * FROM Activo ORDER BY tipo_activo, nombre";

        try (Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar activos: " + e.getMessage());
        }
        return lista;
    }

    public Laboratorio obtenerLaboratorioDeActivo(String codigoActivo) {
        // CORRECCIÓN: usamos id_laboratorio porque ced_docente ya no está en el diseño de tabla única
        String sql = "SELECT id_laboratorio FROM Activo WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigoActivo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Laboratorio(
                            rs.getString("id_laboratorio"),
                            "Sin Asignar",
                            0
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener laboratorio: " + e.getMessage());
        }
        return null;
    }

    private Activo mapearDesdeResultSet(ResultSet rs) throws SQLException {
        // CORRECCIÓN: Nombres de columnas mapeados exactamente como están en la tabla
        String tipo = rs.getString("tipo_activo");
        String codigo = rs.getString("codigo");
        String nombre = rs.getString("nombre");
        String desc = rs.getString("descripcion");
        double costo = rs.getDouble("costo_adquisicion");

        // SQLite guarda las fechas como texto, así que getString es lo más seguro
        LocalDate fechaAdq = LocalDate.parse(rs.getString("fecha_adquisicion"));
        String estado = rs.getString("estado");

        switch (tipo) {
            case "HARDWARE":
                int freq = rs.getInt("frecuencia_mantenimiento");
                Hardware hw = new Hardware(codigo, nombre, desc, costo, fechaAdq, freq);
                hw.setEstado(estado);
                return hw;

            case "LICENCIA":
            case "SUBSCRIPCION":
            case "SOFTWARE":
                String fechaRenStr = rs.getString("fecha_renovacion");
                LocalDate fechaRen = (fechaRenStr != null) ? LocalDate.parse(fechaRenStr) : fechaAdq.plusYears(1);
                double numEq = rs.getDouble("numero_equipos");

                if ("SUBSCRIPCION".equals(tipo)) {
                    Subscripcion sub = new Subscripcion("ANUAL", numEq, fechaRen, codigo, nombre, desc, costo, fechaAdq);
                    sub.setEstado(estado);
                    return sub;
                } else if ("LICENCIA".equals(tipo)) {
                    Licencia lic = new Licencia("COD-" + codigo, numEq, fechaRen, codigo, nombre, desc, costo, fechaAdq);
                    lic.setEstado(estado);
                    return lic;
                } else {
                    Software sw = new Software(numEq, fechaRen, codigo, nombre, desc, costo, fechaAdq);
                    sw.setEstado(estado);
                    return sw;
                }

            default:
                return null;
        }
    }
}

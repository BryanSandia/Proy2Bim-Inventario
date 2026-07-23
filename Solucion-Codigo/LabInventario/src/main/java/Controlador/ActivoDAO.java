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
    }

    public void crear(Activo activo, String codigoLab, String cedDocente) {
        String sql = """
            INSERT INTO activos 
            (codigo, nombre, descripcion, tipo, costo_adquisicion, fecha_adquisicion, 
             estado, frecuencia_mant, fecha_renovacion, num_equipos, codigo_lab, ced_docente)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            mapearAStatement(ps, activo, codigoLab, cedDocente);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear activo: " + e.getMessage());
        }
    }

    public void actualizar(Activo activo, String codigoLab, String cedDocente) {
        String sql = """
            UPDATE activos SET 
                nombre=?, descripcion=?, tipo=?, costo_adquisicion=?, fecha_adquisicion=?,
                estado=?, frecuencia_mant=?, fecha_renovacion=?, num_equipos=?, 
                codigo_lab=?, ced_docente=?
            WHERE codigo=?
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            mapearAStatement(ps, activo, codigoLab, cedDocente);
            ps.setString(12, activo.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar activo: " + e.getMessage());
        }
    }

    public void eliminar(String codigo) {
        String sql = "DELETE FROM activos WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar activo: " + e.getMessage());
        }
    }

    public Activo buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM activos WHERE codigo = ?";
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
        String sql = "SELECT * FROM activos ORDER BY tipo, nombre";

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
        String sql = "SELECT codigo_lab, ced_docente FROM activos WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigoActivo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Laboratorio(
                            rs.getString("codigo_lab"),
                            rs.getString("ced_docente"),
                            0
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener laboratorio: " + e.getMessage());
        }
        return null;
    }

    private void mapearAStatement(PreparedStatement ps, Activo activo, String codigoLab, String cedDocente) throws SQLException {
        ps.setString(1, activo.getCodigo());
        ps.setString(2, activo.getNombre());
        ps.setString(3, activo.getDescripcion());
        ps.setDouble(5, activo.getCostoAdquisicion());
        ps.setDate(6, java.sql.Date.valueOf(activo.getFechaAdquisicion()));
        ps.setString(7, activo.getEstado());

        if (activo instanceof Hardware) {
            Hardware hw = (Hardware) activo;
            ps.setString(4, "HARDWARE");
            ps.setInt(8, hw.getFrecuenciaMantenimiento());
            ps.setNull(9, Types.DATE);
            ps.setNull(10, Types.DOUBLE);
        } else if (activo instanceof Software) {
            Software sw = (Software) activo;
            ps.setString(4, sw.getTipoActivo());
            ps.setNull(8, Types.INTEGER);
            ps.setDate(9, java.sql.Date.valueOf(sw.getFechaRenovacion()));
            ps.setDouble(10, sw.getNumeroEquipos());
        }

        ps.setString(11, codigoLab);
        ps.setString(12, cedDocente);
    }

    private Activo mapearDesdeResultSet(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        String codigo = rs.getString("codigo");
        String nombre = rs.getString("nombre");
        String desc = rs.getString("descripcion");
        double costo = rs.getDouble("costo_adquisicion");
        LocalDate fechaAdq = rs.getDate("fecha_adquisicion").toLocalDate();
        String estado = rs.getString("estado");

        switch (tipo) {
            case "HARDWARE":
                int freq = rs.getInt("frecuencia_mant");
                Hardware hw = new Hardware(codigo, nombre, desc, costo, fechaAdq, freq);
                hw.setEstado(estado);
                return hw;

            case "LICENCIA":
            case "SUBSCRIPCION":
                LocalDate fechaRen = rs.getDate("fecha_renovacion").toLocalDate();
                double numEq = rs.getDouble("num_equipos");

                if ("SUBSCRIPCION".equals(tipo)) {
                    Subscripcion sub = new Subscripcion("ANUAL", numEq, fechaRen, codigo, nombre, desc, costo, fechaAdq);
                    sub.setEstado(estado);
                    return sub;
                } else {
                    Licencia lic = new Licencia("COD-" + codigo, numEq, fechaRen, codigo, nombre, desc, costo, fechaAdq);
                    lic.setEstado(estado);
                    return lic;
                }

            default:
                LocalDate fechaRenDef = rs.getDate("fecha_renovacion") != null
                        ? rs.getDate("fecha_renovacion").toLocalDate() : fechaAdq.plusYears(1);
                double numEqDef = rs.getDouble("num_equipos");
                Software sw = new Software(numEqDef, fechaRenDef, codigo, nombre, desc, costo, fechaAdq);
                sw.setEstado(estado);
                return sw;
        }
    }
}

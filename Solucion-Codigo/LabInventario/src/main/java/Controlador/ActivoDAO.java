package Controlador;

import Modelo.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActivoDAO implements IActivoDAO {
    private final Connection conexion;

    public ActivoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void crear(Activo activo) {
        String sql = """
            INSERT INTO activos 
            (codigo, nombre, descripcion, tipo, costo_adquisicion, fecha_adquisicion, 
             estado, frecuencia_mant, fecha_renovacion, num_equipos, codigo_lab, ced_docente)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            mapearAStatement(ps, activo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear activo: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Activo activo) {
        String sql = """
            UPDATE activos SET 
                nombre=?, descripcion=?, tipo=?, costo_adquisicion=?, fecha_adquisicion=?,
                estado=?, frecuencia_mant=?, fecha_renovacion=?, num_equipos=?, 
                codigo_lab=?, ced_docente=?
            WHERE codigo=?
        """;
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            mapearAStatement(ps, activo);
            ps.setString(12, activo.getCodigo()); // WHERE clause
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar activo: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(String codigo) {
        String sql = "DELETE FROM activos WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar activo: " + e.getMessage());
        }
    }

    @Override
    public Activo buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM activos WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapearDesdeResultSet(rs) : null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar activo: " + e.getMessage());
        }
    }

    @Override
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

    @Override
    public Laboratorio obtenerLaboratorioDeActivo(String codigoActivo) {
        String sql = "SELECT codigo_lab, ced_docente FROM activos WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigoActivo);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                // Crea un objeto Laboratorio mínimo solo con los datos necesarios
                return new Laboratorio(
                    rs.getString("codigo_lab"),
                    rs.getString("ced_docente"),
                    0 // Capacidad no necesaria para validación de permisos
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener laboratorio: " + e.getMessage());
        }
        return null;
    }

    // === MÉTODOS PRIVADOS DE MAPEO (El corazón del DAO) ===

    private void mapearAStatement(PreparedStatement ps, Activo activo) throws SQLException {
        ps.setString(1, activo.getCodigo());
        ps.setString(2, activo.getNombre());
        ps.setString(3, activo.getDescripcion());
        ps.setDouble(5, activo.getCostoAdquisicion());
        ps.setDate(6, Date.valueOf(activo.getFechaAdquisicion()));
        ps.setString(7, activo.getEstado());

        // Campos específicos según tipo (null si no aplica)
        if (activo instanceof Hardware hw) {
            ps.setString(4, "HARDWARE");
            ps.setInt(8, hw.getFrecuenciaMantenimiento());
            ps.setNull(9, Types.DATE);
            ps.setNull(10, Types.DOUBLE);
        } else if (activo instanceof Software sw) {
            ps.setString(4, sw.getTipoActivo()); // SOFTWARE, LICENCIA, SUBSCRIPCION
            ps.setNull(8, Types.INTEGER);
            ps.setDate(9, Date.valueOf(sw.getFechaRenovacion()));
            ps.setDouble(10, sw.getNumeroEquipos());
        }

        // Campos de relación (asumimos que el Controlador ya seteó estos valores en el modelo)
        // NOTA: En un diseño más puro, esto vendría de un servicio, pero para el DAO simple:
        ps.setString(11, "LAB-DEFAULT"); // Placeholder - Deberías pasar el lab desde el controlador
        ps.setString(12, "DOC-DEFAULT"); // Placeholder
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
                
            default: // SOFTWARE genérico
                LocalDate fechaRenDef = rs.getDate("fecha_renovacion") != null ? 
                    rs.getDate("fecha_renovacion").toLocalDate() : fechaAdq.plusYears(1);
                double numEqDef = rs.getDouble("num_equipos");
                Software sw = new Software(numEqDef, fechaRenDef, codigo, nombre, desc, costo, fechaAdq);
                sw.setEstado(estado);
                return sw;
        }
    }
}
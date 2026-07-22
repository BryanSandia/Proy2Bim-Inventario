package Controlador;

import java.util.List;

import Controlador.Servicios.ServicioAutorizacion;
import Controlador.Servicios.ServicioHardware;
import Modelo.Docente;
import Modelo.Hardware;
import Modelo.Laboratorio;
import Modelo.NivelAcceso;
import Modelo.Persona;

public class ControladorHardware {
    private final ServicioAutorizacion auth;
    private final ServicioHardware servicioHW;
    private final IActivoDAO activoDAO;

    public ControladorHardware(ServicioAutorizacion auth, 
                               ServicioHardware servicioHW
                               ,IActivoDAO activoDAO) {
        this.auth = auth;
        this.servicioHW = servicioHW;
        this.activoDAO = activoDAO;
    }

 
    public String registrarMantenimiento(Persona usuario, Hardware hw, 
                                         String encargado, double costo, String obs) {
        Laboratorio lab = activoDAO.obtenerLaboratorioDeActivo(hw.getCodigo());
        
        // VALIDACIÓN DE PERMISOS PRIMERO
        if (!auth.puedeEditarEnLaboratorio(usuario, lab)) {
            return "🚫 No tienes permisos para registrar mantenimiento en este laboratorio.";
        }

        servicioHW.procesarMantenimiento(hw, encargado, costo, obs);
        activoDAO.actualizar(hw); // Guarda cambios en la tabla única
        return "✅ Mantenimiento registrado exitosamente.";
    }


    public String eliminarActivo(Persona usuario, String codigoActivo) {
        Hardware hw = (Hardware) activoDAO.buscarPorCodigo(codigoActivo);
        if (hw == null) return "❌ Activo no encontrado";
        
        Laboratorio lab = activoDAO.obtenerLaboratorioDeActivo(codigoActivo);
        if (!auth.puedeEditarEnLaboratorio(usuario, lab)) {
            return "🚫 No puedes eliminar este activo.";
        }

        activoDAO.eliminar(codigoActivo);
        return "✅ Activo eliminado correctamente.";
    }

    /**
     * Obtiene lista filtrada según permisos del usuario.
     * - Admin: Ve TODO
     * - Docente: Solo sus laboratorios asignados
     * - Usuario: Solo lectura de todos
     */
    public List<Hardware> obtenerHardwareVisible(Persona usuario) {
        //Obtener TODOS los activos y filtrar solo Hardware
        List<Hardware> todos = activoDAO.listarTodos().stream()
            .filter(a -> a instanceof Hardware)
            .map(a -> (Hardware) a)
            .toList();
        
        //Aplicar lógica de permisos
        if (usuario.getNivelAcceso() == NivelAcceso.ADMIN) {
            return todos;
        } 
        
        if (usuario.getNivelAcceso() == NivelAcceso.DOCENTE) {
            Docente doc = (Docente) usuario;
            return todos.stream()
                .filter(h -> doc.tieneAccesoALaboratorio(
                    activoDAO.obtenerLaboratorioDeActivo(h.getCodigo()).getCodigoLab()))
                .toList();
        }
        
        return todos;
    }
    }
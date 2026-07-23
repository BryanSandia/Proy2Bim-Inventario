package Controlador;

import java.util.List;

import Controlador.Servicios.ServicioAutorizacion;
import Controlador.Servicios.ServicioSoftware;
import Modelo.Activo;
import Modelo.Docente;
import Modelo.Laboratorio;
import Modelo.NivelAcceso;
import Modelo.Persona;
import Modelo.Software;

public class ControladorSoftware {

    private final ServicioAutorizacion auth;
    private final ServicioSoftware servicioSW;
    private final ActivoDAO activoDAO;

    public ControladorSoftware(ServicioAutorizacion auth,
            ServicioSoftware servicioSW,
            ActivoDAO activoDAO) {
        this.auth = auth;
        this.servicioSW = servicioSW;
        this.activoDAO = activoDAO;
    }

    /**
     * Renueva software. Solo quienes tengan permiso de edición en el lab
     * asociado.
     */
    public String renovarSoftware(Persona usuario, Software sw, double costo, int cantidadARenovar) {
        Laboratorio lab = activoDAO.obtenerLaboratorioDeActivo(sw.getCodigo());

        // ?VALIDACIÓN DE PERMISOS PRIMERO
        if (!auth.puedeEditarEnLaboratorio(usuario, lab)) {
            return "No autorizado para renovar este software.";
        }

        try {
            servicioSW.procesarRenovacion(sw, costo, cantidadARenovar);
            // Pasamos los datos reales del laboratorio a la base de datos
            activoDAO.actualizar(sw, lab.getCodigoLab(), lab.getCEDdocenteEncargado());
            return "Software renovado hasta: " + sw.getFechaRenovacion();
        } catch (IllegalArgumentException e) {
            return "Error en renovación: " + e.getMessage();
        }
    }

    /**
     * Verifica estado de vigencia. Todos los roles pueden VER, solo algunos
     * ACTUAR.
     */
    public String verificarEstadoSoftware(Persona usuario, Software sw) {
        // Validar que pueda ver este software
        Laboratorio lab = activoDAO.obtenerLaboratorioDeActivo(sw.getCodigo());
        if (!auth.puedeVerLaboratorio(usuario, lab)) {
            return " Sin acceso a esta información.";
        }

        servicioSW.verificarEstado(sw);
        return servicioSW.listarInfoSoftware(sw);
    }

    /**
     * Lista software accesible según rol (mismo patrón que Hardware).
     */
    public List<Software> obtenerSoftwareVisible(Persona usuario) {

        List<Activo> todosActivos = activoDAO.listarTodos();

        //Filtrar solo los que son Software
        List<Software> todos = todosActivos.stream()
                .filter(a -> a instanceof Software)
                .map(a -> (Software) a)
                .toList();

        //Aplicar lógica de permisos
        if (usuario.getNivelAcceso() == NivelAcceso.ADMIN) {
            return todos;
        }

        if (usuario.getNivelAcceso() == NivelAcceso.DOCENTE) {
            Docente doc = (Docente) usuario;
            return todos.stream()
                    .filter(s -> doc.tieneAccesoALaboratorio(
                    activoDAO.obtenerLaboratorioDeActivo(s.getCodigo()).getCodigoLab()))
                    .toList();
        }

        return todos; // USUARIO: Solo lectura
    }
}

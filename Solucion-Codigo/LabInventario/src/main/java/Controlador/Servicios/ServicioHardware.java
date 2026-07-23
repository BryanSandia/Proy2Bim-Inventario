package Controlador.Servicios;

import java.time.LocalDate;
import Modelo.Hardware;
import Modelo.Mantenimiento;

public class ServicioHardware {

    
    public static final String ESTADO_DISPONIBLE = "DISPONIBLE";
    public static final String ESTADO_REQUIERE_MANT = "REQUIERE MANTENIMIENTO";
    public static final String ESTADO_DE_BAJA = "DE BAJA";

    public void verificarEstado(Hardware hw) {
        if (ESTADO_DE_BAJA.equals(hw.getEstado())) {
            return; // Si el equipo está dado de baja, no hacemos transiciones
        }

        LocalDate hoy = LocalDate.now();
        // Si la fecha ya pasó o es hoy, requiere mantenimiento
        if (hw.getFechaProximoMantenimiento() != null
                && (hoy.isAfter(hw.getFechaProximoMantenimiento()) || hoy.isEqual(hw.getFechaProximoMantenimiento()))) {
            ActualizarEstado(hw, ESTADO_REQUIERE_MANT);
        }
    }

    public String obtenerEstadoActual(Hardware hw) {
        return hw.getEstado();
    }

    public void procesarMantenimiento(Hardware hw, String encargado, double costo, String obs) {
        Mantenimiento m = new Mantenimiento(costo, encargado, obs, hw.getCodigo(), LocalDate.now());
        hw.RegistrarMantenimiento(m);

        hw.setCostoAcumuladoMantenimiento(hw.getCostoAcumuladoMantenimiento() + costo);
        hw.setFechaUltimoMantenimiento(LocalDate.now());
        hw.setFechaProximoMantenimiento(LocalDate.now().plusMonths(hw.getFrecuenciaMantenimiento()));
        hw.setCantidadMantenimientos(hw.getCantidadMantenimientos() + 1); // Bug corregido

        // Después de mantenerlo, vuelve a estar disponible
        ActualizarEstado(hw, ESTADO_DISPONIBLE);
    }

    public void ActualizarEstado(Hardware hw, String nuevoEstado) {
        if (!nuevoEstado.equals(ESTADO_DISPONIBLE)
                && !nuevoEstado.equals(ESTADO_REQUIERE_MANT)
                && !nuevoEstado.equals(ESTADO_DE_BAJA)) {
            throw new IllegalArgumentException("Opción de estado inválida para Hardware.");
        }
        hw.setEstado(nuevoEstado);
    }

    public String listarMantenimiento(Hardware hw) {
        if (hw.getMantenimiento() == null || hw.getMantenimiento().isEmpty()) {
            return "No hay historial de mantenimientos registrado.";
        }
        StringBuilder sb = new StringBuilder("Historial de Mantenimientos:\n");
        for (Mantenimiento m : hw.getMantenimiento()) {
            sb.append(m.toString()).append("\n");
        }
        return sb.toString();
    }

    public String listarInfoHardware(Hardware hw) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Hardware: %s (%s)\n", hw.getNombre(), hw.getCodigo()));
        sb.append(String.format("Tipo: %s\n", hw.getTipoActivo()));
        sb.append(String.format("Descripción: %s\n", hw.getDescripcion()));
        sb.append(String.format("Costo Adquisición: $%.2f\n", hw.getCostoAdquisicion()));
        sb.append(String.format("Fecha Adquisición: %s\n", hw.getFechaAdquisicion()));
        sb.append(String.format("Frecuencia Mantenimiento: cada %d meses\n", hw.getFrecuenciaMantenimiento()));
        sb.append(String.format("Próximo Mantenimiento: %s\n", hw.getFechaProximoMantenimiento()));
        sb.append(String.format("Estado Actual: %s\n", hw.getEstado()));
        sb.append(String.format("Mantenimientos Realizados: %d\n", hw.getCantidadMantenimientos()));
        sb.append(String.format("Costo Acumulado: $%.2f\n", hw.getCostoAcumuladoMantenimiento()));
        return sb.toString();
    }
}

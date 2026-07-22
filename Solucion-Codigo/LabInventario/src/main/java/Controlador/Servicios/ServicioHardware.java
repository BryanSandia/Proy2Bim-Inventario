package Controlador.Servicios;

import java.time.LocalDate;

import Modelo.Hardware;
import Modelo.Mantenimiento;

public class ServicioHardware {

    public void verificarEstado(Hardware hw) {
        LocalDate hoy = LocalDate.now();
        if (hw.getFechaProximoMantenimiento() != null && hoy.isAfter(hw.getFechaProximoMantenimiento())) {
            ActualizarEstado(hw, 2); // EN MANTENIMIENTO
        } else if ("EN MANTENIMIENTO".equals(hw.getEstado()) 
                   && hw.getFechaProximoMantenimiento() != null 
                   && hoy.isBefore(hw.getFechaProximoMantenimiento())) {
            ActualizarEstado(hw, 1); // DISPONIBLE
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
    }

    public String listarMantenimiento(Hardware hw){
        if (hw.getMantenimiento() == null || hw.getMantenimiento().isEmpty()) {
            return "No hay historial de mantenimientos registrado.";
        }
        StringBuilder sb = new StringBuilder("Historial de Mantenimientos:\n");
        for (Mantenimiento m : hw.getMantenimiento()) {
            sb.append(m.toString());
        }
        return sb.toString();
    }

    public String listarInfoHardware(Hardware hw) {
        StringBuilder sb = new StringBuilder();
    
        // Información básica heredada de Activo
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
    

    public void ActualizarEstado(Hardware hw, int opcion) {
        switch (opcion) {
            case 1:
                hw.setEstado("DISPONIBLE");
                break;
            case 2:
                hw.setEstado("EN MANTENIMIENTO");
                break; 
            case 3:
                hw.setEstado("DE BAJA");
                break; 
            default:        
                System.out.println("Opción inválida. No se realizó ningún cambio de estado.");
                break;
        }
    }
}
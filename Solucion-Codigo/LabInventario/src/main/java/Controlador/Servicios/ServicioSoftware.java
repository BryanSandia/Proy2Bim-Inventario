package Controlador.Servicios;

import java.time.LocalDate;

import Modelo.Licencia;
import Modelo.Software;
import Modelo.Subscripcion;

public class ServicioSoftware {

    public void verificarEstado(Software sw) {
        
        if (sw.getEstado().equals("DE BAJA")){
            return;
        }

        LocalDate hoy = LocalDate.now();
        // Si la fecha de renovación ya pasó, cambia a "Necesita Renovación"
        if (sw.getFechaRenovacion() != null && hoy.isAfter(sw.getFechaRenovacion())) {
            ActualizarEstado(sw, 2); 
        } 
    }

    public String obtenerEstadoActual(Software sw) {
        return sw.getEstado();
    }
    

    public void procesarRenovacion(Software sw, double costo, int cantidadARenovar) {
        LocalDate hoy = LocalDate.now();
        LocalDate nuevaFecha;

        // Lógica específica para Subscripciones
        if (sw instanceof Subscripcion sub) {
            switch (sub.getTipoRenovacion()) {
                case "MENSUAL":
                    nuevaFecha = hoy.plusMonths(cantidadARenovar);
                    break;
                case "TRIMESTRAL":
                    nuevaFecha = hoy.plusMonths(cantidadARenovar * 3);
                    break;
                case "ANUAL":
                    nuevaFecha = hoy.plusYears(cantidadARenovar);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de suscripción no válido: " + sub.getTipoRenovacion());
            }
            sub.renovar(nuevaFecha);
        } 
        // Lógica para Licencias estándar (siempre por años)
        else if (sw instanceof Licencia || sw.getClass().equals(Software.class)) {
            nuevaFecha = hoy.plusYears(cantidadARenovar);
            sw.setFechaRenovacion(nuevaFecha);
        } else {
            throw new IllegalArgumentException("Tipo de software no soportado para renovación");
        }

        // Marcar como Activo tras renovación exitosa
        ActualizarEstado(sw, 1); 
        
        System.out.println("✅ Renovación registrada. Nueva fecha: " + nuevaFecha);
    }

    public String listarInfoSoftware(Software sw){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Software: %s (%s)\n", sw.getNombre(), sw.getCodigo()));
        sb.append(String.format("Tipo: %s\n", sw.getTipoActivo()));
        sb.append(String.format("Equipos permitidos: %.0f\n", sw.getNumeroEquipos()));
        sb.append(String.format("Fecha Renovación: %s\n", sw.getFechaRenovacion()));
        sb.append(String.format("Estado Actual: %s\n", sw.getEstado()));
        
        if (sw instanceof Subscripcion) {
            sb.append(String.format("Tipo Suscripción: %s\n", ((Subscripcion)sw).getTipoRenovacion()));
        } else if (sw instanceof Licencia) {
            sb.append(String.format("Código Licencia: %s\n", ((Licencia)sw).getCodigoLicencia()));
        }
        
        return sb.toString();
    }
    
    public void ActualizarEstado(Software sw, int opcion) {
        switch (opcion) {
            case 1:
                sw.setEstado("ACTIVO");
                break;
            case 2:
                sw.setEstado("RENOVAR!");
                break; 
            case 3:
                sw.setEstado("DE BAJA");
                break; 
            default:        
                System.out.println("Opción inválida. No se realizó ningún cambio de estado.");
                break;
        }
    }
}
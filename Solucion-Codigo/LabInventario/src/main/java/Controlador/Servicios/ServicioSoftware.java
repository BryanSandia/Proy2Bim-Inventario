package Controlador.Servicios;

import java.time.LocalDate;
import Modelo.Licencia;
import Modelo.Software;
import Modelo.Subscripcion;

public class ServicioSoftware {

    public static final String ESTADO_ACTIVO = "ACTIVO";
    public static final String ESTADO_RENOVAR = "RENOVAR!";
    public static final String ESTADO_DE_BAJA = "DE BAJA";

    public void verificarEstado(Software sw) {
        if (ESTADO_DE_BAJA.equals(sw.getEstado())) {
            return;
        }

        LocalDate hoy = LocalDate.now();
        if (sw.getFechaRenovacion() != null && hoy.isAfter(sw.getFechaRenovacion())) {
            ActualizarEstado(sw, ESTADO_RENOVAR);
        }
    }

    public String obtenerEstadoActual(Software sw) {
        return sw.getEstado();
    }

    public void procesarRenovacion(Software sw, double costo, int cantidadARenovar) {
        LocalDate hoy = LocalDate.now();
        LocalDate nuevaFecha;

        if (sw instanceof Subscripcion sub) {
            switch (sub.getTipoRenovacion().toUpperCase()) {
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
        } else if (sw instanceof Licencia || sw.getClass().equals(Software.class)) {
            nuevaFecha = hoy.plusYears(cantidadARenovar);
            sw.setFechaRenovacion(nuevaFecha);
        } else {
            throw new IllegalArgumentException("Tipo de software no soportado para renovación");
        }

        // Marcar como Activo tras renovación exitosa
        ActualizarEstado(sw, ESTADO_ACTIVO);
    }

    public void ActualizarEstado(Software sw, String nuevoEstado) {
        if (!nuevoEstado.equals(ESTADO_ACTIVO)
                && !nuevoEstado.equals(ESTADO_RENOVAR)
                && !nuevoEstado.equals(ESTADO_DE_BAJA)) {
            throw new IllegalArgumentException("Opción de estado inválida para Software.");
        }
        sw.setEstado(nuevoEstado);
    }

    public String listarInfoSoftware(Software sw) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Software: %s (%s)\n", sw.getNombre(), sw.getCodigo()));
        sb.append(String.format("Tipo: %s\n", sw.getTipoActivo()));
        sb.append(String.format("Equipos permitidos: %.0f\n", sw.getNumeroEquipos()));
        sb.append(String.format("Fecha Renovación: %s\n", sw.getFechaRenovacion()));
        sb.append(String.format("Estado Actual: %s\n", sw.getEstado()));

        if (sw instanceof Subscripcion) {
            sb.append(String.format("Tipo Suscripción: %s\n", ((Subscripcion) sw).getTipoRenovacion()));
        } else if (sw instanceof Licencia) {
            sb.append(String.format("Código Licencia: %s\n", ((Licencia) sw).getCodigoLicencia()));
        }

        return sb.toString();
    }
}

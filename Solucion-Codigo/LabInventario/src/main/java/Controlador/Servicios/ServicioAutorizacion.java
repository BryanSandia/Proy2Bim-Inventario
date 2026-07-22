package Controlador.Servicios;

import Modelo.Docente;
import Modelo.Laboratorio;
import Modelo.NivelAcceso;
import Modelo.Persona;

public class ServicioAutorizacion {

    /**
     * Verifica si una persona puede VER un laboratorio.
     */
    public boolean puedeVerLaboratorio(Persona persona, Laboratorio lab) {
        switch (persona.getNivelAcceso()) {
            case ADMIN:
                return true;
            case DOCENTE:
                return ((Docente) persona).tieneAccesoALaboratorio(lab.getCodigoLab());
            case USUARIO:
                return true; // Los usuarios pueden ver todos los labs
            default:
                return false;
        }
    }

    /**
     * Verifica si una persona puede EDITAR/ELIMINAR activos en un lab.
     */
    public boolean puedeEditarEnLaboratorio(Persona persona, Laboratorio lab) {
        if (persona.getNivelAcceso() == NivelAcceso.ADMIN) return true;
        
        if (persona.getNivelAcceso() == NivelAcceso.DOCENTE) {
            return ((Docente) persona).tieneAccesoALaboratorio(lab.getCodigoLab());
        }
        
        return false; // Usuarios nunca pueden editar
    }
}
package Controlador.Servicios;

import java.util.List;

import Modelo.Activo;
import Modelo.Hardware;
import Modelo.Laboratorio;

public class ServicioLaboratorio {

    public boolean agregarActivo(Laboratorio lab, Activo activo) {
        // 1. Validar duplicados
        boolean existe = lab.getActivos().stream()
            .anyMatch(a -> a.getCodigo().equals(activo.getCodigo()));
            
        if (existe) {
            System.out.println("ERROR: El activo " + activo.getCodigo() + " ya existe.");
            return false;
        }

        // 2. Validar capacidad física (solo Hardware cuenta)
        long hwCount = lab.getActivos().stream()
            .filter(a -> a instanceof Hardware)
            .count();
            
        if (activo instanceof Hardware && hwCount >= lab.getCapacidadMaximaHardware()) {
            System.out.println("ERROR: Laboratorio lleno. Máx: " 
                + lab.getCapacidadMaximaHardware() + " equipos.");
            return false;
        }

        // 3. Si pasa todas las validaciones, modifica el modelo
        lab.getActivos().add(activo);
        System.out.println("✅ Activo " + activo.getCodigo() + " agregado.");
        return true;
    }

    public List<Hardware> obtenerHardware(Laboratorio lab) {
        return lab.getActivos().stream()
            .filter(a -> a instanceof Hardware)
            .map(a -> (Hardware) a)
            .toList();
    }
}
package Controlador.Calculadora;
import java.util.List;

import Modelo.Hardware;
import Modelo.Mantenimiento;
public class CalculadoraHardware implements ICalculadoraHardware {

    @Override
    public double calcular(Hardware hw) {
        // 1. Obtener el costo base dinámico (Último mant. o Adquisición)
        double costoBase = obtenerCostoBase(hw);
        // 2. calcular el costo
        return costoBase * 0.08; // 8% del precio base
    }

 
    /**
     * Determina el costo base según el historial.
     * Si hay mantenimientos previos, usa el costo del último.
     * Si es nuevo, usa el costo de adquisición original.
     */
    private double obtenerCostoBase(Hardware hw) {
        List<Mantenimiento> historial = hw.getMantenimiento();
        
        if (historial != null && !historial.isEmpty()) {
            // Retorna el costo del ÚLTIMO mantenimiento registrado
            Mantenimiento ultimo = historial.get(historial.size() - 1);
            return ultimo.getCosto();
        }
        
        // Si nunca se ha mantenido, retorna el precio de compra
        return hw.getCostoAdquisicion();
    }


}
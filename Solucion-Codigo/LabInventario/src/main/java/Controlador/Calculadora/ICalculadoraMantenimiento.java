
package Controlador.Calculadora;

import Modelo.Activo;

// == Interfaz para calcular costos y frecuencias de mantenimiento == //

public interface ICalculadoraMantenimiento {
    
    /**
     * Calcula el costo de mantenimiento para un activo específico.
     * 
     * @param activo el activo a evaluar
     * @return costo estimado del mantenimiento
     */

    double calcular(Activo activo);
    
    /**
     * Obtiene la frecuencia de mantenimiento en meses para un activo.
     * 
     * @param activo el activo a evaluar
     * @return número de meses entre cada mantenimiento
     */
    int obtenerFrecuenciaMeses(Activo activo);
}
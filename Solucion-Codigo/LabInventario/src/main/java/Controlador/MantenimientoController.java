/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
/**
 *
 * @author gemzie
 */

import java.time.LocalDate;

import Controlador.Calculadora.ICalculadoraMantenimiento;
import Modelo.Activo;
import java.util.Map;

public class MantenimientoController {

    // Mapa que asocia el tipo de activo (String) con su calculadora correspondiente
    private final Map<String, ICalculadoraMantenimiento> calculadoras;

    // Constructor que recibe el mapa (Soluciona el error de NetBeans)
    public MantenimientoController(Map<String, ICalculadoraMantenimiento> calculadoras) {
        this.calculadoras = calculadoras;
    }

    /**
     * Registra un mantenimiento, calcula el costo y actualiza el seguimiento.
     */
    public double registrarMantenimiento(Activo activo, String tipoMantenimiento, String tecnico) {
        
        // 1. Obtener la calculadora correcta desde el Map (Sin usar Switch)
        ICalculadoraMantenimiento calc = calculadoras.get(activo.getTipoActivo());
        
        if (calc == null) {
            throw new IllegalArgumentException("No existe calculadora configurada para el tipo: " + activo.getTipoActivo());
        }
        
        // 2. Calcular el costo
        double costo = calc.calcular(activo);
        
        // 3. Actualizar los datos de seguimiento del activo
        activo.setFechaUltimoMantenimiento(LocalDate.now());
        activo.setCostoAcumuladoMantenimiento(activo.getCostoAcumuladoMantenimiento() + costo);
        activo.setCantidadMantenimientos(activo.getCantidadMantenimientos() + 1);
        
        // 4. Programar el próximo mantenimiento
        int mesesFrecuencia = calc.obtenerFrecuenciaMeses(activo);
        LocalDate base = (activo.getFechaUltimoMantenimiento() != null) 
            ? activo.getFechaUltimoMantenimiento() 
            : activo.getFechaAdquisicion();
        activo.setFechaProximoMantenimiento(base.plusMonths(mesesFrecuencia));
        
        activo.setEstado("EN_MANTENIMIENTO");
        
        System.out.println(" Mantenimiento registrado para: " + activo.getNombre());
        System.out.println(" Costo calculado: $" + costo);
        System.out.println(" Próximo mantenimiento: " + activo.getFechaProximoMantenimiento());
        
        return costo;
    }
}
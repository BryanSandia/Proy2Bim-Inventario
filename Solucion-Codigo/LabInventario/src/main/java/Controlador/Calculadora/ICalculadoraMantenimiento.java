/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Calculadora;

import Modelo.Activo;

//==== Interfaz para calcular costos de mantenimiento ====//
public interface ICalculadoraMantenimiento {
    
    /**
     * Calcula el costo de mantenimiento para un activo.
     * @param activo el activo a evaluar
     * @return costo estimado del mantenimiento
     */
    double calcular(Activo activo);
    
    /**
     * Obtiene la frecuencia de mantenimiento en meses.
     * @param activo el activo a evaluar
     * @return meses entre mantenimientos
     */
    int obtenerFrecuenciaMeses(Activo activo);
}
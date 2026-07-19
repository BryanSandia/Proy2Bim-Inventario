/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador.Calculadora;

import java.time.LocalDate;
import Modelo.Activo;
import Modelo.Mobiliario;

public class CalculadoraMobiliario implements ICalculadoraMantenimiento {

    @Override
    public double calcular(Activo activo) {
        if (!(activo instanceof Mobiliario)) {
            throw new IllegalArgumentException("Esta calculadora solo funciona con Mobiliario");
        }

        Mobiliario mob = (Mobiliario) activo;
        int anios = LocalDate.now().getYear() - mob.getFechaAdquisicion().getYear(); // Obtener años de uso
        // Fórmula con un factor de desgaste menor para muebles (ej. 0.02) y base del 5% (0.05)
        double costoMantenimiento = mob.getCostoAdquisicion() * 0.05 * (1 + anios * 0.02);
        return costoMantenimiento;
    }

    @Override
    public int obtenerFrecuenciaMeses(Activo activo) {
        return 24; // Mantenimiento cada 24 meses para Mobiliario
    }
}


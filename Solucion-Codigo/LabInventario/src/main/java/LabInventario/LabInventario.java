/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package LabInventario;

import Controlador.Calculadora.CalculadoraHardware;
import Controlador.Calculadora.CalculadoraMobiliario;
import Controlador.Calculadora.CalculadoraPeriferico;
import Controlador.Calculadora.ICalculadoraMantenimiento;
import Controlador.IActivoBD;
import Controlador.IMantenimientoBD;
import Controlador.MantenimientoBDImpl;
import Controlador.MantenimientoController;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gemzie
 */
public class LabInventario {

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema Lab-Inventario...");

        java.util.Map<Controlador.Calculadora.ICalculadoraMantenimiento, Controlador.Calculadora.ICalculadoraMantenimiento> calculadoras = new java.util.HashMap<>();
        java.util.Map<String, Controlador.Calculadora.ICalculadoraMantenimiento> mapaCalculadoras = new java.util.HashMap<>();
        mapaCalculadoras.put("HARDWARE", new Controlador.Calculadora.CalculadoraHardware());
        mapaCalculadoras.put("PERIFERICO", new Controlador.Calculadora.CalculadoraPeriferico());
        mapaCalculadoras.put("MOBILIARIO", new Controlador.Calculadora.CalculadoraMobiliario());
        Controlador.IActivoBD activoBD = new Controlador.ActivoDBImpl();
        Controlador.IMantenimientoBD mantenimientoBD = new Controlador.MantenimientoBDImpl();
        Controlador.MantenimientoController mantController = new Controlador.MantenimientoController(mapaCalculadoras, activoBD, mantenimientoBD);
    }
}

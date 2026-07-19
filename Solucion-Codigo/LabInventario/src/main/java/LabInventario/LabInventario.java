/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package LabInventario;

import Controlador.Calculadora.CalculadoraHardware;
import Controlador.Calculadora.CalculadoraMobiliario;
import Controlador.Calculadora.CalculadoraPeriferico;
import Controlador.Calculadora.ICalculadoraMantenimiento;
import Controlador.MantenimientoController;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gemzie
 */
public class LabInventario {

    public static void main(String[] args) {
       Map<String, ICalculadoraMantenimiento> calculadoras = new HashMap<>();
        calculadoras.put("HARDWARE", new CalculadoraHardware());
        calculadoras.put("PERIFERICO", new CalculadoraPeriferico());
        calculadoras.put("MOBILIARIO", new CalculadoraMobiliario());

        // 3. Inyectar las calculadoras al controlador
        MantenimientoController mantController = new MantenimientoController(calculadoras);

        System.out.println("Sistema Lab-Inventario Inicializado correctamente.");
        System.out.println("Calculadoras registradas y listas para operar.");
    }
}

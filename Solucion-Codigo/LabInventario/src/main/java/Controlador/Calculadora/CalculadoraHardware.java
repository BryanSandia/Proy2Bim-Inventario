package Controlador.Calculadora;
import java.time.LocalDate;

import Modelo.Activo;
import Modelo.Hardware;

/**
 *
 * @author gemzie
 */
public class CalculadoraHardware implements ICalculadoraMantenimiento{


    @Override
    public double calcular(Activo activo) {
        if (!(activo instanceof Hardware)){
        throw new IllegalArgumentException("Esta calculadora solo funciona con Hardware");
        }

        Hardware hw = (Hardware) activo;
        int anios = LocalDate.now().getYear() - hw.getFechaAdquisicion().getYear(); //Obtener anios en uso
        double costoMantenimiento = hw.getCostoAdquisicion() * 1.2 * (1 + anios * 0.1); //calcula la renovacion con formula, CostoAdquisicion * 1,2 * (1 + anios * 0.1)
        return costoMantenimiento;
    }


    @Override
    public int obtenerFrecuenciaMeses(Activo activo) {
        return 6; //Mantenimiento cada 6 meses para Hw
    }

    
}

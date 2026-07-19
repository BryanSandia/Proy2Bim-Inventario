package Controlador.Calculadora;
import java.time.LocalDate;

import Modelo.Activo;
import Modelo.Periferico;
public class CalculadoraPeriferico implements ICalculadoraMantenimiento{


    @Override
    public double calcular(Activo activo) {
        if (!(activo instanceof Periferico)){
        throw new IllegalArgumentException("Esta calculadora solo funciona con Perifericos");
        }

        Periferico pf = (Periferico) activo;
        int anios = LocalDate.now().getYear() - pf.getFechaAdquisicion().getYear(); //Obtener anios en uso
        double costoMantenimiento = pf.getCostoAdquisicion() * 0.8 * (1 + anios * 0.05); //calcula la renovacion con formula, CostoAdquisicion * 0.8 * (1 + anios * 0.05)
        return costoMantenimiento;
    }


    @Override
    public int obtenerFrecuenciaMeses(Activo activo) {
        return 12; //Mantenimiento cada 12 meses para Perifericos
    }

    
}


package Controlador.Calculadora;
import Modelo.Software;    

    // Calcula el costo de mantener un software, o de usarlo. por mes y por anio. El resultado es el calculo por equipo
public class CalculadoraSoftware implements ICalculadoraSoftware {

    @Override
    public double calcularMensual(Software software) {
        int meses = (software.getFechaRenovacion().getMonthValue() - software.getFechaAdquisicion().getMonthValue() + 
                    12 * (software.getFechaRenovacion().getYear() - software.getFechaAdquisicion().getYear()));

        double costoPerEquipo = software.getCostoAdquisicion() / software.getNumeroEquipos();
        double costoMantenimiento = costoPerEquipo / meses;
        return costoMantenimiento;
    }

    @Override
    public double calcularAnual(Software software) {
        double costoAnual = calcularMensual(software) * 12;
        return costoAnual;
    }
    
}



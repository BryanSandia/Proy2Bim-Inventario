package Controlador.Calculadora;

import Modelo.Software;
import java.time.temporal.ChronoUnit;

// Calcula el costo de mantener un software, o de usarlo. por mes y por anio. El resultado es el calculo por equipo
public class CalculadoraSoftware {

    public double calcularMensual(Software software) {
        // 1. Validación defensiva para evitar división por cero en equipos
        if (software.getNumeroEquipos() <= 0) {
            throw new IllegalArgumentException("El número de equipos debe ser mayor a cero para calcular costos.");
        }

        // 2. Cálculo seguro de meses usando la API de Java
        long meses = ChronoUnit.MONTHS.between(software.getFechaAdquisicion(), software.getFechaRenovacion());

        // 3. Validación defensiva para el tiempo: mínimo 1 mes de cobro
        if (meses <= 0) {
            meses = 1;
        }
        double costoPerEquipo = software.getCostoAdquisicion() / software.getNumeroEquipos();
        return costoPerEquipo / meses;
    }

    public double calcularAnual(Software software) {
        double costoAnual = calcularMensual(software) * 12;
        return costoAnual;
    }

}

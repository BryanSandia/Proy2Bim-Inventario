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

    private final Map<String, ICalculadoraMantenimiento> calculadoras;
    private final IActivoBD activoDAO;
    private final IMantenimientoBD mantenimientoDAO;

    // DIP: Inyectamos todas las dependencias en el constructor
    public MantenimientoController(Map<String, ICalculadoraMantenimiento> calculadoras,
            IActivoBD activoDAO, IMantenimientoBD mantenimientoDAO) {
        this.calculadoras = calculadoras;
        this.activoDAO = activoDAO;
        this.mantenimientoDAO = mantenimientoDAO;
    }

    public double registrarMantenimiento(Activo activo, String tipoMantenimiento, String tecnico) {

        ICalculadoraMantenimiento calc = calculadoras.get(activo.getTipoActivo());

        if (calc == null) {
            throw new IllegalArgumentException("No existe calculadora configurada para el tipo: " + activo.getTipoActivo());
        }

        double costo = calc.calcular(activo);

        activo.setFechaUltimoMantenimiento(LocalDate.now());
        activo.setCostoAcumuladoMantenimiento(activo.getCostoAcumuladoMantenimiento() + costo);
        activo.setCantidadMantenimientos(activo.getCantidadMantenimientos() + 1);

        int mesesFrecuencia = calc.obtenerFrecuenciaMeses(activo);
        LocalDate base = (activo.getFechaUltimoMantenimiento() != null)
                ? activo.getFechaUltimoMantenimiento()
                : activo.getFechaAdquisicion();
        activo.setFechaProximoMantenimiento(base.plusMonths(mesesFrecuencia));

        activo.setEstado("EN_MANTENIMIENTO");

        mantenimientoDAO.registrar(activo.getCodigo(), LocalDate.now().toString(), tipoMantenimiento, costo, tecnico);
        activoDAO.actualizar(activo);

        System.out.println(" Mantenimiento registrado y guardado para: " + activo.getNombre());

        return costo;
    }
}

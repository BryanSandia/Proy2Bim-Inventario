/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author gemzie
 */
public class Hardware extends Activo {

    private ArrayList<Mantenimiento> mantenimiento;

    // Seguimiento
    private LocalDate fechaUltimoMantenimiento;
    private LocalDate fechaProximoMantenimiento;
    private double costoAcumuladoMantenimiento;
    private int frecuenciaMantenimiento; //NUMERO EN MESES UNICAMENTE
    private int cantidadMantenimientos;


    public Hardware() {
    }

    public Hardware( String codigo, String nombre, String descripcion, double costoAdquisicion, LocalDate fechaAdquisicion,int frecuenciaMantenimiento) {
        super(codigo, nombre, descripcion, costoAdquisicion, fechaAdquisicion);
        this.cantidadMantenimientos = 0;
        this.costoAcumuladoMantenimiento = 0;
        this.fechaProximoMantenimiento = LocalDate.now().plusMonths(frecuenciaMantenimiento);
        this.fechaUltimoMantenimiento = null;
        this.frecuenciaMantenimiento = frecuenciaMantenimiento;
        this.mantenimiento = new ArrayList<>();
    }



    // ==================== GETTERS Y SETTERS ==================== //

    public List<Mantenimiento> getMantenimiento() {
        return Collections.unmodifiableList(this.mantenimiento);
    }

    public LocalDate getFechaUltimoMantenimiento() {
        return fechaUltimoMantenimiento;
    }

    public void setFechaUltimoMantenimiento(LocalDate fechaUltimoMantenimiento) {
        this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
    }

    public LocalDate getFechaProximoMantenimiento() {
        return fechaProximoMantenimiento;
    }

    public void setFechaProximoMantenimiento(LocalDate fechaProximoMantenimiento) {
        this.fechaProximoMantenimiento = fechaProximoMantenimiento;
    }

    public double getCostoAcumuladoMantenimiento() {
        return costoAcumuladoMantenimiento;
    }

    public void setCostoAcumuladoMantenimiento(double costoAcumuladoMantenimiento) {
        this.costoAcumuladoMantenimiento = costoAcumuladoMantenimiento;
    }

    public int getFrecuenciaMantenimiento() {
        return frecuenciaMantenimiento;
    }

    public void setFrecuenciaMantenimiento(int frecuenciaMantenimiento) {
        this.frecuenciaMantenimiento = frecuenciaMantenimiento;
    }

    public int getCantidadMantenimientos() {
        return cantidadMantenimientos;
    }

    public void setCantidadMantenimientos(int cantidadMantenimientos) {
        this.cantidadMantenimientos = cantidadMantenimientos;
    }


    // ==== Metodos ==== //
    public void RegistrarMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento.add(mantenimiento);
    }

    public void EliminarMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento.remove(mantenimiento);
    }

    @Override
    public String getTipoActivo() {
        return "HARDWARE";
    }
}


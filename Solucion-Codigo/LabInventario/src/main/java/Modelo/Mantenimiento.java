package Modelo;

import java.time.LocalDate;

public class Mantenimiento {
    private final String codigoMantenimiento;
    private String Encargado; //Nombre de empresa o Persona encargada del mantenimiento
    private double Costo;
    private String Observaciones; 
    private LocalDate fechaMantenimiento;


    public Mantenimiento(String codigoMantenimiento) {
        this.codigoMantenimiento = codigoMantenimiento;
    }

    public Mantenimiento(double Costo, String Encargado, String Observaciones, String codigoMantenimiento, LocalDate fechaMantenimiento) {
        this.Costo = Costo;
        this.Encargado = Encargado;
        this.Observaciones = Observaciones;
        this.codigoMantenimiento = codigoMantenimiento;
        this.fechaMantenimiento = fechaMantenimiento;
    }



    public String getCodigoMantenimiento() {
        return codigoMantenimiento;
    }

    public String getEncargado() {
        return Encargado;
    }

    public void setEncargado(String Encargado) {
        this.Encargado = Encargado;
    }

    public double getCosto() {
        return Costo;
    }

    public void setCosto(double Costo) {
        this.Costo = Costo;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public LocalDate getFechaMantenimiento() {
        return fechaMantenimiento;
    }

    public void setFechaMantenimiento(LocalDate fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }

    @Override
    public String toString() {
         return (this.codigoMantenimiento + "|" + this.fechaMantenimiento + "|" + this.Encargado +"|"+Costo);
    }

}
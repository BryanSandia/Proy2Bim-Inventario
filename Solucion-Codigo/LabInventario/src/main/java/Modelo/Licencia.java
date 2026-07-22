/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo;

import java.time.LocalDate;

/**
 *
 * @author gemzie
 */
public class Licencia extends Software{
    private String codigoLicencia; //codigo de activación de licencia

    public Licencia() {
    }

    public Licencia(String codigoLicencia, double NumeroEquipos, LocalDate fechaRenovacion, String codigo, String nombre, String descripcion, double costoAdquisicion, LocalDate fechaAdquisicion) {
        super(NumeroEquipos, fechaRenovacion, codigo, nombre, descripcion, costoAdquisicion, fechaAdquisicion);
        this.codigoLicencia = codigoLicencia;
    }

    public Licencia(String codigoLicencia) {
        this.codigoLicencia = codigoLicencia;
    }

    public String getCodigoLicencia() {
        return codigoLicencia;
    }

    public void setCodigoLicencia(String codigoLicencia) {
        this.codigoLicencia = codigoLicencia;
    }
}

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
public class Periferico extends Activo {

    Hardware hwPertence;   

    public Periferico(Hardware hwPertence, String codigo, String nombre, String descripcion, double costoAdquisicion, LocalDate fechaAdquisicion) {
        super(codigo, nombre, descripcion, costoAdquisicion, fechaAdquisicion);
        this.hwPertence = hwPertence;
    }

    // ==================== GETTERS Y SETTERS ==================== //
    public Hardware getHwPertence() {
        return hwPertence;
    }

    public void setHwPertence(Hardware hwPertence) {
        this.hwPertence = hwPertence;
    }

    @Override
    public String getTipoActivo() {
        return "PERIFERICO";
    }


}

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
public class Mobiliario extends Activo {

    private int capacidad;   // Cantidad de puestos/equipos que soporta

    public Mobiliario(int capacidad, String codigo, String nombre, String descripcion, double costoAdquisicion, LocalDate fechaAdquisicion) {
        super(codigo, nombre, descripcion, costoAdquisicion, fechaAdquisicion);
        this.capacidad = capacidad;
    }

    // ==================== GETTERS Y SETTERS ==================== //
    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String getTipoActivo() {
        return "MOBILIARIO";
    }

}

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
public class Hardware extends Activo {


    private String Especificaciones;


    public Hardware(String codigo, String nombre, String descripcion,
                    double costoAdquisicion, LocalDate fechaAdquisicion,
                    String especificacionesTecnicas) {
        super(codigo, nombre, descripcion, costoAdquisicion, fechaAdquisicion);
        this.Especificaciones = especificacionesTecnicas;
    }
  

    // ==================== GETTERS Y SETTERS ==================== //
    public String getEspecificaciones() {
        return Especificaciones;
    }
    

    public void setEspecificaciones(String especificaciones) {
        this.Especificaciones = especificaciones;
    }
    
    @Override
    public String getTipoActivo() {
        return "HARDWARE";
    }

}


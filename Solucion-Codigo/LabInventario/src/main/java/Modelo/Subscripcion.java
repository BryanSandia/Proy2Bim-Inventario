
package Modelo;

import java.time.LocalDate;

public class Subscripcion extends Software {
    private String tipoRenovacion; //Mensual, Trimestral, Anual

    public Subscripcion() {
        super();
    }

    public Subscripcion(String tipoRenovacion) {
        this.tipoRenovacion = tipoRenovacion;
    }

    public Subscripcion(String tipoRenovacion, double NumeroEquipos, LocalDate fechaRenovacion, String codigo, String nombre, String descripcion, double costoAdquisicion, LocalDate fechaAdquisicion) {
        super(NumeroEquipos, fechaRenovacion, codigo, nombre, descripcion, costoAdquisicion, fechaAdquisicion);
        this.tipoRenovacion = tipoRenovacion;
    }

    public String getTipoRenovacion() {
        return tipoRenovacion;
    }

    public void setTipoRenovacion(String tipoRenovacion) {
        this.tipoRenovacion = tipoRenovacion;
    }
    
    @Override
    public String getTipoActivo() {
        return "SUBSCRIPCION";
    }

    public void renovar(LocalDate nuevaFechaRenovacion) {
        setFechaRenovacion(nuevaFechaRenovacion);
    }
}


package Modelo;

import java.time.LocalDate;

public class Software extends Activo {

    // Seguimiento Mantenimiento
    private LocalDate fechaRenovacion; // caduca y se debe renovar
    private double NumeroEquipos; //equipos que permite registrar

    
    
    public Software() {
    }

    public Software(double NumeroEquipos, LocalDate fechaRenovacion, String codigo, String nombre, String descripcion, double costoAdquisicion, LocalDate fechaAdquisicion) {
        super(codigo, nombre, descripcion, costoAdquisicion, fechaAdquisicion);
        this.NumeroEquipos = NumeroEquipos;
        this.fechaRenovacion = fechaRenovacion;
    }

    public LocalDate getFechaRenovacion() {
        return fechaRenovacion;
    }

    public void setFechaRenovacion(LocalDate fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    public double getNumeroEquipos() {
        return NumeroEquipos;
    }

    public void setNumeroEquipos(double NumeroEquipos) {
        this.NumeroEquipos = NumeroEquipos;
    }

    @Override
    public String getTipoActivo() {
        return "SOFTWARE";
    }

}

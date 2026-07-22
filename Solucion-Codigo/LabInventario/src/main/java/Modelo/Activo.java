package Modelo;

// === Clase abstracta que representa un activo de laboratorio === //
import java.time.LocalDate;

public abstract class Activo {

    protected String codigo;
    protected String nombre;
    protected String descripcion;
    protected double costoAdquisicion;
    protected LocalDate fechaAdquisicion;

    // Seguimiento
    protected String estado; 
    

    public Activo() {
    }


    public Activo(String codigo, String nombre, String descripcion,
            double costoAdquisicion, LocalDate fechaAdquisicion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoAdquisicion = costoAdquisicion;
        this.fechaAdquisicion = fechaAdquisicion;
        this.estado = "DISPONIBLE"; // DISPONBLE, EN MANTENIMIENTO, DE BAJA
    }


    //método abstracto: Para identificar el tipo en el Controlador
    public abstract String getTipoActivo();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCostoAdquisicion() {
        return costoAdquisicion;
    }

    public void setCostoAdquisicion(double costoAdquisicion) {
        this.costoAdquisicion = costoAdquisicion;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s | Estado: %s", getTipoActivo(), codigo, nombre, estado);
    }
}

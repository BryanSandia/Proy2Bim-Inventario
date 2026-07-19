/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    protected LocalDate fechaUltimoMantenimiento;
    protected LocalDate fechaProximoMantenimiento;
    protected double costoAcumuladoMantenimiento;
    protected int cantidadMantenimientos;
    protected String estado;

    public Activo(String codigo, String nombre, String descripcion,
            double costoAdquisicion, LocalDate fechaAdquisicion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoAdquisicion = costoAdquisicion;
        this.fechaAdquisicion = fechaAdquisicion;
        this.estado = "DISPONIBLE"; // DISPONBLE, EN MANTENIMIENTO, DE BAJA
        this.costoAcumuladoMantenimiento = 0.0;
        this.cantidadMantenimientos = 0;
    }

    // ÚNICO método abstracto: Para identificar el tipo en el Controlador
    public abstract String getTipoActivo();

    // Getters y Setters (Puros, sin lógica)
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

    public LocalDate getFechaUltimoMantenimiento() {
        return fechaUltimoMantenimiento;
    }

    public void setFechaUltimoMantenimiento(LocalDate fecha) {
        this.fechaUltimoMantenimiento = fecha;
    }

    public LocalDate getFechaProximoMantenimiento() {
        return fechaProximoMantenimiento;
    }

    public void setFechaProximoMantenimiento(LocalDate fecha) {
        this.fechaProximoMantenimiento = fecha;
    }

    public double getCostoAcumuladoMantenimiento() {
        return costoAcumuladoMantenimiento;
    }

    public void setCostoAcumuladoMantenimiento(double costo) {
        this.costoAcumuladoMantenimiento = costo;
    }

    public int getCantidadMantenimientos() {
        return cantidadMantenimientos;
    }

    public void setCantidadMantenimientos(int cantidad) {
        this.cantidadMantenimientos = cantidad;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s | Estado: %s", getTipoActivo(), codigo, nombre, estado);
    }
}

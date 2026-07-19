/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author ASUS
 */
public interface IMantenimientoBD {
    /**
     * Registra un nuevo historial de mantenimiento en la base de datos.
     */
    void registrar(String codigoActivo, String fecha, String tipo, double costo, String tecnico);
}

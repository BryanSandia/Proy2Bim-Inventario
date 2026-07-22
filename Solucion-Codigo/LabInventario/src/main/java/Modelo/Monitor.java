/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Modelo;

/**
 *
 * @author gemzie
 */
public class Monitor extends Hardware {

    private String resolucion;

    public Monitor() {
    }

    public Monitor(String marca, String modelo, String resolucion, double tamanio) {
        this.resolucion = resolucion;

    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }
   
     @Override
     public String getTipoActivo() {
         return "MONITOR";
     }

}

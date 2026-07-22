package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Laboratorio {
    private final String codigoLab;
    private String CEDdocenteEncargado;
    private int capacidadMaximaHardware; //Puestos de trabajo, espacios, etc
    private List<Activo> activos; 

    public Laboratorio(String codigoLab, String CEDdocenteEncargado, int capacidadMaximaHardware) {
        this.codigoLab = codigoLab;
        this.CEDdocenteEncargado = CEDdocenteEncargado;
        this.capacidadMaximaHardware = capacidadMaximaHardware;
        this.activos = new ArrayList<>();
    }

    // ==== SOLO GETTERS Y SETTERS BÁSICOS ==== //
    public String getCodigoLab() {
        return codigoLab;
    }

    public String getCEDdocenteEncargado() {
        return CEDdocenteEncargado;
    }

    public void setCEDdocenteEncargado(String ced) {
        this.CEDdocenteEncargado = ced;
    }

    public int getCapacidadMaximaHardware() {
        return capacidadMaximaHardware;
    }

    public void setCapacidadMaximaHardware(int cap) {
        this.capacidadMaximaHardware = cap;
    }

    public List<Activo> getActivos() {
        return activos;
    }

    public void setActivos(List<Activo> activos) {
        this.activos = activos;
    }
}
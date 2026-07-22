package Modelo;
import java.util.ArrayList;

public class CPU extends Hardware {

    private String procesador;
    private int ram; // en GB
    private int almacenamiento; // en GB
    private ArrayList<Software> software;

    public CPU() {
    }

    public CPU( String procesador, int ram, int almacenamiento) {
        this.procesador = procesador;
        this.ram = ram;
        this.almacenamiento = almacenamiento;
        this.software = new ArrayList<>();
    }


    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(int almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public ArrayList<Software> getSoftware() {
        return software;
    }

    public void AgregarSoftware(Software e) {
        software.add(e);
    }

    public void EliminarSoftware(Software e){
        software.remove(e);
    }

    @Override
    public String getTipoActivo() {
        return "CPU";
    }

}

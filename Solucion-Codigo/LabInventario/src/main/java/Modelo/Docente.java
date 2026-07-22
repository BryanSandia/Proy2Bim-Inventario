package Modelo;
import java.util.List;
// Docente: Edicion Limitada a su laboratorio
public class Docente extends Persona {
    private List<String> codigosLaboratoriosAsignados;

    public Docente(String codigo, String nombre, String correo, 
                   List<String> laboratoriosAsignados) {
        super(codigo, nombre, correo, NivelAcceso.DOCENTE);
        this.codigosLaboratoriosAsignados = laboratoriosAsignados;
    }

    public boolean tieneAccesoALaboratorio(String codigoLab) {
        return codigosLaboratoriosAsignados != null && 
               codigosLaboratoriosAsignados.contains(codigoLab);
    }

    public List<String> getCodigosLaboratoriosAsignados() {
        return codigosLaboratoriosAsignados;
    }

    public void setCodigosLaboratoriosAsignados(List<String> codigosLaboratoriosAsignados) {
        this.codigosLaboratoriosAsignados = codigosLaboratoriosAsignados;
    }


}
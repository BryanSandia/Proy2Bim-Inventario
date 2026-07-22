package Modelo;


public abstract class Persona {
    protected String codigo;
    protected String nombre;
    protected String correo;
    protected NivelAcceso nivelAcceso;

    public Persona(String codigo, String nombre, String correo, NivelAcceso nivelAcceso) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
        this.nivelAcceso = nivelAcceso;
    }

    public NivelAcceso getNivelAcceso() {
        return nivelAcceso;
    }
    
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
}
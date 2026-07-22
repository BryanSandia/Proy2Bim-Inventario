package Modelo;

// Usuario: Solo puede visualizar mas no editar
public class Usuario extends Persona {
    public Usuario(String codigo, String nombre, String correo) {
        super(codigo, nombre, correo, NivelAcceso.USUARIO);
    }
}

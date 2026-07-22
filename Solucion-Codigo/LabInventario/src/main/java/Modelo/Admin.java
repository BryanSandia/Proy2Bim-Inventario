package Modelo;
// Admin: Nivel de acceso total, puede editar, crear o eliminar
public class Admin extends Persona {
    public Admin(String codigo, String nombre, String correo) {
        super(codigo, nombre, correo, NivelAcceso.ADMIN);
    }
}
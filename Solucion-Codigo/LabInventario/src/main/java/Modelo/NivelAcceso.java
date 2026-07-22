package Modelo;

public enum NivelAcceso {
    USUARIO,      // Solo lectura
    DOCENTE,      // Edición limitada a sus laboratorios
    ADMIN         // Acceso total
}
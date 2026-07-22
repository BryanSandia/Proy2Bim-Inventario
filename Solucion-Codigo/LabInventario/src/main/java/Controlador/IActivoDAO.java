package Controlador;

import java.util.List;

import Modelo.Activo;
import Modelo.Laboratorio;

public interface IActivoDAO {
    void crear(Activo activo);
    void actualizar(Activo activo);
    void eliminar(String codigo);
    Activo buscarPorCodigo(String codigo);
    List<Activo> listarTodos();
    
    // Métodos auxiliares para la tabla única
    Laboratorio obtenerLaboratorioDeActivo(String codigoActivo);
}
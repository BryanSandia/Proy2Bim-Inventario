/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Activo;
import java.util.List;

public interface IActivoBD {

    void crear(Activo activo);

    Activo buscarPorCodigo(String codigo);

    List<Activo> listarTodos();

    void actualizar(Activo activo);

    void eliminar(String codigo);
}

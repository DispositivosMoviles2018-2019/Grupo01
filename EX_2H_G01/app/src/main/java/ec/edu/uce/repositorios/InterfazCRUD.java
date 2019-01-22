package ec.edu.uce.repositorios;

import java.util.Collection;

import ec.edu.uce.componentes.Funcion;

public interface InterfazCRUD<T, E> {

    /**
     * Metodo que permite crear un nuevo Objeto de cualquier clase.
     *
     * @param obj Es el nuevo Objeto que se va a crear
     * @return Un mensaje para alertar al usuario
     */
    public String crear(T obj);

    /**
     * Metodo que permite actualizar el estado de un Objeto.
     *
     * @param id El id del Objeto a ser actualizado
     * @param obj El objeto con los nuevos valores
     * @return Un mensaje para alertar al usuario
     */
    public String actualizar(E id, T obj);

    /**
     * Metodo que permite borrar un Objeto
     *
     * @param id El id del Objeto a ser eliminado
     * @return Un mensaje para alertar al usuario
     */
    public String borrar(E id);

    /**
     * Metodo que permite buscar un Objeto
     *
     * @param parametro El parametro del Objeto que se está buscando
     * @return El Objeto encontrado, si el objeto no existe, retorna null
     */
    public <F> Collection<T> buscarPorParametro(Funcion<T, F> atributo, F parametro);

    /**
     * Metodo que permita listar los Objetos
     *
     * @return La colección con el resultado
     */
    public Collection<T> listar();
}

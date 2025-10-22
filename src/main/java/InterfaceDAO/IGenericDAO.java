package InterfaceDAO;

import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD básicas.
 * Todas las interfaces DAO específicas deben extender esta interfaz.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 * @param <T> Tipo de entidad
 * @param <ID> Tipo de la clave primaria
 */
public interface IGenericDAO<T, ID> {

    /**
     * Crea una nueva entidad en la base de datos.
     *
     * @param entidad La entidad a crear
     * @return La entidad creada con su ID generado
     * @throws Exception Si ocurre un error durante la creación
     */
    T crear(T entidad) throws Exception;

    /**
     * Busca una entidad por su ID.
     *
     * @param id El ID de la entidad a buscar
     * @return La entidad encontrada o null si no existe
     * @throws Exception Si ocurre un error durante la búsqueda
     */
    T buscarPorId(ID id) throws Exception;

    /**
     * Lista todas las entidades con un límite máximo.
     *
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de entidades
     * @throws Exception Si ocurre un error durante la consulta
     * @throws IllegalArgumentException Si limit > 100
     */
    List<T> listar(int limit) throws Exception;

    /**
     * Actualiza una entidad existente.
     *
     * @param entidad La entidad con los datos actualizados
     * @return La entidad actualizada
     * @throws Exception Si ocurre un error durante la actualización
     */
    T actualizar(T entidad) throws Exception;

    /**
     * Elimina una entidad por su ID.
     *
     * @param id El ID de la entidad a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     * @throws Exception Si ocurre un error durante la eliminación
     */
    boolean eliminar(ID id) throws Exception;
}

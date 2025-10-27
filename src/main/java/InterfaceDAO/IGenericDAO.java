package InterfaceDAO;

import jakarta.persistence.EntityManager;
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
    
    T crear(EntityManager em, T entidad) throws Exception;

    T buscarPorId(EntityManager em, ID id) throws Exception;

    List<T> listar(EntityManager em, int limit) throws Exception;

    T actualizar(EntityManager em, T entidad) throws Exception;

    boolean eliminar(EntityManager em, ID id) throws Exception;
}
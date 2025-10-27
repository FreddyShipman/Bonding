package DAO;

import InterfaceDAO.IGenericDAO;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public abstract class GenericDAO<T, ID> implements IGenericDAO<T, ID> {

    private final Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T crear(EntityManager em, T entidad) throws Exception {
        try {
            em.persist(entidad);
            return entidad;
        } catch (Exception e) {
            throw new Exception("Error al crear la entidad: " + e.getMessage(), e);
        }
    }

    @Override
    public T buscarPorId(EntityManager em, ID id) throws Exception {
        try {
            return em.find(entityClass, id);
        } catch (Exception e) {
            throw new Exception("Error al buscar por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<T> listar(EntityManager em, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El limite no puede ser mayor a 100");
        }
        try {
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
            return em.createQuery(jpql, entityClass)
                     .setMaxResults(limit)
                     .getResultList();
        } catch (Exception e) {
            throw new Exception("Error al listar las entidades: " + e.getMessage(), e);
        }
    }

    @Override
    public T actualizar(EntityManager em, T entidad) throws Exception {
        try {
            T entidadActualizada = em.merge(entidad);
            return entidadActualizada;
        } catch (Exception e) {
            throw new Exception("Error al actualizar la entidad: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(EntityManager em, ID id) throws Exception {
        try {
            T entidad = this.buscarPorId(em, id);
            if (entidad == null) {
                return false;
            }
            T entidadParaEliminar = em.merge(entidad);
            em.remove(entidadParaEliminar);
            return true;
        } catch (Exception e) {
            throw new Exception("Error al eliminar la entidad por ID: " + e.getMessage(), e);
        }
    }
}
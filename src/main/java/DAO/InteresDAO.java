package DAO;

import Domain.Estudiante;
import Domain.Interes;
import InterfaceDAO.IInteresDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IInteresDAO para gestionar operaciones CRUD
 * y consultas específicas sobre la entidad Interes.
 *
 * @author Bonding Team
 */

public class InteresDAO extends GenericDAO<Interes, Long> implements IInteresDAO {

    public InteresDAO() {
        super(Interes.class);
    }

    @Override
    public Interes buscarPorNombre(EntityManager em, String nombreInteres) throws Exception {
        try {
            TypedQuery<Interes> query = em.createQuery(
                "SELECT i FROM Interes i WHERE i.nombreInteres = :nombre", Interes.class);
            query.setParameter("nombre", nombreInteres);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al buscar interes por nombre: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Interes> buscarPorCategoria(EntityManager em, String categoria, int limit) throws Exception {
        try {
            TypedQuery<Interes> query = em.createQuery(
                "SELECT i FROM Interes i WHERE i.categoria = :categoria", Interes.class);
            query.setParameter("categoria", categoria);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar interes por categoria: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> obtenerCategoriasUnicas(EntityManager em) throws Exception {
        try {
            TypedQuery<String> query = em.createQuery(
                "SELECT DISTINCT i.categoria FROM Interes i", String.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener categorias unicas: " + e.getMessage(), e);
        }
    }

    @Override
    public Long contarEstudiantesPorInteres(EntityManager em, Long idInteres) throws Exception {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(e) FROM Estudiante e JOIN e.intereses i WHERE i.idInteres = :idInteres", Long.class);
            query.setParameter("idInteres", idInteres);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Error al contar estudiantes por interes: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesPorInteres(EntityManager em, Long idInteres, int limit) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e JOIN e.intereses i WHERE i.idInteres = :idInteres", Estudiante.class);
            query.setParameter("idInteres", idInteres);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiantes por interes: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Interes> obtenerInteresesMasPopulares(EntityManager em, int limit) throws Exception {
        try {
            TypedQuery<Interes> query = em.createQuery(
                "SELECT i FROM Interes i JOIN i.estudiantes e " +
                "GROUP BY i " +
                "ORDER BY COUNT(e) DESC", Interes.class);
            
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener intereses mas populares: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Interes> obtenerInteresesEnComun(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception {
        try {
            TypedQuery<Interes> query = em.createQuery(
                "SELECT i FROM Interes i JOIN i.estudiantes e1 JOIN i.estudiantes e2 " +
                "WHERE e1.idEstudiante = :idEst1 AND e2.idEstudiante = :idEst2", Interes.class);
            query.setParameter("idEst1", idEstudiante1);
            query.setParameter("idEst2", idEstudiante2);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener intereses en comun: " + e.getMessage(), e);
        }
    }
}
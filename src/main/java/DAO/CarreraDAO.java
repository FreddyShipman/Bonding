package DAO;

import Domain.Carrera;
import Domain.Estudiante;
import InterfaceDAO.ICarreraDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class CarreraDAO extends GenericDAO<Carrera, Long> implements ICarreraDAO {

    public CarreraDAO() {
        super(Carrera.class);
    }

    @Override
    public Carrera buscarPorNombre(EntityManager em, String nombreCarrera) throws Exception {
        try {
            TypedQuery<Carrera> query = em.createQuery(
                "SELECT c FROM Carrera c WHERE c.nombreCarrera = :nombre", Carrera.class);
            query.setParameter("nombre", nombreCarrera);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al buscar carrera por nombre: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Carrera> listarCarrerasConEstudiantes(EntityManager em) throws Exception {
        try {
            TypedQuery<Carrera> query = em.createQuery(
                "SELECT c FROM Carrera c WHERE c.estudiantes IS NOT EMPTY", Carrera.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al listar carreras con estudiantes: " + e.getMessage(), e);
        }
    }

    @Override
    public Long contarEstudiantesPorCarrera(EntityManager em, Long idCarrera) throws Exception {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(e) FROM Estudiante e WHERE e.carrera.idCarrera = :idCarrera", Long.class);
            query.setParameter("idCarrera", idCarrera);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Error al contar estudiantes por carrera: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesPorCarrera(EntityManager em, Long idCarrera, int limit) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e WHERE e.carrera.idCarrera = :idCarrera", Estudiante.class);
            query.setParameter("idCarrera", idCarrera);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiantes por carrera: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Carrera> obtenerCarrerasMasPopulares(EntityManager em, int limit) throws Exception {
        try {
            TypedQuery<Carrera> query = em.createQuery(
                "SELECT c FROM Carrera c JOIN c.estudiantes e " +
                "GROUP BY c " +
                "ORDER BY COUNT(e) DESC", Carrera.class);
            
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener carreras mas populares: " + e.getMessage(), e);
        }
    }
}
package DAO;

import Domain.Interaccion;
import Domain.Estudiante;
import InterfaceDAO.IInteraccionDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class InteraccionDAO extends GenericDAO<Interaccion, Long> implements IInteraccionDAO {

    public InteraccionDAO() {
        super(Interaccion.class);
    }

    @Override
    public List<Interaccion> buscarPorTipo(EntityManager em, String tipoInteraccion, int limit) throws Exception {
        try {
            TypedQuery<Interaccion> query = em.createQuery(
                "SELECT i FROM Interaccion i WHERE i.tipoInteraccion = :tipo", Interaccion.class);
            query.setParameter("tipo", tipoInteraccion);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar interaccion por tipo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Interaccion> buscarPorFecha(EntityManager em, LocalDate fecha, int limit) throws Exception {
        try {
            TypedQuery<Interaccion> query = em.createQuery(
                "SELECT i FROM Interaccion i WHERE i.fechaInteraccion = :fecha", Interaccion.class);
            query.setParameter("fecha", fecha);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar interaccion por fecha: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Interaccion> buscarPorRangoFechas(EntityManager em, LocalDate fechaInicio, LocalDate fechaFin, int limit) throws Exception {
        try {
            TypedQuery<Interaccion> query = em.createQuery(
                "SELECT i FROM Interaccion i WHERE i.fechaInteraccion BETWEEN :inicio AND :fin", Interaccion.class);
            query.setParameter("inicio", fechaInicio);
            query.setParameter("fin", fechaFin);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar interaccion por rango de fechas: " + e.getMessage(), e);
        }
    }

    @Override
    public Long contarEstudiantesPorInteraccion(EntityManager em, Long idInteraccion) throws Exception {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(e) FROM Interaccion i JOIN i.estudiantes e WHERE i.idInteraccion = :idInteraccion", Long.class);
            query.setParameter("idInteraccion", idInteraccion);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Error al contar estudiantes por interaccion: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Estudiante> obtenerEstudiantesPorInteraccion(EntityManager em, Long idInteraccion, int limit) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Interaccion i JOIN i.estudiantes e WHERE i.idInteraccion = :idInteraccion", Estudiante.class);
            query.setParameter("idInteraccion", idInteraccion);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiantes por interaccion: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Interaccion> obtenerInteraccionesMasPopulares(EntityManager em, int limit) throws Exception {
        try {
            TypedQuery<Interaccion> query = em.createQuery(
                "SELECT i FROM Interaccion i JOIN i.estudiantes e " +
                "GROUP BY i " +
                "ORDER BY COUNT(e) DESC", Interaccion.class);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener interacciones mas populares: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<String> obtenerTiposInteraccionUnicos(EntityManager em) throws Exception {
        try {
            TypedQuery<String> query = em.createQuery(
                "SELECT DISTINCT i.tipoInteraccion FROM Interaccion i", String.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener tipos de interaccion unicos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Interaccion> obtenerInteraccionesEnComun(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception {
        try {
            TypedQuery<Interaccion> query = em.createQuery(
                "SELECT i FROM Interaccion i JOIN i.estudiantes e1 JOIN i.estudiantes e2 " +
                "WHERE e1.idEstudiante = :idEst1 AND e2.idEstudiante = :idEst2", Interaccion.class);
            query.setParameter("idEst1", idEstudiante1);
            query.setParameter("idEst2", idEstudiante2);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener interacciones en comun: " + e.getMessage(), e);
        }
    }
}
package DAO;

import Domain.Chat;
import Domain.Estudiante;
import Domain.Match;
import InterfaceDAO.IMatchDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación de la interfaz IMatchDAO para gestionar operaciones CRUD
 * y consultas específicas sobre la entidad Match.
 *
 * @author Bonding Team
 */

public class MatchDAO extends GenericDAO<Match, Long> implements IMatchDAO {

    public MatchDAO() {
        super(Match.class);
    }

    @Override
    public List<Match> obtenerMatchesPorEstudiante(EntityManager em, Long idEstudiante, int limit) throws Exception {
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m JOIN m.estudiantes e WHERE e.idEstudiante = :idEst", Match.class);
            query.setParameter("idEst", idEstudiante);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener matches por estudiante: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Match> buscarPorFecha(EntityManager em, LocalDateTime fecha, int limit) throws Exception {
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m WHERE m.fechaMatch = :fecha", Match.class);
            query.setParameter("fecha", fecha);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar match por fecha: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Match> buscarPorRangoFechas(EntityManager em, LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception {
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m WHERE m.fechaMatch BETWEEN :inicio AND :fin", Match.class);
            query.setParameter("inicio", fechaInicio);
            query.setParameter("fin", fechaFin);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar match por rango fechas: " + e.getMessage(), e);
        }
    }

    @Override
    public Match verificarMatchExistente(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception {
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m JOIN m.estudiantes e1 JOIN m.estudiantes e2 " +
                "WHERE e1.idEstudiante = :idEst1 AND e2.idEstudiante = :idEst2", Match.class);
            query.setParameter("idEst1", idEstudiante1);
            query.setParameter("idEst2", idEstudiante2);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al verificar match existente: " + e.getMessage(), e);
        }
    }

    @Override
    public Long contarMatchesPorEstudiante(EntityManager em, Long idEstudiante) throws Exception {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Match m JOIN m.estudiantes e WHERE e.idEstudiante = :idEst", Long.class);
            query.setParameter("idEst", idEstudiante);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Error al contar matches por estudiante: " + e.getMessage(), e);
        }
    }

    @Override
    public Chat obtenerChatDelMatch(EntityManager em, Long idMatch) throws Exception {
        try {
            TypedQuery<Chat> query = em.createQuery(
                "SELECT m.chat FROM Match m WHERE m.idMatch = :idMatch", Chat.class);
            query.setParameter("idMatch", idMatch);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al obtener chat del match: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesDelMatch(EntityManager em, Long idMatch) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Match m JOIN m.estudiantes e WHERE m.idMatch = :idMatch", Estudiante.class);
            query.setParameter("idMatch", idMatch);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiantes del match: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Match> obtenerMatchesRecientes(EntityManager em, int limit) throws Exception {
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m ORDER BY m.fechaMatch DESC", Match.class);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener matches recientes: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Match> obtenerMatchesConChat(EntityManager em, Long idEstudiante, int limit) throws Exception {
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m JOIN m.estudiantes e WHERE e.idEstudiante = :idEst AND m.chat IS NOT NULL", Match.class);
            query.setParameter("idEst", idEstudiante);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener matches con chat: " + e.getMessage(), e);
        }
    }

    @Override
    public Long contarMatchesPorPeriodo(EntityManager em, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws Exception {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Match m WHERE m.fechaMatch BETWEEN :inicio AND :fin", Long.class);
            query.setParameter("inicio", fechaInicio);
            query.setParameter("fin", fechaFin);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Error al contar matches por periodo: " + e.getMessage(), e);
        }
    }
}
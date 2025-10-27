package DAO;

import Config.JpaUtil;
import Domain.Chat;
import Domain.Estudiante;
import Domain.Match;
import InterfaceDAO.IMatchDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación de la interfaz IMatchDAO para gestionar operaciones CRUD
 * y consultas específicas sobre la entidad Match.
 *
 * @author Bonding Team
 */
public class MatchDAO implements IMatchDAO {

    @Override
    public Match crear(Match entidad) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();
            return entidad;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al crear el match: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Match buscarPorId(Long id) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            Match match = em.find(Match.class, id);
            if (match == null) {
                throw new Exception("Match con ID " + id + " no encontrado");
            }
            return match;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Match> listar(int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Match> query = em.createQuery("SELECT m FROM Match m", Match.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Match actualizar(Match entidad) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Match matchActualizado = em.merge(entidad);
            em.getTransaction().commit();
            return matchActualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar el match: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminar(Long id) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Match match = em.find(Match.class, id);
            if (match != null) {
                em.remove(match);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar el match: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Match> obtenerMatchesPorEstudiante(Long idEstudiante, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m JOIN m.estudiantes e WHERE e.idEstudiante = :idEstudiante",
                Match.class
            );
            query.setParameter("idEstudiante", idEstudiante);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Match> buscarPorFecha(LocalDateTime fecha, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m WHERE FUNCTION('DATE', m.fechaMatch) = FUNCTION('DATE', :fecha)",
                Match.class
            );
            query.setParameter("fecha", fecha);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Match> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m WHERE m.fechaMatch BETWEEN :inicio AND :fin",
                Match.class
            );
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Match verificarMatchExistente(Long idEstudiante1, Long idEstudiante2) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m JOIN m.estudiantes e1 JOIN m.estudiantes e2 " +
                "WHERE e1.idEstudiante = :id1 AND e2.idEstudiante = :id2",
                Match.class
            );
            query.setParameter("id1", idEstudiante1);
            query.setParameter("id2", idEstudiante2);
            List<Match> resultado = query.getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public Long contarMatchesPorEstudiante(Long idEstudiante) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Match m JOIN m.estudiantes e WHERE e.idEstudiante = :idEstudiante",
                Long.class
            );
            query.setParameter("idEstudiante", idEstudiante);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Chat obtenerChatDelMatch(Long idMatch) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Chat> query = em.createQuery(
                "SELECT c FROM Chat c WHERE c.match.idMatch = :idMatch",
                Chat.class
            );
            query.setParameter("idMatch", idMatch);
            List<Chat> resultado = query.getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesDelMatch(Long idMatch) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Match m JOIN m.estudiantes e WHERE m.idMatch = :idMatch",
                Estudiante.class
            );
            query.setParameter("idMatch", idMatch);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Match> obtenerMatchesRecientes(int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m ORDER BY m.fechaMatch DESC",
                Match.class
            );
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Match> obtenerMatchesConChat(Long idEstudiante, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT m FROM Match m JOIN m.estudiantes e WHERE e.idEstudiante = :idEstudiante " +
                "AND m.chat IS NOT NULL",
                Match.class
            );
            query.setParameter("idEstudiante", idEstudiante);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Long contarMatchesPorPeriodo(LocalDateTime inicio, LocalDateTime fin) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Match m WHERE m.fechaMatch BETWEEN :inicio AND :fin",
                Long.class
            );
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}

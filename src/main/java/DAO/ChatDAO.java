package DAO;

import Config.JpaUtil;
import Domain.Chat;
import Domain.Match;
import Domain.Mensaje;
import InterfaceDAO.IChatDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación de la interfaz IChatDAO para gestionar operaciones CRUD
 * y consultas específicas sobre la entidad Chat.
 *
 * @author Bonding Team
 */
public class ChatDAO implements IChatDAO {

    @Override
    public Chat crear(Chat entidad) throws Exception {
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
            throw new Exception("Error al crear el chat: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Chat buscarPorId(Long id) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            Chat chat = em.find(Chat.class, id);
            if (chat == null) {
                throw new Exception("Chat con ID " + id + " no encontrado");
            }
            return chat;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Chat> listar(int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Chat> query = em.createQuery("SELECT c FROM Chat c", Chat.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Chat actualizar(Chat entidad) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Chat chatActualizado = em.merge(entidad);
            em.getTransaction().commit();
            return chatActualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar el chat: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminar(Long id) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Chat chat = em.find(Chat.class, id);
            if (chat != null) {
                em.remove(chat);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar el chat: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Chat buscarPorMatch(Long idMatch) throws Exception {
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
    public List<Chat> obtenerChatsPorEstudiante(Long idEstudiante, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Chat> query = em.createQuery(
                    "SELECT c FROM Chat c JOIN c.match m JOIN m.estudiantes e " +
                            "WHERE e.idEstudiante = :idEstudiante",
                    Chat.class
            );
            query.setParameter("idEstudiante", idEstudiante);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Chat> buscarPorFechaCreacion(LocalDateTime fecha, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Chat> query = em.createQuery(
                    "SELECT c FROM Chat c WHERE FUNCTION('DATE', c.fechaCreacion) = FUNCTION('DATE', :fecha)",
                    Chat.class
            );
            query.setParameter("fecha", fecha);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Chat> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Chat> query = em.createQuery(
                    "SELECT c FROM Chat c WHERE c.fechaCreacion BETWEEN :inicio AND :fin",
                    Chat.class
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
    public List<Mensaje> obtenerMensajesDelChat(Long idChat, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                    "SELECT m FROM Mensaje m WHERE m.chat.idChat = :idChat ORDER BY m.fechaEnvio",
                    Mensaje.class
            );
            query.setParameter("idChat", idChat);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Long contarMensajesDelChat(Long idChat) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(m) FROM Mensaje m WHERE m.chat.idChat = :idChat",
                    Long.class
            );
            query.setParameter("idChat", idChat);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Match obtenerMatchDelChat(Long idChat) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Match> query = em.createQuery(
                    "SELECT c.match FROM Chat c WHERE c.idChat = :idChat",
                    Match.class
            );
            query.setParameter("idChat", idChat);
            List<Match> resultado = query.getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Chat> obtenerChatsMasActivos(int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Chat> query = em.createQuery(
                    "SELECT c FROM Chat c LEFT JOIN c.mensajes m " +
                            "GROUP BY c.idChat ORDER BY COUNT(m) DESC",
                    Chat.class
            );
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Chat> obtenerChatsInactivos(int diasInactividad, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            LocalDateTime fechaLimite = LocalDateTime.now().minusDays(diasInactividad);
            TypedQuery<Chat> query = em.createQuery(
                    "SELECT c FROM Chat c WHERE NOT EXISTS " +
                            "(SELECT m FROM Mensaje m WHERE m.chat.idChat = c.idChat AND m.fechaEnvio > :fechaLimite)",
                    Chat.class
            );
            query.setParameter("fechaLimite", fechaLimite);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Mensaje obtenerUltimoMensaje(Long idChat) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                    "SELECT m FROM Mensaje m WHERE m.chat.idChat = :idChat " +
                            "ORDER BY m.fechaEnvio DESC",
                    Mensaje.class
            );
            query.setParameter("idChat", idChat);
            query.setMaxResults(1);
            List<Mensaje> resultado = query.getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            em.close();
        }
    }
}

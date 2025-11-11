package DAO;

import Domain.Chat;
import Domain.Match;
import Domain.Mensaje;
import InterfaceDAO.IChatDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación de la interfaz IChatDAO para gestionar operaciones CRUD
 * y consultas específicas sobre la entidad Chat.
 *
 * @author Bonding Team
 */

public class ChatDAO extends GenericDAO<Chat, Long> implements IChatDAO {

    public ChatDAO() {
        super(Chat.class);
    }

    @Override
    public Chat buscarPorMatch(EntityManager em, Long idMatch) throws Exception {
        try {
            TypedQuery<Chat> query = em.createQuery(
                "SELECT c FROM Chat c WHERE c.match.idMatch = :idMatch", Chat.class);
            query.setParameter("idMatch", idMatch);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al buscar chat por match: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Chat> obtenerChatsPorEstudiante(EntityManager em, Long idEstudiante, int limit) throws Exception {
        try {
            // Primero obtener los IDs de los chats
            TypedQuery<Long> queryIds = em.createQuery(
                "SELECT c.idChat FROM Chat c " +
                "JOIN c.match m " +
                "JOIN m.estudiantes e " +
                "WHERE e.idEstudiante = :idEst", Long.class);
            queryIds.setParameter("idEst", idEstudiante);
            queryIds.setMaxResults(Math.min(limit, 100));
            List<Long> chatIds = queryIds.getResultList();

            if (chatIds.isEmpty()) {
                return List.of(); // Retornar lista vacía
            }

            // Luego cargar los chats con JOIN FETCH
            TypedQuery<Chat> query = em.createQuery(
                "SELECT DISTINCT c FROM Chat c " +
                "LEFT JOIN FETCH c.match m " +
                "LEFT JOIN FETCH m.estudiantes " +
                "WHERE c.idChat IN :chatIds", Chat.class);
            query.setParameter("chatIds", chatIds);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener chats por estudiante: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Chat> buscarPorFechaCreacion(EntityManager em, LocalDateTime fecha, int limit) throws Exception {
        try {
            TypedQuery<Chat> query = em.createQuery(
                "SELECT c FROM Chat c WHERE c.fechaCreacion = :fecha", Chat.class);
            query.setParameter("fecha", fecha);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar chat por fecha creacion: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Chat> buscarPorRangoFechas(EntityManager em, LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception {
        try {
            TypedQuery<Chat> query = em.createQuery(
                "SELECT c FROM Chat c WHERE c.fechaCreacion BETWEEN :inicio AND :fin", Chat.class);
            query.setParameter("inicio", fechaInicio);
            query.setParameter("fin", fechaFin);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar chat por rango fechas: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Mensaje> obtenerMensajesDelChat(EntityManager em, Long idChat, int limit) throws Exception {
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.chat.idChat = :idChat ORDER BY m.fechaEnvio ASC", Mensaje.class);
            query.setParameter("idChat", idChat);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener mensajes del chat: " + e.getMessage(), e);
        }
    }

    @Override
    public Long contarMensajesDelChat(EntityManager em, Long idChat) throws Exception {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Mensaje m WHERE m.chat.idChat = :idChat", Long.class);
            query.setParameter("idChat", idChat);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Error al contar mensajes del chat: " + e.getMessage(), e);
        }
    }

    @Override
    public Match obtenerMatchDelChat(EntityManager em, Long idChat) throws Exception {
        try {
            TypedQuery<Match> query = em.createQuery(
                "SELECT c.match FROM Chat c WHERE c.idChat = :idChat", Match.class);
            query.setParameter("idChat", idChat);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al obtener match del chat: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Chat> obtenerChatsMasActivos(EntityManager em, int limit) throws Exception {
        try {
            TypedQuery<Chat> query = em.createQuery(
                "SELECT c FROM Chat c JOIN c.mensajes m GROUP BY c ORDER BY COUNT(m) DESC", Chat.class);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener chats mas activos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Chat> obtenerChatsInactivos(EntityManager em, int diasInactividad, int limit) throws Exception {
        try {
            LocalDateTime fechaLimite = LocalDateTime.now().minusDays(diasInactividad);
            TypedQuery<Chat> query = em.createQuery(
                "SELECT c FROM Chat c WHERE (SELECT MAX(m.fechaEnvio) FROM Mensaje m WHERE m.chat = c) < :fechaLimite", Chat.class);
            query.setParameter("fechaLimite", fechaLimite);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener chats inactivos: " + e.getMessage(), e);
        }
    }

    @Override
    public Mensaje obtenerUltimoMensaje(EntityManager em, Long idChat) throws Exception {
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.chat.idChat = :idChat ORDER BY m.fechaEnvio DESC", Mensaje.class);
            query.setParameter("idChat", idChat);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al obtener ultimo mensaje: " + e.getMessage(), e);
        }
    }
}
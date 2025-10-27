package DAO;

import Domain.Chat;
import Domain.Mensaje;
import InterfaceDAO.IMensajeDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación de la interfaz IMensajeDAO para gestionar operaciones CRUD
 * y consultas específicas sobre la entidad Mensaje.
 *
 * @author Bonding Team
 */

public class MensajeDAO extends GenericDAO<Mensaje, Long> implements IMensajeDAO {

    public MensajeDAO() {
        super(Mensaje.class);
    }

    @Override
    public List<Mensaje> obtenerMensajesPorChat(EntityManager em, Long idChat, int limit) throws Exception {
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.chat.idChat = :idChat ORDER BY m.fechaEnvio ASC", Mensaje.class);
            query.setParameter("idChat", idChat);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener mensajes por chat: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Mensaje> obtenerMensajesPorChatYEmisor(EntityManager em, Long idChat, Long idEstudianteEmisor, int limit) throws Exception {
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.chat.idChat = :idChat AND m.estudianteEmisor.idEstudiante = :idEmisor ORDER BY m.fechaEnvio ASC", Mensaje.class);
            query.setParameter("idChat", idChat);
            query.setParameter("idEmisor", idEstudianteEmisor);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener mensajes por chat y emisor: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Mensaje> obtenerMensajesPorEmisor(EntityManager em, Long idEstudianteEmisor, int limit) throws Exception {
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.estudianteEmisor.idEstudiante = :idEmisor", Mensaje.class);
            query.setParameter("idEmisor", idEstudianteEmisor);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener mensajes por emisor: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Mensaje> buscarPorFechaEnvio(EntityManager em, LocalDateTime fecha, int limit) throws Exception {
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.fechaEnvio = :fecha", Mensaje.class);
            query.setParameter("fecha", fecha);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar mensaje por fecha: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Mensaje> buscarPorRangoFechas(EntityManager em, LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception {
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.fechaEnvio BETWEEN :inicio AND :fin", Mensaje.class);
            query.setParameter("inicio", fechaInicio);
            query.setParameter("fin", fechaFin);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar mensaje por rango fechas: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Mensaje> buscarPorContenido(EntityManager em, String textoBusqueda, int limit) throws Exception {
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE LOWER(m.contenido) LIKE LOWER(:texto)", Mensaje.class);
            query.setParameter("texto", "%" + textoBusqueda + "%");
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar mensaje por contenido: " + e.getMessage(), e);
        }
    }

    @Override
    public Long contarMensajesPorChat(EntityManager em, Long idChat) throws Exception {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Mensaje m WHERE m.chat.idChat = :idChat", Long.class);
            query.setParameter("idChat", idChat);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Error al contar mensajes por chat: " + e.getMessage(), e);
        }
    }

    @Override
    public Long contarMensajesPorEmisor(EntityManager em, Long idEstudianteEmisor) throws Exception {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Mensaje m WHERE m.estudianteEmisor.idEstudiante = :idEmisor", Long.class);
            query.setParameter("idEmisor", idEstudianteEmisor);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Error al contar mensajes por emisor: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Mensaje> obtenerMensajesRecientes(EntityManager em, Long idChat, int limit) throws Exception {
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.chat.idChat = :idChat ORDER BY m.fechaEnvio DESC", Mensaje.class);
            query.setParameter("idChat", idChat);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener mensajes recientes: " + e.getMessage(), e);
        }
    }

    @Override
    public Mensaje obtenerUltimoMensajeDelChat(EntityManager em, Long idChat) throws Exception {
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.chat.idChat = :idChat ORDER BY m.fechaEnvio DESC", Mensaje.class);
            query.setParameter("idChat", idChat);
            query.setMaxResults(1);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al obtener ultimo mensaje del chat: " + e.getMessage(), e);
        }
    }

    @Override
    public int eliminarMensajesDelChat(EntityManager em, Long idChat) throws Exception {
        try {
            int deletedCount = em.createQuery("DELETE FROM Mensaje m WHERE m.chat.idChat = :idChat")
                                 .setParameter("idChat", idChat)
                                 .executeUpdate();
            return deletedCount;
        } catch (Exception e) {
            throw new Exception("Error al eliminar mensajes del chat: " + e.getMessage(), e);
        }
    }

    @Override
    public Chat obtenerChatDelMensaje(EntityManager em, Long idMensaje) throws Exception {
        try {
            TypedQuery<Chat> query = em.createQuery(
                "SELECT m.chat FROM Mensaje m WHERE m.idMensaje = :idMensaje", Chat.class);
            query.setParameter("idMensaje", idMensaje);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al obtener chat del mensaje: " + e.getMessage(), e);
        }
    }
}
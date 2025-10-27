package DAO;

import Config.JpaUtil;
import Domain.Chat;
import Domain.Mensaje;
import InterfaceDAO.IMensajeDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación de la interfaz IMensajeDAO para gestionar operaciones CRUD
 * y consultas específicas sobre la entidad Mensaje.
 *
 * @author Bonding Team
 */
public class MensajeDAO implements IMensajeDAO {

    @Override
    public Mensaje crear(Mensaje entidad) throws Exception {
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
            throw new Exception("Error al crear el mensaje: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Mensaje buscarPorId(Long id) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            Mensaje mensaje = em.find(Mensaje.class, id);
            if (mensaje == null) {
                throw new Exception("Mensaje con ID " + id + " no encontrado");
            }
            return mensaje;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Mensaje> listar(int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Mensaje> query = em.createQuery("SELECT m FROM Mensaje m", Mensaje.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Mensaje actualizar(Mensaje entidad) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Mensaje mensajeActualizado = em.merge(entidad);
            em.getTransaction().commit();
            return mensajeActualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar el mensaje: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminar(Long id) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Mensaje mensaje = em.find(Mensaje.class, id);
            if (mensaje != null) {
                em.remove(mensaje);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar el mensaje: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Mensaje> obtenerMensajesPorChat(Long idChat, int limit) throws Exception {
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
    public List<Mensaje> obtenerMensajesPorChatYEmisor(Long idChat, Long idEstudianteEmisor, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.chat.idChat = :idChat " +
                "AND m.idEstudianteEmisor = :idEmisor ORDER BY m.fechaEnvio",
                Mensaje.class
            );
            query.setParameter("idChat", idChat);
            query.setParameter("idEmisor", idEstudianteEmisor);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Mensaje> obtenerMensajesPorEmisor(Long idEstudianteEmisor, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.idEstudianteEmisor = :idEmisor ORDER BY m.fechaEnvio DESC",
                Mensaje.class
            );
            query.setParameter("idEmisor", idEstudianteEmisor);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Mensaje> buscarPorFechaEnvio(LocalDateTime fecha, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE FUNCTION('DATE', m.fechaEnvio) = FUNCTION('DATE', :fecha)",
                Mensaje.class
            );
            query.setParameter("fecha", fecha);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Mensaje> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.fechaEnvio BETWEEN :inicio AND :fin ORDER BY m.fechaEnvio",
                Mensaje.class
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
    public List<Mensaje> buscarPorContenido(String textoBusqueda, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.contenido LIKE :texto ORDER BY m.fechaEnvio DESC",
                Mensaje.class
            );
            query.setParameter("texto", "%" + textoBusqueda + "%");
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Long contarMensajesPorChat(Long idChat) throws Exception {
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
    public Long contarMensajesPorEmisor(Long idEstudianteEmisor) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(m) FROM Mensaje m WHERE m.idEstudianteEmisor = :idEmisor",
                Long.class
            );
            query.setParameter("idEmisor", idEstudianteEmisor);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Mensaje> obtenerMensajesRecientes(Long idChat, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.chat.idChat = :idChat ORDER BY m.fechaEnvio DESC",
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
    public Mensaje obtenerUltimoMensajeDelChat(Long idChat) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Mensaje> query = em.createQuery(
                "SELECT m FROM Mensaje m WHERE m.chat.idChat = :idChat ORDER BY m.fechaEnvio DESC",
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

    @Override
    public int eliminarMensajesDelChat(Long idChat) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            int deletedCount = em.createQuery(
                "DELETE FROM Mensaje m WHERE m.chat.idChat = :idChat"
            )
            .setParameter("idChat", idChat)
            .executeUpdate();
            em.getTransaction().commit();
            return deletedCount;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar mensajes del chat: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Chat obtenerChatDelMensaje(Long idMensaje) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Chat> query = em.createQuery(
                "SELECT m.chat FROM Mensaje m WHERE m.idMensaje = :idMensaje",
                Chat.class
            );
            query.setParameter("idMensaje", idMensaje);
            List<Chat> resultado = query.getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            em.close();
        }
    }
}

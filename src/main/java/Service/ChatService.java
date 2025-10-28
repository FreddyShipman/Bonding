package Service;

import Config.JpaUtil;
import DAO.ChatDAO;
import Domain.Chat;
import Domain.Mensaje;
import InterfaceDAO.IChatDAO;
import InterfaceService.IChatService;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class ChatService implements IChatService {

    private IChatDAO chatDAO;

    public ChatService() {
        this.chatDAO = new ChatDAO();
    }

    @Override
    public Chat buscarPorId(Long idChat) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.chatDAO.buscarPorId(em, idChat);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar chat por ID: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Chat buscarPorMatch(Long idMatch) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.chatDAO.buscarPorMatch(em, idMatch);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar chat por match: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Chat> obtenerChatsPorEstudiante(Long idEstudiante, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.chatDAO.obtenerChatsPorEstudiante(em, idEstudiante, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener chats por estudiante: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Mensaje> obtenerMensajesDelChat(Long idChat, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.chatDAO.obtenerMensajesDelChat(em, idChat, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener mensajes del chat: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Mensaje obtenerUltimoMensajeDelChat(Long idChat) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.chatDAO.obtenerUltimoMensaje(em, idChat);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener ultimo mensaje: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarChat(Long idChat) throws Exception {
        EntityManager em = null;
        try {
            if (idChat == null) {
                throw new Exception("El ID no puede ser nulo para eliminar.");
            }

            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            
            boolean eliminado = this.chatDAO.eliminar(em, idChat);
            
            em.getTransaction().commit();
            return eliminado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al eliminar chat: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
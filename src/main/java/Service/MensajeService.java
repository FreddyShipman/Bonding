package Service;

import Config.JpaUtil;
import DAO.ChatDAO;
import DAO.MensajeDAO;
import Domain.Chat;
import Domain.Mensaje;
import InterfaceDAO.IChatDAO;
import InterfaceDAO.IMensajeDAO;
import InterfaceService.IMensajeService;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class MensajeService implements IMensajeService {

    private IMensajeDAO mensajeDAO;
    private IChatDAO chatDAO;

    public MensajeService() {
        this.mensajeDAO = new MensajeDAO();
        this.chatDAO = new ChatDAO();
    }

    @Override
    public Mensaje crearMensaje(Mensaje mensaje) throws Exception {
        EntityManager em = null;
        try {
            if (mensaje == null) {
                throw new Exception("El mensaje no puede ser nulo.");
            }
            if (mensaje.getChat() == null || mensaje.getChat().getIdChat() == null) {
                throw new Exception("El chat es obligatorio.");
            }
            if (mensaje.getEstudianteEmisor() == null || mensaje.getEstudianteEmisor().getIdEstudiante() == null) {
                throw new Exception("El emisor es obligatorio.");
            }
            if (mensaje.getContenido() == null || mensaje.getContenido().trim().isEmpty()) {
                throw new Exception("El contenido no puede estar vacio.");
            }

            em = JpaUtil.getInstance().getEntityManager();
            
            Chat chat = this.chatDAO.buscarPorId(em, mensaje.getChat().getIdChat());
            if (chat == null) {
                throw new Exception("Regla de negocio fallida: No se puede enviar mensaje, el chat (y el match) no existen.");
            }
            
            em.getTransaction().begin();
            
            mensaje.setFechaEnvio(LocalDateTime.now());
            this.mensajeDAO.crear(em, mensaje);
            
            em.getTransaction().commit();
            return mensaje;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al crear mensaje: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarMensaje(Long idMensaje) throws Exception {
        EntityManager em = null;
        try {
            if (idMensaje == null) {
                throw new Exception("El ID no puede ser nulo.");
            }
            
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            
            boolean eliminado = this.mensajeDAO.eliminar(em, idMensaje);
            
            em.getTransaction().commit();
            return eliminado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al eliminar mensaje: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public int eliminarMensajesDelChat(Long idChat) throws Exception {
        EntityManager em = null;
        try {
            if (idChat == null) {
                throw new Exception("El ID del chat no puede ser nulo.");
            }
            
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            
            int eliminados = this.mensajeDAO.eliminarMensajesDelChat(em, idChat);
            
            em.getTransaction().commit();
            return eliminados;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al eliminar mensajes del chat: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Mensaje> obtenerMensajesPorChat(Long idChat, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.mensajeDAO.obtenerMensajesPorChat(em, idChat, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener mensajes por chat: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Mensaje> obtenerMensajesPorChatYEmisor(Long idChat, Long idEstudianteEmisor, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.mensajeDAO.obtenerMensajesPorChatYEmisor(em, idChat, idEstudianteEmisor, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener mensajes por chat y emisor: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Mensaje> buscarPorContenido(String textoBusqueda, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.mensajeDAO.buscarPorContenido(em, textoBusqueda, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar por contenido: " + e.getMessage(), e);
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
            return this.mensajeDAO.obtenerUltimoMensajeDelChat(em, idChat);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener ultimo mensaje: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
package Service;

import Config.JpaUtil;
import DAO.ChatDAO;
import DAO.MatchDAO;
import Domain.Chat;
import Domain.Estudiante;
import Domain.Match;
import InterfaceDAO.IChatDAO;
import InterfaceDAO.IMatchDAO;
import InterfaceService.IMatchService;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */


public class MatchService implements IMatchService {

    private IMatchDAO matchDAO;
    private IChatDAO chatDAO;

    public MatchService() {
        this.matchDAO = new MatchDAO();
        this.chatDAO = new ChatDAO();
    }

    @Override
    public Match buscarPorId(Long idMatch) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.matchDAO.buscarPorId(em, idMatch);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar match por ID: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarMatch(Long idMatch) throws Exception {
        EntityManager em = null;
        try {
            if (idMatch == null) {
                throw new Exception("El ID no puede ser nulo para eliminar.");
            }
            
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            
            boolean eliminado = this.matchDAO.eliminar(em, idMatch);
            
            em.getTransaction().commit();
            return eliminado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al eliminar match: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Match> obtenerMatchesPorEstudiante(Long idEstudiante, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.matchDAO.obtenerMatchesPorEstudiante(em, idEstudiante, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener matches: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Match verificarMatchExistente(Long idEstudiante1, Long idEstudiante2) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.matchDAO.verificarMatchExistente(em, idEstudiante1, idEstudiante2);
        } catch (Exception e) {
            throw new Exception("Error en servicio al verificar match: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Chat obtenerOCrearChatDelMatch(Long idMatch) throws Exception {
        EntityManager em = null;
        try {
            if (idMatch == null) {
                throw new Exception("El ID del match no puede ser nulo.");
            }
            
            em = JpaUtil.getInstance().getEntityManager();
            
            Chat chat = this.chatDAO.buscarPorMatch(em, idMatch);
            
            if (chat != null) {
                return chat;
            }

            Match match = this.matchDAO.buscarPorId(em, idMatch);
            if (match == null) {
                throw new Exception("No se encontro el match con ID: " + idMatch);
            }

            em.getTransaction().begin();
            
            Chat nuevoChat = new Chat();
            nuevoChat.setFechaCreacion(LocalDateTime.now());
            nuevoChat.setMatch(match);
            
            this.chatDAO.crear(em, nuevoChat);
            
            em.getTransaction().commit();
            return nuevoChat;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al obtener o crear chat: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesDelMatch(Long idMatch) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.matchDAO.obtenerEstudiantesDelMatch(em, idMatch);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener estudiantes del match: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Match> obtenerMatchesRecientes(int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.matchDAO.obtenerMatchesRecientes(em, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener matches recientes: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    @Override
    public Match buscarMatchPorEstudiantes(Long idEstudiante1, Long idEstudiante2) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.matchDAO.buscarMatchPorEstudiantes(em, idEstudiante1, idEstudiante2);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar match por estudiantes: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
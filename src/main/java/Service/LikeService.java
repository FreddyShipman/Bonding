package Service;

import Config.JpaUtil;
import DAO.LikeDAO;
import DAO.MatchDAO;
import Domain.Estudiante;
import Domain.Like;
import Domain.Match;
import InterfaceDAO.ILikeDAO;
import InterfaceDAO.IMatchDAO;
import InterfaceService.ILikeService;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class LikeService implements ILikeService {

    private ILikeDAO likeDAO;
    private IMatchDAO matchDAO;

    public LikeService() {
        this.likeDAO = new LikeDAO();
        this.matchDAO = new MatchDAO();
    }

    @Override
    public Like crearLike(Like like) throws Exception {
        EntityManager em = null;
        try {
            if (like == null || like.getEstudianteEmisor() == null || like.getEstudianteReceptor() == null) {
                throw new Exception("Like, emisor y receptor no pueden ser nulos.");
            }

            Long idEmisor = like.getEstudianteEmisor().getIdEstudiante();
            Long idReceptor = like.getEstudianteReceptor().getIdEstudiante();

            if (idEmisor == null || idReceptor == null) {
                throw new Exception("Los IDs de emisor y receptor no pueden ser nulos.");
            }

            em = JpaUtil.getInstance().getEntityManager();

            Like existente = this.likeDAO.verificarLikeExistente(em, idEmisor, idReceptor);
            if (existente != null) {
                throw new Exception("Este like ya existe.");
            }

            em.getTransaction().begin();

            like.setFechaLike(LocalDateTime.now());
            this.likeDAO.crear(em, like);

            boolean hayMatchMutuo = this.likeDAO.verificarLikeMutuo(em, idEmisor, idReceptor);

            if (hayMatchMutuo) {
                Match matchExistente = this.matchDAO.verificarMatchExistente(em, idEmisor, idReceptor);
                if (matchExistente == null) {
                    Match nuevoMatch = new Match();
                    nuevoMatch.setFechaMatch(LocalDateTime.now());
                    
                    Estudiante emisor = em.find(Estudiante.class, idEmisor);
                    Estudiante receptor = em.find(Estudiante.class, idReceptor);
                    
                    nuevoMatch.setEstudiantes(Set.of(emisor, receptor));
                    
                    this.matchDAO.crear(em, nuevoMatch);
                }
            }

            em.getTransaction().commit();
            return like;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al crear like: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarLike(Long idLike) throws Exception {
        EntityManager em = null;
        try {
            if (idLike == null) {
                throw new Exception("El ID no puede ser nulo para eliminar.");
            }
            
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            
            boolean eliminado = this.likeDAO.eliminar(em, idLike);
            
            em.getTransaction().commit();
            return eliminado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al eliminar like: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Like> obtenerLikesRecibidos(Long idEstudianteReceptor, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.likeDAO.obtenerLikesPorReceptor(em, idEstudianteReceptor, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener likes recibidos: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Like> obtenerLikesEnviados(Long idEstudianteEmisor, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.likeDAO.obtenerLikesPorEmisor(em, idEstudianteEmisor, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener likes enviados: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Like verificarLikeExistente(Long idEmisor, Long idReceptor) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.likeDAO.verificarLikeExistente(em, idEmisor, idReceptor);
        } catch (Exception e) {
            throw new Exception("Error en servicio al verificar like: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> obtenerLikesPendientes(Long idEstudiante, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.likeDAO.obtenerLikesPendientesDeResponder(em, idEstudiante, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener likes pendientes: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
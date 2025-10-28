package Service;

import Config.JpaUtil;
import DAO.HobbyDAO;
import Domain.Estudiante;
import Domain.Hobby;
import InterfaceDAO.IHobbyDAO;
import InterfaceService.IHobbyService;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class HobbyService implements IHobbyService {

    private IHobbyDAO hobbyDAO;

    public HobbyService() {
        this.hobbyDAO = new HobbyDAO();
    }

    @Override
    public Hobby crearHobby(Hobby hobby) throws Exception {
        EntityManager em = null;
        try {
            if (hobby == null || hobby.getNombreHobby() == null || hobby.getNombreHobby().trim().isEmpty()) {
                throw new Exception("El nombre del hobby es obligatorio.");
            }

            em = JpaUtil.getInstance().getEntityManager();

            Hobby existente = this.hobbyDAO.buscarPorNombre(em, hobby.getNombreHobby());
            if (existente != null) {
                throw new Exception("Ya existe un hobby con ese nombre.");
            }

            em.getTransaction().begin();
            this.hobbyDAO.crear(em, hobby);
            em.getTransaction().commit();
            return hobby;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al crear hobby: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Hobby actualizarHobby(Hobby hobby) throws Exception {
        EntityManager em = null;
        try {
            if (hobby == null || hobby.getIdHobby() == null) {
                throw new Exception("El hobby o su ID no pueden ser nulos para actualizar.");
            }
            if (hobby.getNombreHobby() == null || hobby.getNombreHobby().trim().isEmpty()) {
                throw new Exception("El nombre del hobby es obligatorio.");
            }

            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            Hobby actualizado = this.hobbyDAO.actualizar(em, hobby);
            em.getTransaction().commit();
            return actualizado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al actualizar hobby: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarHobby(Long idHobby) throws Exception {
        EntityManager em = null;
        try {
            if (idHobby == null) {
                throw new Exception("El ID no puede ser nulo para eliminar.");
            }

            em = JpaUtil.getInstance().getEntityManager();
            
            // Validacion: No eliminar si esta asociado a estudiantes
            Long numEstudiantes = this.hobbyDAO.contarEstudiantesPorHobby(em, idHobby);
             if (numEstudiantes > 0) {
                 throw new Exception("No se puede eliminar el hobby porque esta asociado a estudiantes.");
             }

            em.getTransaction().begin();
            boolean eliminado = this.hobbyDAO.eliminar(em, idHobby);
            em.getTransaction().commit();
            return eliminado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            if (e.getMessage().contains("asociado a estudiantes")) {
                throw e;
            }
            throw new Exception("Error en servicio al eliminar hobby: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Hobby buscarPorId(Long idHobby) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.hobbyDAO.buscarPorId(em, idHobby);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar hobby por ID: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Hobby buscarPorNombre(String nombreHobby) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.hobbyDAO.buscarPorNombre(em, nombreHobby);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar hobby por nombre: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Hobby> listarHobbies(int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.hobbyDAO.listar(em, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al listar hobbies: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesPorHobby(Long idHobby, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.hobbyDAO.obtenerEstudiantesPorHobby(em, idHobby, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener estudiantes por hobby: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Hobby> obtenerHobbiesMasPopulares(int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.hobbyDAO.obtenerHobbiesMasPopulares(em, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener hobbies populares: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Hobby> obtenerHobbiesEnComun(Long idEstudiante1, Long idEstudiante2) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.hobbyDAO.obtenerHobbiesEnComun(em, idEstudiante1, idEstudiante2);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener hobbies en comun: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
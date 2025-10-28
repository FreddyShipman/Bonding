package Service;

import Config.JpaUtil;
import DAO.CarreraDAO;
import Domain.Carrera;
import Domain.Estudiante;
import InterfaceDAO.ICarreraDAO;
import InterfaceService.ICarreraService;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class CarreraService implements ICarreraService {

    private ICarreraDAO carreraDAO;

    public CarreraService() {
        this.carreraDAO = new CarreraDAO();
    }

    @Override
    public Carrera crearCarrera(Carrera carrera) throws Exception {
        EntityManager em = null;
        try {
            if (carrera == null || carrera.getNombreCarrera() == null || carrera.getNombreCarrera().trim().isEmpty()) {
                throw new Exception("El nombre de la carrera es obligatorio.");
            }

            em = JpaUtil.getInstance().getEntityManager();

            Carrera existente = this.carreraDAO.buscarPorNombre(em, carrera.getNombreCarrera());
            if (existente != null) {
                throw new Exception("Ya existe una carrera con ese nombre.");
            }

            em.getTransaction().begin();
            this.carreraDAO.crear(em, carrera);
            em.getTransaction().commit();
            return carrera;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al crear carrera: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Carrera actualizarCarrera(Carrera carrera) throws Exception {
        EntityManager em = null;
        try {
            if (carrera == null || carrera.getIdCarrera() == null) {
                throw new Exception("La carrera o su ID no pueden ser nulos para actualizar.");
            }
            if (carrera.getNombreCarrera() == null || carrera.getNombreCarrera().trim().isEmpty()) {
                throw new Exception("El nombre de la carrera es obligatorio.");
            }

            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            Carrera actualizada = this.carreraDAO.actualizar(em, carrera);
            em.getTransaction().commit();
            return actualizada;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al actualizar carrera: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarCarrera(Long idCarrera) throws Exception {
        EntityManager em = null;
        try {
            if (idCarrera == null) {
                throw new Exception("El ID no puede ser nulo para eliminar.");
            }

            em = JpaUtil.getInstance().getEntityManager();
            
            // Validacion adicional: No eliminar si tiene estudiantes
            Long numEstudiantes = this.carreraDAO.contarEstudiantesPorCarrera(em, idCarrera);
            if (numEstudiantes > 0) {
                 throw new Exception("No se puede eliminar la carrera porque tiene estudiantes inscritos.");
            }

            em.getTransaction().begin();
            boolean eliminado = this.carreraDAO.eliminar(em, idCarrera);
            em.getTransaction().commit();
            return eliminado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Simplifica el mensaje si es la excepcion de estudiantes
            if (e.getMessage().contains("estudiantes inscritos")) {
                throw e;
            }
            throw new Exception("Error en servicio al eliminar carrera: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Carrera buscarPorId(Long idCarrera) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.carreraDAO.buscarPorId(em, idCarrera);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar carrera por ID: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Carrera buscarPorNombre(String nombreCarrera) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.carreraDAO.buscarPorNombre(em, nombreCarrera);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar carrera por nombre: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Carrera> listarCarreras(int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.carreraDAO.listar(em, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al listar carreras: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesPorCarrera(Long idCarrera, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.carreraDAO.obtenerEstudiantesPorCarrera(em, idCarrera, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener estudiantes por carrera: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Carrera> obtenerCarrerasMasPopulares(int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.carreraDAO.obtenerCarrerasMasPopulares(em, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener carreras populares: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
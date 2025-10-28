package Service;

import Config.JpaUtil;
import DAO.EstudianteDAO;
import Domain.Estudiante;
import Domain.Hobby;
import Domain.Interes;
import InterfaceDAO.IEstudianteDAO;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import InterfaceService.IEstudianteService;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class EstudianteService implements IEstudianteService {

    private IEstudianteDAO estudianteDAO;

    public EstudianteService() {
        this.estudianteDAO = new EstudianteDAO();
    }

    @Override
    public Estudiante crearEstudiante(Estudiante estudiante) throws Exception {
        EntityManager em = null;
        try {
            if (estudiante == null) {
                throw new Exception("El estudiante no puede ser nulo.");
            }
            if (estudiante.getCorreoInstitucional() == null || estudiante.getCorreoInstitucional().trim().isEmpty()) {
                throw new Exception("El correo institucional es obligatorio.");
            }
            if (estudiante.getNombreEstudiante() == null || estudiante.getNombreEstudiante().trim().isEmpty()) {
                throw new Exception("El nombre es obligatorio.");
            }
            em = JpaUtil.getInstance().getEntityManager();
            
            Estudiante existente = this.estudianteDAO.buscarPorCorreo(em, estudiante.getCorreoInstitucional());
            if (existente != null) {
                throw new Exception("El correo institucional ya esta registrado.");
            }

            em.getTransaction().begin();
            
            this.estudianteDAO.crear(em, estudiante);
            
            em.getTransaction().commit();
            return estudiante;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al crear estudiante: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Estudiante actualizarEstudiante(Estudiante estudiante) throws Exception {
        EntityManager em = null;
        try {
            if (estudiante == null || estudiante.getIdEstudiante() == null) {
                throw new Exception("El estudiante o su ID no pueden ser nulos para actualizar.");
            }
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            
            Estudiante actualizado = this.estudianteDAO.actualizar(em, estudiante);
            
            em.getTransaction().commit();
            return actualizado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al actualizar estudiante: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarEstudiante(Long idEstudiante) throws Exception {
        EntityManager em = null;
        try {
            if (idEstudiante == null) {
                throw new Exception("El ID no puede ser nulo para eliminar.");
            }
            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            
            boolean eliminado = this.estudianteDAO.eliminar(em, idEstudiante);
            
            em.getTransaction().commit();
            return eliminado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al eliminar estudiante: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Estudiante buscarPorId(Long idEstudiante) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.buscarPorId(em, idEstudiante);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar por ID: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Estudiante autenticar(String correoInstitucional, String contrasena) throws Exception {
        EntityManager em = null;
        try {
            if (correoInstitucional == null || correoInstitucional.trim().isEmpty() || contrasena == null || contrasena.isEmpty()) {
                throw new Exception("Correo y contrasena son obligatorios.");
            }
            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.autenticar(em, correoInstitucional, contrasena);
        } catch (Exception e) {
            throw new Exception("Error en servicio al autenticar: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> buscarEstudiantesCompatibles(Long idEstudiante, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.buscarEstudiantesCompatibles(em, idEstudiante, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar compatibles: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> obtenerMatches(Long idEstudiante, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.obtenerMatches(em, idEstudiante, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener matches: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Hobby> obtenerHobbies(Long idEstudiante) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.obtenerHobbies(em, idEstudiante);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener hobbies: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Interes> obtenerIntereses(Long idEstudiante) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.obtenerIntereses(em, idEstudiante);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener intereses: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Map<String, Long> obtenerEstadisticas(Long idEstudiante) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.obtenerEstadisticas(em, idEstudiante);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener estadisticas: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> listarEstudiantes(int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.listar(em, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al listar estudiantes: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
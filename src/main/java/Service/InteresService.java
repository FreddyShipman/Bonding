package Service;

import Config.JpaUtil;
import DAO.InteresDAO;
import Domain.Estudiante;
import Domain.Interes;
import InterfaceDAO.IInteresDAO;
import InterfaceService.IInteresService;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class InteresService implements IInteresService {

    private IInteresDAO interesDAO;

    public InteresService() {
        this.interesDAO = new InteresDAO();
    }

    @Override
    public Interes crearInteres(Interes interes) throws Exception {
        EntityManager em = null;
        try {
            if (interes == null || interes.getNombreInteres() == null || interes.getNombreInteres().trim().isEmpty()) {
                throw new Exception("El nombre del interes es obligatorio.");
            }
            if (interes.getCategoria() == null || interes.getCategoria().trim().isEmpty()) {
                throw new Exception("La categoria del interes es obligatoria.");
            }

            em = JpaUtil.getInstance().getEntityManager();

            Interes existente = this.interesDAO.buscarPorNombre(em, interes.getNombreInteres());
            if (existente != null) {
                throw new Exception("Ya existe un interes con ese nombre.");
            }

            em.getTransaction().begin();
            this.interesDAO.crear(em, interes);
            em.getTransaction().commit();
            return interes;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al crear interes: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Interes actualizarInteres(Interes interes) throws Exception {
        EntityManager em = null;
        try {
            if (interes == null || interes.getIdInteres() == null) {
                throw new Exception("El interes o su ID no pueden ser nulos para actualizar.");
            }
            if (interes.getNombreInteres() == null || interes.getNombreInteres().trim().isEmpty()) {
                throw new Exception("El nombre del interes es obligatorio.");
            }
             if (interes.getCategoria() == null || interes.getCategoria().trim().isEmpty()) {
                throw new Exception("La categoria del interes es obligatoria.");
            }

            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            Interes actualizado = this.interesDAO.actualizar(em, interes);
            em.getTransaction().commit();
            return actualizado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al actualizar interes: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarInteres(Long idInteres) throws Exception {
        EntityManager em = null;
        try {
            if (idInteres == null) {
                throw new Exception("El ID no puede ser nulo para eliminar.");
            }

            em = JpaUtil.getInstance().getEntityManager();
            
            Long numEstudiantes = this.interesDAO.contarEstudiantesPorInteres(em, idInteres);
             if (numEstudiantes > 0) {
                 throw new Exception("No se puede eliminar el interes porque esta asociado a estudiantes.");
             }

            em.getTransaction().begin();
            boolean eliminado = this.interesDAO.eliminar(em, idInteres);
            em.getTransaction().commit();
            return eliminado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
             if (e.getMessage().contains("asociado a estudiantes")) {
                throw e;
            }
            throw new Exception("Error en servicio al eliminar interes: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Interes buscarPorId(Long idInteres) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interesDAO.buscarPorId(em, idInteres);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar interes por ID: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Interes buscarPorNombre(String nombreInteres) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interesDAO.buscarPorNombre(em, nombreInteres);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar interes por nombre: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Interes> buscarPorCategoria(String categoria, int limit) throws Exception {
         if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interesDAO.buscarPorCategoria(em, categoria, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar interes por categoria: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Interes> listarIntereses(int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interesDAO.listar(em, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al listar intereses: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesPorInteres(Long idInteres, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interesDAO.obtenerEstudiantesPorInteres(em, idInteres, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener estudiantes por interes: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Interes> obtenerInteresesMasPopulares(int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interesDAO.obtenerInteresesMasPopulares(em, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener intereses populares: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Interes> obtenerInteresesEnComun(Long idEstudiante1, Long idEstudiante2) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interesDAO.obtenerInteresesEnComun(em, idEstudiante1, idEstudiante2);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener intereses en comun: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<String> obtenerCategoriasUnicas() throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interesDAO.obtenerCategoriasUnicas(em);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener categorias unicas: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
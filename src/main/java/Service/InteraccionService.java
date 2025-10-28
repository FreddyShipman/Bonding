package Service;

import DAO.InteraccionDAO;
import Domain.Estudiante;
import Domain.Interaccion;
import InterfaceDAO.IInteraccionDAO;
import Config.JpaUtil;
import InterfaceService.IInteraccionService;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class InteraccionService implements IInteraccionService {

    private IInteraccionDAO interaccionDAO;

    public InteraccionService() {
        this.interaccionDAO = new InteraccionDAO();
    }

    @Override
    public Interaccion crearInteraccion(Interaccion interaccion) throws Exception {
        EntityManager em = null;
        try {
            if (interaccion == null) {
                throw new Exception("La interaccion no puede ser nula.");
            }
            if (interaccion.getTipoInteraccion() == null || interaccion.getTipoInteraccion().trim().isEmpty()) {
                throw new Exception("El tipo de interaccion es obligatorio.");
            }
            if (interaccion.getFechaInteraccion() == null) {
                throw new Exception("La fecha de interaccion es obligatoria.");
            }
            if (interaccion.getEstudiantes() == null || interaccion.getEstudiantes().isEmpty()) {
                 throw new Exception("Debe haber al menos un estudiante asociado.");
             }
             if (interaccion.getFechaInteraccion().isAfter(LocalDate.now())) {
                 throw new Exception("La fecha de interaccion no puede ser futura.");
             }

            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            this.interaccionDAO.crear(em, interaccion);
            em.getTransaction().commit();
            return interaccion;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al crear interaccion: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Interaccion actualizarInteraccion(Interaccion interaccion) throws Exception {
        EntityManager em = null;
        try {
            if (interaccion == null || interaccion.getIdInteraccion() == null) {
                throw new Exception("La interaccion o su ID no pueden ser nulos para actualizar.");
            }
            if (interaccion.getTipoInteraccion() == null || interaccion.getTipoInteraccion().trim().isEmpty()) {
                throw new Exception("El tipo de interaccion es obligatorio.");
            }
             if (interaccion.getFechaInteraccion() == null) {
                throw new Exception("La fecha de interaccion es obligatoria.");
            }
             if (interaccion.getFechaInteraccion().isAfter(LocalDate.now())) {
                 throw new Exception("La fecha de interaccion no puede ser futura.");
             }

            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            Interaccion actualizada = this.interaccionDAO.actualizar(em, interaccion);
            em.getTransaction().commit();
            return actualizada;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al actualizar interaccion: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarInteraccion(Long idInteraccion) throws Exception {
        EntityManager em = null;
        try {
            if (idInteraccion == null) {
                throw new Exception("El ID no puede ser nulo para eliminar.");
            }

            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            boolean eliminado = this.interaccionDAO.eliminar(em, idInteraccion);
            em.getTransaction().commit();
            return eliminado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al eliminar interaccion: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Interaccion buscarPorId(Long idInteraccion) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interaccionDAO.buscarPorId(em, idInteraccion);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar interaccion por ID: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Interaccion> buscarPorTipo(String tipoInteraccion, int limit) throws Exception {
         if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interaccionDAO.buscarPorTipo(em, tipoInteraccion, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar interaccion por tipo: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Interaccion> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin, int limit) throws Exception {
         if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interaccionDAO.buscarPorRangoFechas(em, fechaInicio, fechaFin, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar interaccion por fechas: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Interaccion> listarInteracciones(int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interaccionDAO.listar(em, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al listar interacciones: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesPorInteraccion(Long idInteraccion, int limit) throws Exception {
        if (limit > 100) {
            throw new Exception("El limite no puede ser mayor a 100.");
        }
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interaccionDAO.obtenerEstudiantesPorInteraccion(em, idInteraccion, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener estudiantes por interaccion: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Interaccion> obtenerInteraccionesEnComun(Long idEstudiante1, Long idEstudiante2) throws Exception {
        EntityManager em = null;
        try {
            em = JpaUtil.getInstance().getEntityManager();
            return this.interaccionDAO.obtenerInteraccionesEnComun(em, idEstudiante1, idEstudiante2);
        } catch (Exception e) {
            throw new Exception("Error en servicio al obtener interacciones en comun: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
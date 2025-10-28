package Service;

import Config.JpaUtil;
import DAO.PreferenciaDAO;
import Domain.Estudiante;
import Domain.Preferencia;
import InterfaceDAO.IPreferenciaDAO;
import InterfaceService.IPreferenciaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 *
 * @author @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class PreferenciaService implements IPreferenciaService {

    private IPreferenciaDAO preferenciaDAO;

    public PreferenciaService() {
        this.preferenciaDAO = new PreferenciaDAO();
    }

    @Override
    public Preferencia guardarPreferencias(Preferencia preferencia) throws Exception {
        EntityManager em = null;
        try {
            if (preferencia == null || preferencia.getEstudiante() == null || preferencia.getEstudiante().getIdEstudiante() == null) {
                throw new Exception("La preferencia y el estudiante asociado no pueden ser nulos.");
            }
            if (preferencia.getEdadMinima() == null || preferencia.getEdadMaxima() == null || preferencia.getEdadMinima() > preferencia.getEdadMaxima()) {
                throw new Exception("El rango de edad no es valido.");
            }
             if (preferencia.getCarreraPreferida() == null || preferencia.getCarreraPreferida().getIdCarrera() == null) {
                throw new Exception("La carrera preferida es obligatoria.");
            }

            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();

            Preferencia guardada;
            Preferencia existente = null;
            try {
                existente = this.preferenciaDAO.buscarPorEstudiante(em, preferencia.getEstudiante().getIdEstudiante());
            } catch (NoResultException ignored) {}

            if (existente != null) {
                existente.setGeneroPreferido(preferencia.getGeneroPreferido());
                existente.setEdadMinima(preferencia.getEdadMinima());
                existente.setEdadMaxima(preferencia.getEdadMaxima());
                existente.setCarreraPreferida(preferencia.getCarreraPreferida());
                guardada = this.preferenciaDAO.actualizar(em, existente);
            } else {
                // Crea una nueva
                guardada = this.preferenciaDAO.crear(em, preferencia);
            }

            em.getTransaction().commit();
            return guardada;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al guardar preferencias: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Preferencia buscarPorEstudiante(Long idEstudiante) throws Exception {
        EntityManager em = null;
        try {
             if (idEstudiante == null) {
                throw new Exception("El ID del estudiante no puede ser nulo.");
            }
            em = JpaUtil.getInstance().getEntityManager();
            return this.preferenciaDAO.buscarPorEstudiante(em, idEstudiante);
        } catch (NoResultException e) {
            return null; // Es valido no tener preferencias
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar preferencias por estudiante: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarPreferencias(Long idPreferencia) throws Exception {
        EntityManager em = null;
        try {
            if (idPreferencia == null) {
                throw new Exception("El ID no puede ser nulo para eliminar.");
            }

            em = JpaUtil.getInstance().getEntityManager();
            em.getTransaction().begin();
            boolean eliminado = this.preferenciaDAO.eliminar(em, idPreferencia);
            em.getTransaction().commit();
            return eliminado;

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error en servicio al eliminar preferencias: " + e.getMessage(), e);
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
            return this.preferenciaDAO.buscarEstudiantesCompatibles(em, idEstudiante, limit);
        } catch (Exception e) {
            throw new Exception("Error en servicio al buscar estudiantes compatibles: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
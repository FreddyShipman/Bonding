package DAO;

import Domain.Estudiante;
import Domain.Preferencia;
import InterfaceDAO.IPreferenciaDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IPreferenciaDAO para gestionar operaciones CRUD
 * y consultas específicas sobre la entidad Preferencia.
 *
 * @author Bonding Team
 */

public class PreferenciaDAO extends GenericDAO<Preferencia, Long> implements IPreferenciaDAO {

    public PreferenciaDAO() {
        super(Preferencia.class);
    }

    @Override
    public Preferencia buscarPorEstudiante(EntityManager em, Long idEstudiante) throws Exception {
        try {
            TypedQuery<Preferencia> query = em.createQuery(
                "SELECT p FROM Preferencia p WHERE p.estudiante.idEstudiante = :idEst", Preferencia.class);
            query.setParameter("idEst", idEstudiante);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al buscar preferencia por estudiante: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesConPreferencias(EntityManager em, int limit) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT p.estudiante FROM Preferencia p", Estudiante.class);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiantes con preferencias: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Preferencia> buscarPorGeneroPreferido(EntityManager em, String generoPreferido, int limit) throws Exception {
        try {
            TypedQuery<Preferencia> query = em.createQuery(
                "SELECT p FROM Preferencia p WHERE p.generoPreferido = :genero", Preferencia.class);
            query.setParameter("genero", generoPreferido);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar preferencias por genero: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Preferencia> buscarPorRangoEdad(EntityManager em, Integer edadMinima, Integer edadMaxima, int limit) throws Exception {
        try {
            TypedQuery<Preferencia> query = em.createQuery(
                "SELECT p FROM Preferencia p WHERE p.edadMinima >= :min AND p.edadMaxima <= :max", Preferencia.class);
            query.setParameter("min", edadMinima);
            query.setParameter("max", edadMaxima);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar preferencias por rango de edad: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Preferencia> buscarPorCarreraPreferida(EntityManager em, Long idCarrera, int limit) throws Exception {
        try {
            TypedQuery<Preferencia> query = em.createQuery(
                "SELECT p FROM Preferencia p WHERE p.carreraPreferida.idCarrera = :idCarrera", Preferencia.class);
            query.setParameter("idCarrera", idCarrera);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar preferencias por carrera: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> buscarEstudiantesCompatibles(EntityManager em, Long idEstudiante, int limit) throws Exception {
        try {
            Preferencia prefs;
            try {
                prefs = this.buscarPorEstudiante(em, idEstudiante);
                if (prefs == null) {
                    return java.util.Collections.emptyList();
                }
            } catch (NoResultException e) {
                return java.util.Collections.emptyList();
            }

            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e " +
                "WHERE e.idEstudiante != :idEst " + 
                "AND e.carrera.idCarrera = :idCarreraPref " + 
                "AND e.idEstudiante NOT IN (SELECT l.estudianteReceptor.idEstudiante FROM Like l WHERE l.estudianteEmisor.idEstudiante = :idEst)",
                Estudiante.class);

            query.setParameter("idEst", idEstudiante);
            query.setParameter("idCarreraPref", prefs.getCarreraPreferida().getIdCarrera());
            
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
            
        } catch (Exception e) {
            throw new Exception("Error al buscar estudiantes compatibles: " + e.getMessage(), e);
        }
    }
}
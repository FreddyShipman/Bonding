package DAO;

import Config.JpaUtil;
import Domain.Estudiante;
import Domain.Preferencia;
import InterfaceDAO.IPreferenciaDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IPreferenciaDAO para gestionar operaciones CRUD
 * y consultas específicas sobre la entidad Preferencia.
 *
 * @author Bonding Team
 */
public class PreferenciaDAO implements IPreferenciaDAO {

    @Override
    public Preferencia crear(Preferencia entidad) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();
            return entidad;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al crear la preferencia: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Preferencia buscarPorId(Long id) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            Preferencia preferencia = em.find(Preferencia.class, id);
            if (preferencia == null) {
                throw new Exception("Preferencia con ID " + id + " no encontrada");
            }
            return preferencia;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Preferencia> listar(int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Preferencia> query = em.createQuery("SELECT p FROM Preferencia p", Preferencia.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Preferencia actualizar(Preferencia entidad) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Preferencia preferenciaActualizada = em.merge(entidad);
            em.getTransaction().commit();
            return preferenciaActualizada;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar la preferencia: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminar(Long id) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Preferencia preferencia = em.find(Preferencia.class, id);
            if (preferencia != null) {
                em.remove(preferencia);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar la preferencia: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Preferencia buscarPorEstudiante(Long idEstudiante) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Preferencia> query = em.createQuery(
                "SELECT p FROM Preferencia p WHERE p.estudiante.idEstudiante = :idEstudiante",
                Preferencia.class
            );
            query.setParameter("idEstudiante", idEstudiante);
            List<Preferencia> resultado = query.getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesConPreferencias(int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e WHERE e.preferencia IS NOT NULL",
                Estudiante.class
            );
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Preferencia> buscarPorGeneroPreferido(String generoPreferido, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Preferencia> query = em.createQuery(
                "SELECT p FROM Preferencia p WHERE p.generoPreferido = :genero",
                Preferencia.class
            );
            query.setParameter("genero", generoPreferido);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Preferencia> buscarPorRangoEdad(Integer edadMinima, Integer edadMaxima, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Preferencia> query = em.createQuery(
                "SELECT p FROM Preferencia p WHERE p.edadMinima = :edadMin AND p.edadMaxima = :edadMax",
                Preferencia.class
            );
            query.setParameter("edadMin", edadMinima);
            query.setParameter("edadMax", edadMaxima);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Preferencia> buscarPorCarreraPreferida(Long idCarrera, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Preferencia> query = em.createQuery(
                "SELECT p FROM Preferencia p WHERE p.carreraPreferida.idCarrera = :idCarrera",
                Preferencia.class
            );
            query.setParameter("idCarrera", idCarrera);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Estudiante> buscarEstudiantesCompatibles(Long idEstudiante, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            // Primero obtenemos las preferencias del estudiante
            TypedQuery<Preferencia> queryPref = em.createQuery(
                "SELECT p FROM Preferencia p WHERE p.estudiante.idEstudiante = :idEstudiante",
                Preferencia.class
            );
            queryPref.setParameter("idEstudiante", idEstudiante);
            List<Preferencia> prefs = queryPref.getResultList();

            if (prefs.isEmpty()) {
                return List.of();
            }

            Preferencia preferencia = prefs.get(0);

            // Construimos la consulta para buscar estudiantes compatibles
            StringBuilder jpql = new StringBuilder(
                "SELECT e FROM Estudiante e WHERE e.idEstudiante != :idEstudiante"
            );

            // Filtro por género si está especificado
            if (preferencia.getGeneroPreferido() != null && !preferencia.getGeneroPreferido().isEmpty()) {
                jpql.append(" AND e.genero = :genero");
            }

            // Filtro por edad si está especificado
            if (preferencia.getEdadMinima() != null && preferencia.getEdadMaxima() != null) {
                jpql.append(" AND e.edad BETWEEN :edadMin AND :edadMax");
            }

            // Filtro por carrera si está especificado
            if (preferencia.getCarreraPreferida() != null) {
                jpql.append(" AND e.carrera.idCarrera = :idCarrera");
            }

            TypedQuery<Estudiante> query = em.createQuery(jpql.toString(), Estudiante.class);
            query.setParameter("idEstudiante", idEstudiante);

            if (preferencia.getGeneroPreferido() != null && !preferencia.getGeneroPreferido().isEmpty()) {
                query.setParameter("genero", preferencia.getGeneroPreferido());
            }

            if (preferencia.getEdadMinima() != null && preferencia.getEdadMaxima() != null) {
                query.setParameter("edadMin", preferencia.getEdadMinima());
                query.setParameter("edadMax", preferencia.getEdadMaxima());
            }

            if (preferencia.getCarreraPreferida() != null) {
                query.setParameter("idCarrera", preferencia.getCarreraPreferida().getIdCarrera());
            }

            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

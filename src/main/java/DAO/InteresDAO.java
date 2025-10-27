package DAO;

import Config.JpaUtil;
import Domain.Estudiante;
import Domain.Interes;
import InterfaceDAO.IInteresDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IInteresDAO para gestionar operaciones CRUD
 * y consultas específicas sobre la entidad Interes.
 *
 * @author Bonding Team
 */
public class InteresDAO implements IInteresDAO {

    @Override
    public Interes crear(Interes entidad) throws Exception {
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
            throw new Exception("Error al crear el interés: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Interes buscarPorId(Long id) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            Interes interes = em.find(Interes.class, id);
            if (interes == null) {
                throw new Exception("Interés con ID " + id + " no encontrado");
            }
            return interes;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Interes> listar(int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Interes> query = em.createQuery("SELECT i FROM Interes i", Interes.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Interes actualizar(Interes entidad) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Interes interesActualizado = em.merge(entidad);
            em.getTransaction().commit();
            return interesActualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al actualizar el interés: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminar(Long id) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            Interes interes = em.find(Interes.class, id);
            if (interes != null) {
                em.remove(interes);
                em.getTransaction().commit();
                return true;
            }
            em.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception("Error al eliminar el interés: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    @Override
    public Interes buscarPorNombre(String nombreInteres) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Interes> query = em.createQuery(
                "SELECT i FROM Interes i WHERE i.nombreInteres = :nombre",
                Interes.class
            );
            query.setParameter("nombre", nombreInteres);
            List<Interes> resultado = query.getResultList();
            return resultado.isEmpty() ? null : resultado.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Interes> buscarPorCategoria(String categoria, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Interes> query = em.createQuery(
                "SELECT i FROM Interes i WHERE i.categoria = :categoria",
                Interes.class
            );
            query.setParameter("categoria", categoria);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<String> obtenerCategoriasUnicas() throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<String> query = em.createQuery(
                "SELECT DISTINCT i.categoria FROM Interes i ORDER BY i.categoria",
                String.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Long contarEstudiantesPorInteres(Long idInteres) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(e) FROM Estudiante e JOIN e.intereses i WHERE i.idInteres = :idInteres",
                Long.class
            );
            query.setParameter("idInteres", idInteres);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesPorInteres(Long idInteres, int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e JOIN e.intereses i WHERE i.idInteres = :idInteres",
                Estudiante.class
            );
            query.setParameter("idInteres", idInteres);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Interes> obtenerInteresesMasPopulares(int limit) throws Exception {
        if (limit > 100) {
            throw new IllegalArgumentException("El límite no puede exceder 100");
        }
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Interes> query = em.createQuery(
                "SELECT i FROM Interes i LEFT JOIN i.estudiantes e " +
                "GROUP BY i.idInteres ORDER BY COUNT(e) DESC",
                Interes.class
            );
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Interes> obtenerInteresesEnComun(Long idEstudiante1, Long idEstudiante2) throws Exception {
        EntityManager em = JpaUtil.getInstance().getEntityManager();
        try {
            TypedQuery<Interes> query = em.createQuery(
                "SELECT i FROM Interes i JOIN i.estudiantes e1 JOIN i.estudiantes e2 " +
                "WHERE e1.idEstudiante = :id1 AND e2.idEstudiante = :id2",
                Interes.class
            );
            query.setParameter("id1", idEstudiante1);
            query.setParameter("id2", idEstudiante2);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

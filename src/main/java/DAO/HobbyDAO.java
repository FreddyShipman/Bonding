package DAO;

import Domain.Hobby;
import Domain.Estudiante;
import InterfaceDAO.IHobbyDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class HobbyDAO extends GenericDAO<Hobby, Long> implements IHobbyDAO {

    public HobbyDAO() {
        super(Hobby.class);
    }

    @Override
    public Hobby buscarPorNombre(EntityManager em, String nombreHobby) throws Exception {
        try {
            TypedQuery<Hobby> query = em.createQuery(
                "SELECT h FROM Hobby h WHERE h.nombreHobby = :nombre", Hobby.class);
            query.setParameter("nombre", nombreHobby);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al buscar hobby por nombre: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Hobby> listarHobbiesConEstudiantes(EntityManager em) throws Exception {
        try {
            TypedQuery<Hobby> query = em.createQuery(
                "SELECT h FROM Hobby h WHERE h.estudiantes IS NOT EMPTY", Hobby.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al listar hobbies con estudiantes: " + e.getMessage(), e);
        }
    }

    @Override
    public Long contarEstudiantesPorHobby(EntityManager em, Long idHobby) throws Exception {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(e) FROM Estudiante e JOIN e.hobbies h WHERE h.idHobby = :idHobby", Long.class);
            query.setParameter("idHobby", idHobby);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Error al contar estudiantes por hobby: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> obtenerEstudiantesPorHobby(EntityManager em, Long idHobby, int limit) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e JOIN e.hobbies h WHERE h.idHobby = :idHobby", Estudiante.class);
            query.setParameter("idHobby", idHobby);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener estudiantes por hobby: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Hobby> obtenerHobbiesMasPopulares(EntityManager em, int limit) throws Exception {
        try {
            TypedQuery<Hobby> query = em.createQuery(
                "SELECT h FROM Hobby h JOIN h.estudiantes e " +
                "GROUP BY h " +
                "ORDER BY COUNT(e) DESC", Hobby.class);
            
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener hobbies mas populares: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Hobby> obtenerHobbiesEnComun(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception {
        try {
            TypedQuery<Hobby> query = em.createQuery(
                "SELECT h FROM Hobby h JOIN h.estudiantes e1 JOIN h.estudiantes e2 " +
                "WHERE e1.idEstudiante = :idEst1 AND e2.idEstudiante = :idEst2", Hobby.class);
            query.setParameter("idEst1", idEstudiante1);
            query.setParameter("idEst2", idEstudiante2);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener hobbies en comun: " + e.getMessage(), e);
        }
    }
}
package DAO;

import Domain.Estudiante;
import Domain.Like;
import InterfaceDAO.ILikeDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class LikeDAO extends GenericDAO<Like, Long> implements ILikeDAO {

    public LikeDAO() {
        super(Like.class);
    }

    @Override
    public List<Like> obtenerLikesPorEmisor(EntityManager em, Long idEstudianteEmisor, int limit) throws Exception {
        try {
            TypedQuery<Like> query = em.createQuery(
                "SELECT l FROM Like l WHERE l.estudianteEmisor.idEstudiante = :idEmisor", Like.class);
            query.setParameter("idEmisor", idEstudianteEmisor);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener likes por emisor: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Like> obtenerLikesPorReceptor(EntityManager em, Long idEstudianteReceptor, int limit) throws Exception {
        try {
            TypedQuery<Like> query = em.createQuery(
                "SELECT l FROM Like l WHERE l.estudianteReceptor.idEstudiante = :idReceptor", Like.class);
            query.setParameter("idReceptor", idEstudianteReceptor);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener likes por receptor: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Like> buscarPorFecha(EntityManager em, LocalDateTime fecha, int limit) throws Exception {
        try {
            TypedQuery<Like> query = em.createQuery(
                "SELECT l FROM Like l WHERE l.fechaLike = :fecha", Like.class);
            query.setParameter("fecha", fecha);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar like por fecha: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Like> buscarPorRangoFechas(EntityManager em, LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception {
        try {
            TypedQuery<Like> query = em.createQuery(
                "SELECT l FROM Like l WHERE l.fechaLike BETWEEN :inicio AND :fin", Like.class);
            query.setParameter("inicio", fechaInicio);
            query.setParameter("fin", fechaFin);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar like por rango de fechas: " + e.getMessage(), e);
        }
    }

    @Override
    public Like verificarLikeExistente(EntityManager em, Long idEmisor, Long idReceptor) throws Exception {
        try {
            TypedQuery<Like> query = em.createQuery(
                "SELECT l FROM Like l WHERE l.estudianteEmisor.idEstudiante = :idEmisor AND l.estudianteReceptor.idEstudiante = :idReceptor", Like.class);
            query.setParameter("idEmisor", idEmisor);
            query.setParameter("idReceptor", idReceptor);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al verificar like existente: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean verificarLikeMutuo(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception {
        try {
            TypedQuery<Long> query1 = em.createQuery(
                "SELECT COUNT(l) FROM Like l WHERE l.estudianteEmisor.idEstudiante = :id1 AND l.estudianteReceptor.idEstudiante = :id2", Long.class);
            query1.setParameter("id1", idEstudiante1);
            query1.setParameter("id2", idEstudiante2);
            boolean A_likes_B = query1.getSingleResult() > 0;

            TypedQuery<Long> query2 = em.createQuery(
                "SELECT COUNT(l) FROM Like l WHERE l.estudianteEmisor.idEstudiante = :id2 AND l.estudianteReceptor.idEstudiante = :id1", Long.class);
            query2.setParameter("id1", idEstudiante1);
            query2.setParameter("id2", idEstudiante2);
            boolean B_likes_A = query2.getSingleResult() > 0;

            return A_likes_B && B_likes_A;
        } catch (Exception e) {
            throw new Exception("Error al verificar like mutuo: " + e.getMessage(), e);
        }
    }

    @Override
    public Long contarLikesEnviados(EntityManager em, Long idEstudiante) throws Exception {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(l) FROM Like l WHERE l.estudianteEmisor.idEstudiante = :idEst", Long.class);
            query.setParameter("idEst", idEstudiante);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Error al contar likes enviados: " + e.getMessage(), e);
        }
    }

    @Override
    public Long contarLikesRecibidos(EntityManager em, Long idEstudiante) throws Exception {
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(l) FROM Like l WHERE l.estudianteReceptor.idEstudiante = :idEst", Long.class);
            query.setParameter("idEst", idEstudiante);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new Exception("Error al contar likes recibidos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> obtenerLikesPendientesDeResponder(EntityManager em, Long idEstudiante, int limit) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT l.estudianteEmisor FROM Like l " +
                "WHERE l.estudianteReceptor.idEstudiante = :idEst " +
                "AND l.estudianteEmisor.idEstudiante NOT IN " +
                "(SELECT l2.estudianteReceptor.idEstudiante FROM Like l2 WHERE l2.estudianteEmisor.idEstudiante = :idEst)",
                Estudiante.class);
            
            query.setParameter("idEst", idEstudiante);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener likes pendientes: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Like> obtenerLikesMutuos(EntityManager em, Long idEstudiante, int limit) throws Exception {
        try {
            TypedQuery<Like> query = em.createQuery(
                "SELECT l1 FROM Like l1 " +
                "WHERE l1.estudianteEmisor.idEstudiante = :idEst " +
                "AND l1.estudianteReceptor.idEstudiante IN " +
                "(SELECT l2.estudianteEmisor.idEstudiante FROM Like l2 WHERE l2.estudianteReceptor.idEstudiante = :idEst)",
                Like.class);

            query.setParameter("idEst", idEstudiante);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener likes mutuos: " + e.getMessage(), e);
        }
    }
}
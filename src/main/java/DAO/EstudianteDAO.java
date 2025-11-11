package DAO;

import Domain.Estudiante;
import Domain.Hobby;
import Domain.Interaccion;
import Domain.Interes;
import Domain.Like;
import Domain.Preferencia;
import InterfaceDAO.IEstudianteDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class EstudianteDAO extends GenericDAO<Estudiante, Long> implements IEstudianteDAO {

    public EstudianteDAO() {
        super(Estudiante.class);
    }

    @Override
    public Estudiante buscarPorId(EntityManager em, Long id) throws Exception {
        try {
            // Usar JOIN FETCH para cargar eagerly las colecciones
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT DISTINCT e FROM Estudiante e " +
                "LEFT JOIN FETCH e.hobbies " +
                "LEFT JOIN FETCH e.intereses " +
                "LEFT JOIN FETCH e.carrera " +
                "WHERE e.idEstudiante = :id", Estudiante.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al buscar por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public Estudiante buscarPorCorreo(EntityManager em, String correoInstitucional) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e WHERE e.correoInstitucional = :correo", Estudiante.class);
            query.setParameter("correo", correoInstitucional);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al buscar por correo: " + e.getMessage(), e);
        }
    }

    @Override
    public Estudiante autenticar(EntityManager em, String correoInstitucional, String contrasena) throws Exception {
        try {
            // Usar JOIN FETCH para cargar eagerly las colecciones
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT DISTINCT e FROM Estudiante e " +
                "LEFT JOIN FETCH e.hobbies " +
                "LEFT JOIN FETCH e.intereses " +
                "LEFT JOIN FETCH e.carrera " +
                "WHERE e.correoInstitucional = :correo AND e.contrasena = :pass", Estudiante.class);
            query.setParameter("correo", correoInstitucional);
            query.setParameter("pass", contrasena);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new Exception("Error al autenticar: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> buscarPorCarrera(EntityManager em, Long idCarrera, int limit) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e WHERE e.carrera.idCarrera = :idCarrera", Estudiante.class);
            query.setParameter("idCarrera", idCarrera);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar por carrera: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> buscarPorNombre(EntityManager em, String nombre, int limit) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e WHERE LOWER(e.nombreEstudiante) LIKE LOWER(:nombre)", Estudiante.class);
            query.setParameter("nombre", "%" + nombre + "%");
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar por nombre: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> obtenerMatches(EntityManager em, Long idEstudiante, int limit) throws Exception {
        try {
            // Usar JOIN FETCH para cargar eagerly las colecciones
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT DISTINCT est FROM Match m JOIN m.estudiantes est " +
                "LEFT JOIN FETCH est.hobbies " +
                "LEFT JOIN FETCH est.intereses " +
                "LEFT JOIN FETCH est.carrera " +
                "WHERE m IN (SELECT m2 FROM Estudiante e JOIN e.matches m2 WHERE e.idEstudiante = :idEst) " +
                "AND est.idEstudiante != :idEst", Estudiante.class);
            query.setParameter("idEst", idEstudiante);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener matches: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Like> obtenerLikesEnviados(EntityManager em, Long idEstudiante, int limit) throws Exception {
        try {
            TypedQuery<Like> query = em.createQuery(
                "SELECT l FROM Like l WHERE l.estudianteEmisor.idEstudiante = :idEmisor", Like.class);
            query.setParameter("idEmisor", idEstudiante);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener likes enviados: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Like> obtenerLikesRecibidos(EntityManager em, Long idEstudiante, int limit) throws Exception {
        try {
            TypedQuery<Like> query = em.createQuery(
                "SELECT l FROM Like l WHERE l.estudianteReceptor.idEstudiante = :idReceptor", Like.class);
            query.setParameter("idReceptor", idEstudiante);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener likes recibidos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Hobby> obtenerHobbies(EntityManager em, Long idEstudiante) throws Exception {
        try {
            TypedQuery<Hobby> query = em.createQuery(
                "SELECT h FROM Estudiante e JOIN e.hobbies h WHERE e.idEstudiante = :idEst", Hobby.class);
            query.setParameter("idEst", idEstudiante);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener hobbies: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Interes> obtenerIntereses(EntityManager em, Long idEstudiante) throws Exception {
        try {
            TypedQuery<Interes> query = em.createQuery(
                "SELECT i FROM Estudiante e JOIN e.intereses i WHERE e.idEstudiante = :idEst", Interes.class);
            query.setParameter("idEst", idEstudiante);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener intereses: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Interaccion> obtenerInteracciones(EntityManager em, Long idEstudiante) throws Exception {
        try {
            TypedQuery<Interaccion> query = em.createQuery(
                "SELECT i FROM Estudiante e JOIN e.interacciones i WHERE e.idEstudiante = :idEst", Interaccion.class);
            query.setParameter("idEst", idEstudiante);
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al obtener interacciones: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> buscarEstudiantesCompatibles(EntityManager em, Long idEstudiante, int limit) throws Exception {
        try {
            // Intentar obtener preferencias del estudiante
            Preferencia prefs = null;
            try {
                prefs = em.createQuery("SELECT p FROM Preferencia p WHERE p.estudiante.idEstudiante = :idEst", Preferencia.class)
                          .setParameter("idEst", idEstudiante)
                          .getSingleResult();
            } catch (NoResultException e) {
                // No hay preferencias, continuar sin filtrar por ellas
            }

            // Construir query dinámicamente según las preferencias disponibles
            // Usar LEFT JOIN FETCH para cargar eagerly las colecciones y evitar LazyInitializationException
            StringBuilder queryStr = new StringBuilder(
                "SELECT DISTINCT e FROM Estudiante e " +
                "LEFT JOIN FETCH e.hobbies " +
                "LEFT JOIN FETCH e.intereses " +
                "LEFT JOIN FETCH e.carrera " +
                "WHERE e.idEstudiante != :idEst " +
                "AND e.idEstudiante NOT IN (SELECT l.estudianteReceptor.idEstudiante FROM Like l WHERE l.estudianteEmisor.idEstudiante = :idEst)");

            // Filtrar por género si existe preferencia
            if (prefs != null && prefs.getGeneroPreferido() != null && !prefs.getGeneroPreferido().trim().isEmpty()) {
                queryStr.append(" AND e.genero = :genero");
            }

            // Filtrar por carrera si existe preferencia y no es nula
            if (prefs != null && prefs.getCarreraPreferida() != null) {
                queryStr.append(" AND e.carrera.idCarrera = :idCarreraPref");
            }

            // Filtrar por edad si existe preferencia de rango
            if (prefs != null && prefs.getEdadMinima() != null) {
                queryStr.append(" AND e.edad >= :edadMin");
            }
            if (prefs != null && prefs.getEdadMaxima() != null) {
                queryStr.append(" AND e.edad <= :edadMax");
            }

            TypedQuery<Estudiante> query = em.createQuery(queryStr.toString(), Estudiante.class);
            query.setParameter("idEst", idEstudiante);

            // Establecer parámetros según las preferencias
            if (prefs != null) {
                if (prefs.getGeneroPreferido() != null && !prefs.getGeneroPreferido().trim().isEmpty()) {
                    query.setParameter("genero", prefs.getGeneroPreferido());
                }
                if (prefs.getCarreraPreferida() != null) {
                    query.setParameter("idCarreraPref", prefs.getCarreraPreferida().getIdCarrera());
                }
                if (prefs.getEdadMinima() != null) {
                    query.setParameter("edadMin", prefs.getEdadMinima());
                }
                if (prefs.getEdadMaxima() != null) {
                    query.setParameter("edadMax", prefs.getEdadMaxima());
                }
            }

            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();

        } catch (Exception e) {
            throw new Exception("Error al buscar estudiantes compatibles: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> buscarPorHobbiesSimilares(EntityManager em, Long idEstudiante, int minimoHobbiesComunes, int limit) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e JOIN e.hobbies h " +
                "WHERE e.idEstudiante != :idEst " +
                "AND h IN (SELECT h2 FROM Estudiante e2 JOIN e2.hobbies h2 WHERE e2.idEstudiante = :idEst) " +
                "GROUP BY e " +
                "HAVING COUNT(h) >= :minComunes", Estudiante.class);
            
            query.setParameter("idEst", idEstudiante);
            query.setParameter("minComunes", (long) minimoHobbiesComunes);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar por hobbies similares: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Estudiante> buscarPorInteresesSimilares(EntityManager em, Long idEstudiante, int minimoInteresesComunes, int limit) throws Exception {
        try {
            TypedQuery<Estudiante> query = em.createQuery(
                "SELECT e FROM Estudiante e JOIN e.intereses i " +
                "WHERE e.idEstudiante != :idEst " +
                "AND i IN (SELECT i2 FROM Estudiante e2 JOIN e2.intereses i2 WHERE e2.idEstudiante = :idEst) " +
                "GROUP BY e " +
                "HAVING COUNT(i) >= :minComunes", Estudiante.class);
            
            query.setParameter("idEst", idEstudiante);
            query.setParameter("minComunes", (long) minimoInteresesComunes);
            query.setMaxResults(Math.min(limit, 100));
            return query.getResultList();
        } catch (Exception e) {
            throw new Exception("Error al buscar por intereses similares: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean verificarMatchMutuo(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception {
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
            throw new Exception("Error al verificar match mutuo: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Long> obtenerEstadisticas(EntityManager em, Long idEstudiante) throws Exception {
        try {
            Map<String, Long> stats = new HashMap<>();

            Long likesEnviados = em.createQuery(
                "SELECT COUNT(l) FROM Like l WHERE l.estudianteEmisor.idEstudiante = :idEst", Long.class)
                .setParameter("idEst", idEstudiante)
                .getSingleResult();
            stats.put("likesEnviados", likesEnviados);

            Long likesRecibidos = em.createQuery(
                "SELECT COUNT(l) FROM Like l WHERE l.estudianteReceptor.idEstudiante = :idEst", Long.class)
                .setParameter("idEst", idEstudiante)
                .getSingleResult();
            stats.put("likesRecibidos", likesRecibidos);

            Long numMatches = em.createQuery(
                "SELECT COUNT(m) FROM Estudiante e JOIN e.matches m WHERE e.idEstudiante = :idEst", Long.class)
                .setParameter("idEst", idEstudiante)
                .getSingleResult();
            stats.put("matches", numMatches);

            return stats;
            
        } catch (Exception e) {
            throw new Exception("Error al obtener estadisticas: " + e.getMessage(), e);
        }
    }
}
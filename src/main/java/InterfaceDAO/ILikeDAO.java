package InterfaceDAO;

import Domain.Like;
import Domain.Estudiante;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz DAO para la entidad Like (MeGusta).
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 */

public interface ILikeDAO extends IGenericDAO<Like, Long> {

    /**
     * Obtiene todos los likes enviados por un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudianteEmisor El ID del estudiante emisor
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de likes enviados
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> obtenerLikesPorEmisor(EntityManager em, Long idEstudianteEmisor, int limit) throws Exception;

    /**
     * Obtiene todos los likes recibidos por un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudianteReceptor El ID del estudiante receptor
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de likes recibidos
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> obtenerLikesPorReceptor(EntityManager em, Long idEstudianteReceptor, int limit) throws Exception;

    /**
     * Busca likes por fecha especifica.
     *
     * @param em El EntityManager
     * @param fecha La fecha a buscar
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de likes en esa fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> buscarPorFecha(EntityManager em, LocalDateTime fecha, int limit) throws Exception;

    /**
     * Busca likes en un rango de fechas.
     *
     * @param em El EntityManager
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de likes en el rango de fechas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> buscarPorRangoFechas(EntityManager em, LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception;

    /**
     * Verifica si existe un like de un estudiante a otro.
     *
     * @param em El EntityManager
     * @param idEmisor ID del estudiante emisor
     * @param idReceptor ID del estudiante receptor
     * @return El like si existe, null en caso contrario
     * @throws Exception Si ocurre un error durante la consulta
     */
    Like verificarLikeExistente(EntityManager em, Long idEmisor, Long idReceptor) throws Exception;

    /**
     * Verifica si hay like mutuo entre dos estudiantes.
     *
     * @param em El EntityManager
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return true si ambos se han dado like, false en caso contrario
     * @throws Exception Si ocurre un error durante la consulta
     */
    boolean verificarLikeMutuo(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception;

    /**
     * Cuenta el numero de likes enviados por un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @return Numero de likes enviados
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarLikesEnviados(EntityManager em, Long idEstudiante) throws Exception;

    /**
     * Cuenta el numero de likes recibidos por un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @return Numero de likes recibidos
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarLikesRecibidos(EntityManager em, Long idEstudiante) throws Exception;

    /**
     * Obtiene los estudiantes que han dado like a un estudiante
     * pero que aun no han recibido like de vuelta (likes pendientes).
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante receptor
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes con likes pendientes
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerLikesPendientesDeResponder(EntityManager em, Long idEstudiante, int limit) throws Exception;

    /**
     * Obtiene los likes mutuos de un estudiante (likes bidireccionales).
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de likes mutuos
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> obtenerLikesMutuos(EntityManager em, Long idEstudiante, int limit) throws Exception;
}
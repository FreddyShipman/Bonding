package InterfaceDAO;

import Domain.Like;
import Domain.Estudiante;
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
     * @param idEstudianteEmisor El ID del estudiante emisor
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de likes enviados
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> obtenerLikesPorEmisor(Long idEstudianteEmisor, int limit) throws Exception;

    /**
     * Obtiene todos los likes recibidos por un estudiante.
     *
     * @param idEstudianteReceptor El ID del estudiante receptor
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de likes recibidos
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> obtenerLikesPorReceptor(Long idEstudianteReceptor, int limit) throws Exception;

    /**
     * Busca likes por fecha específica.
     *
     * @param fecha La fecha a buscar
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de likes en esa fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> buscarPorFecha(LocalDateTime fecha, int limit) throws Exception;

    /**
     * Busca likes en un rango de fechas.
     *
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de likes en el rango de fechas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception;

    /**
     * Verifica si existe un like de un estudiante a otro.
     *
     * @param idEmisor ID del estudiante emisor
     * @param idReceptor ID del estudiante receptor
     * @return El like si existe, null en caso contrario
     * @throws Exception Si ocurre un error durante la consulta
     */
    Like verificarLikeExistente(Long idEmisor, Long idReceptor) throws Exception;

    /**
     * Verifica si hay like mutuo entre dos estudiantes.
     *
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return true si ambos se han dado like, false en caso contrario
     * @throws Exception Si ocurre un error durante la consulta
     */
    boolean verificarLikeMutuo(Long idEstudiante1, Long idEstudiante2) throws Exception;

    /**
     * Cuenta el número de likes enviados por un estudiante.
     *
     * @param idEstudiante El ID del estudiante
     * @return Número de likes enviados
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarLikesEnviados(Long idEstudiante) throws Exception;

    /**
     * Cuenta el número de likes recibidos por un estudiante.
     *
     * @param idEstudiante El ID del estudiante
     * @return Número de likes recibidos
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarLikesRecibidos(Long idEstudiante) throws Exception;

    /**
     * Obtiene los estudiantes que han dado like a un estudiante
     * pero que aún no han recibido like de vuelta (likes pendientes).
     *
     * @param idEstudiante El ID del estudiante receptor
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes con likes pendientes
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerLikesPendientesDeResponder(Long idEstudiante, int limit) throws Exception;

    /**
     * Obtiene los likes mutuos de un estudiante (likes bidireccionales).
     *
     * @param idEstudiante El ID del estudiante
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de likes mutuos
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> obtenerLikesMutuos(Long idEstudiante, int limit) throws Exception;
}

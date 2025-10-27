package InterfaceDAO;

import Domain.Match;
import Domain.Estudiante;
import Domain.Chat;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz DAO para la entidad Match (Matches).
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 */

public interface IMatchDAO extends IGenericDAO<Match, Long> {

    /**
     * Obtiene todos los matches de un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de matches del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Match> obtenerMatchesPorEstudiante(EntityManager em, Long idEstudiante, int limit) throws Exception;

    /**
     * Busca matches por fecha especifica.
     *
     * @param em El EntityManager
     * @param fecha La fecha a buscar
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de matches en esa fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Match> buscarPorFecha(EntityManager em, LocalDateTime fecha, int limit) throws Exception;

    /**
     * Busca matches en un rango de fechas.
     *
     * @param em El EntityManager
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de matches en el rango de fechas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Match> buscarPorRangoFechas(EntityManager em, LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception;

    /**
     * Verifica si existe un match entre dos estudiantes.
     *
     * @param em El EntityManager
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return El match si existe, null en caso contrario
     * @throws Exception Si ocurre un error durante la consulta
     */
    Match verificarMatchExistente(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception;

    /**
     * Cuenta el numero de matches de un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @return Numero de matches
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarMatchesPorEstudiante(EntityManager em, Long idEstudiante) throws Exception;

    /**
     * Obtiene el chat asociado a un match.
     *
     * @param em El EntityManager
     * @param idMatch El ID del match
     * @return El chat del match o null si no existe
     * @throws Exception Si ocurre un error durante la consulta
     */
    Chat obtenerChatDelMatch(EntityManager em, Long idMatch) throws Exception;

    /**
     * Obtiene los estudiantes que participan en un match.
     *
     * @param em El EntityManager
     * @param idMatch El ID del match
     * @return Lista de estudiantes en el match
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesDelMatch(EntityManager em, Long idMatch) throws Exception;

    /**
     * Obtiene los matches mas recientes del sistema.
     *
     * @param em El EntityManager
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de matches ordenados por fecha descendente
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Match> obtenerMatchesRecientes(EntityManager em, int limit) throws Exception;

    /**
     * Obtiene los matches de un estudiante que tienen chat activo.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de matches con chat
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Match> obtenerMatchesConChat(EntityManager em, Long idEstudiante, int limit) throws Exception;

    /**
     * Cuenta el numero total de matches en el sistema en un periodo.
     *
     * @param em El EntityManager
     * @param fechaInicio Fecha de inicio del periodo
     * @param fechaFin Fecha de fin del periodo
     * @return Numero de matches en el periodo
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarMatchesPorPeriodo(EntityManager em, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws Exception;
}
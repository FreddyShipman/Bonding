package InterfaceDAO;

import Domain.Match;
import Domain.Estudiante;
import Domain.Chat;
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
     * @param idEstudiante El ID del estudiante
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de matches del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Match> obtenerMatchesPorEstudiante(Long idEstudiante, int limit) throws Exception;

    /**
     * Busca matches por fecha específica.
     *
     * @param fecha La fecha a buscar
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de matches en esa fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Match> buscarPorFecha(LocalDateTime fecha, int limit) throws Exception;

    /**
     * Busca matches en un rango de fechas.
     *
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de matches en el rango de fechas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Match> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception;

    /**
     * Verifica si existe un match entre dos estudiantes.
     *
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return El match si existe, null en caso contrario
     * @throws Exception Si ocurre un error durante la consulta
     */
    Match verificarMatchExistente(Long idEstudiante1, Long idEstudiante2) throws Exception;

    /**
     * Cuenta el número de matches de un estudiante.
     *
     * @param idEstudiante El ID del estudiante
     * @return Número de matches
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarMatchesPorEstudiante(Long idEstudiante) throws Exception;

    /**
     * Obtiene el chat asociado a un match.
     *
     * @param idMatch El ID del match
     * @return El chat del match o null si no existe
     * @throws Exception Si ocurre un error durante la consulta
     */
    Chat obtenerChatDelMatch(Long idMatch) throws Exception;

    /**
     * Obtiene los estudiantes que participan en un match.
     *
     * @param idMatch El ID del match
     * @return Lista de estudiantes en el match
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesDelMatch(Long idMatch) throws Exception;

    /**
     * Obtiene los matches más recientes del sistema.
     *
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de matches ordenados por fecha descendente
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Match> obtenerMatchesRecientes(int limit) throws Exception;

    /**
     * Obtiene los matches de un estudiante que tienen chat activo.
     *
     * @param idEstudiante El ID del estudiante
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de matches con chat
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Match> obtenerMatchesConChat(Long idEstudiante, int limit) throws Exception;

    /**
     * Cuenta el número total de matches en el sistema en un periodo.
     *
     * @param fechaInicio Fecha de inicio del periodo
     * @param fechaFin Fecha de fin del periodo
     * @return Número de matches en el periodo
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarMatchesPorPeriodo(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws Exception;
}

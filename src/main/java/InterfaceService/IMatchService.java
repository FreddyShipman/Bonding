package InterfaceService;

import Domain.Chat;
import Domain.Estudiante;
import Domain.Match;
import java.util.List;

/**
 * Interfaz para la logica de negocio relacionada con los 'Matches'.
 * Define las operaciones de alto nivel, como consultar matches
 * y obtener la informacion asociada a ellos (como el chat).
 * 
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
public interface IMatchService {

    /**
     * Busca un match por su ID.
     *
     * @param idMatch El ID del match
     * @return El match encontrado o null
     * @throws Exception Si ocurre un error
     */
    Match buscarPorId(Long idMatch) throws Exception;

    /**
     * Elimina un match. Esto podria implicar eliminar el chat asociado
     * (logica de cascada definida en la entidad o en el servicio).
     *
     * @param idMatch El ID del match a eliminar
     * @return true si se elimino, false si no se encontro
     * @throws Exception Si ocurre un error
     */
    boolean eliminarMatch(Long idMatch) throws Exception;

    /**
     * Obtiene todos los matches de un estudiante.
     *
     * @param idEstudiante El ID del estudiante
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de matches
     * @throws Exception Si ocurre un error
     */
    List<Match> obtenerMatchesPorEstudiante(Long idEstudiante, int limit) throws Exception;

    /**
     * Verifica si ya existe un match entre dos estudiantes.
     *
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return El Match si existe, null si no
     * @throws Exception Si ocurre un error
     */
    Match verificarMatchExistente(Long idEstudiante1, Long idEstudiante2) throws Exception;

    /**
     * Obtiene el chat asociado a un match.
     * Si el chat no existe, este metodo podria crearlo.
     *
     * @param idMatch El ID del match
     * @return El Chat asociado
     * @throws Exception Si ocurre un error
     */
    Chat obtenerOCrearChatDelMatch(Long idMatch) throws Exception;

    /**
     * Obtiene los estudiantes que participan en un match.
     *
     * @param idMatch El ID del match
     * @return Lista de los dos estudiantes del match
     * @throws Exception Si ocurre un error
     */
    List<Estudiante> obtenerEstudiantesDelMatch(Long idMatch) throws Exception;

    /**
     * Obtiene los matches mas recientes del sistema.
     *
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de matches recientes
     * @throws Exception Si ocurre un error
     */
    List<Match> obtenerMatchesRecientes(int limit) throws Exception;
    
    Match buscarMatchPorEstudiantes(Long idEstudiante1, Long idEstudiante2) throws Exception;
}
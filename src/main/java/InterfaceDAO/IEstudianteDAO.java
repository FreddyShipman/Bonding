package InterfaceDAO;

import Domain.Estudiante;
import Domain.Like;
import Domain.Match;
import Domain.Hobby;
import Domain.Interes;
import Domain.Interaccion;
import java.util.List;

/**
 * Interfaz DAO para la entidad Estudiante.
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author JOSE ALFREDO GUZMAN MORENO, Alex Adrian Nieblas Moreno - 252524, 252865
 */
public interface IEstudianteDAO extends IGenericDAO<Estudiante, Long> {

    /**
     * Busca un estudiante por su correo institucional.
     *
     * @param correoInstitucional El correo institucional del estudiante
     * @return El estudiante encontrado o null si no existe
     * @throws Exception Si ocurre un error durante la búsqueda
     */
    Estudiante buscarPorCorreo(String correoInstitucional) throws Exception;

    /**
     * Autentica un estudiante con correo y contraseña.
     *
     * @param correoInstitucional El correo institucional
     * @param contrasena La contraseña del estudiante
     * @return El estudiante autenticado o null si las credenciales son incorrectas
     * @throws Exception Si ocurre un error durante la autenticación
     */
    Estudiante autenticar(String correoInstitucional, String contrasena) throws Exception;

    /**
     * Busca estudiantes por carrera.
     *
     * @param idCarrera El ID de la carrera
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes de esa carrera
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarPorCarrera(Long idCarrera, int limit) throws Exception;

    /**
     * Busca estudiantes por nombre (búsqueda parcial).
     *
     * @param nombre El nombre a buscar (puede ser parcial)
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes que coinciden con el nombre
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarPorNombre(String nombre, int limit) throws Exception;

    /**
     * Obtiene los estudiantes con los que un estudiante ha hecho match.
     *
     * @param idEstudiante El ID del estudiante
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes con match
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerMatches(Long idEstudiante, int limit) throws Exception;

    /**
     * Obtiene los likes que ha enviado un estudiante.
     *
     * @param idEstudiante El ID del estudiante emisor
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de likes enviados
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> obtenerLikesEnviados(Long idEstudiante, int limit) throws Exception;

    /**
     * Obtiene los likes que ha recibido un estudiante.
     *
     * @param idEstudiante El ID del estudiante receptor
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de likes recibidos
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> obtenerLikesRecibidos(Long idEstudiante, int limit) throws Exception;

    /**
     * Obtiene los hobbies de un estudiante.
     *
     * @param idEstudiante El ID del estudiante
     * @return Lista de hobbies del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Hobby> obtenerHobbies(Long idEstudiante) throws Exception;

    /**
     * Obtiene los intereses de un estudiante.
     *
     * @param idEstudiante El ID del estudiante
     * @return Lista de intereses del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interes> obtenerIntereses(Long idEstudiante) throws Exception;

    /**
     * Obtiene las interacciones de un estudiante.
     *
     * @param idEstudiante El ID del estudiante
     * @return Lista de interacciones del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> obtenerInteracciones(Long idEstudiante) throws Exception;

    /**
     * Busca estudiantes compatibles según las preferencias de un estudiante.
     * Consulta compleja que combina género, edad, carrera y considera
     * estudiantes con los que NO ha interactuado previamente.
     *
     * @param idEstudiante El ID del estudiante
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes compatibles
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarEstudiantesCompatibles(Long idEstudiante, int limit) throws Exception;

    /**
     * Obtiene estudiantes con hobbies similares a un estudiante dado.
     *
     * @param idEstudiante El ID del estudiante
     * @param minimoHobbiesComunes Número mínimo de hobbies en común
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes con hobbies similares
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarPorHobbiesSimilares(Long idEstudiante, int minimoHobbiesComunes, int limit) throws Exception;

    /**
     * Obtiene estudiantes con intereses similares a un estudiante dado.
     *
     * @param idEstudiante El ID del estudiante
     * @param minimoInteresesComunes Número mínimo de intereses en común
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes con intereses similares
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarPorInteresesSimilares(Long idEstudiante, int minimoInteresesComunes, int limit) throws Exception;

    /**
     * Verifica si existe un match mutuo entre dos estudiantes.
     *
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return true si existe match mutuo, false en caso contrario
     * @throws Exception Si ocurre un error durante la consulta
     */
    boolean verificarMatchMutuo(Long idEstudiante1, Long idEstudiante2) throws Exception;

    /**
     * Obtiene estadísticas de actividad de un estudiante.
     * (Número de likes enviados, recibidos, matches, etc.)
     *
     * @param idEstudiante El ID del estudiante
     * @return Mapa con estadísticas del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    java.util.Map<String, Long> obtenerEstadisticas(Long idEstudiante) throws Exception;
}

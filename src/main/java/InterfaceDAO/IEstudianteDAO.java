package InterfaceDAO;

import Domain.Estudiante;
import Domain.Like;
import Domain.Hobby;
import Domain.Interes;
import Domain.Interaccion;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;

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
     * @param em El EntityManager
     * @param correoInstitucional El correo institucional del estudiante
     * @return El estudiante encontrado o null si no existe
     * @throws Exception Si ocurre un error durante la busqueda
     */
    Estudiante buscarPorCorreo(EntityManager em, String correoInstitucional) throws Exception;

    /**
     * Autentica un estudiante con correo y contrasena.
     *
     * @param em El EntityManager
     * @param correoInstitucional El correo institucional
     * @param contrasena La contrasena del estudiante
     * @return El estudiante autenticado o null si las credenciales son incorrectas
     * @throws Exception Si ocurre un error durante la autenticacion
     */
    Estudiante autenticar(EntityManager em, String correoInstitucional, String contrasena) throws Exception;

    /**
     * Busca estudiantes por carrera.
     *
     * @param em El EntityManager
     * @param idCarrera El ID de la carrera
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes de esa carrera
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarPorCarrera(EntityManager em, Long idCarrera, int limit) throws Exception;

    /**
     * Busca estudiantes por nombre (busqueda parcial).
     *
     * @param em El EntityManager
     * @param nombre El nombre a buscar (puede ser parcial)
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes que coinciden con el nombre
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarPorNombre(EntityManager em, String nombre, int limit) throws Exception;

    /**
     * Obtiene los estudiantes con los que un estudiante ha hecho match.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes con match
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerMatches(EntityManager em, Long idEstudiante, int limit) throws Exception;

    /**
     * Obtiene los likes que ha enviado un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante emisor
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de likes enviados
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> obtenerLikesEnviados(EntityManager em, Long idEstudiante, int limit) throws Exception;

    /**
     * Obtiene los likes que ha recibido un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante receptor
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de likes recibidos
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Like> obtenerLikesRecibidos(EntityManager em, Long idEstudiante, int limit) throws Exception;

    /**
     * Obtiene los hobbies de un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @return Lista de hobbies del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Hobby> obtenerHobbies(EntityManager em, Long idEstudiante) throws Exception;

    /**
     * Obtiene los intereses de un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @return Lista de intereses del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interes> obtenerIntereses(EntityManager em, Long idEstudiante) throws Exception;

    /**
     * Obtiene las interacciones de un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @return Lista de interacciones del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> obtenerInteracciones(EntityManager em, Long idEstudiante) throws Exception;

    /**
     * Busca estudiantes compatibles segun las preferencias de un estudiante.
     * Consulta compleja que combina genero, edad, carrera y considera
     * estudiantes con los que NO ha interactuado previamente.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes compatibles
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarEstudiantesCompatibles(EntityManager em, Long idEstudiante, int limit) throws Exception;

    /**
     * Obtiene estudiantes con hobbies similares a un estudiante dado.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @param minimoHobbiesComunes Numero minimo de hobbies en comun
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes con hobbies similares
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarPorHobbiesSimilares(EntityManager em, Long idEstudiante, int minimoHobbiesComunes, int limit) throws Exception;

    /**
     * Obtiene estudiantes con intereses similares a un estudiante dado.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @param minimoInteresesComunes Numero minimo de intereses en comun
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes con intereses similares
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarPorInteresesSimilares(EntityManager em, Long idEstudiante, int minimoInteresesComunes, int limit) throws Exception;

    /**
     * Verifica si existe un match mutuo entre dos estudiantes.
     *
     * @param em El EntityManager
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return true si existe match mutuo, false en caso contrario
     * @throws Exception Si ocurre un error durante la consulta
     */
    boolean verificarMatchMutuo(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception;

    /**
     * Obtiene estadisticas de actividad de un estudiante.
     * (Numero de likes enviados, recibidos, matches, etc.)
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @return Mapa con estadisticas del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    Map<String, Long> obtenerEstadisticas(EntityManager em, Long idEstudiante) throws Exception;
}
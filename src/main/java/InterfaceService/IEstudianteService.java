package InterfaceService;

import Domain.Estudiante;
import Domain.Hobby;
import Domain.Interes;
import Excepciones.BondingException;
import java.util.List;
import java.util.Map;

/**
 * Interfaz para la logica de negocio de Estudiante.
 * Define las operaciones de alto nivel que la aplicacion puede realizar.
 * La implementacion de esta interfaz manejara las transacciones
 * y las validaciones de reglas de negocio.
 * 
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
public interface IEstudianteService {

    /**
     * Registra un nuevo estudiante en el sistema.
     * Valida que el correo no este duplicado y que los campos
     * obligatorios esten presentes.
     *
     * @param estudiante El nuevo estudiante a crear
     * @return El estudiante creado con su ID
     * @throws BondingException Si falla la validacion o la persistencia
     */
    Estudiante crearEstudiante(Estudiante estudiante) throws BondingException;

    /**
     * Actualiza la informacion de un estudiante existente.
     *
     * @param estudiante El estudiante con la informacion actualizada
     * @return El estudiante actualizado
     * @throws BondingException Si falla la validacion o la actualizacion
     */
    Estudiante actualizarEstudiante(Estudiante estudiante) throws BondingException;

    /**
     * Elimina un estudiante del sistema por su ID.
     *
     * @param idEstudiante El ID del estudiante a eliminar
     * @return true si se elimino, false si no se encontro
     * @throws BondingException Si ocurre un error durante la eliminacion
     */
    boolean eliminarEstudiante(Long idEstudiante) throws BondingException;

    /**
     * Busca un estudiante por su ID.
     *
     * @param idEstudiante El ID del estudiante
     * @return El estudiante encontrado
     * @throws BondingException Si ocurre un error en la consulta o no se encuentra
     */
    Estudiante buscarPorId(Long idEstudiante) throws BondingException;

    /**
     * Autentica un estudiante usando su correo y contrasena.
     *
     * @param correoInstitucional El correo del estudiante
     * @param contrasena La contrasena del estudiante
     * @return El estudiante si la autenticacion es exitosa
     * @throws BondingException Si las credenciales son invalidas o ocurre un error
     */
    Estudiante autenticar(String correoInstitucional, String contrasena) throws BondingException;

    /**
     * Obtiene la lista de estudiantes compatibles para el "swipe deck".
     * Aplica las reglas de negocio de las preferencias del estudiante.
     *
     * @param idEstudiante El ID del estudiante que esta buscando
     * @param limit Numero maximo de resultados
     * @return Lista de estudiantes compatibles
     * @throws BondingException Si ocurre un error en la consulta
     */
    List<Estudiante> buscarEstudiantesCompatibles(Long idEstudiante, int limit) throws BondingException;

    /**
     * Obtiene la lista de estudiantes con los que el usuario ya hizo match.
     *
     * @param idEstudiante El ID del estudiante
     * @param limit Numero maximo de resultados
     * @return Lista de estudiantes con match
     * @throws BondingException Si ocurre un error en la consulta
     */
    List<Estudiante> obtenerMatches(Long idEstudiante, int limit) throws BondingException;

    /**
     * Obtiene los hobbies de un estudiante.
     *
     * @param idEstudiante El ID del estudiante
     * @return Lista de hobbies
     * @throws BondingException Si ocurre un error en la consulta
     */
    List<Hobby> obtenerHobbies(Long idEstudiante) throws BondingException;

    /**
     * Obtiene los intereses de un estudiante.
     *
     * @param idEstudiante El ID del estudiante
     * @return Lista de intereses
     * @throws BondingException Si ocurre un error en la consulta
     */
    List<Interes> obtenerIntereses(Long idEstudiante) throws BondingException;

    /**
     * Obtiene estadisticas basicas del perfil de un estudiante.
     * (Likes enviados, recibidos, numero de matches).
     *
     * @param idEstudiante El ID del estudiante
     * @return Un mapa con las estadisticas
     * @throws BondingException Si ocurre un error en la consulta
     */
    Map<String, Long> obtenerEstadisticas(Long idEstudiante) throws BondingException;

    /**
     * Lista todos los estudiantes registrados en el sistema.
     *
     * @param limit Numero maximo de resultados
     * @return Lista de todos los estudiantes
     * @throws BondingException Si ocurre un error en la consulta
     */
    List<Estudiante> listarEstudiantes(int limit) throws BondingException;
}
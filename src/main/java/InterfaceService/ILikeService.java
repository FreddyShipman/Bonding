package InterfaceService;

import Domain.Estudiante;
import Domain.Like;
import java.util.List;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

/**
 * Interfaz para la logica de negocio relacionada con los 'Likes'.
 * Define las operaciones de alto nivel, como crear un like (que puede
 * resultar en un match) y consultar likes.
 */
public interface ILikeService {

    /**
     * Crea un nuevo 'Like'.
     * Este metodo contiene la logica de negocio principal:
     * 1. Valida que el like sea valido (emisor, receptor no nulos).
     * 2. Valida que el like no exista previamente.
     * 3. Guarda el nuevo like.
     * 4. Verifica si existe un like mutuo.
     * 5. Si existe like mutuo, crea un nuevo Match.
     * Todo esto ocurre en una sola transaccion.
     *
     * @param like El objeto Like a crear (solo necesita emisor y receptor)
     * @return El Like creado
     * @throws Exception Si falla la validacion o la persistencia
     */
    boolean crearLike(Like like) throws Exception;

    /**
     * Elimina un 'Like' (ej. "unlike").
     *
     * @param idLike El ID del like a eliminar
     * @return true si se elimino, false si no se encontro
     * @throws Exception Si ocurre un error durante la eliminacion
     */
    boolean eliminarLike(Long idLike) throws Exception;

    /**
     * Obtiene la lista de likes que un estudiante ha recibido.
     *
     * @param idEstudianteReceptor El ID del estudiante que recibio los likes
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de likes recibidos
     * @throws Exception Si ocurre un error
     */
    List<Like> obtenerLikesRecibidos(Long idEstudianteReceptor, int limit) throws Exception;

    /**
     * Obtiene la lista de likes que un estudiante ha enviado.
     *
     * @param idEstudianteEmisor El ID del estudiante que envio los likes
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de likes enviados
     * @throws Exception Si ocurre un error
     */
    List<Like> obtenerLikesEnviados(Long idEstudianteEmisor, int limit) throws Exception;

    /**
     * Verifica si ya existe un like de un estudiante a otro.
     *
     * @param idEmisor El ID del estudiante emisor
     * @param idReceptor El ID del estudiante receptor
     * @return El Like si existe, null si no
     * @throws Exception Si ocurre un error
     */
    Like verificarLikeExistente(Long idEmisor, Long idReceptor) throws Exception;

    /**
     * Obtiene los estudiantes que le han dado like al usuario, pero
     * a los que el usuario aun no ha respondido.
     *
     * @param idEstudiante El ID del estudiante (receptor)
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de estudiantes
     * @throws Exception Si ocurre un error
     */
    List<Estudiante> obtenerLikesPendientes(Long idEstudiante, int limit) throws Exception;
}
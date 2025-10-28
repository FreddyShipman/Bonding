package InterfaceService;

import Domain.Chat;
import Domain.Mensaje;
import java.util.List;

/**
 * Interfaz para la logica de negocio relacionada con los 'Chats'.
 * Define operaciones como buscar chats, obtener mensajes y gestionar
 * la vida del chat.
 * 
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
public interface IChatService {

    /**
     * Busca un chat por su ID.
     *
     * @param idChat El ID del chat
     * @return El chat encontrado o null
     * @throws Exception Si ocurre un error
     */
    Chat buscarPorId(Long idChat) throws Exception;

    /**
     * Busca un chat asociado a un ID de match especifico.
     *
     * @param idMatch El ID del match
     * @return El chat encontrado o null
     * @throws Exception Si ocurre un error
     */
    Chat buscarPorMatch(Long idMatch) throws Exception;

    /**
     * Obtiene los chats en los que participa un estudiante.
     *
     * @param idEstudiante El ID del estudiante
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de chats del estudiante
     * @throws Exception Si ocurre un error
     */
    List<Chat> obtenerChatsPorEstudiante(Long idEstudiante, int limit) throws Exception;

    /**
     * Obtiene los mensajes de un chat especifico.
     *
     * @param idChat El ID del chat
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de mensajes ordenados por fecha
     * @throws Exception Si ocurre un error
     */
    List<Mensaje> obtenerMensajesDelChat(Long idChat, int limit) throws Exception;

    /**
     * Obtiene el ultimo mensaje enviado en un chat.
     *
     * @param idChat El ID del chat
     * @return El ultimo mensaje o null si no hay mensajes
     * @throws Exception Si ocurre un error
     */
    Mensaje obtenerUltimoMensajeDelChat(Long idChat) throws Exception;

    /**
     * Elimina un chat y potencialmente sus mensajes (depende de la cascada
     * o la logica implementada aqui).
     *
     * @param idChat El ID del chat a eliminar
     * @return true si se elimino, false si no se encontro
     * @throws Exception Si ocurre un error
     */
    boolean eliminarChat(Long idChat) throws Exception;
}
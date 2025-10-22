package InterfaceDAO;

import Domain.Chat;
import Domain.Match;
import Domain.Mensaje;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz DAO para la entidad Chat.
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 */
public interface IChatDAO extends IGenericDAO<Chat, Long> {

    /**
     * Busca un chat por el ID del match asociado.
     *
     * @param idMatch El ID del match
     * @return El chat asociado al match o null si no existe
     * @throws Exception Si ocurre un error durante la búsqueda
     */
    Chat buscarPorMatch(Long idMatch) throws Exception;

    /**
     * Obtiene los chats de un estudiante específico.
     *
     * @param idEstudiante El ID del estudiante
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de chats del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Chat> obtenerChatsPorEstudiante(Long idEstudiante, int limit) throws Exception;

    /**
     * Busca chats por fecha de creación.
     *
     * @param fecha La fecha a buscar
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de chats creados en esa fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Chat> buscarPorFechaCreacion(LocalDateTime fecha, int limit) throws Exception;

    /**
     * Busca chats en un rango de fechas de creación.
     *
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de chats en el rango de fechas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Chat> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception;

    /**
     * Obtiene todos los mensajes de un chat.
     *
     * @param idChat El ID del chat
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de mensajes del chat ordenados por fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> obtenerMensajesDelChat(Long idChat, int limit) throws Exception;

    /**
     * Cuenta el número de mensajes en un chat.
     *
     * @param idChat El ID del chat
     * @return Número de mensajes en el chat
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarMensajesDelChat(Long idChat) throws Exception;

    /**
     * Obtiene el match asociado a un chat.
     *
     * @param idChat El ID del chat
     * @return El match asociado al chat
     * @throws Exception Si ocurre un error durante la consulta
     */
    Match obtenerMatchDelChat(Long idChat) throws Exception;

    /**
     * Obtiene los chats más activos (con más mensajes).
     *
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de chats ordenados por número de mensajes descendente
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Chat> obtenerChatsMasActivos(int limit) throws Exception;

    /**
     * Obtiene los chats sin actividad reciente.
     *
     * @param diasInactividad Número de días sin mensajes
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de chats inactivos
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Chat> obtenerChatsInactivos(int diasInactividad, int limit) throws Exception;

    /**
     * Obtiene el último mensaje enviado en un chat.
     *
     * @param idChat El ID del chat
     * @return El último mensaje del chat o null si no hay mensajes
     * @throws Exception Si ocurre un error durante la consulta
     */
    Mensaje obtenerUltimoMensaje(Long idChat) throws Exception;
}

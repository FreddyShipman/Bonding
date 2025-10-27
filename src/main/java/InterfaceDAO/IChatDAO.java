package InterfaceDAO;

import Domain.Chat;
import Domain.Match;
import Domain.Mensaje;
import jakarta.persistence.EntityManager;
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
     * @param em El EntityManager
     * @param idMatch El ID del match
     * @return El chat asociado al match o null si no existe
     * @throws Exception Si ocurre un error durante la busqueda
     */
    Chat buscarPorMatch(EntityManager em, Long idMatch) throws Exception;

    /**
     * Obtiene los chats de un estudiante especifico.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de chats del estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Chat> obtenerChatsPorEstudiante(EntityManager em, Long idEstudiante, int limit) throws Exception;

    /**
     * Busca chats por fecha de creacion.
     *
     * @param em El EntityManager
     * @param fecha La fecha a buscar
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de chats creados en esa fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Chat> buscarPorFechaCreacion(EntityManager em, LocalDateTime fecha, int limit) throws Exception;

    /**
     * Busca chats en un rango de fechas de creacion.
     *
     * @param em El EntityManager
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de chats en el rango de fechas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Chat> buscarPorRangoFechas(EntityManager em, LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception;

    /**
     * Obtiene todos los mensajes de un chat.
     *
     * @param em El EntityManager
     * @param idChat El ID del chat
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de mensajes del chat ordenados por fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> obtenerMensajesDelChat(EntityManager em, Long idChat, int limit) throws Exception;

    /**
     * Cuenta el numero de mensajes en un chat.
     *
     * @param em El EntityManager
     * @param idChat El ID del chat
     * @return Numero de mensajes en el chat
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarMensajesDelChat(EntityManager em, Long idChat) throws Exception;

    /**
     * Obtiene el match asociado a un chat.
     *
     * @param em El EntityManager
     * @param idChat El ID del chat
     * @return El match asociado al chat
     * @throws Exception Si ocurre un error durante la consulta
     */
    Match obtenerMatchDelChat(EntityManager em, Long idChat) throws Exception;

    /**
     * Obtiene los chats mas activos (con mas mensajes).
     *
     * @param em El EntityManager
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de chats ordenados por numero de mensajes descendente
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Chat> obtenerChatsMasActivos(EntityManager em, int limit) throws Exception;

    /**
     * Obtiene los chats sin actividad reciente.
     *
     * @param em El EntityManager
     * @param diasInactividad Numero de dias sin mensajes
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de chats inactivos
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Chat> obtenerChatsInactivos(EntityManager em, int diasInactividad, int limit) throws Exception;

    /**
     * Obtiene el ultimo mensaje enviado en un chat.
     *
     * @param em El EntityManager
     * @param idChat El ID del chat
     * @return El ultimo mensaje del chat o null si no hay mensajes
     * @throws Exception Si ocurre un error durante la consulta
     */
    Mensaje obtenerUltimoMensaje(EntityManager em, Long idChat) throws Exception;
}
package InterfaceDAO;

import Domain.Mensaje;
import Domain.Chat;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz DAO para la entidad Mensaje.
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 */
public interface IMensajeDAO extends IGenericDAO<Mensaje, Long> {

    /**
     * Obtiene todos los mensajes de un chat específico.
     *
     * @param idChat El ID del chat
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de mensajes del chat ordenados por fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> obtenerMensajesPorChat(Long idChat, int limit) throws Exception;

    /**
     * Obtiene los mensajes enviados por un estudiante en un chat específico.
     *
     * @param idChat El ID del chat
     * @param idEstudianteEmisor El ID del estudiante emisor
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de mensajes del estudiante en ese chat
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> obtenerMensajesPorChatYEmisor(Long idChat, Long idEstudianteEmisor, int limit) throws Exception;

    /**
     * Obtiene todos los mensajes enviados por un estudiante en todos sus chats.
     *
     * @param idEstudianteEmisor El ID del estudiante emisor
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de mensajes enviados por el estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> obtenerMensajesPorEmisor(Long idEstudianteEmisor, int limit) throws Exception;

    /**
     * Busca mensajes por fecha de envío.
     *
     * @param fecha La fecha a buscar
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de mensajes enviados en esa fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> buscarPorFechaEnvio(LocalDateTime fecha, int limit) throws Exception;

    /**
     * Busca mensajes en un rango de fechas.
     *
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de mensajes en el rango de fechas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> buscarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception;

    /**
     * Busca mensajes que contengan una palabra o frase específica.
     *
     * @param textoBusqueda El texto a buscar en el contenido
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de mensajes que contienen el texto
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> buscarPorContenido(String textoBusqueda, int limit) throws Exception;

    /**
     * Cuenta el número de mensajes en un chat.
     *
     * @param idChat El ID del chat
     * @return Número de mensajes en el chat
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarMensajesPorChat(Long idChat) throws Exception;

    /**
     * Cuenta el número de mensajes enviados por un estudiante.
     *
     * @param idEstudianteEmisor El ID del estudiante
     * @return Número de mensajes enviados
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarMensajesPorEmisor(Long idEstudianteEmisor) throws Exception;

    /**
     * Obtiene los mensajes más recientes de un chat.
     *
     * @param idChat El ID del chat
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de mensajes más recientes ordenados por fecha descendente
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> obtenerMensajesRecientes(Long idChat, int limit) throws Exception;

    /**
     * Obtiene el último mensaje enviado en un chat.
     *
     * @param idChat El ID del chat
     * @return El último mensaje del chat o null si no hay mensajes
     * @throws Exception Si ocurre un error durante la consulta
     */
    Mensaje obtenerUltimoMensajeDelChat(Long idChat) throws Exception;

    /**
     * Elimina todos los mensajes de un chat.
     *
     * @param idChat El ID del chat
     * @return Número de mensajes eliminados
     * @throws Exception Si ocurre un error durante la eliminación
     */
    int eliminarMensajesDelChat(Long idChat) throws Exception;

    /**
     * Obtiene el chat al que pertenece un mensaje.
     *
     * @param idMensaje El ID del mensaje
     * @return El chat del mensaje
     * @throws Exception Si ocurre un error durante la consulta
     */
    Chat obtenerChatDelMensaje(Long idMensaje) throws Exception;
}

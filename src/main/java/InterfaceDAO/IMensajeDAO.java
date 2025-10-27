package InterfaceDAO;

import Domain.Mensaje;
import Domain.Chat;
import jakarta.persistence.EntityManager;
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
     * Obtiene todos los mensajes de un chat especifico.
     *
     * @param em El EntityManager
     * @param idChat El ID del chat
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de mensajes del chat ordenados por fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> obtenerMensajesPorChat(EntityManager em, Long idChat, int limit) throws Exception;

    /**
     * Obtiene los mensajes enviados por un estudiante en un chat especifico.
     *
     * @param em El EntityManager
     * @param idChat El ID del chat
     * @param idEstudianteEmisor El ID del estudiante emisor
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de mensajes del estudiante en ese chat
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> obtenerMensajesPorChatYEmisor(EntityManager em, Long idChat, Long idEstudianteEmisor, int limit) throws Exception;

    /**
     * Obtiene todos los mensajes enviados por un estudiante en todos sus chats.
     *
     * @param em El EntityManager
     * @param idEstudianteEmisor El ID del estudiante emisor
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de mensajes enviados por el estudiante
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> obtenerMensajesPorEmisor(EntityManager em, Long idEstudianteEmisor, int limit) throws Exception;

    /**
     * Busca mensajes por fecha de envio.
     *
     * @param em El EntityManager
     * @param fecha La fecha a buscar
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de mensajes enviados en esa fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> buscarPorFechaEnvio(EntityManager em, LocalDateTime fecha, int limit) throws Exception;

    /**
     * Busca mensajes en un rango de fechas.
     *
     * @param em El EntityManager
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de mensajes en el rango de fechas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> buscarPorRangoFechas(EntityManager em, LocalDateTime fechaInicio, LocalDateTime fechaFin, int limit) throws Exception;

    /**
     * Busca mensajes que contengan una palabra o frase especifica.
     *
     * @param em El EntityManager
     * @param textoBusqueda El texto a buscar en el contenido
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de mensajes que contienen el texto
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> buscarPorContenido(EntityManager em, String textoBusqueda, int limit) throws Exception;

    /**
     * Cuenta el numero de mensajes en un chat.
     *
     * @param em El EntityManager
     * @param idChat El ID del chat
     * @return Numero de mensajes en el chat
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarMensajesPorChat(EntityManager em, Long idChat) throws Exception;

    /**
     * Cuenta el numero de mensajes enviados por un estudiante.
     *
     * @param em El EntityManager
     * @param idEstudianteEmisor El ID del estudiante
     * @return Numero de mensajes enviados
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarMensajesPorEmisor(EntityManager em, Long idEstudianteEmisor) throws Exception;

    /**
     * Obtiene los mensajes mas recientes de un chat.
     *
     * @param em El EntityManager
     * @param idChat El ID del chat
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de mensajes mas recientes ordenados por fecha descendente
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Mensaje> obtenerMensajesRecientes(EntityManager em, Long idChat, int limit) throws Exception;

    /**
     * Obtiene el ultimo mensaje enviado en un chat.
     *
     * @param em El EntityManager
     * @param idChat El ID del chat
     * @return El ultimo mensaje del chat o null si no hay mensajes
     * @throws Exception Si ocurre un error durante la consulta
     */
    Mensaje obtenerUltimoMensajeDelChat(EntityManager em, Long idChat) throws Exception;

    /**
     * Elimina todos los mensajes de un chat.
     *
     * @param em El EntityManager
     * @param idChat El ID del chat
     * @return Numero de mensajes eliminados
     * @throws Exception Si ocurre un error durante la eliminacion
     */
    int eliminarMensajesDelChat(EntityManager em, Long idChat) throws Exception;

    /**
     * Obtiene el chat al que pertenece un mensaje.
     *
     * @param em El EntityManager
     * @param idMensaje El ID del mensaje
     * @return El chat del mensaje
     * @throws Exception Si ocurre un error durante la consulta
     */
    Chat obtenerChatDelMensaje(EntityManager em, Long idMensaje) throws Exception;
}
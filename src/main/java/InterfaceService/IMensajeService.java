package InterfaceService;

import Domain.Mensaje;
import java.util.List;

/**
 * Interfaz para la logica de negocio de Mensajes.
 * Define las operaciones de alto nivel, como enviar un mensaje
 * (validando que exista un match/chat) y consultar historiales.
 * 
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
public interface IMensajeService {

    /**
     * Crea un nuevo mensaje.
     * Valida la regla de negocio principal: "Para enviar mensaje debe existir un match"
     * (lo hace verificando que el Chat exista).
     * Tambien valida que el contenido no este vacio.
     *
     * @param mensaje El mensaje a crear (debe tener Chat y EstudianteEmisor)
     * @return El mensaje creado
     * @throws Exception Si falla la validacion o la persistencia
     */
    Mensaje crearMensaje(Mensaje mensaje) throws Exception;

    /**
     * Elimina un mensaje.
     *
     * @param idMensaje El ID del mensaje a eliminar
     * @return true si se elimino, false si no se encontro
     * @throws Exception Si ocurre un error
     */
    boolean eliminarMensaje(Long idMensaje) throws Exception;

    /**
     * Elimina todos los mensajes de un chat.
     *
     * @param idChat El ID del chat
     * @return El numero de mensajes eliminados
     * @throws Exception Si ocurre un error
     */
    int eliminarMensajesDelChat(Long idChat) throws Exception;

    /**
     * Obtiene el historial de mensajes de un chat.
     *
     * @param idChat El ID del chat
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de mensajes del chat
     * @throws Exception Si ocurre un error
     */
    List<Mensaje> obtenerMensajesPorChat(Long idChat, int limit) throws Exception;

    /**
     * Obtiene los mensajes enviados por un estudiante en un chat especifico.
     *
     * @param idChat El ID del chat
     * @param idEstudianteEmisor El ID del estudiante emisor
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de mensajes
     * @throws Exception Si ocurre un error
     */
    List<Mensaje> obtenerMensajesPorChatYEmisor(Long idChat, Long idEstudianteEmisor, int limit) throws Exception;

    /**
     * Busca mensajes que contengan un texto especifico.
     *
     * @param textoBusqueda El texto a buscar
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de mensajes que coinciden
     * @throws Exception Si ocurre un error
     */
    List<Mensaje> buscarPorContenido(String textoBusqueda, int limit) throws Exception;

    /**
     * Obtiene el ultimo mensaje de un chat.
     *
     * @param idChat El ID del chat
     * @return El ultimo mensaje, o null si no hay
     * @throws Exception Si ocurre un error
     */
    Mensaje obtenerUltimoMensajeDelChat(Long idChat) throws Exception;
}
package InterfaceService;

import Domain.Carrera;
import Domain.Estudiante;
import java.util.List;

/**
 * Interfaz para la logica de negocio relacionada con las 'Carreras'.
 * Define operaciones como crear, buscar y listar carreras, asi como
 * obtener informacion relacionada con los estudiantes inscritos.
 * 
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
public interface ICarreraService {

    /**
     * Crea una nueva carrera.
     * Valida que el nombre no este vacio y que no exista previamente.
     *
     * @param carrera La carrera a crear
     * @return La carrera creada
     * @throws Exception Si falla la validacion o la persistencia
     */
    Carrera crearCarrera(Carrera carrera) throws Exception;

    /**
     * Actualiza la informacion de una carrera.
     *
     * @param carrera La carrera con la informacion actualizada
     * @return La carrera actualizada
     * @throws Exception Si falla la validacion o la actualizacion
     */
    Carrera actualizarCarrera(Carrera carrera) throws Exception;

    /**
     * Elimina una carrera por su ID.
     * Podria incluir validaciones adicionales (ej. no eliminar si tiene estudiantes).
     *
     * @param idCarrera El ID de la carrera a eliminar
     * @return true si se elimino, false si no se encontro
     * @throws Exception Si ocurre un error
     */
    boolean eliminarCarrera(Long idCarrera) throws Exception;

    /**
     * Busca una carrera por su ID.
     *
     * @param idCarrera El ID de la carrera
     * @return La carrera encontrada o null
     * @throws Exception Si ocurre un error
     */
    Carrera buscarPorId(Long idCarrera) throws Exception;

    /**
     * Busca una carrera por su nombre exacto.
     *
     * @param nombreCarrera El nombre de la carrera
     * @return La carrera encontrada o null
     * @throws Exception Si ocurre un error
     */
    Carrera buscarPorNombre(String nombreCarrera) throws Exception;

    /**
     * Lista todas las carreras registradas.
     *
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de carreras
     * @throws Exception Si ocurre un error
     */
    List<Carrera> listarCarreras(int limit) throws Exception;

    /**
     * Obtiene los estudiantes inscritos en una carrera especifica.
     *
     * @param idCarrera El ID de la carrera
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de estudiantes
     * @throws Exception Si ocurre un error
     */
    List<Estudiante> obtenerEstudiantesPorCarrera(Long idCarrera, int limit) throws Exception;

    /**
     * Obtiene las carreras mas populares (con mas estudiantes).
     *
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de carreras ordenadas por popularidad
     * @throws Exception Si ocurre un error
     */
    List<Carrera> obtenerCarrerasMasPopulares(int limit) throws Exception;
}
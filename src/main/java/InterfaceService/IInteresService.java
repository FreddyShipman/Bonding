package InterfaceService;

import Domain.Estudiante;
import Domain.Interes;
import java.util.List;

/**
 * Interfaz para la logica de negocio relacionada con los 'Intereses'.
 * Define operaciones como crear, buscar y listar intereses, asi como
 * obtener informacion relacionada con los estudiantes que los comparten.
 * 
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
public interface IInteresService {

    /**
     * Crea un nuevo interes.
     * Valida que el nombre no este vacio y que no exista previamente.
     *
     * @param interes El interes a crear
     * @return El interes creado
     * @throws Exception Si falla la validacion o la persistencia
     */
    Interes crearInteres(Interes interes) throws Exception;

    /**
     * Actualiza la informacion de un interes.
     *
     * @param interes El interes con la informacion actualizada
     * @return El interes actualizado
     * @throws Exception Si falla la validacion o la actualizacion
     */
    Interes actualizarInteres(Interes interes) throws Exception;

    /**
     * Elimina un interes por su ID.
     * Podria incluir validaciones (ej. no eliminar si esta asociado a estudiantes).
     *
     * @param idInteres El ID del interes a eliminar
     * @return true si se elimino, false si no se encontro
     * @throws Exception Si ocurre un error
     */
    boolean eliminarInteres(Long idInteres) throws Exception;

    /**
     * Busca un interes por su ID.
     *
     * @param idInteres El ID del interes
     * @return El interes encontrado o null
     * @throws Exception Si ocurre un error
     */
    Interes buscarPorId(Long idInteres) throws Exception;

    /**
     * Busca un interes por su nombre exacto.
     *
     * @param nombreInteres El nombre del interes
     * @return El interes encontrado o null
     * @throws Exception Si ocurre un error
     */
    Interes buscarPorNombre(String nombreInteres) throws Exception;

    /**
     * Busca intereses por categoria.
     *
     * @param categoria La categoria a buscar
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de intereses de esa categoria
     * @throws Exception Si ocurre un error
     */
    List<Interes> buscarPorCategoria(String categoria, int limit) throws Exception;

    /**
     * Lista todos los intereses registrados.
     *
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de intereses
     * @throws Exception Si ocurre un error
     */
    List<Interes> listarIntereses(int limit) throws Exception;

    /**
     * Obtiene los estudiantes que tienen un interes especifico.
     *
     * @param idInteres El ID del interes
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de estudiantes
     * @throws Exception Si ocurre un error
     */
    List<Estudiante> obtenerEstudiantesPorInteres(Long idInteres, int limit) throws Exception;

    /**
     * Obtiene los intereses mas populares (compartidos por mas estudiantes).
     *
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de intereses ordenados por popularidad
     * @throws Exception Si ocurre un error
     */
    List<Interes> obtenerInteresesMasPopulares(int limit) throws Exception;

    /**
     * Obtiene los intereses en comun entre dos estudiantes.
     *
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return Lista de intereses en comun
     * @throws Exception Si ocurre un error
     */
    List<Interes> obtenerInteresesEnComun(Long idEstudiante1, Long idEstudiante2) throws Exception;

    /**
     * Obtiene todas las categorias unicas de intereses.
     *
     * @return Lista de categorias unicas
     * @throws Exception Si ocurre un error
     */
    List<String> obtenerCategoriasUnicas() throws Exception;
}
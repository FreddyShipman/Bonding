package InterfaceDAO;

import Domain.Interes;
import Domain.Estudiante;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * Interfaz DAO para la entidad Interes.
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 */

public interface IInteresDAO extends IGenericDAO<Interes, Long> {
    /**
     * Busca un interes por su nombre.
     *
     * @param em El EntityManager
     * @param nombreInteres El nombre del interes a buscar
     * @return El interes encontrado o null si no existe
     * @throws Exception Si ocurre un error durante la busqueda
     */
    Interes buscarPorNombre(EntityManager em, String nombreInteres) throws Exception;

    /**
     * Busca intereses por categoria.
     *
     * @param em El EntityManager
     * @param categoria La categoria a buscar
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de intereses de esa categoria
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interes> buscarPorCategoria(EntityManager em, String categoria, int limit) throws Exception;

    /**
     * Obtiene todas las categorias unicas de intereses.
     *
     * @param em El EntityManager
     * @return Lista de categorias unicas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<String> obtenerCategoriasUnicas(EntityManager em) throws Exception;

    /**
     * Cuenta el numero de estudiantes que tienen un interes especifico.
     *
     * @param em El EntityManager
     * @param idInteres El ID del interes
     * @return Numero de estudiantes con ese interes
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarEstudiantesPorInteres(EntityManager em, Long idInteres) throws Exception;

    /**
     * Obtiene los estudiantes que tienen un interes especifico.
     *
     * @param em El EntityManager
     * @param idInteres El ID del interes
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes con ese interes
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesPorInteres(EntityManager em, Long idInteres, int limit) throws Exception;

    /**
     * Obtiene los intereses mas populares ordenados por numero de estudiantes.
     *
     * @param em El EntityManager
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de intereses ordenados por popularidad
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interes> obtenerInteresesMasPopulares(EntityManager em, int limit) throws Exception;

    /**
     * Obtiene los intereses en comun entre dos estudiantes.
     *
     * @param em El EntityManager
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return Lista de intereses en comun
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interes> obtenerInteresesEnComun(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception;
}
package InterfaceDAO;

import Domain.Interes;
import Domain.Estudiante;
import java.util.List;

/**
 * Interfaz DAO para la entidad Interes.
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 */
public interface IInteresDAO extends IGenericDAO<Interes, Long> {

    /**
     * Busca un interés por su nombre.
     *
     * @param nombreInteres El nombre del interés a buscar
     * @return El interés encontrado o null si no existe
     * @throws Exception Si ocurre un error durante la búsqueda
     */
    Interes buscarPorNombre(String nombreInteres) throws Exception;

    /**
     * Busca intereses por categoría.
     *
     * @param categoria La categoría a buscar
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de intereses de esa categoría
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interes> buscarPorCategoria(String categoria, int limit) throws Exception;

    /**
     * Obtiene todas las categorías únicas de intereses.
     *
     * @return Lista de categorías únicas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<String> obtenerCategoriasUnicas() throws Exception;

    /**
     * Cuenta el número de estudiantes que tienen un interés específico.
     *
     * @param idInteres El ID del interés
     * @return Número de estudiantes con ese interés
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarEstudiantesPorInteres(Long idInteres) throws Exception;

    /**
     * Obtiene los estudiantes que tienen un interés específico.
     *
     * @param idInteres El ID del interés
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes con ese interés
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesPorInteres(Long idInteres, int limit) throws Exception;

    /**
     * Obtiene los intereses más populares ordenados por número de estudiantes.
     *
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de intereses ordenados por popularidad
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interes> obtenerInteresesMasPopulares(int limit) throws Exception;

    /**
     * Obtiene los intereses en común entre dos estudiantes.
     *
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return Lista de intereses en común
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interes> obtenerInteresesEnComun(Long idEstudiante1, Long idEstudiante2) throws Exception;
}

package InterfaceDAO;

import Domain.Carrera;
import Domain.Estudiante;
import java.util.List;

/**
 * Interfaz DAO para la entidad Carrera.
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 */
public interface ICarreraDAO extends IGenericDAO<Carrera, Long> {

    /**
     * Busca una carrera por su nombre.
     *
     * @param nombreCarrera El nombre de la carrera a buscar
     * @return La carrera encontrada o null si no existe
     * @throws Exception Si ocurre un error durante la búsqueda
     */
    Carrera buscarPorNombre(String nombreCarrera) throws Exception;

    /**
     * Obtiene todas las carreras que tienen al menos un estudiante registrado.
     *
     * @return Lista de carreras con estudiantes
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Carrera> listarCarrerasConEstudiantes() throws Exception;

    /**
     * Cuenta el número de estudiantes inscritos en una carrera específica.
     *
     * @param idCarrera El ID de la carrera
     * @return Número de estudiantes en la carrera
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarEstudiantesPorCarrera(Long idCarrera) throws Exception;

    /**
     * Obtiene los estudiantes de una carrera específica.
     *
     * @param idCarrera El ID de la carrera
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes de la carrera
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesPorCarrera(Long idCarrera, int limit) throws Exception;

    /**
     * Obtiene las carreras más populares ordenadas por número de estudiantes.
     *
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de carreras ordenadas por popularidad
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Carrera> obtenerCarrerasMasPopulares(int limit) throws Exception;
}

package InterfaceDAO;

import Domain.Carrera;
import Domain.Estudiante;
import jakarta.persistence.EntityManager;
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
     * @param em El EntityManager
     * @param nombreCarrera El nombre de la carrera a buscar
     * @return La carrera encontrada o null si no existe
     * @throws Exception Si ocurre un error durante la busqueda
     */
    Carrera buscarPorNombre(EntityManager em, String nombreCarrera) throws Exception;

    /**
     * Obtiene todas las carreras que tienen al menos un estudiante registrado.
     *
     * @param em El EntityManager
     * @return Lista de carreras con estudiantes
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Carrera> listarCarrerasConEstudiantes(EntityManager em) throws Exception;

    /**
     * Cuenta el numero de estudiantes inscritos en una carrera especifica.
     *
     * @param em El EntityManager
     * @param idCarrera El ID de la carrera
     * @return Numero de estudiantes en la carrera
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarEstudiantesPorCarrera(EntityManager em, Long idCarrera) throws Exception;

    /**
     * Obtiene los estudiantes de una carrera especifica.
     *
     * @param em El EntityManager
     * @param idCarrera El ID de la carrera
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes de la carrera
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesPorCarrera(EntityManager em, Long idCarrera, int limit) throws Exception;

    /**
     * Obtiene las carreras mas populares ordenadas por numero de estudiantes.
     *
     * @param em El EntityManager
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de carreras ordenadas por popularidad
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Carrera> obtenerCarrerasMasPopulares(EntityManager em, int limit) throws Exception;
}
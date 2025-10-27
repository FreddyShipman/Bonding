package InterfaceDAO;

import Domain.Hobby;
import Domain.Estudiante;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * Interfaz DAO para la entidad Hobby.
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 */

public interface IHobbyDAO extends IGenericDAO<Hobby, Long> {
    /**
     * Busca un hobby por su nombre.
     *
     * @param em El EntityManager
     * @param nombreHobby El nombre del hobby a buscar
     * @return El hobby encontrado o null si no existe
     * @throws Exception Si ocurre un error durante la busqueda
     */
    Hobby buscarPorNombre(EntityManager em, String nombreHobby) throws Exception;

    /**
     * Obtiene todos los hobbies que tienen al menos un estudiante asociado.
     *
     * @param em El EntityManager
     * @return Lista de hobbies con estudiantes
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Hobby> listarHobbiesConEstudiantes(EntityManager em) throws Exception;

    /**
     * Cuenta el numero de estudiantes que tienen un hobby especifico.
     *
     * @param em El EntityManager
     * @param idHobby El ID del hobby
     * @return Numero de estudiantes con ese hobby
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarEstudiantesPorHobby(EntityManager em, Long idHobby) throws Exception;

    /**
     * Obtiene los estudiantes que tienen un hobby especifico.
     *
     * @param em El EntityManager
     * @param idHobby El ID del hobby
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes con ese hobby
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesPorHobby(EntityManager em, Long idHobby, int limit) throws Exception;

    /**
     * Obtiene los hobbies mas populares ordenados por numero de estudiantes.
     *
     * @param em El EntityManager
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de hobbies ordenados por popularidad
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Hobby> obtenerHobbiesMasPopulares(EntityManager em, int limit) throws Exception;

    /**
     * Obtiene los hobbies en comun entre dos estudiantes.
     *
     * @param em El EntityManager
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return Lista de hobbies en comun
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Hobby> obtenerHobbiesEnComun(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception;
}
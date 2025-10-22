package InterfaceDAO;

import Domain.Hobby;
import Domain.Estudiante;
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
     * @param nombreHobby El nombre del hobby a buscar
     * @return El hobby encontrado o null si no existe
     * @throws Exception Si ocurre un error durante la búsqueda
     */
    Hobby buscarPorNombre(String nombreHobby) throws Exception;

    /**
     * Obtiene todos los hobbies que tienen al menos un estudiante asociado.
     *
     * @return Lista de hobbies con estudiantes
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Hobby> listarHobbiesConEstudiantes() throws Exception;

    /**
     * Cuenta el número de estudiantes que tienen un hobby específico.
     *
     * @param idHobby El ID del hobby
     * @return Número de estudiantes con ese hobby
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarEstudiantesPorHobby(Long idHobby) throws Exception;

    /**
     * Obtiene los estudiantes que tienen un hobby específico.
     *
     * @param idHobby El ID del hobby
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes con ese hobby
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesPorHobby(Long idHobby, int limit) throws Exception;

    /**
     * Obtiene los hobbies más populares ordenados por número de estudiantes.
     *
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de hobbies ordenados por popularidad
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Hobby> obtenerHobbiesMasPopulares(int limit) throws Exception;

    /**
     * Obtiene los hobbies en común entre dos estudiantes.
     *
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return Lista de hobbies en común
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Hobby> obtenerHobbiesEnComun(Long idEstudiante1, Long idEstudiante2) throws Exception;
}

package InterfaceDAO;

import Domain.Preferencia;
import Domain.Estudiante;
import java.util.List;

/**
 * Interfaz DAO para la entidad Preferencia.
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 */
public interface IPreferenciaDAO extends IGenericDAO<Preferencia, Long> {

    /**
     * Busca la preferencia asociada a un estudiante específico.
     *
     * @param idEstudiante El ID del estudiante
     * @return La preferencia del estudiante o null si no existe
     * @throws Exception Si ocurre un error durante la búsqueda
     */
    Preferencia buscarPorEstudiante(Long idEstudiante) throws Exception;

    /**
     * Obtiene todos los estudiantes que tienen preferencias configuradas.
     *
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes con preferencias
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesConPreferencias(int limit) throws Exception;

    /**
     * Busca preferencias por género preferido.
     *
     * @param generoPreferido El género preferido a buscar
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de preferencias con ese género
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Preferencia> buscarPorGeneroPreferido(String generoPreferido, int limit) throws Exception;

    /**
     * Busca preferencias que incluyan un rango de edad específico.
     *
     * @param edadMinima Edad mínima a buscar
     * @param edadMaxima Edad máxima a buscar
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de preferencias que coinciden con el rango
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Preferencia> buscarPorRangoEdad(Integer edadMinima, Integer edadMaxima, int limit) throws Exception;

    /**
     * Busca preferencias que incluyan una carrera preferida específica.
     *
     * @param idCarrera El ID de la carrera preferida
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de preferencias con esa carrera preferida
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Preferencia> buscarPorCarreraPreferida(Long idCarrera, int limit) throws Exception;

    /**
     * Encuentra estudiantes compatibles basados en las preferencias de un estudiante.
     * (Consulta compleja que combina género, edad y carrera)
     *
     * @param idEstudiante El ID del estudiante
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes compatibles según preferencias
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarEstudiantesCompatibles(Long idEstudiante, int limit) throws Exception;
}

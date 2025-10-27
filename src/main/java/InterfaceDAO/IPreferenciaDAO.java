package InterfaceDAO;

import Domain.Preferencia;
import Domain.Estudiante;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * Interfaz DAO para la entidad Preferencia.
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 */

public interface IPreferenciaDAO extends IGenericDAO<Preferencia, Long> {

    /**
     * Busca la preferencia asociada a un estudiante especifico.
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @return La preferencia del estudiante o null si no existe
     * @throws Exception Si ocurre un error durante la busqueda
     */
    Preferencia buscarPorEstudiante(EntityManager em, Long idEstudiante) throws Exception;

    /**
     * Obtiene todos los estudiantes que tienen preferencias configuradas.
     *
     * @param em El EntityManager
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes con preferencias
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesConPreferencias(EntityManager em, int limit) throws Exception;

    /**
     * Busca preferencias por genero preferido.
     *
     * @param em El EntityManager
     * @param generoPreferido El genero preferido a buscar
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de preferencias con ese genero
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Preferencia> buscarPorGeneroPreferido(EntityManager em, String generoPreferido, int limit) throws Exception;

    /**
     * Busca preferencias que incluyan un rango de edad especifico.
     *
     * @param em El EntityManager
     * @param edadMinima Edad minima a buscar
     * @param edadMaxima Edad maxima a buscar
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de preferencias que coinciden con el rango
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Preferencia> buscarPorRangoEdad(EntityManager em, Integer edadMinima, Integer edadMaxima, int limit) throws Exception;

    /**
     * Busca preferencias que incluyan una carrera preferida especifica.
     *
     * @param em El EntityManager
     * @param idCarrera El ID de la carrera preferida
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de preferencias con esa carrera preferida
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Preferencia> buscarPorCarreraPreferida(EntityManager em, Long idCarrera, int limit) throws Exception;

    /**
     * Encuentra estudiantes compatibles basados en las preferencias de un estudiante.
     * (Consulta compleja que combina genero, edad y carrera)
     *
     * @param em El EntityManager
     * @param idEstudiante El ID del estudiante
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes compatibles segun preferencias
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> buscarEstudiantesCompatibles(EntityManager em, Long idEstudiante, int limit) throws Exception;
}
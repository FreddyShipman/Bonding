package InterfaceDAO;

import Domain.Interaccion;
import Domain.Estudiante;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz DAO para la entidad Interaccion.
 * Incluye operaciones CRUD básicas y consultas complejas específicas.
 *
 * @author Alex Adrian Nieblas Moreno - 252865
 */

public interface IInteraccionDAO extends IGenericDAO<Interaccion, Long> {
    /**
     * Busca interacciones por tipo.
     *
     * @param em El EntityManager
     * @param tipoInteraccion El tipo de interaccion a buscar
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de interacciones de ese tipo
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> buscarPorTipo(EntityManager em, String tipoInteraccion, int limit) throws Exception;

    /**
     * Busca interacciones por fecha especifica.
     *
     * @param em El EntityManager
     * @param fecha La fecha a buscar
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de interacciones en esa fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> buscarPorFecha(EntityManager em, LocalDate fecha, int limit) throws Exception;

    /**
     * Busca interacciones en un rango de fechas.
     *
     * @param em El EntityManager
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de interacciones en el rango de fechas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> buscarPorRangoFechas(EntityManager em, LocalDate fechaInicio, LocalDate fechaFin, int limit) throws Exception;

    /**
     * Cuenta el numero de estudiantes que han realizado una interaccion especifica.
     *
     * @param em El EntityManager
     * @param idInteraccion El ID de la interaccion
     * @return Numero de estudiantes que han realizado esa interaccion
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarEstudiantesPorInteraccion(EntityManager em, Long idInteraccion) throws Exception;

    /**
     * Obtiene los estudiantes que han realizado una interaccion especifica.
     *
     * @param em El EntityManager
     * @param idInteraccion El ID de la interaccion
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de estudiantes que han realizado esa interaccion
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesPorInteraccion(EntityManager em, Long idInteraccion, int limit) throws Exception;

    /**
     * Obtiene las interacciones mas populares ordenadas por numero de estudiantes.
     *
     * @param em El EntityManager
     * @param limit Numero maximo de resultados (≤ 100)
     * @return Lista de interacciones ordenadas por popularidad
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> obtenerInteraccionesMasPopulares(EntityManager em, int limit) throws Exception;

    /**
     * Obtiene todos los tipos unicos de interacciones registradas.
     *
     * @param em El EntityManager
     * @return Lista de tipos de interacciones unicas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<String> obtenerTiposInteraccionUnicos(EntityManager em) throws Exception;

    /**
     * Obtiene las interacciones en comun entre dos estudiantes.
     *
     * @param em El EntityManager
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return Lista de interacciones en comun
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> obtenerInteraccionesEnComun(EntityManager em, Long idEstudiante1, Long idEstudiante2) throws Exception;
}
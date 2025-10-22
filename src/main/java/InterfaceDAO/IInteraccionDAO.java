package InterfaceDAO;

import Domain.Interaccion;
import Domain.Estudiante;
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
     * @param tipoInteraccion El tipo de interacción a buscar
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de interacciones de ese tipo
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> buscarPorTipo(String tipoInteraccion, int limit) throws Exception;

    /**
     * Busca interacciones por fecha específica.
     *
     * @param fecha La fecha a buscar
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de interacciones en esa fecha
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> buscarPorFecha(LocalDate fecha, int limit) throws Exception;

    /**
     * Busca interacciones en un rango de fechas.
     *
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de interacciones en el rango de fechas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin, int limit) throws Exception;

    /**
     * Cuenta el número de estudiantes que han realizado una interacción específica.
     *
     * @param idInteraccion El ID de la interacción
     * @return Número de estudiantes que han realizado esa interacción
     * @throws Exception Si ocurre un error durante la consulta
     */
    Long contarEstudiantesPorInteraccion(Long idInteraccion) throws Exception;

    /**
     * Obtiene los estudiantes que han realizado una interacción específica.
     *
     * @param idInteraccion El ID de la interacción
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de estudiantes que han realizado esa interacción
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Estudiante> obtenerEstudiantesPorInteraccion(Long idInteraccion, int limit) throws Exception;

    /**
     * Obtiene las interacciones más populares ordenadas por número de estudiantes.
     *
     * @param limit Número máximo de resultados (≤ 100)
     * @return Lista de interacciones ordenadas por popularidad
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> obtenerInteraccionesMasPopulares(int limit) throws Exception;

    /**
     * Obtiene todos los tipos únicos de interacciones registradas.
     *
     * @return Lista de tipos de interacciones únicas
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<String> obtenerTiposInteraccionUnicos() throws Exception;

    /**
     * Obtiene las interacciones en común entre dos estudiantes.
     *
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return Lista de interacciones en común
     * @throws Exception Si ocurre un error durante la consulta
     */
    List<Interaccion> obtenerInteraccionesEnComun(Long idEstudiante1, Long idEstudiante2) throws Exception;
}

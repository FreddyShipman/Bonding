package InterfaceService;

import Domain.Estudiante;
import Domain.Interaccion;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz para la logica de negocio relacionada con las 'Interacciones'.
 * Define operaciones como registrar, buscar y listar interacciones.
 * 
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public interface IInteraccionService {

    /**
     * Registra una nueva interaccion entre estudiantes.
     * Valida que el tipo y la fecha no esten vacios, que la fecha no sea futura,
     * y que los estudiantes asociados existan.
     *
     * @param interaccion La interaccion a crear (debe tener tipo, fecha y estudiantes)
     * @return La interaccion creada
     * @throws Exception Si falla la validacion o la persistencia
     */
    Interaccion crearInteraccion(Interaccion interaccion) throws Exception;

    /**
     * Actualiza la informacion de una interaccion (ej. cambiar fecha o tipo).
     * Valida que los campos obligatorios esten presentes y la fecha no sea futura.
     *
     * @param interaccion La interaccion con la informacion actualizada
     * @return La interaccion actualizada
     * @throws Exception Si falla la validacion o la actualizacion
     */
    Interaccion actualizarInteraccion(Interaccion interaccion) throws Exception;

    /**
     * Elimina una interaccion por su ID.
     *
     * @param idInteraccion El ID de la interaccion a eliminar
     * @return true si se elimino, false si no se encontro
     * @throws Exception Si ocurre un error
     */
    boolean eliminarInteraccion(Long idInteraccion) throws Exception;

    /**
     * Busca una interaccion por su ID.
     *
     * @param idInteraccion El ID de la interaccion
     * @return La interaccion encontrada o null
     * @throws Exception Si ocurre un error
     */
    Interaccion buscarPorId(Long idInteraccion) throws Exception;

    /**
     * Busca interacciones por tipo.
     *
     * @param tipoInteraccion El tipo a buscar
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de interacciones de ese tipo
     * @throws Exception Si ocurre un error
     */
    List<Interaccion> buscarPorTipo(String tipoInteraccion, int limit) throws Exception;

    /**
     * Busca interacciones en un rango de fechas.
     *
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de interacciones en ese rango
     * @throws Exception Si ocurre un error
     */
    List<Interaccion> buscarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin, int limit) throws Exception;

    /**
     * Lista todas las interacciones registradas.
     *
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de interacciones
     * @throws Exception Si ocurre un error
     */
    List<Interaccion> listarInteracciones(int limit) throws Exception;

    /**
     * Obtiene los estudiantes asociados a una interaccion especifica.
     *
     * @param idInteraccion El ID de la interaccion
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de estudiantes
     * @throws Exception Si ocurre un error
     */
    List<Estudiante> obtenerEstudiantesPorInteraccion(Long idInteraccion, int limit) throws Exception;

    /**
     * Obtiene las interacciones en comun entre dos estudiantes.
     *
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return Lista de interacciones en comun
     * @throws Exception Si ocurre un error
     */
    List<Interaccion> obtenerInteraccionesEnComun(Long idEstudiante1, Long idEstudiante2) throws Exception;
}
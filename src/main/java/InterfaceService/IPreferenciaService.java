package InterfaceService;

import Domain.Estudiante;
import Domain.Preferencia;
import java.util.List;

/**
 * Interfaz para la logica de negocio relacionada con las 'Preferencias' de los estudiantes.
 * Define operaciones como crear, actualizar y buscar preferencias.
 * 
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public interface IPreferenciaService {

    /**
     * Crea o actualiza las preferencias de un estudiante.
     * Si el estudiante ya tiene preferencias, las actualiza.
     * Si no, las crea.
     * Valida que los rangos de edad sean logicos y que el estudiante exista.
     *
     * @param preferencia Las preferencias a guardar (debe tener el estudiante asociado)
     * @return Las preferencias guardadas
     * @throws Exception Si falla la validacion o la persistencia
     */
    Preferencia guardarPreferencias(Preferencia preferencia) throws Exception;

    /**
     * Busca las preferencias de un estudiante especifico.
     *
     * @param idEstudiante El ID del estudiante
     * @return Las preferencias encontradas o null si no tiene
     * @throws Exception Si ocurre un error
     */
    Preferencia buscarPorEstudiante(Long idEstudiante) throws Exception;

    /**
     * Elimina las preferencias de un estudiante.
     *
     * @param idPreferencia El ID de la preferencia a eliminar
     * @return true si se elimino, false si no se encontro
     * @throws Exception Si ocurre un error
     */
    boolean eliminarPreferencias(Long idPreferencia) throws Exception;

    /**
     * Busca estudiantes que coincidan con las preferencias de un estudiante dado.
     * Este metodo encapsula la logica compleja de compatibilidad.
     *
     * @param idEstudiante El ID del estudiante que busca
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de estudiantes compatibles
     * @throws Exception Si ocurre un error
     */
    List<Estudiante> buscarEstudiantesCompatibles(Long idEstudiante, int limit) throws Exception;
}
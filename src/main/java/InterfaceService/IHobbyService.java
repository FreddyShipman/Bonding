package InterfaceService;

import Domain.Estudiante;
import Domain.Hobby;
import java.util.List;

/**
 * Interfaz para la logica de negocio relacionada con los 'Hobbies'.
 * Define operaciones como crear, buscar y listar hobbies, asi como
 * obtener informacion relacionada con los estudiantes que los comparten.
 * 
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
public interface IHobbyService {

    /**
     * Crea un nuevo hobby.
     * Valida que el nombre no este vacio y que no exista previamente.
     *
     * @param hobby El hobby a crear
     * @return El hobby creado
     * @throws Exception Si falla la validacion o la persistencia
     */
    Hobby crearHobby(Hobby hobby) throws Exception;

    /**
     * Actualiza la informacion de un hobby.
     *
     * @param hobby El hobby con la informacion actualizada
     * @return El hobby actualizado
     * @throws Exception Si falla la validacion o la actualizacion
     */
    Hobby actualizarHobby(Hobby hobby) throws Exception;

    /**
     * Elimina un hobby por su ID.
     * Podria incluir validaciones (ej. no eliminar si esta asociado a estudiantes).
     *
     * @param idHobby El ID del hobby a eliminar
     * @return true si se elimino, false si no se encontro
     * @throws Exception Si ocurre un error
     */
    boolean eliminarHobby(Long idHobby) throws Exception;

    /**
     * Busca un hobby por su ID.
     *
     * @param idHobby El ID del hobby
     * @return El hobby encontrado o null
     * @throws Exception Si ocurre un error
     */
    Hobby buscarPorId(Long idHobby) throws Exception;

    /**
     * Busca un hobby por su nombre exacto.
     *
     * @param nombreHobby El nombre del hobby
     * @return El hobby encontrado o null
     * @throws Exception Si ocurre un error
     */
    Hobby buscarPorNombre(String nombreHobby) throws Exception;

    /**
     * Lista todos los hobbies registrados.
     *
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de hobbies
     * @throws Exception Si ocurre un error
     */
    List<Hobby> listarHobbies(int limit) throws Exception;

    /**
     * Obtiene los estudiantes que tienen un hobby especifico.
     *
     * @param idHobby El ID del hobby
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de estudiantes
     * @throws Exception Si ocurre un error
     */
    List<Estudiante> obtenerEstudiantesPorHobby(Long idHobby, int limit) throws Exception;

    /**
     * Obtiene los hobbies mas populares (compartidos por mas estudiantes).
     *
     * @param limit Numero maximo de resultados (max 100)
     * @return Lista de hobbies ordenados por popularidad
     * @throws Exception Si ocurre un error
     */
    List<Hobby> obtenerHobbiesMasPopulares(int limit) throws Exception;

    /**
     * Obtiene los hobbies en comun entre dos estudiantes.
     *
     * @param idEstudiante1 ID del primer estudiante
     * @param idEstudiante2 ID del segundo estudiante
     * @return Lista de hobbies en comun
     * @throws Exception Si ocurre un error
     */
    List<Hobby> obtenerHobbiesEnComun(Long idEstudiante1, Long idEstudiante2) throws Exception;
}
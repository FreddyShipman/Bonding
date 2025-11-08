package Service;

import Config.JpaUtil;
import DAO.EstudianteDAO;
import Domain.Estudiante;
import Domain.Hobby;
import Domain.Interes;
import Excepciones.*;
import InterfaceDAO.IEstudianteDAO;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import InterfaceService.IEstudianteService;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */

public class EstudianteService implements IEstudianteService {

    private IEstudianteDAO estudianteDAO;

    public EstudianteService() {
        this.estudianteDAO = new EstudianteDAO();
    }

    @Override
    public Estudiante crearEstudiante(Estudiante estudiante) throws BondingException {
        EntityManager em = null;
        try {
            // Validaciones
            if (estudiante == null) {
                throw new ValidacionException("El estudiante no puede ser nulo");
            }
            if (estudiante.getCorreoInstitucional() == null || estudiante.getCorreoInstitucional().trim().isEmpty()) {
                throw new ValidacionException("El correo institucional es obligatorio", "correoInstitucional");
            }
            if (estudiante.getNombreEstudiante() == null || estudiante.getNombreEstudiante().trim().isEmpty()) {
                throw new ValidacionException("El nombre es obligatorio", "nombreEstudiante");
            }

            // Validar formato de correo institucional
            if (!estudiante.getCorreoInstitucional().toLowerCase().endsWith("@itson.edu.mx")) {
                throw new ValidacionException("El correo debe ser institucional (@itson.edu.mx)", "correoInstitucional");
            }

            em = JpaUtil.getInstance().getEntityManager();

            // Verificar duplicados
            Estudiante existente = this.estudianteDAO.buscarPorCorreo(em, estudiante.getCorreoInstitucional());
            if (existente != null) {
                throw new DuplicadoException("Estudiante", "correo institucional", estudiante.getCorreoInstitucional());
            }

            em.getTransaction().begin();
            this.estudianteDAO.crear(em, estudiante);
            em.getTransaction().commit();

            return estudiante;

        } catch (BondingException e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e; // Re-lanzar excepciones de negocio
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DatabaseException("Error al crear estudiante en la base de datos", "CREAR", "Estudiante", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Estudiante actualizarEstudiante(Estudiante estudiante) throws BondingException {
        EntityManager em = null;
        try {
            // Validaciones
            if (estudiante == null) {
                throw new ValidacionException("El estudiante no puede ser nulo");
            }
            if (estudiante.getIdEstudiante() == null) {
                throw new ValidacionException("El ID del estudiante no puede ser nulo", "idEstudiante");
            }

            em = JpaUtil.getInstance().getEntityManager();

            // Verificar que el estudiante existe
            Estudiante existente = this.estudianteDAO.buscarPorId(em, estudiante.getIdEstudiante());
            if (existente == null) {
                throw new EntidadNoEncontradaException("Estudiante", estudiante.getIdEstudiante());
            }

            em.getTransaction().begin();
            Estudiante actualizado = this.estudianteDAO.actualizar(em, estudiante);
            em.getTransaction().commit();

            return actualizado;

        } catch (BondingException e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DatabaseException("Error al actualizar estudiante en la base de datos", "ACTUALIZAR", "Estudiante", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarEstudiante(Long idEstudiante) throws BondingException {
        EntityManager em = null;
        try {
            // Validaciones
            if (idEstudiante == null) {
                throw new ValidacionException("El ID del estudiante no puede ser nulo", "idEstudiante");
            }

            em = JpaUtil.getInstance().getEntityManager();

            // Verificar que el estudiante existe
            Estudiante existente = this.estudianteDAO.buscarPorId(em, idEstudiante);
            if (existente == null) {
                throw new EntidadNoEncontradaException("Estudiante", idEstudiante);
            }

            em.getTransaction().begin();
            boolean eliminado = this.estudianteDAO.eliminar(em, idEstudiante);
            em.getTransaction().commit();

            return eliminado;

        } catch (BondingException e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new DatabaseException("Error al eliminar estudiante de la base de datos", "ELIMINAR", "Estudiante", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Estudiante buscarPorId(Long idEstudiante) throws BondingException {
        EntityManager em = null;
        try {
            // Validaciones
            if (idEstudiante == null) {
                throw new ValidacionException("El ID del estudiante no puede ser nulo", "idEstudiante");
            }

            em = JpaUtil.getInstance().getEntityManager();
            Estudiante estudiante = this.estudianteDAO.buscarPorId(em, idEstudiante);

            if (estudiante == null) {
                throw new EntidadNoEncontradaException("Estudiante", idEstudiante);
            }

            return estudiante;

        } catch (BondingException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar estudiante por ID", "BUSCAR", "Estudiante", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Estudiante autenticar(String correoInstitucional, String contrasena) throws BondingException {
        EntityManager em = null;
        try {
            // Validaciones
            if (correoInstitucional == null || correoInstitucional.trim().isEmpty()) {
                throw new ValidacionException("El correo institucional es obligatorio", "correoInstitucional");
            }
            if (contrasena == null || contrasena.isEmpty()) {
                throw new ValidacionException("La contraseña es obligatoria", "contrasena");
            }

            em = JpaUtil.getInstance().getEntityManager();
            Estudiante estudiante = this.estudianteDAO.autenticar(em, correoInstitucional, contrasena);

            if (estudiante == null) {
                throw new AutenticacionException(correoInstitucional,
                    AutenticacionException.TipoErrorAutenticacion.CREDENCIALES_INVALIDAS);
            }

            return estudiante;

        } catch (BondingException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error al autenticar estudiante", "AUTENTICAR", "Estudiante", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> buscarEstudiantesCompatibles(Long idEstudiante, int limit) throws BondingException {
        EntityManager em = null;
        try {
            // Validaciones
            if (idEstudiante == null) {
                throw new ValidacionException("El ID del estudiante no puede ser nulo", "idEstudiante");
            }
            if (limit <= 0) {
                throw new ValidacionException("El límite debe ser mayor a 0", "limit");
            }
            if (limit > 100) {
                throw new ValidacionException("El límite no puede ser mayor a 100", "limit");
            }

            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.buscarEstudiantesCompatibles(em, idEstudiante, limit);

        } catch (BondingException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error al buscar estudiantes compatibles", "BUSCAR", "Estudiante", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> obtenerMatches(Long idEstudiante, int limit) throws BondingException {
        EntityManager em = null;
        try {
            // Validaciones
            if (idEstudiante == null) {
                throw new ValidacionException("El ID del estudiante no puede ser nulo", "idEstudiante");
            }
            if (limit <= 0) {
                throw new ValidacionException("El límite debe ser mayor a 0", "limit");
            }
            if (limit > 100) {
                throw new ValidacionException("El límite no puede ser mayor a 100", "limit");
            }

            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.obtenerMatches(em, idEstudiante, limit);

        } catch (BondingException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener matches del estudiante", "BUSCAR", "Match", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Hobby> obtenerHobbies(Long idEstudiante) throws BondingException {
        EntityManager em = null;
        try {
            // Validaciones
            if (idEstudiante == null) {
                throw new ValidacionException("El ID del estudiante no puede ser nulo", "idEstudiante");
            }

            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.obtenerHobbies(em, idEstudiante);

        } catch (BondingException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener hobbies del estudiante", "BUSCAR", "Hobby", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Interes> obtenerIntereses(Long idEstudiante) throws BondingException {
        EntityManager em = null;
        try {
            // Validaciones
            if (idEstudiante == null) {
                throw new ValidacionException("El ID del estudiante no puede ser nulo", "idEstudiante");
            }

            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.obtenerIntereses(em, idEstudiante);

        } catch (BondingException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener intereses del estudiante", "BUSCAR", "Interes", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Map<String, Long> obtenerEstadisticas(Long idEstudiante) throws BondingException {
        EntityManager em = null;
        try {
            // Validaciones
            if (idEstudiante == null) {
                throw new ValidacionException("El ID del estudiante no puede ser nulo", "idEstudiante");
            }

            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.obtenerEstadisticas(em, idEstudiante);

        } catch (BondingException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error al obtener estadísticas del estudiante", "BUSCAR", "Estadisticas", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Estudiante> listarEstudiantes(int limit) throws BondingException {
        EntityManager em = null;
        try {
            // Validaciones
            if (limit <= 0) {
                throw new ValidacionException("El límite debe ser mayor a 0", "limit");
            }
            if (limit > 100) {
                throw new ValidacionException("El límite no puede ser mayor a 100", "limit");
            }

            em = JpaUtil.getInstance().getEntityManager();
            return this.estudianteDAO.listar(em, limit);

        } catch (BondingException e) {
            throw e;
        } catch (Exception e) {
            throw new DatabaseException("Error al listar estudiantes", "LISTAR", "Estudiante", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
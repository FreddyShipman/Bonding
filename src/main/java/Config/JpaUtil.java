package Config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase de utilidad que administra la conexion a la base de datos usando JPA.
 *
 * Esta clase implementa el patron de diseno Singleton. Esto asegura
 * que solo exista UNA instancia de esta clase (y por lo tanto, solo UN
 * EntityManagerFactory) en toda la aplicacion. 
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
public class JpaUtil {

    /**
     * Constante que guarda el nombre de nuestra unidad de persistencia.
     * Este nombre debe coincidir exactamente con el nombre definido
     * en el archivo 'persistence.xml'.
     */
    private static final String PERSISTENCE_UNIT_NAME = "BondingPU";

    /**
     * La fabrica de EntityManagers (EntityManagerFactory).
     * Es 'static' para que sea compartida por toda la aplicacion.
     * Es la que maneja la conexion a la base de datos y crea los
     * EntityManagers.
     */
    private static EntityManagerFactory emf;

    /**
     * Almacena la unica instancia de esta clase (JpaUtil).
     * Esto es parte del patron Singleton.
     */
    private static JpaUtil instance;

    /**
     * Constructor privado. Nadie fuera de esta clase puede crear una
     * instancia usando 'new JpaUtil()'.
     *
     * Al ser llamado (solo por getInstance), intenta crear el
     * EntityManagerFactory usando el nombre de la unidad de persistencia.
     * Si algo sale mal (ej. no encuentra persistence.xml o la BD esta caida),
     * imprime un error y detiene la inicializacion.
     */
    private JpaUtil() {
        try {
            // Intenta crear la fabrica de conexiones
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Throwable ex) {
            // Si falla, informa el error.
            System.err.println("La inicializacion de EntityManagerFactory fallo: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Metodo publico estatico para obtener la unica instancia de JpaUtil.
     *
     * Es 'synchronized' para ser seguro en ambientes con multiples hilos
     * (evita que dos hilos creen la instancia al mismo tiempo).
     *
     * Si la instancia aun no existe (es null), la crea llamando
     * al constructor privado. Esto se llama "inicializacion perezosa"
     * (lazy initialization).
     *
     * @return La unica instancia (Singleton) de JpaUtil.
     */
    public static synchronized JpaUtil getInstance() {
        if (instance == null) {
            instance = new JpaUtil();
        }
        return instance;
    }

    /**
     * Proporciona un EntityManager nuevo.
     *
     * El EntityManager es el objeto que realmente usamos para hablar
     * con la base de datos (para guardar, buscar, actualizar o borrar
     * entidades/objetos).
     *
     * IMPORTANTE: Cada vez que necesites hacer una operacion de BD,
     * debes pedir un EntityManager nuevo. Cuando termines de usarlo,
     * DEBES cerrarlo (con em.close()).
     *
     * @return Un nuevo EntityManager listo para usarse.
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Cierra la fabrica de EntityManagers (emf).
     *
     * Este metodo debe llamarse solo cuando la aplicacion se va a
     * detener completamente (ej. cuando cierras el servidor).
     * Libera todos los recursos y conexiones a la base de datos.
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
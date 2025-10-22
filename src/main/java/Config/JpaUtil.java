package Config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author JOSE ALFREDO GUZMAN MORENO - 252524
 */
public class JpaUtil {

    private static final String PERSISTENCE_UNIT_NAME = "BondingPU";

    private static EntityManagerFactory emf;

    private static JpaUtil instance;

    private JpaUtil() {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Throwable ex) {
            System.err.println("La inicializacion de EntityManagerFactory fallo: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static synchronized JpaUtil getInstance() {
        if (instance == null) {
            instance = new JpaUtil();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
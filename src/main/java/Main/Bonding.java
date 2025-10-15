package Main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author alfre
 */
public class Bonding {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("BondingPU");
             EntityManager em = emf.createEntityManager()) {

            System.out.println("Conexión exitosa - Tablas creadas/actualizadas");

        } catch (Exception e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}

package Main;

/**
 *
 * @author alfre
 */

// --- Imports de Dominio y Servicios ---
import InterfaceService.*;
import Service.*;
import Vistas.FrmLogin;

// --- Imports de Swing ---
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal que inicia la aplicación Bonding.
 * * Es la responsable de crear todas las instancias de los servicios
 * y de inyectarlas en la primera ventana (FrmLogin).
 */
public class BondingApp {

    public static void main(String[] args) {
        
        // 1. Establecer el Look and Feel nativo del Sistema Operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el LookAndFeel nativo.");
            e.printStackTrace();
        }

        // 2. --- El Núcleo de la Inyección de Dependencias ---
        // Creamos UNA SOLA VEZ todas las instancias de los servicios.
        IEstudianteService estudianteService = new EstudianteService();
        ICarreraService carreraService = new CarreraService();
        IHobbyService hobbyService = new HobbyService();
        IInteresService interesService = new InteresService();
        ILikeService likeService = new LikeService();
        IMatchService matchService = new MatchService();
        IChatService chatService = new ChatService();
        IMensajeService mensajeService = new MensajeService();
        IPreferenciaService preferenciaService = new PreferenciaService();
        
        // 3. Iniciar la interfaz gráfica en el hilo de Swing (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // 4. Iniciar FrmLogin, pasándole todos los servicios
                FrmLogin ventanaPrincipal = new FrmLogin(
                    estudianteService,
                    carreraService,
                    hobbyService,
                    interesService,
                    likeService,
                    matchService,
                    chatService,
                    mensajeService,
                    preferenciaService
                );
                
                ventanaPrincipal.setVisible(true);
            }
        });
    }
}
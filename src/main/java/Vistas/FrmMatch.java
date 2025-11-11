package Vistas;

/**
 *
 * @author alfre
 */

// --- Imports de Dominio y Servicios ---
import Domain.Estudiante;
import Domain.Hobby;
import Domain.Interes;
import Domain.Carrera;
import Domain.Match;
import Domain.Chat;
import Domain.Mensaje;
import Domain.Preferencia; // AÑADIDO
import InterfaceService.*; // Importa TODAS las 9 interfaces
import Service.*; // Importa TODAS las 9 implementaciones

// --- Imports de Swing y AWT ---
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDateTime;

public class FrmMatch extends JFrame {

    // --- Servicios (LOS 9) ---
    private IEstudianteService estudianteService;
    private ICarreraService carreraService;
    private IHobbyService hobbyService;
    private IInteresService interesService;
    private ILikeService likeService;
    private IMatchService matchService; 
    private IChatService chatService; 
    private IMensajeService mensajeService; 
    private IPreferenciaService preferenciaService; // AÑADIDO
    private Estudiante estudianteActual;

    // --- Colores ---
    private static final Color COLOR_FONDO = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_BARRA_IZQUIERDA = new Color(250, 250, 250);
    private static final Color COLOR_BOTON_AZUL = new Color(0, 86, 179);
    private static final Color COLOR_SELECCIONADO = new Color(230, 240, 250);

    // --- Componentes ---
    private JButton btnInicio, btnBuscar, btnMensajes, btnMiPerfil, btnConfiguracion;
    private JPanel panelGridMatches;

    // --- CAMBIO: Constructor recibe los 9 servicios ---
    public FrmMatch(Estudiante estudiante, 
                    IEstudianteService estService, 
                    ICarreraService carService, 
                    IHobbyService hobService, 
                    IInteresService intService,
                    ILikeService likeService,
                    IMatchService matchService,
                    IChatService chatService,     
                    IMensajeService mensajeService,
                    IPreferenciaService preferenciaService) { // AÑADIDO
        
        this.estudianteActual = estudiante;
        this.estudianteService = estService;
        this.carreraService = carService;
        this.hobbyService = hobService;
        this.interesService = intService;
        this.likeService = likeService;
        this.matchService = matchService;
        this.chatService = chatService;         
        this.mensajeService = mensajeService;   
        this.preferenciaService = preferenciaService; // AÑADIDO
        
        initComponentes();
        cargarMatches();
    }

    private void initComponentes() {
        setTitle("Mis Matches");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearPanelNavegacion(), BorderLayout.WEST);
        add(crearPanelPrincipal(), BorderLayout.CENTER);

        // Listeners de Navegación
        btnInicio.addActionListener(e -> irAExplorar());
        btnBuscar.addActionListener(e -> irAExplorar());
        btnMensajes.addActionListener(e -> irAMensajes());
        btnMiPerfil.addActionListener(e -> irAPerfil());
    }

    private JPanel crearPanelNavegacion() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_BARRA_IZQUIERDA);
        panel.setPreferredSize(new Dimension(240, 0));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)));
        JPanel panelLogo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelLogo.setBackground(COLOR_BARRA_IZQUIERDA);
        panelLogo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        JLabel lblIconoLogo = new JLabel("[Logo]");
        lblIconoLogo.setPreferredSize(new Dimension(30, 30));
        lblIconoLogo.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel lblTextoLogo = new JLabel("ITSON Conecta");
        lblTextoLogo.setFont(new Font("Arial", Font.BOLD, 16));
        panelLogo.add(lblIconoLogo);
        panelLogo.add(lblTextoLogo);
        panel.add(panelLogo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        btnInicio = crearBotonNavegacion("Inicio", null);
        btnBuscar = crearBotonNavegacion("Buscar", null);
        btnMensajes = crearBotonNavegacion("Mensajes", null);
        btnMiPerfil = crearBotonNavegacion("Mi Perfil", null);
        btnMiPerfil.setBackground(COLOR_SELECCIONADO);
        btnMiPerfil.setFont(new Font("Arial", Font.BOLD, 14));
        btnMiPerfil.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, COLOR_BOTON_AZUL),
                new EmptyBorder(15, 16, 15, 20)
        ));
        panel.add(btnInicio);
        panel.add(btnBuscar);
        panel.add(btnMensajes);
        panel.add(btnMiPerfil);
        panel.add(Box.createVerticalGlue());
        btnConfiguracion = crearBotonNavegacion("Configuración", null);
        panel.add(btnConfiguracion);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        return panel;
    }

    private JButton crearBotonNavegacion(String texto, ImageIcon icono) {
        JButton boton = new JButton(texto, icono);
        boton.setFont(new Font("Arial", Font.PLAIN, 14));
        boton.setBackground(COLOR_BARRA_IZQUIERDA);
        boton.setForeground(new Color(50, 50, 50));
        boton.setFocusPainted(false);
        boton.setBorder(new EmptyBorder(15, 20, 15, 20));
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if (boton != btnMiPerfil) {
                    boton.setBackground(new Color(235, 235, 235));
                }
            }
            public void mouseExited(MouseEvent evt) {
                if (boton != btnMiPerfil) {
                    boton.setBackground(COLOR_BARRA_IZQUIERDA);
                }
            }
        });
        return boton;
    }

    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(40, 50, 40, 50));
        JLabel lblTitulo = new JLabel("Mis Matches");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setBorder(new EmptyBorder(0, 5, 0, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        panelGridMatches = new JPanel(new GridLayout(0, 4, 15, 15));
        panelGridMatches.setBackground(COLOR_FONDO);
        JScrollPane scrollPane = new JScrollPane(panelGridMatches);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // =================================================================
    // --- LÓGICA DE CARGA Y ACCIONES ---
    // =================================================================

    private void cargarMatches() {
        try {
            // Usando tu método existente que devuelve List<Estudiante>
            List<Estudiante> matches = estudianteService.obtenerMatches(estudianteActual.getIdEstudiante(), 50);
            if (matches == null || matches.isEmpty()) {
                mostrarMensajeVacio("Aún no tienes matches. ¡Sigue explorando!");
                return;
            }
            for (Estudiante match : matches) {
                PanelMiniPerfil miniPerfil = new PanelMiniPerfil(match);
                panelGridMatches.add(miniPerfil);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeVacio("Error al cargar tus matches: " + e.getMessage());
        }
    }

    private void mostrarMensajeVacio(String mensaje) {
        panelGridMatches.setLayout(new GridBagLayout());
        JLabel lblMensaje = new JLabel(mensaje);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 18));
        panelGridMatches.add(lblMensaje);
    }
    
    // --- Lógica del "Puente": Busca el Match ID y abre el Chat ---
    private void abrirChatConBusqueda(Estudiante destino) {
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // 1. Buscar el Match
            Match match = matchService.buscarMatchPorEstudiantes(
                estudianteActual.getIdEstudiante(), 
                destino.getIdEstudiante()
            );
            if (match == null) {
                match = matchService.buscarMatchPorEstudiantes(
                    destino.getIdEstudiante(),
                    estudianteActual.getIdEstudiante()
                );
            }
            if (match == null) {
                throw new Exception("Error fatal: No se pudo encontrar el Match asociado.");
            }

            // 2. Abrir FrmChat, pasando el ID del Match
            new FrmChat(
                estudianteActual, 
                destino, 
                match.getIdMatch(), // ¡La clave!
                estudianteService, 
                carreraService, 
                hobbyService, 
                interesService, 
                likeService,
                matchService,
                chatService,
                mensajeService,
                preferenciaService // AÑADIDO
            ).setVisible(true);
            this.dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al abrir el chat: " + e.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    // =================================================================
    // --- NAVEGACIÓN (Actualizada a 9 servicios) ---
    // =================================================================

    private void irAExplorar() {
        new FrmExplorar(
            estudianteActual, estudianteService, carreraService, 
            hobbyService, interesService, likeService, 
            matchService, chatService, mensajeService, 
            preferenciaService // AÑADIDO
        ).setVisible(true);
        this.dispose();
    }

    private void irAPerfil() {
        new FrmPerfil(
            estudianteActual, estudianteService, carreraService, 
            hobbyService, interesService, likeService,
            matchService, chatService, mensajeService,
            preferenciaService // AÑADIDO
        ).setVisible(true);
        this.dispose();
    }
    
    private void irAMensajes() {
        new FrmListaMensajes(
            estudianteActual, estudianteService, carreraService,
            hobbyService, interesService, likeService,
            matchService, chatService, mensajeService,
            preferenciaService
        ).setVisible(true);
        this.dispose();
    }

    // =================================================================
    // --- MAIN DE PRUEBA (Actualizado a 9 servicios) ---
    // =================================================================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IEstudianteService estService = new EstudianteService();
            ICarreraService carService = new CarreraService();
            IHobbyService hobService = new HobbyService();
            IInteresService intService = new InteresService();
            ILikeService likeService = new LikeService();
            IMatchService matchService = new MatchService();
            IChatService chatService = new ChatService();
            IMensajeService msgService = new MensajeService();
            IPreferenciaService prefService = new PreferenciaService(); // AÑADIDO
            
            Estudiante estudiantePrueba = new Estudiante();
            estudiantePrueba.setIdEstudiante(1L); 
            
            new FrmMatch(
                estudiantePrueba, estService, carService, hobService, 
                intService, likeService, matchService, chatService, msgService,
                prefService // AÑADIDO
            ).setVisible(true);
        });
    }

    // =================================================================
    // --- CLASE INTERNA: PANEL PEQUEÑO PARA CADA MATCH ---
    // =================================================================
    
    class PanelMiniPerfil extends JPanel {
        private Estudiante matchEstudiante; 

        public PanelMiniPerfil(Estudiante matchEstudiante) {
            this.matchEstudiante = matchEstudiante;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(COLOR_PANEL_BLANCO);
            setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            JLabel lblFoto = new JLabel();
            lblFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblFoto.setPreferredSize(new Dimension(150, 150));
            lblFoto.setMinimumSize(new Dimension(150, 150));
            lblFoto.setMaximumSize(new Dimension(150, 150));
            cargarFoto(lblFoto, matchEstudiante.getFotoPerfil(), 150);
            
            JLabel lblNombre = new JLabel(matchEstudiante.getNombreEstudiante());
            lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
            lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(lblFoto);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(lblNombre);
            add(Box.createRigidArea(new Dimension(0, 10)));
            
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    abrirChatConBusqueda(matchEstudiante);
                }
            });
        }
        
        private void cargarFoto(JLabel label, String rutaFoto, int tamano) {
            if (rutaFoto != null && !rutaFoto.isEmpty()) {
                try {
                    ImageIcon icono = new ImageIcon(rutaFoto);
                    Image img = icono.getImage().getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);
                    BufferedImage imgCircular = new BufferedImage(tamano, tamano, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = imgCircular.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, tamano, tamano));
                    g2.drawImage(img, 0, 0, tamano, tamano, null);
                    g2.dispose();
                    label.setIcon(new ImageIcon(imgCircular));
                } catch (Exception e) { label.setText("[Foto]"); }
            } else { label.setText("[Sin Foto]"); }
        }
    }
}
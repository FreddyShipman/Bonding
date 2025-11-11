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

public class FrmListaMensajes extends JFrame {

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
    private static final Color COLOR_TEXTO_GRIS = new Color(100, 100, 100);

    // --- Componentes de Navegacion ---
    private JButton btnInicio, btnBuscar, btnMensajes, btnMiPerfil, btnConfiguracion;
    
    // --- Componentes Principales ---
    private JPanel panelContenedorLista; 

    // --- CAMBIO: Constructor recibe los 9 servicios ---
    public FrmListaMensajes(Estudiante estudiante, 
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
        cargarListaChats();
    }

    private void initComponentes() {
        setTitle("Mis Mensajes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearPanelNavegacion(), BorderLayout.WEST);
        add(crearPanelPrincipal(), BorderLayout.CENTER);

        // Listeners de Navegación
        btnInicio.addActionListener(e -> irAExplorar());
        btnBuscar.addActionListener(e -> irAPreferencias());
        btnMiPerfil.addActionListener(e -> irAPerfil());
        btnConfiguracion.addActionListener(e -> irAConfiguracion());
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

        // --- Estado Activo ---
        btnMensajes.setBackground(COLOR_SELECCIONADO);
        btnMensajes.setFont(new Font("Arial", Font.BOLD, 14));
        btnMensajes.setBorder(BorderFactory.createCompoundBorder(
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
                if (boton != btnMensajes) { 
                    boton.setBackground(new Color(235, 235, 235));
                }
            }
            public void mouseExited(MouseEvent evt) {
                if (boton != btnMensajes) {
                    boton.setBackground(COLOR_BARRA_IZQUIERDA);
                }
            }
        });
        return boton;
    }

    private JPanel crearPanelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(40, 50, 40, 150)); 
        JLabel lblTitulo = new JLabel("Mensajes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setBorder(new EmptyBorder(0, 5, 0, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        panelContenedorLista = new JPanel();
        panelContenedorLista.setLayout(new BoxLayout(panelContenedorLista, BoxLayout.Y_AXIS));
        panelContenedorLista.setBackground(COLOR_PANEL_BLANCO);
        JScrollPane scrollPane = new JScrollPane(panelContenedorLista);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    // =================================================================
    // --- LÓGICA DE CARGA Y ACCIONES ---
    // =================================================================

    private void cargarListaChats() {
        try {
            List<Chat> chats = chatService.obtenerChatsPorEstudiante(estudianteActual.getIdEstudiante(), 50);
            if (chats == null || chats.isEmpty()) {
                mostrarMensajeVacio("No tienes mensajes. ¡Haz un match primero!");
                return;
            }
            for (Chat chat : chats) {
                Mensaje ultimoMsg = chatService.obtenerUltimoMensajeDelChat(chat.getIdChat());
                Estudiante destino = null;
                for (Estudiante est : chat.getMatch().getEstudiantes()) {
                    if (!est.getIdEstudiante().equals(estudianteActual.getIdEstudiante())) {
                        destino = est;
                        break;
                    }
                }
                if(destino != null) {
                    PanelPreviewChat preview = new PanelPreviewChat(chat, destino, ultimoMsg);
                    panelContenedorLista.add(preview);
                    panelContenedorLista.add(new JSeparator());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeVacio("Error al cargar tus conversaciones.");
        }
    }

    private void mostrarMensajeVacio(String mensaje) {
        panelContenedorLista.setLayout(new GridBagLayout());
        JLabel lblMensaje = new JLabel(mensaje);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 18));
        panelContenedorLista.add(lblMensaje);
    }
    
    // --- Acción al hacer clic en un Chat ---
    private void abrirChat(Estudiante destino, Long idMatch) {
        new FrmChat(
            estudianteActual, 
            destino, 
            idMatch,
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

    private void irAPreferencias() {
        new FrmPreferencias(
            estudianteActual,
            estudianteService,
            carreraService,
            hobbyService,
            interesService,
            likeService,
            matchService,
            chatService,
            mensajeService,
            preferenciaService
        ).setVisible(true);
        this.dispose();
    }

    private void irAConfiguracion() {
        JOptionPane.showMessageDialog(this, "Navegando a Configuración...");
        // Aquí irá FrmConfiguracion cuando esté implementado
    }
    
    // =================================================================
    // --- MAIN DE PRUEBA (Actualizado a 9 servicios) ---
    // =================================================================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // --- Servicios (9) ---
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
            
            new FrmListaMensajes(
                estudiantePrueba, estService, carService, hobService, 
                intService, likeService, matchService, chatService, msgService,
                prefService // AÑADIDO
            ).setVisible(true);
        });
    }

    // =================================================================
    // --- CLASE INTERNA: PANEL PEQUEÑO PARA CADA CONVERSACIÓN ---
    // =================================================================
    
    class PanelPreviewChat extends JPanel {
        private Chat chat;
        private Estudiante destino;
        private Mensaje ultimoMensaje;

        public PanelPreviewChat(Chat chat, Estudiante destino, Mensaje ultimoMensaje) {
            this.chat = chat;
            this.destino = destino;
            this.ultimoMensaje = ultimoMensaje;
            
            setLayout(new BorderLayout(15, 0));
            setBackground(COLOR_PANEL_BLANCO);
            setBorder(new EmptyBorder(10, 10, 10, 10));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // --- Foto ---
            JLabel lblFoto = new JLabel();
            lblFoto.setPreferredSize(new Dimension(50, 50));
            lblFoto.setMinimumSize(new Dimension(50, 50));
            cargarFoto(lblFoto, destino.getFotoPerfil(), 50);
            add(lblFoto, BorderLayout.WEST);

            // --- Contenido (Nombre y Mensaje) ---
            JPanel panelContenido = new JPanel();
            panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
            panelContenido.setOpaque(false);
            
            JLabel lblNombre = new JLabel(destino.getNombreEstudiante() + " " + destino.getApellidoPaterno());
            lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
            panelContenido.add(lblNombre);
            
            panelContenido.add(Box.createRigidArea(new Dimension(0, 4)));

            // --- Lógica del último mensaje ---
            JLabel lblUltimoMsg = new JLabel();
            lblUltimoMsg.setFont(new Font("Arial", Font.PLAIN, 14));
            lblUltimoMsg.setForeground(COLOR_TEXTO_GRIS);
            
            if (ultimoMensaje == null) {
                lblUltimoMsg.setText("¡Inicia la conversación!");
            } else {
                String prefijo = "";
                if (ultimoMensaje.getEstudianteEmisor().getIdEstudiante().equals(estudianteActual.getIdEstudiante())) {
                    prefijo = "Tú: ";
                }
                // Asumo que tu entidad Mensaje tiene "getContenido()"
                lblUltimoMsg.setText(prefijo + ultimoMensaje.getContenido()); 
            }
            panelContenido.add(lblUltimoMsg);
            add(panelContenido, BorderLayout.CENTER);
            
            // --- Listener ---
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    abrirChat(destino, chat.getMatch().getIdMatch());
                }
            });
        }
        
        private void cargarFoto(JLabel label, byte[] fotoBytes, int tamano) {
            if (fotoBytes != null && fotoBytes.length > 0) {
                try {
                    ImageIcon icono = new ImageIcon(fotoBytes);
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
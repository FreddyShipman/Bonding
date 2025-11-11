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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDateTime;

public class FrmPerfil extends JFrame {

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
    private Estudiante estudiante;

    // --- Colores ---
    private static final Color COLOR_FONDO = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_BARRA_IZQUIERDA = new Color(250, 250, 250);
    private static final Color COLOR_BOTON_AZUL = new Color(0, 86, 179);
    private static final Color COLOR_TEXTO_GRIS = new Color(100, 100, 100);
    private static final Color COLOR_SELECCIONADO = new Color(230, 240, 250);
    private static final Color COLOR_LINK_ROJO = new Color(210, 50, 50);

    // --- Componentes ---
    private JButton btnInicio, btnBuscar, btnMensajes, btnMiPerfil, btnConfiguracion;
    private JLabel lblFotoPerfilGrande, lblNombrePerfil, lblCarreraPerfil;
    private JTextArea txtBiografia;
    private JPanel panelTags;
    private JButton btnExplorarCard, btnMatchCard, btnMensajesCard;
    private JButton btnEditarPerfil, btnConfigurarBusqueda, btnCerrarSesionCard;
    private JLabel lblEliminarCuenta;

    // --- CAMBIO: Constructor ahora recibe 9 servicios ---
    public FrmPerfil(Estudiante estudiante, 
                     IEstudianteService estudianteService, 
                     ICarreraService carreraService, 
                     IHobbyService hobbyService, 
                     IInteresService interesService,
                     ILikeService likeService,
                     IMatchService matchService, 
                     IChatService chatService, 
                     IMensajeService mensajeService,
                     IPreferenciaService preferenciaService) { // AÑADIDO
        
        this.estudiante = estudiante;
        this.estudianteService = estudianteService;
        this.carreraService = carreraService;
        this.hobbyService = hobbyService;
        this.interesService = interesService;
        this.likeService = likeService;
        this.matchService = matchService; 
        this.chatService = chatService; 
        this.mensajeService = mensajeService; 
        this.preferenciaService = preferenciaService; // AÑADIDO
        
        initComponentes();
        cargarDatosPerfil();
    }

    private void initComponentes() {
        setTitle("Mi Perfil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearPanelNavegacion(), BorderLayout.WEST);
        add(crearPanelPrincipal(), BorderLayout.CENTER);
        
        // Listeners Tarjeta Derecha
        btnExplorarCard.addActionListener(e -> irAExplorar());
        btnMatchCard.addActionListener(e -> irAMatch());
        btnMensajesCard.addActionListener(e -> irAMensajes());
        btnEditarPerfil.addActionListener(e -> editarPerfil());
        btnConfigurarBusqueda.addActionListener(e -> irAPreferencias()); // ¡Este botón ya funciona!
        btnCerrarSesionCard.addActionListener(e -> cerrarSesion());
        lblEliminarCuenta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                eliminarCuenta();
            }
        });
        
        // Listeners Barra Izquierda
        btnInicio.addActionListener(e -> irAExplorar());
        btnBuscar.addActionListener(e -> irAPreferencias());
        btnMensajes.addActionListener(e -> irAMensajes());
        btnConfiguracion.addActionListener(e -> irAConfiguracion());
    }

    private JPanel crearPanelNavegacion() {
        // (Sin cambios)
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
        // (Sin cambios)
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
        // (Sin cambios)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(40, 50, 40, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 5, 20, 0);
        JLabel lblTitulo = new JLabel("Mi Perfil");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        panel.add(lblTitulo, gbc);
        gbc.gridy = 1; gbc.gridwidth = 1; gbc.weightx = 0.7; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH; gbc.insets = new Insets(0, 0, 0, 20);
        panel.add(crearTarjetaPerfil(), gbc);
        gbc.gridx = 1; gbc.weightx = 0.3; gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(crearTarjetaGestion(), gbc);
        return panel;
    }
    
    private JPanel crearTarjetaPerfil() {
        // (Sin cambios)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_PANEL_BLANCO);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        lblFotoPerfilGrande = new JLabel();
        lblFotoPerfilGrande.setPreferredSize(new Dimension(100, 100));
        lblFotoPerfilGrande.setMinimumSize(new Dimension(100, 100));
        lblFotoPerfilGrande.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        gbc.gridx = 0; gbc.gridheight = 3; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH; gbc.insets = new Insets(0, 0, 0, 20);
        panel.add(lblFotoPerfilGrande, gbc);
        lblNombrePerfil = new JLabel("Cargando...");
        lblNombrePerfil.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 1; gbc.gridheight = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST; gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(lblNombrePerfil, gbc);
        lblCarreraPerfil = new JLabel("Cargando...");
        lblCarreraPerfil.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCarreraPerfil.setForeground(COLOR_BOTON_AZUL);
        gbc.gridy = 1; gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(lblCarreraPerfil, gbc);
        txtBiografia = new JTextArea("Cargando biografía...");
        txtBiografia.setFont(new Font("Arial", Font.PLAIN, 13));
        txtBiografia.setForeground(COLOR_TEXTO_GRIS);
        txtBiografia.setWrapStyleWord(true); txtBiografia.setLineWrap(true);
        txtBiografia.setEditable(false); txtBiografia.setOpaque(false);
        gbc.gridy = 2; gbc.insets = new Insets(10, 0, 0, 0);
        panel.add(txtBiografia, gbc);
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2; gbc.insets = new Insets(30, 0, 10, 0);
        JLabel lblHobbies = new JLabel("Pasatiempos e Intereses");
        lblHobbies.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblHobbies, gbc);
        gbc.gridy = 4; gbc.weighty = 1.0;
        panelTags = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        panelTags.setBackground(COLOR_PANEL_BLANCO);
        panel.add(panelTags, gbc);
        return panel;
    }

    private JPanel crearTarjetaGestion() {
        // (Sin cambios)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_PANEL_BLANCO);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(5, 0, 5, 0); gbc.ipady = 10; 
        btnExplorarCard = new JButton("Explorar");
        btnExplorarCard.setBackground(COLOR_BOTON_AZUL); btnExplorarCard.setForeground(Color.WHITE);
        btnExplorarCard.setFont(new Font("Arial", Font.BOLD, 14)); btnExplorarCard.setFocusPainted(false);
        panel.add(btnExplorarCard, gbc);
        gbc.gridy++;
        btnMatchCard = new JButton("Mis Match");
        btnMatchCard.setBackground(COLOR_BOTON_AZUL); btnMatchCard.setForeground(Color.WHITE);
        btnMatchCard.setFont(new Font("Arial", Font.BOLD, 14)); btnMatchCard.setFocusPainted(false);
        panel.add(btnMatchCard, gbc);
        gbc.gridy++; gbc.insets = new Insets(5, 0, 15, 0); 
        btnMensajesCard = new JButton("Mensajes");
        btnMensajesCard.setBackground(COLOR_BOTON_AZUL); btnMensajesCard.setForeground(Color.WHITE);
        btnMensajesCard.setFont(new Font("Arial", Font.BOLD, 14)); btnMensajesCard.setFocusPainted(false);
        panel.add(btnMensajesCard, gbc);
        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(5, 0, 5, 0);
        panel.add(new JSeparator(), gbc);
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(15, 0, 5, 0); 
        JLabel lblTituloGestion = new JLabel("Gestión de la Cuenta");
        lblTituloGestion.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblTituloGestion, gbc);
        gbc.gridy++; gbc.insets = new Insets(10, 0, 5, 0);
        btnEditarPerfil = new JButton("Editar Perfil");
        btnEditarPerfil.setBackground(Color.WHITE); btnEditarPerfil.setFont(new Font("Arial", Font.PLAIN, 14));
        btnEditarPerfil.setFocusPainted(false);
        panel.add(btnEditarPerfil, gbc);
        gbc.gridy++; gbc.insets = new Insets(5, 0, 5, 0);
        btnConfigurarBusqueda = new JButton("Configurar Preferencias");
        btnConfigurarBusqueda.setBackground(Color.WHITE); btnConfigurarBusqueda.setFont(new Font("Arial", Font.PLAIN, 14));
        btnConfigurarBusqueda.setFocusPainted(false);
        panel.add(btnConfigurarBusqueda, gbc);
        gbc.gridy++; gbc.insets = new Insets(5, 0, 20, 0);
        btnCerrarSesionCard = new JButton("Cerrar Sesión");
        btnCerrarSesionCard.setBackground(Color.WHITE); btnCerrarSesionCard.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCerrarSesionCard.setFocusPainted(false);
        panel.add(btnCerrarSesionCard, gbc);
        gbc.gridy++; gbc.ipady = 0;
        panel.add(new JSeparator(), gbc);
        gbc.gridy++; gbc.insets = new Insets(20, 0, 5, 0);
        lblEliminarCuenta = new JLabel("Eliminar mi cuenta");
        lblEliminarCuenta.setFont(new Font("Arial", Font.PLAIN, 13)); lblEliminarCuenta.setForeground(COLOR_LINK_ROJO);
        lblEliminarCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR)); lblEliminarCuenta.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblEliminarCuenta, gbc);
        return panel;
    }

    private void cargarDatosPerfil() {
        // (Sin cambios)
        lblNombrePerfil.setText(estudiante.getNombreEstudiante() + " " + estudiante.getApellidoPaterno());
        if (estudiante.getCarrera() != null) {
            lblCarreraPerfil.setText(estudiante.getCarrera().getNombreCarrera());
        }
        txtBiografia.setText("Haz clic en 'Editar Perfil' para añadir una biografía...");
        cargarFoto(lblFotoPerfilGrande, estudiante.getFotoPerfil(), 100, true);
        panelTags.removeAll();
        if (estudiante.getHobbies() != null) {
            for (Hobby hobby : estudiante.getHobbies()) {
                panelTags.add(crearTag(hobby.getNombreHobby()));
            }
        }
        if (estudiante.getIntereses() != null) {
            for (Interes interes : estudiante.getIntereses()) {
                panelTags.add(crearTag(interes.getNombreInteres()));
            }
        }
        panelTags.revalidate();
        panelTags.repaint();
    }
    
    private JLabel crearTag(String texto) {
        // (Sin cambios)
        JLabel tag = new JLabel(texto);
        tag.setFont(new Font("Arial", Font.PLAIN, 12));
        tag.setOpaque(true); tag.setBackground(new Color(230, 240, 250));
        tag.setForeground(COLOR_BOTON_AZUL); tag.setBorder(new EmptyBorder(8, 12, 8, 12));
        return tag;
    }

    private void cargarFoto(JLabel label, String rutaFoto, int tamano, boolean circular) {
        // (Sin cambios)
        if (rutaFoto != null && !rutaFoto.isEmpty()) {
            try {
                ImageIcon icono = new ImageIcon(rutaFoto);
                Image img = icono.getImage().getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);
                if (circular) {
                    BufferedImage imgCircular = new BufferedImage(tamano, tamano, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = imgCircular.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, tamano, tamano));
                    g2.drawImage(img, 0, 0, tamano, tamano, null);
                    g2.dispose();
                    label.setIcon(new ImageIcon(imgCircular));
                } else {
                    label.setIcon(new ImageIcon(img));
                }
                label.setText(null);
            } catch (Exception e) { label.setText("[Foto]"); }
        } else { label.setText("[Foto]"); }
    }

    private void editarPerfil() {
        new FrmEditarPerfil(
            this.estudiante,
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
    
    private void eliminarCuenta() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas eliminar tu cuenta?\nEsta acción es irreversible.",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                estudianteService.eliminarEstudiante(this.estudiante.getIdEstudiante());
                JOptionPane.showMessageDialog(this, "Cuenta eliminada exitosamente.");
                cerrarSesion();
            } catch (Exception e) { 
                JOptionPane.showMessageDialog(this, "Error al eliminar la cuenta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // =================================================================
    // --- NAVEGACIÓN (Actualizada a 9 servicios) ---
    // =================================================================
    
    // --- CAMBIO: Pasa los 9 servicios ---
    private void irAExplorar() {
        new FrmExplorar(
            this.estudiante, this.estudianteService, this.carreraService, 
            this.hobbyService, this.interesService, this.likeService, 
            this.matchService, this.chatService, this.mensajeService, 
            this.preferenciaService // AÑADIDO
        ).setVisible(true);
        this.dispose();
    }
    
    // --- CAMBIO: Pasa los 9 servicios ---
    private void irAMatch() {
        new FrmMatch(
            this.estudiante, this.estudianteService, this.carreraService, 
            this.hobbyService, this.interesService, this.likeService,
            this.matchService, this.chatService, this.mensajeService,
            this.preferenciaService // AÑADIDO
        ).setVisible(true);
        this.dispose();
    }

    // --- CAMBIO: Pasa los 9 servicios ---
    private void irAMensajes() {
        new FrmListaMensajes(
            this.estudiante, estudianteService, carreraService,
            hobbyService, interesService, likeService,
            matchService, chatService, mensajeService,
            preferenciaService
        ).setVisible(true);
        this.dispose();
    }
    
    // --- CAMBIO: Pasa los 9 servicios ---
    private void irAPreferencias() {
        new FrmPreferencias(
            this.estudiante, this.estudianteService, this.carreraService,
            this.hobbyService, this.interesService, this.likeService,
            this.matchService, this.chatService, this.mensajeService,
            this.preferenciaService // AÑADIDO
        ).setVisible(true);
        this.dispose();
    }

    private void irAConfiguracion() {
        JOptionPane.showMessageDialog(this, "Navegando a Configuración...");
        // Aquí irá FrmConfiguracion cuando esté implementado
    }

    // --- CAMBIO: Pasa los 9 servicios ---
    private void cerrarSesion() {
        new FrmLogin(
            this.estudianteService, this.carreraService, this.hobbyService,
            this.interesService, this.likeService, this.matchService,
            this.chatService, this.mensajeService, this.preferenciaService // AÑADIDO
        ).setVisible(true);
        this.dispose();
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
                
            // --- Datos de Prueba ---
            Estudiante estudiantePrueba = new Estudiante();
            estudiantePrueba.setIdEstudiante(1L);
            estudiantePrueba.setNombreEstudiante("Ana Sofía");
            estudiantePrueba.setApellidoPaterno("García");
            Carrera c = new Carrera(); c.setNombreCarrera("Ingeniería en Software");
            estudiantePrueba.setCarrera(c);
            Set<Hobby> h = new HashSet<>(); Hobby h1 = new Hobby(); h1.setNombreHobby("Gaming"); h.add(h1);
            estudiantePrueba.setHobbies(h);
            Set<Interes> i = new HashSet<>(); Interes i1 = new Interes(); i1.setNombreInteres("Programación"); i.add(i1);
            estudiantePrueba.setIntereses(i);
                
            new FrmPerfil(
                estudiantePrueba, estService, carService, hobService, 
                intService, likeService, matchService, chatService, msgService,
                prefService // AÑADIDO
            ).setVisible(true);
        });
    }
}
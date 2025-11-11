package Vistas;

/**
 *
 * @author alfre
 */

// --- CAMBIO: Imports para TODOS los 9 servicios ---
import Domain.Estudiante;
import Domain.Hobby;
import Domain.Interes;
import Domain.Like;
import Domain.Match;
import Domain.Carrera;
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
import java.time.LocalDateTime; // Para el main de prueba
import java.util.Collections;
import java.util.List;
import java.util.Set; // Para el main de prueba
import java.util.HashSet; // Para el main de prueba

public class FrmExplorar extends JFrame {

    // --- Servicios (LOS 9) ---
    private IEstudianteService estudianteService;
    private ICarreraService carreraService;
    private IHobbyService hobbyService;
    private IInteresService interesService;
    private ILikeService likeService;
    private IMatchService matchService; // AÑADIDO
    private IChatService chatService; // AÑADIDO
    private IMensajeService mensajeService; // AÑADIDO
    private IPreferenciaService preferenciaService; // AÑADIDO
    private Estudiante estudianteActual;

    // --- Datos ---
    private List<Estudiante> candidatos;
    private int indiceCandidatoActual = 0;

    // --- Colores ---
    private static final Color COLOR_FONDO = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_BARRA_IZQUIERDA = new Color(250, 250, 250);
    private static final Color COLOR_BOTON_AZUL = new Color(0, 86, 179);
    private static final Color COLOR_SELECCIONADO = new Color(230, 240, 250);
    private static final Color COLOR_LIKE = new Color(26, 188, 156);
    private static final Color COLOR_DISLIKE = new Color(231, 76, 60);

    // --- Componentes UI ---
    private JButton btnInicio, btnBuscar, btnMensajes, btnMiPerfil, btnConfiguracion;
    private JPanel panelCentralContenido;
    private JButton btnDislike;
    private JButton btnLike;

    // --- CAMBIO: Constructor ahora recibe 9 servicios ---
    public FrmExplorar(Estudiante estudiante, 
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
        cargarCandidatos();
    }

    private void initComponentes() {
        setTitle("Explorar Perfiles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearPanelNavegacion(), BorderLayout.WEST);
        add(crearPanelPrincipal(), BorderLayout.CENTER);

        // Listeners
        btnInicio.addActionListener(e -> recargarCandidatos());
        btnBuscar.addActionListener(e -> irAPreferencias());
        btnMensajes.addActionListener(e -> irAMensajes());
        btnMiPerfil.addActionListener(e -> irAPerfil());
        btnConfiguracion.addActionListener(e -> irAConfiguracion());
        btnDislike.addActionListener(e -> registrarInteraccion(false));
        btnLike.addActionListener(e -> registrarInteraccion(true));
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
        btnInicio.setBackground(COLOR_SELECCIONADO);
        btnInicio.setFont(new Font("Arial", Font.BOLD, 14));
        btnInicio.setBorder(BorderFactory.createCompoundBorder(
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
                if (boton != btnInicio) {
                    boton.setBackground(new Color(235, 235, 235));
                }
            }
            public void mouseExited(MouseEvent evt) {
                if (boton != btnInicio) {
                    boton.setBackground(COLOR_BARRA_IZQUIERDA);
                }
            }
        });
        return boton;
    }

    private JPanel crearPanelPrincipal() {
        // (Sin cambios)
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(40, 50, 40, 50));
        panelCentralContenido = new JPanel(new CardLayout());
        panelCentralContenido.setOpaque(false);
        panel.add(panelCentralContenido, BorderLayout.CENTER);
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        panelAcciones.setOpaque(false);
        btnDislike = new JButton("X");
        btnDislike.setFont(new Font("Arial", Font.BOLD, 28));
        btnDislike.setForeground(Color.WHITE);
        btnDislike.setBackground(COLOR_DISLIKE);
        btnDislike.setPreferredSize(new Dimension(80, 80));
        btnDislike.setFocusPainted(false);
        btnLike = new JButton("❤️");
        btnLike.setFont(new Font("Arial", Font.BOLD, 28));
        btnLike.setForeground(Color.WHITE);
        btnLike.setBackground(COLOR_LIKE);
        btnLike.setPreferredSize(new Dimension(80, 80));
        btnLike.setFocusPainted(false);
        panelAcciones.add(btnDislike);
        panelAcciones.add(btnLike);
        panel.add(panelAcciones, BorderLayout.SOUTH);
        return panel;
    }

    // =================================================================
    // --- LÓGICA DE CARGA Y ACCIONES ---
    // (Sin cambios en esta sección)
    // =================================================================

    private void cargarCandidatos() {
        try {
            Long idActual = this.estudianteActual.getIdEstudiante();
            // ¡AQUÍ ES DONDE SE USAN LAS PREFERENCIAS!
            // Tu PreferenciaDAO/Service ya tiene 'buscarEstudiantesCompatibles'
            // Podrías cambiar esta línea por:
            // this.candidatos = preferenciaService.buscarEstudiantesCompatibles(idActual, 50);
            // Por ahora, usamos el método original:
            this.candidatos = estudianteService.buscarEstudiantesCompatibles(idActual, 50);
            Collections.shuffle(this.candidatos);
            this.indiceCandidatoActual = 0;
            mostrarSiguienteCandidato();
        } catch (Exception e) { 
            e.printStackTrace();
            mostrarPanelFinal("Error al cargar perfiles: " + e.getMessage());
        }
    }

    private void mostrarSiguienteCandidato() {
        panelCentralContenido.removeAll();
        if (candidatos != null && indiceCandidatoActual < candidatos.size()) {
            Estudiante candidato = candidatos.get(indiceCandidatoActual);
            panelCentralContenido.add(new CardEstudiantePanel(candidato));
        } else {
            mostrarPanelFinal("¡Eso es todo por ahora! Vuelve más tarde.");
        }
        panelCentralContenido.revalidate();
        panelCentralContenido.repaint();
    }

    private void mostrarPanelFinal(String mensaje) {
        JPanel panelFinal = new JPanel(new GridBagLayout());
        panelFinal.setBackground(COLOR_PANEL_BLANCO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblMensaje = new JLabel(mensaje);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 18));
        panelFinal.add(lblMensaje, gbc);

        // Agregar botón de recargar
        gbc.gridy = 1;
        JButton btnRecargar = new JButton("Recargar");
        btnRecargar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRecargar.setBackground(COLOR_BOTON_AZUL);
        btnRecargar.setForeground(Color.WHITE);
        btnRecargar.setFocusPainted(false);
        btnRecargar.setPreferredSize(new Dimension(150, 40));
        btnRecargar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRecargar.addActionListener(e -> recargarCandidatos());
        panelFinal.add(btnRecargar, gbc);

        panelCentralContenido.add(panelFinal);
        btnLike.setEnabled(false);
        btnDislike.setEnabled(false);
    }
    
    private void registrarInteraccion(boolean leGusta) {
        if (candidatos == null || indiceCandidatoActual >= candidatos.size()) {
            return; 
        }
        Estudiante candidato = candidatos.get(indiceCandidatoActual);
        try {
            if (leGusta) {
                Like nuevoLike = new Like();
                nuevoLike.setEstudianteEmisor(this.estudianteActual);
                nuevoLike.setEstudianteReceptor(candidato);
                boolean esMatch = likeService.crearLike(nuevoLike);
                if (esMatch) {
                    JOptionPane.showMessageDialog(this, 
                        "¡Es un Match con " + candidato.getNombreEstudiante() + "!", 
                        "¡Nuevo Match!", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
             System.err.println("Error al registrar interacción: " + e.getMessage());
        } finally {
            indiceCandidatoActual++;
            mostrarSiguienteCandidato();
        }
    }

    // =================================================================
    // --- NAVEGACIÓN (Actualizada a 9 servicios) ---
    // =================================================================

    // --- CAMBIO: 'irAPerfil' ahora pasa los 9 servicios ---
    private void irAPerfil() {
        new FrmPerfil(
            estudianteActual, 
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
    
    // --- CAMBIO: 'irAMensajes' ahora pasa los 9 servicios ---
    private void irAMensajes() {
        new FrmListaMensajes(
            estudianteActual, estudianteService, carreraService,
            hobbyService, interesService, likeService,
            matchService, chatService, mensajeService,
            preferenciaService
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

    private void recargarCandidatos() {
        btnLike.setEnabled(true);
        btnDislike.setEnabled(true);
        cargarCandidatos();
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
            estudiantePrueba.setNombreEstudiante("Usuario Actual");

            new FrmExplorar(
                estudiantePrueba, estService, carService, hobService, 
                intService, likeService, matchService, chatService, msgService,
                prefService // AÑADIDO
            ).setVisible(true);
        });
    }
    
    /**
     * Panel personalizado para mostrar la tarjeta de un estudiante.
     */
    class CardEstudiantePanel extends JPanel {

        CardEstudiantePanel(Estudiante estudiante) {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220)),
                    new EmptyBorder(20, 20, 20, 20)
            ));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            // --- Foto ---
            JLabel lblFoto = new JLabel();
            lblFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblFoto.setPreferredSize(new Dimension(250, 250));
            lblFoto.setMinimumSize(new Dimension(250, 250));
            lblFoto.setMaximumSize(new Dimension(250, 250));
            lblFoto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            cargarFoto(lblFoto, estudiante.getFotoPerfil(), 250);
            add(lblFoto);

            add(Box.createRigidArea(new Dimension(0, 20)));

            // --- Nombre y Carrera ---
            JLabel lblNombre = new JLabel(estudiante.getNombreEstudiante() + " " + estudiante.getApellidoPaterno());
            lblNombre.setFont(new Font("Arial", Font.BOLD, 24));
            lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(lblNombre);

            if (estudiante.getCarrera() != null) {
                JLabel lblCarrera = new JLabel(estudiante.getCarrera().getNombreCarrera());
                lblCarrera.setFont(new Font("Arial", Font.PLAIN, 16));
                lblCarrera.setForeground(new Color(0, 86, 179)); // COLOR_BOTON_AZUL
                lblCarrera.setAlignmentX(Component.CENTER_ALIGNMENT);
                add(Box.createRigidArea(new Dimension(0, 5)));
                add(lblCarrera);
            }

            add(Box.createRigidArea(new Dimension(0, 20)));
            add(new JSeparator());
            add(Box.createRigidArea(new Dimension(0, 10)));

            JLabel lblTituloTags = new JLabel("Hobbies e Intereses");
            lblTituloTags.setFont(new Font("Arial", Font.BOLD, 16));
            lblTituloTags.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(lblTituloTags);
            add(Box.createRigidArea(new Dimension(0, 10)));

            // --- Tags ---
            JPanel panelTags = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
            panelTags.setBackground(Color.WHITE);

            if (estudiante.getHobbies() != null) {
                for (Hobby hobby : estudiante.getHobbies()) {
                    panelTags.add(crearTag(hobby.getNombreHobby()));
                }
            }
            if (estudiante.getIntereses() != null) {
                for (Interes interes : estudiante.getIntereses()) {
                    // Asumo que tu entidad Interes se llama getNombreInteres()
                    panelTags.add(crearTag(interes.getNombreInteres()));
                }
            }
            add(panelTags);

            add(Box.createVerticalGlue()); // Empuja todo hacia arriba
        }

        private JLabel crearTag(String texto) {
            JLabel tag = new JLabel(texto);
            tag.setFont(new Font("Arial", Font.PLAIN, 12));
            tag.setOpaque(true);
            tag.setBackground(new Color(230, 240, 250)); // COLOR_TAG
            tag.setForeground(new Color(0, 86, 179)); // COLOR_BOTON_AZUL
            tag.setBorder(new EmptyBorder(8, 12, 8, 12));
            return tag;
        }

        private void cargarFoto(JLabel label, String rutaFoto, int tamano) {
            if (rutaFoto != null && !rutaFoto.isEmpty()) {
                try {
                    ImageIcon icono = new ImageIcon(rutaFoto);
                    Image img = icono.getImage().getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);

                    // Recorte circular
                    BufferedImage imgCircular = new BufferedImage(tamano, tamano, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = imgCircular.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, tamano, tamano));
                    g2.drawImage(img, 0, 0, tamano, tamano, null);
                    g2.dispose();
                    label.setIcon(new ImageIcon(imgCircular));

                } catch (Exception e) {
                    label.setText("[Foto no disp.]");
                }
            } else {
                label.setText("[Sin Foto]");
            }
        }
    }
}
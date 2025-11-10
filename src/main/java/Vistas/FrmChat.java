package Vistas;

/**
 *
 * @author alfre
 */

// --- Imports de Dominio y Servicios ---
import Domain.Estudiante;
import Domain.Chat;
import Domain.Mensaje;
import Domain.Match;
import Domain.Carrera;
import Domain.Hobby;
import Domain.Interes;
import Domain.Preferencia; // AÑADIDO
import InterfaceService.*;
import Service.*; 

// --- Imports de Swing y AWT ---
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class FrmChat extends JFrame {

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

    // --- Datos ---
    private Estudiante estudianteActual;
    private Estudiante estudianteDestino; 
    private Long idMatch;
    private Chat chatActual; 

    // --- Colores ---
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_BURBUJA_PROPIA = new Color(0, 86, 179);
    private static final Color COLOR_TEXTO_PROPIO = Color.WHITE;

    // --- Componentes UI ---
    private JTextArea areaChatHistorial;
    private JTextField txtNuevoMensaje;
    private JButton btnEnviar;
    private JButton btnVolver;
    private JLabel lblNombreDestino;

    // --- CAMBIO: Constructor recibe 9 servicios + idMatch ---
    public FrmChat(Estudiante estudianteActual, 
                   Estudiante estudianteDestino,
                   Long idMatch, 
                   IEstudianteService estService, 
                   ICarreraService carService, 
                   IHobbyService hobService, 
                   IInteresService intService,
                   ILikeService likeService,
                   IMatchService matchService, 
                   IChatService chatService,
                   IMensajeService mensajeService,
                   IPreferenciaService preferenciaService) { // AÑADIDO

        this.estudianteActual = estudianteActual;
        this.estudianteDestino = estudianteDestino;
        this.idMatch = idMatch; 
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
        cargarHistorialChat();
    }

    private void initComponentes() {
        setTitle("Chat con " + estudianteDestino.getNombreEstudiante());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(crearPanelHeader(), BorderLayout.NORTH);
        areaChatHistorial = new JTextArea();
        areaChatHistorial.setEditable(false);
        areaChatHistorial.setLineWrap(true);
        areaChatHistorial.setWrapStyleWord(true);
        areaChatHistorial.setFont(new Font("Arial", Font.PLAIN, 14));
        areaChatHistorial.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(areaChatHistorial);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
        add(crearPanelEntrada(), BorderLayout.SOUTH);
        btnVolver.addActionListener(e -> volverAMatch());
        btnEnviar.addActionListener(e -> enviarMensaje());
        txtNuevoMensaje.addActionListener(e -> enviarMensaje());
    }

    private JPanel crearPanelHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_PANEL_BLANCO);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        panel.setPreferredSize(new Dimension(0, 50));
        btnVolver = new JButton("< Volver a Matches");
        btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(new EmptyBorder(10, 10, 10, 10));
        btnVolver.setContentAreaFilled(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(btnVolver, BorderLayout.WEST);
        lblNombreDestino = new JLabel(estudianteDestino.getNombreEstudiante());
        lblNombreDestino.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombreDestino.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblNombreDestino, BorderLayout.CENTER);
        panel.add(Box.createRigidArea(new Dimension(btnVolver.getPreferredSize().width, 0)), BorderLayout.EAST);
        return panel;
    }

    private JPanel crearPanelEntrada() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(COLOR_PANEL_BLANCO);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        txtNuevoMensaje = new JTextField();
        txtNuevoMensaje.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(txtNuevoMensaje, BorderLayout.CENTER);
        btnEnviar = new JButton("Enviar");
        btnEnviar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEnviar.setBackground(COLOR_BURBUJA_PROPIA);
        btnEnviar.setForeground(COLOR_TEXTO_PROPIO);
        btnEnviar.setFocusPainted(false);
        panel.add(btnEnviar, BorderLayout.EAST);
        return panel;
    }

    // =================================================================
    // --- LÓGICA DE CHAT (Usa tus métodos de servicio) ---
    // =================================================================

    private void cargarHistorialChat() {
        try {
            this.chatActual = chatService.buscarPorMatch(this.idMatch);
            if (this.chatActual == null) {
                throw new Exception("Error fatal: No se encontró el Chat para este Match. (¿Se creó en el LikeService?)");
            }
            List<Mensaje> mensajes = chatService.obtenerMensajesDelChat(this.chatActual.getIdChat(), 100);
            areaChatHistorial.setText(""); 
            if(mensajes == null || mensajes.isEmpty()){
                areaChatHistorial.setText("¡Es un Match! Sé el primero en enviar un mensaje.");
                return;
            }
            for (Mensaje msg : mensajes) {
                String autor = msg.getEstudianteEmisor().getIdEstudiante().equals(estudianteActual.getIdEstudiante()) 
                             ? "Tú" 
                             : estudianteDestino.getNombreEstudiante();
                appendMensaje(autor, msg.getContenido());
            }
        } catch (Exception e) {
            e.printStackTrace();
            areaChatHistorial.setText("Error al cargar el historial de chat.\n" + e.getMessage());
            btnEnviar.setEnabled(false); 
        }
    }

    private void enviarMensaje() {
        String texto = txtNuevoMensaje.getText().trim();
        if (texto.isEmpty() || this.chatActual == null) {
            return;
        }
        try {
            Mensaje nuevoMensaje = new Mensaje();
            nuevoMensaje.setChat(this.chatActual);
            nuevoMensaje.setEstudianteEmisor(this.estudianteActual);
            nuevoMensaje.setContenido(texto); 
            nuevoMensaje.setFechaEnvio(LocalDateTime.now());
            mensajeService.crearMensaje(nuevoMensaje);
            appendMensaje("Tú", texto);
            txtNuevoMensaje.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al enviar el mensaje: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void appendMensaje(String autor, String texto) {
        areaChatHistorial.append(autor + ": " + texto + "\n\n");
        areaChatHistorial.setCaretPosition(areaChatHistorial.getDocument().getLength());
    }

    // =================================================================
    // --- NAVEGACIÓN (Actualizada a 9 servicios) ---
    // =================================================================

    private void volverAMatch() {
        new FrmMatch(
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
            estudiantePrueba.setNombreEstudiante("Usuario Actual");
            Estudiante estudianteDestinoPrueba = new Estudiante();
            estudianteDestinoPrueba.setIdEstudiante(2L);
            estudianteDestinoPrueba.setNombreEstudiante("Ana Sofía");
            Long idMatchPrueba = 1L; 

            new FrmChat(
                estudiantePrueba, estudianteDestinoPrueba, idMatchPrueba,
                estService, carService, hobService, 
                intService, likeService, matchService, 
                chatService, msgService, prefService // AÑADIDO
            ).setVisible(true);
        });
    }
}
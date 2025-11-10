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
import Domain.Preferencia; // ¡NUEVO!
import InterfaceService.*; // Importa TODAS las 9 interfaces
import Service.*; // Importa TODAS las 9 implementaciones

// --- Imports de Swing y AWT ---
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.time.LocalDateTime;

public class FrmPreferencias extends JFrame {

    // --- Servicios (LOS 9) ---
    private IEstudianteService estudianteService;
    private ICarreraService carreraService;
    private IHobbyService hobbyService;
    private IInteresService interesService;
    private ILikeService likeService;
    private IMatchService matchService;
    private IChatService chatService; 
    private IMensajeService mensajeService; 
    private IPreferenciaService preferenciaService; // ¡NUEVO!
    
    private Estudiante estudianteActual;
    private Preferencia preferenciaActual; // El objeto que vamos a editar

    // --- Colores ---
    private static final Color COLOR_FONDO = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_BARRA_IZQUIERDA = new Color(250, 250, 250);
    private static final Color COLOR_BOTON_AZUL = new Color(0, 86, 179);
    private static final Color COLOR_SELECCIONADO = new Color(230, 240, 250);

    // --- Componentes de Navegacion ---
    private JButton btnInicio, btnBuscar, btnMensajes, btnMiPerfil, btnConfiguracion;
    
    // --- Componentes del Formulario ---
    private JComboBox<String> cmbGenero;
    private JSpinner spinnerEdadMin;
    private JSpinner spinnerEdadMax;
    private JComboBox<Carrera> cmbCarrera;

    // --- Constructor recibe los 9 servicios ---
    public FrmPreferencias(Estudiante estudiante, 
                           IEstudianteService estService, 
                           ICarreraService carService, 
                           IHobbyService hobService, 
                           IInteresService intService,
                           ILikeService likeService,
                           IMatchService matchService,
                           IChatService chatService,     
                           IMensajeService mensajeService,
                           IPreferenciaService prefService) { // ¡NUEVO!
        
        this.estudianteActual = estudiante;
        this.estudianteService = estService;
        this.carreraService = carService;
        this.hobbyService = hobService;
        this.interesService = intService;
        this.likeService = likeService;
        this.matchService = matchService;
        this.chatService = chatService;         
        this.mensajeService = mensajeService;   
        this.preferenciaService = prefService; // ¡NUEVO!
        
        initComponentes();
        cargarDatos();
    }

    private void initComponentes() {
        setTitle("Preferencias de Búsqueda");
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
        
        // Estado Activo (Mi Perfil)
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
        panel.setBorder(new EmptyBorder(40, 50, 40, 150)); 

        JLabel lblTitulo = new JLabel("Preferencias de Búsqueda");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setBorder(new EmptyBorder(0, 5, 0, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // --- Panel del Formulario ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(COLOR_PANEL_BLANCO);
        panelFormulario.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 5, 10, 5);

        // Fila 1: Género
        gbc.gridwidth = 1;
        panelFormulario.add(new JLabel("Mostrar perfiles de:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        // OJO: Tu Estudiante.java no tiene 'genero'. 
        // Esto se guarda, pero el filtro del DAO no funcionará hasta que lo añadas.
        String[] generos = {"Todos", "Hombre", "Mujer", "No binario"}; 
        cmbGenero = new JComboBox<>(generos);
        panelFormulario.add(cmbGenero, gbc);

        // Fila 2: Edad Mínima
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Edad mínima:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        spinnerEdadMin = new JSpinner(new SpinnerNumberModel(18, 18, 99, 1));
        panelFormulario.add(spinnerEdadMin, gbc);

        // Fila 3: Edad Máxima
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Edad máxima:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        spinnerEdadMax = new JSpinner(new SpinnerNumberModel(30, 18, 99, 1));
        panelFormulario.add(spinnerEdadMax, gbc);

        // Fila 4: Carrera
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Carrera preferida:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cmbCarrera = new JComboBox<>();
        panelFormulario.add(cmbCarrera, gbc);

        // Fila 5: Botones
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(COLOR_BOTON_AZUL);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFocusPainted(false);
        
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        panelFormulario.add(panelBotones, gbc);

        // Relleno
        gbc.gridy++;
        gbc.weighty = 1.0;
        panelFormulario.add(new JPanel(), gbc);
        
        panel.add(panelFormulario, BorderLayout.CENTER);
        
        // Listeners de Botones
        btnGuardar.addActionListener(e -> guardarPreferencias());
        btnCancelar.addActionListener(e -> irAPerfil());

        return panel;
    }

    // =================================================================
    // --- LÓGICA DE CARGA Y ACCIONES ---
    // =================================================================

    private void cargarDatos() {
        // Cargar el JComboBox de Carreras
        try {
            List<Carrera> carreras = carreraService.listarCarreras(100);
            cmbCarrera.addItem(null); // Opción "Cualquiera"
            for (Carrera c : carreras) {
                cmbCarrera.addItem(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar carreras: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Cargar las preferencias existentes del usuario
        try {
            // Usa tu método de servicio para buscar
            this.preferenciaActual = preferenciaService.buscarPorEstudiante(estudianteActual.getIdEstudiante());
            
            if (this.preferenciaActual == null) {
                // No tiene preferencias, creamos una nueva
                this.preferenciaActual = new Preferencia();
                this.preferenciaActual.setEstudiante(estudianteActual);
                // Dejamos los valores por defecto
                cmbGenero.setSelectedItem("Todos");
                spinnerEdadMin.setValue(18);
                spinnerEdadMax.setValue(30);
                cmbCarrera.setSelectedItem(null);
            } else {
                // Sí tiene, las cargamos en la UI
                cmbGenero.setSelectedItem(preferenciaActual.getGeneroPreferido() != null ? preferenciaActual.getGeneroPreferido() : "Todos");
                spinnerEdadMin.setValue(preferenciaActual.getEdadMinima() != null ? preferenciaActual.getEdadMinima() : 18);
                spinnerEdadMax.setValue(preferenciaActual.getEdadMaxima() != null ? preferenciaActual.getEdadMaxima() : 30);
                cmbCarrera.setSelectedItem(preferenciaActual.getCarreraPreferida());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar tus preferencias: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarPreferencias() {
        try {
            // Recolectar datos de la UI
            preferenciaActual.setGeneroPreferido((String) cmbGenero.getSelectedItem());
            preferenciaActual.setEdadMinima((Integer) spinnerEdadMin.getValue());
            preferenciaActual.setEdadMaxima((Integer) spinnerEdadMax.getValue());
            preferenciaActual.setCarreraPreferida((Carrera) cmbCarrera.getSelectedItem());
            
            if (preferenciaActual.getEdadMinima() > preferenciaActual.getEdadMaxima()) {
                JOptionPane.showMessageDialog(this, "La edad mínima no puede ser mayor que la edad máxima.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Llamar al servicio para guardar (crea o actualiza)
            preferenciaService.guardarPreferencias(preferenciaActual);
            
            JOptionPane.showMessageDialog(this, "Preferencias guardadas.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Actualizar el objeto Estudiante
            this.estudianteActual.setPreferencia(preferenciaActual);
            
            irAPerfil(); // Volver al perfil
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar preferencias: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =================================================================
    // --- NAVEGACIÓN (Actualizada a 9 servicios) ---
    // =================================================================

    private void irAExplorar() {
        new FrmExplorar(
            estudianteActual, estudianteService, carreraService, 
            hobbyService, interesService, likeService, 
            matchService, chatService, mensajeService, preferenciaService
        ).setVisible(true);
        this.dispose();
    }

    private void irAPerfil() {
        new FrmPerfil(
            estudianteActual, estudianteService, carreraService, 
            hobbyService, interesService, likeService,
            matchService, chatService, mensajeService, preferenciaService
        ).setVisible(true);
        this.dispose();
    }
    
    private void irAMensajes() {
        new FrmListaMensajes(
                this.estudianteActual,
                this.estudianteService,
                this.carreraService,
                this.hobbyService,
                this.interesService,
                this.likeService,
                this.matchService,
                this.chatService,
                this.mensajeService,
                this.preferenciaService
        ).setVisible(true);

        this.dispose();
    }
}
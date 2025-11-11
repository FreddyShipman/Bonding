package Vistas;

// --- CAMBIO: Imports para TODOS los 9 servicios ---
import Domain.Estudiante;
import Domain.Hobby;
import Domain.Interes;
import Domain.Carrera;
import Domain.Like;
import Domain.Match;
import Domain.Chat;
import Domain.Mensaje;
import Domain.Preferencia; // AÑADIDO
import Excepciones.*;
import Service.CarreraService;
import Service.EstudianteService;
import Service.HobbyService;
import Service.InteresService;
import Service.LikeService;
import Service.MatchService; // AÑADIDO
import Service.ChatService; // AÑADIDO
import Service.MensajeService; // AÑADIDO
import Service.PreferenciaService; // AÑADIDO
import InterfaceService.ICarreraService;
import InterfaceService.IEstudianteService;
import InterfaceService.IHobbyService;
import InterfaceService.IInteresService;
import InterfaceService.ILikeService;
import InterfaceService.IMatchService; // AÑADIDO
import InterfaceService.IChatService; // AÑADIDO
import InterfaceService.IMensajeService; // AÑADIDO
import InterfaceService.IPreferenciaService; // AÑADIDO

// Imports de Java Swing
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDateTime; // Para el main de prueba

// Imports para la lógica de FOTOS
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.awt.image.BufferedImage; // Para la foto circular

public class FrmCompletarPerfil extends JFrame {

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

    // --- Datos ---
    private Estudiante estudiante;
    private Set<Hobby> hobbiesSet;
    private Set<Interes> interesesSet;
    private String rutaFotoPerfil;

    // --- Componentes UI ---
    private JButton btnSubirFoto, btnFinalizar;
    private JTextField txtHobby, txtInteres;
    private JPanel panelHobbiesTags, panelInteresesTags, panelSubirFoto;
    private JLabel lblFotoPreview;
    private JComboBox<String> cmbGenero;
    private JSpinner spinnerEdad;

    // --- Colores ---
    private static final Color COLOR_FONDO_GRIS = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_BOTON = new Color(0, 86, 179);
    private static final Color COLOR_PLACEHOLDER = new Color(180, 180, 180);
    private static final Color COLOR_TAG = new Color(230, 240, 250);

    // --- CAMBIO: Constructor recibe los 9 servicios ---
    public FrmCompletarPerfil(Estudiante estudiante, 
                              IEstudianteService estudianteService, 
                              ICarreraService carreraService, 
                              IHobbyService hobbyService, 
                              IInteresService interesService,
                              ILikeService likeService,
                              IMatchService matchService, // AÑADIDO
                              IChatService chatService, // AÑADIDO
                              IMensajeService mensajeService, // AÑADIDO
                              IPreferenciaService preferenciaService) { // AÑADIDO
        
        this.estudianteService = estudianteService;
        this.carreraService = carreraService;
        this.hobbyService = hobbyService;
        this.interesService = interesService;
        this.likeService = likeService;
        this.matchService = matchService; // AÑADIDO
        this.chatService = chatService; // AÑADIDO
        this.mensajeService = mensajeService; // AÑADIDO
        this.preferenciaService = preferenciaService; // AÑADIDO
        
        this.estudiante = estudiante;
        this.hobbiesSet = new HashSet<>();
        this.interesesSet = new HashSet<>();
        this.rutaFotoPerfil = null;
        
        initComponentes();
    }

    private void initComponentes() {
        // (El código de UI no cambia)
        setTitle("Registro (Paso 2: Perfil e Intereses)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1000);
        setLocationRelativeTo(null);
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(COLOR_FONDO_GRIS);
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(COLOR_PANEL_BLANCO);
        panelFormulario.setBorder(new EmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        gbc.insets = new Insets(8, 5, 8, 5);
        JLabel lblPaso = new JLabel("Paso 2 de 2");
        lblPaso.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(lblPaso, gbc);
        gbc.gridy++; gbc.insets = new Insets(2, 5, 20, 5);
        JProgressBar progressBar = new JProgressBar(0, 2);
        progressBar.setValue(2); progressBar.setStringPainted(false);
        panelFormulario.add(progressBar, gbc);
        gbc.gridy++; gbc.insets = new Insets(10, 5, 5, 5);
        JLabel lblTitulo = new JLabel("Completa Tu Perfil");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        panelFormulario.add(lblTitulo, gbc);
        gbc.gridy++; gbc.insets = new Insets(0, 5, 25, 5);
        JLabel lblSubtitulo = new JLabel("¡Deja que los demás sepan quién eres!");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.GRAY);
        panelFormulario.add(lblSubtitulo, gbc);
        gbc.gridy++; gbc.ipady = 150; 
        panelSubirFoto = crearPanelSubirFoto();
        panelFormulario.add(panelSubirFoto, gbc);
        gbc.ipady = 0;
        gbc.gridy++; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; gbc.insets = new Insets(10, 5, 5, 5);
        btnSubirFoto = new JButton("Subir Foto");
        btnSubirFoto.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSubirFoto.setFocusPainted(false); btnSubirFoto.setBackground(new Color(235, 235, 235));
        btnSubirFoto.setBorder(new EmptyBorder(5, 15, 5, 15));
        panelFormulario.add(btnSubirFoto, gbc);

        // --- Campos de Género y Edad ---
        gbc.gridy++; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH; gbc.insets = new Insets(25, 5, 5, 5);
        JLabel lblGenero = new JLabel("Género");
        lblGenero.setFont(new Font("Arial", Font.BOLD, 16));
        panelFormulario.add(lblGenero, gbc);

        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(5, 5, 5, 5);
        String[] generos = {"Hombre", "Mujer", "No binario"};
        cmbGenero = new JComboBox<>(generos);
        cmbGenero.setFont(new Font("Arial", Font.PLAIN, 14));
        panelFormulario.add(cmbGenero, gbc);

        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(15, 5, 5, 5);
        JLabel lblEdad = new JLabel("Edad");
        lblEdad.setFont(new Font("Arial", Font.BOLD, 16));
        panelFormulario.add(lblEdad, gbc);

        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(5, 5, 5, 5);
        spinnerEdad = new JSpinner(new SpinnerNumberModel(18, 18, 99, 1));
        spinnerEdad.setFont(new Font("Arial", Font.PLAIN, 14));
        panelFormulario.add(spinnerEdad, gbc);

        gbc.gridy++; gbc.ipady = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH; gbc.insets = new Insets(25, 5, 5, 5);
        JLabel lblHobbies = new JLabel("Mis Pasatiempos");
        lblHobbies.setFont(new Font("Arial", Font.BOLD, 16));
        panelFormulario.add(lblHobbies, gbc);
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(5, 5, 5, 5);
        txtHobby = new JTextField();
        addPlaceholder(txtHobby, "Añade un pasatiempo (ej. Gaming, Senderismo, Programar...)");
        panelFormulario.add(txtHobby, gbc);
        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(0, 5, 10, 5);
        panelHobbiesTags = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelHobbiesTags.setBackground(Color.WHITE);
        panelFormulario.add(panelHobbiesTags, gbc);
        gbc.gridy++; gbc.insets = new Insets(15, 5, 5, 5);
        JLabel lblIntereses = new JLabel("Mis Intereses");
        lblIntereses.setFont(new Font("Arial", Font.BOLD, 16));
        panelFormulario.add(lblIntereses, gbc);
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(5, 5, 5, 5);
        txtInteres = new JTextField();
        addPlaceholder(txtInteres, "Añade un interés (ej. IA, Música Indie, Pelis Sci-Fi...)");
        panelFormulario.add(txtInteres, gbc);
        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(0, 5, 25, 5);
        panelInteresesTags = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelInteresesTags.setBackground(Color.WHITE);
        panelFormulario.add(panelInteresesTags, gbc);
        gbc.gridy++; gbc.ipady = 12; gbc.insets = new Insets(20, 5, 10, 5);
        btnFinalizar = new JButton("Finalizar Registro");
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFinalizar.setBackground(COLOR_BOTON); btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFocusPainted(false); btnFinalizar.setOpaque(true);
        btnFinalizar.setBorderPainted(false);
        panelFormulario.add(btnFinalizar, gbc);
        gbc.gridy++; gbc.weighty = 1.0;
        JPanel spacer = new JPanel(); spacer.setBackground(Color.WHITE);
        panelFormulario.add(spacer, gbc);
        panelPrincipal.add(panelFormulario, new GridBagConstraints());
        btnSubirFoto.addActionListener(e -> subirFoto());
        txtHobby.addActionListener(e -> agregarHobby());
        txtInteres.addActionListener(e -> agregarInteres());
        btnFinalizar.addActionListener(e -> guardarPerfil());
    }

    private JPanel crearPanelSubirFoto() {
        // (Sin cambios)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        Border dashedBorder = BorderFactory.createDashedBorder(Color.GRAY, 1, 5, 2, false);
        panel.setBorder(BorderFactory.createCompoundBorder(dashedBorder, new EmptyBorder(10, 10, 10, 10)));
        lblFotoPreview = new JLabel();
        lblFotoPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblFotoPreview.setPreferredSize(new Dimension(150, 150));
        lblFotoPreview.setText("<html><center>Foto de Perfil<br><font size='-2' color='gray'>Sube una foto</font></center></html>");
        lblFotoPreview.setFont(new Font("Arial", Font.BOLD, 16));
        lblFotoPreview.setForeground(Color.BLACK);
        panel.add(lblFotoPreview, new GridBagConstraints());
        return panel;
    }

    private void subirFoto() {
        // (Sin cambios - lógica de copia de archivo)
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una Foto de Perfil");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            try {
                String uploadDir = "user_uploads/fotos_perfil/";
                new File(uploadDir).mkdirs(); 
                String extension = "";
                String nombreArchivo = archivoSeleccionado.getName();
                int i = nombreArchivo.lastIndexOf('.');
                if (i > 0) {
                    extension = nombreArchivo.substring(i + 1);
                }
                String uniqueFileName = UUID.randomUUID().toString() + "." + extension;
                Path sourcePath = archivoSeleccionado.toPath();
                Path destPath = Paths.get(uploadDir + uniqueFileName);
                Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                this.rutaFotoPerfil = destPath.toString(); 
                ImageIcon iconoOriginal = new ImageIcon(this.rutaFotoPerfil);
                Image imagen = iconoOriginal.getImage();
                Image imagenRedimensionada = imagen.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblFotoPreview.setIcon(new ImageIcon(imagenRedimensionada));
                lblFotoPreview.setText(null);
                btnSubirFoto.setText("Cambiar Foto");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar o cargar la imagen.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
                this.rutaFotoPerfil = null;
            }
        }
    }

    private void agregarHobby() {
        // (Sin cambios)
        String nombreHobby = txtHobby.getText().trim();
        if (nombreHobby.isEmpty() || nombreHobby.startsWith("Añade un pasatiempo")) {
            return;
        }
        try {
            Hobby hobby = hobbyService.buscarPorNombre(nombreHobby);
            if (hobby == null) {
                hobby = new Hobby();
                hobby.setNombreHobby(nombreHobby);
                hobby = hobbyService.crearHobby(hobby);
            }
            if (hobbiesSet.add(hobby)) {
                crearTagVisual(hobby, panelHobbiesTags);
                txtHobby.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ya has añadido ese pasatiempo.", "Duplicado", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al añadir pasatiempo: " + e.getMessage(), "Error de Servicio", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarInteres() {
        // (Sin cambios)
        String nombreInteres = txtInteres.getText().trim();
        if (nombreInteres.isEmpty() || nombreInteres.startsWith("Añade un interés")) {
            return;
        }
        try {
            Interes interes = interesService.buscarPorNombre(nombreInteres);
            if (interes == null) {
                interes = new Interes();
                interes.setNombreInteres(nombreInteres);
                interes.setCategoria("General");
                interes = interesService.crearInteres(interes);
            }
            if (interesesSet.add(interes)) {
                crearTagVisual(interes, panelInteresesTags);
                txtInteres.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ya has añadido ese interés.", "Duplicado", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al añadir interés: " + e.getMessage(), "Error de Servicio", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- CAMBIO: Navega a FrmExplorar y pasa los 9 servicios ---
    private void guardarPerfil() {
        if (this.rutaFotoPerfil == null || this.rutaFotoPerfil.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, sube una foto de perfil.", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (this.hobbiesSet.isEmpty() || this.interesesSet.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Añade al menos un pasatiempo e interés.", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            this.estudiante.setFotoPerfil(this.rutaFotoPerfil);
            this.estudiante.setGenero((String) cmbGenero.getSelectedItem());
            this.estudiante.setEdad((Integer) spinnerEdad.getValue());
            this.estudiante.setHobbies(this.hobbiesSet);
            this.estudiante.setIntereses(this.interesesSet);
            // (Aquí también podrías setear una biografía si añades el campo)

            estudianteService.actualizarEstudiante(this.estudiante);
            
            JOptionPane.showMessageDialog(this, 
                    "¡Perfil completado! Bienvenido, " + this.estudiante.getNombreEstudiante() + ".", 
                    "Registro Completo", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            // Navegación a FrmExplorar
            FrmExplorar frmExplorar = new FrmExplorar(
                this.estudiante,
                this.estudianteService,
                this.carreraService,
                this.hobbyService,
                this.interesService,
                this.likeService,
                this.matchService, // AÑADIDO
                this.chatService, // AÑADIDO
                this.mensajeService, // AÑADIDO
                this.preferenciaService // AÑADIDO
            );
            frmExplorar.setVisible(true);
            this.dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar tu perfil: " + e.getMessage(), "Error de Servicio", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearTagVisual(final Object entidad, final JPanel panelDestino) {
        // (Sin cambios)
        String nombre = (entidad instanceof Hobby) ? ((Hobby) entidad).getNombreHobby() : ((Interes) entidad).getNombreInteres();
        final JPanel tagPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        tagPanel.setBackground(COLOR_TAG);
        tagPanel.setBorder(new EmptyBorder(3, 5, 3, 5));
        JLabel tagLabel = new JLabel(nombre);
        tagLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        tagPanel.add(tagLabel);
        JButton removeButton = new JButton("x");
        removeButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeButton.setMargin(new Insets(0, 2, 0, 2));
        removeButton.setBorder(null); removeButton.setContentAreaFilled(false);
        removeButton.setForeground(Color.GRAY); removeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelDestino.remove(tagPanel);
                if (entidad instanceof Hobby) {
                    hobbiesSet.remove((Hobby) entidad);
                } else if (entidad instanceof Interes) {
                    interesesSet.remove((Interes) entidad);
                }
                panelDestino.revalidate();
                panelDestino.repaint();
            }
        });
        tagPanel.add(removeButton);
        panelDestino.add(tagPanel);
        panelDestino.revalidate();
        panelDestino.repaint();
    }
    
    private void addPlaceholder(final JTextField field, final String placeholder) {
        // (Sin cambios)
        field.setText(placeholder);
        field.setForeground(COLOR_PLACEHOLDER);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(COLOR_PLACEHOLDER);
                }
            }
        });
    }

    // --- CAMBIO: 'main' ahora crea e inyecta los 9 servicios ---
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Estudiante estudiantePrueba = new Estudiante();
                estudiantePrueba.setNombreEstudiante("UsuarioPrueba");
                
                // Creación de los 9 servicios
                IEstudianteService estService = new EstudianteService();
                ICarreraService carService = new CarreraService();
                IHobbyService hobService = new HobbyService();
                IInteresService intService = new InteresService();
                ILikeService likeService = new LikeService(); 
                IMatchService matchService = new MatchService(); // AÑADIDO
                IChatService chatService = new ChatService(); // AÑADIDO
                IMensajeService mensajeService = new MensajeService(); // AÑADIDO
                IPreferenciaService prefService = new PreferenciaService(); // AÑADIDO
                
                new FrmCompletarPerfil(
                    estudiantePrueba, estService, carService, 
                    hobService, intService, likeService, 
                    matchService, chatService, mensajeService,
                    prefService // Inyecta los 9
                ).setVisible(true);
            }
        });
    }
}
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
import Domain.Like;
import Domain.Match;
import Domain.Chat;
import Domain.Mensaje;
import Domain.Preferencia;
import InterfaceService.*;
import Service.*; 

// --- Imports de Swing y AWT ---
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
import java.time.LocalDateTime;

// --- Imports para la lógica de FOTOS ---
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.awt.image.BufferedImage;
import javax.swing.text.JTextComponent; // Para el placeholder

public class FrmEditarPerfil extends JFrame {

    // --- Servicios (LOS 9) ---
    private IEstudianteService estudianteService;
    private ICarreraService carreraService;
    private IHobbyService hobbyService;
    private IInteresService interesService;
    private ILikeService likeService;
    private IMatchService matchService;
    private IChatService chatService;
    private IMensajeService mensajeService;
    private IPreferenciaService preferenciaService;

    // --- Datos ---
    private Estudiante estudiante; // El estudiante que estamos editando
    private Set<Hobby> hobbiesSet;
    private Set<Interes> interesesSet;
    private String rutaFotoPerfil;

    // --- Componentes UI ---
    private JButton btnSubirFoto;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JTextArea txtBiografia; // AÑADIDO
    private JTextField txtHobby;
    private JTextField txtInteres;
    private JPanel panelHobbiesTags;
    private JPanel panelInteresesTags;
    private JPanel panelSubirFoto;
    private JLabel lblFotoPreview;

    // --- Colores ---
    private static final Color COLOR_FONDO_GRIS = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_BOTON = new Color(0, 86, 179);
    private static final Color COLOR_PLACEHOLDER = new Color(180, 180, 180);
    private static final Color COLOR_TAG = new Color(230, 240, 250);

    // --- CAMBIO: Constructor recibe los 9 servicios ---
    public FrmEditarPerfil(Estudiante estudiante, 
                           IEstudianteService estudianteService, 
                           ICarreraService carreraService, 
                           IHobbyService hobbyService, 
                           IInteresService interesService,
                           ILikeService likeService,
                           IMatchService matchService,
                           IChatService chatService,
                           IMensajeService mensajeService,
                           IPreferenciaService preferenciaService) {
        
        this.estudiante = estudiante;
        this.estudianteService = estudianteService;
        this.carreraService = carreraService;
        this.hobbyService = hobbyService;
        this.interesService = interesService;
        this.likeService = likeService;
        this.matchService = matchService;
        this.chatService = chatService;
        this.mensajeService = mensajeService;
        this.preferenciaService = preferenciaService;
        
        // Inicializar los Sets con los datos existentes
        this.hobbiesSet = new HashSet<>(estudiante.getHobbies());
        this.interesesSet = new HashSet<>(estudiante.getIntereses());
        this.rutaFotoPerfil = estudiante.getFotoPerfil();
        
        initComponentes();
        
        // Cargar los datos en la UI
        cargarDatosExistentes();
    }

    private void initComponentes() {
        setTitle("Editar Perfil");
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

        JLabel lblTitulo = new JLabel("Editar Tu Perfil");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        panelFormulario.add(lblTitulo, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 5, 25, 5);
        JLabel lblSubtitulo = new JLabel("Actualiza tu foto, biografía, hobbies e intereses.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.GRAY);
        panelFormulario.add(lblSubtitulo, gbc);

        gbc.gridy++; gbc.ipady = 150; 
        panelSubirFoto = crearPanelSubirFoto();
        panelFormulario.add(panelSubirFoto, gbc);
        gbc.ipady = 0;

        gbc.gridy++; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER; gbc.insets = new Insets(10, 5, 5, 5);
        btnSubirFoto = new JButton("Cambiar Foto");
        btnSubirFoto.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSubirFoto.setFocusPainted(false); btnSubirFoto.setBackground(new Color(235, 235, 235));
        btnSubirFoto.setBorder(new EmptyBorder(5, 15, 5, 15));
        panelFormulario.add(btnSubirFoto, gbc);
        
        // --- Campo de Biografía ---
        gbc.gridy++; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH; gbc.insets = new Insets(25, 5, 5, 5);
        JLabel lblBiografia = new JLabel("Mi Biografía");
        lblBiografia.setFont(new Font("Arial", Font.BOLD, 16));
        panelFormulario.add(lblBiografia, gbc);
        
        gbc.gridy++; gbc.ipady = 80; // Hacemos el JTextArea más alto
        txtBiografia = new JTextArea();
        txtBiografia.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBiografia.setLineWrap(true);
        txtBiografia.setWrapStyleWord(true);
        txtBiografia.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            new EmptyBorder(5, 5, 5, 5)
        ));
        panelFormulario.add(txtBiografia, gbc);
        gbc.ipady = 0;

        // Pasatiempos
        gbc.gridy++; gbc.fill = GridBagConstraints.HORIZONTAL;
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

        // Intereses
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

        // --- Botón "Guardar" ---
        gbc.gridy++; gbc.ipady = 12; gbc.insets = new Insets(20, 5, 10, 5);
        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBackground(COLOR_BOTON);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false); btnGuardar.setOpaque(true);
        btnGuardar.setBorderPainted(false);
        panelFormulario.add(btnGuardar, gbc);

        // --- Botón "Cancelar" ---
        gbc.gridy++;
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        panelFormulario.add(btnCancelar, gbc);

        // Relleno
        gbc.gridy++; gbc.weighty = 1.0;
        JPanel spacer = new JPanel(); spacer.setBackground(Color.WHITE);
        panelFormulario.add(spacer, gbc);
        panelPrincipal.add(panelFormulario, new GridBagConstraints());

        // --- Listeners ---
        btnSubirFoto.addActionListener(e -> subirFoto());
        txtHobby.addActionListener(e -> agregarHobby());
        txtInteres.addActionListener(e -> agregarInteres());
        btnGuardar.addActionListener(e -> guardarPerfil());
        btnCancelar.addActionListener(e -> volverAPerfil());
    }

    private JPanel crearPanelSubirFoto() {
        // (Idéntico a FrmCompletarPerfil)
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
        // (Idéntico a FrmCompletarPerfil)
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
        // (Idéntico a FrmCompletarPerfil)
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
        // (Idéntico a FrmCompletarPerfil)
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

    // --- Carga los datos existentes del Estudiante ---
    private void cargarDatosExistentes() {
        // Cargar Foto
        if (this.rutaFotoPerfil != null && !this.rutaFotoPerfil.isEmpty()) {
            try {
                ImageIcon iconoOriginal = new ImageIcon(this.rutaFotoPerfil);
                Image imagen = iconoOriginal.getImage();
                Image imagenRedimensionada = imagen.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblFotoPreview.setIcon(new ImageIcon(imagenRedimensionada));
                lblFotoPreview.setText(null);
            } catch (Exception e) {
                lblFotoPreview.setText("[Foto no encontrada]");
            }
        }
        
        // Cargar Biografía (Asumo que Estudiante tiene get/setBiografia)
        // if (this.estudiante.getBiografia() != null) {
        //     txtBiografia.setText(this.estudiante.getBiografia());
        // } else {
             addPlaceholder(txtBiografia, "Escribe algo sobre ti...");
        // }

        // Cargar Hobbies
        if (this.hobbiesSet != null) {
            for (Hobby hobby : this.hobbiesSet) {
                crearTagVisual(hobby, panelHobbiesTags);
            }
        }
        
        // Cargar Intereses
        if (this.interesesSet != null) {
            for (Interes interes : this.interesesSet) {
                crearTagVisual(interes, panelInteresesTags);
            }
        }
    }

    // --- Lógica de guardado (llama a ACTUALIZAR) ---
    private void guardarPerfil() {
        try {
            this.estudiante.setFotoPerfil(this.rutaFotoPerfil);
            this.estudiante.setHobbies(this.hobbiesSet);
            this.estudiante.setIntereses(this.interesesSet);
            
            // Asumo que Estudiante tiene get/setBiografia
            // if(!txtBiografia.getText().equals("Escribe algo sobre ti...")) {
            //    this.estudiante.setBiografia(txtBiografia.getText().trim());
            // }

            // Llama a ACTUALIZAR
            estudianteService.actualizarEstudiante(this.estudiante);
            
            JOptionPane.showMessageDialog(this, 
                    "¡Perfil actualizado exitosamente!", 
                    "Guardado", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            volverAPerfil(); // Regresa al perfil

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar tu perfil: " + e.getMessage(), "Error de Servicio", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Navegación de vuelta a FrmPerfil (pasa 9 servicios) ---
    private void volverAPerfil() {
        new FrmPerfil(
            this.estudiante,
            this.estudianteService,
            this.carreraService,
            this.hobbyService,
            this.interesService,
            this.likeService,
            this.matchService,
            this.chatService,
            this.mensajeService,
            this.preferenciaService // AÑADIDO
        ).setVisible(true);
        this.dispose();
    }

    private void crearTagVisual(final Object entidad, final JPanel panelDestino) {
        // (Idéntico a FrmCompletarPerfil)
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
    
    private void addPlaceholder(final JTextComponent field, final String placeholder) {
        // (Ajustado para JTextComponent para que funcione con JTextArea)
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

    // --- Main de prueba actualizado a 9 servicios ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Estudiante estudiantePrueba = new Estudiante();
            estudiantePrueba.setNombreEstudiante("UsuarioPrueba");
            estudiantePrueba.setHobbies(new HashSet<>()); 
            estudiantePrueba.setIntereses(new HashSet<>());
            
            IEstudianteService estService = new EstudianteService();
            ICarreraService carService = new CarreraService();
            IHobbyService hobService = new HobbyService();
            IInteresService intService = new InteresService();
            ILikeService likeService = new LikeService(); 
            IMatchService matchService = new MatchService();
            IChatService chatService = new ChatService();
            IMensajeService mensajeService = new MensajeService();
            IPreferenciaService prefService = new PreferenciaService();
                
            new FrmEditarPerfil(
                estudiantePrueba, estService, carService, 
                hobService, intService, likeService, 
                matchService, chatService, mensajeService,
                prefService
            ).setVisible(true);
        });
    }
}
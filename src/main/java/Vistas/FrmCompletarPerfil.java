package Vistas;

/**
 *
 * @author alfre
 */
import Domain.Estudiante;
import Domain.Hobby;
import Domain.Interes;
import Service.EstudianteService;
import Service.HobbyService;
import InterfaceService.IEstudianteService;
import InterfaceService.IHobbyService;
import InterfaceService.IInteresService;
import Service.InteresService;

// Imports de Java Swing
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FrmCompletarPerfil extends JFrame {

    // --- Servicios ---
    private IEstudianteService estudianteService;
    private IHobbyService hobbyService;
    private IInteresService interesService;

    // --- Datos ---
    private Estudiante estudiante;
    private Set<Hobby> hobbiesSet;
    private Set<Interes> interesesSet;
    private String rutaFotoPerfil;

    // --- Componentes UI ---
    private JButton btnSubirFoto;
    private JButton btnFinalizar;
    private JTextField txtHobby;
    private JTextField txtInteres;
    private JPanel panelHobbiesTags;
    private JPanel panelInteresesTags;
    private JPanel panelSubirFoto;
    private JLabel lblFotoPreview; // Para la vista previa

    // --- Colores ---
    private static final Color COLOR_FONDO_GRIS = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_BOTON = new Color(0, 86, 179);
    private static final Color COLOR_PLACEHOLDER = new Color(180, 180, 180);
    private static final Color COLOR_TAG = new Color(230, 240, 250);

    public FrmCompletarPerfil(Estudiante estudiante) {
        // 1. Inicializar servicios y datos
        this.estudianteService = new EstudianteService();
        this.hobbyService = new HobbyService();
        this.interesService = new InteresService();
        
        this.estudiante = estudiante;
        this.hobbiesSet = new HashSet<>();
        this.interesesSet = new HashSet<>();
        this.rutaFotoPerfil = null;
        
        // 2. Construir UI
        initComponentes();
    }

    private void initComponentes() {
        // --- 1. Ventana ---
        setTitle("Registro (Paso 2: Perfil e Intereses)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 1000);
        setLocationRelativeTo(null);

        // --- 2. Panel Principal (Gris) con Scroll ---
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(COLOR_FONDO_GRIS);
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. Panel Formulario (Blanco) ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(COLOR_PANEL_BLANCO);
        panelFormulario.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(8, 5, 8, 5);

        // --- 4. Componentes ---

        // Barra de Progreso
        JLabel lblPaso = new JLabel("Paso 2 de 2");
        lblPaso.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(lblPaso, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(2, 5, 20, 5);
        JProgressBar progressBar = new JProgressBar(0, 2);
        progressBar.setValue(2);
        progressBar.setStringPainted(false);
        panelFormulario.add(progressBar, gbc);

        // Titulo
        gbc.gridy++;
        gbc.insets = new Insets(10, 5, 5, 5);
        JLabel lblTitulo = new JLabel("Completa Tu Perfil");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        panelFormulario.add(lblTitulo, gbc);

        // Subtitulo
        gbc.gridy++;
        gbc.insets = new Insets(0, 5, 25, 5);
        JLabel lblSubtitulo = new JLabel("¡Deja que los demás sepan quién eres!");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.GRAY);
        panelFormulario.add(lblSubtitulo, gbc);

        // Area de Carga de Foto
        gbc.gridy++;
        gbc.ipady = 150; // Altura fija para el panel de foto
        panelSubirFoto = crearPanelSubirFoto();
        panelFormulario.add(panelSubirFoto, gbc);
        gbc.ipady = 0;

        // Boton Subir Foto (separado)
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 5, 5, 5);
        btnSubirFoto = new JButton("Subir Foto");
        btnSubirFoto.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSubirFoto.setFocusPainted(false);
        btnSubirFoto.setBackground(new Color(235, 235, 235));
        btnSubirFoto.setBorder(new EmptyBorder(5, 15, 5, 15));
        panelFormulario.add(btnSubirFoto, gbc);

        // Pasatiempos
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(25, 5, 5, 5);
        JLabel lblHobbies = new JLabel("Mis Pasatiempos");
        lblHobbies.setFont(new Font("Arial", Font.BOLD, 16));
        panelFormulario.add(lblHobbies, gbc);

        gbc.gridy++;
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 5, 5, 5);
        txtHobby = new JTextField();
        addPlaceholder(txtHobby, "Añade un pasatiempo (ej. Gaming, Senderismo, Programar...)");
        panelFormulario.add(txtHobby, gbc);

        gbc.gridy++;
        gbc.ipady = 0;
        gbc.insets = new Insets(0, 5, 10, 5);
        panelHobbiesTags = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelHobbiesTags.setBackground(Color.WHITE);
        panelFormulario.add(panelHobbiesTags, gbc);

        // Intereses
        gbc.gridy++;
        gbc.insets = new Insets(15, 5, 5, 5);
        JLabel lblIntereses = new JLabel("Mis Intereses");
        lblIntereses.setFont(new Font("Arial", Font.BOLD, 16));
        panelFormulario.add(lblIntereses, gbc);

        gbc.gridy++;
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 5, 5, 5);
        txtInteres = new JTextField();
        addPlaceholder(txtInteres, "Añade un interés (ej. IA, Música Indie, Pelis Sci-Fi...)");
        panelFormulario.add(txtInteres, gbc);

        gbc.gridy++;
        gbc.ipady = 0;
        gbc.insets = new Insets(0, 5, 25, 5);
        panelInteresesTags = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelInteresesTags.setBackground(Color.WHITE);
        panelFormulario.add(panelInteresesTags, gbc);

        // Boton Finalizar
        gbc.gridy++;
        gbc.ipady = 12;
        gbc.insets = new Insets(20, 5, 10, 5);
        btnFinalizar = new JButton("Finalizar Registro");
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFinalizar.setBackground(COLOR_BOTON);
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFocusPainted(false);
        btnFinalizar.setOpaque(true);
        btnFinalizar.setBorderPainted(false);
        panelFormulario.add(btnFinalizar, gbc);

        // Relleno
        gbc.gridy++;
        gbc.weighty = 1.0;
        JPanel spacer = new JPanel();
        spacer.setBackground(Color.WHITE);
        panelFormulario.add(spacer, gbc);

        // --- 5. Añadir Formulario al Panel Principal ---
        panelPrincipal.add(panelFormulario, new GridBagConstraints());

        // --- 6. Listeners ---
        btnSubirFoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subirFoto();
            }
        });

        txtHobby.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarHobby();
            }
        });

        txtInteres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarInteres();
            }
        });

        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPerfil();
            }
        });
    }

    private JPanel crearPanelSubirFoto() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        Border dashedBorder = BorderFactory.createDashedBorder(Color.GRAY, 1, 5, 2, false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                dashedBorder,
                new EmptyBorder(10, 10, 10, 10)
        ));

        lblFotoPreview = new JLabel();
        lblFotoPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblFotoPreview.setPreferredSize(new Dimension(150, 150));
        
        // Estado inicial (placeholder)
        lblFotoPreview.setText("<html><center>Foto de Perfil<br><font size='-2' color='gray'>Sube una foto o arrastra una imagen</font></center></html>");
        lblFotoPreview.setFont(new Font("Arial", Font.BOLD, 16));
        lblFotoPreview.setForeground(Color.BLACK);
        // Aquí podrías añadir un icono por defecto
        
        panel.add(lblFotoPreview, new GridBagConstraints());
        return panel;
    }

    private void subirFoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una Foto de Perfil");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "png"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            
            // 1. Guardar la ruta (String) para la BD
            this.rutaFotoPerfil = archivoSeleccionado.getAbsolutePath();

            // 2. Crear la vista previa
            try {
                ImageIcon iconoOriginal = new ImageIcon(this.rutaFotoPerfil);
                Image imagen = iconoOriginal.getImage();
                
                // Redimensionar para la vista previa
                Image imagenRedimensionada = imagen.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                
                lblFotoPreview.setIcon(new ImageIcon(imagenRedimensionada));
                lblFotoPreview.setText(null); // Quitar el texto
                btnSubirFoto.setText("Cambiar Foto");
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar la vista previa de la imagen.", "Error de Imagen", JOptionPane.ERROR_MESSAGE);
                this.rutaFotoPerfil = null;
            }
        }
    }

    private void agregarHobby() {
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
        String nombreInteres = txtInteres.getText().trim();
        if (nombreInteres.isEmpty() || nombreInteres.startsWith("Añade un interés")) {
            return;
        }

        try {
            Interes interes = interesService.buscarPorNombre(nombreInteres);

            if (interes == null) {
                interes = new Interes();
                interes.setNombreInteres(nombreInteres);
                interes.setCategoria("General"); // Categoria por defecto
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

    private void guardarPerfil() {
        // Validaciones
        if (this.rutaFotoPerfil == null || this.rutaFotoPerfil.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, sube una foto de perfil.", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (this.hobbiesSet.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Añade al menos un pasatiempo.", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (this.interesesSet.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Añade al menos un interés.", "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // 1. Asignar los datos al objeto Estudiante
            this.estudiante.setFotoPerfil(this.rutaFotoPerfil);
            this.estudiante.setHobbies(this.hobbiesSet);
            this.estudiante.setIntereses(this.interesesSet);

            // 2. Llamar al servicio para ACTUALIZAR en la BD
            estudianteService.actualizarEstudiante(this.estudiante);
            
            // 3. Éxito y navegación
            JOptionPane.showMessageDialog(this, 
                    "¡Perfil completado! Bienvenido, " + this.estudiante.getNombreEstudiante() + ".",
                    "Registro Completo", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            // --- PROXIMO PASO: Abrir la pantalla principal ---
            // FrmPrincipal frmPrincipal = new FrmPrincipal(this.estudiante);
            // frmPrincipal.setVisible(true);
            
            this.dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar tu perfil: " + e.getMessage(), "Error de Servicio", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearTagVisual(final Object entidad, final JPanel panelDestino) {
        String nombre = (entidad instanceof Hobby)
                ? ((Hobby) entidad).getNombreHobby()
                : ((Interes) entidad).getNombreInteres();

        final JPanel tagPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        tagPanel.setBackground(COLOR_TAG);
        tagPanel.setBorder(new EmptyBorder(3, 5, 3, 5));

        JLabel tagLabel = new JLabel(nombre);
        tagLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        tagPanel.add(tagLabel);

        JButton removeButton = new JButton("x");
        removeButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeButton.setMargin(new Insets(0, 2, 0, 2));
        removeButton.setBorder(null);
        removeButton.setContentAreaFilled(false);
        removeButton.setForeground(Color.GRAY);
        removeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // NOTA: Este Frame REQUIERE un Estudiante para funcionar.
                // Este main es solo para pruebas visuales.
                Estudiante estudiantePrueba = new Estudiante();
                estudiantePrueba.setNombreEstudiante("UsuarioPrueba");
                
                new FrmCompletarPerfil(estudiantePrueba).setVisible(true);
            }
        });
    }
}
package Vistas;

// --- CAMBIO: Imports para TODOS los 9 servicios ---
import Domain.Carrera;
import Domain.Estudiante;
import Domain.Hobby;
import Domain.Interes;
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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL; 
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate; 

public class FrmRegistro extends JFrame {

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
    
    private List<Carrera> listaCompletaDeCarreras;

    // --- Componentes UI ---
    private JTextField txtNombre, txtApellidoPaterno, txtApellidoMaterno, txtEmail;
    private JComboBox<String> cmbNivel;
    private JComboBox<Carrera> cmbCarrera;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnSiguiente;
    private JLabel lblLogin, lblLogo, lblImagenGrande;

    // --- Colores ---
    private static final Color COLOR_FONDO_GRIS = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_IZQUIERDO = new Color(230, 240, 250);
    private static final Color COLOR_PANEL_DERECHO = Color.WHITE;
    private static final Color COLOR_BOTON = new Color(0, 86, 179);
    private static final Color COLOR_TEXTO_GRIS = new Color(100, 100, 100);
    private static final Color COLOR_PLACEHOLDER = new Color(180, 180, 180);

    // --- CAMBIO: Constructor ahora recibe los 9 servicios ---
    public FrmRegistro(IEstudianteService estudianteService, 
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
        
        this.listaCompletaDeCarreras = new ArrayList<>();
        
        initComponentes();
        cargarLogo();
        cargarCarrerasDesdeBD();
    }

    private void initComponentes() {
        // (Este método de UI no cambia)
        setTitle("Registro (Paso 1: Informacion Basica)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO_GRIS);
        setLayout(new BorderLayout());
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setOpaque(false);
        add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.add(crearPanelIzquierdo());
        panelPrincipal.add(crearPanelDerecho());
        btnSiguiente.addActionListener(e -> registrarEstudiante());
        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                irALogin();
            }
        });
        cmbNivel.addActionListener(e -> actualizarCarrerasFiltradas());
    }

    private JPanel crearPanelIzquierdo() {
        // (Este método de UI no cambia)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_PANEL_IZQUIERDO);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.NORTHWEST; gbc.weightx = 1;
        lblLogo = new JLabel();
        lblLogo.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblLogo, gbc);
        gbc.gridy++; gbc.anchor = GridBagConstraints.CENTER; gbc.weighty = 1; gbc.insets = new Insets(30, 0, 30, 0);
        lblImagenGrande = new JLabel("[ Imagen Grande Aqui ]");
        lblImagenGrande.setFont(new Font("Arial", Font.PLAIN, 20));
        lblImagenGrande.setPreferredSize(new Dimension(300, 300));
        lblImagenGrande.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenGrande.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(lblImagenGrande, gbc);
        gbc.gridy++; gbc.weighty = 0; gbc.anchor = GridBagConstraints.SOUTHWEST;
        JLabel lblTitulo = new JLabel("Conecta con tus compañeros Potros.");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitulo, gbc);
        gbc.gridy++; gbc.insets = new Insets(10, 0, 0, 0);
        JLabel lblSubtitulo = new JLabel("Encuentra compañeros con tus mismas pasiones.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(COLOR_TEXTO_GRIS);
        panel.add(lblSubtitulo, gbc);
        return panel;
    }

    private JPanel crearPanelDerecho() {
        // (Este método de UI no cambia)
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_PANEL_DERECHO);
        panel.setBorder(new EmptyBorder(40, 60, 40, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        panel.add(lblTitulo, gbc);
        gbc.gridy++; gbc.insets = new Insets(5, 0, 20, 0);
        JLabel lblSubtitulo = new JLabel("Únete a la comunidad estudiantil de ITSON.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(COLOR_TEXTO_GRIS);
        panel.add(lblSubtitulo, gbc);
        gbc.gridy++; gbc.gridwidth = 1; gbc.insets = new Insets(5, 0, 0, 5);
        panel.add(new JLabel("Nombre(s)"), gbc);
        gbc.gridx = 1; gbc.insets = new Insets(5, 5, 0, 0);
        panel.add(new JLabel("Apellido Paterno"), gbc);
        gbc.gridy++; gbc.gridx = 0; gbc.ipady = 10; gbc.insets = new Insets(0, 0, 10, 5);
        txtNombre = new JTextField(); addPlaceholder(txtNombre, "Ingresa tu nombre");
        panel.add(txtNombre, gbc);
        gbc.gridx = 1; gbc.insets = new Insets(0, 5, 10, 0);
        txtApellidoPaterno = new JTextField(); addPlaceholder(txtApellidoPaterno, "Ingresa tu apellido");
        panel.add(txtApellidoPaterno, gbc);
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2; gbc.ipady = 0; gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Apellido Materno"), gbc);
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(0, 0, 10, 0);
        txtApellidoMaterno = new JTextField(); addPlaceholder(txtApellidoMaterno, "Ingresa tu apellido materno");
        panel.add(txtApellidoMaterno, gbc);
        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Correo Institucional"), gbc);
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(0, 0, 10, 0);
        txtEmail = new JTextField(); addPlaceholder(txtEmail, "tu.id@potros.itson.edu.mx");
        panel.add(txtEmail, gbc);
        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Nivel"), gbc);
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(0, 0, 10, 0);
        String[] niveles = {"Selecciona tu nivel", "Ingeniería", "Licenciatura", "Medicina"};
        cmbNivel = new JComboBox<>(niveles); cmbNivel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(cmbNivel, gbc);
        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Carrera"), gbc);
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(0, 0, 10, 0);
        cmbCarrera = new JComboBox<>(); cmbCarrera.setFont(new Font("Arial", Font.PLAIN, 14)); cmbCarrera.setEnabled(false);
        panel.add(cmbCarrera, gbc);
        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Contraseña"), gbc);
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(0, 0, 10, 0);
        txtPassword = new JPasswordField(); addPlaceholder(txtPassword, "Crea una contraseña segura");
        panel.add(txtPassword, gbc);
        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Confirmar Contraseña"), gbc);
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(0, 0, 20, 0);
        txtConfirmPassword = new JPasswordField(); addPlaceholder(txtConfirmPassword, "Confirma tu contraseña");
        panel.add(txtConfirmPassword, gbc);
        gbc.gridy++; gbc.ipady = 12; gbc.insets = new Insets(10, 0, 10, 0);
        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setFont(new Font("Arial", Font.BOLD, 14)); btnSiguiente.setBackground(COLOR_BOTON);
        btnSiguiente.setForeground(Color.WHITE); btnSiguiente.setFocusPainted(false);
        btnSiguiente.setOpaque(true); btnSiguiente.setBorderPainted(false);
        panel.add(btnSiguiente, gbc);
        gbc.gridy++; gbc.ipady = 0; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER; gbc.insets = new Insets(10, 0, 0, 0);
        lblLogin = new JLabel("<html>¿Ya tienes cuenta? <font color='#0056B3'>Inicia sesión</font></html>");
        lblLogin.setFont(new Font("Arial", Font.PLAIN, 12)); lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(lblLogin, gbc);
        gbc.gridy++; gbc.weighty = 1.0; panel.add(new JPanel(), gbc);
        return panel;
    }

    private void cargarLogo() {
        // (Sin cambios)
        try {
            URL logoURL = getClass().getResource("/Recursos/Logo.png"); 
            if (logoURL != null) {
                ImageIcon originalIcon = new ImageIcon(logoURL);
                Image imagenOriginal = originalIcon.getImage();
                Image imagenRedimensionada = imagenOriginal.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(imagenRedimensionada));
            } else {
                throw new Exception("No se encontró el recurso: /Recursos/Logo.png");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el logo: " + e.getMessage());
            lblLogo.setText("Logo");
        }
    }

    private void cargarCarrerasDesdeBD() {
        // (Sin cambios)
        try {
            System.out.println("Cargando carreras desde BD...");
            this.listaCompletaDeCarreras = carreraService.listarCarreras(100);
            System.out.println("Carreras cargadas: " + (listaCompletaDeCarreras != null ? listaCompletaDeCarreras.size() : 0));
            if (listaCompletaDeCarreras == null || listaCompletaDeCarreras.isEmpty()) {
                System.err.println("⚠️ WARNING: No se encontraron carreras en la BD");
                JOptionPane.showMessageDialog(this, "No se encontraron carreras en la base de datos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ ERROR al cargar carreras: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al cargar las carreras: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarCarrerasFiltradas() {
        // (Sin cambios)
        String seleccion = (String) cmbNivel.getSelectedItem();
        cmbCarrera.removeAllItems();
        if (seleccion.equals("Selecciona tu nivel")) {
            cmbCarrera.setEnabled(false);
            return;
        }
        String prefijo;
        if (seleccion.equals("Ingeniería")) {
            prefijo = "Ing";
        } else if (seleccion.equals("Licenciatura")) {
            prefijo = "Lic";
        } else if (seleccion.equals("Medicina")) {
             prefijo = "Med";
        } else {
            prefijo = "---";
        }
        for (Carrera carrera : listaCompletaDeCarreras) {
            if (carrera.getNombreCarrera().startsWith(prefijo)) {
                cmbCarrera.addItem(carrera);
            }
        }
        cmbCarrera.setEnabled(true);
    }
    
    private void registrarEstudiante() {
        String nombre = txtNombre.getText();
        String apellidoP = txtApellidoPaterno.getText();
        String apellidoM = txtApellidoMaterno.getText();
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());
        String confirmPass = new String(txtConfirmPassword.getPassword());
        String nivel = (String) cmbNivel.getSelectedItem();
        Carrera carrera = (Carrera) cmbCarrera.getSelectedItem();

        // (Validaciones de campos vacíos, etc. - Sin cambios)
        if (nombre.isEmpty() || nombre.equals("Ingresa tu nombre") ||
            apellidoP.isEmpty() || apellidoP.equals("Ingresa tu apellido") ||
            apellidoM.isEmpty() || apellidoM.equals("Ingresa tu apellido materno") ||
            email.isEmpty() || email.equals("tu.id@potros.itson.edu.mx") ||
            password.isEmpty() || password.equals("Crea una contraseña segura")) {
            JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (nivel.equals("Selecciona tu nivel") || carrera == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona tu nivel y carrera.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!password.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error de Contraseña", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Estudiante nuevoEstudiante = new Estudiante();
            nuevoEstudiante.setNombreEstudiante(nombre);
            nuevoEstudiante.setApellidoPaterno(apellidoP);
            nuevoEstudiante.setApellidoMaterno(apellidoM);
            nuevoEstudiante.setCorreoInstitucional(email);
            nuevoEstudiante.setContrasena(password); 
            nuevoEstudiante.setCarrera(carrera);

            Estudiante estudianteCreado = estudianteService.crearEstudiante(nuevoEstudiante); 

            JOptionPane.showMessageDialog(this, "¡Registro exitoso, " + estudianteCreado.getNombreEstudiante() + "!\nAhora, completa tu perfil.", "¡Bienvenido!", JOptionPane.INFORMATION_MESSAGE);
            
            // --- CAMBIO: Abrir FrmCompletarPerfil pasando los 9 servicios ---
            FrmCompletarPerfil frmPerfil = new FrmCompletarPerfil(
                estudianteCreado,
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
            frmPerfil.setVisible(true);
            this.dispose();

        } catch (Exception e) { 
            JOptionPane.showMessageDialog(this, "Error en el registro: " + e.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // --- CAMBIO: 'irALogin' pasa los 9 servicios de vuelta ---
    private void irALogin() {
        FrmLogin frmLogin = new FrmLogin(
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
        frmLogin.setVisible(true);
        this.dispose();
    }
    
    private void addPlaceholder(final JTextField field, final String placeholder) {
        // (Sin cambios)
        field.setText(placeholder);
        field.setForeground(COLOR_PLACEHOLDER);
        if (field instanceof JPasswordField) {
            ((JPasswordField) field).setEchoChar((char) 0);
        }
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar('•');
                    }
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                String text = (field instanceof JPasswordField)
                        ? new String(((JPasswordField) field).getPassword())
                        : field.getText();
                if (text.isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(COLOR_PLACEHOLDER);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar((char) 0);
                    }
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
                IEstudianteService estService = new EstudianteService();
                ICarreraService carService = new CarreraService();
                IHobbyService hobService = new HobbyService();
                IInteresService intService = new InteresService();
                ILikeService likeService = new LikeService(); 
                IMatchService matchService = new MatchService(); // AÑADIDO
                IChatService chatService = new ChatService(); // AÑADIDO
                IMensajeService mensajeService = new MensajeService(); // AÑADIDO
                IPreferenciaService prefService = new PreferenciaService(); // AÑADIDO
                
                new FrmRegistro(
                    estService, carService, hobService, intService, 
                    likeService, matchService, chatService, mensajeService,
                    prefService // AÑADIDO
                ).setVisible(true);
            }
        });
    }
}
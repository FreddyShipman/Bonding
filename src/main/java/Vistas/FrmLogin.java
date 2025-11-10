package Vistas;

// --- CAMBIO: Imports para TODOS los 9 servicios ---
import Domain.Estudiante;
import Excepciones.*;
import Service.CarreraService;
import Service.EstudianteService;
import Service.HobbyService;
import Service.InteresService;
import Service.LikeService;
import Service.MatchService;
import Service.ChatService;
import Service.MensajeService;
import Service.PreferenciaService; 
import InterfaceService.ICarreraService;
import InterfaceService.IEstudianteService;
import InterfaceService.IHobbyService;
import InterfaceService.IInteresService;
import InterfaceService.ILikeService;
import InterfaceService.IMatchService;
import InterfaceService.IChatService;
import InterfaceService.IMensajeService;
import InterfaceService.IPreferenciaService; 

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

public class FrmLogin extends JFrame {

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

    // --- Componentes UI ---
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JLabel lblRegistro;
    private JLabel lblLogo;

    // --- Colores ---
    private static final Color COLOR_FONDO = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_BOTON = new Color(0, 86, 179);
    private static final Color COLOR_TEXTO_GRIS = new Color(100, 100, 100);
    private static final Color COLOR_PLACEHOLDER = new Color(180, 180, 180);

    // --- CAMBIO: Constructor ahora recibe los 9 servicios ---
    public FrmLogin(IEstudianteService estudianteService, 
                    ICarreraService carreraService, 
                    IHobbyService hobbyService, 
                    IInteresService interesService,
                    ILikeService likeService,
                    IMatchService matchService,
                    IChatService chatService,
                    IMensajeService mensajeService,
                    IPreferenciaService preferenciaService) { // AÑADIDO
        
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
        cargarLogo();
    }

    private void initComponentes() {
        // (El código de initComponentes no cambia... es solo UI)
        setTitle("Inicio de Sesion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);
        JPanel panelFondo = new JPanel(new GridBagLayout());
        panelFondo.setBackground(COLOR_FONDO);
        add(panelFondo, BorderLayout.CENTER);
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(COLOR_PANEL_BLANCO);
        panelFormulario.setBorder(new EmptyBorder(40, 50, 40, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0); 
        lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 10, 0);
        panelFormulario.add(lblLogo, gbc);
        gbc.gridy++;
        JLabel lblTitulo = new JLabel("¡Bienvenido de vuelta!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(10, 0, 5, 0);
        panelFormulario.add(lblTitulo, gbc);
        gbc.gridy++;
        JLabel lblSubtitulo = new JLabel("Inicia sesion para conectar con tus compañeros.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(COLOR_TEXTO_GRIS);
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 25, 0);
        panelFormulario.add(lblSubtitulo, gbc);
        gbc.gridy++;
        JLabel lblEmail = new JLabel("Correo Institucional");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 0, 0);
        panelFormulario.add(lblEmail, gbc);
        gbc.gridy++;
        txtEmail = new JTextField(25);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 0, 5, 0);
        addPlaceholder(txtEmail, "tu.id@potros.itson.edu.mx");
        panelFormulario.add(txtEmail, gbc);
        gbc.gridy++;
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.ipady = 0;
        gbc.insets = new Insets(10, 0, 0, 0);
        panelFormulario.add(lblPassword, gbc);
        gbc.gridy++;
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 0, 5, 0);
        addPlaceholder(txtPassword, "Ingresa tu contraseña");
        panelFormulario.add(txtPassword, gbc);
        gbc.gridy++;
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setBackground(COLOR_BOTON);
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setOpaque(true);
        btnIngresar.setBorderPainted(false);
        gbc.ipady = 12;
        gbc.insets = new Insets(20, 0, 10, 0);
        panelFormulario.add(btnIngresar, gbc);
        gbc.gridy++;
        lblRegistro = new JLabel("<html>¿No tienes cuenta? <font color='#0056B3'>Regístrate aquí</font></html>");
        lblRegistro.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.ipady = 0;
        gbc.insets = new Insets(15, 0, 10, 0);
        panelFormulario.add(lblRegistro, gbc);
        JLabel lblFooter = new JLabel("Ayuda - Términos de Servicio");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(COLOR_TEXTO_GRIS);
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        lblFooter.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(lblFooter, BorderLayout.SOUTH);
        panelFondo.add(panelFormulario, new GridBagConstraints());
        btnIngresar.addActionListener(e -> iniciarSesion());
        lblRegistro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirRegistro();
            }
        });
    }

    private void cargarLogo() {
        // (Sin cambios)
        try {
            URL logoURL = getClass().getResource("/Recursos/Logo.png");
            if (logoURL != null) {
                ImageIcon originalIcon = new ImageIcon(logoURL);
                Image imagenOriginal = originalIcon.getImage();
                Image imagenRedimensionada = imagenOriginal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(imagenRedimensionada));
            } else {
                throw new Exception("No se encontró el recurso: /Recursos/Logo.png");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el logo: " + e.getMessage());
            lblLogo.setText("[Logo no encontrado]");
            lblLogo.setForeground(Color.RED);
        }
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

    private void iniciarSesion() {
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());

        if (email.equals("tu.id@potros.itson.edu.mx") || email.isEmpty() ||
            password.equals("Ingresa tu contraseña") || password.isEmpty()) {
            mostrarError("Por favor, ingresa tu correo y contraseña.", "Campos Vacíos");
            return;
        }

        try {
            Estudiante estudianteLogueado = estudianteService.autenticar(email, password);
            JOptionPane.showMessageDialog(this,
                    "¡Bienvenido, " + estudianteLogueado.getNombreEstudiante() + "!",
                    "Inicio de Sesión Exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            // --- CAMBIO: Navega a FrmExplorar con los 9 servicios ---
            FrmExplorar frmExplorar = new FrmExplorar(
                estudianteLogueado,
                this.estudianteService,
                this.carreraService,
                this.hobbyService,
                this.interesService,
                this.likeService,
                this.matchService, 
                this.chatService, 
                this.mensajeService,
                this.preferenciaService // AÑADIDO
            );
            frmExplorar.setVisible(true);
            this.dispose();

        } catch (AutenticacionException e) {
            mostrarError("Correo institucional o contraseña incorrectos.", "Error de Autenticación");
        } catch (DatabaseException e) {
             mostrarError("No se pudo conectar con el servidor.", "Error de Conexión");
        } catch (BondingException e) {
             mostrarError("Ocurrió un error inesperado: " + e.getMessage(), "Error del Sistema");
        } catch (Exception e) {
            mostrarError("Error desconocido: " + e.getMessage(), "Error");
        }
    }

    // --- CAMBIO: 'abrirRegistro' pasa los 9 servicios ---
    private void abrirRegistro() {
        FrmRegistro frmRegistro = new FrmRegistro(
            this.estudianteService, 
            this.carreraService,
            this.hobbyService,
            this.interesService,
            this.likeService,
            this.matchService,
            this.chatService,
            this.mensajeService,
            this.preferenciaService // AÑADIDO
        ); 
        frmRegistro.setVisible(true);
        this.dispose(); 
    }
    
    private void mostrarError(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.WARNING_MESSAGE);
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
                // El 'main' ahora es responsable de crear todas las dependencias
                IEstudianteService estService = new EstudianteService(); 
                ICarreraService carService = new CarreraService();
                IHobbyService hobService = new HobbyService();
                IInteresService intService = new InteresService();
                ILikeService likeService = new LikeService();
                IMatchService matchService = new MatchService();
                IChatService chatService = new ChatService();
                IMensajeService mensajeService = new MensajeService();
                IPreferenciaService prefService = new PreferenciaService(); // AÑADIDO
                
                new FrmLogin(
                    estService, carService, hobService, intService, 
                    likeService, matchService, chatService, mensajeService,
                    prefService // AÑADIDO
                ).setVisible(true);
            }
        });
    }
}
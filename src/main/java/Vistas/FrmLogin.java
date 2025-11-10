package Vistas;

// --- CAMBIO: Imports para TODOS los servicios ---
import Domain.Estudiante;
import Excepciones.*;
import Service.CarreraService;
import Service.EstudianteService;
import Service.HobbyService;
import Service.InteresService;
import InterfaceService.ICarreraService;
import InterfaceService.IEstudianteService;
import InterfaceService.IHobbyService;
import InterfaceService.IInteresService;

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
import java.net.URL; // Import para getResource

public class FrmLogin extends JFrame {

    // --- CAMBIO: Campos para TODOS los servicios ---
    private IEstudianteService estudianteService;
    private ICarreraService carreraService;
    private IHobbyService hobbyService;
    private IInteresService interesService;

    // --- Componentes UI ---
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JLabel lblRegistro;
    private JLabel lblLogo;
    private JCheckBox chkMostrarPassword;
    private JLabel lblErrorEmail;
    private JLabel lblErrorPassword;

    // --- Colores del diseno ---
    private static final Color COLOR_FONDO = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_BOTON = new Color(0, 86, 179);
    private static final Color COLOR_TEXTO_GRIS = new Color(100, 100, 100);
    private static final Color COLOR_PLACEHOLDER = new Color(180, 180, 180);

    // --- CAMBIO: Constructor ahora recibe TODOS los servicios ---
    public FrmLogin(IEstudianteService estudianteService, 
                    ICarreraService carreraService, 
                    IHobbyService hobbyService, 
                    IInteresService interesService) {
        
        // Asigna todos los servicios
        this.estudianteService = estudianteService;
        this.carreraService = carreraService;
        this.hobbyService = hobbyService;
        this.interesService = interesService;
        
        initComponentes();
        cargarLogo();
    }

    private void initComponentes() {
        // ... (Todo tu código de initComponentes va aquí, no cambia) ...
        // --- 1. Configuracion de la Ventana ---
        setTitle("Inicio de Sesion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_FONDO);

        // --- 2. Panel de Fondo (Gris) ---
        JPanel panelFondo = new JPanel(new GridBagLayout());
        panelFondo.setBackground(COLOR_FONDO);
        add(panelFondo, BorderLayout.CENTER);

        // --- 3. Panel de Formulario (Blanco) ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(COLOR_PANEL_BLANCO);
        panelFormulario.setBorder(new EmptyBorder(40, 50, 40, 50));
        
        // --- Configuracion de GridBagLayout ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0); 

        // --- 4. Componentes del Formulario ---
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
        JLabel lblEmail = new JLabel("Correo Institucional *");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 0, 0);
        panelFormulario.add(lblEmail, gbc);

        gbc.gridy++;
        txtEmail = new JTextField(25);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 0, 0, 0);
        addPlaceholder(txtEmail, "tu.id@potros.itson.edu.mx");
        // Validación en tiempo real del correo
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarCorreoEnTiempoReal();
            }
        });
        panelFormulario.add(txtEmail, gbc);

        // Label de error para email
        gbc.gridy++;
        lblErrorEmail = new JLabel(" ");
        lblErrorEmail.setFont(new Font("Arial", Font.PLAIN, 11));
        lblErrorEmail.setForeground(Color.RED);
        gbc.ipady = 0;
        gbc.insets = new Insets(0, 0, 5, 0);
        panelFormulario.add(lblErrorEmail, gbc);

        gbc.gridy++;
        JLabel lblPassword = new JLabel("Contraseña *");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 0, 0, 0);
        panelFormulario.add(lblPassword, gbc);

        gbc.gridy++;
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 0, 0, 0);
        addPlaceholder(txtPassword, "Ingresa tu contraseña");
        panelFormulario.add(txtPassword, gbc);

        // Checkbox para mostrar contraseña
        gbc.gridy++;
        chkMostrarPassword = new JCheckBox("Mostrar contraseña");
        chkMostrarPassword.setFont(new Font("Arial", Font.PLAIN, 11));
        chkMostrarPassword.setBackground(COLOR_PANEL_BLANCO);
        chkMostrarPassword.setFocusPainted(false);
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 0, 5, 0);
        chkMostrarPassword.addActionListener(e -> toggleMostrarPassword());
        panelFormulario.add(chkMostrarPassword, gbc);

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

        // --- 5. Footer ---
        JLabel lblFooter = new JLabel("Ayuda - Términos de Servicio");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(COLOR_TEXTO_GRIS);
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        lblFooter.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(lblFooter, BorderLayout.SOUTH);

        // --- 6. Anadir panel blanco al panel gris (para centrarlo) ---
        panelFondo.add(panelFormulario, new GridBagConstraints());

        // --- 7. Listeners (Logica) ---
        btnIngresar.addActionListener(e -> iniciarSesion());
        lblRegistro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirRegistro();
            }
        });
    }

    // --- CAMBIO: 'cargarLogo' usa getResource ---
    private void cargarLogo() {
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
        // ... (Tu código de addPlaceholder va aquí, no cambia) ...
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

        // Validar placeholders y campos vacíos
        if (email.equals("tu.id@potros.itson.edu.mx") || email.isEmpty() ||
            password.equals("Ingresa tu contraseña") || password.isEmpty()) {
            mostrarError("Por favor, ingresa tu correo y contraseña.", "Campos Vacíos");
            return;
        }

        try {
            Estudiante estudianteLogueado = estudianteService.autenticar(email, password);

            // Si llega aquí, la autenticación fue exitosa
            JOptionPane.showMessageDialog(this,
                "¡Bienvenido, " + estudianteLogueado.getNombreEstudiante() + "!",
                "Inicio de Sesión Exitoso",
                JOptionPane.INFORMATION_MESSAGE);

            // FrmPrincipal frmPrincipal = new FrmPrincipal(estudianteLogueado, estudianteService, carreraService, hobbyService, interesService);
            // frmPrincipal.setVisible(true);
            this.dispose();

        } catch (AutenticacionException e) {
            // Error de credenciales inválidas
            mostrarError("Correo institucional o contraseña incorrectos.\nPor favor, verifica tus credenciales.",
                        "Error de Autenticación");
            txtPassword.setText("Ingresa tu contraseña");
            txtPassword.setForeground(COLOR_PLACEHOLDER);
            txtPassword.setEchoChar((char) 0);

        } catch (ValidacionException e) {
            // Error de validación de campos
            mostrarError(e.getMessage(), "Error de Validación");

        } catch (DatabaseException e) {
            // Error de base de datos
            mostrarError("No se pudo conectar con el servidor.\nPor favor, intenta más tarde.",
                        "Error de Conexión");
            System.err.println("Error de BD: " + e.getMensajeCompleto());

        } catch (BondingException e) {
            // Otras excepciones del sistema
            mostrarError("Ocurrió un error inesperado: " + e.getMessage(),
                        "Error del Sistema");
            System.err.println("Error: " + e.getMensajeCompleto());

        } catch (Exception e) {
            // Cualquier otra excepción no manejada
            mostrarError("Ocurrió un error inesperado.\nPor favor, contacta al administrador.",
                        "Error Desconocido");
            e.printStackTrace();
        }
    }

    /**
     * Método auxiliar para mostrar mensajes de error
     */
    private void mostrarError(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this,
            mensaje,
            titulo,
            JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Valida el formato del correo en tiempo real
     */
    private void validarCorreoEnTiempoReal() {
        String email = txtEmail.getText();

        // Si está vacío o es el placeholder, no mostrar error
        if (email.isEmpty() || email.equals("tu.id@potros.itson.edu.mx")) {
            lblErrorEmail.setText(" ");
            txtEmail.setBorder(new JTextField().getBorder());
            return;
        }

        // Validar formato básico de correo
        if (!email.contains("@")) {
            lblErrorEmail.setText("Formato de correo inválido");
            txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            return;
        }

        // Validar que sea correo institucional
        if (!email.toLowerCase().endsWith("@potros.itson.edu.mx")) {
            lblErrorEmail.setText("Debe ser correo institucional (@potros.itson.edu.mx)");
            txtEmail.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0), 1));
            return;
        }

        // Correo válido
        lblErrorEmail.setText("Correo válido");
        lblErrorEmail.setForeground(new Color(0, 128, 0));
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 1));
    }

    /**
     * Alterna la visibilidad de la contraseña
     */
    private void toggleMostrarPassword() {
        if (chkMostrarPassword.isSelected()) {
            txtPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar('•');
        }
    }

    // --- CAMBIO: 'abrirRegistro' pasa TODOS los servicios ---
    private void abrirRegistro() {
        // Pasa todos los servicios que FrmRegistro necesitará
        FrmRegistro frmRegistro = new FrmRegistro(
            this.estudianteService, 
            this.carreraService,
            this.hobbyService,
            this.interesService
        ); 
        frmRegistro.setVisible(true);
        this.dispose(); 
    }

    // --- CAMBIO: 'main' ahora crea e inyecta TODOS los servicios ---
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
                
                // Y se las "inyecta" al FrmLogin
                new FrmLogin(estService, carService, hobService, intService).setVisible(true);
            }
        });
    }
}
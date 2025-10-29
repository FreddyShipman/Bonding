package Vistas;

/**
 *
 * @author alfre
 */

import Domain.Estudiante;
import Service.EstudianteService;
import InterfaceService.IEstudianteService;

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

    // --- Servicios ---
    private IEstudianteService estudianteService;

    // --- Componentes UI ---
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JLabel lblRegistro;
    private JLabel lblLogo;

    // --- Colores del diseno ---
    private static final Color COLOR_FONDO = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_BOTON = new Color(0, 86, 179);
    private static final Color COLOR_TEXTO_GRIS = new Color(100, 100, 100);
    private static final Color COLOR_PLACEHOLDER = new Color(180, 180, 180);

    public FrmLogin() {
        this.estudianteService = new EstudianteService();
        initComponentes();
        cargarLogo();
    }

    private void initComponentes() {
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

        // Logo
        lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 10, 0);
        panelFormulario.add(lblLogo, gbc);

        // Titulo
        gbc.gridy++;
        JLabel lblTitulo = new JLabel("¡Bienvenido de vuelta!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(10, 0, 5, 0);
        panelFormulario.add(lblTitulo, gbc);

        // Subtitulo
        gbc.gridy++;
        JLabel lblSubtitulo = new JLabel("Inicia sesion para conectar con tus compañeros.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(COLOR_TEXTO_GRIS);
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 25, 0);
        panelFormulario.add(lblSubtitulo, gbc);

        // Label Correo
        gbc.gridy++;
        JLabel lblEmail = new JLabel("Correo Institucional");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 0, 0);
        panelFormulario.add(lblEmail, gbc);

        // Campo Correo
        gbc.gridy++;
        txtEmail = new JTextField(25);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.ipady = 10; // Alto del campo
        gbc.insets = new Insets(5, 0, 5, 0);
        addPlaceholder(txtEmail, "username@itson.edu.mx");
        panelFormulario.add(txtEmail, gbc);

        // Label Contrasena
        gbc.gridy++;
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.ipady = 0;
        gbc.insets = new Insets(10, 0, 0, 0);
        panelFormulario.add(lblPassword, gbc);

        // Campo Contrasena
        gbc.gridy++;
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 0, 5, 0);
        addPlaceholder(txtPassword, "Ingresa tu contraseña");
        panelFormulario.add(txtPassword, gbc);

        // Boton Ingresar
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

        // Link Registro
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
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        lblRegistro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirRegistro();
            }
        });
    }

    private void cargarLogo() {
        try {
            // --- ¡AQUI VA TU RUTA! ---
            String rutaLogo = "PEGA_AQUI_LA_RUTA_DE_TU_LOGO.png";
            // Por ejemplo: "C:\\Users\\tu\\proyecto\\src\\recursos\\logo.png"
            
            ImageIcon originalIcon = new ImageIcon(rutaLogo);
            
            // Redimensionar logo para que quepa (ej. 100x100)
            Image imagenOriginal = originalIcon.getImage();
            Image imagenRedimensionada = imagenOriginal.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            
            lblLogo.setIcon(new ImageIcon(imagenRedimensionada));
            
        } catch (Exception e) {
            System.err.println("Error al cargar el logo: " + e.getMessage());
            lblLogo.setText("[Logo no encontrado]");
            lblLogo.setForeground(Color.RED);
        }
    }

    private void addPlaceholder(final JTextField field, final String placeholder) {
        field.setText(placeholder);
        field.setForeground(COLOR_PLACEHOLDER);

        // Manejo especial para JPasswordField
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

        // Validacion de placeholders
        if (email.equals("username@itson.edu.mx") || email.isEmpty() ||
            password.equals("Ingresa tu contraseña") || password.isEmpty()) {
            
            JOptionPane.showMessageDialog(this,
                    "Por favor, ingresa tu correo y contraseña.",
                    "Campos Vacíos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Logica de autenticacion
        try {
            Estudiante estudianteLogueado = estudianteService.autenticar(email, password);

            if (estudianteLogueado != null) {
                JOptionPane.showMessageDialog(this,
                        "¡Bienvenido, " + estudianteLogueado.getNombreEstudiante() + "!",
                        "Inicio de Sesión Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);

                // --- PROXIMO PASO: Abrir la pantalla principal ---
                // FrmPrincipal frmPrincipal = new FrmPrincipal(estudianteLogueado);
                // frmPrincipal.setVisible(true);
                this.dispose();

            } else {
                // Esto no deberia pasar si el servicio lanza excepcion, pero es buena practica
                JOptionPane.showMessageDialog(this,
                        "Correo institucional o contraseña incorrectos.",
                        "Error de Inicio de Sesión",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            // Captura errores del servicio (ej. "Usuario no encontrado", "DB caida")
            JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Error de Inicio de Sesión",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        // --- PROXIMO PASO: Abrir la pantalla de registro ---
        FrmRegistro frmRegistro = new FrmRegistro();
        frmRegistro.setVisible(true);
        this.dispose(); 
    }

    // --- Main para probar esta pantalla individualmente ---
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }
}
package Vistas;

// --- CAMBIO: Imports para TODOS los servicios ---
import Domain.Carrera;
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
import java.util.ArrayList;
import java.util.List;

public class FrmRegistro extends JFrame {

    // --- CAMBIO: Campos para TODOS los servicios ---
    private IEstudianteService estudianteService;
    private ICarreraService carreraService;
    private IHobbyService hobbyService;
    private IInteresService interesService;
    
    private List<Carrera> listaCompletaDeCarreras;

    // ... (Componentes UI: txtNombre, cmbCarrera, etc. van aquí) ...
    private JTextField txtNombre;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtEmail;
    private JComboBox<String> cmbNivel;
    private JComboBox<Carrera> cmbCarrera;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JButton btnSiguiente;
    private JLabel lblLogin;
    private JLabel lblLogo;
    private JLabel lblImagenGrande;
    private JLabel lblFuerzaPassword;
    private JLabel lblErrorEmail;
    private JCheckBox chkMostrarPassword;
    
    // ... (Colores) ...
    private static final Color COLOR_FONDO_GRIS = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_IZQUIERDO = new Color(230, 240, 250);
    private static final Color COLOR_PANEL_DERECHO = Color.WHITE;
    private static final Color COLOR_BOTON = new Color(0, 86, 179);
    private static final Color COLOR_TEXTO_GRIS = new Color(100, 100, 100);
    private static final Color COLOR_PLACEHOLDER = new Color(180, 180, 180);

    // --- CAMBIO: Constructor ahora recibe TODOS los servicios ---
    public FrmRegistro(IEstudianteService estudianteService, 
                       ICarreraService carreraService, 
                       IHobbyService hobbyService, 
                       IInteresService interesService) {
        
        // Asigna todos los servicios
        this.estudianteService = estudianteService;
        this.carreraService = carreraService;
        this.hobbyService = hobbyService;
        this.interesService = interesService;
        
        this.listaCompletaDeCarreras = new ArrayList<>();
        
        initComponentes();
        cargarLogo();
        cargarCarrerasDesdeBD();
    }

    // ... (initComponentes, crearPanelIzquierdo, crearPanelDerecho van aquí, no cambian) ...
    private void initComponentes() {
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
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(0, 0, 0, 0);
        txtEmail = new JTextField(); addPlaceholder(txtEmail, "tu.id@potros.itson.edu.mx");
        // Validación en tiempo real del correo
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarCorreoEnTiempoReal();
            }
        });
        panel.add(txtEmail, gbc);
        // Label de error para email
        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(2, 0, 8, 0);
        lblErrorEmail = new JLabel(" ");
        lblErrorEmail.setFont(new Font("Arial", Font.PLAIN, 11));
        panel.add(lblErrorEmail, gbc);
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
        panel.add(new JLabel("Contraseña *"), gbc);
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(0, 0, 0, 0);
        txtPassword = new JPasswordField(); addPlaceholder(txtPassword, "Crea una contraseña segura");
        // Validar fuerza de contraseña en tiempo real
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validarFuerzaPassword();
            }
        });
        panel.add(txtPassword, gbc);
        // Label de fuerza de contraseña
        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(2, 0, 8, 0);
        lblFuerzaPassword = new JLabel(" ");
        lblFuerzaPassword.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(lblFuerzaPassword, gbc);

        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Confirmar Contraseña *"), gbc);
        gbc.gridy++; gbc.ipady = 10; gbc.insets = new Insets(0, 0, 0, 0);
        txtConfirmPassword = new JPasswordField(); addPlaceholder(txtConfirmPassword, "Confirma tu contraseña");
        panel.add(txtConfirmPassword, gbc);

        // Checkbox para mostrar contraseña
        gbc.gridy++; gbc.ipady = 0; gbc.insets = new Insets(8, 0, 12, 0);
        chkMostrarPassword = new JCheckBox("Mostrar contraseñas");
        chkMostrarPassword.setFont(new Font("Arial", Font.PLAIN, 11));
        chkMostrarPassword.setBackground(COLOR_PANEL_DERECHO);
        chkMostrarPassword.setFocusPainted(false);
        chkMostrarPassword.addActionListener(e -> toggleMostrarPassword());
        panel.add(chkMostrarPassword, gbc);
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
        gbc.gridy++; gbc.weighty = 1.0; panel.add(new JPanel(), gbc); // Spacer
        return panel;
    }

    // --- CAMBIO: 'cargarLogo' usa getResource ---
    private void cargarLogo() {
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

    // Los métodos cargarCarrerasDesdeBD y actualizarCarrerasFiltradas no cambian
    private void cargarCarrerasDesdeBD() {
        try {
            this.listaCompletaDeCarreras = carreraService.listarCarreras(100);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las carreras: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarCarrerasFiltradas() {
        String seleccion = (String) cmbNivel.getSelectedItem();
        cmbCarrera.removeAllItems();
        if (seleccion.equals("Selecciona tu nivel")) {
            cmbCarrera.setEnabled(false);
            return;
        }

        // Filtrar carreras según el nivel seleccionado
        for (Carrera carrera : listaCompletaDeCarreras) {
            String nombreCarrera = carrera.getNombreCarrera();

            if (seleccion.equals("Ingeniería") && nombreCarrera.startsWith("Ingeniería")) {
                cmbCarrera.addItem(carrera);
            } else if (seleccion.equals("Licenciatura") && nombreCarrera.startsWith("Licenciatura")) {
                cmbCarrera.addItem(carrera);
            } else if (seleccion.equals("Medicina") && nombreCarrera.startsWith("Medicina")) {
                cmbCarrera.addItem(carrera);
            }
        }

        // Si no hay carreras para ese nivel, mostrar mensaje
        if (cmbCarrera.getItemCount() == 0) {
            cmbCarrera.addItem(null);
            cmbCarrera.setEnabled(false);
            JOptionPane.showMessageDialog(this,
                "No hay carreras disponibles para el nivel seleccionado.",
                "Sin Carreras",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            cmbCarrera.setEnabled(true);
        }
    }
    
    private void registrarEstudiante() {
        // ... (Tu código de validaciones va aquí, no cambia) ...
        String nombre = txtNombre.getText();
        String apellidoP = txtApellidoPaterno.getText();
        String apellidoM = txtApellidoMaterno.getText();
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());
        String confirmPass = new String(txtConfirmPassword.getPassword());
        String nivel = (String) cmbNivel.getSelectedItem();
        Carrera carrera = (Carrera) cmbCarrera.getSelectedItem();

        if (nombre.isEmpty() || nombre.equals("Ingresa tu nombre") || /* ...más validaciones... */
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

            JOptionPane.showMessageDialog(this,
                "¡Registro exitoso, " + estudianteCreado.getNombreEstudiante() + "!\nAhora, completa tu perfil.",
                "¡Bienvenido!",
                JOptionPane.INFORMATION_MESSAGE);

            // --- CAMBIO: Abrir FrmCompletarPerfil pasando TODOS los servicios ---
            FrmCompletarPerfil frmPerfil = new FrmCompletarPerfil(
                estudianteCreado,
                this.estudianteService,
                this.carreraService,
                this.hobbyService,
                this.interesService
            );
            frmPerfil.setVisible(true);
            this.dispose();

        } catch (DuplicadoException e) {
            // Correo ya registrado
            mostrarError("El correo institucional '" + email + "' ya está registrado.\n¿Ya tienes cuenta? Inicia sesión.",
                        "Correo Duplicado");
            txtEmail.requestFocus();
            txtEmail.selectAll();

        } catch (ValidacionException e) {
            // Error de validación
            String mensaje = e.getMessage();
            if (e.getCampo() != null && e.getCampo().equals("correoInstitucional")) {
                mensaje += "\nAsegúrate de usar tu correo institucional @potros.itson.edu.mx";
            }
            mostrarError(mensaje, "Error de Validación");

        } catch (DatabaseException e) {
            // Error de base de datos
            mostrarError("No se pudo completar el registro.\nPor favor, verifica tu conexión e intenta nuevamente.",
                        "Error de Conexión");
            System.err.println("Error de BD: " + e.getMensajeCompleto());

        } catch (BondingException e) {
            // Otras excepciones del sistema
            mostrarError("Ocurrió un error durante el registro: " + e.getMessage(),
                        "Error de Registro");
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

        if (email.isEmpty() || email.equals("tu.id@potros.itson.edu.mx")) {
            lblErrorEmail.setText(" ");
            txtEmail.setBorder(new JTextField().getBorder());
            return;
        }

        if (!email.contains("@")) {
            lblErrorEmail.setText("Formato inválido");
            lblErrorEmail.setForeground(Color.RED);
            txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            return;
        }

        if (!email.toLowerCase().endsWith("@potros.itson.edu.mx")) {
            lblErrorEmail.setText("Debe terminar en @potros.itson.edu.mx");
            lblErrorEmail.setForeground(new Color(255, 140, 0));
            txtEmail.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0), 1));
            return;
        }

        lblErrorEmail.setText("Correo válido");
        lblErrorEmail.setForeground(new Color(0, 128, 0));
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 1));
    }

    /**
     * Valida la fuerza de la contraseña
     */
    private void validarFuerzaPassword() {
        String pass = new String(txtPassword.getPassword());

        if (pass.isEmpty() || pass.equals("Crea una contraseña segura")) {
            lblFuerzaPassword.setText(" ");
            txtPassword.setBorder(new JTextField().getBorder());
            return;
        }

        int fuerza = calcularFuerzaPassword(pass);

        if (fuerza < 2) {
            lblFuerzaPassword.setText("Débil (mínimo 6 caracteres)");
            lblFuerzaPassword.setForeground(Color.RED);
            txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        } else if (fuerza < 3) {
            lblFuerzaPassword.setText("Media (agrega números o símbolos)");
            lblFuerzaPassword.setForeground(new Color(255, 140, 0));
            txtPassword.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0), 1));
        } else {
            lblFuerzaPassword.setText("Fuerte");
            lblFuerzaPassword.setForeground(new Color(0, 128, 0));
            txtPassword.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 1));
        }
    }

    /**
     * Calcula la fuerza de una contraseña (0-4)
     */
    private int calcularFuerzaPassword(String pass) {
        int fuerza = 0;
        if (pass.length() >= 6) fuerza++;
        if (pass.length() >= 8) fuerza++;
        if (pass.matches(".*\\d.*")) fuerza++; // Tiene números
        if (pass.matches(".*[a-z].*") && pass.matches(".*[A-Z].*")) fuerza++; // Tiene mayús y minús
        return fuerza;
    }

    /**
     * Alterna la visibilidad de las contraseñas
     */
    private void toggleMostrarPassword() {
        if (chkMostrarPassword.isSelected()) {
            txtPassword.setEchoChar((char) 0);
            txtConfirmPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar('•');
            txtConfirmPassword.setEchoChar('•');
        }
    }

    // --- CAMBIO: 'irALogin' pasa TODOS los servicios de vuelta ---
    private void irALogin() {
        // Pasa todos los servicios que FrmLogin necesita
        FrmLogin frmLogin = new FrmLogin(
            this.estudianteService, 
            this.carreraService,
            this.hobbyService,
            this.interesService
        ); 
        frmLogin.setVisible(true);
        this.dispose();
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
                
                // Y se las "inyecta" al FrmRegistro
                new FrmRegistro(estService, carService, hobService, intService).setVisible(true);
            }
        });
    }
}
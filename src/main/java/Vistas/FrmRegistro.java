package Vistas;

/**
 *
 * @author alfre
 */

import Domain.Carrera;
import Domain.Estudiante;
import Service.CarreraService;
import Service.EstudianteService;
import InterfaceService.ICarreraService;
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
import java.util.ArrayList;
import java.util.List;

public class FrmRegistro extends JFrame {

    // --- Servicios ---
    private IEstudianteService estudianteService;
    private ICarreraService carreraService;
    private List<Carrera> listaCompletaDeCarreras;

    // --- Componentes UI ---
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

    // --- Colores ---
    private static final Color COLOR_FONDO_GRIS = new Color(240, 242, 245);
    private static final Color COLOR_PANEL_IZQUIERDO = new Color(230, 240, 250);
    private static final Color COLOR_PANEL_DERECHO = Color.WHITE;
    private static final Color COLOR_BOTON = new Color(0, 86, 179);
    private static final Color COLOR_TEXTO_GRIS = new Color(100, 100, 100);
    private static final Color COLOR_PLACEHOLDER = new Color(180, 180, 180);

    public FrmRegistro() {
        this.estudianteService = new EstudianteService();
        this.carreraService = new CarreraService();
        this.listaCompletaDeCarreras = new ArrayList<>();
        
        initComponentes();
        cargarLogo();
        cargarCarrerasDesdeBD();
    }

    private void initComponentes() {
        // --- 1. Ventana ---
        setTitle("Registro (Paso 1: Informacion Basica)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO_GRIS);
        setLayout(new BorderLayout());

        // --- 2. Panel Principal (Contenedor) ---
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setOpaque(false);
        add(panelPrincipal, BorderLayout.CENTER);

        // --- 3. Panel Izquierdo ---
        panelPrincipal.add(crearPanelIzquierdo());

        // --- 4. Panel Derecho ---
        panelPrincipal.add(crearPanelDerecho());

        // --- 5. Listeners ---
        btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarEstudiante();
            }
        });

        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                irALogin();
            }
        });

        cmbNivel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCarrerasFiltradas();
            }
        });
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_PANEL_IZQUIERDO);
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1;

        lblLogo = new JLabel();
        lblLogo.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblLogo, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1;
        gbc.insets = new Insets(30, 0, 30, 0);
        lblImagenGrande = new JLabel("[ Imagen Grande Aqui ]");
        lblImagenGrande.setFont(new Font("Arial", Font.PLAIN, 20));
        lblImagenGrande.setPreferredSize(new Dimension(300, 300));
        lblImagenGrande.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenGrande.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(lblImagenGrande, gbc);

        gbc.gridy++;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        JLabel lblTitulo = new JLabel("Conecta con tus compañeros Potros.");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(lblTitulo, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 0, 0);
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
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        // Titulo
        gbc.gridwidth = 2;
        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        panel.add(lblTitulo, gbc);

        // Subtitulo
        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 20, 0);
        JLabel lblSubtitulo = new JLabel("Únete a la comunidad estudiantil de ITSON.");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(COLOR_TEXTO_GRIS);
        panel.add(lblSubtitulo, gbc);

        // Fila Nombres
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 0, 0, 5);
        panel.add(new JLabel("Nombre(s)"), gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(5, 5, 0, 0);
        panel.add(new JLabel("Apellido Paterno"), gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.ipady = 10;
        gbc.insets = new Insets(0, 0, 10, 5);
        txtNombre = new JTextField();
        addPlaceholder(txtNombre, "Ingresa tu nombre");
        panel.add(txtNombre, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 5, 10, 0);
        txtApellidoPaterno = new JTextField();
        addPlaceholder(txtApellidoPaterno, "Ingresa tu apellido");
        panel.add(txtApellidoPaterno, gbc);

        // Apellido Materno
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Apellido Materno"), gbc);

        gbc.gridy++;
        gbc.ipady = 10;
        gbc.insets = new Insets(0, 0, 10, 0);
        txtApellidoMaterno = new JTextField();
        addPlaceholder(txtApellidoMaterno, "Ingresa tu apellido materno");
        panel.add(txtApellidoMaterno, gbc);

        // Correo
        gbc.gridy++;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Correo Institucional"), gbc);

        gbc.gridy++;
        gbc.ipady = 10;
        gbc.insets = new Insets(0, 0, 10, 0);
        txtEmail = new JTextField();
        addPlaceholder(txtEmail, "tu.id@potros.itson.edu.mx");
        panel.add(txtEmail, gbc);

        // Nivel (Ing/Lic)
        gbc.gridy++;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Nivel"), gbc);

        gbc.gridy++;
        gbc.ipady = 10;
        gbc.insets = new Insets(0, 0, 10, 0);
        String[] niveles = {"Selecciona tu nivel", "Ingeniería", "Licenciatura"};
        cmbNivel = new JComboBox<>(niveles);
        cmbNivel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(cmbNivel, gbc);

        // Carrera
        gbc.gridy++;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Carrera"), gbc);

        gbc.gridy++;
        gbc.ipady = 10;
        gbc.insets = new Insets(0, 0, 10, 0);
        cmbCarrera = new JComboBox<>();
        cmbCarrera.setFont(new Font("Arial", Font.PLAIN, 14));
        cmbCarrera.setEnabled(false);
        panel.add(cmbCarrera, gbc);

        // Password
        gbc.gridy++;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Contraseña"), gbc);

        gbc.gridy++;
        gbc.ipady = 10;
        gbc.insets = new Insets(0, 0, 10, 0);
        txtPassword = new JPasswordField();
        addPlaceholder(txtPassword, "Crea una contraseña segura");
        panel.add(txtPassword, gbc);

        // Confirmar Password
        gbc.gridy++;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 0, 0, 0);
        panel.add(new JLabel("Confirmar Contraseña"), gbc);

        gbc.gridy++;
        gbc.ipady = 10;
        gbc.insets = new Insets(0, 0, 20, 0);
        txtConfirmPassword = new JPasswordField();
        addPlaceholder(txtConfirmPassword, "Confirma tu contraseña");
        panel.add(txtConfirmPassword, gbc);

        // Boton Siguiente
        gbc.gridy++;
        gbc.ipady = 12;
        gbc.insets = new Insets(10, 0, 10, 0);
        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setFont(new Font("Arial", Font.BOLD, 14));
        btnSiguiente.setBackground(COLOR_BOTON);
        btnSiguiente.setForeground(Color.WHITE);
        btnSiguiente.setFocusPainted(false);
        btnSiguiente.setOpaque(true);
        btnSiguiente.setBorderPainted(false);
        panel.add(btnSiguiente, gbc);

        // Link Login
        gbc.gridy++;
        gbc.ipady = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 0, 0);
        lblLogin = new JLabel("<html>¿Ya tienes cuenta? <font color='#0056B3'>Inicia sesión</font></html>");
        lblLogin.setFont(new Font("Arial", Font.PLAIN, 12));
        lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.add(lblLogin, gbc);
        
        gbc.gridy++;
        gbc.weighty = 1.0;
        panel.add(new JPanel(), gbc);

        return panel;
    }

    private void cargarLogo() {
        try {
            String rutaLogo = "PEGA_AQUI_LA_RUTA_DE_TU_LOGO_PEQUENO.png";
            ImageIcon originalIcon = new ImageIcon(rutaLogo);
            Image imagenOriginal = originalIcon.getImage();
            Image imagenRedimensionada = imagenOriginal.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagenRedimensionada));
        } catch (Exception e) {
            lblLogo.setText("Logo");
        }
    }

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
        
        String prefijo = seleccion.equals("Ingeniería") ? "Ing" : "Lic";
        
        for (Carrera carrera : listaCompletaDeCarreras) {
            if (carrera.getNombreCarrera().startsWith(prefijo)) {
                cmbCarrera.addItem(carrera);
            }
        }
        
        cmbCarrera.setEnabled(true);
    }
    
    private void registrarEstudiante() {
        // 1. Recolectar datos
        String nombre = txtNombre.getText();
        String apellidoP = txtApellidoPaterno.getText();
        String apellidoM = txtApellidoMaterno.getText();
        String email = txtEmail.getText();
        String password = new String(txtPassword.getPassword());
        String confirmPass = new String(txtConfirmPassword.getPassword());
        String nivel = (String) cmbNivel.getSelectedItem();
        Carrera carrera = (Carrera) cmbCarrera.getSelectedItem();

        // 2. Validaciones de Frontend
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

        // 3. Logica de Backend
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
            
            // --- PROXIMO PASO: Abrir la pantalla de completar perfil ---
            // FrmCompletarPerfil frmPerfil = new FrmCompletarPerfil(estudianteCreado);
            // frmPerfil.setVisible(true);
            
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Error en el registro: " + e.getMessage(), 
                    "Error de Registro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void irALogin() {
        FrmLogin frmLogin = new FrmLogin();
        frmLogin.setVisible(true);
        this.dispose();
    }
    
    private void addPlaceholder(final JTextField field, final String placeholder) {
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmRegistro().setVisible(true);
            }
        });
    }
}
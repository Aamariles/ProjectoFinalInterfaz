package co.edu.unbosque.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener; // Para el Controller
import java.net.URL;

public class LoginPanel extends JPanel { // Cambiado de FondoPanel a JPanel, FondoPanel será un hijo
    private FondoPanel panelFondo; // Panel que tendrá la imagen de fondo
    private RedondeadoPanel panelFormulario; // Panel redondeado para el formulario
    private JLabel lblTitulo;
    private JLabel lblIconoUsuario;
    private JTextField txtUsername;
    private JLabel lblIconoPassword;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public static final String LOGIN_COMMAND = "LOGIN_SUBMIT";

    public LoginPanel() {
        setLayout(new BorderLayout()); // El LoginPanel usará BorderLayout para contener panelConFondo
        initComponents();
    }

    private void initComponents() {
        // 1. Panel con la imagen de fondo
        panelFondo = new FondoPanel("/imagenes/FondoLogin.png");
        panelFondo.setLayout(new GridBagLayout()); // Para centrar el panelFormulario
        add(panelFondo, BorderLayout.CENTER);

        // 2. Panel redondeado para el formulario
        panelFormulario = new RedondeadoPanel(new GridBagLayout(), 35, Color.WHITE);
        panelFormulario.setBorder(new EmptyBorder(40, 40, 40, 40));
        // Ajustar el tamaño preferido del panel de formulario si es necesario
        panelFormulario.setPreferredSize(new Dimension(380, 450)); // Ejemplo de tamaño

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.gridwidth = GridBagConstraints.REMAINDER; // Cada componente principal ocupa una fila
        gbcForm.weightx = 1.0;


        URL logoUrl = getClass().getResource("/imagenes/LogoNeoLeague.png");
        if (logoUrl != null) {
            ImageIcon originalIcon = new ImageIcon(logoUrl);
            Image image = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JLabel lblIconoLogo = new JLabel(new ImageIcon(image));
            gbcForm.insets = new Insets(0, 0, 15, 0);
            panelFormulario.add(lblIconoLogo, gbcForm);
        }


        lblTitulo = new JLabel("LOG IN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(50, 50, 50));
        gbcForm.fill = GridBagConstraints.HORIZONTAL; // El título puede ocupar el ancho
        gbcForm.anchor = GridBagConstraints.CENTER;
        gbcForm.insets = new Insets(0, 0, 20, 0); // Espacio debajo del título
        panelFormulario.add(lblTitulo, gbcForm); // Resetear insets

        // Campo Username con icono emoji
        JLabel lblPromptUsuario = new JLabel("Usuario:");
        lblPromptUsuario.setFont(new Font("Arial", Font.BOLD, 13)); // Tamaño de fuente para la etiqueta
        lblPromptUsuario.setForeground(Color.BLACK);
        gbcForm.fill = GridBagConstraints.NONE; // La etiqueta no se expande
        gbcForm.anchor = GridBagConstraints.WEST; // Alineada a la izquierda
        gbcForm.insets = new Insets(10, 5, 2, 5); // Espacio: arriba, izq, abajo, der
        panelFormulario.add(lblPromptUsuario, gbcForm);

        JPanel panelUsername = new JPanel(new BorderLayout(10, 0));
        panelUsername.setOpaque(false); // Hacer transparente para que se vea el fondo del panelFormulario
        lblIconoUsuario = new JLabel("👤");
        lblIconoUsuario.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        lblIconoUsuario.setForeground(Color.GRAY);
        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 16));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 12, 10, 12)
        ));
        panelUsername.add(lblIconoUsuario, BorderLayout.WEST);
        panelUsername.add(txtUsername, BorderLayout.CENTER);
        gbcForm.fill = GridBagConstraints.HORIZONTAL; // El panel del campo de texto sí se expande
        gbcForm.anchor = GridBagConstraints.CENTER;
        gbcForm.insets = new Insets(0, 5, 10, 5);
        panelFormulario.add(panelUsername, gbcForm);

        // --- Etiqueta y Campo Contraseña ---
        JLabel lblPromptPassword = new JLabel("Contraseña:");
        lblPromptPassword.setFont(new Font("Arial", Font.BOLD, 13));
        lblPromptPassword.setForeground( Color.BLACK);
        gbcForm.fill = GridBagConstraints.NONE;
        gbcForm.anchor = GridBagConstraints.WEST;
        gbcForm.insets = new Insets(10, 5, 2, 5);
        panelFormulario.add(lblPromptPassword, gbcForm);

        JPanel panelPassword = new JPanel(new BorderLayout(10, 0));
        panelPassword.setOpaque(false);
        lblIconoPassword = new JLabel("🔒");
        lblIconoPassword.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        lblIconoPassword.setForeground(Color.GRAY);
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 16));
        // Padding interno del campo de texto: arriba/abajo aumentado para más altura
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 12, 10, 12) // Aumentado el padding vertical de 8 a 10
        ));
        panelPassword.add(lblIconoPassword, BorderLayout.WEST);
        panelPassword.add(txtPassword, BorderLayout.CENTER);
        gbcForm.fill = GridBagConstraints.HORIZONTAL;
        gbcForm.anchor = GridBagConstraints.CENTER;
        gbcForm.insets = new Insets(0, 5, 15, 5); // Un poco más de espacio antes del botón
        panelFormulario.add(panelPassword, gbcForm);

        // --- Botón Ingresar ---
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 18));
        btnIngresar.setBackground(Color.decode("#32327e"));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setActionCommand(LOGIN_COMMAND);
        btnIngresar.setBorder(new EmptyBorder(12, 30, 12, 30)); // Un poco más de padding para el botón

        btnIngresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnIngresar.setBackground(Color.decode("#1e1e50"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnIngresar.setBackground(Color.decode("#32327e"));
            }
        });
        gbcForm.fill = GridBagConstraints.HORIZONTAL; // El botón también se expande
        gbcForm.anchor = GridBagConstraints.CENTER;
        gbcForm.insets = new Insets(20, 5, 0, 5); // Espacio encima del botón
        panelFormulario.add(btnIngresar, gbcForm);

        GridBagConstraints gbcMain = new GridBagConstraints();
        panelFondo.add(panelFormulario, gbcMain);
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        btnIngresar.addActionListener(listener);
    }

    public void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
    }

    public JTextField getUsernameField() {
        return txtUsername;
    }

    public JButton getBtnIngresar() {
        return btnIngresar;
    }
}
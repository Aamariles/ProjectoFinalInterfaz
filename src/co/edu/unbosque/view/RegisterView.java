package co.edu.unbosque.view;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class RegisterView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField user;
	private JPasswordField password, confirmPassword;
	private JButton register, goBack;
	private JToggleButton showPassword;
	private JLabel passwordStatus;

	public RegisterView() {
		setTitle("Register Users");
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
		panelPrincipal.setBackground(new Color(255, 223, 0));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel tituloLabel = new JLabel("Register Users", SwingConstants.CENTER);
		tituloLabel.setFont(new Font("Arial", Font.BOLD, 28));
		tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelPrincipal.add(tituloLabel);
		panelPrincipal.add(Box.createVerticalStrut(20));

		JPanel panelFormulario = new JPanel(new GridBagLayout());
		panelFormulario.setBackground(new Color(255, 223, 0));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		panelFormulario.add(new JLabel("Usuario:"), gbc);
		gbc.gridx = 1;
		user = new JTextField(25);
		panelFormulario.add(user, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		panelFormulario.add(new JLabel("Contraseña:"), gbc);
		gbc.gridx = 1;
		password = new JPasswordField(25);
		panelFormulario.add(password, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		panelFormulario.add(new JLabel("Confirmar Contraseña:"), gbc);
		gbc.gridx = 1;
		confirmPassword = new JPasswordField(25);
		panelFormulario.add(confirmPassword, gbc);

		gbc.gridx = 1;
		gbc.gridy++;
		passwordStatus = new JLabel(" ");
		passwordStatus.setForeground(Color.RED);
		panelFormulario.add(passwordStatus, gbc);

		showPassword = new JToggleButton("👁️ Mostrar");
		showPassword.addActionListener(e -> {
			boolean mostrar = showPassword.isSelected();
			password.setEchoChar(mostrar ? (char) 0 : '•');
			showPassword.setText(mostrar ? "👁️ Ocultar" : "👁️ Mostrar");
		});
		gbc.gridx = 0;
		gbc.gridy++;
		panelFormulario.add(showPassword, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		register = new JButton("📝 Registrar");
		estilizarBoton(register);
		panelFormulario.add(register, gbc);

		goBack = new JButton("🔙 Regresar");
		estilizarBoton(goBack);
		gbc.gridy++;
		panelFormulario.add(goBack, gbc);

		panelPrincipal.add(panelFormulario);
		add(panelPrincipal);

		password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				validarContrasena();
			}
		});
	}

	private void validarContrasena() {
		String contrasena = new String(password.getPassword());
		if (contrasena.length() >= 8 && contrasena.matches(".*[A-Z].*")
				&& contrasena.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
			passwordStatus.setText("✔ Contraseña válida");
			passwordStatus.setForeground(Color.GREEN);
		} else {
			passwordStatus.setText("❌ La contraseña debe tener 8 caracteres, 1 mayúscula y 1 símbolo.");
			passwordStatus.setForeground(Color.RED);
		}
	}

	private void estilizarBoton(JButton boton) {
		boton.setBackground(new Color(0, 80, 160));
		boton.setForeground(Color.WHITE);
		boton.setFont(new Font("Arial", Font.BOLD, 16));
		boton.setFocusPainted(false);
		boton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
		boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public JTextField getuser() {
		return user;
	}

	public JPasswordField getpassword() {
		return password;
	}

	public JPasswordField getconfirmPassword() {
		return confirmPassword;
	}

	public JButton getregister() {
		return register;
	}

	public JButton getgoBack() {
		return goBack;
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje);
	}
}
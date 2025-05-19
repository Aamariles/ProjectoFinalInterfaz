package co.edu.unbosque.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class LoginView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField User;
	private JPasswordField password;
	private JButton Login, Register;
	private JToggleButton ShowPassword;

	public LoginView() {
		setTitle("Login - Users");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// Panel principal con diseño moderno
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(new Color(255, 223, 0)); 
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Label de usuario
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(styleLabel("User:"), gbc);

		// field de usuario
		gbc.gridx = 1;
		User = new JTextField(15);
		styleField(User);
		panel.add(User, gbc);

		// Label de contraseña
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(styleLabel("Password:"), gbc);

		// field de contraseña
		gbc.gridx = 1;
		password = new JPasswordField(15);
		styleField(password);
		panel.add(password, gbc);

		// Botón para mostrar/ocultar contraseña
		ShowPassword = new JToggleButton("👁");
		ShowPassword.setPreferredSize(new Dimension(50, 25));
		gbc.gridx = 2;
		panel.add(ShowPassword, gbc);

		// Botón de login
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		Login = new JButton("Iniciar Sesión");
		styleButton(Login, new Color(0, 80, 160)); // Azul oscuro
		panel.add(Login, gbc);

		// Botón de registro
		gbc.gridy = 3;
		Register = new JButton("Register");
		styleButton(Register, new Color(0, 120, 255)); // Azul claro
		panel.add(Register, gbc);

		add(panel, BorderLayout.CENTER);

		// Acción para mostrar/ocultar contraseña
		ShowPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ShowPassword.isSelected()) {
					password.setEchoChar((char) 0);
				} else {
					password.setEchoChar('•');
				}
			}
		});
	}

	private JLabel styleLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("Arial", Font.BOLD, 14));
		return label;
	}

	private void styleField(JTextField field) {
		field.setFont(new Font("Arial", Font.PLAIN, 14));
		field.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}

	private void styleButton(JButton button, Color color) {
		button.setBackground(color);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, 14));
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
	}

	// Método para mostrar mensajes
	public void showMensagge(String mensagge) {
		JOptionPane.showMessageDialog(this, mensagge);
	}

	public JTextField getUser() {
		return User;
	}

	public void setUser(JTextField user) {
		User = user;
	}

	public JPasswordField getPassword() {
		return password;
	}

	public void setPassword(JPasswordField password) {
		this.password = password;
	}

	public JButton getLogin() {
		return Login;
	}

	public void setLogin(JButton login) {
		Login = login;
	}

	public JButton getRegister() {
		return Register;
	}

	public void setRegister(JButton register) {
		Register = register;
	}

	public JToggleButton getShowPassword() {
		return ShowPassword;
	}

	public void setShowPassword(JToggleButton showPassword) {
		ShowPassword = showPassword;
	}
	
}

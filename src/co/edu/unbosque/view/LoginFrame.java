package co.edu.unbosque.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private LoginPanel panelDeLogin; // Panel que contiene todos los elementos del login

    public LoginFrame() {
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650); // Tamaño original
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        setVisible(true); // Es mejor llamar a setVisible(true) después de añadir todos los componentes
    }

    private void initComponents() {
        panelDeLogin = new LoginPanel();
        add(panelDeLogin); // Agrega el panel de login al frame
    }

    // Métodos delegados al LoginPanel
    public String getUsername() {
        return panelDeLogin.getUsername();
    }

    public String getPassword() {
        return panelDeLogin.getPassword();
    }

    public void addLoginListener(ActionListener listener) {
        panelDeLogin.addLoginListener(listener);
    }

    public void clearFields() {
        panelDeLogin.clearFields();
    }

    public JTextField getUsernameField() {
        return panelDeLogin.getUsernameField();
    }

    /**
     * Muestra un mensaje en un diálogo.
     * Este método tiene sentido que permanezca en el JFrame.
     * @param message El mensaje a mostrar.
     * @param title El título del diálogo.
     * @param messageType El tipo de mensaje (JOptionPane.ERROR_MESSAGE, INFORMATION_MESSAGE, etc.)
     */
    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    // Si necesitas acceso al panel principal desde fuera (por ejemplo, desde un controlador)
    public LoginPanel getLoginPanel() {
        return panelDeLogin;
    }

    // Ejemplo de cómo podrías iniciar esto desde un main
    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> new LoginFrame());
    // }
}

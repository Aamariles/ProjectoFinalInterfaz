package co.edu.unbosque.view;
import javax.swing.*;
import java.awt.event.ActionListener;
public class LoginFrame extends JFrame {
    private LoginPanel panelDeLogin;
    public LoginFrame() {
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
        setVisible(true);
    }
    private void initComponents() {
        panelDeLogin = new LoginPanel();
        add(panelDeLogin);
    }
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
    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    public LoginPanel getLoginPanel() {
        return panelDeLogin;
    }
}
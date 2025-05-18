package co.edu.unbosque.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
public class PrincipalFrame extends JFrame {
    private PanelNavegacion panelNavegacion;
    private JPanel currentCenterPanel;
    private JLabel lblMainTitle;
    private JPanel mainContentWrapper;
    public static final String NAV_DASHBOARD_COMMAND = PanelNavegacion.NAV_DASHBOARD_COMMAND;
    public static final String NAV_TOURNAMENTS_COMMAND = PanelNavegacion.NAV_TOURNAMENTS_COMMAND;
    public static final String NAV_TEAMS_COMMAND = PanelNavegacion.NAV_TEAMS_COMMAND;
    public static final String NAV_MATCHES_COMMAND = PanelNavegacion.NAV_MATCHES_COMMAND;
    public static final String NAV_REPORTS_COMMAND = PanelNavegacion.NAV_REPORTS_COMMAND;
    public static final String NAV_SETTINGS_COMMAND = PanelNavegacion.NAV_SETTINGS_COMMAND;
    public PrincipalFrame() {
        setTitle("Dashboard Aplicación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(Color.decode("#e1e8ed"));
        initComponents();
    }
    private void initComponents() {
        panelNavegacion = new PanelNavegacion();
        add(panelNavegacion, BorderLayout.WEST);
        mainContentWrapper = new JPanel(new BorderLayout(10,10));
        mainContentWrapper.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        mainContentWrapper.setBackground(Color.decode("#e1e8ed"));
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setOpaque(false);
        lblMainTitle = new JLabel("Dashboard");
        lblMainTitle.setFont(new Font("Arial", Font.BOLD, 28));
        JLabel lblAdminInfo = new JLabel("Admin \u263A");
        lblAdminInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        panelHeader.add(lblMainTitle, BorderLayout.WEST);
        panelHeader.add(lblAdminInfo, BorderLayout.EAST);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        mainContentWrapper.add(panelHeader, BorderLayout.NORTH);
        add(mainContentWrapper, BorderLayout.CENTER);
    }
    public void setCenterPanel(JPanel newPanel) {
        if (newPanel == null) return;
        if (currentCenterPanel != null) {
            mainContentWrapper.remove(currentCenterPanel);
        }
        currentCenterPanel = newPanel;
        mainContentWrapper.add(currentCenterPanel, BorderLayout.CENTER);
        mainContentWrapper.revalidate();
        mainContentWrapper.repaint();
    }
    public void setMainContentTitle(String title) {
        if (lblMainTitle != null) {
            lblMainTitle.setText(title);
        }
    }
    public void addNavigationListeners(ActionListener listener) {
        if (panelNavegacion != null) {
            panelNavegacion.addNavigationListeners(listener);
        }
    }
    public PanelNavegacion getNavigationPanel() {
        return panelNavegacion;
    }
    public JPanel getCurrentCenterPanel() {
        return currentCenterPanel;
    }
    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
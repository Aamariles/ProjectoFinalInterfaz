package co.edu.unbosque.view;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class PanelNavegacion extends JPanel {
    private JLabel lblAppName;
    private JButton btnNavDashboard;
    private JButton btnNavTournaments;
    private JButton btnNavTeams;
    private JButton btnNavMatches;
    private JButton btnNavReports;
    private JButton btnNavSettings;
    public static final String NAV_DASHBOARD_COMMAND = "NAV_DASHBOARD";
    public static final String NAV_TOURNAMENTS_COMMAND = "NAV_TOURNAMENTS";
    public static final String NAV_TEAMS_COMMAND = "NAV_TEAMS";
    public static final String NAV_MATCHES_COMMAND = "NAV_MATCHES";
    public static final String NAV_REPORTS_COMMAND = "NAV_REPORTS";
    public static final String NAV_SETTINGS_COMMAND = "NAV_SETTINGS";
    private JButton currentlyActiveButton;
    private final Color COLOR_FONDO = new Color(33, 37, 41);
    private final Color COLOR_TEXTO = new Color(222, 226, 230);
    private final Color COLOR_HOVER = new Color(52, 58, 64);
    private final Color COLOR_ACTIVO_FONDO = new Color(0, 123, 255);
    private final Color COLOR_ACTIVO_TEXTO = Color.WHITE;
    private final int ANCHO_BARRA_ACTIVA = 5;
    private final Border PADDING_CONTENIDO_BOTON = new EmptyBorder(10, 3, 10, 5);
    private final Border BORDE_ACTIVO = new CompoundBorder(
            new MatteBorder(0, ANCHO_BARRA_ACTIVA, 0, 0, COLOR_ACTIVO_FONDO),
            PADDING_CONTENIDO_BOTON
    );
    private final Border BORDE_INACTIVO = new CompoundBorder(
            new EmptyBorder(0, ANCHO_BARRA_ACTIVA, 0, 0),
            PADDING_CONTENIDO_BOTON
    );
    public PanelNavegacion() {
        initComponents();
    }
    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(COLOR_FONDO);
        setPreferredSize(new Dimension(180, 0));
        setBorder(new EmptyBorder(10, 0, 10, 0));
        lblAppName = new JLabel("APP");
        lblAppName.setFont(new Font("Arial", Font.BOLD, 22));
        lblAppName.setForeground(Color.WHITE);
        lblAppName.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblAppName.setBorder(new EmptyBorder(0, 15, 25, 15));
        String iconDashboard = "📊";
        String iconTournaments = "🏆";
        String iconTeams = "👥";
        String iconMatches = "⚔️";
        String iconReports = "📄";
        String iconSettings = "⚙️";
        btnNavDashboard = createNavButton(iconDashboard, "Dashboard", NAV_DASHBOARD_COMMAND);
        btnNavTournaments = createNavButton(iconTournaments, "Torneos", NAV_TOURNAMENTS_COMMAND);
        btnNavTeams = createNavButton(iconTeams, "Equipos", NAV_TEAMS_COMMAND);
        btnNavMatches = createNavButton(iconMatches, "Partidas", NAV_MATCHES_COMMAND);
        btnNavReports = createNavButton(iconReports, "Reportes", NAV_REPORTS_COMMAND);
        btnNavSettings = createNavButton(iconSettings, "Configuración", NAV_SETTINGS_COMMAND);
        setActiveButton(btnNavDashboard);
        add(lblAppName);
        add(btnNavDashboard);
        add(createButtonSpacing());
        add(btnNavTournaments);
        add(createButtonSpacing());
        add(btnNavTeams);
        add(createButtonSpacing());
        add(btnNavMatches);
        add(createButtonSpacing());
        add(btnNavReports);
        add(Box.createVerticalGlue());
        add(btnNavSettings);
        add(createButtonSpacing());
    }
    private Component createButtonSpacing() {
        return Box.createRigidArea(new Dimension(0, 8));
    }
    private JButton createNavButton(String iconText, String buttonText, String actionCommand) {
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setLayout(new BorderLayout(8, 0));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BORDE_INACTIVO);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMinimumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setPreferredSize(new Dimension(180, 40));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        JLabel lblIcon = new JLabel(iconText);
        lblIcon.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblIcon.setBorder(null);
        JLabel lblText = new JLabel(buttonText);
        lblText.setFont(button.getFont());
        button.setBackground(COLOR_FONDO);
        lblIcon.setForeground(COLOR_TEXTO);
        lblText.setForeground(COLOR_TEXTO);
        button.add(lblIcon, BorderLayout.WEST);
        button.add(lblText, BorderLayout.CENTER);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if (button != currentlyActiveButton) {
                    button.setBackground(COLOR_HOVER);
                }
            }
            public void mouseExited(MouseEvent evt) {
                if (button != currentlyActiveButton) {
                    button.setBackground(COLOR_FONDO);
                }
            }
        });
        return button;
    }
    public void setActiveButton(JButton activeButton) {
        if (currentlyActiveButton != null) {
            currentlyActiveButton.setBackground(COLOR_FONDO);
            currentlyActiveButton.setBorder(BORDE_INACTIVO);
            if (currentlyActiveButton.getComponentCount() >= 2) {
                Component iconComp = currentlyActiveButton.getComponent(0);
                Component textComp = currentlyActiveButton.getComponent(1);
                if (iconComp instanceof JLabel) {
                    ((JLabel) iconComp).setForeground(COLOR_TEXTO);
                }
                if (textComp instanceof JLabel) {
                    ((JLabel) textComp).setForeground(COLOR_TEXTO);
                }
            }
        }
        currentlyActiveButton = activeButton;
        if (currentlyActiveButton != null) {
            currentlyActiveButton.setBackground(COLOR_ACTIVO_FONDO);
            currentlyActiveButton.setBorder(BORDE_ACTIVO);
            if (currentlyActiveButton.getComponentCount() >= 2) {
                Component iconComp = currentlyActiveButton.getComponent(0);
                Component textComp = currentlyActiveButton.getComponent(1);
                if (iconComp instanceof JLabel) {
                    ((JLabel) iconComp).setForeground(COLOR_ACTIVO_TEXTO);
                }
                if (textComp instanceof JLabel) {
                    ((JLabel) textComp).setForeground(COLOR_ACTIVO_TEXTO);
                }
            }
        }
        repaint();
        revalidate();
    }
    public void addNavigationListeners(ActionListener listener) {
        btnNavDashboard.addActionListener(listener);
        btnNavTournaments.addActionListener(listener);
        btnNavTeams.addActionListener(listener);
        btnNavMatches.addActionListener(listener);
        btnNavReports.addActionListener(listener);
        btnNavSettings.addActionListener(listener);
    }
    public JButton getBtnNavDashboard() { return btnNavDashboard; }
    public JButton getBtnNavTournaments() { return btnNavTournaments; }
    public JButton getBtnNavTeams() { return btnNavTeams; }
    public JButton getBtnNavMatches() { return btnNavMatches; }
    public JButton getBtnNavReports() { return btnNavReports; }
    public JButton getBtnNavSettings() { return btnNavSettings; }
}
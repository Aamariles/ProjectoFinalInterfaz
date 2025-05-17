package co.edu.unbosque.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelNavegacion extends JPanel { // Nombre de clase cambiado

    private JLabel lblAppName;
    private JButton btnNavDashboard;
    private JButton btnNavTournaments;
    private JButton btnNavTeams;
    private JButton btnNavMatches;
    private JButton btnNavReports;
    private JButton btnNavSettings;

    // --- Constantes para Action Commands ---
    // Estas constantes pueden ser referenciadas como PanelNavegacion.NAV_DASHBOARD_COMMAND
    public static final String NAV_DASHBOARD_COMMAND = "NAV_DASHBOARD";
    public static final String NAV_TOURNAMENTS_COMMAND = "NAV_TOURNAMENTS";
    public static final String NAV_TEAMS_COMMAND = "NAV_TEAMS";
    public static final String NAV_MATCHES_COMMAND = "NAV_MATCHES";
    public static final String NAV_REPORTS_COMMAND = "NAV_REPORTS";
    public static final String NAV_SETTINGS_COMMAND = "NAV_SETTINGS";

    private JButton currentlyActiveButton;

    public PanelNavegacion() { // Constructor actualizado al nuevo nombre de clase
        initComponents();
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(52, 58, 64)); // Color oscuro para la barra lateral
        setPreferredSize(new Dimension(220, 0)); // Ancho preferido, altura se adaptará
        setBorder(new EmptyBorder(20, 10, 20, 10));

        lblAppName = new JLabel("Dashboard App"); // Puedes cambiar este nombre si lo deseas
        lblAppName.setFont(new Font("Arial", Font.BOLD, 20));
        lblAppName.setForeground(Color.WHITE);
        lblAppName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAppName.setBorder(new EmptyBorder(0, 0, 20, 0));

        btnNavDashboard = createNavButton("Dashboard", NAV_DASHBOARD_COMMAND);
        btnNavTournaments = createNavButton("Torneos", NAV_TOURNAMENTS_COMMAND); // Texto actualizado a español
        btnNavTeams = createNavButton("Equipos", NAV_TEAMS_COMMAND);         // Texto actualizado a español
        btnNavMatches = createNavButton("Partidas", NAV_MATCHES_COMMAND);      // Texto actualizado a español
        btnNavReports = createNavButton("Reportes", NAV_REPORTS_COMMAND);      // Texto actualizado a español
        btnNavSettings = createNavButton("Configuración", NAV_SETTINGS_COMMAND); // Texto actualizado a español

        // Marcar Dashboard como activo inicialmente
        setActiveButton(btnNavDashboard);

        add(lblAppName);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btnNavDashboard);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(btnNavTournaments);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(btnNavTeams);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(btnNavMatches);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(btnNavReports);
        add(Box.createVerticalGlue()); // Empuja Settings hacia abajo
        add(btnNavSettings);
    }

    private JButton createNavButton(String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setActionCommand(actionCommand);
        button.setForeground(new Color(200, 200, 200)); // Texto claro
        button.setBackground(new Color(52, 58, 64)); // Fondo del panel
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding interno
        button.setHorizontalAlignment(SwingConstants.LEFT);
        // Asegura que todos los botones puedan tener el mismo ancho si el BoxLayout lo permite
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height + 10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Inicialmente, los botones no activos no son opacos para el fondo del panel
        button.setOpaque(false);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if (button != currentlyActiveButton) {
                    button.setBackground(new Color(73, 80, 87));
                    button.setOpaque(true); // Necesario para que se vea el fondo en hover
                }
            }

            public void mouseExited(MouseEvent evt) {
                if (button != currentlyActiveButton) {
                    button.setBackground(new Color(52, 58, 64)); // Restaurar color
                    button.setOpaque(false); // Volver a no opaco si no está activo
                }
            }
        });
        return button;
    }

    public void setActiveButton(JButton activeButton) {
        if (currentlyActiveButton != null) {
            // Restaura el botón anteriormente activo a su estado normal
            currentlyActiveButton.setBackground(new Color(52, 58, 64));
            currentlyActiveButton.setForeground(new Color(200,200,200));
            currentlyActiveButton.setOpaque(false); // Deja de ser opaco
        }
        currentlyActiveButton = activeButton;
        if (currentlyActiveButton != null) {
            // Configura el nuevo botón activo
            currentlyActiveButton.setBackground(new Color(0, 123, 255)); // Color azul para el activo
            currentlyActiveButton.setForeground(Color.WHITE);
            currentlyActiveButton.setOpaque(true); // Hacer opaco para mostrar el color de fondo
        }
        // Forzar redibujado del panel para asegurar que los cambios visuales se apliquen
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

    // Getters para los botones, útiles para que el controlador pueda llamar a setActiveButton
    public JButton getBtnNavDashboard() { return btnNavDashboard; }
    public JButton getBtnNavTournaments() { return btnNavTournaments; }
    public JButton getBtnNavTeams() { return btnNavTeams; }
    public JButton getBtnNavMatches() { return btnNavMatches; }
    public JButton getBtnNavReports() { return btnNavReports; }
    public JButton getBtnNavSettings() { return btnNavSettings; }
}
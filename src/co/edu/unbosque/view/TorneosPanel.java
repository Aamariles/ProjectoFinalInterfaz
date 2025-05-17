package co.edu.unbosque.view;

import javax.swing.*;
import java.awt.*;

public class TorneosPanel extends JPanel {

    private JTabbedPane tabbedPane;
    private TorneosInfoPanel generalInfoPanel;
    private JPanel participantsPanel; // Placeholder
    private JPanel schedulePanel;     // Placeholder
    private JPanel matchesPanel;      // Placeholder
    private JPanel standingsPanel;    // Placeholder
    private JPanel historyPanel;      // Placeholder

    public TorneosPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 242, 245)); // Fondo consistente

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        // Pestaña de Información General
        generalInfoPanel = new TorneosInfoPanel();
        tabbedPane.addTab("Información general", null, generalInfoPanel, "Vista general de los torneos");

        // Pestañas Placeholder
        participantsPanel = createPlaceholderPanel("Listado de Participantes por Torneo");
        tabbedPane.addTab("Participantes", null, participantsPanel, "Ver participantes");

        schedulePanel = createPlaceholderPanel("Cronograma de Próximos Torneos");
        tabbedPane.addTab("Cronograma", null, schedulePanel, "Fechas de torneos");

        matchesPanel = createPlaceholderPanel("Partidas en Curso por Torneo");
        tabbedPane.addTab("Partidas", null, matchesPanel, "Juegos actuales");

        standingsPanel = createPlaceholderPanel("Clasificación de Equipos por Torneo");
        tabbedPane.addTab("Clasificación", null, standingsPanel, "Tabla de posiciones");

        historyPanel = createPlaceholderPanel("Historial de Torneos Pasados");
        tabbedPane.addTab("Historial", null, historyPanel, "Consultar torneos finalizados");

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new GridBagLayout()); // Centrar el texto
        panel.setBackground(Color.WHITE);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.LIGHT_GRAY);
        panel.add(label);
        return panel;
    }

    // Métodos para que el controlador actualice datos específicos de cada pestaña
    public TorneosInfoPanel getGeneralInfoPanel() {
        return generalInfoPanel;
    }
    // ... getters para otros paneles si es necesario ...
}
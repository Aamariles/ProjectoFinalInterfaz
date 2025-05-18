package co.edu.unbosque.view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.function.Consumer;
public class PrincipalPanel extends JPanel {
    private JPanel panelTarjetasResumen;
    private JPanel cardUpcomingMatchesPanel;
    private JPanel cardActiveTournamentsPanel;
    private JPanel cardTopPlayersPanel;
    private JLabel lblValueUpcoming;
    private JLabel lblValueActive;
    private JLabel lblValueTopPlayers;
    private JPanel panelRecentMatches;
    private JLabel lblTitleRecentMatches;
    private JTable tableRecentMatches;
    private JScrollPane scrollPaneRecentMatches;
    private DefaultTableModel modelRecentMatches;
    private JPanel panelPerformanceMetrics;
    private JLabel lblTitlePerformanceMetrics;
    private JPanel panelGraphPerformance;
    private JPanel panelRecentIndices;
    private JLabel lblTitleRecentIndices;
    private JPanel panelGraphIndices;
    public PrincipalPanel() {
        initComponents();
    }
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 20, 15, 20));
        setBackground(Color.decode("#cfdbe3"));
        JPanel panelContenidoCentral = new JPanel();
        panelContenidoCentral.setOpaque(false);
        panelContenidoCentral.setLayout(new BoxLayout(panelContenidoCentral, BoxLayout.Y_AXIS));
        add(panelContenidoCentral, BorderLayout.NORTH);
        panelTarjetasResumen = new JPanel(new GridLayout(1, 3, 15, 15));
        panelTarjetasResumen.setOpaque(false);
        cardUpcomingMatchesPanel = createSummaryCard("Upcoming Matches", "Loading...", "📅", valLabel -> this.lblValueUpcoming = valLabel);
        cardActiveTournamentsPanel = createSummaryCard("Active Tournaments", "0", "🏆", valLabel -> this.lblValueActive = valLabel);
        cardTopPlayersPanel = createSummaryCard("Top Players", "Loading...", "⭐", valLabel -> this.lblValueTopPlayers = valLabel);
        panelTarjetasResumen.add(cardUpcomingMatchesPanel);
        panelTarjetasResumen.add(cardActiveTournamentsPanel);
        panelTarjetasResumen.add(cardTopPlayersPanel);
        panelTarjetasResumen.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));
        panelContenidoCentral.add(panelTarjetasResumen);
        panelContenidoCentral.add(Box.createRigidArea(new Dimension(0, 20)));
        JPanel panelInferior = new JPanel(new GridLayout(1, 2, 20, 0));
        panelInferior.setOpaque(false);
        panelRecentMatches = new JPanel(new BorderLayout(0, 10));
        panelRecentMatches.setOpaque(false);
        panelRecentMatches.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(10, 10, 10, 10)
        ));
        lblTitleRecentMatches = new JLabel("Recent Matches");
        lblTitleRecentMatches.setFont(new Font("Arial", Font.BOLD, 18));
        String[] columnNames = {"Match", "Date", "Result"};
        Object[][] data = {{"Loading...", "", ""}};
        modelRecentMatches = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableRecentMatches = new JTable(modelRecentMatches);
        tableRecentMatches.setFillsViewportHeight(true);
        tableRecentMatches.setRowHeight(25);
        tableRecentMatches.setFont(new Font("Arial", Font.PLAIN, 14));
        tableRecentMatches.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        scrollPaneRecentMatches = new JScrollPane(tableRecentMatches);
        panelRecentMatches.add(lblTitleRecentMatches, BorderLayout.NORTH);
        panelRecentMatches.add(scrollPaneRecentMatches, BorderLayout.CENTER);
        panelInferior.add(panelRecentMatches);
        JPanel panelGraficosDerecha = new JPanel();
        panelGraficosDerecha.setOpaque(false);
        panelGraficosDerecha.setLayout(new BoxLayout(panelGraficosDerecha, BoxLayout.Y_AXIS));
        panelPerformanceMetrics = new JPanel(new BorderLayout(0, 10));
        panelPerformanceMetrics.setOpaque(false);
        panelPerformanceMetrics.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(10, 10, 10, 10)
        ));
        lblTitlePerformanceMetrics = new JLabel("Performance Metrics");
        lblTitlePerformanceMetrics.setFont(new Font("Arial", Font.BOLD, 18));
        panelGraphPerformance = new JPanel();
        panelGraphPerformance.add(new JLabel("Area del Gráfico de Líneas"));
        panelGraphPerformance.setOpaque(false);
        panelGraphPerformance.setBorder(new LineBorder(Color.GRAY));
        panelGraphPerformance.setPreferredSize(new Dimension(300, 150));
        panelPerformanceMetrics.add(lblTitlePerformanceMetrics, BorderLayout.NORTH);
        panelPerformanceMetrics.add(panelGraphPerformance, BorderLayout.CENTER);
        panelGraficosDerecha.add(panelPerformanceMetrics);
        panelGraficosDerecha.add(Box.createRigidArea(new Dimension(0, 20)));
        panelRecentIndices = new JPanel(new BorderLayout(0, 10));
        panelRecentIndices.setOpaque(false);
        panelRecentIndices.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(10, 10, 10, 10)
        ));
        lblTitleRecentIndices = new JLabel("Recent Indices");
        lblTitleRecentIndices.setFont(new Font("Arial", Font.BOLD, 18));
        panelGraphIndices = new JPanel();
        panelGraphIndices.add(new JLabel("Area del Gráfico de Barras"));
        panelGraphIndices.setOpaque(false);
        panelGraphIndices.setBorder(new LineBorder(Color.GRAY));
        panelGraphIndices.setPreferredSize(new Dimension(300, 150));
        panelRecentIndices.add(lblTitleRecentIndices, BorderLayout.NORTH);
        panelRecentIndices.add(panelGraphIndices, BorderLayout.CENTER);
        panelGraficosDerecha.add(panelRecentIndices);
        panelInferior.add(panelGraficosDerecha);
        panelContenidoCentral.add(panelInferior);
        add(panelContenidoCentral, BorderLayout.CENTER);
    }
    private JPanel createSummaryCard(String title, String initialValue, String iconText, Consumer<JLabel> valueLabelConsumer) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.decode("#dcdcdc")),
                new EmptyBorder(15, 15, 15, 15)
        ));
        JLabel lblIcon = new JLabel(iconText);
        lblIcon.setFont(new Font("Arial", Font.BOLD, 28));
        lblIcon.setForeground(new Color(0, 123, 255));
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setForeground(Color.DARK_GRAY);
        JLabel lblValue = new JLabel(initialValue);
        lblValue.setFont(new Font("Arial", Font.PLAIN, title.equals("Active Tournaments") ? 28 : 16));
        valueLabelConsumer.accept(lblValue);
        textPanel.add(lblTitle);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(lblValue);
        card.add(lblIcon, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }
    public void updateSummaryCards(String upcomingMatchInfo, String activeTournamentsCount, String topPlayerName) {
        if (lblValueUpcoming != null) lblValueUpcoming.setText(upcomingMatchInfo);
        if (lblValueActive != null) lblValueActive.setText(activeTournamentsCount);
        if (lblValueTopPlayers != null) lblValueTopPlayers.setText(topPlayerName);
    }
    public void updateRecentMatchesTable(Object[][] data) {
        modelRecentMatches.setDataVector(data, new String[]{"Match", "Date", "Result"});
    }
}
package co.edu.unbosque.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener; // Para los botones "Ver detalles"
import java.net.URL;

public class TorneosInfoPanel extends JPanel {

    private JPanel cardsContainerPanel;

    public TorneosInfoPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Fondo blanco para esta área específica
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // Panel para las tarjetas, envuelto en un JScrollPane
        cardsContainerPanel = new JPanel();
        // Usamos WrapLayout (necesitarías añadir esta clase a tu proyecto)
        // o GridLayout si prefieres un número fijo de columnas.
        // Por ahora, usaremos un GridLayout para simplicidad.
        cardsContainerPanel.setLayout(new GridLayout(0, 3, 15, 15)); // 0 filas = dinámico, 3 columnas, espaciado
        cardsContainerPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(cardsContainerPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        add(scrollPane, BorderLayout.CENTER);

        // Cargar torneos de ejemplo (el controlador lo haría con datos reales)
        loadSampleTournamentCards();
    }

    private void loadSampleTournamentCards() {
        // Datos de ejemplo
        addTournamentCard("Torneo de Primavera", "En curso", "/imagenes/Rocketleague.png", "16 equipos", "24 jugadores", "10 Abr 2024 - 15 May 2024", true, null);
        addTournamentCard("Campeonato Nacional", "Registrando", "/imagenes/Fifa.png", "32 equipos", "N/A", "08 Jul 2024 - 05 Ago 2024", false, null);
        addTournamentCard("Liga de Verano", "Registrando", "/imagenes/Formula1.png", "Ilimitado", "N/A", "25 Jul 2024 - 25 Ago 2024", false, null);
        addTournamentCard("Copa Internacional", "Finalizado", "/imagenes/GranTurismo.png", "64 equipos", "128 jugadores", "24 Ene 2024 - 20 Nov 2024", false, null);
        // Añade más tarjetas para probar el scroll
        addTournamentCard("Torneo Relámpago", "Próximo", "/imagenes/Rocketleague.png", "8 equipos", "16 jugadores", "01 Jun 2024", false, null);
    }

    public void addTournamentCard(String name, String status, String imagePath,
                                  String teamsInfo, String playersInfo, String dateInfo,
                                  boolean showDetailsButton, ActionListener detailsListener) {
        JPanel card = new JPanel(new BorderLayout(0, 10)); // Espaciado vertical
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true), // Borde redondeado ligero
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Panel Superior: Nombre y Estado
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JLabel lblName = new JLabel(name);
        lblName.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel lblStatus = new JLabel(status);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        lblStatus.setOpaque(true);
        lblStatus.setBorder(new EmptyBorder(3, 8, 3, 8));
        lblStatus.setForeground(Color.WHITE);
        // Colores de status (ejemplos)
        if ("En curso".equalsIgnoreCase(status)) {
            lblStatus.setBackground(new Color(0, 123, 255)); // Azul
        } else if ("Registrando".equalsIgnoreCase(status)) {
            lblStatus.setBackground(new Color(255, 193, 7)); // Amarillo/Naranja
            lblStatus.setForeground(Color.BLACK);
        } else if ("Finalizado".equalsIgnoreCase(status)) {
            lblStatus.setBackground(new Color(108, 117, 125)); // Gris
        } else { // Próximo, etc.
            lblStatus.setBackground(new Color(40, 167, 69)); // Verde
        }
        topPanel.add(lblName, BorderLayout.WEST);
        topPanel.add(lblStatus, BorderLayout.EAST);
        card.add(topPanel, BorderLayout.NORTH);

        // Panel Central: Icono e Información
        JPanel centerPanel = new JPanel(new BorderLayout(15, 0)); // Espaciado entre icono e info
        centerPanel.setOpaque(false);

        JLabel lblIcon = new JLabel();
        try {
            URL imgUrl = getClass().getResource(imagePath);
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                // Escalar imagen si es necesario para que todas tengan un tamaño similar
                Image img = icon.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
                lblIcon.setIcon(new ImageIcon(img));
            } else {
                lblIcon.setText("NoImg"); // Placeholder si no se carga
                lblIcon.setPreferredSize(new Dimension(80,60));
                lblIcon.setBorder(new LineBorder(Color.GRAY));
                lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            lblIcon.setText("ErrImg");
            lblIcon.setPreferredSize(new Dimension(80,60));
            lblIcon.setBorder(new LineBorder(Color.RED));
            lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
        }
        centerPanel.add(lblIcon, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(createLabelPair("Equipos:", teamsInfo));
        infoPanel.add(createLabelPair("Jugadores:", playersInfo));
        infoPanel.add(createLabelPair("Fechas:", dateInfo));
        centerPanel.add(infoPanel, BorderLayout.CENTER);
        card.add(centerPanel, BorderLayout.CENTER);

        // Botón "Ver detalles" (si aplica)
        if (showDetailsButton) {
            JButton btnDetails = new JButton("Ver detalles");
            btnDetails.setFont(new Font("Arial", Font.PLAIN, 14));
            btnDetails.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnDetails.putClientProperty("tournamentName", name); // Para identificar el torneo
            if (detailsListener != null) {
                btnDetails.addActionListener(detailsListener);
            }
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setOpaque(false);
            buttonPanel.add(btnDetails);
            card.add(buttonPanel, BorderLayout.SOUTH);
        }
        cardsContainerPanel.add(card);
        cardsContainerPanel.revalidate();
        cardsContainerPanel.repaint();
    }

    private JPanel createLabelPair(String labelText, String valueText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText + " ");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(Color.DARK_GRAY);
        JLabel value = new JLabel(valueText);
        value.setFont(new Font("Arial", Font.PLAIN, 12));
        value.setForeground(Color.GRAY);
        panel.add(label);
        panel.add(value);
        return panel;
    }

    // Método para limpiar y recargar las tarjetas (llamado por el controlador)
    public void refreshTournamentCards(java.util.List<Object[]> tournamentDataList, ActionListener detailsListener) {
        cardsContainerPanel.removeAll();
        for (Object[] data : tournamentDataList) {
            // Asume un orden: name, status, imagePath, teamsInfo, playersInfo, dateInfo, showDetails
            addTournamentCard(
                    (String) data[0], (String) data[1], (String) data[2],
                    (String) data[3], (String) data[4], (String) data[5],
                    (Boolean) data[6], detailsListener
            );
        }
        cardsContainerPanel.revalidate();
        cardsContainerPanel.repaint();
    }
}
package co.edu.unbosque.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer; // Para el callback de "Ver Detalles"

public class EquiposPrincipalPanel extends JPanel {

    private JTextField txtBuscador;
    private JButton btnBuscar;
    private JComboBox<String> comboFiltroTorneo;
    private JComboBox<String> comboFiltroJuego;
    private JComboBox<String> comboFiltroJugadores;
    private JPanel panelTarjetasEquipos;
    private JButton btnCrearNuevoEquipo; // Botón "flotante"

    private Consumer<String> verDetallesCallback; // Callback para mostrar detalles

    public static final String ACCION_CREAR_EQUIPO = "CREAR NUEVO EQUIPO";
    public static final String ACCION_BUSCAR_EQUIPOS = "BUSCAR EQUIPOS";
    public static final String ACCION_VER_DETALLES_EQUIPO = "VER DETALLES EQUIPO_"; // Se le añade el ID


    public EquiposPrincipalPanel(ActionListener generalListener, Consumer<String> verDetallesCallback) {
        this.verDetallesCallback = verDetallesCallback;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        // Panel Superior: Buscador y Filtros
        JPanel panelControlesSuperiores = new JPanel(new BorderLayout(10,10));
        panelControlesSuperiores.setOpaque(false);

        JPanel panelBuscador = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBuscador.setOpaque(false);
        txtBuscador = new JTextField(25);
        txtBuscador.setFont(new Font("Arial", Font.PLAIN, 14));
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnBuscar.setActionCommand(ACCION_BUSCAR_EQUIPOS);
        btnBuscar.addActionListener(generalListener);
        panelBuscador.add(new JLabel("Buscar: "));
        panelBuscador.add(txtBuscador);
        panelBuscador.add(btnBuscar);
        panelControlesSuperiores.add(panelBuscador, BorderLayout.NORTH);

        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.setOpaque(false);
        panelFiltros.setBorder(new TitledBorder("Filtros"));
        comboFiltroTorneo = new JComboBox<>(new String[]{"Torneo Actual...", "Torneo Primavera", "Liga Verano"});
        comboFiltroJuego = new JComboBox<>(new String[]{"Juego Principal...", "Gran Turismo", "FIFA", "Formula 1", "Rocket League"});
        comboFiltroJugadores = new JComboBox<>(new String[]{"Jugadores...", "1-5", "6-10", "11+"});
        // Añadir listeners a los combos si la actualización es automática, o usar un botón "Aplicar Filtros"
        panelFiltros.add(new JLabel("Torneo:"));
        panelFiltros.add(comboFiltroTorneo);
        panelFiltros.add(new JLabel("Juego:"));
        panelFiltros.add(comboFiltroJuego);
        panelFiltros.add(new JLabel("Nº Jugadores:"));
        panelFiltros.add(comboFiltroJugadores);
        panelControlesSuperiores.add(panelFiltros, BorderLayout.CENTER);

        add(panelControlesSuperiores, BorderLayout.NORTH);


        // Panel Central: Tarjetas de Equipos
        panelTarjetasEquipos = new JPanel(new GridLayout(0, 3, 15, 15)); // 3 columnas, filas dinámicas
        panelTarjetasEquipos.setOpaque(false); // El scrollpane tendrá el fondo
        // Datos de ejemplo
        cargarTarjetasDeEjemplo(generalListener);


        JScrollPane scrollPane = new JScrollPane(panelTarjetasEquipos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);


        // Panel Inferior: Botón "flotante" para Crear Nuevo Equipo
        JPanel panelBotonCrear = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonCrear.setOpaque(false);
        btnCrearNuevoEquipo = new JButton("+ Crear Nuevo Equipo");
        btnCrearNuevoEquipo.setFont(new Font("Arial", Font.BOLD, 14));
        btnCrearNuevoEquipo.setBackground(new Color(40, 167, 69)); // Verde
        btnCrearNuevoEquipo.setForeground(Color.WHITE);
        btnCrearNuevoEquipo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCrearNuevoEquipo.setFocusPainted(false);
        btnCrearNuevoEquipo.setBorder(new EmptyBorder(10,15,10,15));
        btnCrearNuevoEquipo.setActionCommand(ACCION_CREAR_EQUIPO);
        btnCrearNuevoEquipo.addActionListener(generalListener);
        panelBotonCrear.add(btnCrearNuevoEquipo);
        add(panelBotonCrear, BorderLayout.SOUTH);
    }

    private void cargarTarjetasDeEjemplo(ActionListener generalListener) {
        // El controller debería pasar estos datos
        String equipoId1 = "EQ001";
        panelTarjetasEquipos.add(crearTarjetaEquipo("Titanes del Teclado", "/imagenes/logo_equipo_1.png", 5, "Bogotá, COL", "Valorant", equipoId1, generalListener));
        String equipoId2 = "EQ002";
        panelTarjetasEquipos.add(crearTarjetaEquipo("Furias Cósmicas", "/imagenes/logo_equipo_2.png", 6, "Medellín, COL", "Rocket League", equipoId2, generalListener));
        String equipoId3 = "EQ003";
        panelTarjetasEquipos.add(crearTarjetaEquipo("Dragones Digitales", "/imagenes/logo_equipo_3.png", 8, "Lima, PER", "FIFA", equipoId3, generalListener));
        String equipoId4 = "EQ004";
        panelTarjetasEquipos.add(crearTarjetaEquipo("CyberLobos", "/imagenes/logo_generic_game.png", 5, "Quito, ECU", "CS:GO", equipoId4, generalListener));
    }

    private JPanel crearTarjetaEquipo(String nombre, String logoPath, int numJugadores, String ubicacion, String juegoPrincipal, String equipoId, ActionListener generalListener) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(new Color(248, 249, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setPreferredSize(new Dimension(250, 320)); // Tamaño preferido para las tarjetas

        // Logo
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            URL imgUrl = getClass().getResource(logoPath);
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(img));
            } else {
                lblLogo.setText("No Logo");
                lblLogo.setPreferredSize(new Dimension(100,100));
                lblLogo.setBorder(new LineBorder(Color.GRAY)); // Borde para visualización
                lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            lblLogo.setText("Err Logo");
            lblLogo.setPreferredSize(new Dimension(100,100));
            lblLogo.setBorder(new LineBorder(Color.RED)); // Borde para visualización
            lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
            System.err.println("Error al cargar logo: " + logoPath + " - " + e.getMessage());
        }
        card.add(lblLogo, BorderLayout.NORTH);

        // Panel de Información
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new EmptyBorder(10,0,10,0));

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(lblNombre);
        infoPanel.add(Box.createRigidArea(new Dimension(0,8)));

        infoPanel.add(crearInfoLabel("Juego: " + juegoPrincipal));
        infoPanel.add(crearInfoLabel(numJugadores + " Jugadores"));
        infoPanel.add(crearInfoLabel(ubicacion));

        card.add(infoPanel, BorderLayout.CENTER);

        // Botón Ver Detalles
        JButton btnDetalles = new JButton("Ver Detalles");
        btnDetalles.setFont(new Font("Arial", Font.PLAIN, 12));
        btnDetalles.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDetalles.setActionCommand(ACCION_VER_DETALLES_EQUIPO + equipoId); // Comando único para este equipo
        btnDetalles.addActionListener(e -> {
            // Al hacer clic, llamamos al callback para cambiar de vista en EquiposPanel
            if (verDetallesCallback != null) {
                verDetallesCallback.accept(equipoId);
            }
        });
        // También podemos añadir el listener general si queremos que el controller también lo escuche.
        // btnDetalles.addActionListener(generalListener);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setOpaque(false);
        southPanel.add(btnDetalles);
        card.add(southPanel, BorderLayout.SOUTH);

        return card;
    }

    private JLabel crearInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.DARK_GRAY);
        return label;
    }

    public String getTerminoBusqueda() {
        return txtBuscador.getText();
    }

    public String getFiltroTorneoSeleccionado() {
        // Devuelve null si "Torneo Actual..." está seleccionado, o el valor real.
        String selected = (String) comboFiltroTorneo.getSelectedItem();
        return "Torneo Actual...".equals(selected) ? null : selected;
    }

    public String getFiltroJuegoSeleccionado() {
        // Devuelve null si "Juego Principal..." está seleccionado, o el valor real.
        String selected = (String) comboFiltroJuego.getSelectedItem();
        return "Juego Principal...".equals(selected) ? null : selected;
    }

    public String getFiltroJugadoresSeleccionado() {
        // Devuelve null si "Jugadores..." está seleccionado, o el valor real.
        String selected = (String) comboFiltroJugadores.getSelectedItem();
        return "Jugadores...".equals(selected) ? null : selected;
    }

    // El controlador llamaría a este método para actualizar las tarjetas
    public void actualizarTarjetasEquipos(List<Object[]> equiposData, ActionListener generalListener) {
        panelTarjetasEquipos.removeAll();
        if (equiposData != null) {
            for (Object[] data : equiposData) {
                // Asumir orden: nombre, logoPath, numJugadores, ubicacion, juegoPrincipal, equipoId
                panelTarjetasEquipos.add(crearTarjetaEquipo(
                        (String) data[0], (String) data[1], (int) data[2],
                        (String) data[3], (String) data[4], (String) data[5],
                        generalListener
                ));
            }
        }
        panelTarjetasEquipos.revalidate();
        panelTarjetasEquipos.repaint();
    }
}
package co.edu.unbosque.view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
public class PartidaDetallePanel extends JPanel {
    private JLabel lblTituloPartida;
    private JButton btnVolver;
    private String partidaIdActual;
    private Consumer<Void> volverCallback;
    private JTextField txtTorneo, txtFaseRonda, txtJuego, txtEquipos, txtModalidad, txtFechaProgramada;
    private JButton btnReprogramar;
    private JTextArea areaParticipantesInfo;
    private JTextField txtResultadoEquipoA, txtResultadoEquipoB;
    private JComboBox<String> comboEstadoResultado;
    private JButton btnAdjuntarEvidencia, btnGuardarResultado, btnConfirmarResultado;
    public static final String ACCION_REPROGRAMAR_FECHA = "REPROGRAMAR_FECHA_PARTIDA_";
    public static final String ACCION_GUARDAR_RESULTADO = "GUARDAR_RESULTADO_PARTIDA_";
    public static final String ACCION_ADJUNTAR_EVIDENCIA = "ADJUNTAR_EVIDENCIA_PARTIDA_";
    public static final String ACCION_CONFIRMAR_RESULTADO_DETALLE = "CONFIRMAR_RESULTADO_DETALLE_PARTIDA_";
    public PartidaDetallePanel(ActionListener generalListener, Consumer<Void> volverCallback) {
        this.volverCallback = volverCallback;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        lblTituloPartida = new JLabel("Detalle de la Partida");
        lblTituloPartida.setFont(new Font("Arial", Font.BOLD, 24));
        btnVolver = new JButton("← Volver a la lista");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 14));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> {
            if (this.volverCallback != null) {
                this.volverCallback.accept(null);
            }
        });
        topPanel.add(lblTituloPartida, BorderLayout.CENTER);
        topPanel.add(btnVolver, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));
        tabbedPane.addTab("Información Básica", null, crearPanelInfoBasica(generalListener), "Datos generales de la partida");
        tabbedPane.addTab("Participantes", null, crearPanelParticipantes(generalListener), "Detalles de los contendientes");
        tabbedPane.addTab("Resultado", null, crearPanelResultado(generalListener), "Registrar y confirmar resultado");
        add(tabbedPane, BorderLayout.CENTER);
    }
    private JComponent crearPanelInfoBasica(ActionListener generalListener) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10,10,10,10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        txtTorneo = createReadOnlyTextField(30);
        txtFaseRonda = createReadOnlyTextField(30);
        txtJuego = createReadOnlyTextField(30);
        txtEquipos = createReadOnlyTextField(30);
        txtModalidad = createReadOnlyTextField(30);
        txtFechaProgramada = createReadOnlyTextField(20);
        btnReprogramar = new JButton("Reprogramar");
        btnReprogramar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnReprogramar.addActionListener(generalListener);
        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Torneo:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.gridwidth = 2; gbc.weightx = 1.0; panel.add(txtTorneo, gbc);
        gbc.weightx = 0.0; gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Fase/Ronda:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.gridwidth = 2; gbc.weightx = 1.0; panel.add(txtFaseRonda, gbc);
        gbc.weightx = 0.0; gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Juego:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.gridwidth = 2; gbc.weightx = 1.0; panel.add(txtJuego, gbc);
        gbc.weightx = 0.0; gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Enfrentamiento:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.gridwidth = 2; gbc.weightx = 1.0; panel.add(txtEquipos, gbc);
        gbc.weightx = 0.0; gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Modalidad:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.gridwidth = 2; gbc.weightx = 1.0; panel.add(txtModalidad, gbc);
        gbc.weightx = 0.0; gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = y; panel.add(new JLabel("Fecha Programada:"), gbc);
        gbc.gridx = 1; gbc.gridy = y; gbc.weightx = 1.0; panel.add(txtFechaProgramada, gbc);
        gbc.gridx = 2; gbc.gridy = y++; gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE; panel.add(btnReprogramar, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.gridwidth = 1;
        gbc.gridy = y++; gbc.gridx = 0; gbc.gridwidth = 3; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(Box.createVerticalGlue(), gbc);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }
    private JTextField createReadOnlyTextField(int columns){
        JTextField textField = new JTextField(columns);
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(new Color(230, 230, 230));
        textField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(3,5,3,5)
        ));
        return textField;
    }
    private JPanel crearPanelParticipantes(ActionListener generalListener) {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10,10,10,10));
        areaParticipantesInfo = new JTextArea(15, 40);
        areaParticipantesInfo.setEditable(false);
        areaParticipantesInfo.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaParticipantesInfo.setText("Información detallada de los participantes...\n- Nombres\n- Avatares/logos\n- Ranking actual\n- Rendimiento previo (resumen)");
        areaParticipantesInfo.setLineWrap(true);
        areaParticipantesInfo.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(areaParticipantesInfo);
        panel.add(new JLabel("Detalles de los Equipos/Jugadores:", SwingConstants.LEFT), BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    private JComponent crearPanelResultado(ActionListener generalListener) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder("Registrar / Ver Resultado"),
                new EmptyBorder(10,10,10,10)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        Font labelFont = new Font("Arial", Font.PLAIN, 13);
        Font fieldFont = new Font("Arial", Font.PLAIN, 13);
        JLabel lblEquipoA = new JLabel("Equipo A:");
        lblEquipoA.setFont(labelFont);
        txtResultadoEquipoA = new JTextField(5);
        txtResultadoEquipoA.setFont(fieldFont);
        JLabel lblEquipoB = new JLabel("Equipo B:");
        lblEquipoB.setFont(labelFont);
        txtResultadoEquipoB = new JTextField(5);
        txtResultadoEquipoB.setFont(fieldFont);
        JLabel lblEstadoRes = new JLabel("Estado del Resultado:");
        lblEstadoRes.setFont(labelFont);
        comboEstadoResultado = new JComboBox<>(new String[]{"Pendiente", "En revisión", "Confirmado"});
        comboEstadoResultado.setFont(fieldFont);
        btnAdjuntarEvidencia = new JButton("Adjuntar Evidencia");
        btnAdjuntarEvidencia.setFont(labelFont);
        btnAdjuntarEvidencia.addActionListener(generalListener);
        btnGuardarResultado = new JButton("Guardar Cambios");
        btnGuardarResultado.setFont(labelFont);
        btnGuardarResultado.addActionListener(generalListener);
        btnConfirmarResultado = new JButton("Confirmar Resultado (Admin)");
        btnConfirmarResultado.setFont(labelFont);
        btnConfirmarResultado.addActionListener(generalListener);
        btnConfirmarResultado.setEnabled(false);
        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; panel.add(lblEquipoA, gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.weightx = 1.0; panel.add(txtResultadoEquipoA, gbc);
        gbc.weightx = 0.0;
        gbc.gridx = 0; gbc.gridy = y; panel.add(lblEquipoB, gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.weightx = 1.0; panel.add(txtResultadoEquipoB, gbc);
        gbc.weightx = 0.0;
        gbc.gridx = 0; gbc.gridy = y; panel.add(lblEstadoRes, gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.weightx = 1.0; panel.add(comboEstadoResultado, gbc);
        gbc.weightx = 0.0;
        gbc.gridy = y++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnAdjuntarEvidencia, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(btnGuardarResultado);
        buttonsPanel.add(btnConfirmarResultado);
        gbc.gridy = y++;
        gbc.gridx = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonsPanel, gbc);
        gbc.gridy = y++; gbc.gridx = 0; gbc.gridwidth = 2; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(Box.createVerticalGlue(), gbc);
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }
    public void cargarDatosPartida(String idPartida) {
        this.partidaIdActual = idPartida;
        lblTituloPartida.setText("Detalle Partida: " + idPartida);
        txtTorneo.setText("Torneo Primavera (Ejemplo)");
        txtFaseRonda.setText("Final (Ejemplo)");
        txtJuego.setText("FIFA (Ejemplo)");
        txtEquipos.setText("Equipo Alfa vs Equipo Beta (Ejemplo)");
        txtModalidad.setText("Online (Ejemplo)");
        txtFechaProgramada.setText("20/05/2025 18:00 (Ejemplo)");
        areaParticipantesInfo.setText(
                "Equipo Alfa (Jugador1, Jugador2)\n" +
                        "  Logo: [LogoAlfa.png]\n" +
                        "  Ranking: #5\n" +
                        "  Rendimiento: G-P-G\n\n" +
                        "Equipo Beta (JugadorX, JugadorY)\n" +
                        "  Logo: [LogoBeta.png]\n" +
                        "  Ranking: #8\n" +
                        "  Rendimiento: P-G-P"
        );
        txtResultadoEquipoA.setText("2");
        txtResultadoEquipoB.setText("1");
        comboEstadoResultado.setSelectedItem("Confirmado");
        btnReprogramar.setActionCommand(ACCION_REPROGRAMAR_FECHA + this.partidaIdActual);
        btnGuardarResultado.setActionCommand(ACCION_GUARDAR_RESULTADO + this.partidaIdActual);
        btnAdjuntarEvidencia.setActionCommand(ACCION_ADJUNTAR_EVIDENCIA + this.partidaIdActual);
        btnConfirmarResultado.setActionCommand(ACCION_CONFIRMAR_RESULTADO_DETALLE + this.partidaIdActual);
        System.out.println("Cargando datos en PartidaDetallePanel para partida ID: " + idPartida);
    }
}
package co.edu.unbosque.view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;
public class PartidasVistaGeneralPanel extends JPanel {
    private JTable tablaPartidas;
    private DefaultTableModel modeloTablaPartidas;
    private JTextField txtBuscadorGlobal;
    private JButton btnBuscar;
    private JComboBox<String> comboFiltroTorneo;
    private JComboBox<String> comboFiltroFecha;
    private JComboBox<String> comboFiltroEstado;
    private JComboBox<String> comboFiltroJuego;
    private JButton btnAgendarPartida;
    private Consumer<String> verDetallesCallback;
    public static final String ACCION_AGENDAR_PARTIDA = "AGENDAR_NUEVA_PARTIDA_GENERAL";
    public static final String ACCION_BUSCAR_PARTIDAS = "BUSCAR_PARTIDAS_GENERAL";
    public static final String ACCION_VER_PARTIDA_TABLA = "VER_PARTIDA_TABLA_";
    public static final String ACCION_EDITAR_PARTIDA_TABLA = "EDITAR_PARTIDA_TABLA_";
    public static final String ACCION_REPORTAR_RESULTADO_TABLA = "REPORTAR_RESULTADO_TABLA_";
    public PartidasVistaGeneralPanel(ActionListener generalListener, Consumer<String> verDetallesCallback) {
        this.verDetallesCallback = verDetallesCallback;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);
        JPanel panelControles = new JPanel(new BorderLayout(10, 5));
        panelControles.setOpaque(false);
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelFiltros.setOpaque(false);
        panelFiltros.setBorder(new TitledBorder("Filtros"));
        comboFiltroTorneo = new JComboBox<>(new String[]{"Todos los Torneos", "Torneo Primavera", "Copa Verano"});
        comboFiltroFecha = new JComboBox<>(new String[]{"Cualquier Fecha", "Hoy", "Próximos 7 días"});
        comboFiltroEstado = new JComboBox<>(new String[]{"Cualquier Estado", "Programada", "En curso", "Finalizada", "Conflicto"});
        comboFiltroJuego = new JComboBox<>(new String[]{"Todos los Juegos", "Gran Turismo", "FIFA", "Formula 1", "Rocket League"});
        panelFiltros.add(new JLabel("Torneo:"));
        panelFiltros.add(comboFiltroTorneo);
        panelFiltros.add(new JLabel("Fecha:"));
        panelFiltros.add(comboFiltroFecha);
        panelFiltros.add(new JLabel("Estado:"));
        panelFiltros.add(comboFiltroEstado);
        panelFiltros.add(new JLabel("Juego:"));
        panelFiltros.add(comboFiltroJuego);
        JPanel panelBusquedaGlobal = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusquedaGlobal.setOpaque(false);
        txtBuscadorGlobal = new JTextField(20);
        txtBuscadorGlobal.setFont(new Font("Arial", Font.PLAIN, 14));
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnBuscar.setActionCommand(ACCION_BUSCAR_PARTIDAS);
        btnBuscar.addActionListener(generalListener);
        panelBusquedaGlobal.add(new JLabel("Búsqueda Global:"));
        panelBusquedaGlobal.add(txtBuscadorGlobal);
        panelBusquedaGlobal.add(btnBuscar);
        panelControles.add(panelFiltros, BorderLayout.CENTER);
        panelControles.add(panelBusquedaGlobal, BorderLayout.SOUTH);
        add(panelControles, BorderLayout.NORTH);
        String[] columnas = {"ID", "Torneo", "Ronda", "Partida", "Fecha/Hora", "Estado", "Resultado", "Acciones"};
        modeloTablaPartidas = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == getColumnCount() -1;
            }
        };
        tablaPartidas = new JTable(modeloTablaPartidas);
        configurarTablaPartidas(generalListener);
        JScrollPane scrollPaneTabla = new JScrollPane(tablaPartidas);
        add(scrollPaneTabla, BorderLayout.CENTER);
        btnAgendarPartida = new JButton("+ Agendar Nueva Partida");
        btnAgendarPartida.setFont(new Font("Arial", Font.BOLD, 14));
        btnAgendarPartida.setBackground(new Color(0, 123, 255));
        btnAgendarPartida.setForeground(Color.WHITE);
        btnAgendarPartida.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgendarPartida.setFocusPainted(false);
        btnAgendarPartida.setBorder(new EmptyBorder(10,15,10,15));
        btnAgendarPartida.setActionCommand(ACCION_AGENDAR_PARTIDA);
        btnAgendarPartida.addActionListener(generalListener);
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setOpaque(false);
        panelSur.add(btnAgendarPartida);
        add(panelSur, BorderLayout.SOUTH);
        cargarPartidasDeEjemplo();
    }
    private void configurarTablaPartidas(ActionListener generalListener) {
        tablaPartidas.setRowHeight(30);
        tablaPartidas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaPartidas.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaPartidas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TableColumnModel tcm = tablaPartidas.getColumnModel();
        tcm.getColumn(0).setMinWidth(0);
        tcm.getColumn(0).setMaxWidth(0);
        tcm.getColumn(0).setPreferredWidth(0);
        ActionButtonsRendererAndEditor actionButtons = new ActionButtonsRendererAndEditor(tablaPartidas, generalListener, verDetallesCallback);
        tcm.getColumn(tablaPartidas.getColumnCount() - 1).setCellRenderer(actionButtons);
        tcm.getColumn(tablaPartidas.getColumnCount() - 1).setCellEditor(actionButtons);
        tcm.getColumn(tablaPartidas.getColumnCount() - 1).setPreferredWidth(220);
        tcm.getColumn(5).setCellRenderer(new EstadoPartidaRenderer());
        tablaPartidas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tablaPartidas.rowAtPoint(e.getPoint());
                int col = tablaPartidas.columnAtPoint(e.getPoint());
                if (row >= 0 && col >= 0) {
                    if (e.getClickCount() == 2 && col != tablaPartidas.getColumnCount() -1) {
                        String partidaId = (String) tablaPartidas.getValueAt(row, 0);
                        if (verDetallesCallback != null) {
                            verDetallesCallback.accept(partidaId);
                        }
                    }
                }
            }
        });
    }
    public String getFiltroTorneo() { return comboFiltroTorneo.getSelectedIndex() > 0 ? (String) comboFiltroTorneo.getSelectedItem() : null; }
    public String getFiltroFecha() { return comboFiltroFecha.getSelectedIndex() > 0 ? (String) comboFiltroFecha.getSelectedItem() : null; }
    public String getFiltroEstado() { return comboFiltroEstado.getSelectedIndex() > 0 ? (String) comboFiltroEstado.getSelectedItem() : null; }
    public String getFiltroJuego() { return comboFiltroJuego.getSelectedIndex() > 0 ? (String) comboFiltroJuego.getSelectedItem() : null; }
    public String getTerminoBusquedaGlobal() { return txtBuscadorGlobal.getText(); }
    private void cargarPartidasDeEjemplo() {
        Object[] fila1 = {"P001", "Torneo Primavera", "Cuartos", "Equipo Alfa vs Equipo Beta", "20/05/2025 18:00", "Programada", "-", "Acciones"};
        Object[] fila2 = {"P002", "Copa Verano", "Semis", "Titanes vs Dragones", "22/05/2025 20:00", "En curso", "1-1", "Acciones"};
        Object[] fila3 = {"P003", "Torneo Primavera", "Final", "Equipo Gamma vs Equipo Delta", "15/05/2025 16:00", "Finalizada", "2-0", "Acciones"};
        Object[] fila4 = {"P004", "Liga Estelar", "Grupo A", "Fénix vs Centauros", "25/05/2025 10:00", "Conflicto", "???", "Acciones"};
        modeloTablaPartidas.addRow(fila1);
        modeloTablaPartidas.addRow(fila2);
        modeloTablaPartidas.addRow(fila3);
        modeloTablaPartidas.addRow(fila4);
    }
    public void actualizarTablaPartidas(List<Object[]> datosPartidas) {
        modeloTablaPartidas.setRowCount(0);
        if (datosPartidas != null) {
            for (Object[] fila : datosPartidas) {
                if (fila.length == modeloTablaPartidas.getColumnCount()) {
                    modeloTablaPartidas.addRow(fila);
                } else {
                    System.err.println("Error en PartidasPrincipalPanel: El número de datos en la fila no coincide con el número de columnas.");
                }
            }
        }
    }
}
class ActionButtonsRendererAndEditor extends AbstractCellEditor implements javax.swing.table.TableCellRenderer, javax.swing.table.TableCellEditor {
    private JPanel panel;
    private JButton btnVer, btnEditar, btnReportar;
    private JTable tabla;
    private ActionListener generalListener;
    private Consumer<String> verDetallesCallback;
    private String currentPartidaId;
    public ActionButtonsRendererAndEditor(JTable table, ActionListener generalListener, Consumer<String> verDetallesCallback) {
        this.tabla = table;
        this.generalListener = generalListener;
        this.verDetallesCallback = verDetallesCallback;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));
        panel.setOpaque(true);
        Font buttonFont = new Font("Arial", Font.PLAIN, 10);
        Dimension buttonSize = new Dimension(70, 20);
        btnVer = new JButton("Ver");
        btnVer.setFont(buttonFont);
        btnVer.setMargin(new Insets(2,2,2,2));
        btnVer.setPreferredSize(buttonSize);
        btnVer.addActionListener(e -> {
            fireEditingStopped();
            if (this.verDetallesCallback != null && currentPartidaId != null) {
                this.verDetallesCallback.accept(currentPartidaId);
            }
        });
        btnEditar = new JButton("Editar");
        btnEditar.setFont(buttonFont);
        btnEditar.setMargin(new Insets(2,2,2,2));
        btnEditar.setPreferredSize(buttonSize);
        btnEditar.addActionListener(e -> {
            fireEditingStopped();
            if (this.generalListener != null && currentPartidaId != null) {
                this.generalListener.actionPerformed(new ActionEvent(btnEditar, ActionEvent.ACTION_PERFORMED, PartidasVistaGeneralPanel.ACCION_EDITAR_PARTIDA_TABLA + currentPartidaId));
            }
        });
        btnReportar = new JButton("Reportar");
        btnReportar.setFont(buttonFont);
        btnReportar.setMargin(new Insets(2,2,2,2));
        btnReportar.setPreferredSize(buttonSize);
        btnReportar.addActionListener(e -> {
            fireEditingStopped();
            if (this.generalListener != null && currentPartidaId != null) {
                this.generalListener.actionPerformed(new ActionEvent(btnReportar, ActionEvent.ACTION_PERFORMED, PartidasVistaGeneralPanel.ACCION_REPORTAR_RESULTADO_TABLA + currentPartidaId));
            }
        });
        panel.add(btnVer);
        panel.add(btnEditar);
        panel.add(btnReportar);
    }
    private void updateData(JTable table, Object value, boolean isSelected, int row) {
        currentPartidaId = (String) table.getValueAt(row, 0);
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        updateData(table, value, isSelected, row);
        return panel;
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        updateData(table, value, isSelected, row);
        return panel;
    }
    @Override
    public Object getCellEditorValue() {
        return currentPartidaId;
    }
}
class EstadoPartidaRenderer extends DefaultTableCellRenderer {
    private static final String ICONO_PROGRAMADA = "🕒";
    private static final String ICONO_EN_CURSO = "⏳";
    private static final String ICONO_FINALIZADA = "✔️";
    private static final String ICONO_CONFLICTO = "⚠️";
    public EstadoPartidaRenderer() {
        setHorizontalAlignment(JLabel.LEFT);
        setOpaque(true);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        String estado = (String) value;
        this.setText("");
        this.setIcon(null);
        if (estado != null) {
            switch (estado.toLowerCase()) {
                case "programada":
                    this.setText(ICONO_PROGRAMADA + " Programada");
                    this.setForeground(new Color(0, 100, 200));
                    break;
                case "en curso":
                    this.setText(ICONO_EN_CURSO + " En curso");
                    this.setForeground(new Color(204, 129, 0));
                    break;
                case "finalizada":
                    this.setText(ICONO_FINALIZADA + " Finalizada");
                    this.setForeground(new Color(0, 128, 0));
                    break;
                case "conflicto":
                    this.setText(ICONO_CONFLICTO + " Conflicto");
                    this.setForeground(Color.RED);
                    break;
                default:
                    this.setText(estado);
                    this.setForeground(table.getForeground());
                    break;
            }
        }
        if (isSelected) {
            this.setBackground(table.getSelectionBackground());
        } else {
            this.setBackground(table.getBackground());
        }
        return this;
    }
}
package co.edu.unbosque.view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
public class EquipoDetallePanel extends JPanel {
    private JLabel lblNombreEquipo;
    private JButton btnVolver;
    private String equipoIdActual;
    private Consumer<Void> volverCallback;
    private JPanel panelInfoGeneral;
    private JPanel panelJugadores;
    private JPanel panelEntrenadores;
    private JPanel panelHistorial;
    public static final String ACCION_EDITAR_EQUIPO = "EDITAR_EQUIPO_";
    public static final String ACCION_GUARDAR_EQUIPO = "GUARDAR_EQUIPO_";
    public static final String ACCION_ELIMINAR_EQUIPO = "ELIMINAR_EQUIPO_";
    public static final String ACCION_AGREGAR_JUGADOR = "AGREGAR_JUGADOR_A_EQUIPO_";
    public EquipoDetallePanel(ActionListener generalListener, Consumer<Void> volverCallback) {
        this.volverCallback = volverCallback;
        setLayout(new BorderLayout(10,10));
        setBorder(new EmptyBorder(15,15,15,15));
        setBackground(Color.WHITE);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        lblNombreEquipo = new JLabel("Detalle del Equipo");
        lblNombreEquipo.setFont(new Font("Arial", Font.BOLD, 24));
        btnVolver = new JButton("← Volver a la lista");
        btnVolver.addActionListener(e -> {
            if (this.volverCallback != null) {
                this.volverCallback.accept(null);
            }
        });
        topPanel.add(lblNombreEquipo, BorderLayout.CENTER);
        topPanel.add(btnVolver, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));
        panelInfoGeneral = new JPanel(new BorderLayout());
        panelInfoGeneral.add(new JLabel("Aquí irá la información general editable del equipo.", SwingConstants.CENTER), BorderLayout.CENTER);
        JButton btnEditarEquipo = new JButton("Editar Equipo");
        btnEditarEquipo.setActionCommand(ACCION_EDITAR_EQUIPO + equipoIdActual);
        btnEditarEquipo.addActionListener(generalListener);
        panelInfoGeneral.add(btnEditarEquipo, BorderLayout.SOUTH);
        tabbedPane.addTab("Información General", panelInfoGeneral);
        panelJugadores = new JPanel();
        panelJugadores.add(new JLabel("Aquí irá la tabla de jugadores del equipo."));
        tabbedPane.addTab("Jugadores", panelJugadores);
        panelEntrenadores = new JPanel();
        panelEntrenadores.add(new JLabel("Aquí irá la información de entrenadores."));
        tabbedPane.addTab("Entrenador(es)", panelEntrenadores);
        panelHistorial = new JPanel();
        panelHistorial.add(new JLabel("Aquí irá el historial competitivo del equipo."));
        tabbedPane.addTab("Historial Competitivo", panelHistorial);
        add(tabbedPane, BorderLayout.CENTER);
    }
    public void cargarDatosEquipo(String idEquipo) {
        this.equipoIdActual = idEquipo;
        lblNombreEquipo.setText("Detalle: Equipo ID " + idEquipo);
        if(panelInfoGeneral.getComponentCount() > 1 && panelInfoGeneral.getComponent(1) instanceof JButton){
            JButton btnEditar = (JButton) panelInfoGeneral.getComponent(1);
            btnEditar.setActionCommand(ACCION_EDITAR_EQUIPO + this.equipoIdActual);
        }
        System.out.println("Cargando datos para el equipo: " + idEquipo);
    }
}
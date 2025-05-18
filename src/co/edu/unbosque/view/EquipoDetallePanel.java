package co.edu.unbosque.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer; // Para el callback de volver

public class EquipoDetallePanel extends JPanel {

    private JLabel lblNombreEquipo;
    private JButton btnVolver;
    private String equipoIdActual; // Para saber qué equipo mostrar/editar
    private Consumer<Void> volverCallback; // Callback para volver a la vista general

    // Paneles para las secciones A, B, C, D
    private JPanel panelInfoGeneral;
    private JPanel panelJugadores;
    private JPanel panelEntrenadores;
    private JPanel panelHistorial;

    // Constantes para acciones internas
    public static final String ACCION_EDITAR_EQUIPO = "EDITAR_EQUIPO_"; // + equipoId
    public static final String ACCION_GUARDAR_EQUIPO = "GUARDAR_EQUIPO_"; // + equipoId
    public static final String ACCION_ELIMINAR_EQUIPO = "ELIMINAR_EQUIPO_"; // + equipoId
    public static final String ACCION_AGREGAR_JUGADOR = "AGREGAR_JUGADOR_A_EQUIPO_"; // + equipoId
    // ... etc.


    public EquipoDetallePanel(ActionListener generalListener, Consumer<Void> volverCallback) {
        this.volverCallback = volverCallback;
        setLayout(new BorderLayout(10,10));
        setBorder(new EmptyBorder(15,15,15,15));
        setBackground(Color.WHITE);

        // Panel Superior: Título y Botón Volver
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

        // Contenedor Principal para las pestañas o secciones
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 14));

        // Sección A: Información General (Placeholder)
        panelInfoGeneral = new JPanel(new BorderLayout());
        panelInfoGeneral.add(new JLabel("Aquí irá la información general editable del equipo.", SwingConstants.CENTER), BorderLayout.CENTER);
        JButton btnEditarEquipo = new JButton("Editar Equipo");
        btnEditarEquipo.setActionCommand(ACCION_EDITAR_EQUIPO + equipoIdActual); // El ID se debe setear en cargarDatosEquipo
        btnEditarEquipo.addActionListener(generalListener);
        panelInfoGeneral.add(btnEditarEquipo, BorderLayout.SOUTH); // Ejemplo
        tabbedPane.addTab("Información General", panelInfoGeneral);

        // Sección B: Jugadores (Placeholder)
        panelJugadores = new JPanel();
        panelJugadores.add(new JLabel("Aquí irá la tabla de jugadores del equipo."));
        tabbedPane.addTab("Jugadores", panelJugadores);

        // Sección C: Entrenadores (Placeholder)
        panelEntrenadores = new JPanel();
        panelEntrenadores.add(new JLabel("Aquí irá la información de entrenadores."));
        tabbedPane.addTab("Entrenador(es)", panelEntrenadores);

        // Sección D: Historial Competitivo (Placeholder)
        panelHistorial = new JPanel();
        panelHistorial.add(new JLabel("Aquí irá el historial competitivo del equipo."));
        tabbedPane.addTab("Historial Competitivo", panelHistorial);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public void cargarDatosEquipo(String idEquipo) {
        this.equipoIdActual = idEquipo;
        lblNombreEquipo.setText("Detalle: Equipo ID " + idEquipo); // Actualizar con nombre real
        // Aquí iría la lógica para cargar los datos del equipo con 'idEquipo'
        // y popular los campos de las diferentes secciones/pestañas.
        // Por ejemplo, el botón de editar ahora puede tener el action command correcto:
        if(panelInfoGeneral.getComponentCount() > 1 && panelInfoGeneral.getComponent(1) instanceof JButton){ // Asumiendo que el botón es el segundo componente
            JButton btnEditar = (JButton) panelInfoGeneral.getComponent(1);
            btnEditar.setActionCommand(ACCION_EDITAR_EQUIPO + this.equipoIdActual);
            // Asegúrate que el listener general esté añadido si lo manejará el controller.
        }
        System.out.println("Cargando datos para el equipo: " + idEquipo);
    }
}
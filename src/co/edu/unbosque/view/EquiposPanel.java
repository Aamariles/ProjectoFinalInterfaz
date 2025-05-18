package co.edu.unbosque.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener; // Para pasar listeners

public class EquiposPanel extends JPanel {

    public static final String VISTA_GENERAL_EQUIPOS = "VISTA_GENERAL_EQUIPOS";
    public static final String VISTA_DETALLE_EQUIPO = "VISTA_DETALLE_EQUIPO";

    private CardLayout cardLayout;
    private EquiposPrincipalPanel equiposPrincipalPanel;
    private EquipoDetallePanel equipoDetallePanel; // Se desarrollará más adelante

    public EquiposPanel(ActionListener generalListener) { // El listener para acciones generales (ej. "Crear Equipo", "Ver Detalles")
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setBackground(new Color(240, 242, 245));

        equiposPrincipalPanel = new EquiposPrincipalPanel(generalListener, this::mostrarDetalleEquipo);
        add(equiposPrincipalPanel, VISTA_GENERAL_EQUIPOS);

        equipoDetallePanel = new EquipoDetallePanel(generalListener, v -> this.mostrarVistaGeneral());
        add(equipoDetallePanel, VISTA_DETALLE_EQUIPO);

        mostrarVistaGeneral();
    }

    public void mostrarVistaGeneral() {
        cardLayout.show(this, VISTA_GENERAL_EQUIPOS);
    }

    // Este método será llamado por EquiposPrincipalPanel cuando se haga clic en "Ver Detalles"
    public void mostrarDetalleEquipo(String idEquipo) { // O un objeto Equipo
        System.out.println("EquiposPanel: Mostrar detalles para equipo ID: " + idEquipo);
        if (equipoDetallePanel != null) {
            equipoDetallePanel.cargarDatosEquipo(idEquipo); // El panel de detalle carga los datos
            cardLayout.show(this, VISTA_DETALLE_EQUIPO);
        } else {
            System.err.println("Error: equipoDetallePanel no está inicializado.");
        }
    }

    // Métodos para que el controlador actualice datos si es necesario
    public EquiposPrincipalPanel getEquiposPrincipalPanel() {
        return equiposPrincipalPanel;
    }

    public EquipoDetallePanel getEquipoDetallePanel() {
        return equipoDetallePanel;
    }
}
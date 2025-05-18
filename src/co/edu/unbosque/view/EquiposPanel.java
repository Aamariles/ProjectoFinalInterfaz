package co.edu.unbosque.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
public class EquiposPanel extends JPanel {
    public static final String PANEL_EQUIPOS = "VISTA_GENERAL_EQUIPOS";
    public static final String VISTA_DETALLE_EQUIPO = "VISTA_DETALLE_EQUIPO";
    private CardLayout cardLayout;
    private EquiposPrincipalPanel equiposPrincipalPanel;
    private EquipoDetallePanel equipoDetallePanel;
    public EquiposPanel(ActionListener generalListener) {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setBackground(new Color(240, 242, 245));
        equiposPrincipalPanel = new EquiposPrincipalPanel(generalListener, this::mostrarDetalleEquipo);
        add(equiposPrincipalPanel, PANEL_EQUIPOS);
        equipoDetallePanel = new EquipoDetallePanel(generalListener, v -> this.mostrarPanelEquipos());
        add(equipoDetallePanel, VISTA_DETALLE_EQUIPO);
        mostrarPanelEquipos();
    }
    public void mostrarPanelEquipos() {
        cardLayout.show(this, PANEL_EQUIPOS);
    }
    public void mostrarDetalleEquipo(String idEquipo) {
        System.out.println("EquiposPanel: Mostrar detalles para equipo ID: " + idEquipo);
        if (equipoDetallePanel != null) {
            equipoDetallePanel.cargarDatosEquipo(idEquipo);
            cardLayout.show(this, VISTA_DETALLE_EQUIPO);
        } else {
            System.err.println("Error: equipoDetallePanel no está inicializado.");
        }
    }
    public EquiposPrincipalPanel getEquiposPrincipalPanel() {
        return equiposPrincipalPanel;
    }
    public EquipoDetallePanel getEquipoDetallePanel() {
        return equipoDetallePanel;
    }
}
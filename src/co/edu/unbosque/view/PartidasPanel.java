package co.edu.unbosque.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
public class PartidasPanel extends JPanel {
    public static final String VISTA_GENERAL_PARTIDAS = "VISTA_GENERAL_PARTIDAS";
    public static final String VISTA_DETALLE_PARTIDA = "VISTA_DETALLE_PARTIDA";
    private CardLayout cardLayout;
    private PartidasVistaGeneralPanel vistaGeneralPanel;
    private PartidaDetallePanel detallePartidaPanel;
    public PartidasPanel(ActionListener generalListener) {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setBackground(new Color(240, 242, 245));
        vistaGeneralPanel = new PartidasVistaGeneralPanel(generalListener, this::mostrarDetallePartida);
        add(vistaGeneralPanel, VISTA_GENERAL_PARTIDAS);
        detallePartidaPanel = new PartidaDetallePanel(generalListener, v -> this.mostrarVistaGeneral());
        add(detallePartidaPanel, VISTA_DETALLE_PARTIDA);
        mostrarVistaGeneral();
    }
    public void mostrarVistaGeneral() {
        cardLayout.show(this, VISTA_GENERAL_PARTIDAS);
    }
    public void mostrarDetallePartida(String idPartida) {
        System.out.println("PartidasPanel: Mostrar detalles para partida ID: " + idPartida);
        if (detallePartidaPanel != null) {
            detallePartidaPanel.cargarDatosPartida(idPartida);
            cardLayout.show(this, VISTA_DETALLE_PARTIDA);
        } else {
            System.err.println("Error: detallePartidaPanel no está inicializado.");
        }
    }
    public PartidasVistaGeneralPanel getVistaGeneralPanel() {
        return vistaGeneralPanel;
    }
    public PartidaDetallePanel getDetallePartidaPanel() {
        return detallePartidaPanel;
    }
}
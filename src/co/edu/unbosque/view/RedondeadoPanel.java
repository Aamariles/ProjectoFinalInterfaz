package co.edu.unbosque.view;

import javax.swing.*;
import java.awt.*;

public class RedondeadoPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius = 15; // Radio de esquina por defecto

    // Constructor que permite especificar el Layout, radio y color de fondo
    public RedondeadoPanel(LayoutManager layout, int radius, Color bgColor) {
        super(layout);
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        setOpaque(false); // Hacemos el panel no opaco para dibujar nuestro propio fondo
    }

    // Constructor que permite especificar solo el radio y color de fondo (usa FlowLayout por defecto)
    public RedondeadoPanel(int radius, Color bgColor) {
        super();
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Para bordes suaves

        // Dibuja el rectángulo redondeado con el color de fondo especificado.
        if (backgroundColor != null) {
            graphics.setColor(backgroundColor);
        } else {
            // Si no se especifica color, usa el color de fondo por defecto del JPanel (aunque es no opaco)
            graphics.setColor(getBackground());
        }
        // Rellena el rectángulo redondeado.
        // Los -1 en width y height son para asegurar que el borde (si se dibujara) quede dentro de los límites.
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

        // Opcional: Si quisieras un borde alrededor del panel redondeado, descomenta esto:
        // graphics.setColor(Color.GRAY); // O el color de borde que prefieras
        // graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }
}
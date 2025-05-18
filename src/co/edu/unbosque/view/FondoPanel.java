package co.edu.unbosque.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class FondoPanel extends JPanel {

    private Image backgroundImage;

    public FondoPanel(String imagePath) {

            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                backgroundImage = new ImageIcon(imageUrl).getImage();

            }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {

            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
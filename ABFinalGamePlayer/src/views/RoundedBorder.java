package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;

public class RoundedBorder implements Border {

    private int radius;
    private String colorCode;

    RoundedBorder(int radius, String colorCode) {
        this.radius = radius;
        this.colorCode = colorCode;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setStroke(new BasicStroke(4));
    	g2.setColor(Color.decode(colorCode));
        Shape shape = new RoundRectangle2D.Float(x, y, width, height, radius, radius);
        g2.draw(shape);
    }
}

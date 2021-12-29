package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;

public class RoundJTextField extends JTextField {
    private Shape shape;
    public RoundJTextField(int size) {
        super(size);
    }

    protected void paintBorder(Graphics g) {
         g.setColor(getForeground());
         Graphics2D g2 = (Graphics2D)g;
         g2.setStroke(new BasicStroke(2));
         g2.setColor(Color.decode("#ffffff"));
         g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
    }
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 10, 10);
         }
         return shape.contains(x, y);
    }
}
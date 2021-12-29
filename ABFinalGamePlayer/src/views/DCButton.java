package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.JButton;

public class DCButton extends JButton{
	
	public DCButton(String colorCode, String text) {
		setBackground(Color.decode(colorCode));
		setText(text);
		setLayout(new BorderLayout());
		setBorderPainted(true);
		setFont(new Font("Oswald", Font.PLAIN, 12));
		setFocusPainted(false);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	} 
}

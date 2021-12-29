package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import controller.Actions;

public class FormularyDialog extends JDialog{
	private RoundJTextField nameField;
	private JLabel daysLabel, xFieldLabel;
	private RoundJTextField codeField;
	private DCButton acceptB, cancelB;
	private LabelPanel labelPanel;
	
	public FormularyDialog(ActionListener controller) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/img/Station.png")).getImage());
		locate(); 
		this.setUIManager();
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		initComponents(controller);
		setVisible(true);
		setModal(true); 
	}
	 
	private void initComponents(ActionListener controller) {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
		labelPanel = new LabelPanel("<html><p style=\"text-align: center;\">SIGN IN</p></html");
		
		codeField = new RoundJTextField(12);
		codeField.setFont(new Font("Oswald", Font.PLAIN, 18));
		codeField.setText("");
		codeField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		codeField.setHorizontalAlignment(JTextField.CENTER);
				
		nameField = new RoundJTextField(12);
		nameField.setFont(new Font("Oswald", Font.PLAIN, 18));
		nameField.setText("localHost");
		nameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		nameField.setHorizontalAlignment(JTextField.CENTER);
		
		codeField.setOpaque(true);
		nameField.setOpaque(true);
		
		daysLabel = new JLabel("NAME :");
		daysLabel.setHorizontalAlignment(JLabel.CENTER);
		xFieldLabel = new JLabel("IP :");
		xFieldLabel.setHorizontalAlignment(JLabel.CENTER);
		acceptB = new DCButton("#343A3C", "SIGN IN");
		acceptB.setActionCommand(Actions.SIGN_IN_BUTTON.name());
		acceptB.addActionListener(controller);
		acceptB.setForeground(Color.decode("#17CD47"));
		cancelB = new DCButton("#343A3C", "CANCEL");
//		cancelB.setActionCommand(Actions.CANCEL_FORMULARY_BUTTON.name());
		cancelB.addActionListener(controller);
		cancelB.setForeground(Color.decode("#EF2D2D"));
		
		JPanel xFieldLabelPanel = new JPanel();
		xFieldLabelPanel.setLayout(new BorderLayout());
		xFieldLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		JPanel xFieldPanel = new JPanel();
		xFieldPanel.setLayout(new GridLayout(2, 1));
		xFieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 100));
		JPanel daysLabelPanel = new JPanel();
		daysLabelPanel.setLayout(new BorderLayout());
		daysLabelPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
		JPanel daysBoxPanel = new JPanel();
		daysBoxPanel.setLayout(new GridLayout(2,1));
		daysBoxPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 100));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 100, 20, 100));
		
		xFieldLabelPanel.add(xFieldLabel);
		xFieldPanel.add(xFieldLabelPanel);
		xFieldPanel.add(nameField);
		
		daysLabelPanel.add(daysLabel);
		daysBoxPanel.add(daysLabelPanel);
		daysBoxPanel.add(codeField);
		
		buttonsPanel.add(acceptB);
		buttonsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		buttonsPanel.add(cancelB);
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		mainPanel.add(labelPanel, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		mainPanel.add(xFieldPanel, c);

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		mainPanel.add(daysBoxPanel, c);

		c.gridx = 0; 
		c.gridy = 4;
		c.weightx = 1;
		c.weighty = 2;
		c.gridheight = 2;
		c.insets = new Insets(30, 0, 0, 0);
		c.fill = GridBagConstraints.VERTICAL;
		mainPanel.add(buttonsPanel, c);
		
		add(mainPanel);
	}
	
	private void setUIManager() {
		UIManager.put("TextField.border", new RoundedBorder(30,"#6C8BAB"));
		UIManager.put("Label.font", new Font("Oswald", Font.BOLD, 15));
		UIManager.put("Panel.background", Color.decode("#262B2C"));
		UIManager.put("Label.foreground", Color.decode("#8F969B"));
	}
	
	private void locate() {
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)screenDimension.getWidth()/3, (int)screenDimension.getHeight()/2);
		setLocation((int)(screenDimension.getWidth()/2 - this.getWidth()/2),
				(int)(screenDimension.getHeight()/2 - this.getHeight()/2 - screenDimension.getHeight()/20));
	}
	
	public String getNameFieldData() {
		return codeField.getText();
	}
	
	public String getIPFieldData() {
		return nameField.getText();
	}
}

package views;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import controller.PlayerEngine;
import models.Game;

public class MainFrame extends JFrame{

	private GamePanel gamePanel;
	
	public MainFrame(PlayerEngine playerEngine) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/img/Station.png")).getImage());
		initComponents(playerEngine);
		locate();
		setVisible(true);
	}
	
	private void initComponents(PlayerEngine playerEngine) {
		gamePanel = new GamePanel(playerEngine);
		add(gamePanel);
	}
	
	public void refreshGamePanel(Game game) {
		gamePanel.refresh(game);
	}
	
	public void locate() {
		int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(width/4,2*height/3);
		setLocation(width/2 - getWidth()/2, height/2 - getHeight()/2);
//		setExtendedState(MAXIMIZED_BOTH);
	}
	
	public void setExplosion(boolean explosion)  {
		gamePanel.setExplosion(explosion);
	}

	public void readuceLifeBar() {
		gamePanel.reduceLifeBar();
	}

	public void showEndAdvice() {
		gamePanel.showEndAdvice();
	}

	public void initHitsOnStation(byte hitsOnStation) {
		gamePanel.initHitsOnStation(hitsOnStation);
	}
}

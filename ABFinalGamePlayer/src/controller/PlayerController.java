package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import deco.JSONManager;
import models.Game;
import network.PlayerListener;
import network.PlayerNetwork;
import views.FormularyDialog;
import views.MainFrame;

public class PlayerController implements ActionListener{
	
	private JSONManager json;
	private FormularyDialog formDialog;
	private Game game;
	private PlayerNetwork playerNetwork;
	private MainFrame mainFrame;
	private PlayerEngine playerEngine;
	private PlayerListener playerListener;
	private boolean playing;
	private String playerName;
	private Timer explosionTimer;
	
	public PlayerController() {
		json = new JSONManager();
//		initNetwork();
		formDialog = new FormularyDialog(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (Actions.valueOf(e.getActionCommand())) {
		case SIGN_IN_BUTTON:
			initNetwork(); 
			break;
		}
	} 
	
	private void initNetwork() {
		playerListener = new PlayerListener(this);
//		playerNetwork = new PlayerNetwork(playerListener, "localhost");
//		playerNetwork.askForJoin("Samuel");
		playerNetwork = new PlayerNetwork(playerListener, formDialog.getIPFieldData());
		playerName = playerNetwork.askForJoin(formDialog.getNameFieldData());
		formDialog.dispose();
	}
	
	private void refreshGUI() {
		mainFrame.refreshGamePanel(game);
	}

	public void startGame() {
		if(!playing) {
			playerEngine = new PlayerEngine(playerNetwork);
			game = new Game();
			game.setName(playerName);
			mainFrame = new MainFrame(playerEngine);
			playing = true;
		}
	}

	public void updateAliensList(String aliensListAsJson) {
		if(playing) {
			game.updateAliensList(json.decodeAliensList(aliensListAsJson));
			refreshGUI();
		}
	}

	public void updatePlayers(String playersListAsJson) {
		if(playing) {
			playerEngine.setPlayer(game.updatePlayers(json.decodePlayersList(playersListAsJson)));
			refreshGUI();
		}
	}

	public void updateBulletsList(String bulletsListAsJson) {
		if(playing) {
			game.updateBulletsList(json.decodeBulletsList(bulletsListAsJson));
			refreshGUI();
		}
	}

	public void initExplosion() {
		playerEngine.playExplosionSound();
		mainFrame.setExplosion(true);
		refreshGUI();
		explosionTimer = new Timer(400, new ActionListener() {
			byte count = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(count == 1) {
					mainFrame.setExplosion(false);
					refreshGUI();
					explosionTimer.stop();
				}
				count +=1;
			}
		});
		if(explosionTimer.isRunning()) {
			refreshGUI();
			explosionTimer.restart();
		} else {
			refreshGUI();
		explosionTimer.start();
		}
	}

	public void reduceLifeBar() {
		mainFrame.readuceLifeBar();
	}

	public void endGame() {
		explosionTimer.stop();
		mainFrame.showEndAdvice();
	}

	public void initHitsOnStation(byte hitsOnStation) {
		mainFrame.initHitsOnStation(hitsOnStation);
	}
}

package network;

import controller.PlayerController;

public class PlayerListener {

	private PlayerController controller;
	
	public PlayerListener(PlayerController controller) {
		this.controller = controller;
	}

	public void startGame() {
		controller.startGame();
	}

	public void updateAliensList(String aliensListAsJson) {
		controller.updateAliensList(aliensListAsJson);
	}

	public void updatePlayers(String playersListAsJson) {
		controller.updatePlayers(playersListAsJson);
	}

	public void updateBullets(String bulletsListAsJson) {
		controller.updateBulletsList(bulletsListAsJson);
	}

	public void initExplosion() {
		controller.initExplosion();
		controller.reduceLifeBar();
	}

	public void endGame() {
		controller.endGame();
	}

	public void updateHitsOnStation(byte hitsOnStation) {
		controller.initHitsOnStation(hitsOnStation);
	}
}

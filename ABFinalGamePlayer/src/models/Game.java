package models;

import java.util.concurrent.CopyOnWriteArrayList;

public class Game {
	
	private CopyOnWriteArrayList<Alien> aliensList;
	private CopyOnWriteArrayList<Player> playersList;
	private CopyOnWriteArrayList<Bullet> bulletsList;
	private String yourName;
	
	public Game() {
		initLists();
	}

	private void initLists() {
		aliensList = new CopyOnWriteArrayList<>(); 
		playersList = new CopyOnWriteArrayList<>();
		bulletsList = new CopyOnWriteArrayList<>();
	}
	
	public void updateAliensList(CopyOnWriteArrayList<Alien> decodedAliensList) {
		aliensList = decodedAliensList; 
	}

	public Player updatePlayers(CopyOnWriteArrayList<Player> playersList) {
		Player youPlayer = new Player();
		this.playersList = playersList;
		for (Player player : playersList) {
			if(player.getNick().equalsIgnoreCase(yourName)) {
				youPlayer = player;
			}
		}
		return youPlayer;
	}

	public void updateBulletsList(CopyOnWriteArrayList<Bullet> decodedBulletsList) {
		bulletsList = decodedBulletsList;
	}
	
	public CopyOnWriteArrayList<Alien> getAliensList() {
		return aliensList;
	}
	
	public CopyOnWriteArrayList<Player> getPlayersList() {
		return playersList;
	}
	
	public CopyOnWriteArrayList<Bullet> getBulletsList() {
		return bulletsList;
	}
	
	public String getName() {
		return yourName;
	}
	
	public void setName(String nick) {
		yourName = nick;
	}
}

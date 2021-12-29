package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PlayerNetwork {

	private Socket socket;
	private DataOutputStream outputStream;
	private DataInputStream inputStream;
	private PlayerListener playerListener;
	private boolean playerUp;

	public PlayerNetwork(PlayerListener clientListener, String ip) {
		this.playerListener = clientListener;
		initComponents(ip);
		updateConcertsList();
	}

	private void initComponents(String ip) {
		try {
			socket = new Socket(ip, 3016);
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
			playerUp = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateConcertsList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (playerUp) {
					try {
						if (inputStream.available() > 0) {
							String string = readUTF();
							switch (Requests.valueOf(string)) {
							case GAME_STARTED:
								startGame();
								break;
							case SERVER_TO_CLIENT_ALIENS:
								updateAliensList();
								break;
							case SERVER_TO_CLIENT_TOKENS:
								updatePlayers();
								break;
							case SERVER_TO_CLIENT_BULLETS:
								updateBullets();
								break;
							case INIT_EXPLOSION:
								initExplosion();
								break;
							case GAME_ENDED:
								endGame();
								break;
							case HITS_ON_STATION:
								initHitsOnStation();
								break;
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private void startGame() {
		playerListener.startGame();
	}
	
	private void endGame() {
		playerListener.endGame();
	}

	private void updateAliensList() {
		playerListener.updateAliensList(readUTF());
	}

	private void updatePlayers() {
		playerListener.updatePlayers(readUTF());
	}

	private void updateBullets() {
		playerListener.updateBullets(readUTF());
	}
	
	private void initExplosion() {
		playerListener.initExplosion();
	}
	
	private void initHitsOnStation() {
		playerListener.updateHitsOnStation(readByte());
	}

	// ============================================== RESPONSES ============================================

	public String askForJoin(String nick) {
		writeUTF(Responses.ASK_FOR_JOIN.toString());
		writeUTF(nick);
		return nick;
	}

	public void sendShot(int x, int y) {
		writeUTF(Responses.SHOT.toString());
		writeInt(x);
		writeInt(y);
	}
	
	public void sendRotation(double degree, boolean inversed) {
		writeUTF(Responses.ROTATION.toString());
		writeDouble(degree);
		writeBoolean(inversed);		
	}

	public void moveUp() {
		writeUTF(Responses.MOVE_UP.toString());
	}

	public void moveDown() {
		writeUTF(Responses.MOVE_DOWN.toString());
	}

	public void moveRight() {
		writeUTF(Responses.MOVE_RIGHT.toString());
	}

	public void moveLeft() {
		writeUTF(Responses.MOVE_LEFT.toString());
	}

	// ============================================= UTILITIES ============================================

	private void writeUTF(String string) {
		try {
			outputStream.writeUTF(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeInt(int number) {
		try {
			outputStream.writeInt(number);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeBoolean(boolean flag) {
		try {
			outputStream.writeBoolean(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeDouble(double number) {
		try {
			outputStream.writeDouble(number);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readUTF() {
		String string = "";
		try {
			string = inputStream.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;
	}
	
	private byte readByte() {
		byte number = 0;
		try {
			number = inputStream.readByte();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return number;
	}
}
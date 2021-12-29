package controller;

import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import models.Player;
import network.PlayerNetwork;

public class PlayerEngine extends MouseAdapter implements KeyListener {

	private boolean upPressed, downPressed, leftPressed, rightPressed;
	private PlayerNetwork playerNetwork;
	private Player player;
	private double width;
	private double height;
	private double size;
	private BufferedImage towerGunImg;
	private BufferedImage partnerImg;
	private BufferedImage towerBaseImg;
	private AudioClip shotAudio;
	private AudioClip explosionAudio;
	private AffineTransformOp op;
	private static final String TOWER_GUN_IMG_PATH = "/img/towerGun.png";
	private static final String TOWER_BASE_IMG_PATH = "/img/towerBase.png";
	private static final String PARTNER_SPACE_SHIP_IMG_PATH = "/img/partnerGun.png";
	private static final String AUDIO_SHOT_SOUND_WAV = "/sounds/shotSound.wav";
	private static final String EXPLOSION_SHOT_SOUND_WAV = "/sounds/explosion5.wav";
	

	public PlayerEngine(PlayerNetwork playerNetwork) {
		this.playerNetwork = playerNetwork;
		initDimensions();
		initImages();
		initSounds();
//		new Thread(this).start();
	}
	
	private void initDimensions() {
		width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(); 
		height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		size = (width+height)/30;
	}
	
	private void initImages() {
		try {
			 towerGunImg = resizeImage(ImageIO.read(getClass().getResourceAsStream(TOWER_GUN_IMG_PATH)));
			 partnerImg = resizeImage(ImageIO.read(getClass().getResourceAsStream(PARTNER_SPACE_SHIP_IMG_PATH)));
			 towerBaseImg = resizeImage(ImageIO.read(getClass().getResourceAsStream(TOWER_BASE_IMG_PATH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private void initSounds() {
		shotAudio = java.applet.Applet.newAudioClip(getClass().getResource(AUDIO_SHOT_SOUND_WAV));
		explosionAudio = java.applet.Applet.newAudioClip(getClass().getResource(EXPLOSION_SHOT_SOUND_WAV));
	}
	
	private BufferedImage resizeImage(BufferedImage img) {
		BufferedImage auxImage =  new BufferedImage((int)size,
                (int)size, img.getType());
		Graphics2D g2d = auxImage.createGraphics();
        g2d.drawImage(img, 0, 0, (int)size, (int)size, null);
        g2d.dispose();
        return auxImage;
	}
	
	public void moveGun(double xCursor, double yCursor) {
		if(player != null) {
		double xDistance = (player.getX() + size/2) - xCursor;
		double yDistance = (player.getY() + size/2) - yCursor;
		double degree = Math.atan(yDistance/xDistance);
		if(xCursor < player.getX() + size/2) {
			rotateGun(degree, true);
			playerNetwork.sendRotation(degree, true);
		}
		else {
			rotateGun(Math.atan(yDistance/xDistance), false);
			playerNetwork.sendRotation(degree, false);
		}
		}
	}
	
	public void rotateGun(double degrees, boolean inversed) {
		double rotationRequired = degrees + Math.PI/2;
		if(inversed) {
			rotationRequired += Math.PI;
		}
		if(degrees == - Math.PI/2) {
			rotationRequired = Math.PI;
		}
		if(degrees == Math.PI/2) {
			rotationRequired = 0;
		}
		double locationX = towerGunImg.getWidth() / 2;
		double locationY = towerGunImg.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	}
	
	//============================================== EVENTS ===============================================
	
	@Override
	public void mousePressed(MouseEvent e) {
		playerNetwork.sendShot(e.getX(), e.getY());
		shotAudio.play();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		moveGun(e.getX(), e.getY());
	}

//	@Override
//	public void run() {
//		while (true) {
//			if (upPressed) {
//				playerNetwork.moveUp();
//				try {
//					Thread.sleep(10);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			if (downPressed) {
//				playerNetwork.moveDown();
//				try {
//					Thread.sleep(10);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			if (leftPressed) {
//				playerNetwork.moveLeft();
//				try {
//					Thread.sleep(10);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			if (rightPressed) {
//				playerNetwork.moveRight();
//				try {
//					Thread.sleep(10);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//
//			try {
//				Thread.sleep(60);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			upPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			downPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftPressed = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public AffineTransformOp getOp() {
		return op;
	}
	
	public BufferedImage getGunImg() {
		return towerGunImg;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public BufferedImage getPartnerGunImg() {
		return partnerImg;
	}
	
	public void playExplosionSound() {
		explosionAudio.play();
	}

}

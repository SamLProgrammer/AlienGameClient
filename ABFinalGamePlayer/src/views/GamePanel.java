package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import controller.PlayerEngine;
import models.Alien;
import models.Bullet;
import models.Game;
import models.Player;

public class GamePanel extends JPanel {

	private CopyOnWriteArrayList<Player> playersList;
	private CopyOnWriteArrayList<Alien> aliensList;
	private CopyOnWriteArrayList<Bullet> bulletsList;
	private PlayerEngine playerEngine;
	private int width;
	private int height;
	private int playerSize;
	private int alienSize;
	private int bulletSize;
	private int stationRadius;
	private int lifeBarBorderSize;
	private int lifeBarSize;
	private int xCenter;
	private int yCenter;
	private String yourName;
	private Image enemyImg;
	private Image stationImg;
	private Image partnerGunImg;
	private Image towerBaseImg;
	private Image partnerBaseImg;
	private ImageIcon explosionImg;
	private ImageIcon smokeImg;
	private boolean explosion;
	private boolean endedGame;
	private static final String ENEMY_IMG_PATH = "/img/bomb.png";
	private static final String TOWER_BASE_IMG_PATH = "/img/towerBase.png";
	private static final String PARTNER_GUN_IMG_PATH = "/img/partnerGun.png";
	private static final String PARTNER_BASE_IMG_PATH = "/img/partnerBase.png";
	private static final String STATION_IMG_PATH = "/img/Station.png";

	public GamePanel(PlayerEngine playerEngine) {
		this.playerEngine = playerEngine;
		initLists();
		initSizes();
		initCursor();
		initImages();
		addKeyListener(playerEngine);
		addMouseListener(playerEngine);
		addMouseMotionListener(playerEngine);
		setFocusable(true);
	}

	private void initSizes() {
		width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		alienSize = (width + height) / 40;
		playerSize = (width + height) / 30;
		bulletSize = (width + height) / 300;
		stationRadius = 3 * playerSize / 2;
		lifeBarSize = stationRadius*2;
		lifeBarBorderSize = lifeBarSize;
		xCenter = width / 2;
		yCenter = height / 2;
	}

	private void initLists() {
		aliensList = new CopyOnWriteArrayList<>();
		playersList = new CopyOnWriteArrayList<>();
		bulletsList = new CopyOnWriteArrayList<>();
	}
	
	public void initCursor() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(getClass().getResource("/img/TargetIcon.png"));
		Point point = new Point(8, 8);
		Cursor cursor = toolkit.createCustomCursor(img, point,"shoterCursor");
		setCursor(cursor);
	}

	private void initImages() {
		enemyImg = initImage(ENEMY_IMG_PATH, alienSize);
		stationImg = initImage(STATION_IMG_PATH, stationRadius * 2);
		partnerGunImg = initImage(PARTNER_GUN_IMG_PATH, playerSize);
		towerBaseImg = initImage(TOWER_BASE_IMG_PATH, playerSize);
		partnerBaseImg = initImage(PARTNER_BASE_IMG_PATH, playerSize);
		explosionImg = new ImageIcon(getClass().getResource("/img/explosion.gif"));
		explosionImg.setImage(explosionImg.getImage().getScaledInstance((int)width/5, (int)height/5, Image.SCALE_DEFAULT));
		smokeImg = new ImageIcon(getClass().getResource("/img/fire.gif"));
		smokeImg.setImage(smokeImg.getImage().getScaledInstance((int)width/20, (int)height/20, Image.SCALE_DEFAULT));
	}

	private Image initImage(String string, int size) {
		Image img = new ImageIcon(getClass().getResource(string)).getImage();
		Image newimg = img.getScaledInstance(size, size, Image.SCALE_DEFAULT);
		return new ImageIcon(newimg).getImage();
	}

	// =============================================== PAINTERS ==============================================

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.red);
		paintStation(g2);
		if(endedGame) {
			g2.setFont(new Font("UNISPACE", Font.BOLD, height/20));
			g2.drawString("GAME OVER", xCenter - g2.getFontMetrics().stringWidth("GAME OVER")/2, height/8);
		}
		else {
			paintAliens(g2);
			paintBullets(g2);
			paintPlayers(g2);
		}
	}

	private void paintStation(Graphics2D g2) {
		g2.drawImage(stationImg, xCenter - stationRadius, yCenter - stationRadius, this);
		if(explosion) {
			explosionImg.paintIcon(this, g2, xCenter - explosionImg.getIconWidth()/2,
					yCenter-explosionImg.getIconHeight()/2);
		}
		if(lifeBarSize > 3*lifeBarBorderSize/4) {
			g2.setColor(Color.green);
		}
		else if(lifeBarSize <= 3*lifeBarBorderSize/4 && lifeBarSize >= lifeBarBorderSize/2) {
			g2.setColor(Color.yellow);
		}
		else if(lifeBarSize < lifeBarBorderSize/2 && lifeBarSize > lifeBarBorderSize/4) {
			g2.setColor(Color.orange);
		}
		else {
			g2.setColor(Color.red);
		}
		g2.fillRect(xCenter - stationRadius, yCenter - 5*stationRadius/4, lifeBarSize, stationRadius/7);
		g2.drawRect(xCenter - stationRadius, yCenter - 5*stationRadius/4,
				lifeBarBorderSize, stationRadius/7);
	}

	private void paintPlayers(Graphics2D g2) {
		g2.setFont(new Font("UNISPACE", Font.BOLD, 12));
		if (playersList != null && playersList.size() > 0) {
			for (Player player : playersList) {
				if (player.getNick().equalsIgnoreCase(yourName) && playerEngine.getGunImg() != null
						&& playerEngine.getOp() != null) {
					g2.drawImage(towerBaseImg, (int)player.getX(), (int)player.getY(), this);
					g2.drawImage(playerEngine.getOp().filter(playerEngine.getGunImg(), null), (int) player.getX(),
							(int) player.getY(), null);

				} else {
					g2.drawImage(partnerBaseImg, (int)player.getX(), (int)player.getY(), this);
					g2.drawImage(rotatePartner(player.getDegrees(), player.isInversed()).filter(playerEngine.getPartnerGunImg(), null),
							(int) player.getX(),
							(int) player.getY(), null);
				}
				g2.setColor(Color.WHITE);
				String nick = player.getNick();
				g2.drawString(nick,
						(int) ((player.getX() + playerSize / 2) - g2.getFontMetrics().stringWidth(nick) / 2),
						(int) (player.getY()));
			}
		}
	}

	private void paintAliens(Graphics2D g2) {
		g2.setColor(Color.orange);
		for (Alien alien : aliensList) {
			if (true) {
				g2.drawImage(enemyImg, (int) alien.getX(), (int) alien.getY(), this);
			}
			if(alien.isFalling()) {
				smokeImg.paintIcon(this, g2, (int)(alien.getX() - smokeImg.getIconWidth()/10),
						(int)alien.getY() - smokeImg.getIconHeight()/2);
			}
		}
	}

	private void paintBullets(Graphics2D g2) {
//		#61ce2e
		g2.setColor(Color.decode("#ff8032"));
		for (Bullet bullet : bulletsList) {
			if (bullet.isAlive()) {
				g2.fillOval((int) bullet.getX(), (int) bullet.getY(), bulletSize, bulletSize);
			}
		}
	}
	
	public AffineTransformOp rotatePartner(double degrees, boolean inversed) {
		AffineTransformOp op;
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
		double locationX = partnerGunImg.getWidth(this) / 2;
		double locationY = partnerGunImg.getHeight(this) / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		return op;
	}

	// ============================================== UTILITIES ============================================

	public void refresh(Game game) {
		this.aliensList = game.getAliensList();
		this.playersList = game.getPlayersList();
		this.bulletsList = game.getBulletsList();
		yourName = game.getName();
		repaint();
	}
	
	public void setExplosion(boolean explosion) {
		this.explosion = explosion;
	}

	public void reduceLifeBar() {
		lifeBarSize -= lifeBarBorderSize/20;
		repaint();
	}

	public void showEndAdvice() {
		endedGame = true;
	}

	public void initHitsOnStation(byte hitsOnStation) {
		if(lifeBarSize == lifeBarBorderSize) {
			lifeBarSize -= hitsOnStation*(lifeBarBorderSize/20);
			repaint();
		}
	}

}

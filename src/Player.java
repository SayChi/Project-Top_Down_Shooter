// As created by Bastiaan Wuisman & Laurence Keijzer on 29-4-2015
// Created using IntelliJ IDEA


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Player {
	MainScript mainScript;

	int currentWeapon = 3;
	int totalWeapons = 4;
	int x, y;
	int moveX, moveY;
	int health = 100;
	int speedLimit = 15;
	double rotation;
	int hitBoxRad = 20;
	Gun[] guns = new Gun[totalWeapons];

	Player( MainScript mainScriptSet ) {
		x = y = 100;
		mainScript = mainScriptSet;
		guns[0] = new Gun(999, 17, 80, 1, 6, 0, 2000, 0.01, 0, false, this);        //pistol
		guns[1] = new Gun(300, 25, 300, 3, 3, 1, 3000, 0.02, 5, true, this);        //micro smg
		guns[2] = new Gun(5, 1, 30, 1, 100, 2, 10000, 0.03, 0, false, this);        //rpg
		guns[3] = new Gun(3000, 1000, 1500, 3, 6, 3, 30000, 0.04, 20, true, this);    //mini gun
	}

	void move() {
		//speed limit
		if( Math.sqrt(Math.pow(moveX, 2) + Math.pow(moveY, 2)) > speedLimit ) {
			double partX = (double) moveX / (Math.sqrt(Math.pow(moveX, 2) + Math.pow(moveY, 2)));
			double partY = (double) moveY / (Math.sqrt(Math.pow(moveX, 2) + Math.pow(moveY, 2)));
			moveX = (int) (partX * speedLimit);
			moveY = (int) (partY * speedLimit);
		}

		//move
		x += moveX;
		y += moveY;

		//screen edges
		if( x >= mainScript.graphicsPanel.getWidth() - 0 ) {
			x = mainScript.graphicsPanel.getWidth() - 1;
		}
		if( y >= mainScript.graphicsPanel.getHeight() - 0 ) {
			y = mainScript.graphicsPanel.getHeight() - 1;
		}
		if( x <= 0 ) {
			x = 1;
		}
		if( y <= 0 ) {
			y = 1;
		}

		rotation = calcRotation();

		//weapon select
		//TODO: numbers select weapons
		currentWeapon += mainScript.inputManager.scolled();
		if( currentWeapon < 0 ) currentWeapon += totalWeapons;
		else if( currentWeapon > totalWeapons - 1 ) currentWeapon -= totalWeapons;

		//input
		if( mainScript.inputManager.mouseButtonDown(MouseEvent.BUTTON1) ) guns[currentWeapon].fire();
		if( mainScript.inputManager.keyDownOnce(KeyEvent.VK_R) ) guns[currentWeapon].reload();

		//hit detection
		ArrayList<Bullet> removeBullets = new ArrayList<Bullet>();
		for( Bullet bullet : mainScript.bullets ) {
			if( Math.sqrt(Math.pow(x - bullet.x, 2) + Math.pow(y - bullet.y, 2)) < hitBoxRad ) {
				removeBullets.add(bullet);
				health -= bullet.damage;
			}
		}
		for( Bullet bullet : removeBullets ) mainScript.bullets.remove(bullet);

		System.out.println("weapon: " + currentWeapon + " totAmmo: " + guns[currentWeapon].totAmmo + " magSize: " +
				guns[currentWeapon].magSize + " currentAmmo: " + guns[currentWeapon].currentAmmo);
	}

	void draw( Graphics g ) {
		BufferedImage imagePlayer;
		g.setColor(Color.BLACK);
		g.fillRect(x - 25, y - 40, 50, 10);
		g.setColor(Color.RED);
		g.fillRect(x - 25, y - 40, health / 2, 10);
		g.drawString(String.valueOf(health), x - 8, y - 43);

		if(guns[currentWeapon].reloadTimer.isRunning()){
			long tempTimeDif = guns[currentWeapon].lastReloadTime - (System.nanoTime() / 1000000);
			double percentageTime = (double) tempTimeDif / guns[currentWeapon].reloadTime;
			g.setColor(Color.GREEN);
			g.fillRect((int)(x - (percentageTime * 25)), y - 30, (int)((percentageTime * 50)), 5);
		}

		try {
			imagePlayer = ImageIO.read(new File("images//player1.png"));
			AffineTransform tx = new AffineTransform();
			tx.rotate(rotation, imagePlayer.getWidth() / 2, imagePlayer.getHeight() / 2);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			imagePlayer = op.filter(imagePlayer, null);
			g.drawImage(imagePlayer, x - 20, y - 20, null);
		}catch( IOException e ) {
		}
	}

	double calcRotation() {
		double deltaX = mainScript.inputManager.mouseLoc.x - x;
		double deltaY = mainScript.inputManager.mouseLoc.y - y;
		double tempAngle;

		if( deltaY == 0 ) {
			if( deltaX > 0 ) {
				return (Math.PI * 0.5);
			}else {
				return (Math.PI * -0.5);
			}
		}else {
			tempAngle = Math.atan(deltaX / deltaY);
		}

		if( deltaX <= 0 && deltaY < 0 ) {
			return (tempAngle * -1);
		}else if( deltaX >= 0 && deltaY < 0 ) {
			return (tempAngle * -1);
		}else if( deltaX <= 0 && deltaY >= 0 ) {
			return (Math.PI - tempAngle);
		}else if( deltaX >= 0 && deltaY >= 0 ) {
			return (Math.PI - tempAngle);
		}else {
			return 0;
		}
	}
}


class Gun implements ActionListener {
	Player player;
	Random r = new Random();

	Timer reloadTimer;
	Timer shootDelay;

	int damage;
	int totAmmo;
	int magSize;
	int fireRate;
	int fireMode;
	int weaponType;
	int reloadTime;
	int currentAmmo;
	int overheatTime;
	double deviation;        // 1 = complete random, 0 = perfect shot
	boolean overheatable;
	boolean isOverheated;

	boolean canFire = true;
	long lastReloadTime;

	Gun( int totAmmoSet, int magSizeSet, int fireRateSet, int fireModeSet, int damageSet, int weaponTypeSet, int
			reloadTimeSet, double deviationSet, int overheatTimeSet, boolean overheatableSet, Player playerSet ) {
		player = playerSet;

		damage = damageSet;
		totAmmo = totAmmoSet;
		magSize = magSizeSet;
		fireRate = fireRateSet;
		fireMode = fireModeSet;
		weaponType = weaponTypeSet;
		reloadTime = reloadTimeSet;
		deviation = deviationSet;
		overheatTime = overheatTimeSet;
		overheatable = overheatableSet;

		shootDelay = new Timer((int) ((60.0 / fireRate) * 1000), this);
		shootDelay.setRepeats(false);
		reloadTimer = new Timer(reloadTime, this);
		reloadTimer.setRepeats(false);
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		if( e.getSource() == reloadTimer ) {
			totAmmo -= magSize - currentAmmo;
			currentAmmo = Math.min(totAmmo, magSize);
		}else if( e.getSource() == shootDelay ) {
			canFire = true;
		}
	}

	void reload() {
		if( totAmmo > currentAmmo && currentAmmo != magSize ) reloadTimer.start();
		lastReloadTime = System.nanoTime() / 1000000;
	}

	void fire() {
		if( canFire && currentAmmo > 0 ) {
			canFire = false;
			double angleDeviated = player.rotation + ((r.nextDouble() - 0.5) * 2 * Math.PI * deviation) - Math.PI / 2;
			player.mainScript.bullets.add(new Bullet(player.x + (int) (Math.cos(player.rotation - Math.PI / 2) *
					(player.hitBoxRad + 2)), player.y + (int) (Math.sin(player.rotation - Math.PI / 2) * (player
					.hitBoxRad + 2)), Math.cos(angleDeviated) * 25, Math.sin(angleDeviated) * 25, damage, player
					.mainScript));
			currentAmmo--;
			shootDelay.start();
			if( currentAmmo == 0 ) reload();
		}
	}
}

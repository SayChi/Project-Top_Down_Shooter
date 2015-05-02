// As created by Bastiaan Wuisman & Laurence Keijzer on 29-4-2015
// Created using IntelliJ IDEA


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Player {
	MainScript mainScript;

	int currentWeapon = 1;
	int x, y;
	int moveX, moveY;
	int health = 50;
	int speedLimit = 15;
	double rotation;
	Gun[] guns = new Gun[10];

	Player( MainScript mainScriptSet ) {
		x = y = 100;
		mainScript = mainScriptSet;
		guns[0] = new Gun(999, 17, 40, 1, 6, 0, 2, 0.01, 0, false, this);    //pistol
		guns[1] = new Gun(300, 30, 300, 3, 3, 1, 3, 0.02, 5, true, this);    //micro smg
		guns[2] = new Gun(5, 1, 30, 1, 100, 2, 10, 0.03, 0, false, this);    //rpg
		guns[3] = new Gun(3000, 1000, 2000, 3, 6, 3, 30, 20, true, this);	//mini gun
	}

	void move() {
		if( Math.abs(moveX) > speedLimit ) moveX = (int) Math.signum(moveX) * speedLimit;
		if( Math.abs(moveY) > speedLimit ) moveY = (int) Math.signum(moveY) * speedLimit;

		x += moveX;
		y += moveY;

		rotation = calcRotation();

		if( mainScript.inputManager.mouseButtonDown(MouseEvent.BUTTON1) ) {
			guns[currentWeapon].fire();
		}
	}

	void draw( Graphics g ) {
		BufferedImage imagePlayer;
		g.setColor(Color.BLACK);
		g.fillRect(x - 25, y - 40, 50, 10);
		g.setColor(Color.RED);
		g.fillRect(x - 25, y - 40, health / 2, 10);
		g.drawString(String.valueOf(health), x - 8, y - 43);

		try {
			imagePlayer = ImageIO.read(new File("images//player1.png"));
			g.translate(-20, -20);
			AffineTransform tx = new AffineTransform();
			tx.rotate(rotation, imagePlayer.getWidth() / 2, imagePlayer.getHeight() / 2);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			imagePlayer = op.filter(imagePlayer, null);
			g.drawImage(imagePlayer, x, y, null);
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
	int firerate;
	int firemode;
	int weaponType;
	int reloadTime;
	int currentAmmo;
	int overheatTime;
	double deviation;        // 1 = complete random, 0 = perfect shot
	boolean overheatable;
	boolean isOverheated;

	boolean canFire = true;

	Gun( int totAmmoSet, int magSizeSet, int firerateSet, int firemodeSet, int damageSet, int weaponTypeSet, int
			reloadTimeSet, double deviationSet, int overheatTimeSet, boolean overheatableSet, Player playerSet ) {
		player = playerSet;

		damage = damageSet;
		totAmmo = totAmmoSet;
		magSize = magSizeSet;
		firerate = firerateSet;
		firemode = firemodeSet;
		weaponType = weaponTypeSet;
		reloadTime = reloadTimeSet;
		deviation = deviationSet;
		overheatTime = overheatTimeSet;
		overheatable = overheatableSet;

		shootDelay = new Timer((60 / firerate) * 1000, this);
		reloadTimer = new Timer(reloadTime, this);
		reloadTimer.setRepeats(false);
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		if( e.getSource() == reloadTimer ) {
			int temp;
			temp = magSize - currentAmmo;
			if( totAmmo >= temp ) {
				totAmmo -= temp;
				currentAmmo = magSize;
			}else {
				magSize = totAmmo;
				totAmmo = 0;
			}
		}else if( e.getSource() == shootDelay ) {
			canFire = true;
		}
	}

	void reload() {
		reloadTimer.start();
	}

	void fire() {
		if( canFire ) {
			double angleDeviated = player.rotation + ((r.nextDouble() - 0.5) * 2 * Math.PI * deviation) - Math.PI / 2;
			player.mainScript.bullets.add(new Bullet(player.x + 20, player.y + 20, Math.cos(angleDeviated) * 25, Math
					.sin(angleDeviated) * 25, damage));
			canFire = false;
			shootDelay.start();
		}
	}
}

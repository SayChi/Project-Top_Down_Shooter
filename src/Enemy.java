// As created by Bastiaan Wuisman & Laurence Keijzer on 29-4-2015
// Created using IntelliJ IDEA


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Enemy {
	MainScript mainScript;

	int damage = 5;
	int x, y;
	int health = 100;
	int hitBoxRad = 25;
	double deviation = 0.06;
	double rotation;
	double angleDeviated;
	Random r = new Random();

	Enemy( MainScript mainScriptSet, int xSet, int ySet ) {
		mainScript = mainScriptSet;

		x = xSet;
		y = ySet;
	}

	void move() {
		ArrayList<Bullet> removeBullets = new ArrayList<Bullet>();

		rotation = calcRotation();

		for( Bullet bullet : mainScript.bullets ) {
			if( Math.sqrt(Math.pow(x - bullet.x, 2) + Math.pow(y - bullet.y, 2)) <= hitBoxRad ) {
				removeBullets.add(bullet);
				health -= bullet.damage;
			}
		}

		for( Bullet bullet : removeBullets ) mainScript.bullets.remove(bullet);
	}

	void draw( java.awt.Graphics g ) {
		BufferedImage imagePlayer;
		//rotation = calcRotation();
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

	double calcRotation(){
		double deltaX = mainScript.player.x - x;
		double deltaY = mainScript.player.y - y;
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

	void shoot() {
		double angleDeviated = rotation + ((r.nextDouble() - 0.5) * 2 * Math.PI * deviation) - Math.PI / 2;
		mainScript.bullets.add(new Bullet(x + 20, y + 20, Math.cos(angleDeviated)
				* 25, Math.sin(angleDeviated) * 25, damage, mainScript));
	}
}

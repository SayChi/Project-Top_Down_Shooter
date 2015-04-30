// As created by Bastiaan Wuisman & Laurence Keijzer on 29-4-2015
// Created using IntelliJ IDEA


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
	Player(MainScript mainScriptSet){
		mainScript = mainScriptSet;
	}

	MainScript mainScript;
	int x,y;
	int moveX, moveY;
	int health;

	int speedLimit = 5;

	void move() {
		if( Math.abs(moveX) > speedLimit ) moveX = (int) Math.signum(moveX) * speedLimit;
		if( Math.abs(moveY) > speedLimit ) moveY = (int) Math.signum(moveY) * speedLimit;

		x += moveX;
		y += moveY;
	}

	void draw( Graphics g ) {
		BufferedImage imagePlayer;

		try{
			imagePlayer = ImageIO.read(new File("images//square1.png"));
			//g.translate(-20, -20);
			AffineTransform tx = new AffineTransform();
			tx.rotate(0.5, imagePlayer.getWidth() / 2, imagePlayer.getHeight() / 2);
			AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
			imagePlayer = op.filter(imagePlayer, null);
			g.drawImage(imagePlayer, x, y, null);
		} catch( IOException e ) {
			//e.printStackTrace();
		}
	}
}

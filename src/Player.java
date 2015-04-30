// As created by Bastiaan Wuisman on 29-4-2015
// Created using IntelliJ IDEA


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
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

	void draw( java.awt.Graphics g ) {
		BufferedImage imagePlayer;

		try{
			imagePlayer = ImageIO.read(new File("images//square1.png"));
			g.drawImage(imagePlayer, 100, 100, null);
		} catch( IOException e ) {
			//e.printStackTrace();
		}

	}
}

// As created by Bastiaan Wuisman & Laurence Keijzer on 29-4-2015
// Created using IntelliJ IDEA


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy {
	MainScript mainScript;

	int x, y;
	int health;
	double rotation;

	Enemy(MainScript mainScriptSet, int xSet, int ySet) {
		mainScript = mainScriptSet;

		x = xSet;
		y = ySet;
	}

	void move(){}

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
}

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
	MainScript mainScript;

	int x,y;
	int moveX, moveY;
	int health;
	int speedLimit = 5;
	double rotation;

	double temp;

	Player(MainScript mainScriptSet){
		mainScript = mainScriptSet;
	}

	void move() {
		if( Math.abs(moveX) > speedLimit ) moveX = (int) Math.signum(moveX) * speedLimit;
		if( Math.abs(moveY) > speedLimit ) moveY = (int) Math.signum(moveY) * speedLimit;

		x += moveX;
		y += moveY;
	}

	void draw( Graphics g ) {
		BufferedImage imagePlayer;

		temp += 0.05;
		rotation = calcRotation();

		try{
			imagePlayer = ImageIO.read(new File("images//square1.png"));
			g.translate(-20, -20);
			AffineTransform tx = new AffineTransform();
			tx.rotate(rotation, imagePlayer.getWidth() / 2, imagePlayer.getHeight() / 2);
			AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
			imagePlayer = op.filter(imagePlayer, null);
			g.drawImage(imagePlayer, x, y, null);
		} catch( IOException e ) {
			//e.printStackTrace();
		}
	}

	double calcRotation(){
		double deltaX, deltaY;
		double tempAngle;
		deltaX = mainScript.mouseLoc.x - x;
		deltaY = mainScript.mouseLoc.y - y;

		if (deltaX <= 0 && deltaY < 0 ){
			try{
				tempAngle = Math.atan(deltaX / deltaY);
				return (tempAngle * -1);
			}catch(Exception e){
				return (Math.PI * 0.5);
			}
		}

		else if (deltaX >= 0 && deltaY < 0 ){
			try{
				tempAngle = Math.atan(deltaX / deltaY);
				return (tempAngle * -1);
			}catch(Exception e){
				return (Math.PI * -0.5);
			}
		}

		else if (deltaX <= 0 && deltaY >= 0 ){
			try{
				tempAngle = Math.atan(deltaX / deltaY);
			}catch(Exception e){
				return (Math.PI * 0.5);
			}
		}

		else if (deltaX >= 0 && deltaY >= 0 ){
			try{
				tempAngle = Math.atan(deltaX / deltaY);
			}catch(Exception e){
				return (Math.PI * -0.5);
			}
		}
		else {
			return 0;
		}
	}
}

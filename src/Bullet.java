// As created by Bastiaan Wuisman on 2-5-2015
// Created using IntelliJ IDEA


import java.awt.*;

public class Bullet {
	MainScript mainScript;

	double x, y;
	double xSpeed, ySpeed;
	int damage;

	Bullet( int xSet, int ySet, double xSpeedSet, double ySpeedSet, int damageSet, MainScript mainScriptSet ) {
		mainScript = mainScriptSet;

		x = xSet;
		y = ySet;
		xSpeed = xSpeedSet;
		ySpeed = ySpeedSet;
		damage = damageSet;
	}

	void move() {
		x += xSpeed;
		y += ySpeed;
	}

	void draw( Graphics g ) {
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y, 2, 2);
	}

	boolean onScreen() {
		if( x > mainScript.graphicsPanel.getWidth() || x < 0 || y > mainScript.graphicsPanel.getHeight() || y < 0 ) {
			return false;
		}
		return true;
	}
}

// As created by Bastiaan Wuisman on 2-5-2015
// Created using IntelliJ IDEA


import java.awt.*;

public class Bullet {
	double x, y;
	double xSpeed, ySpeed;
	int damage;

	Bullet(int xSet, int ySet, double xSpeedSet, double ySpeedSet, int damageSet) {
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

	void draw( Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y, 2, 2);
	}
}

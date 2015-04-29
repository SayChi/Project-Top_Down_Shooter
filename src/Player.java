// As created by Bastiaan Wuisman on 29-4-2015
// Created using IntelliJ IDEA


import java.awt.*;

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

	}
}

// As created by Bastiaan Wuisman on 29-4-2015
// Created using IntelliJ IDEA


public class Player {
	int x,y;
	int moveX, moveY;
	int health;

	int speedLimit = 5;

	void move() {
		if( Math.abs(moveX) <= speedLimit ) x += moveX;
		else x += Math.signum(moveX) * speedLimit;
		y += moveY;
	}
}

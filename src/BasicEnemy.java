// As created by Bastiaan Wuisman on 2-5-2015
// Created using IntelliJ IDEA


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasicEnemy extends Enemy implements ActionListener {
	Timer shootDelay = new Timer(1000, this);

	BasicEnemy( MainScript mainScriptSet, int xSet, int ySet ) {
		super(mainScriptSet, xSet, ySet);
		shootDelay.start();
	}

	@Override
	void move() {
		super.move();
		if( mainScript.player.x > x ) x++;
		else if( mainScript.player.x < x ) x--;

		if( mainScript.player.y > y ) y++;
		else if( mainScript.player.y < y ) y--;
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		if( health <= 0 ) {
			shootDelay.stop();
			return;
		}
		super.shoot();
	}
}

// As created by Bastiaan Wuisman & Laurence Keijzer on 29-4-2015
// Created using IntelliJ IDEA


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel {
	MainScript mainScript;

	GraphicsPanel( MainScript mainScriptSet ) {
		mainScript = mainScriptSet;
		setLayout(new FlowLayout());
	}

	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent(g);

		mainScript.player.draw(g);

		ArrayList<Bullet> removeBullets = new ArrayList<Bullet>();

		for( Bullet bullet : mainScript.bullets ) {
			if( bullet.onScreen() )	bullet.draw(g);
			else removeBullets.add(bullet);
		}
		for( Bullet bullet : removeBullets ) mainScript.bullets.remove(bullet);
	}
}

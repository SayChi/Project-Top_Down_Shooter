// As created by Bastiaan Wuisman & Laurence Keijzer on 29-4-2015
// Created using IntelliJ IDEA


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

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
		for( Bullet bullet : mainScript.bullets ) bullet.draw(g);
	}
}

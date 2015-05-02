// As created by Bastiaan Wuisman & Laurence Keijzer on 29-4-2015
// Created using IntelliJ IDEA


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Graphics extends JPanel {
	MainScript mainScript;

	Graphics( MainScript mainScriptSet ) {
		mainScript = mainScriptSet;
		setLayout(new FlowLayout());
	}

	@Override
	protected void paintComponent( java.awt.Graphics g ) {
		super.paintComponent(g);
		//temp
		if( mainScript.inputManager.mouseButtonDown(MouseEvent.BUTTON1) ) g.setColor(Color.RED);
		else g.setColor(Color.BLACK);
		g.drawLine(mainScript.player.x, mainScript.player.y, mainScript.inputManager.mouseLoc.x, mainScript
				.inputManager.mouseLoc.y);

		mainScript.player.draw(g);
	}
}

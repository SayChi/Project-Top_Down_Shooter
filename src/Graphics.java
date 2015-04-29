// As created by Bastiaan Wuisman on 29-4-2015
// Created using IntelliJ IDEA


import javax.swing.*;
import java.awt.*;

public class Graphics extends JPanel {
	MainScript mainScript;

	Graphics( MainScript mainScriptSet ) {
		mainScript = mainScriptSet;
	}

	@Override
	protected void paintComponent( java.awt.Graphics g ) {
		super.paintComponent(g);
		//temp
		if( mainScript.clicky ) g.setColor(Color.RED);
		else g.setColor(Color.BLACK);
		g.drawLine(0, 0, mainScript.mouseLoc.x, mainScript.mouseLoc.y);
	}
}

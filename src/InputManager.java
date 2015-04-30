// As created by Bastiaan Wuisman & Laurence Keijzer on 29-4-2015
// Created using IntelliJ IDEA


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class InputManager extends JPanel {
	MainScript mainScript;
	Action aW, aA, aS, aD;

	InputManager(MainScript mainScriptSet) {
		mainScript = mainScriptSet;

		aW = new actionW("W", null);
		getInputMap().put(KeyStroke.getKeyStroke("w"), "forward");
		getActionMap().put("forward", aW);
	}
}

class actionW extends AbstractAction {

	actionW(String text, ImageIcon icon) {
		super(text, icon);
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		System.out.println("forward!");
	}
}
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

		aW = new actionW("W", null, "forward", KeyEvent.VK_W);
	}
}

class actionW extends AbstractAction {

	actionW(String text, ImageIcon icon, String desc, Integer mnemonic) {
		super(text, icon);
		putValue(SHORT_DESCRIPTION, desc);
		putValue(MNEMONIC_KEY, mnemonic);
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		System.out.println("forward!");
	}
}
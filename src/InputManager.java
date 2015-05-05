// As created by Bastiaan Wuisman & Laurence Keijzer on 29-4-2015
// Created using IntelliJ IDEA


import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class InputManager implements KeyListener, MouseListener, MouseWheelListener {
	MainScript mainScript;

	final int KEY_COUNT = 256;
	final int BUTTON_COUNT = 3;

	Point mouseLoc = new Point();

	boolean[] currentMouseButtons = new boolean[BUTTON_COUNT];
	KeyState[] mouseButtons = new KeyState[BUTTON_COUNT];

	boolean[] currentKeys = new boolean[KEY_COUNT];
	KeyState[] keys = new KeyState[KEY_COUNT];

	int theySeeMeScrolling;

	InputManager( MainScript mainScriptSet ) {
		mainScript = mainScriptSet;

		Arrays.fill(mouseButtons, KeyState.RELEASED);
		Arrays.fill(keys, KeyState.RELEASED);
	}

	void poll() {
		mouseLoc.x = MouseInfo.getPointerInfo().getLocation().x - mainScript.graphicsPanel.getLocationOnScreen().x;
		mouseLoc.y = MouseInfo.getPointerInfo().getLocation().y - mainScript.graphicsPanel.getLocationOnScreen().y;

		for( int i = 0; i < KEY_COUNT; ++i ) {
			if( currentKeys[i] ) {
				if( keys[i] == KeyState.RELEASED ) {
					keys[i] = KeyState.ONCE;
				}else {
					keys[i] = KeyState.PRESSED;
				}
			}else {
				keys[i] = KeyState.RELEASED;
			}
		}

		for( int i = 0; i < BUTTON_COUNT; ++i ) {
			if( currentMouseButtons[i] ) {
				if( mouseButtons[i] == KeyState.RELEASED ) {
					mouseButtons[i] = KeyState.ONCE;
				}else {
					mouseButtons[i] = KeyState.PRESSED;
				}
			}else {
				mouseButtons[i] = KeyState.RELEASED;
			}
		}
	}

	enum KeyState {
		RELEASED, // Button is not pressed
		PRESSED,  // Button is pressed
		ONCE      // Rising edge
	}

	int scolled() {
		int temp = -theySeeMeScrolling;
		theySeeMeScrolling = 0;
		return temp;
	}

	boolean keyDown( int keyCode ) {
		return keys[ keyCode ] == KeyState.ONCE || keys[ keyCode ] == KeyState.PRESSED;
	}

	boolean keyDownOnce( int keyCode ) {
		return keys[ keyCode ] == KeyState.ONCE;
	}

	boolean mouseButtonDown( int keyCode ) {
		return mouseButtons[ keyCode - 1 ] == KeyState.ONCE || mouseButtons[ keyCode - 1 ] == KeyState.PRESSED;
	}

	boolean mouseButtonDownOnce( int keyCode ) {
		return mouseButtons[ keyCode - 1 ] == KeyState.ONCE;
	}

	@Override
	public void mouseWheelMoved( MouseWheelEvent e ) {
		theySeeMeScrolling = e.getWheelRotation();
	}

	@Override
	public void keyPressed( KeyEvent e ) {
		if( e.getKeyCode() >= 0 && e.getKeyCode() < KEY_COUNT ) currentKeys[ e.getKeyCode() ] = true;
	}

	@Override
	public void keyReleased( KeyEvent e ) {
		if( e.getKeyCode() >= 0 && e.getKeyCode() < KEY_COUNT ) currentKeys[ e.getKeyCode() ] = false;
	}

	@Override
	public void mousePressed( MouseEvent e ) {
		currentMouseButtons[ e.getButton()-1 ] = true;
	}

	@Override
	public void mouseReleased( MouseEvent e ) {
		currentMouseButtons[ e.getButton()-1 ] = false;
	}

	//region<junk>
	@Override
	public void mouseClicked( MouseEvent e ) {

	}

	@Override
	public void mouseEntered( MouseEvent e ) {

	}

	@Override
	public void mouseExited( MouseEvent e ) {

	}

	@Override
	public void keyTyped( KeyEvent e ) {

	}
	//endregion
}
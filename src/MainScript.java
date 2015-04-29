// As created by Bastiaan Wuisman on 29-4-2015
// Created using IntelliJ IDEA


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class MainScript implements MouseListener, KeyListener {
	JFrame frame = new JFrame();
	JPanel graphicsPanel = new Graphics(this);
	Player player = new Player();

	Point mouseLoc = new Point();				//mouse location in the graphicsPanel
	boolean clicky;								//is the left mouse button currently pressed?

	int logicTimeDelayMilliSecs = 1000 / 30;	//delay between logic loops

	public void run() {
		//region<frame stuffs>
		frame.setSize(1080, 720);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(graphicsPanel);

		frame.addKeyListener(this);
		frame.addMouseListener(this);
		//endregion

		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				while( true ) {
					long loopStartTime = System.nanoTime() / 1000000;	//saves starting time of the logic
					mouseLoc.x = MouseInfo.getPointerInfo().getLocation().x - graphicsPanel.getLocationOnScreen().x;
					mouseLoc.y = MouseInfo.getPointerInfo().getLocation().y - graphicsPanel.getLocationOnScreen().y;

					//region<game logic>
					if( player.moveX > 0 ) player.moveX--;
					else if( player.moveX < 0 ) player.moveX++;
					if( player.moveY > 0 ) player.moveY--;
					else if( player.moveY < 0 ) player.moveY++;
					player.move();
					//endregion

					publish();

					//stops if there is not enough time for logic (too slow PC) else sleeps
					if( System.nanoTime() / 1000000 - loopStartTime > logicTimeDelayMilliSecs ) return null;
					try {
						Thread.sleep(logicTimeDelayMilliSecs - (System.nanoTime() / 1000000 - loopStartTime));
					}catch( InterruptedException e ) {
					}
				}
			}

			@Override
			protected void process( List<Void> v ) {
				graphicsPanel.repaint();
			}
		}.execute();
	}

	public static void main( String[] args ) {
		new MainScript().run();
	}

	@Override
	public void keyPressed( KeyEvent e ) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_W:
				player.moveY -= 3;
				break;

			case KeyEvent.VK_S:
				player.moveY += 3;
				break;

			case KeyEvent.VK_A:
				player.moveX -= 3;
				break;

			case KeyEvent.VK_D:
				player.moveX += 3;
				break;
		}
	}

	@Override
	public void mousePressed( MouseEvent e ) {
		clicky = true;
	}

	@Override
	public void mouseReleased( MouseEvent e ) {
		clicky = false;
	}

	//region<trash>
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

	@Override
	public void keyReleased( KeyEvent e ) {

	}
	//endregion
}
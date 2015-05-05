// As created by Bastiaan Wuisman & Laurence Keijzer on 29-4-2015
// Created using IntelliJ IDEA


import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MainScript {
	JFrame frame = new JFrame();
	JPanel graphicsPanel = new GraphicsPanel(this);
	Player player = new Player(this);
	InputManager inputManager = new InputManager(this);
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	int logicTimeDelayMilliSecs = 1000 / 25;	//delay between logic loops

	long lastDrawTime;
	int fps;
	long lastCalcTime;
	int cps;

	public void run() {
		//region<frame stuffs>
		frame.setSize(1080, 720);
		//frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(graphicsPanel);

		frame.addKeyListener(inputManager);
		frame.addMouseListener(inputManager);
		frame.addMouseWheelListener(inputManager);
		//endregion

		enemies.add(new BasicEnemy(this, 50, 50));

		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				while( true ) {
					long loopStartTime = System.nanoTime() / 1000000;	//saves starting time of the logic

					//region<game logic>
					inputManager.poll();	//get input

					if( inputManager.keyDown(KeyEvent.VK_W) ) player.moveY -= 3;
					if( inputManager.keyDown(KeyEvent.VK_S) ) player.moveY += 3;
					if( inputManager.keyDown(KeyEvent.VK_A) ) player.moveX -= 3;
					if( inputManager.keyDown(KeyEvent.VK_D) ) player.moveX += 3;

					//slow player down
					if( player.moveX > 0 ) player.moveX--;
					else if( player.moveX < 0 ) player.moveX++;
					if( player.moveY > 0 ) player.moveY--;
					else if( player.moveY < 0 ) player.moveY++;

					for( Bullet bullet : bullets ) bullet.move();
					player.move();			//move player

					for( Enemy enemy : enemies ) enemy.move();
					//endregion

					publish();

					//stops if there is not enough time for logic (too slow PC) else sleeps
					//if( System.nanoTime() / 1000000 - loopStartTime > logicTimeDelayMilliSecs ) return null;
					cps = (int) (1000/(System.nanoTime()/1000000 - lastCalcTime));
					lastCalcTime = System.nanoTime()/1000000;
					frame.setTitle("FPS: " + fps + "   CPS: " + cps);
					try {
						Thread.sleep(logicTimeDelayMilliSecs - (System.nanoTime() / 1000000 - loopStartTime));
					}catch( InterruptedException e ) {
					}
				}
			}

			@Override
			protected void process( List<Void> v ) {
				graphicsPanel.repaint();
				fps = (int) (1000/(System.nanoTime()/1000000 - lastDrawTime));
				lastDrawTime = System.nanoTime()/1000000;
			}
		}.execute();
	}

	public static void main( String[] args ) {
		new MainScript().run();
	}
}
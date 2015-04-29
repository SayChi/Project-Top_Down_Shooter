// As created by Bastiaan Wuisman on 29-4-2015
// Created using IntelliJ IDEA


import javax.swing.*;
import java.util.List;

public class MainScript {
	JFrame jFrame = new JFrame();
	int logicTimeDelayMilliSecs = 1000 / 30;	//delay between logic loops

	public void run() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				while( true ) {
					long loopStartTime = System.nanoTime() / 1000000;	//saves starting time of the logic

					//region<game logic>

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

			}
		}.execute();
	}

	public static void main( String[] args ) {
		new MainScript().run();
	}
}
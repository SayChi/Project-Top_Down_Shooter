// As created by Bastiaan Wuisman on 29-4-2015
// Created using IntelliJ IDEA


import javax.swing.*;
import java.util.List;

public class MainScript {
	public void run(){
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				//logic here
				//use publish() to draw
				for( int i = 0; i < 1000; i++ ) {
					System.out.println("hi");
					try {
						Thread.sleep(1000/60);
					}catch( InterruptedException e ) {
					}
					publish();
				}
				return null;
			}

			@Override
			protected void process(List<Void> v) {
				//draw here
				System.out.println("bye");
			}
		}.execute();
	}

	public static void main(String[] args){
		new MainScript().run();
	}
}
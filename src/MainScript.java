// As created by Bastiaan Wuisman on 29-4-2015
// Created using IntelliJ IDEA


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainScript implements ActionListener {
	JFrame jFrame = new JFrame();
	SwingWorker swingWorker;
	Timer timerLogic = new Timer(1000 / 30, this);

	public void run() {
		swingWorker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				System.out.println("logic");
				publish();
				return null;
			}

			@Override
			protected void process( List<Void> v ) {
				System.out.println("draw");
			}
		};

		timerLogic.start();
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		System.out.println("action");
		swingWorker.run();
	}

	public static void main( String[] args ) {
		new MainScript().run();
	}
}
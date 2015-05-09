package wingman;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/* Runnable game application */
public class GameApplication {
	WingmanWorld game;
	Thread thread;
	
	public static void main(String argv[]) {
	    final WingmanWorld game = WingmanWorld.getInstance();
	    JFrame f = new JFrame("Wingman Game");
	    f.addWindowListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		        game.requestFocusInWindow();
		    }
	    });
	    f.getContentPane().add("Center", game);
	    f.pack();
	    f.setSize(new Dimension(800, 600));
	    game.setDimensions(800, 600);
	    game.init();
	    f.setVisible(true);
	    f.setResizable(false);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    game.start();
	}

}
package lazarus;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observer;

import javax.swing.JPanel;

import lazarus.game.BackgroundObject;


public abstract class GameWorld extends JPanel implements Runnable, Observer {
	public abstract void addClockObserver(Observer obs);
	public static HashMap<String,Image> sprites = new HashMap<String,Image>();
	
	private static Point speed=new Point(0,0);
	
    public static final GameSounds sound = new GameSounds();
    public static final GameClock clock = new GameClock();
    
    protected BufferedImage bimg;
    protected ArrayList<BackgroundObject> background;
    
    abstract protected void loadSprites();
    
    public static void setSpeed(Point speed){
    	GameWorld.speed = speed;
    }
    
    public static Point getSpeed(){
    	return new Point(GameWorld.speed);
    }
    
    public Graphics2D createGraphics2D(int w, int h) {
        Graphics2D g2 = null;
        if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
            bimg = (BufferedImage) createImage(w, h);
        }
        g2 = bimg.createGraphics();
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }
    
    public Image getSprite(String name) {
        URL url = LazarusWorld.class.getResource(name);
        Image img = java.awt.Toolkit.getDefaultToolkit().getImage(url);
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        } catch (Exception e) {
        	System.out.println("Couldnt get sprite:" + name);
        }
        return img;
    }
}

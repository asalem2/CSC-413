package lazarus.game;

import java.awt.Image;
import java.awt.Point;

import lazarus.GameWorld;


/*BackgroundObjects move at speed of 1 and are not collidable*/
public class BackgroundObject extends GameObject {
	public BackgroundObject(Point location, Image img){
		super(location, GameWorld.getSpeed(), img);
	}
	
	public BackgroundObject(Point location, Point speed, Image img){
		super(location, speed, img);
	}

}

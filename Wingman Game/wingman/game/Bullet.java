package wingman.game;

import java.awt.Point;

import wingman.WingmanWorld;
import wingman.modifiers.motions.MotionController;

/*Bullets fired by player and enemy weapons*/
public class Bullet extends MoveableObject {
	protected PlayerShip owner;
	boolean friendly;
	
	public Bullet(Point location, Point speed, int strength, MotionController motion, GameObject owner){
		super(location, speed, WingmanWorld.sprites.get("enemybullet1"));
		this.strength=strength;
		if(owner instanceof PlayerShip){
			this.owner = (PlayerShip) owner;
			this.friendly=true;
			this.setImage(WingmanWorld.sprites.get("bullet"));
		}
		this.motion = motion;
		motion.addObserver(this);
	}
	
	public PlayerShip getOwner(){
		return owner;
	}
	
	public boolean isFriendly(){
		if(friendly){
			return true;
		}
		return false;
	}
}

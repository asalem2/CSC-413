package wingman.game;

import java.awt.Point;
import java.util.Observable;

import wingman.WingmanWorld;
import wingman.modifiers.AbstractGameModifier;
import wingman.modifiers.motions.SimpleMotion;
import wingman.modifiers.weapons.AbstractWeapon;

/* PowerUp extends ship so that it can hold a weapon to give to player*/
public class PowerUp extends Ship {
	public PowerUp(Ship theShip){
		super(theShip.getLocationPoint(), theShip.getSpeed(), 1, WingmanWorld.sprites.get("powerup"));
		this.motion = new SimpleMotion();
		this.motion.addObserver(this);
		this.weapon = theShip.getWeapon();
	}
	
	public PowerUp(int location, int health, AbstractWeapon weapon){
		this(new Point(location, -100), health, weapon);
		this.motion = new SimpleMotion();
		this.motion.addObserver(this);
		this.weapon = weapon;
	}
	
	public PowerUp(Point location, int health, AbstractWeapon weapon){
		super(new Point(location),new Point(0,2), health, WingmanWorld.sprites.get("powerup"));
		this.motion = new SimpleMotion();
		this.motion.addObserver(this);
		this.weapon = weapon;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		AbstractGameModifier modifier = (AbstractGameModifier) o;
		modifier.read(this);
	}
	
	public void die(){
    	this.show=false;
    	weapon.deleteObserver(this);
    	motion.deleteObserver(this);
    	WingmanWorld.getInstance().removeClockObserver(motion);
	}

}

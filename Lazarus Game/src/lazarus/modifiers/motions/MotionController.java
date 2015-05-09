package lazarus.modifiers.motions;

import java.util.Observable;
import java.util.Observer;

import lazarus.GameWorld;
import lazarus.LazarusWorld;
import lazarus.game.*;
import lazarus.modifiers.AbstractGameModifier;


/*MotionControllers move around objects!*/
public abstract class MotionController extends AbstractGameModifier implements Observer {
	int fireInterval;
	
	public MotionController(){
		this(LazarusWorld.getInstance());
	}
	
	public MotionController(GameWorld world){
		world.addClockObserver(this);
	}
	
	public void delete(Observer theObject){
		LazarusWorld.getInstance().removeClockObserver(this);
		this.deleteObserver(theObject);
	}
	
	/*Motion Controllers observe the GameClock and fire on every clock tick
	 * The default controller doesn't do anything though*/
	public void update(Observable o, Object arg){
		this.setChanged();
		this.notifyObservers();
	}
	
	public void read(Object theObject){
		Ship object = (Ship) theObject;
		object.move();
		
	}
}

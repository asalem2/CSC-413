package wingman.game;

import java.awt.Image;
import java.awt.Point;

import wingman.GameWorld;
import wingman.WingmanWorld;

/* Small explosions happen whenever an enemy dies */
public class SmallExplosion extends BackgroundObject {
	int timer;
	int frame;
	static Image animation[] = new Image[] {WingmanWorld.sprites.get("explosion1_1"),
		WingmanWorld.sprites.get("explosion1_2"),
		WingmanWorld.sprites.get("explosion1_3"),
		WingmanWorld.sprites.get("explosion1_4"),
		WingmanWorld.sprites.get("explosion1_5"),
		WingmanWorld.sprites.get("explosion1_6"),
		WingmanWorld.sprites.get("explosion1_7")};
	
	public SmallExplosion(Point location) {
		super(location, animation[0]);
		timer = 0;
		frame=0;
		GameWorld.sound.play("Resources/snd_explosion2.wav");
	}
	
	public void update(int w, int h){
    	super.update(w, h);
    	timer++;
    	if(timer%5==0){
    		frame++;
    		if(frame<6)
    			this.img = animation[frame];
    		else
    			this.show = false;
    	}

	}
	
	public boolean collision(GameObject otherObject){
		return false;
	}
}

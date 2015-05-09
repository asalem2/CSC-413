package wingman.game;

import java.awt.Image;
import java.awt.Point;

import wingman.GameWorld;
import wingman.WingmanWorld;

/* BigExplosion plays when player dies*/
public class BigExplosion extends BackgroundObject {
	int timer;
	int frame;
	static Image animation[] = new Image[] {GameWorld.sprites.get("explosion2_1"),
			GameWorld.sprites.get("explosion2_2"),
			GameWorld.sprites.get("explosion2_3"),
			GameWorld.sprites.get("explosion2_4"),
			GameWorld.sprites.get("explosion2_5"),
			GameWorld.sprites.get("explosion2_6"),
			GameWorld.sprites.get("explosion2_7")};
	public BigExplosion(Point location) {
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
    		if(frame<7)
    			this.img = animation[frame];
    		else
    			this.show = false;
    	}
	}
	
	public boolean collision(GameObject otherObject){
		return false;
	}
}

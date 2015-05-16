/**
 *  The Lazarus class is where everything player related is 
*/

package lazarus;

import java.awt.*;
import java.util.ListIterator;
import lazarus.GameSounds;
import lazarus.GameWorld;
import lazarus.game.PlayerShip;
import lazarus.game.enemy.Box;
import lazarus.modifiers.motions.InputController;

public class Lazarus extends PlayerShip{
	public Lazarus(Point location, Image img, int controls[], String name){
        super(location, new Point(0, 0), img, controls, name);
        prevRight = 0;
        prevLeft = 0;
        resetPoint = new Point(location);
        this.name = name;
        motion = new InputController(this, controls, LazarusWorld.getInstance());
        lives = 1;
        respawnCounter = 0;
        height = 40;
        width = 40;
        this.location = new Rectangle(location.x, location.y, width, height);
    }

    private boolean isPlayerWallCollision(){
        for(ListIterator wallList = LazarusWorld.getInstance().getWalls(); wallList.hasNext();){
            Wall nWall = (Wall)wallList.next();
            if(nWall.collision(this))
                if(right == 1){ 
                    if(location.x + 40 >= nWall.getX())
                        return true;
                } 
                else{
                	if(location.x - 40 <= nWall.getX())
                    	return true;
                }
        }
        return false;
    }

    private boolean isPlayerBoxCollision(){
        int Col = location.x / 40;
        for(ListIterator boxlist = LazarusWorld.getInstance().getRestedboxesAtCol(Col);
        	boxlist.hasNext();){
            Box nBox = (Box)boxlist.next();
            if(nBox.collision(this))
                if(LazarusWorld.getInstance().getNumberOfRestedBoxesAbove(Col, nBox.getY()) > 0){
                    return true;
                } 
                else{
                    location.y -= 38;
                    return false;
                }
        }

        if(location.y < 360){
            location.y += 38;
            return isPlayerBoxCollision();
        }
        else{
            return false;
        }
    }

    public void update(int w, int h){
        if(prevRight != right || prevLeft != left){
            prevRight = right;
            prevLeft = left;
            if(right == 1 || left == 1){
                location.x += (right - left) * 40;
                if (location.y > 220 || (location.x > 80 && location.x < 520)) {
                	if(isPlayerWallCollision() || isPlayerBoxCollision()){
                		GameSounds.play("Resources/Wall.wav");
                		location.x -= (right - left) * 40;
                	} 
                	else{
                		GameSounds.play("Resources/Move.wav");
                	}
                }
                else {
            	location.y = 161;
            	GameSounds.play("Resources/Move.wav");
            	if (location.x == Wall.firstx || location.x == Wall.secondx-40){
            		LazarusLevel.endgameDelay = 0;
            		GameSounds.play("Resources/Button.wav");
            		LazarusWorld.gameWon = true;
            		LazarusWorld.gameOver = true;
            	}
                }
            }
        }
    }

    public void die(){
    		lives--;
        if(lives == 0 && LazarusWorld.squished == true){
        	show = false;
            GameSounds.play("Resources/Squished.wav");
            GameWorld.setSpeed(new Point(0, 0));
            LazarusWorld.getInstance().removeClockObserver(motion);
            reset();
            LazarusWorld.squished = false;
            LazarusWorld.gameOver = true;
        }
        
    }

    public void reset(){
        setLocation(resetPoint);
        health = strength;
        respawnCounter = 100;
    }

    int prevRight;
    int prevLeft;
    int prevUp;
    int prevDown;
}

package lazarus.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

import lazarus.GameSounds;
import lazarus.LazarusWorld;
import lazarus.modifiers.AbstractGameModifier;
import lazarus.modifiers.motions.InputController;


public class PlayerShip extends Ship implements Observer{
    protected int lives;
    protected int score;
    protected Point resetPoint;
    public int respawnCounter;
    protected int lastFired=0;
    protected boolean isFiring=false;
    // movement flags
    public int left=0,right=0,up=0,down=0;
    protected String name;

    public PlayerShip(Point location, Point speed, Image img, int[] controls, String name) {
        super(location,speed,100,img);
        resetPoint = new Point(location);
        this.gunLocation = new Point(18,0);
        
        this.name = name;
        motion = new InputController(this, controls);
        lives = 2;
        health = 100;
        strength = 100;
        score = 0;
        respawnCounter=0;
    }

    public void draw(Graphics g, ImageObserver observer) {
    	if(respawnCounter<=0)
    		g.drawImage(img, location.x, location.y, observer);
    	else if(respawnCounter==80){
    		LazarusWorld.getInstance().addClockObserver(this.motion);
    		respawnCounter -=1;
    	}
    	else
    		respawnCounter -= 1;
    }
    
    public void damage(int damageDone){
    	if(respawnCounter<=0)
    		super.damage(damageDone);
    }
    
    public void update(int w, int h) {
    	
    }
    public void startFiring(){
    	isFiring=true;
    }
    
    public void stopFiring(){
    	isFiring=false;
    }
    
    public void fire()
    {
    	if(respawnCounter<=0){
    		GameSounds.play("Resources/snd_explosion1.wav");
    	}
    }
    
    public void die(){
    	this.show=false;
    	lives-=1;
    	if(lives>=0){
    		LazarusWorld.getInstance().removeClockObserver(this.motion);
    		reset();
    	}
    	else{
    		this.motion.delete(this);
    	}
    }
    
    public void reset(){
    	this.setLocation(resetPoint);
    	health=strength;
    	respawnCounter=160;
    }
    
    public Point getPoint(){
    	return this.resetPoint;
    }
    
    public int getLives(){
    	return this.lives;
    }
    
    public int getScore(){
    	return this.score;
    }
    
    public String getName(){
    	return this.name;
    }
    
    public void incrementScore(int increment){
    	score += increment;
    }
    
    public boolean isDead(){
    	if(health<=0)
    		return true;
    	else
    		return false;
    }
    
	public void update(Observable o, Object arg) {
		AbstractGameModifier modifier = (AbstractGameModifier) o;
		modifier.read(this);
	}
}

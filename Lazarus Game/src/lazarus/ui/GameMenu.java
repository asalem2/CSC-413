package lazarus.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.Observable;

import lazarus.LazarusWorld;
import lazarus.SoundPlayer;
import lazarus.game.PlayerShip;
import lazarus.modifiers.AbstractGameModifier;
import lazarus.modifiers.motions.MenuController;


public class GameMenu extends InterfaceObject {
	public static int selection;
	MenuController controller;
	public static boolean waiting;
	
	public GameMenu(){
		selection = 0;
		controller = new MenuController(this);
		waiting = true;
	}
	public void draw(Graphics g2, int x, int y){
		g2.setFont(new Font("Berlin Sans FB", Font.PLAIN, 24));
		if(selection==0)
			g2.setColor(Color.MAGENTA);
		else
			g2.setColor(Color.WHITE);
		g2.drawString("Easy", 200,310);
		if(selection==1)
			g2.setColor(Color.MAGENTA);
		else
			g2.setColor(Color.WHITE);
		g2.drawString("Medium", 200, 350);
		if(selection==2)
			g2.setColor(Color.MAGENTA);
		else
			g2.setColor(Color.WHITE);
		g2.drawString("Hard", 200, 390);
		if(selection==3)
			g2.setColor(Color.MAGENTA);
		else
			g2.setColor(Color.WHITE);
		g2.drawString("Quit", 200, 430);
	}
	
	public void down(){
		if(selection<3)
			selection++;
	}
	
	public void up(){
		if(selection>0)
			selection--;
	}
	
	public void applySelection(){
		LazarusWorld world = LazarusWorld.getInstance();
		Dimension size = world.getSize();
		if(selection == 0){
			selection = 0;
		}
		else if(selection == 1){
			selection = 1;
		}
		else if (selection == 2){
			selection = 2;
		}
		else{
			System.exit(0);
		}
		
		controller.deleteObservers();
		world.removeKeyListener(controller);
		waiting=false;
		LazarusWorld.sp = new SoundPlayer(1, "Resources/Music.wav");
	}
	
	public void update(Observable o, Object arg) {
		AbstractGameModifier modifier = (AbstractGameModifier) o;
		modifier.read(this);
	}
	
	public boolean isWaiting(){
		return this.waiting;
	}
}

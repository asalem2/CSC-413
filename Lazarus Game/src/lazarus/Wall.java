/**
 *  The Wall class is used to create and draw the wall objects
*/

package lazarus;

import java.awt.Image;
import java.awt.Point;
import lazarus.game.BackgroundObject;
import lazarus.game.enemy.Box;

public class Wall extends Box{
	static int firstx;
	static int secondx;
  public Wall(int x, int y, Image image){
	  super(new Point(x * 40, y * 40), new Point(0, 0), y, image);
    
    if (LazarusWorld.first == 1){
    	firstx = x * 40;
    }
    else if (LazarusWorld.first == 2)
    	secondx = x * 40;
  }
 }
  


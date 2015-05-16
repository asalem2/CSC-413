/**
 *  The StoneBox class is used to create and draw the stone type box
*/

package lazarus.game.enemy;

import java.awt.Image;
import java.awt.Point;

import lazarus.LazarusWorld;

public class StoneBox extends Box{
  public StoneBox(int x, int y){
    super(new Point(x, y), new Point(0, 0), 4, (Image)LazarusWorld.sprites.get("StoneBox"));
  }
}

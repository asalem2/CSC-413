/**
 *  The MetalBox class is used to create and draw the metal type box
*/

package lazarus.game.enemy;

import java.awt.Image;
import java.awt.Point;

import lazarus.LazarusWorld;

public class MetalBox extends Box{
  public MetalBox(int x, int y){
    super(new Point(x, y), new Point(0, 0), 3, (Image)LazarusWorld.sprites.get("MetalBox"));
  }
}

/**
 *  The WoodBox class is used to create and draw the wood type box
*/

package lazarus;

import java.awt.Image;
import java.awt.Point;

public class WoodBox extends Box{
  public WoodBox(int x, int y){
    super(new Point(x, y), new Point(0, 0), 2, (Image)LazarusWorld.sprites.get("WoodBox"));
  }
}

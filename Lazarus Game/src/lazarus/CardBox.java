/**
 *  The CardBox class is used to create and draw the cardboard type box
*/

package lazarus;

import java.awt.Image;
import java.awt.Point;

public class CardBox extends Box{
	public CardBox(int x, int y){
        super(new Point(x, y), new Point(0, 0), 1, (Image)LazarusWorld.sprites.get("CardBox"));
    }
}

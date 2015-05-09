/**
 * This is where the background is drawn
*/
package lazarus;

import java.awt.Image;
import java.awt.Point;
import lazarus.game.BackgroundObject;
import lazarus.game.GameObject;

public class LazarusBackground extends BackgroundObject {
  int w;
  int h;
  
  public LazarusBackground(int w, int h, Point speed, Image img){
    super(new Point(0, 0), speed, img);
    setImage(img);
    this.img = img;
    this.w = w;
    this.h = h;
  }
  
  public void update(int w, int h) {}
  
  public boolean collision(GameObject otherObject)
  {
    return false;
  }
}

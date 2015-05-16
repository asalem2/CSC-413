/**
 * LazarusWorld is the the class that keeps everything running until the end. 
 * It holds the main method for running the game
*/

package lazarus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.JFrame;
import lazarus.GameClock;
import lazarus.GameSounds;
import lazarus.GameWorld;
import lazarus.game.BackgroundObject;
import lazarus.game.PlayerShip;
import lazarus.modifiers.AbstractGameModifier;
import lazarus.modifiers.motions.MotionController;
import lazarus.game.enemy.Box;
import lazarus.ui.GameMenu;
import lazarus.ui.InterfaceObject;


public class LazarusWorld extends GameWorld{
private Thread thread;
  private static LazarusWorld game = new LazarusWorld();
  public static final GameSounds sound = new GameSounds();
  public static final GameClock clock = new GameClock();
  static GameMenu menu;
  public LazarusLevel level;
  public static HashMap<String, Image> sprites = GameWorld.sprites;
  private BufferedImage bimg;
  Random generator = new Random();
  int sizeX;
  int sizeY;
  static boolean squished;
  static int times = 0;
  static int first;
  Point mapSize;
  private ArrayList<PlayerShip> players;
  private ArrayList<InterfaceObject> ui;
  private ArrayList<Box> boxes;
  private ArrayList<Wall> walls;
  private ArrayList<Box> fallingboxes;
  private ArrayList<ArrayList<Box>> restedboxes;
  public static HashMap<String, MotionController> motions = new HashMap();
  static boolean gameOver;
  static boolean gameWon;
  boolean gameFinished;
  ImageObserver observer;
  public static SoundPlayer sp;
  private LazarusWorld() {
   
  }
  
  public static LazarusWorld getInstance(){
    return game;
  }
  
  public void init(){
	  setFocusable(true);
	    this.background = new ArrayList();
	    this.players = new ArrayList();
	    this.ui = new ArrayList();
	    this.boxes = new ArrayList();
	    this.walls = new ArrayList();
	    this.fallingboxes = new ArrayList();
	    this.restedboxes = new ArrayList(16);
	    for (int i = 0; i < 16; i++) {
	      this.restedboxes.add(new ArrayList());
	    }
	    squished = false;
	    first = 0;
	    gameOver = false;
	    gameWon = false;
	    gameFinished = false;
    setBackground(Color.white);
    this.observer = this;
	 loadSprites();
   loading();
    
  }
  public void loading(){
	   
	    this.level = new LazarusLevel("Resources/level.txt");
	    //this.level.addObserver(this);
	    clock.addObserver(this.level);
	    this.mapSize = new Point(this.level.w * 40, this.level.h * 40);
	    addBackground(new BackgroundObject[] { new LazarusBackground(this.mapSize.x, this.mapSize.y, GameWorld.getSpeed(), (Image)sprites.get("background")) });
	    this.level.load();
  }
  protected void loadSprites(){
	    sprites.put("background", getSprite("Resources/Background.gif"));
	    sprites.put("wall", getSprite("Resources/Wall.gif"));
	    sprites.put("Mesh", getSprite("Resources/Mesh.gif"));
	    sprites.put("CardBox", getSprite("Resources/CardBox.gif"));
	    sprites.put("WoodBox", getSprite("Resources/WoodBox.gif"));
	    sprites.put("MetalBox", getSprite("Resources/MetalBox.gif"));
	    sprites.put("StoneBox", getSprite("Resources/StoneBox.gif"));
	    sprites.put("StopButton", getSprite("Resources/Button.gif"));
	    sprites.put("Lazarus_afraid", getSprite("Resources/Lazarus_afraid.gif"));
	    sprites.put("Lazarus_jump_left", getSprite("Resources/Lazarus_jump_left.gif"));
	    sprites.put("Lazarus_jump_right", getSprite("Resources/Lazarus_jump_right.gif"));
	    sprites.put("Lazarus_left", getSprite("Resources/Lazarus_left.gif"));
	    sprites.put("Lazarus_right", getSprite("Resources/Lazarus_right.gif"));
	    sprites.put("Lazarus_squished", getSprite("Resources/Lazarus_squished.gif"));
	    sprites.put("Lazarus_stand", getSprite("Resources/Lazarus_stand.png"));
	    sprites.put("Title", getSprite("Resources/Title.gif"));
	    sprites.put("LazarusIcon", getSprite("Resources/lazarus.ico"));
	    sprites.put("player1", getSprite("Resources/Lazarus_stand.png"));
	  }
	  
	  public Image getSprite(String name){
	    URL url = LazarusWorld.class.getResource(name);
	    Image img = Toolkit.getDefaultToolkit().getImage(url);
	    try{
	      MediaTracker tracker = new MediaTracker(this);
	      tracker.addImage(img, 0);
	      tracker.waitForID(0);
	    }
	    catch (Exception localException) {}
	    return img;
	  }
  
	  /**********************************
	  *   These functions GET things	*
	  * 	 from the game world		*
	  ***********************************/
  public int getFrameNumber(){
    return clock.getFrame();
  }
  
  public int getTime(){
    return clock.getTime();
  }
  
  public void removeClockObserver(Observer theObject){
    clock.deleteObserver(theObject);
  }
  
  public ListIterator<BackgroundObject> getBackgroundObjects(){
    return this.background.listIterator();
  }
  
  public ListIterator<PlayerShip> getPlayers(){
    return this.players.listIterator();
  }
  
  public int countPlayers(){
    return this.players.size();
  }
  
  public ListIterator<Box> getFallingboxes(){
    return this.fallingboxes.listIterator();
  }
  
  public int countFallingboxes(){
    return this.fallingboxes.size();
  }
  
  public void setDimensions(int w, int h){
    this.sizeX = w;
    this.sizeY = h;
  }
  
  /********************************
   *   These functions ADD things *
   * 	to the game world	      *
   ********************************/
  
  public void addFallingbox(Box... newObjects){
    Box[] arrayOfBox;
    int j = (arrayOfBox = newObjects).length;
    for (int i = 0; i < j; i++){
      Box box = arrayOfBox[i];
      this.fallingboxes.add(box);
    }
  }
  
  public void removeFallingbox(Box... newObjects){
	  
    Box[] arrayOfBox;
    int j = (arrayOfBox = newObjects).length;
    for (int i = 0; i < j; i++){
      Box box = arrayOfBox[i];
      System.out.print("Removing");
      this.fallingboxes.remove(box);
    }
  }
  
  public void addBackground(BackgroundObject... newObjects){
    BackgroundObject[] arrayOfBackgroundObject;
    int j = (arrayOfBackgroundObject = newObjects).length;
    for (int i = 0; i < j; i++){
      BackgroundObject object = arrayOfBackgroundObject[i];
      this.background.add(object);
    }
  }
  
  public void addPlayer(PlayerShip... newObjects){
    PlayerShip[] arrayOfPlayerShip;
    int j = (arrayOfPlayerShip = newObjects).length;
    for (int i = 0; i < j; i++){
      PlayerShip player = arrayOfPlayerShip[i];
      this.players.add(player);
    }
  }
  
  public void addBackground(Box... cardbox){
    Box[] arrayOfBox;
    int j = (arrayOfBox = cardbox).length;
    for (int i = 0; i < j; i++){
      Box object = arrayOfBox[i];
      this.boxes.add(object);
    }
  }
  
  public void addBackground(Wall... newObjects){
    Wall[] arrayOfWall;
    int j = (arrayOfWall = newObjects).length;
    for (int i = 0; i < j; i++){
      Wall object = arrayOfWall[i];
      this.walls.add(object);
    }
  }
  
  public void addRestedBox(Box... resetdBox){
    Box[] arrayOfBox;
    int j = (arrayOfBox = resetdBox).length;
    for (int i = 0; i < j; i++){
      Box object = arrayOfBox[i];
      Rectangle boxLocation = object.getLocation();
      int boxCol = boxLocation.x / 40;
      ((ArrayList)this.restedboxes.get(boxCol)).add(object);
    }
  }
  
  public void addRestedBox(int Col, Box... resetdBox){
    Box[] arrayOfBox;
    int j = (arrayOfBox = resetdBox).length;
    for (int i = 0; i < j; i++){
      Box object = arrayOfBox[i];
      ((ArrayList)this.restedboxes.get(Col)).add(object);
    }
  }
  
  public void removeRestedBox(Box... resetdBox){
    Box[] arrayOfBox;
    int j = (arrayOfBox = resetdBox).length;
    for (int i = 0; i < j; i++){
      Box object = arrayOfBox[i];
      Rectangle boxLocation = object.getLocation();
      int boxCol = boxLocation.x / 40;
      ((ArrayList)this.restedboxes.get(boxCol)).remove(object);
    }
  }
  
  public ListIterator<Box> getRestedboxesAtCol(int Col){
    return ((ArrayList)this.restedboxes.get(Col)).listIterator();
  }
  
  public void removeRestedBoxInColAtIndex(int Col, int index){
    ((ArrayList)this.restedboxes.get(Col)).remove(index);
  }
  
  public int getNumberOfRestedBoxesInCol(int Col){
    return ((ArrayList)this.restedboxes.get(Col)).size();
  }
  
  public int getNumberOfRestedBoxesAbove(int Col, int LocationY){
    int count = 0;
    ListIterator<Box> boxlist = getRestedboxesAtCol(Col);
    while (boxlist.hasNext()){
      Box nBox = (Box)boxlist.next();
      if (nBox.getY() < LocationY){
        count++;
      }
    }
    return count;
  }
  
  public ListIterator<Wall> getWalls(){
    return this.walls.listIterator();
  }
  
  // this is the main function where game stuff happens!
  // each frame is also drawn here
  public void drawFrame(int w, int h, Graphics2D g2){
	  ListIterator<?> iterator = getBackgroundObjects();

	    while (iterator.hasNext()){
		      BackgroundObject obj = (BackgroundObject)iterator.next();
		      obj.update(w, h);
		      obj.draw(g2, this);
		    }
	    if (menu.isWaiting()){
	    	g2.drawImage((Image)sprites.get("Title"), 75, 0, null);
    		menu.draw(g2, w, h);
    	}
	    else{
        ListIterator<Box> fboxes = getFallingboxes();
        while (fboxes.hasNext()){
          Box fallingBox = (Box)fboxes.next();
          Rectangle fallingLoc = fallingBox.getLocation();
          int colFalling = fallingLoc.x / 40;
          ListIterator<PlayerShip> players = getPlayers();
          while (players.hasNext()){
            Lazarus player = (Lazarus)players.next();
            if (fallingBox.collision(player)){

                squished = true;
              player.die();
            }
          }
          ListIterator<Box> rbox = getRestedboxesAtCol(colFalling);
          boolean fallingStopped = false;
          while (rbox.hasNext()){
            Box restedBox = (Box)rbox.next();
            if (fallingBox.collision(restedBox)) {
              if (restedBox.getStrength() < fallingBox.getStrength()){
                restedBox.hide();
                rbox.remove();
                GameSounds.play("Resources/Crush.wav");
              }
              else{
                rbox.add(fallingBox);
                fboxes.remove();
                fallingStopped = true;
              }
            }
            restedBox.draw(g2, this);
          }
          if ((!fallingStopped) && (fallingBox.getY() > 360)){
            addRestedBox(colFalling, new Box[] { fallingBox });
            fboxes.remove();
          }
        }
      
    
    if (!this.gameFinished){
      iterator = getPlayers();
      while (iterator.hasNext()){
        PlayerShip player = (PlayerShip)iterator.next();
        if (player.isDead()){
        }
        else{
          ListIterator<Box> boxList = this.boxes.listIterator();
          while (boxList.hasNext()){
            Box nbox = (Box)boxList.next();
            nbox.draw(g2, this);
          }
          ListIterator<Box> fboxList = this.fallingboxes.listIterator();
          while (fboxList.hasNext()){
            Box nbox = (Box)fboxList.next();
            nbox.update( w , h);
            nbox.draw(g2, this);
          }
          ListIterator<Wall> wallList = this.walls.listIterator();
          while (wallList.hasNext()){
            Wall nWall = (Wall)wallList.next();
            nWall.draw(g2, this);
          }
          for (int Col = 0; Col < 16; Col++){
            ListIterator<Box> rboxList = getRestedboxesAtCol(Col);
            while (rboxList.hasNext()){
              Box nbox = (Box)rboxList.next();
              if (nbox.collision(player)) {

                  squished = true;
                player.die();
              }
              nbox.draw(g2, this);
            }
          }
        }
      }
      PlayerShip p1 = (PlayerShip)this.players.get(0);
      p1.update(w, h);
      p1.draw(g2, this);
    }
    else{
      g2.setColor(Color.MAGENTA);
      g2.setFont(new Font("Ravie", 0, 40));
      
      if (!this.gameWon){
    	  g2.drawString("Game Over!", this.sizeX / 4, 200);

    	  g2.drawString("You've been SQUISHED", this.sizeX / 4 -120, 300);
    	     
    	  this.thread.interrupt();
      } 
      else{
    	  g2.drawString("  Winner!", this.sizeX / 4, 200);

    	  g2.drawString("You made it to the top", this.sizeX / 4 -145, 300);
    	     
    	  this.thread.interrupt();
      }
    }
  }
  }
  public Graphics2D createGraphics2D(int w, int h){
    Graphics2D g2 = null;
    if ((this.bimg == null) || (this.bimg.getWidth() != w) || (this.bimg.getHeight() != h)){
      this.bimg = ((BufferedImage)createImage(w, h));
    }
    g2 = this.bimg.createGraphics();
    g2.setBackground(getBackground());
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, 
    RenderingHints.VALUE_RENDER_QUALITY);
    g2.clearRect(0, 0, w, h);
    return g2;
  }
  
  public void paint(Graphics g){
    if (this.players.size() != 0){
      clock.tick();
    }
    Dimension windowSize = getSize();
    Graphics2D g2 = createGraphics2D(windowSize.width, windowSize.height);
    drawFrame(windowSize.width, windowSize.height, g2);
    g2.dispose();
    g.drawImage(this.bimg, 0, 0, this);
  }
  
  public void addClockObserver(Observer theObject){
    clock.addObserver(theObject);
  }
  
  public void start(){
    this.thread = new Thread(this);
    this.thread.setPriority(1);
    this.thread.start();
  }
  
  public void run(){
    Thread me = Thread.currentThread();
    while (this.thread == me){
      requestFocusInWindow();
      repaint();
      try{
        Thread.sleep(19L);
      }
      catch (InterruptedException e){
        break;
      }
    }
    try { 
    	Thread.sleep(3000);
    } 
    catch (InterruptedException exc) {
    	/* code to handle what happens if someone didn't want you to sleep*/}
    removeRestedBox();
    removeFallingbox();

	sp.stop();
    LazarusWorld game = getInstance();
    menu = new GameMenu();
    game.init();
    game.start();
  }
  
 
  public boolean isGameOver(){
    return this.gameOver;
  }
  
  public void finishGame(){
    this.gameFinished = true;
  }
  
  public void update(Observable o, Object arg) {
    AbstractGameModifier modifier = (AbstractGameModifier)o;
    modifier.read(this);
  }
  
 
public static void main(String[] argv){
    LazarusWorld game = getInstance();
    JFrame f = new JFrame("Lazarus");
    f.addWindowListener(new WindowAdapter(){
      public void windowGainedFocus(WindowEvent e){
      }
    });
    f.getContentPane().add("Center", game);
    f.pack();
    f.setSize(new Dimension(640, 505));
    game.setDimensions(640, 480);
    menu = new GameMenu();
    game.init();
    f.setVisible(true);
    f.setResizable(false);
    f.setDefaultCloseOperation(3);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    game.start();
  }
}

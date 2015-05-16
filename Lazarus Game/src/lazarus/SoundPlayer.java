package lazarus;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
   private AudioInputStream soundStream; 
   private String soundFile;
   private Clip clip;
   private int type;
   
   
   public SoundPlayer(int type, String soundFile){
       this.soundFile = soundFile;
       this.type = type;
       try{
           soundStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResource(soundFile));
           clip = AudioSystem.getClip();
           clip.open(soundStream);
       }
       catch(Exception e){
           System.out.println(e.getMessage() + "No Sound");
       }
       if(this.type == 1){
           Runnable myRunnable = new Runnable(){
               public void run(){
                   while(true){
                       clip.start();
                       try {
                           Thread.sleep(1000000000);
                           Thread.interrupted();
                       } catch (InterruptedException ex) {
                           Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, null, ex);
                       }
                    }
               }
           };
           Thread thread = new Thread(myRunnable);
           thread.start();
       }
   }
   
   public void play(){
       clip.start();
   }
   public void stop(){
       clip.stop();
   }
}
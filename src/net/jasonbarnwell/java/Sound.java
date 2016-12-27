/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jasonbarnwell.java;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Jason
 */
public class Sound { 
    
    public static final float LOW = -25f;
    public static final float MEDIUM = -10f;
    public static final float HIGH = 0.0f;
    
    
    AudioInputStream inputStream;
    Clip clip;
    
    public Sound(String filePath) throws Exception{
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
    }
    
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
    }
    
    public void loop(int position){
        clip.setFramePosition(position % clip.getFrameLength());
        clip.loop(Clip.LOOP_CONTINUOUSLY); 
    }
    
    public void play(){
        clip.setFramePosition(0);
        clip.start();
    }    
    
    public void stop(){
        clip.flush();
        clip.stop();
    }
    
    public void setVolume(float volume){ 
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);   
    }
    public int getFramePosition(){
        return clip.getFramePosition();
    }
}

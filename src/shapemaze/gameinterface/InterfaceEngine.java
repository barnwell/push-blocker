/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapemaze.gameinterface;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import net.jasonbarnwell.java.Sound;

/**
 *
 * @author Jason
 */
public class InterfaceEngine {
    public final static int LOW = 1;
    public final static int MEDIUM = 2;
    public final static int HIGH = 3;
    
    Font customFont, altFont; 
    Sound backgroundMusic, clickSound, rolloverSound;
    
    public InterfaceEngine(){
        loadFonts();
        loadAudio();
    }
    
    public void changeBackgroundVolume(int option){
        int position = backgroundMusic.getFramePosition();
        backgroundMusic.stop();
        switch(option){
            case LOW:   backgroundMusic.setVolume(Sound.LOW);
                        break; 
            case MEDIUM: backgroundMusic.setVolume(Sound.MEDIUM);
                        break; 
            case HIGH:  backgroundMusic.setVolume(Sound.HIGH);
                        break; 
            default:    break;
        }
        backgroundMusic.loop(position); 
    }
    
    
    private void loadFonts(){
        try { 
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/101! Block LetterZ.ttf")); 
            altFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/GrutchShaded.ttf"));
        } catch (FontFormatException | IOException ex) { }
    }

    private void loadAudio(){
        try {
            backgroundMusic = new Sound("audio/DustyGarage.wav");
            clickSound = new Sound("audio/pack/click2.wav");
            rolloverSound = new Sound("audio/pack/rollover1.wav");
        } catch (Exception ex) {
            System.err.println("JB: Something wrong in setupAudio()");
        }
    }
    
    
    
    
    public Font getCustomFont() {
        return customFont;
    }
     
    public Font getAltFont() {
        return altFont;
    }

    public Sound getBackgroundMusic() {
        return backgroundMusic;
    }

    public Sound getClickSound() {
        return clickSound;
    }

    public Sound getRolloverSound() {
        return rolloverSound;
    }
    
}

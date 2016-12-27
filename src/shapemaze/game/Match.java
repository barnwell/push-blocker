/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapemaze.game;

/**
 *
 * @author Jason
 */
import java.awt.Image; 
import javax.swing.ImageIcon;

public class Match extends ScreenObject {
    String areaImagePath;
        
    public Match(int x, int y, ImageHandler imageHandler) {
        super(x, y);
 
        this.imageHandler = imageHandler;
        ImageIcon iia = this.imageHandler.getOuterShapeImageIcon();
        Image image = iia.getImage();
        this.setImage(image);
    }
}
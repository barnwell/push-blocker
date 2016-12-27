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

public class Shape extends ScreenObject { 
    
    public Shape(int x, int y, ImageHandler imageHandler) {
        super(x, y); 
        ImageIcon iia = imageHandler.getInnerShapeImageIcon();
        Image image = iia.getImage();
        this.setImage(image);
    }

    public void move(int x, int y) {
        int nx = this.x() + x;
        int ny = this.y() + y;
        this.setX(nx);
        this.setY(ny);
    }
}
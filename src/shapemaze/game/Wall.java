/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapemaze.game;

/**
 *
 * @author Jason
 */

import javax.swing.ImageIcon;

public class Wall extends ScreenObject {
    

    public Wall(int x, int y, ImageHandler imageHandler) {
        super(x, y);
        this.imageHandler = imageHandler;
        ImageIcon icon = this.imageHandler.getWallImageIcon(); 
        this.setImage(icon.getImage()); 
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapemaze.game;
 

/**
 *
 * @author Jason
 */
public class AI extends ScreenObject{ 
    public AI(int x, int y, ImageHandler imageHandler) {
        super(x, y);  
        this.imageHandler = imageHandler;
        updateImage();
         
    }
    
    private void updateImage(){
        this.setImage(imageHandler.getAiImageIcon().getImage());
    }
    
    public void move(int x, int y) {
        updateImage();
        int nx = this.x() + x;
        int ny = this.y() + y;
        this.setX(nx);
        this.setY(ny);
    }
}

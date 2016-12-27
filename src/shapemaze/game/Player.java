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

public class Player extends ScreenObject { 
     
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;
    public static final int STANDING = 1;
    public static final int WALKING = 2;
    public static final int PUSHING = 3;
    
    private int action, direction;
    private int livesRemaining;
    
    public Player(int x, int y, ImageHandler imageHandler) {
        super(x, y);  
        this.imageHandler = imageHandler;
        action = ImageHandler.STANDING;
        direction = ImageHandler.RIGHT;
        updateImage();
    }

    public void move(int x, int y) {
        updateImage();
        int nx = this.x() + x;
        int ny = this.y() + y;
        this.setX(nx);
        this.setY(ny);
    }
    
    public void setDirection(int direction){
        this.direction = direction;
        updateImage();
    }

    public void setAction(int action) {
        this.action = action; 
        updateImage();
    }
    
    protected void updateImage(){  
        Image image = imageHandler.getPlayerImageIcon(action, direction).getImage();
        this.setImage(image); 
    }

    public int getLivesRemaining() {
        return livesRemaining;
    }

    public void setLivesRemaining(int livesRemaining) {
        this.livesRemaining = livesRemaining;
    }
    
}
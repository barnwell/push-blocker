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

public class ScreenObject { 
    
    private final int SPACE = 40;
    private int x;
    private int y;
    private Image image;
    protected ImageHandler imageHandler;

    public ScreenObject(int x, int y) {
        this.x = x;
        this.y = y;
         
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLeftCollision(ScreenObject actor) {
        if (((this.x() - SPACE) == actor.x()) &&
            (this.y() == actor.y())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRightCollision(ScreenObject actor) {
        if (((this.x() + SPACE) == actor.x())
                && (this.y() == actor.y())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isTopCollision(ScreenObject actor) {
        if (((this.y() - SPACE) == actor.y()) &&
            (this.x() == actor.x())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isBottomCollision(ScreenObject actor) {
        if (((this.y() + SPACE) == actor.y())
                && (this.x() == actor.x())) {
            return true;
        } else {
            return false;
        }
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapematch.core;

import java.awt.Color;
import javax.swing.JButton;
import net.jasonbarnwell.java.Basics;

/**
 *
 * @author Jason Barnwell
 * 13/0719/2191
 */

public abstract class ShapeTile extends JButton {
    
    public static final int SQUARE = 1;
    public static final int CIRCLE = 2;
    public static final int TRIANGLE = 3;
    
    public static final int COVERED = 1;
    public static final int UNCOVERED = 2;
    public static final int HIDDEN = 3;
        
    private String colour;
    private int sides;
    private int shapeType;
    private String imagePath;
    private int status = 1;
    
    public ShapeTile(String colour, int sides) {
        this.colour = colour;
        this.sides = sides;
        setHorizontalTextPosition(JButton.CENTER);
        setVerticalTextPosition(JButton.BOTTOM);
        setFocusPainted(false);
        setForeground(Color.BLACK);
    }
    
    public void flipTile(){
        if(status == COVERED){
            updateTileStatus(UNCOVERED);
        }
        else if (status == UNCOVERED){
            updateTileStatus(COVERED);
        }
        else {
            System.out.println("flipTile() called on tile that's not covered or uncovered... nothing to worry about");
        }
        
    }
    
    public void coverTile(){
        status = COVERED;
        toggleTileContents();
        setVisible(true);
    }
    
    public void uncoverTile(){
        status = UNCOVERED;
        toggleTileContents();
        setVisible(true);
    }
    
    public void hideTile(){
        status = HIDDEN;
        setVisible(false);
    }

    
    private void setImageIcon(String path){ 
        setIcon(Basics.getScaledImageIcon(path, 55,55));
    }
    
    private void setBackgroundColor(String color){ 
        setBackground(Basics.ConvertStringToColor(color));
    }
    
    private void toggleTileContents(){
        if(status == COVERED){
            setImageIcon("");
            hidePerimeterText();
            setBackgroundColor(null);
        }
        
        else if(status == UNCOVERED){
            showPerimeterText();
            setImageIcon(this.imagePath);
            setBackgroundColor(this.colour);
            
        }
    }
    
    private void updateTileStatus(int newStatus){
        switch (newStatus){
            case COVERED: coverTile();
                break;
            case UNCOVERED: uncoverTile();
                break;
            case HIDDEN: hideTile();
                break;
            default: System.out.println("Something unexpected happened in the ShapeTile updateStatus switch");
                break;
        }
    }
    
    private void showPerimeterText(){
        setText(String.valueOf(getPerimeter()+" cm"));
    }
    
    private void hidePerimeterText(){
        setText("");
    }
    
    public abstract double getPerimeter();

    public String getDescription() {
        String description = "I have "+getSides()+" side(s), my colour is "+getColour()+" and my perimeter is "+getPerimeter();
        return description;
    }
    
    public String getColour() {
        return colour;
    }

    public int getSides() {
        return sides;
    }

    public int getShapeType() {
        return shapeType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getStatus() {
        return status;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }

    public void setImagePath(String ImagePath) {
        this.imagePath = ImagePath;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}

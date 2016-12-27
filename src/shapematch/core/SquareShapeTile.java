/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapematch.core;

/**
 *
 * @author Jason Barnwell
 * 13/0719/2191
 */
public class SquareShapeTile extends ShapeTile {
    
    private int width;
    private int height;
    
    public SquareShapeTile(String colour, int width) {
        super(colour, 4);
        this.width = width;
        this.height = width;
        
        /*kinda static stuff*/
        setShapeType(SQUARE);
        setImagePath("images/square.png");
    }
   
    
    @Override
    public double getPerimeter() {
        return getSides()*width;
    }
    
    @Override
    public String getDescription() {
        String description = super.getDescription();
        description += "\n I am also a square with sides measuring: "+width+", "+height+", "+width+", "+height;
        return description;
    }
}

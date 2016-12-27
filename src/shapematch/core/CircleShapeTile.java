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
public class CircleShapeTile extends ShapeTile {
    
    private int radius;
    
    public CircleShapeTile(String colour, int radius) {
        super(colour, 1);
        this.radius = radius;
        
        /*kinda static stuff*/
        setShapeType(CIRCLE);
        
        setImagePath("images/circle.png");
    }

    public int getRadius() {
        return radius;
    }
    
    @Override
    public double getPerimeter() {
        return 3.14*(radius*2);
    }
    
    @Override
    public String getDescription() {
        String description = super.getDescription();
        description += "\n I am also a circle with radius measuring: "+radius;
        return description;
    }
}

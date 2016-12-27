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
public class TriangleShapeTile extends ShapeTile {
    
    private int sideA;
    private int sideB;
    private int sideC;
    
    public TriangleShapeTile(String colour, int sideA, int sideB, int sideC) {
        //the "super" call here passes values direclty to the parent class's constructor
        //it is similar to calling new ShapeTile(colour, 3)..
        super(colour, 3);
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        
        
        /*kinda static stuff*/
        setShapeType(TRIANGLE);
        setImagePath("images/triangle.png");
    }
    
    @Override
    public double getPerimeter() {
        return sideA+sideB+sideC;
    }
    
    @Override
    public String getDescription() {
        String description = super.getDescription();
        description += "\n I am also a triangle with sides measuring: "+sideA+", "+sideB+", "+sideC;
        return description;
    }
}

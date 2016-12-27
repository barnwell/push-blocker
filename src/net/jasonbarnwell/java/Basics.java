/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jasonbarnwell.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.lang.reflect.Field;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Jason
 */
public class Basics {
    
    public static int randomInt(int max){
        return new Random().nextInt(max);
    }
    
    public static Color ConvertStringToColor(String colorString){
        Color color;
        try {
            Field field = Class.forName("java.awt.Color").getField(colorString);
            color = (Color)field.get(null);
        } catch (Exception e) {
            color = null; // Not defined
        }
        return color;
    }   
    
    
    public static ImageIcon getScaledImageIcon(String path, int width, int height){
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH));
    }
    
    public static void pauseProgram(int time){
        try{ Thread.sleep(time); }catch(InterruptedException ex){}
    }
    
    public class ImagePanel extends JPanel
{
private static final long serialVersionUID = 1L;
private Image image = null;
private int iWidth2;
private int iHeight2;

public ImagePanel(Image image)
{
    this.image = image;
    this.iWidth2 = image.getWidth(this)/2;
    this.iHeight2 = image.getHeight(this)/2;
}


public void paintComponent(Graphics g)
{
    super.paintComponent(g);
    if (image != null)
    {
        int x = this.getParent().getWidth()/2 - iWidth2;
        int y = this.getParent().getHeight()/2 - iHeight2;
        g.drawImage(image,x,y,this);
    }
}
}
    
}


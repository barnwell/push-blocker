/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapemaze.game;
 
import javax.swing.ImageIcon;
import net.jasonbarnwell.java.Basics;

/**
 *
 * @author Jason
 */
public class ImageHandler { 
   
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int LEFT = 4;
    public static final int STANDING = 1;
    public static final int WALKING = 2;
    public static final int PUSHING = 3;
  
    private final String imagesDirectory = "images/"; 
    private final int numberOfMovements = 3, numberOfDirections = 4;  
    private String playerImagesNamePrefix;
    private ImageIcon playerImageIcons[];
    
    private String wallImageFileName;
    private ImageIcon wallImageIcon;
    
    private String gameOverImageFileName;
    private ImageIcon gameOverImageIcon;
      
    private String[] shapePrefixes; 
    private String randomShapePrefix; 
    private ImageIcon randomInnerShape, randomOuterShape; 
    
    private String aiImageFileName;
    private ImageIcon aiImageIcon;
    

    public ImageHandler() {
        setPaths();
        createGameOverImageIcon();
        createPlayerImageIcons();  
        createWallImageIcon();
        createAiImageIcon();
        createShapeImageIcons();
    }
    
    private void setPaths(){ 
        playerImagesNamePrefix = "player-";  
        wallImageFileName = "wall.png";
        aiImageFileName = "ai.png";
        gameOverImageFileName = "gameover.gif";
        shapePrefixes = new String[]{ "triangle", "circle", "square"};
        randomShapePrefix = generateRandomShapePrefix(); 
    }
    
    
    private String generateRandomShapePrefix(){   
        return shapePrefixes[Basics.randomInt(shapePrefixes.length)];
    }
    
    private void createPlayerImageIcons(){  
        playerImageIcons = new ImageIcon[numberOfMovements * numberOfDirections];
        for (int i = 0; i < numberOfMovements; i++) {
           for (int j = 0; j < numberOfDirections; j++) {
               playerImageIcons[(i * numberOfDirections) + j] = makeImageIcon(imagesDirectory+playerImagesNamePrefix + j +"-"+ i+".gif"); 
           }
        } 
    }
    
    public ImageIcon getPlayerImageIcon(int movement, int direction){
        
        int j = -1, i = -1;
        switch(direction){
            case UP:    i = 0; break;
            case RIGHT: i = 1; break;
            case DOWN:  i = 2; break;
            case LEFT:  i = 3; break; 
            default:    System.out.println("Invalid sprite requested from getPlayerImageIcon()"); break; 
        } 
        
        switch(movement){
            case WALKING:   j = 0; break;
            case PUSHING:   j = 1; break; 
            case STANDING:  j = 2; break;
            default:    System.out.println("Invalid sprite requested from getPlayerImageIcon()"); break; 
        } 
         
        return playerImageIcons[j*numberOfDirections + i]; 
    }  

    private void createWallImageIcon() {
        wallImageIcon = makeImageIcon(imagesDirectory+wallImageFileName); 
    }

    public ImageIcon getWallImageIcon() {
        return wallImageIcon;
    }
    
    private void createGameOverImageIcon() {
        gameOverImageIcon = makeImageIcon(imagesDirectory+gameOverImageFileName); 
    }

    public ImageIcon getGameOverImageIcon() {
        return gameOverImageIcon;
    }
    
    private void createAiImageIcon() {
        aiImageIcon = makeImageIcon(imagesDirectory+aiImageFileName); 
    }

    public ImageIcon getAiImageIcon() {
        return aiImageIcon;
    } 
    
    private void createShapeImageIcons(){ 
        randomInnerShape = makeImageIcon(imagesDirectory+ randomShapePrefix +"-inner.png");
        randomOuterShape = makeImageIcon(imagesDirectory+ randomShapePrefix +"-outer.png");  
    } 

    public ImageIcon getOuterShapeImageIcon() {
        return randomOuterShape;
    }

    public ImageIcon getInnerShapeImageIcon() {
        return randomInnerShape;
    }
     
    private ImageIcon makeImageIcon(String path){ 
        return new ImageIcon( path ); 
    }
     
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapemaze.game;

/**
 *
 * @author Jason
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import net.jasonbarnwell.java.Basics;
import net.jasonbarnwell.java.Countdown;

public class MazeEngine extends JPanel { 

    private ImageHandler imageHandler;
    
    private final int OFFSET = 30;
    private final int SPACE = 40;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;
    
    public static final int EASY = 1;
    public static final int NORMAL = 2;
    public static final int HARD = 3;
    
    private int numberOfBots = 10;
    
    private Countdown walkingTimeout;

    private ArrayList<ScreenObject> walls = new ArrayList();
    private ArrayList<Shape> shapes = new ArrayList();
    private ArrayList<Match> areas = new ArrayList();
    private ArrayList<AI> aiBots = new ArrayList();
    private TimerTask huntTimerTask; 
    private Player player;
    private int w = 0;
    private int h = 0;
    private boolean won = false, loss = false;
    private int livesRemaining = 2;
    private Font font;
    private String statusMessage = "";
    private String level =
              "###########################\n"
            + "#            #     #      #\n"
            + "#    #### ##     #   ##   #\n"
            + "#  #   ##    #  #   #     #\n"
            + "#  ### #   #   #          #\n"
            + "#    #   # ###  #       # #\n"
            + "#    # # #    #  ## #     #\n"
            + "#  #   ###  #  #  #    ## #\n"
            + "#    #   # ###  #   #   # #\n" 
            + "#  ### #   #  .   # # #   #\n"
            + "#    # # #      # #       #\n"
            + "#    # # #  ###       #   #\n"
            + "#                   ##    #\n"
            + "## ### ### # ##  ##   $   #\n"
            + "#    #          ##        #\n"
            + "###########################\n";

    public MazeEngine() {
        try { font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/OpenSans-Regular.TTF")); } 
        catch (Exception ex) {}
        statusMessage = "Lives Remaining: " + livesRemaining;
            
        imageHandler = new ImageHandler();
        addKeyListener(new TAdapter()); 
        setFocusable(true);

    }
    
    public void startGame(){
        initWorld();
        hunt();
    }
    
    public void setDifficulty(int difficulty){
        switch(difficulty){
            case EASY: 
                numberOfBots = 5;
                break;
            case NORMAL: 
                numberOfBots = 10;
                break;
            case HARD: 
                numberOfBots = 20;
                break;
            default: System.err.println("Invalid difficulty level selected");
        }
    }

    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }

    public final void initWorld() {
        
        int x = OFFSET;
        int y = OFFSET;
        
        Wall wall;
        Shape b;
        Match a;
        AI bot;
        
        setNumberOfAiBots(numberOfBots);
        
        randomizePlayerPosition();

        for (int i = 0; i < level.length(); i++) {

            char item = level.charAt(i);

            if (item == '\n') {
                y += SPACE;
                if (this.w < x) {
                    this.w = x;
                }

                x = OFFSET;
            } else if (item == '#') {
                wall = new Wall(x, y, imageHandler);
                walls.add(wall);
                x += SPACE;
            } else if (item == '$') {
                b = new Shape(x, y, imageHandler);
                shapes.add(b);
                x += SPACE;
            } else if (item == '.') {
                a = new Match(x, y, imageHandler);
                areas.add(a);
                x += SPACE;
            } else if (item == '@') {
                player = new Player(x, y, imageHandler);
                x += SPACE;
            } else if (item == '!') {
                bot = new AI(x, y, imageHandler);
                aiBots.add(bot);
                x += SPACE;
            } else if (item == ' ') {
                x += SPACE;
            }

            h = y;
        }
    }

    public void buildWorld(Graphics g) {

        g.setColor(new Color(240, 240, 240, 0)); 
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList world = new ArrayList();
        world.addAll(walls);
        world.addAll(areas);
        world.addAll(shapes);
        world.addAll(aiBots);
        world.add(player);

        for (int i = 0; i < world.size(); i++) {

            ScreenObject item = (ScreenObject) world.get(i);

            if ((item instanceof Player)
                    || (item instanceof Shape)) {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            } else {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }
            checkGameOver(g);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintStatus(g);
        buildWorld(g);
    } 
    
    private void paintStatus(Graphics g){
        g.setFont(font.deriveFont(25f));  
        g.setColor(new Color(0, 0, 0));
        g.drawString(statusMessage, OFFSET, 25);
    }

    private void setNumberOfAiBots(int number) {
        level = level.replace("!", " ");
        int totalSpaces = level.length() - level.replace(" ", "").length();  
        
        for(int j = 0; j<number; j++){  
        int randomInt = Basics.randomInt(totalSpaces);
            for (int i=totalSpaces; i > 0; i--) {
                if(level.charAt(i) == ' ' && i <= randomInt){ 
                    level = level.substring(0, i) + "!" + level.substring(i + 1);
                    break;
                }
                else if(level.charAt(i) != ' '){
                    randomInt++;
                }
            }
        }
    }
    
    private void resetPlayer(){ 
        int x=OFFSET, y=OFFSET; 
        randomizePlayerPosition();
        for (int i = 0; i < level.length(); i++) {
            if (level.charAt(i) == '\n') {
                y+=SPACE;
                x = OFFSET;
            }
            else if (level.charAt(i) == '@') {
                player = new Player(x, y, imageHandler);
                x += SPACE; 
            }
            else {
                x += SPACE; 
            } 
        }
        repaint();
    }
     
    
    private void randomizePlayerPosition() {
        int totalSpaces = level.length() - level.replace(" ", "").length(); 
        int randomInt = Basics.randomInt(totalSpaces);
        
        for (int i=0; i < level.length(); i++) {  
            if(level.charAt(i) == '@'){ 
                level = level.substring(0, i) + " " + level.substring(i + 1); 
            }
        } 
        
        for (int i=0; i < totalSpaces; i++) { 
            if(level.charAt(i) == ' ' && i >= randomInt){ 
                level = level.substring(0, i) + "@" + level.substring(i + 1);
                break;
            } 
            else if(level.charAt(i) != ' '){
                totalSpaces++;
            }
        }  
    }

    
    private boolean checkGameOver(Graphics g) { 
        if (won) {
            g.drawImage(imageHandler.getGameOverImageIcon().getImage(), OFFSET, OFFSET, null);
            huntTimerTask.cancel();
            statusMessage = "You Win";
            return true;
        }
        else if(loss){  
            g.drawImage(imageHandler.getGameOverImageIcon().getImage(), OFFSET, OFFSET, null);
            huntTimerTask.cancel();
            statusMessage = "You Lose";
            return true;
        }
        return false;
    }
 
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (won || loss) {
                return;
            }
 
            int key = e.getKeyCode(); 

            
            
            if (key == KeyEvent.VK_LEFT) {  
                walkForAWhile();
                
                if(checkForAiCollision(player, LEFT_COLLISION)){
                    handleAiCollision();
                }
                
                if (checkWallCollision(player, LEFT_COLLISION)) {
                    return;
                }

                if (checkBagCollision(LEFT_COLLISION)) {
                    return;
                }

                player.move(-SPACE, 0);

            } else if (key == KeyEvent.VK_RIGHT) {  
                walkForAWhile();
                if(checkForAiCollision(player, RIGHT_COLLISION)){
                    handleAiCollision();
                }
                if (checkWallCollision(player, RIGHT_COLLISION)) {
                    return;
                }

                if (checkBagCollision(RIGHT_COLLISION)) { 
                    return;
                }

                player.move(SPACE, 0);

            } else if (key == KeyEvent.VK_UP) { 
                walkForAWhile();
                if(checkForAiCollision(player, TOP_COLLISION)){
                    handleAiCollision();
                }
                if (checkWallCollision(player,
                        TOP_COLLISION)) {
                    return;
                }

                if (checkBagCollision(TOP_COLLISION)) { 
                    return;
                }

                player.move(0, -SPACE);

            } else if (key == KeyEvent.VK_DOWN) {
                walkForAWhile(); 
                if(checkForAiCollision(player, BOTTOM_COLLISION)){
                    handleAiCollision();
                }
                if (checkWallCollision(player, BOTTOM_COLLISION)) {
                    return;
                }

                if (checkBagCollision(BOTTOM_COLLISION)) { 
                    return;
                }

                player.move(0, SPACE);

            } else if (key == KeyEvent.VK_R) {
                restartLevel();
            }

            repaint();
        }
    }
    
    private boolean aiCollidesWithAnything(AI bot, int type){
        if (checkWallCollision(bot, type) 
                || checkBotCollision(bot, type) 
                    || checkBagCollisionOnly(bot, type)){
            return true;
        }
        else{
            return false;
        }
    }
    
    private boolean checkForPlayerCollision(ScreenObject actor, int type){  
        if((type == LEFT_COLLISION) && actor.isLeftCollision(player)){
            return true;
        }
        else if((type == RIGHT_COLLISION) && actor.isRightCollision(player)){
            return true;
        }    
        else if((type == TOP_COLLISION) && actor.isTopCollision(player)){
            return true;
        }
        else if((type == BOTTOM_COLLISION) && actor.isBottomCollision(player)){
            return true;
        }  
        return false;  
    }
    
    
    private boolean checkForAiCollision(ScreenObject actor, int type){   
        for(AI bot: aiBots) {
            if((type == LEFT_COLLISION) && actor.isLeftCollision(bot)){
                return true;
            }
            else if((type == RIGHT_COLLISION) && actor.isRightCollision(bot)){
                return true;
            }    
            else if((type == TOP_COLLISION) && actor.isTopCollision(bot)){
                return true;
            }
            else if((type == BOTTOM_COLLISION) && actor.isBottomCollision(bot)){
                return true;
            } 
        } 
        return false;  
    }

    private boolean checkWallCollision(ScreenObject actor, int type) { 
        if (type == LEFT_COLLISION) { 
            if(actor == player) player.setDirection(Player.LEFT);
            
            for (int i = 0; i < walls.size(); i++) {
                Wall wall = (Wall) walls.get(i);
                if (actor.isLeftCollision(wall)) { 
                    if(actor == player) { player.setAction(Player.STANDING); }
                    return true;
                }
            } 
            return false; 
            
        } else if (type == RIGHT_COLLISION) {
            if(actor == player) player.setDirection(Player.RIGHT); 
            for (int i = 0; i < walls.size(); i++) {
                Wall wall = (Wall) walls.get(i);
                if (actor.isRightCollision(wall)) { 
                    if(actor == player) { player.setAction(Player.STANDING); }
                    return true;
                }
            }
            return false;

        } else if (type == TOP_COLLISION) { 
            if(actor == player) player.setDirection(Player.UP);
            for (int i = 0; i < walls.size(); i++) {
                Wall wall = (Wall) walls.get(i);
                if (actor.isTopCollision(wall)) { 
                    if(actor == player) { player.setAction(Player.STANDING); }
                    return true;
                }
            }
            return false;

        } else if (type == BOTTOM_COLLISION) {
            if(actor == player) player.setDirection(Player.DOWN); 
            for (int i = 0; i < walls.size(); i++) {
                Wall wall = (Wall) walls.get(i);
                if (actor.isBottomCollision(wall)) {  
                    if(actor == player) { player.setAction(Player.STANDING); }
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean checkBagCollisionOnly(ScreenObject actor, int type) {
        for(Shape shape: shapes) {
            if((type == LEFT_COLLISION) && actor.isLeftCollision(shape)){
                return true;
            }
            else if((type == RIGHT_COLLISION) && actor.isRightCollision(shape)){
                return true;
            }    
            else if((type == TOP_COLLISION) && actor.isTopCollision(shape)){
                return true;
            }
            else if((type == BOTTOM_COLLISION) && actor.isBottomCollision(shape)){
                return true;
            } 
        } 
        return false; 
    }
    private boolean checkBagCollision(int type) {
        
        if (type == LEFT_COLLISION) {

            for (int i = 0; i < shapes.size(); i++) {

                Shape shape = (Shape) shapes.get(i);
                if (player.isLeftCollision(shape)) {
                player.setAction(Player.PUSHING);

                    for (int j=0; j < shapes.size(); j++) {
                        Shape item = (Shape) shapes.get(j);
                        if (!shape.equals(item)) {
                            if (shape.isLeftCollision(item)) {
                                return true;
                            }
                        }
                        if (checkWallCollision(shape, LEFT_COLLISION) || checkForAiCollision(shape, LEFT_COLLISION)) {
                            return true;
                        }
                    }
                    shape.move(-SPACE, 0);
                    isCompleted();
                }
            }
            return false;

        } else if (type == RIGHT_COLLISION) {

            for (int i = 0; i < shapes.size(); i++) {

                Shape bag = (Shape) shapes.get(i);
                if (player.isRightCollision(bag)) {
                player.setAction(Player.PUSHING);
                    for (int j=0; j < shapes.size(); j++) {

                        Shape item = (Shape) shapes.get(j);
                        if (!bag.equals(item)) {
                            if (bag.isRightCollision(item)) {
                                return true;
                            }
                        }
                        if (checkWallCollision(bag, RIGHT_COLLISION) || checkForAiCollision(bag, RIGHT_COLLISION) ) {
                            return true;
                        }
                    }
                    bag.move(SPACE, 0);
                    isCompleted();                   
                }
            }
            return false;

        } else if (type == TOP_COLLISION) {

            for (int i = 0; i < shapes.size(); i++) {

                Shape bag = (Shape) shapes.get(i);
                if (player.isTopCollision(bag)) {
                player.setAction(Player.PUSHING);
                    for (int j = 0; j < shapes.size(); j++) {

                        Shape item = (Shape) shapes.get(j);
                        if (!bag.equals(item)) {
                            if (bag.isTopCollision(item)) {
                                return true;
                            }
                        }
                        if (checkWallCollision(bag, TOP_COLLISION) || checkForAiCollision(bag, TOP_COLLISION)) {
                            return true;
                        }
                    }
                    bag.move(0, -SPACE);
                    isCompleted();
                }
            }

            return false;

        } else if (type == BOTTOM_COLLISION) {
        
            for (int i = 0; i < shapes.size(); i++) {

                Shape bag = (Shape) shapes.get(i);
                if (player.isBottomCollision(bag)) {
                player.setAction(Player.PUSHING);
                    for (int j = 0; j < shapes.size(); j++) {

                        Shape item = (Shape) shapes.get(j);
                        if (!bag.equals(item)) {
                            if (bag.isBottomCollision(item)) {
                                return true;
                            }
                        }
                        if (checkWallCollision(bag, BOTTOM_COLLISION) || checkForAiCollision(bag, BOTTOM_COLLISION)) {
                            return true;
                        }
                    }
                    bag.move(0, SPACE);
                    isCompleted();
                }
            }
        }

        return false;
    }

    public void isCompleted() {

        int num = shapes.size();
        int compl = 0;

        for (int i = 0; i < num; i++) {
            Shape bag = (Shape) shapes.get(i);
            for (int j = 0; j < num; j++) {
                Match area = (Match) areas.get(j);
                if (bag.x() == area.x()
                        && bag.y() == area.y()) {
                    compl += 1;
                }
            }
        }

        if (compl == num) {
            won = true;
            repaint();
        }
    }

    public void restartLevel() { 
        areas.clear();
        shapes.clear();
        walls.clear();
        aiBots.clear();
        initWorld();
        if (won || loss) {
            won = false;
            loss = false;
        }
    }
    
    private void handleAiCollision(){
        if(livesRemaining>0){
            updateLifeRemaining(livesRemaining-1); 
            resetPlayer(); 
        }
        else{
            loss = true;
        }
    }
    
    private void updateLifeRemaining(int n){
        if(n<livesRemaining&&livesRemaining>0){ 
            livesRemaining = n;
            statusMessage = "Lives Remaining: " + livesRemaining;
        }
    }
    
    private boolean checkBotCollision(AI actor , int type){
        for(AI bot: aiBots) {
            
            if((type == LEFT_COLLISION) && actor.isLeftCollision(bot)){
                return true;
            }
            else if((type == RIGHT_COLLISION) && actor.isRightCollision(bot)){
                return true;
            }    
            else if((type == TOP_COLLISION) && actor.isTopCollision(bot)){
                return true;
            }
            else if((type == BOTTOM_COLLISION) && actor.isBottomCollision(bot)){
                return true;
            } 
        } 
        return false; 
    }
    
    private void huntPlayer(AI bot){
        
        /*if it is not colliding with the wall:
         and player is in that direction
         go there
         */ 
        
            if (!aiCollidesWithAnything(bot, RIGHT_COLLISION) && (player.x() > bot.x())) { 
                    if(checkForPlayerCollision(bot, RIGHT_COLLISION)){
                        handleAiCollision();
                    } 
                bot.move(SPACE, 0);  
                return;
            }
            if (!aiCollidesWithAnything(bot, LEFT_COLLISION) && (player.x() < bot.x())) { 
                    if(checkForPlayerCollision(bot, LEFT_COLLISION)){
                        handleAiCollision();
                    } 
                bot.move(-SPACE, 0);   
                return;
            }
            if (!aiCollidesWithAnything(bot, BOTTOM_COLLISION) && (player.y() > bot.y())) { 
                    if(checkForPlayerCollision(bot, BOTTOM_COLLISION)){
                        handleAiCollision();
                    } 
                bot.move(0, SPACE);     
                return;
            }
            if (!aiCollidesWithAnything(bot, TOP_COLLISION) && (player.y() < bot.y())) { 
                    if(checkForPlayerCollision(bot, TOP_COLLISION)){
                        handleAiCollision();
                    } 
                bot.move(0, -SPACE);     
                return;
            }
              moveAiToRandomSpace(bot);
             
    }
    
    
    
    private void moveAiToRandomSpace(AI bot){   
        int randomInt = Basics.randomInt(3);
        switch (randomInt){  //3 represents the 4 total directions [0,1,2,3] 
            case 0: 
                if (!aiCollidesWithAnything(bot, RIGHT_COLLISION)) {  
                    if(checkForPlayerCollision(bot, RIGHT_COLLISION)){
                        handleAiCollision();
                    } 
                    bot.move(SPACE, 0);  
                    return;
                }
            case 1:  
                if (!aiCollidesWithAnything(bot, LEFT_COLLISION)) { 
                    if(checkForPlayerCollision(bot, LEFT_COLLISION)){
                        handleAiCollision();
                    } 
                    bot.move(-SPACE, 0);   
                    return;
                }
            case 2: 
                if (!aiCollidesWithAnything(bot, BOTTOM_COLLISION)) { 
                    if(checkForPlayerCollision(bot, BOTTOM_COLLISION)){
                        handleAiCollision();
                    } 
                    bot.move(0, SPACE);     
                    return;
                }
            case 3: 
                if (!aiCollidesWithAnything(bot, TOP_COLLISION)) {
                    if(checkForPlayerCollision(bot, TOP_COLLISION)){
                        handleAiCollision();
                    } 
                    bot.move(0, -SPACE);     
                    return;
                }
            default: 
                 if (!checkWallCollision(bot, TOP_COLLISION)||!checkWallCollision(bot, RIGHT_COLLISION)||!checkWallCollision(bot, LEFT_COLLISION)||!checkWallCollision(bot, BOTTOM_COLLISION)){
                   //  moveAiToRandomSpace(bot);
                }
         } 
    }
     
    private void moveAi(){
        for(AI bot: aiBots) { 
            if (Basics.randomInt(1)==1){ 
                moveAiToRandomSpace(bot);
            }
            else{
                huntPlayer(bot); 
            }
        } 
        repaint();
    }
    
    
    private void walkForAWhile() {
        player.setAction(Player.WALKING);
        
        if(walkingTimeout != null){ 
            walkingTimeout.cancel();
        }
        
        walkingTimeout= new Countdown(
                1, 
                1000,
                new Runnable() { @Override public void run() {  }},
                new Runnable() { @Override public void run() { player.setAction(Player.STANDING); }}
                ); 
    }
    
    private void hunt(){
        huntTimerTask = new TimerTask(){ @Override public void run() { moveAi();}  };
        new Timer().scheduleAtFixedRate(huntTimerTask, 1600, 800);
        
    }
}

//shit... this class(es) is big
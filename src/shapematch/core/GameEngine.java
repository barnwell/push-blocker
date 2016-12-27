/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapematch.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.jasonbarnwell.java.Basics;
import net.jasonbarnwell.java.Countdown;

/**
 *
 * @author Jason Barnwell
 * 13/0719/2191
 */


public class GameEngine {
    
    public static final int EASY = 1, MEDIUM = 2, HARD = 3;
    private final int second = 1000;
    
    private LinkedList<ShapeTile> shapeTilesList;
    
    private Countdown countdown;
    
    private ShapeTile tileA, tileB;

    private int[] shapeTileTypes;
    private String[] shapeTileColours;
    
    private int difficulty, numberOfTiles;
    
    private int wins, losses, moves, fewestMoves;
    private long startingTime, timeRemaining, fastestTime;
    
    private TimerTask matchTimerTask = null;
    private JLabel timeRemainingLabel;
    
    public GameEngine(){
        this.shapeTilesList = new LinkedList();this.shapeTileTypes = new int[]{ShapeTile.CIRCLE, ShapeTile.TRIANGLE, ShapeTile.SQUARE};
        this.shapeTileTypes = new int[]{ShapeTile.CIRCLE, ShapeTile.TRIANGLE, ShapeTile.SQUARE};
        this.shapeTileColours = new String[]{"red", "green", "blue", "yellow", "pink", "orange", "magenta", "lightGray", "cyan", "white"};
        
        this.timeRemainingLabel = new JLabel();
        this.tileA = null;
        this.tileB = null;
        this.fastestTime = 300;
        this.fewestMoves = 99;
        this.moves = 0;
        this.losses = 0;
        this.wins = 0;
    }
    
    public long setStartingTimeBasedOnDifficulty(int difficulty){
        switch (difficulty){
            /*act fast - Mwahahahaahhaha */
            case EASY:  startingTime = 30 * 1000;
                        break;
            case MEDIUM: startingTime = 35 * 1000;
                         break;
            case HARD:  startingTime = 120 * 1000;  
                        break;
            default:    System.out.println("Something unexpected happened when setting the dimficulty number of tiles in the GameEngine");
                        break;
        }
        return startingTime;   
    }
    
    public int setNumberOfTilesBasedOnDifficulty(int difficulty){
        switch (difficulty){
            case EASY:  numberOfTiles = 12;
                        break;
            case MEDIUM: numberOfTiles = 16;
                         break;
            case HARD:  numberOfTiles = 36;
                        break;
            default:    System.out.println("Something unexpected happened when setting the difficulty number of tiles in the GameEngine");
                        break;
        }
        return numberOfTiles;
    }
        
    public void newGame(int difficulty){
        tileA = tileB = null;
        moves = 0;
        shapeTilesList = new LinkedList();
        setNumberOfTilesBasedOnDifficulty(difficulty);
        setStartingTimeBasedOnDifficulty(difficulty);
        addRandomTilesToEngine();
        shuffleTiles();
        
        startCountdownTimer();
        timeRemaining = countdown.getTime();
    }
    
    public void restartGame(){
        tileA = tileB = null;
        moves = 0;
        
        for(final ShapeTile tile: shapeTilesList) {
            tile.coverTile();
        }
        
        
        startCountdownTimer();
        timeRemaining = countdown.getTime();
    }

    public JLabel getUpdatedJlabel(){
        timeRemainingLabel.setText(String.valueOf(timeRemaining));
        timeRemainingLabel.revalidate();
        return timeRemainingLabel;
    }
    
    
    
    private ArrayList<ShapeTile> generateRandomShapeTilePair(){  //gotta clean this up l8r
        ShapeTile tileA = null, tileB = null;
        ArrayList<ShapeTile> tiles = new ArrayList();
        
        String colour = shapeTileColours[ Basics.randomInt(shapeTileColours.length) ];
        int type = shapeTileTypes[Basics.randomInt(shapeTileTypes.length)];
        int lengthA = Basics.randomInt(100),
            lengthB = Basics.randomInt(100),
            lengthC = Basics.randomInt(100);
        
        switch(type){

            case ShapeTile.CIRCLE : tileA = new CircleShapeTile(colour, lengthA);
                                    tileB = new CircleShapeTile(colour, lengthA);
                                    break;
            case ShapeTile.SQUARE : tileA = new SquareShapeTile(colour, lengthA);
                                    tileB = new SquareShapeTile(colour, lengthA);
                                    break;
            case ShapeTile.TRIANGLE :   tileA = new TriangleShapeTile(colour, lengthA, lengthB, lengthC);
                                        tileB = new TriangleShapeTile(colour, lengthA, lengthB, lengthC);
                                        break;
            default: System.out.println("Something unexpected happened in the GameEngine Randomizer");
                     break;
        }

        tileA.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                handleTileClick((ShapeTile)e.getSource());
            }
        });
        tileB.addActionListener( new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                handleTileClick((ShapeTile)e.getSource());
            }
        });
        
        tiles.add(tileA);
        tiles.add(tileB);
        
        return tiles;
    }
    
    private void addRandomTilesToEngine(){
        
        for(int i=0; i<numberOfTiles/2; i++){
        final ArrayList<ShapeTile> pair = generateRandomShapeTilePair();
            for(final ShapeTile tile: pair) {
                shapeTilesList.add(tile);
            }
        }
    }

    private void shuffleTiles(){
        Collections.shuffle(shapeTilesList);
    }
    
    private boolean compareTiles(ShapeTile tileA, ShapeTile tileB){
        return ( 
            tileA.getColour().equals(tileB.getColour()) &&
            tileA.getShapeType() == tileB.getShapeType() &&
            tileA.getPerimeter() == tileB.getPerimeter() 
        ) ? true : false;
    }
    
    private void matchTiles(final ShapeTile tileA, final ShapeTile tileB){       
        matchTimerTask = new TimerTask(){ @Override public void run() {   
            if (compareTiles(tileA, tileB) == true){
                tileA.hideTile();
                tileB.hideTile();      
                if(!moreTilesInList()){
                    if(timeRemaining>0){
                        gameOver();
                    }
                }
            }
            else{
                tileA.flipTile();
                tileB.flipTile();
            }
            this.cancel();
        }};
        
        new Timer().schedule(matchTimerTask, 500);
    }
    
    private void  handleTileClick(ShapeTile tile){
        if(tile != tileA){
            tile.flipTile();
            if (tileA == null){
                tileA = tile;
            }
            else {
                tileB = tile;
                matchTiles(tileA, tileB);
                tileA = null;
                tileB = null;
                incrementMoves();
            }
        }
    }
            
    private int incrementMoves(){
        return ++moves;
    }
    
    private boolean moreTilesInList(){
        boolean bool = false;
        for(final ShapeTile tile: shapeTilesList) {
            if(tile.getStatus() != ShapeTile.HIDDEN){
                bool = true;
                break;
            }
        }
        return bool;
    }
    
    private boolean wonGame(){
        return !moreTilesInList();
    }
    
    private void gameOver(){
        countdown.stop();
        
        String message;
        ArrayList<String> words = new ArrayList();
        
        if(wonGame()){
            ++wins;
            words.add("Congratulations!");
            words.add("won");
            words.add("victory");
            if(updateFastestTime(timeRemaining)){
                //
            }
            if(updateFewestMoves(moves)){
                //
            }
        }
        
        else{
            ++losses;
            words.add("Sorry");
            words.add("lost");
            words.add("defeat");
        }
        /* while(sleepy) code(spaghetti); */
        message = words.get(0)+" you "+words.get(1)+" the game!\nYour inevitable "+words.get(2)+" took "+moves+" moves and "+
                   (startingTime/second -timeRemaining)+" seconds. \nPlay again to improve your score!";
        JOptionPane.showMessageDialog(null, message, "You "+words.get(1), JOptionPane.INFORMATION_MESSAGE);
        
        for(final ShapeTile tile: shapeTilesList) {
            if (tile.getStatus() != ShapeTile.HIDDEN){
                tile.hideTile();
                Basics.pauseProgram(300);
            }
        }
    }

    private boolean updateFastestTime(long time){
        boolean bool = false;
        if((startingTime/second - time)<fastestTime){
            fastestTime = startingTime/second - time;
            bool = true;
        }
        return bool;
    }
    
    private boolean updateFewestMoves(int moves){
        boolean bool = false;
            if(moves<fewestMoves){
            fewestMoves = moves;
            bool = true;
        }
        return bool;
    }

    private Countdown startCountdownTimer(){
        if (countdown != null){
            countdown.stop();
            countdown.destroy();
        }
        countdown = new Countdown(
                startingTime/second, 
                second,
                new Runnable() { @Override public void run() { countdownTick(); }},
                new Runnable() { @Override public void run() { gameOver(); }}
                );
        return countdown;
    }
    
    private void countdownTick(){
        timeRemaining = countdown.getTime();
        getUpdatedJlabel();
    }

    
    
    
    
    //getters, setters and all other distracting public methods below
    
    public LinkedList<ShapeTile> getShapeTilesList() {
        return shapeTilesList;
    }

    public int getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getNumberoftiles() {
        return numberOfTiles;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getFewestMoves() {
        return fewestMoves;
    }

    public long getFastestTime() {
        return fastestTime;
    }
    
    

}



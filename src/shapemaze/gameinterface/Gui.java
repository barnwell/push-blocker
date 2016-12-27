/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapemaze.gameinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import net.jasonbarnwell.java.BackgroundPanel;
import net.jasonbarnwell.java.Sound;
import shapemaze.game.MazeEngine;

/**
 *
 * @author Jason
 */
public class Gui extends JFrame implements MouseListener{ 
    
    InterfaceEngine interfaceEngine;
    ClickableLabelListener clickableLabelListener; 
    
    private JPanel currentScreenPanel;
    private BackgroundPanel textureBackgroundPanel, backgroundImagePanel;
    private JPanel menuContentPanel, menuPanel, optionsPanel, helpPanel, statsPanel;
    private JPanel backButtonPanel;
    private JPanel volumeButtonOptionsPanel;
    private JLabel newGameButton, optionsButton, statsButton, helpButton, exitButton, backButton; 
    private JLabel lowVolumeLabel, mediumVolumeLabel, highVolumeLabel, turnOffMusicLabel;
    private JPanel difficultyPanel;
    private JLabel easy, medium, hard;
    private Font tinyFont, tinyAltFont, smallFont, smallAltFont, mediumFont,mediumAltFont, bigAltFont, bigFont, hugeFont, hugeAltFont;
    private MazeEngine newGamePanel;
    private int difficulty = MazeEngine.NORMAL;
    
    private boolean fullscreen = false;
     
    
    public Gui(){
        interfaceEngine = new InterfaceEngine(); 
        clickableLabelListener =  new ClickableLabelListener(interfaceEngine);
        setupFonts();
        setupMainBackground();
        setupCurrentScreenPanel();
        setupBackgrounds();
        setupMenuContentPanel();
        setupMenuPanel();
        setupNewGamePanel();
        setupOptionsPanel();
        setupDifficultyOptionsPanel();
        setupStatsPanel();
        setupHelpPanel();
        setupBackButton();
        setupGameInfoVersion();
        setupFrame();
    }
    private void setupMainBackground(){
        textureBackgroundPanel = new BackgroundPanel(new ImageIcon("images/texture.png").getImage(), BackgroundPanel.TILED);
        textureBackgroundPanel.setLayout(new BorderLayout());
        add(textureBackgroundPanel); 
    }
    
    private void setupCurrentScreenPanel(){ 
        currentScreenPanel = new JPanel();
        currentScreenPanel.setLayout(new BorderLayout());
        currentScreenPanel.setOpaque(false);
        textureBackgroundPanel.add(currentScreenPanel);
    }
    
    private void setupMenuContentPanel(){ 
        menuContentPanel = new JPanel();
        menuContentPanel.setOpaque(false);
        currentScreenPanel.add(menuContentPanel, BorderLayout.CENTER); 
        menuContentPanel.setLayout(new BoxLayout(menuContentPanel, BoxLayout.Y_AXIS)); 
        menuContentPanel.add(Box.createRigidArea(new Dimension(50,30))); 
    }
    
    private void setupGameInfoVersion(){
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        FlowLayout f=new FlowLayout(FlowLayout.LEFT);
        f.setAlignOnBaseline(true);
        panel.setLayout(f);
        JLabel name = new JLabel("ShapeMaze");
        name.setFont(hugeAltFont);
        name.setForeground(Color.lightGray);
        JLabel version = new JLabel(" v0.9"); 
        version.setFont(bigAltFont);
        version.setForeground(Color.lightGray);
        
        
        panel.add(name);
        panel.add(version);
        textureBackgroundPanel.add(panel, BorderLayout.SOUTH);   
    }
    
    private void setupMenuPanel(){
        menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        addMenuPanelItems();
        menuContentPanel.add(menuPanel, BorderLayout.CENTER);
    }
    
    private void addMenuPanelItems(){ 
        
        newGameButton = new JLabel("New Game");
        newGameButton.setFont(bigFont);
        newGameButton.addMouseListener(clickableLabelListener); 
        newGameButton.addMouseListener(this); 
        
        optionsButton = new JLabel("Options");
        optionsButton.setFont(bigFont);
        optionsButton.addMouseListener(clickableLabelListener);
        optionsButton.addMouseListener(this);
        
        statsButton = new JLabel("Stats");
        statsButton.setFont(bigFont);
        statsButton.addMouseListener(clickableLabelListener);
        statsButton.addMouseListener(this);
        
        helpButton = new JLabel("Help");
        helpButton.setFont(bigFont);
        helpButton.addMouseListener(clickableLabelListener);
        helpButton.addMouseListener(this);
        
        exitButton = new JLabel("Exit");
        exitButton.setFont(bigFont);
        exitButton.addMouseListener(clickableLabelListener);
        exitButton.addMouseListener(this);
        
        menuPanel.add(newGameButton); 
        menuPanel.add(Box.createRigidArea(new Dimension(0,10))); 
        menuPanel.add(optionsButton);
//        menuPanel.add(Box.createRigidArea(new Dimension(0,10)));
//        menuPanel.add(statsButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0,10)));
        menuPanel.add(helpButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0,10)));
        menuPanel.add(exitButton);
    }
    
    private void setupNewGamePanel(){
        newGamePanel = new MazeEngine();
        newGamePanel.setDifficulty(difficulty);
        newGamePanel.setOpaque(false);
        newGamePanel.setVisible(false);   
    }
    
    private void setupOptionsPanel(){
        optionsPanel = new JPanel();
        optionsPanel.setOpaque(false); 
        optionsPanel.setVisible(false);
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        
        JPanel volumeOptionsPanel = new JPanel();
        volumeOptionsPanel.setOpaque(false); 
        volumeOptionsPanel.setLayout(new BoxLayout(volumeOptionsPanel, BoxLayout.Y_AXIS));
        
        volumeButtonOptionsPanel = new JPanel();
        volumeButtonOptionsPanel.setOpaque(false); 
        volumeButtonOptionsPanel.setLayout(new BoxLayout(volumeButtonOptionsPanel, BoxLayout.Y_AXIS));
        
        JLabel optionsLabel = new JLabel("Options"); 
        optionsLabel.setFont(bigFont); 
        
        JLabel volumeLabel = new JLabel("Volume"); 
        volumeLabel.setFont(mediumFont); 
        
        lowVolumeLabel = new JLabel("Low");
        lowVolumeLabel.setFont(smallFont); 
        lowVolumeLabel.addMouseListener(clickableLabelListener);
        lowVolumeLabel.addMouseListener(this);
        
        mediumVolumeLabel = new JLabel("Medium");
        mediumVolumeLabel.setFont(smallFont); 
        mediumVolumeLabel.addMouseListener(clickableLabelListener);
        mediumVolumeLabel.addMouseListener(this);
        
        highVolumeLabel = new JLabel("High");
        highVolumeLabel.setFont(smallFont); 
        highVolumeLabel.addMouseListener(clickableLabelListener);
        highVolumeLabel.addMouseListener(this);
        
        turnOffMusicLabel = new JLabel("Off");
        turnOffMusicLabel.setFont(smallFont); 
        turnOffMusicLabel.addMouseListener(clickableLabelListener);
        turnOffMusicLabel.addMouseListener(this);
        
        volumeButtonOptionsPanel.add(Box.createRigidArea(new Dimension(0,15)));
        volumeButtonOptionsPanel.add(lowVolumeLabel);
        volumeButtonOptionsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        volumeButtonOptionsPanel.add(mediumVolumeLabel);
        volumeButtonOptionsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        volumeButtonOptionsPanel.add(highVolumeLabel);
        volumeButtonOptionsPanel.add(Box.createRigidArea(new Dimension(0,10)));
        volumeButtonOptionsPanel.add(turnOffMusicLabel);
        
        volumeOptionsPanel.add(volumeLabel);
        volumeOptionsPanel.add(volumeButtonOptionsPanel); 
        optionsPanel.add(optionsLabel);
        optionsPanel.add(Box.createRigidArea(new Dimension(0,20)));
        optionsPanel.add(volumeOptionsPanel);
        menuContentPanel.add(optionsPanel);
    } 
    
    private void setupDifficultyOptionsPanel() {
        difficultyPanel = new JPanel();
        difficultyPanel.setVisible(true);
        difficultyPanel.setOpaque(false);
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
        
        JLabel difficultyLabel = new JLabel("Difficulty"); 
        difficultyLabel.setFont(mediumFont); 
        
        easy = new JLabel("Easy");
        easy.setFont(smallFont); 
        easy.addMouseListener(clickableLabelListener);
        easy.addMouseListener(this);
        
        medium = new JLabel("Medium");
        medium.setFont(smallFont); 
        medium.addMouseListener(clickableLabelListener);
        medium.addMouseListener(this);
        
        hard = new JLabel("Hard");
        hard.setFont(smallFont); 
        hard.addMouseListener(clickableLabelListener);
        hard.addMouseListener(this); 
        
        optionsPanel.add(Box.createRigidArea(new Dimension(0,20)));
        optionsPanel.add(difficultyLabel); 
        
        difficultyPanel.add(Box.createRigidArea(new Dimension(0,15)));
        difficultyPanel.add(easy);
        difficultyPanel.add(Box.createRigidArea(new Dimension(0,10)));
        difficultyPanel.add(medium);
        difficultyPanel.add(Box.createRigidArea(new Dimension(0,10)));
        difficultyPanel.add(hard); 
        
        optionsPanel.add(difficultyPanel);
        
    }
    
    private void setupHelpPanel(){
        helpPanel = new JPanel();
        helpPanel.setOpaque(false);
        helpPanel.setVisible(false);
        helpPanel.setLayout(new GridLayout(0,1));
        
        JLabel helpPanelTitle = new JLabel("Help", JLabel.CENTER);
        helpPanelTitle.setFont(bigFont);  
        JTextArea helpDescriptionLabel = new JTextArea(); 
        helpDescriptionLabel.setFont(smallFont); 
        helpDescriptionLabel.setOpaque(false); 
        helpDescriptionLabel.setText("For immediate help, please call 911 or your nearest police station.");  
        helpDescriptionLabel.setEditable(false);
        helpDescriptionLabel.setWrapStyleWord(true);
        helpDescriptionLabel.setLineWrap(true); 
        
        helpPanel.add(helpPanelTitle, Component.CENTER_ALIGNMENT);
        helpPanel.add(helpDescriptionLabel);
        
        menuContentPanel.add(helpPanel);
    }
    
    private void setupStatsPanel(){
        statsPanel = new JPanel();
        statsPanel.setOpaque(false);
        statsPanel.setVisible(false);
        statsPanel.setLayout(new GridLayout(0,1));
        
        JLabel statsPanelTitle = new JLabel("Stats", JLabel.CENTER);
        statsPanelTitle.setFont(bigFont);   
        
        statsPanel.add(statsPanelTitle, Component.CENTER_ALIGNMENT);  
        menuContentPanel.add(statsPanel);
    }
    
    private void setupFonts(){
        hugeFont = interfaceEngine.getCustomFont().deriveFont(72f);
        bigFont = interfaceEngine.getCustomFont().deriveFont(48f);
        mediumFont = interfaceEngine.getCustomFont().deriveFont(32f);
        smallFont = interfaceEngine.getCustomFont().deriveFont(24f);
        tinyFont = interfaceEngine.getCustomFont().deriveFont(16f);
        
        hugeAltFont = interfaceEngine.getAltFont().deriveFont(72f);
        bigAltFont = interfaceEngine.getAltFont().deriveFont(48f);
        mediumAltFont = interfaceEngine.getAltFont().deriveFont(32f);
        smallAltFont = interfaceEngine.getAltFont().deriveFont(24f);
        tinyAltFont = interfaceEngine.getAltFont().deriveFont(16f);
    }
    
    private void setupBackgrounds(){
        backgroundImagePanel = new BackgroundPanel(new ImageIcon("images/labyrinth.png").getImage(), BackgroundPanel.ACTUAL, BackgroundPanel.RIGHT_ALIGNMENT, BackgroundPanel.BOTTOM_ALIGNMENT);
        currentScreenPanel.add(backgroundImagePanel, BorderLayout.EAST);
        
    }
    
    private void setupBackButton(){
        backButtonPanel = new JPanel();
        backButtonPanel.setOpaque(false);
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
        
        backButton = new JLabel("Back"); 
        backButton.setVisible(false);
        backButton.setFont(mediumFont);
        backButton.addMouseListener(clickableLabelListener);
        backButton.addMouseListener(this);
        backButtonPanel.add(Box.createRigidArea(new Dimension(20,80)));
        backButtonPanel.add(backButton); 
        innerBackButton(backButtonPanel);
    }
    
    private void innerBackButton(JPanel backButtonPanel){ 
        menuContentPanel.add(backButtonPanel, BorderLayout.SOUTH);
    }
    
    private void outerBackButton(JPanel backButtonPanel){
        textureBackgroundPanel.add(backButtonPanel, BorderLayout.LINE_START);
    }
    

    private void setupFrame(){ 
        setUndecorated(true);
        if(fullscreen){ 
            try{ UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
            catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {} 
            
            setResizable(false);
            validate();
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
        }
        else{
            setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
        } 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        interfaceEngine.getBackgroundMusic().setVolume(Sound.LOW);
        interfaceEngine.getBackgroundMusic().loop();
    }
     
    private void handleVolumeChange(MouseEvent e){
        if(e.getComponent().getParent()==volumeButtonOptionsPanel){
           if(e.getComponent() == lowVolumeLabel){
               interfaceEngine.changeBackgroundVolume(InterfaceEngine.LOW); 
           }
           if(e.getComponent() == mediumVolumeLabel){
               interfaceEngine.changeBackgroundVolume(InterfaceEngine.MEDIUM); 
           }
           if(e.getComponent() == highVolumeLabel){
               interfaceEngine.changeBackgroundVolume(InterfaceEngine.HIGH); 
           }
           if(e.getComponent() == turnOffMusicLabel){
               interfaceEngine.getBackgroundMusic().stop();
           }
       }
    }
    
    
    private void handleDifficultyChange(MouseEvent e) {
        if(e.getComponent().getParent()==difficultyPanel){
           if(e.getComponent() == easy){
               difficulty = MazeEngine.EASY;
           }
           else if(e.getComponent() == medium){
               difficulty = MazeEngine.NORMAL;
           }
           else if(e.getComponent() == hard){
               difficulty = MazeEngine.HARD;
           } 
        }
    }
    
    private void showHomePage(){ 
        
        innerBackButton(backButtonPanel);
        currentScreenPanel.setVisible(true);
        menuPanel.setVisible(true); 
        newGamePanel.setVisible(false);
        optionsPanel.setVisible(false);
        statsPanel.setVisible(false); 
        helpPanel.setVisible(false); 
        hideBackButton();
    }
    
        
    private void showNewGamePanel(){ 
        outerBackButton(backButtonPanel);
        setupNewGamePanel(); 
        textureBackgroundPanel.add(newGamePanel);
        currentScreenPanel.setVisible(false);
        newGamePanel.setVisible(true);
        newGamePanel.requestFocus(); 
        newGamePanel.startGame();
        hideHomePage();
    }
    
    private void showOptionsPanel(){ 
        optionsPanel.setVisible(true);
        hideHomePage();
    }
    
    private void showHelpPanel(){ 
        helpPanel.setVisible(true);
        hideHomePage();
    } 
    
    private void showStatsPanel(){ 
        statsPanel.setVisible(true);
        hideHomePage();
    }  
    
    private void hideHomePage(){ 
        menuPanel.setVisible(false);
        showBackButton();
    }
    
    private void showBackButton(){
        backButton.setVisible(true);
    }
    
    private void hideBackButton(){ 
        backButton.setVisible(false);
    }  
    
    @Override
    public void mouseClicked(MouseEvent e) {   
        if(e.getComponent()==newGameButton){
            showNewGamePanel();
        } 
        
        if(e.getComponent().getParent()==volumeButtonOptionsPanel){
            handleVolumeChange(e);
        }
        
        if(e.getComponent().getParent()==difficultyPanel){
            handleDifficultyChange(e);
        }
        
        if(e.getComponent()==optionsButton){
            showOptionsPanel();
        }
        
        if(e.getComponent()==helpButton){
            showHelpPanel();
        }
        if(e.getComponent()==statsButton){
            showStatsPanel();
        }
                
        if(e.getComponent()==backButton){
            showHomePage();
        }
       
        if(e.getComponent()==exitButton){
            System.exit(0);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) {  }


   
}

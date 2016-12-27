/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapemaze.game;

import javax.swing.JFrame;

/**
 *
 * @author Jason
 */
public final class Sandbox extends JFrame {

    //private final int OFFSET = 30;

    public Sandbox() {
        InitUI();
    }

    public void InitUI() {
        MazeEngine board = new MazeEngine();
        board.setDifficulty(MazeEngine.NORMAL);
        board.startGame();
        add(board);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1150, 750);
        setLocationRelativeTo(null);
        setTitle("Sokoban");
    }


    public static void main(String[] args) {
        Sandbox sokoban = new Sandbox();
        sokoban.setVisible(true);
    }
}
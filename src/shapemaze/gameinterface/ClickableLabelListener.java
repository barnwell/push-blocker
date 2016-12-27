/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shapemaze.gameinterface;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Jason
 */
public class ClickableLabelListener implements MouseListener{
    InterfaceEngine interfaceEngine;

    public ClickableLabelListener(InterfaceEngine interfaceEngine){
        this.interfaceEngine = interfaceEngine;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) { 
        interfaceEngine.getClickSound().play(); 
    }
    
    @Override
    public void mousePressed(MouseEvent e) {  
        e.getComponent().setForeground(Color.darkGray); 
    }

    @Override
    public void mouseReleased(MouseEvent e) { 
            e.getComponent().setForeground(Color.gray); 
    }

    @Override
    public void mouseEntered(MouseEvent e) {  
            interfaceEngine.getRolloverSound().play();
            e.getComponent().setForeground(Color.gray);
            e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    

    @Override
    public void mouseExited(MouseEvent e) {  
            interfaceEngine.getRolloverSound().stop();
            e.getComponent().setForeground(Color.black);
            e.getComponent().setCursor(Cursor.getDefaultCursor());
    }
} 
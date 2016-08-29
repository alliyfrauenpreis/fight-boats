/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsinthenight;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author allisonfrauenpreis
 */
public class PiecesPanel {
    
    private static PiecesPanel instance = null;
    JPanel piecesPanel = new JPanel();
    ArrayList<JButton> pieceButtons;
    JButton activeButton;
    
    private PiecesPanel(){
    
    }
    
    public static PiecesPanel getInstance(){
        
        if (instance == null){
            instance = new PiecesPanel();
        }
        
        return instance;
        
    }
    
    public void setup(){
        
        // create a new panel and add buttons to it for each pieces
        piecesPanel = new JPanel();
        pieceButtons = new ArrayList<JButton>();
        
        ArrayList<Piece> pieces = GameController.getInstance().playerBoard.pieces;
        
        for (Piece p : pieces){ 
            
            int size = p.getSize();
            JButton newButton = new JButton();
            newButton.setMinimumSize(new Dimension(10,10*size));
            ImageIcon imageIcon = new ImageIcon(new BufferedImage(10, 10*size, BufferedImage.TYPE_INT_ARGB));
            newButton.setIcon(imageIcon);
            newButton.addActionListener(new PieceButtonListener(size));
            pieceButtons.add(newButton);
            piecesPanel.add(newButton);
        }
    }
    
    public JPanel getPanel(){
        
        return piecesPanel;
    }
    
    // disable all piece buttons
    public void disable(){
        
        for (JButton b : pieceButtons ){
            b.setEnabled(false);
        }
    }
    
    // enable all piece buttons
    public void enable(){
        
        for (JButton b : pieceButtons ){
            b.setEnabled(true);
        }
    }
}
    
    
class PieceButtonListener implements ActionListener{

    int buttonSize;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // save selected piece
        System.out.println("saving piece size" + buttonSize);
        GameController.getInstance().savePieceToSetup(buttonSize);
        
    }
    
    public PieceButtonListener(int s){
        
       buttonSize = s;
        
    }
}

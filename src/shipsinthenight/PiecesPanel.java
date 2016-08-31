/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsinthenight;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import static shipsinthenight.pieceType.*;

/**
 *
 * @author allisonfrauenpreis
 */
public class PiecesPanel {
    
    private static PiecesPanel instance = null;
    JPanel piecesPanel = new JPanel();
    ArrayList<JButton> pieceButtons;
    JButton activeButton;
    ArrayList<Piece> pieces;
    int[] sizeArray = new int[5];
    
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
        sizeArray[0] = 5;
        sizeArray[1] = 4;
        sizeArray[2] = 3;
        sizeArray[3] = 2;
        sizeArray[4] = 2;
        
        pieces = new ArrayList<Piece>();
        pieces.add(new Piece(AIR_CARRIER));
        pieces.add(new Piece(BATTLESHIP));
        pieces.add(new Piece(SUB));
        pieces.add(new Piece(DESTROYER));
        pieces.add(new Piece(PATROL));
        
        
        for (Piece p : pieces){ 
            
            int size = p.getSize();
            JButton newButton = new JButton();
            newButton.setMinimumSize(new Dimension(10,10*size));
            ImageIcon imageIcon = new ImageIcon(new BufferedImage(10, 10*size, BufferedImage.TYPE_INT_ARGB));
            newButton.setIcon(p.icon);
            newButton.setText(Integer.toString(p.size));
            newButton.setHorizontalTextPosition(CENTER);
            newButton.setVerticalTextPosition(CENTER);
            newButton.setFont(new Font("Courier New", Font.BOLD, 20));
            newButton.setForeground(Color.WHITE);
            newButton.setBorder(BorderFactory.createEmptyBorder());
            newButton.addActionListener(new PieceButtonListener(size, newButton, this));
            pieceButtons.add(newButton);
            piecesPanel.add(newButton);
        }
        
        GameController.getInstance().pieceToSetup = 5;
        piecesPanel.setBackground(new Color (1, 22, 49));
        
    }
    
    
    // update pieces to reflect set ones when switching players
    public void update(GameBoard currentBoard){
        
        /*
        ArrayList<Piece> currentPieces = currentBoard.pieces;
        
        for (int i = 0; i < currentPieces.size(); i++){
            
            if (currentPieces.get(i).isSet()){
                
                pieceButtons.get(i).setEnabled(false);
                
            } else {
                pieceButtons.get(i).setEnabled(true);
            }
        }
        
        */
        
    }
    
    
    public void updateCounts(GameBoard currentBoard){
        ArrayList<Piece> currentPieces = currentBoard.pieces;
        
        for (int i = 0; i < pieceButtons.size(); i++){
            if (currentPieces.get(i).sunk){
                pieceButtons.get(i).setEnabled(false);
                pieceButtons.get(i).setText("X");
            } else {
                System.out.println("NOT SUNK!");
                pieceButtons.get(i).setEnabled(true);
                pieceButtons.get(i).setText(Integer.toString(currentPieces.get(i).size - currentPieces.get(i).hits));
            }
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
    
    // disables saved active button
    public void disableButton(){
        
        activeButton.setEnabled(false);
        
        boolean ready = true;
        
        for (JButton b : pieceButtons ){
            if (b.isEnabled()){
                System.out.println("NOT READY");
                ready = false;
            }
        }
        
        if (ready){
            OptionsPanel.getInstance().ready.setEnabled(true);
        }
    }
}
    
    
class PieceButtonListener implements ActionListener{

    int buttonSize;
    JButton pieceButton;
    PiecesPanel piecesPanel;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // save selected piece
        System.out.println("saving piece size" + buttonSize);
        GameController.getInstance().savePieceToSetup(buttonSize);
        piecesPanel.activeButton = pieceButton;
        
        for (JButton b : piecesPanel.pieceButtons){
            
            b.setBorder(BorderFactory.createEmptyBorder());
            
        }
        
        pieceButton.setBorder(new LineBorder(Color.white));
        
        
    }
    
    public PieceButtonListener(int s, JButton button, PiecesPanel panel){
        
       buttonSize = s;
       pieceButton = button;
       piecesPanel = panel;
        
    }
}

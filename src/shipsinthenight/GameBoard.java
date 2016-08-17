/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsinthenight;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author allisonfrauenpreis
 * additional credit for some code to: http://stackoverflow.com/questions/21077322/create-a-chess-board-with-jpanel
 * 
 * This class handles the data and the GUI of the opponent and player boards.
 * In a future release, the data and the GUI components may be separated to better reflect the MVC paradigm.
 */

public class GameBoard {
    
    ArrayList<ArrayList> boardValues;
    ArrayList markerLocations = new ArrayList();
    ArrayList<Piece> pieces = new ArrayList();
    JPanel boardPanel;
    JButton [][] boardButtons = new JButton [10][10];
    Position selected = null;
    
    
    public GameBoard(){
        
        // create a new list of lists that represents board
        // 0 means no piece, 1 means piece
        // create the panel for displaying the board in the gui
        
        boardPanel = new JPanel(new GridLayout(0,10));
        boardPanel.setBorder(new EmptyBorder(20,20,20,20));
        boardValues = new ArrayList();
        
        // below is pulled from SO link (see comments above)
        Insets margin = new Insets(0,0,0,0);
        
        // add top indexes
        for (int i = 1; i <= 10; i++){
            boardPanel.add(new JLabel(String.valueOf(i), SwingConstants.CENTER));   
        }
        
        
        // create rows and columns
        for (int i = 0; i <= 9; i++){
            
            ArrayList thisRow = new ArrayList();
            
            for (int j = 0; j <= 9; j++){
                
                // below is pulled in part from SO link (see comments above)
                thisRow.add(j, 0);
                JButton button = new JButton();
                button.setMargin(margin);
                ImageIcon imageIcon = new ImageIcon(new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB));
                button.setIcon(imageIcon);
                button.setBorder(new LineBorder(Color.BLACK, 2));
                button.addActionListener(new BoardButtonListener(i, j, button, this));
                boardButtons[i][j] = button;
                boardPanel.add(boardButtons[i][j]);
                
            }
            
            boardValues.add(i,thisRow);
            
        }
       
        boardPanel.setMinimumSize(new Dimension(400,500));
    }
    
    // getter for the GUI board
    public JPanel getBoard(){
        
        return boardPanel;
    }
    
    // adds a piece to the positions that the piece holds
    void addPiece(Piece piece){
        
        if (piece.isSet()){
            
            pieces.add(piece);
            ArrayList<Position> piecePositions = piece.getPositions();

            for (int i = 0; i < piecePositions.size(); i++){
                
                int row = piecePositions.get(i).getRow();
                int col = piecePositions.get(i).getCol();
                
                // get the current row, update the column
                ArrayList rowList = boardValues.get(row);
                rowList.set(col, 1);
                
                // also update the GUI
                boardButtons[row][col].setBorder(new LineBorder(Color.CYAN, 2));
                
            }

        } else {
            
            System.out.println("piece improperly set!");
        }
        
    }
    
    // add a hit marker to the board; returns true if this results in sunken ship
    public boolean addHit(Position pos){
        
         boolean isHit = false;
         int row = pos.getRow();
         int col = pos.getCol();
                
         ArrayList rowList = boardValues.get(row);
         rowList.set(col, -1);
         
        
        // check all the pieces to add hit & check for sinking
        for (Piece p : pieces){
            
            if (p.hasPosition(pos)){
                
                p.addHit();
                boardValues.get(row).set(col, -2);
                isHit = true;
                
                if (p.sunk == true){
                    
                    System.out.println("SINK!");
                    
                    // set this piece's spaces to all yellow
                    for (Position piecePosition : p.position){
                        
                        boardButtons[piecePosition.row][piecePosition.column].setBorder(new LineBorder(Color.YELLOW,2));
                        boardButtons[piecePosition.row][piecePosition.column].setEnabled(false);
                    }
                    return true;
                }
            }
            
          // set color of space according to whether it was a hit or a miss
          if (isHit){
              boardButtons[row][col].setBorder(new LineBorder(Color.RED, 1));
          }else {
              boardButtons[row][col].setBorder(new LineBorder(Color.GRAY, 1));
          }
          
          // disable the button
          boardButtons[row][col].setEnabled(false);
        }
        
        
        printDebugBoard();
        return false;
    }
    
    // checks if space is occupied; returns 1 if so, 0 otherwise
    public int checkSpace(Position pos){
        
         if ((int) boardValues.get(pos.getRow()).get(pos.getCol()) == -2){
             
            return 1;
            
         } else { 
             
            return 0;
         }
         
    }
    
    // prints representation of board to console; 1 for occupied, 0 for empty, -1 or -2 for sunk/hits
    void printDebugBoard(){
        
        
        for (int x = 0; x <= 9; x++){
            
            for (int j = 0; j <= 9; j++){
                
                System.out.print(boardValues.get(x).get(j) + " ");
            }
            
            System.out.println("");
        }
        
    }
    
    // checks win condition
    public boolean win(){
        
        for (Piece p: pieces){
            if (!p.sunk){
                return false;
            }
        }
        
        return true;
    }
    
}

// listener for each JButton in GUI of board
class BoardButtonListener implements ActionListener{

    int row, col;
    JButton button;
    GameBoard board;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // if there's no previously selected piece, set this one selected
        if (board.selected == null){
            
            board.selected = new Position(row,col);
            button.setBorder(new LineBorder(Color.MAGENTA));
            OptionsPanel.getInstance().fire.setEnabled(true);
        
        } 
            // if we're reselecting the same piece, deselect it
            else if (row == board.selected.row && col == board.selected.column){
                button.setBorder(new LineBorder(Color.BLACK, 2));
                board.selected = null;
                OptionsPanel.getInstance().fire.setEnabled(false);

            } else {

                // only change the color if this piece hasn't been attacked before
                if ((int)board.boardValues.get(board.selected.row).get(board.selected.column) > -1) {
                    board.boardButtons[board.selected.row][board.selected.column].setBorder(new LineBorder(Color.BLACK));
                }
                board.selected = new Position(row,col);
                button.setBorder(new LineBorder(Color.MAGENTA));
                OptionsPanel.getInstance().fire.setEnabled(true);
            }
    }
    
    public BoardButtonListener(int r, int c, JButton b, GameBoard gb){
        
        row = r;
        col = c;
        button = b;
        board = gb;
    }
}

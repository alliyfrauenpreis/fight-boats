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
import static shipsinthenight.GameState.SETUP;
import static shipsinthenight.pieceType.*;

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
    ArrayList highlightedRows, highlightedCols;
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
        
        highlightedRows = new ArrayList();
        
        highlightedCols = new ArrayList();
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
        
//        
//        JLabel picLabel = new JLabel(piece.icon);
//        boardPanel.add(picLabel);
        
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
    
    
    // highlights all the permissible positions for the currently selected piece
    public void highlightSuggested(int pieceSize, int row, int col){
        
        // check each side of selected position, and highlight or dehighlight the legal ones
        int rowOffset = 0;
        int colOffset = 0;
        
        // check straight up
        boolean legal = true;
        
        for (int i = 1; i < pieceSize; i++){
                    
            rowOffset = row - i;
                    
            if (rowOffset < 0) {
                        
                legal = false;
            }
                   
            else if ((int)boardValues.get(rowOffset).get(col) == 1){
                legal = false;
            }
        }  
          
        if (legal){
            
            for (int i = 1; i < pieceSize; i++){
                boardButtons[row-i][col].setBorder(new LineBorder(Color.YELLOW));
                highlightedRows.add(row-i);
                highlightedCols.add(col);
            }
        }
        
        
        // check straight down
        legal = true;
        
        for (int i = 1; i < pieceSize; i++){
            
            rowOffset = row + i;
                    
            if (rowOffset > 9) {
                        
                legal = false;
            }
                   
            else if ((int)boardValues.get(rowOffset).get(col) == 1){
                legal = false;
            }
        }  
          
        if (legal){
            
            for (int i = 1; i < pieceSize; i++){
                boardButtons[row+i][col].setBorder(new LineBorder(Color.YELLOW));
                highlightedRows.add(row+i);
                highlightedCols.add(col);
            }
        }
        
        
        // check left
        legal = true;
        
        for (int i = 1; i < pieceSize; i++){
            
            colOffset = col - i;
                    
            if (colOffset < 0) {
                        
                legal = false;
            }
                   
            else if ((int)boardValues.get(row).get(colOffset) == 1){
                legal = false;
            }
        }  
          
        if (legal){
            
            for (int i = 1; i < pieceSize; i++){
                boardButtons[row][col-i].setBorder(new LineBorder(Color.YELLOW));
                highlightedRows.add(row);
                highlightedCols.add(col-i);
            }
        }
        
        // check right
        legal = true;
        
        for (int i = 1; i < pieceSize; i++){
            
            colOffset = col + i;
                    
            if (colOffset > 9) {
                        
                legal = false;
            }
                   
            else if ((int)boardValues.get(row).get(colOffset) == 1){
                legal = false;
            }
        }  
          
        if (legal){
            
            for (int i = 1; i < pieceSize; i++){
                boardButtons[row][col+i].setBorder(new LineBorder(Color.YELLOW));
                highlightedRows.add(row);
                highlightedCols.add(col+1);
            }
        }
        
       
        return;
    }
    
    // unhighlights all that was highlighted previously.
    public void dehighlightEverything(){
        
        for (int i = 0; i < highlightedRows.size(); i++){
            for (int j = 0; j < highlightedCols.size(); j++){
                
                if ((int) boardValues.get((int)highlightedRows.get(i)).get((int)highlightedCols.get(j)) != 1)
                    boardButtons[(int)highlightedRows.get(i)][(int)highlightedCols.get(j)].setBorder(new LineBorder(Color.BLACK, 2));
                
            }
        }
        
        
        highlightedRows.clear();
        highlightedCols.clear();
    }
    
    public void placePiece(int pieceSize, int row, int col){
        
        System.out.println("place piece!");
        Piece activePiece;
        
        switch (pieceSize){
            case 5:
                activePiece = new Piece(AIR_CARRIER);
                break;
            case 4:
                activePiece = new Piece(BATTLESHIP);
                break;
            case 3:
                activePiece = new Piece(SUB);
                break;
            case 2:
                activePiece = new Piece(DESTROYER);
                break;
            case 1:
                activePiece = new Piece(BATTLESHIP);
                break;
            default:
                activePiece = new Piece(BATTLESHIP);
            
        }
        
        // get the initial space
        Position initial = selected;
        
        // get the last space selected
        Position last = new Position(row,col);
        
        activePiece.addPosition(initial);
        activePiece.addPosition(last);
        
        // left
        if (col < initial.column){
            
            for (int i = 1; i < pieceSize-1; i++){
                
                activePiece.addPosition(new Position(initial.row, initial.column - i));
            }
            
        } 
        
        // right
        else if (col > initial.column) {
            
            for (int i = 1; i < pieceSize-1; i++){
                
                activePiece.addPosition(new Position(initial.row, initial.column + i));
            }
            
        }
        
        // down
        else if (row < initial.row){
            
            for (int i = 1; i < pieceSize-1; i++){
                
                activePiece.addPosition(new Position(initial.row-1, initial.column));
            }
            
            
        }
        
        // up
        else if (row > initial.row) {
            
            for (int i = 1; i < pieceSize-1; i++){
                
                activePiece.addPosition(new Position(initial.row+1, initial.column));
            }
            
        }
        
        addPiece(activePiece);
        
        
    }
    
    public void enableBoard(boolean state){
        
       // disable your own board because you can only attack opponent
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                
                this.boardButtons[i][j].setEnabled(state);
                
            }
        }
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
            
            button.setBorder(new LineBorder(Color.MAGENTA));
            OptionsPanel.getInstance().fire.setEnabled(true);
            
            // if we're in setup mode, highlight suggestions, de-highlight previous ones
            if (GameController.getInstance().pieceToSetup != -1 && GameController.getInstance().state == SETUP){
                
                 board.dehighlightEverything();
                 board.highlightSuggested(GameController.getInstance().pieceToSetup, row, col);
            }
            
            
            board.selected = new Position(row,col);
            
        
        } 
            // if we're reselecting the same piece, deselect it
            else if (row == board.selected.row && col == board.selected.column){
                button.setBorder(new LineBorder(Color.BLACK, 2));
                board.selected = null;
                OptionsPanel.getInstance().fire.setEnabled(false);

            } else {

                System.out.println("Row is " + row + " and col " + col);
                // if it's part of the suggested area, place the piece
                
                if (board.highlightedRows.contains(row) && board.highlightedCols.contains(col)){
                
                    board.placePiece(GameController.getInstance().pieceToSetup, row, col);
                }
                
                else {
                    
                // otherwise,
                // only change the color if this piece hasn't been attacked before
                if ((int)board.boardValues.get(board.selected.row).get(board.selected.column) > -1) {
                    board.boardButtons[row][col].setBorder(new LineBorder(Color.BLACK));
                }
                
                button.setBorder(new LineBorder(Color.MAGENTA));
                OptionsPanel.getInstance().fire.setEnabled(true);
                
                // if we're in setup mode, highlight suggestions, de-highlight previous ones
                if (GameController.getInstance().pieceToSetup != -1 && GameController.getInstance().state == SETUP){
                  
                    
        board.dehighlightEverything();
                    board.highlightSuggested(GameController.getInstance().pieceToSetup, row, col);
                    
                }
                
                
                board.selected = new Position(row,col);
                
                }
            }
    }
    
    public BoardButtonListener(int r, int c, JButton b, GameBoard gb){
        
        row = r;
        col = c;
        button = b;
        board = gb;
    }
}

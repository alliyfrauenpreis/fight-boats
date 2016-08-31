/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsinthenight;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import static shipsinthenight.GameState.PLAYING;
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
    Icon hitMarker, missMarker;
    
    
    public GameBoard(){
        
        hitMarker = new ImageIcon(new ImageIcon("red-circle-icon-7.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        missMarker = new ImageIcon(new ImageIcon("Grey_close_x.svg.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        
                
        // http://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
        
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
            
            JLabel number;
            number = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            number.setForeground(new Color(1, 95, 156));
            number.setFont(new Font ("Courier New", Font.BOLD, 20));
            boardPanel.add(number);   
        }
        
        
        // create rows and columns
        for (int i = 0; i <= 9; i++){
            
            ArrayList thisRow = new ArrayList();
            
            for (int j = 0; j <= 9; j++){
                // below is pulled in part from SO link (see comments above)
                thisRow.add(j, 0);
                JButton button = new JButton();
                button.setMargin(margin);
                ImageIcon imageIcon = new ImageIcon(new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB));
                button.setIcon(imageIcon);
                button.setBorder(new LineBorder(new Color(1, 95, 156), 2));
                button.setBackground(new Color (1, 22, 49));
                button.addActionListener(new BoardButtonListener(i, j, button, this));
                boardButtons[i][j] = button;
                boardPanel.add(boardButtons[i][j]);
                
            }
            
            boardValues.add(i,thisRow);
            
        }
       
        boardPanel.setMinimumSize(new Dimension(400,500));
        boardPanel.setBackground(new Color (1, 22, 49));
        
        // new Color(0, 33, 72)
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
                System.out.println("COLORING " + row + ", " + col);
                boardButtons[row][col].setBorder(new LineBorder(Color.CYAN, 2));
                
                // disable the side panel for that piece
                if (PiecesPanel.getInstance().activeButton != null)
                    PiecesPanel.getInstance().disableButton();
                
            }

        } else {
            
            System.out.println("piece improperly set!");
        }
        
//        
//        JLabel picLabel = new JLabel(piece.icon);
//        boardPanel.add(picLabel);
        
        dehighlightEverything();
        
        // disable the last selected piece
        
        
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
              boardButtons[row][col].setIcon(hitMarker);
          }else {
              System.out.println("MISS");
              boardButtons[row][col].setBorder(new LineBorder(Color.GRAY, 1));
              boardButtons[row][col].setIcon(missMarker);
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
    
    // updates GUI to deflect model
    public void update(){
        
        for (int i = 0; i <= 9; i++){
            
            for (int j = 0; j <= 9; j++){
                
                // space occupied by ship
                if ((int)boardValues.get(i).get(j) == 1)
                    boardButtons[i][j].setBorder(new LineBorder(Color.CYAN, 2));
                    
                // space hit
                else if ((int)boardValues.get(i).get(j) == -2){
                    boardButtons[i][j].setBorder(new LineBorder(Color.RED, 1));
                    boardButtons[i][j].setIcon(hitMarker);
                }
                    
                // space miss
                else if ((int)boardValues.get(i).get(j) == -1){
                    boardButtons[i][j].setBorder(new LineBorder(Color.GRAY, 1));
                    boardButtons[i][j].setIcon(missMarker);
                }
                    
                // space empty
                else
                    boardButtons[i][j].setBorder(new LineBorder(new Color(1, 95, 156), 2));
                    
            }
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
    
    public void hidePieces(){
        
        for (int i = 0; i <=9; i++){
            
            for (int j = 0; j <= 9; j++){
                
                boardButtons[i][j].setBorder(new LineBorder(new Color(1, 95, 156), 2));
                
            }
        }
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
                    boardButtons[(int)highlightedRows.get(i)][(int)highlightedCols.get(j)].setBorder(new LineBorder(new Color(1, 95, 156), 2));
                
            }
        }
        
        for (int i = 0; i <= 9; i++){
            for (int j = 0; j <= 9; j++){
                
                if ((int)boardValues.get(i).get(j) == 0)
                    boardButtons[i][j].setBorder(new LineBorder(new Color(1, 95, 156), 2));
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
        
        System.out.println("initial is " + initial.row + ", " + initial.column + " and last is " + last.row + ", " + last.column);
        
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
                
                activePiece.addPosition(new Position(initial.row-i, initial.column));
            }
            
            
        }
        
        // up
        else if (row > initial.row) {
            
            for (int i = 1; i < pieceSize-1; i++){
                
                activePiece.addPosition(new Position(initial.row+i, initial.column));
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
            System.out.println("first piece selected");
            board.selected = new Position(row, col);
            button.setBorder(new LineBorder(Color.MAGENTA, 2));
            
            if (GameController.getInstance().state == PLAYING) {
                System.out.println("allow fire");
                OptionsPanel.getInstance().fire.setEnabled(true);
                OptionsPanel.getInstance().fire.requestFocus();
                
            }
            
            // if we're in setup mode, highlight suggestions
            else if (GameController.getInstance().state == SETUP){
                
                if (GameController.getInstance().pieceToSetup != -1) {
                     board.highlightSuggested(GameController.getInstance().pieceToSetup, row, col);
                }
            }
        
        } 
        
        // if we're reselecting the same piece, deselect it
        else if (row == board.selected.row && col == board.selected.column){
                
            System.out.println("reselecting");
                button.setBorder(new LineBorder(new Color(1, 95, 156), 2));
                board.selected = null;
                OptionsPanel.getInstance().fire.setEnabled(false);
                
                if (GameController.getInstance().state == SETUP){
                    board.dehighlightEverything();
                }

        }
        
        // we're selecting a new piece, and another one is already selected
        else {
            
            // if we're setting up...
            if (GameController.getInstance().state == SETUP){
                
               // if it's part of the suggested area, place the piece
               if (board.highlightedRows.contains(row) && board.highlightedCols.contains(col)){
                
                   if (GameController.getInstance().state == SETUP){
                        board.placePiece(GameController.getInstance().pieceToSetup, row, col);
                        board.selected = null;
                        
                   }
               }
               
               // otherwise, de-highlight everything and suggest again
               else {
                   board.dehighlightEverything();
                   board.boardButtons[board.selected.row][board.selected.column].setBorder(new LineBorder(new Color(1, 95, 156), 2));
                   board.selected = new Position(row, col);
                   button.setBorder(new LineBorder(Color.MAGENTA, 2));
                   board.highlightSuggested(GameController.getInstance().pieceToSetup, row, col);
               } 
            }
            
            else if (GameController.getInstance().state == PLAYING){
                System.out.println("Playing, selecting another piece");
                
                // change previous piece back to white
                if ((int)board.boardValues.get(board.selected.row).get(board.selected.column) > -1) {
                    
                    board.boardButtons[board.selected.row][board.selected.column].setBorder(new LineBorder(new Color(1, 95, 156), 2));
               }
                
               button.setBorder(new LineBorder(Color.MAGENTA));
               OptionsPanel.getInstance().fire.setEnabled(true);
                OptionsPanel.getInstance().fire.requestFocus();
               board.selected = new Position(row, col);
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

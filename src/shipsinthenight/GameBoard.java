/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsinthenight;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author allisonfrauenpreis
 */
public class GameBoard {
    
    ArrayList<ArrayList> boardValues;
    ArrayList markerLocations = new ArrayList();
    ArrayList<Piece> pieces = new ArrayList();
    JPanel boardPanel;
    Position selected = new Position(1,1);
    
    public GameBoard(){
        
        // create a new list of lists that represents board
        // 0 means no piece, 1 means piece
        boardValues = new ArrayList();
        
        for (int i = 0; i <= 9; i++){
            
            ArrayList thisRow = new ArrayList();
            for (int j = 0; j <= 9; j++){
                
                thisRow.add(j, 0);
            }
            boardValues.add(i,thisRow);
        }
        
    }
    
    
    // adds a piece to the position that the piece holds
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
                
                
            }

        } else {
            
            System.out.println("piece improperly set!");
        }
        
    }
    
    // add a hit marker to the board; returns true if this results in sunken ship
    public boolean addHit(Position pos){
        
        
         int row = pos.getRow();
         int col = pos.getCol();
                
         ArrayList rowList = boardValues.get(row);
         rowList.set(col, -1);
         
        
        // check all the pieces to add hit & check for sinking
        for (Piece p : pieces){
            
            if (p.hasPosition(pos)){
                
                p.addHit();
                if (p.sunk == true){
                    
                    System.out.println("SINK!");
                    return true;
                }
            }
        }
        printDebugBoard();
        return false;
    }
    
    // checks if space is occupied; returns 1 if so, 0 otherwise
    public int checkSpace(Position pos){
        
         if ((int) boardValues.get(pos.getRow()).get(pos.getCol()) == 1){
             
            return 1;
            
         } else { 
             
            return 0;
         }
         
    }
    
    // sets the current user-selected position
    public void setSelectedPosition(Position p){
        
        selected = p;
    }
    
    // gets the current user-selected position
    public Position getSelectedPosition(){
        
        return selected;
    }
    
    
    // prints representation of board to console; 1 for occupied, 0 for empty, -1 for sunk
    void printDebugBoard(){
        
        
        
        for (int x = 0; x <= 9; x++){
            
            for (int j = 0; j <= 9; j++){
                
                System.out.print(boardValues.get(x).get(j) + " ");
            }
            
            System.out.println("");
        }
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsinthenight;

import java.util.ArrayList;
import javax.swing.ImageIcon;

    enum pieceType { AIR_CARRIER, BATTLESHIP, SUB, DESTROYER, PATROL };

/**
 *
 * @author allisonfrauenpreis
 * This class handles error-checking for each piece type as well as checking if a piece is sunk and where it is placed.
 */
public class Piece {
    
    
    private int size;
    private pieceType type;
    ArrayList<Position> position;
    public int hits = 0;
    public boolean sunk = false;
    ImageIcon icon;
  
    
    // create a new piece with default values
    public Piece(pieceType pType){
       
        type = pType;
        position = new ArrayList();
        size = 0;
        setup();
        
    }
    
    // set up piece size based on game rules
    public void setup(){
        
        switch (type){
            case AIR_CARRIER:
                size = 5;
                icon = new ImageIcon("ship1.png");
                break;
            case BATTLESHIP:
                size = 4;
                icon = new ImageIcon("ship2.png");
                break;
            case SUB:
                size = 3;
                icon = new ImageIcon("ship3.png");
                break;
            case DESTROYER:
                icon = new ImageIcon("ship3.png");
                size = 2;
                break;
            case PATROL:
                icon = new ImageIcon("ship3.png");
                size = 2;
                break;
            
        }
    }
    
    // sets piece that has at least its origin point and direction set as first two positions
  /*  public void autoSetPositions(){
        
        if (position.size() < 2){
            
            System.out.println("Piece does not have enough positions to be set");
            
        } else { 
            
            
            Position origin = position.get(0);
            Position next = position.get(1);
            int row = origin.getRow();
            int col = origin.getCol();
            
            position.clear();
            
            if (origin.getRow() < next.getRow()){
                
                // going down
                
                System.out.println("down");
                for (int i = 1; i <= size; i++){
                    
                    position.add(new Position(row, origin.getCol()));
                    row += 1;
                }
                
            } else if (origin.getRow() > next.getRow()) {
                
                // going up
                
                System.out.println("up");
                for (int i = 1; i <= size; i++){
                    
                    position.add(new Position(row,origin.getCol()));
                    row = row - 1;
                }
                
            } else if (origin.getCol() < next.getCol()){
                
                
                // going right
                System.out.println("right");
                for (int i = 1; i <= size; i++){
                    
                    position.add(new Position(origin.getRow(),col));
                    col+=1;
                }
                
                
            } else if (origin.getCol() > next.getCol()) {
                
                
                // going left
                
                System.out.println("left");
                for (int i = 1; i <= size; i++){
                    
                    position.add(new Position(origin.getRow(),col));
                    System.out.println("col" + col);
                    System.out.println("row" + origin.getRow());
                    col = col - 1;
                }
            }
        }
    }
    */
    
    // adds marker to piece
    public void addHit(){
        
        hits = hits + 1;
        if (hits == size){
            
            sunk = true;
        }
    }
    
    // returns true if this piece exists at this position
    public boolean hasPosition(Position p){
        
        for (Position pos : position){
            
            if (pos.getCol() == p.getCol()){
                
                if (pos.getRow() == p.getRow()){
                    
                    return true;
                }
            }
            
        }
        
        return false;
    }
    
    public int getSize(){
        
        return size;
    }
    
    public ArrayList getPositions(){
        
        return position;
    }
    
    public void addPosition(Position pos){
        
        position.add(pos);
    }
    
    // checks if all positions in piece are set
    public boolean isSet(){
        
        if (position.size() == size){
            
            return true;
            
        } else {
            return false;
        }
    }
    
    public void printPositions(){
        
        
        for (Position p: position){
            
            System.out.println("Row: " + p.getRow() + ", column: " + p.getCol());
        }
    }
}

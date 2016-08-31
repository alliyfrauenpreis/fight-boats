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
    
    
    public int size;
    public pieceType type;
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
                icon = new ImageIcon("ship5.png");
                break;
            case BATTLESHIP:
                size = 4;
                icon = new ImageIcon("ship4.png");
                break;
            case SUB:
                size = 3;
                icon = new ImageIcon("ship3.png");
                break;
            case DESTROYER:
                icon = new ImageIcon("ship2-1.png");
                size = 2;
                break;
            case PATROL:
                icon = new ImageIcon("ship2-2.png");
                size = 2;
                break;
            
        }
    }
    
    
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
        
       System.out.println("position size is " + position.size() + " and size is " + size);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author allisonfrauenpreis
 */


package shipsinthenight;

import shipsinthenight.GameBoard;

public class GameController {
    
    GameBoard playerBoard, opponentBoard;
    
    public GameController(){
        
        
    }
    
    
    public void setupGame(){
        
        
        
        GameBoard board = new GameBoard();
        Piece patrol = new Piece(shipsinthenight.pieceType.PATROL);
        Piece airCarrier = new Piece(shipsinthenight.pieceType.AIR_CARRIER);
        Piece battleship = new Piece(shipsinthenight.pieceType.BATTLESHIP);
        Piece destroyer = new Piece(shipsinthenight.pieceType.DESTROYER);
        Piece sub = new Piece(shipsinthenight.pieceType.SUB);
        
//        
       airCarrier.addPosition(new Position(5,8));  
        airCarrier.addPosition(new Position(4,8));  
       airCarrier.addPosition(new Position(3,8)); 
        airCarrier.addPosition(new Position(2,8)); 
       airCarrier.addPosition(new Position(1,8)); 
       
       board.addHit(new Position(5,8)); 
       board.addHit(new Position(4,8));  
       board.addHit(new Position(3,8));  
       board.addHit(new Position(2,8));  
       board.addHit(new Position(1,8));  
       
        
//        
//        airCarrier.addPosition(new Position(2,1));
//        airCarrier.addPosition(new Position(3,1));
//        airCarrier.addPosition(new Position(4,1));
//        airCarrier.addPosition(new Position(5,1));
//        airCarrier.addPosition(new Position(6,1));
//        
        board.addPiece(airCarrier);
        
        board.printDebugBoard();
        
    }
    
    public void displayBoards(){
        
    }
    
}

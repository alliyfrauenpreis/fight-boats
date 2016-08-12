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
        
        
        airCarrier.addPosition(new Position(3,1));  
        airCarrier.addPosition(new Position(4,1));  
        
        airCarrier.autoSetPositions();
        
        board.addPiece(airCarrier);
//                
//        board.addPiece(airCarrier);
//        board.addHit(new Position(3,1));
//        board.addHit(new Position(3,2));
//        
        
        board.printDebugBoard();
        
    }
    
    public void displayBoards(){
        
    }
    
}

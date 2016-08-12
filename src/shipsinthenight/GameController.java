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
        patrol.addPosition(new Position(1,1));
        patrol.addPosition(new Position(1,2));
        board.addPiece(patrol);
        board.addHit(new Position(1,1));
        board.addHit(new Position(1,2));
        
        
    }
    
    public void displayBoards(){
        
    }
    
}

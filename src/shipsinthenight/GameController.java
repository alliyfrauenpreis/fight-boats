/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author allisonfrauenpreis
 * This class handles general game setup including laying out boards and processing attacks.
 */


package shipsinthenight;

import javax.swing.JPanel;
import shipsinthenight.GameBoard;

// SINGLETON CLASS
public class GameController {
    
    private static GameController instance = null;
    
    GameBoard playerBoard, opponentBoard;
    Position selected = null;
    PopupController popupController;
    ShipsInTheNight appController;
    
    public GameController(){
        
        
    }
    
    public static GameController getInstance(){
        
        if (instance == null){
            instance = new GameController();
        }
        return instance;
    }
    
    
    // setup a new game with dummy content for now, and two boards
    public void setupGame(ShipsInTheNight s){
        
        playerBoard = new GameBoard();
        opponentBoard = new GameBoard();
        popupController = new PopupController();
        appController = s;
        Piece patrol = new Piece(shipsinthenight.pieceType.PATROL);
        Piece airCarrier = new Piece(shipsinthenight.pieceType.AIR_CARRIER);
        Piece battleship = new Piece(shipsinthenight.pieceType.BATTLESHIP);
        Piece destroyer = new Piece(shipsinthenight.pieceType.DESTROYER);
        Piece sub = new Piece(shipsinthenight.pieceType.SUB);
        
       airCarrier.addPosition(new Position(5,8));  
        airCarrier.addPosition(new Position(4,8));  
       airCarrier.addPosition(new Position(3,8)); 
        airCarrier.addPosition(new Position(2,8)); 
       airCarrier.addPosition(new Position(1,8)); 
       
       patrol.addPosition(new Position(2,2));
       patrol.addPosition(new Position(2,3));
       
       battleship.addPosition(new Position(7,1));
       battleship.addPosition(new Position(7,2));
       battleship.addPosition(new Position(7,3));
       battleship.addPosition(new Position(7,4));
       
        playerBoard.addPiece(airCarrier);
        playerBoard.addPiece(patrol);
        playerBoard.addPiece(battleship);
        
        Piece patrol2 = new Piece(shipsinthenight.pieceType.PATROL);
        Piece airCarrier2 = new Piece(shipsinthenight.pieceType.AIR_CARRIER);
        
       airCarrier2.addPosition(new Position(5,8));  
        airCarrier2.addPosition(new Position(4,8));  
       airCarrier2.addPosition(new Position(3,8)); 
        airCarrier2.addPosition(new Position(2,8)); 
       airCarrier2.addPosition(new Position(1,8)); 
       
       patrol2.addPosition(new Position(2,2));
       patrol2.addPosition(new Position(2,3));
       
       battleship.addPosition(new Position(7,1));
       battleship.addPosition(new Position(7,2));
       battleship.addPosition(new Position(7,3));
       battleship.addPosition(new Position(7,4));
       
       
        playerBoard.addPiece(airCarrier);
        playerBoard.addPiece(patrol);
        playerBoard.addPiece(battleship);
        
        opponentBoard.addPiece(patrol2);
        opponentBoard.addPiece(airCarrier2);
        
        // disable your own board because you can only attack opponent
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                playerBoard.boardButtons[i][j].setEnabled(false);
            }
        }
        
        playerBoard.printDebugBoard();
        
    }
    
    public JPanel getPlayerBoardPanel(){
        
        return playerBoard.getBoard();
    }
    
     public JPanel getOpponentBoardPanel(){
        
        return opponentBoard.getBoard();
    }
    
    public void sendAttack(){
        
        // get the selected position on the opponent board
        Position target = opponentBoard.selected;
        boolean sunk = opponentBoard.addHit(target);
        
        // TODO: send over socket
        
        // launch popup depending on result of attack
        if (sunk){
            if (opponentBoard.win()){
                popupController.launchWinPopup();
                appController.returnToStartScreen();
            } else {
                popupController.launchSinkPopup(target);
            }
        } else if (opponentBoard.checkSpace(target) == 1){
            popupController.launchHitPopup(target);
        } else {
            popupController.launchMissPopup(target);
        }

        
    }
    
    public void quitGame(){
        
        appController.quitGame();
    }
    
}

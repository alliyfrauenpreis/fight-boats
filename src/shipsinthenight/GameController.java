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
import static shipsinthenight.GameState.READY;
import static shipsinthenight.GameState.SETUP;



enum GameState { SETUP, READY, PLAYING };

// SINGLETON CLASS
public class GameController {
    
    private static GameController instance = null;
    
    GameBoard player1Board, player2Board, currentBoard, otherBoard;
    Position selected = null;
    PopupController popupController;
    ShipsInTheNight appController;
    GameState state = SETUP;
    int pieceToSetup = 0;
    int currentPlayer = 1;
    boolean player1Ready = false;
    boolean player2Ready = false;
    
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
        
        player1Board = new GameBoard();
        player2Board = new GameBoard();
        popupController = new PopupController();
        appController = s;
        
        Piece patrol = new Piece(shipsinthenight.pieceType.PATROL);
        Piece airCarrier = new Piece(shipsinthenight.pieceType.AIR_CARRIER);
        Piece battleship = new Piece(shipsinthenight.pieceType.BATTLESHIP);
        Piece destroyer = new Piece(shipsinthenight.pieceType.DESTROYER);
        Piece sub = new Piece(shipsinthenight.pieceType.SUB);
        
        
        player1Board.addPiece(airCarrier);
        player1Board.addPiece(patrol);
        player1Board.addPiece(battleship);
        player1Board.addPiece(destroyer);
        player1Board.addPiece(sub);
        
        patrol = new Piece(shipsinthenight.pieceType.PATROL);
        airCarrier = new Piece(shipsinthenight.pieceType.AIR_CARRIER);
        battleship = new Piece(shipsinthenight.pieceType.BATTLESHIP);
        destroyer = new Piece(shipsinthenight.pieceType.DESTROYER);
        sub = new Piece(shipsinthenight.pieceType.SUB);
        
        
        player2Board.addPiece(airCarrier);
        player2Board.addPiece(patrol);
        player2Board.addPiece(battleship);
        player2Board.addPiece(destroyer);
        player2Board.addPiece(sub);
        

        currentBoard = player1Board;
        otherBoard = player2Board;
        
        otherBoard.hidePieces();
        
      //  playerBoard.printDebugBoard();
        
        
    }
    
    public JPanel getPlayerBoardPanel(){
        
        return currentBoard.getBoard();
    }
    
     public JPanel getOpponentBoardPanel(){
        
        return otherBoard.getBoard();
    }
    
    public void sendAttack(){
        
        // get the selected position on the opponent board
        Position target = otherBoard.selected;
        boolean sunk = otherBoard.addHit(target);
        
        System.out.println("sending attack");
        
        // launch popup depending on result of attack
        if (sunk){
            if (otherBoard.win()){
                popupController.launchWinPopup();
                appController.returnToStartScreen();
            } else {
                popupController.launchSinkPopup(target);
            }
        } else if (otherBoard.checkSpace(target) == 1){
            popupController.launchHitPopup(target);
        } else {
            popupController.launchMissPopup(target);
        }

        
    }
    
    public void sendReadySignal(){
        
    
    }
    
    public void quitGame(){
        
        appController.quitGame();
    }
    
    public void setState(GameState newState){
        
        state = newState;
        
        switch (state) {
            
            case SETUP:
                otherBoard.enableBoard(false);
                currentBoard.enableBoard(true);
                otherBoard.hidePieces();
                PiecesPanel.getInstance().enable();
                break;
            case READY:
                sendReadySignal();
                PiecesPanel.getInstance().disable();
                otherBoard.enableBoard(false);
                currentBoard.enableBoard(true);
                break;
            case PLAYING:
                otherBoard.hidePieces();
                otherBoard.enableBoard(true);
                currentBoard.update();
                currentBoard.enableBoard(false);
            default:
                break;
                
        }
    }
    
    public void savePieceToSetup(int size){
        
        pieceToSetup = size;
        
    }
    
    public void swapPlayers(){
        
       if (currentPlayer == 1){
           currentBoard = player2Board;
           otherBoard = player1Board;
           currentPlayer = 2;
       } else {
           currentPlayer = 1;
           currentBoard = player1Board;
           otherBoard = player2Board;
       }
       
       if (currentPlayer == 2 & !player2Ready){
           
           // swap to player 2 board
           currentBoard = player2Board;
           otherBoard = player1Board;
           
           currentBoard.update();
           otherBoard.update();
           
           
           // hide and disable other board; show and enable player 2 board;
           setState(SETUP);
          
           PopupController.getInstance().launchPlacePiecesPopup();
       }
       
       // they're both ready!
       else {
           
           if (currentPlayer == 1){
               
               player2Board.hidePieces();
               player1Board.enableBoard(false);
               player2Board.enableBoard(true);
               player1Board.update();
               
               
           }
           
           else if (currentPlayer == 2){
               
               player1Board.hidePieces();
               player2Board.enableBoard(false);
               player1Board.enableBoard(true);
               player2Board.update();
               
               
           }
           
       }
       
       System.out.println("OTHER BOARD:");
       otherBoard.printDebugBoard();
       
       
       System.out.println("CURRENT BOARD:");
       currentBoard.printDebugBoard();
       
       
       System.out.println("P1 BOARD:");
       player1Board.printDebugBoard();
       
       
       
       System.out.println("P2 BOARD:");
       player2Board.printDebugBoard();
       
       
       
       
       
       
       
    }
    
}

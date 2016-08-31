/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsinthenight;

import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.DEFAULT_OPTION;

/**
 *
 * @author allisonfrauenpreis
 * This class handles all popups for win, miss, hit, and sink conditions.
 */
public class PopupController {
    
    private static PopupController instance = null;
    JOptionPane hit, miss, sink;
    int currentPlayer;
    
    public static PopupController getInstance(){
        
        if (instance == null){
            instance = new PopupController();
        }
        
        return instance;
    }
    
    
    public PopupController(){
        
        hit = new JOptionPane("Direct hit!");
        miss = new JOptionPane("It's a miss!");
        sink = new JOptionPane("You sunk their ship!");
        currentPlayer = 1;
    }
    
    public void launchHitPopup(Position p){
        
        JOptionPane.showMessageDialog(null, "Direct hit on space " + p.positionString() + "!");
    }
    
       
    public void launchSinkPopup(Position p){
        
        JOptionPane.showMessageDialog(null, "You sunk the opponent's ship!");
    } 
    
   public void launchMissPopup(Position p){
        
        JOptionPane.showMessageDialog(null, "You missed on space " + p.positionString() + "!");
    } 
   
   public void launchWinPopup(){
       
       JOptionPane.showMessageDialog(null, "YOU WIN!!!!");
   }
   
   
   public void launchPlacePiecesPopup(){
       
       currentPlayer = GameController.getInstance().currentPlayer;
       
       if (currentPlayer == 1)
            JOptionPane.showMessageDialog(null, "Player 1: Please place your pieces on the board. Make sure that Player 2 isn't looking while you do it!");
       else
            JOptionPane.showMessageDialog(null, "Player 2: Please place your pieces on the board. Make sure that Player 1 isn't looking while you do it!");
   }
}

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
 */
public class PopupController {
    
    JOptionPane hit, miss, sink;
    
    public PopupController(){
        
        hit = new JOptionPane("Direct hit!");
        miss = new JOptionPane("It's a miss!");
        sink = new JOptionPane("You sunk their ship!");
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
}

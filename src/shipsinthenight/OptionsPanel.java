/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsinthenight;

/**
 *
 * @author allisonfrauenpreis
 * This class handles the panel of buttons for things like fire, quit, etc.
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import shipsinthenight.GameBoard;

// SINGLETON CLASS
public class OptionsPanel {
    
    JPanel panel;
    JButton fire, quit, ready, set, toggle;
    
    private static OptionsPanel instance = null;
    
    public OptionsPanel(){
    }
    
    public static OptionsPanel getInstance(){
        
        if (OptionsPanel.instance == null){
            
            instance = new OptionsPanel();
            instance.setup();
            
        }
        return instance;
    }
    
    public void setup(){
        
        panel = new JPanel();
        fire = new JButton("Fire!");
        fire.addActionListener(new FireButtonListener());
        fire.setEnabled(false);
        quit = new JButton("Quit");
        quit.addActionListener(new QuitButtonListener());
        ready = new JButton("Ready!");
        ready.addActionListener(new ReadyButtonListener());
        ready.setEnabled(false);
        toggle = new JButton("Toggle who's turn it is");
        toggle.addActionListener(new ToggleButtonListener());
        panel.add(fire);
        panel.add(quit);
        panel.add(ready);
        panel.add(toggle);
        
    }
    
    public JPanel getPanel(){
        
        return panel;
    }
    
}

// listener for button to launch attack
class FireButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        // notify game controller
        GameController.getInstance().sendAttack();
        GameController.getInstance().swapPlayers();
        
    }
    
    public FireButtonListener(){
        
      
    }
    
}

// listener for button to quit game
// TODO: add "are you sure" message
class QuitButtonListener implements ActionListener{

    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        GameController.getInstance().quitGame();
    }
    
    public QuitButtonListener(){
        
       
        
    }
}


class ReadyButtonListener implements ActionListener{

    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (GameController.getInstance().player1Ready){
                GameController.getInstance().player2Ready = true;
            } else {
                GameController.getInstance().player1Ready = true;
            }
    
    
        if (GameController.getInstance().player1Ready && GameController.getInstance().player2Ready) {
            
            GameController.getInstance().setState(GameState.PLAYING);
        } 
        
        GameController.getInstance().swapPlayers();
    }
    
    public ReadyButtonListener(){
        
       
        
    }
}

// toggles player turn, for debugging
class ToggleButtonListener implements ActionListener{

    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        GameController.getInstance().swapPlayers();
    }
    
    public ToggleButtonListener(){
        
       
        
    }
}
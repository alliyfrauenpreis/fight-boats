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


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import shipsinthenight.GameBoard;

// SINGLETON CLASS
public class OptionsPanel {
    
    JPanel panel;
    JButton fire, quit, ready, set, help;
    
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        fire = new JButton("Fire!");
        fire.addActionListener(new FireButtonListener());
        fire.setEnabled(false);
        quit = new JButton("Quit");
        quit.addActionListener(new QuitButtonListener());
        ready = new JButton("Ready!");
        ready.addActionListener(new ReadyButtonListener());
        ready.setEnabled(false);
        help = new JButton("Help");
        help.addActionListener(new HelpButtonListener());
        help.setEnabled(true);
        panel.add(fire);
        panel.add(quit);
        panel.add(ready);
        panel.add(help);
        
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
            
        JRootPane pane = SwingUtilities.getRootPane(OptionsPanel.getInstance().panel);
        pane.setDefaultButton(OptionsPanel.getInstance().fire);
        } 
        
        GameController.getInstance().swapPlayers();
    }
    
    public ReadyButtonListener(){
        
       
        
    }
}

class HelpButtonListener implements ActionListener{

    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch (GameController.getInstance().state){
            
            case READY:
                
                JOptionPane.showMessageDialog(null, "Switch players so that your opponent can place their pieces before you start playing!");
                
                break;
            case PLAYING:
                
                
                JOptionPane.showMessageDialog(null, "Select a square on your opponent's board to attack, then click FIRE! to launch your missile. \n You will be notified whether you hit or miss. \n The pieces at the left show you how many squares there are left un-scarred for each of your opponent's ships.");
                
                break;
            case SETUP:
                
                JOptionPane pane = new JOptionPane();
                pane.setSize(new Dimension(200,200));
                pane.showMessageDialog(null, "Select a piece on the left and then click on a space on your board to see legal placement areas. \n Click the last box in the placement area you want to set to place your piece. \n Once all are set, click READY. \n Be careful, though -- you only get one shot to place them, because moving ships is expensive!");
                
                
                break;
                
        }
    }
    
    public HelpButtonListener(){
        
       
        
    }
}
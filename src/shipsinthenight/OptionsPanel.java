/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipsinthenight;

/**
 *
 * @author allisonfrauenpreis
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import shipsinthenight.GameBoard;

public class OptionsPanel {
    
    JPanel panel;
    JButton fire, quit;
    
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
        panel.add(fire);
        panel.add(quit);
        
        
    }
    
    public JPanel getPanel(){
        
        return panel;
    }
    
}

class FireButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        // notify game controller
        GameController.getInstance().sendAttack();
        
    }
    
    public FireButtonListener(){
        
        
        
        
    }
    
}

class QuitButtonListener implements ActionListener{

    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        GameController.getInstance().quitGame();
    }
    
    public QuitButtonListener(){
        
       
        
    }
}
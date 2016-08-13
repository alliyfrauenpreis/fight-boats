
package shipsinthenight;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class ShipsInTheNight {

    JFrame         mainFrame = new JFrame();
    JPanel         mainPanel = new JPanel();
    JTextField     textField = new JTextField();
    JButton        startButton = new JButton();
    ServerHandler  serverHandler = new ServerHandler();
    GameController gameController = new GameController();
    
    
    public static void main(String[] args) {
        
        ShipsInTheNight s = new ShipsInTheNight();
       //s.serverHandler.start();
        s.gameController.setupGame();
        
        /*
        s.mainPanel = new JPanel();
        s.mainFrame = new JFrame();
        s.mainPanel.setLayout(new BoxLayout(s.mainPanel,BoxLayout.Y_AXIS));
        s.mainPanel.add(new JLabel("Fight Boats"));
        s.mainPanel.add(new JLabel("The first rule of Fight Boats: you don't talk about Fight Boats."));
        s.startButton = new JButton("START!");
        s.startButton.addActionListener(new StartButtonListener(s));
        s.textField.setMinimumSize(new Dimension(200,50));
        s.mainPanel.add(s.textField);
        s.mainPanel.add(s.startButton);
        s.mainFrame.add(s.mainPanel);
        s.mainFrame.pack();
        s.mainFrame.setVisible(true);
        */
        
    }
    
    public void startNewGame(String fieldContents){
        
        mainFrame = new JFrame();
        mainFrame.pack();
        mainFrame.setVisible(true);
        
    }
    
}


class StartButtonListener implements ActionListener{

    ShipsInTheNight app;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        app.mainFrame.setVisible(false);
        app.mainFrame.dispose();
        app.startNewGame(app.textField.getText());
    }
    
    public StartButtonListener(ShipsInTheNight s){
        
        app = s;
    }
    
    
}

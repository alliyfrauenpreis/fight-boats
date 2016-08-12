
package shipsinthenight;

import java.util.ArrayList;
import javax.swing.*;

public class ShipsInTheNight {

    JFrame         mainFrame = new JFrame();
    JPanel         mainPanel = new JPanel();
    JButton        startButton = new JButton();
    ServerHandler  serverHandler = new ServerHandler();
    GameController gameController = new GameController();
    
    
    public static void main(String[] args) {
        
        ShipsInTheNight s = new ShipsInTheNight();
       //s.serverHandler.start();
        s.gameController.setupGame();
        
        
        
//        s.mainPanel = new JPanel();
//        s.mainFrame = new JFrame();
//        
//        s.mainPanel.setLayout(new BoxLayout(s.mainPanel,BoxLayout.Y_AXIS));
//        s.mainPanel.add(new JLabel("Fight Boats"));
//        s.mainPanel.add(new JLabel("The first rule of Fight Boats: you don't talk about Fight Boats."));
//        s.startButton = new JButton("START!");
//        s.mainPanel.add(s.startButton);
//        s.mainFrame.add(s.mainPanel);
//        
//        
//        
//        s.mainFrame.pack();
//        s.mainFrame.setVisible(true);
        
        
    }
    
}

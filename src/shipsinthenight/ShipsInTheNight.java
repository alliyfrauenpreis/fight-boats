
package shipsinthenight;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShipsInTheNight {

    JFrame         mainFrame;
    JPanel         mainPanel;
    ServerHandler  severHandler;
    GameController gameController = new GameController();
    
    
    public static void main(String[] args) {
        
        ShipsInTheNight s = new ShipsInTheNight();
        s.gameController.setupGame();
        
    }
    
}

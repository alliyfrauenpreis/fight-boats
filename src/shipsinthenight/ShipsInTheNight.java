
package shipsinthenight;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.SwingConstants.CENTER;

/**
 *
 * @author allisonfrauenpreis
 * This is the main app class, where all panels and controllers are created and laid out.
 */

public class ShipsInTheNight {

    JFrame         mainFrame = new JFrame();
    JPanel         mainPanel = new JPanel();
    JTextField     textField = new JTextField();
    JButton        startButton = new JButton();
    GameController gameController = GameController.getInstance();
    JLabel         title, subtitle, pBoardLabel, oBoardLabel;
    
    
    public static void main(String[] args) {
        
        ShipsInTheNight s = new ShipsInTheNight();
       //s.serverHandler.start();
        
        s.mainPanel = new JPanel();
        s.mainFrame = new JFrame();
        s.mainPanel.setLayout(new BoxLayout(s.mainPanel,BoxLayout.Y_AXIS));
        s.title = new JLabel("Ships in the Night");
        s.mainPanel.add(s.title);
        s.startButton = new JButton("START!");
        s.startButton.addActionListener(new StartButtonListener(s));
        s.pBoardLabel = new JLabel("Your Board");
        s.oBoardLabel = new JLabel("Opponent's Board");
        s.textField.setMinimumSize(new Dimension(200,50));
        s.mainPanel.add(s.startButton);
        s.mainPanel.setBorder((BorderFactory.createEmptyBorder(20, 30, 30, 20)));
        s.mainFrame.add(s.mainPanel);
        s.mainFrame.pack();
        s.mainFrame.setVisible(true);
        
        
    }
    
    public void startNewGame(String fieldContents){
        
        
        this.gameController.setupGame(this);
        mainFrame = new JFrame();
        textField.setVisible(false);
        startButton.setVisible(false);
        title.setVisible(false);
        
        // layout left to right
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
        
        // left panel
        PiecesPanel piecesPanel = PiecesPanel.getInstance();
        piecesPanel.setup();
        mainPanel.add(piecesPanel.getPanel());
        
        // middle panel
        JPanel middle = new JPanel();
        middle.add(oBoardLabel);
        middle.add(gameController.getOpponentBoardPanel());
        
        middle.add(pBoardLabel);
        middle.add(gameController.getPlayerBoardPanel());
        
        mainPanel.add(middle);
        
        // right panel
        OptionsPanel options = OptionsPanel.getInstance();
        mainPanel.add(options.getPanel());
        
        
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
        
        GameController.getInstance().setState(GameState.SETUP);
        
    }
    
    public void returnToStartScreen(){
        
        
        mainFrame.setVisible(false);
        mainFrame.dispose();
        mainFrame = new JFrame();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        title = new JLabel("Ships in the Night");
        mainPanel.add(title);
        startButton = new JButton("START!");
        startButton.addActionListener(new StartButtonListener(this));
        textField.setMinimumSize(new Dimension(200,50));
        mainPanel.add(textField);
        mainPanel.add(startButton);
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    
    
    public void quitGame(){
        
        mainFrame.setVisible(false);
        mainFrame.dispose();
        
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
    

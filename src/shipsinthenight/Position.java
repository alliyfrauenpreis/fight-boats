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
public class Position {
    
    int row;
    int column;
    
    public Position(int row1, int column1){
        
        row = row1;
        column = column1;
    }
    
    public int getRow(){
        
        return row;
    }
    
    public int getCol(){
        
        return column;
    }
    
}

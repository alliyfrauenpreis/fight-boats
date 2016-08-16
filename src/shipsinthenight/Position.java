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
    
    public String positionString(){
        
        switch (row){
            case 0:
                return "A" + String.valueOf(column+1);
            case 1:
                return "B" + String.valueOf(column+1);
            case 2:
                return "C" + String.valueOf(column+1);
            case 3:
                return "D" + String.valueOf(column+1);
            case 4:
                return "E" + String.valueOf(column+1);
            case 5:
                return "F" + String.valueOf(column+1);
            case 6:
                return "G" + String.valueOf(column+1);
            case 7:
                return "H" + String.valueOf(column+1);
            case 8:
                return "I" + String.valueOf(column+1);
            case 9:
                return "J" + String.valueOf(column+1);
            default:
                break;
        }
        
        return "";   
    }
    
}

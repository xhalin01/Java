package game;
import board.BoardField;
import board.BorderField;
import board.Field;
import board.Rules;

/**
 * Created by majkl on 26.03.2016.
 */
public class ReversiRules implements Rules{
    int size;
    int numDisks;

    //Inicializace pravidel.
    public ReversiRules(int size) {
        this.size=size;
        numDisks=size*size;
    }
    //Vrací velikost desky.
    public int getSize() {
        return size;
    }
    //Vrací počet kamenů jednotlivých hráčů.
    public int numberDisks() {
        return numDisks/2;
    }
    //Vytvoří odpovídající pole na zadaných indexech.
    public Field createField(int row,int col) {
        if(row==size+1 || row==0 || col==0 || col==size+1) {
            return new BorderField();
        }
        else {
            return new BoardField(row,col);
        }
    }
}
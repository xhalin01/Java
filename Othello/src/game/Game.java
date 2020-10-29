package game;

import board.Board;
import board.Field;

import java.util.ArrayList;

/**
 * Created by majkl on 26.03.2016.
 */
public class Game implements java.io.Serializable{

    Board doska;
    User player1;
    User player2;
    boolean aktPlayer;
    Board lastPlay;
    ArrayList<Field> arr;

    // Inicializuje hru.
    public Game(Board board)
    {
        this.doska=board;
        aktPlayer=true;
    }



    //Přidá hráče a současně vyvolá jeho inicializaci. Pokud hráč dané barvy již existuje, nedělá nic a vrací false.
    public boolean addPlayer(User player)
    {
        if(player.isWhite() && player1==null)
        {
            player1=player;
            player1.init(doska);
            return true;
        }
        if((!player.isWhite()) && player2==null)
        {
            player2=player;
            player2.init(doska);
            return true;
        }
        return false;
    }

    //Vrátí aktuálního hráče, který je na tahu.
    public User currentPlayer()
    {
        if(aktPlayer)
            return player1;
        else
            return player2;
    }

    public void undo(Board board)
    {
        this.doska = board;
    }

    // Změní aktuálního hráče.
    public User nextPlayer()
    {
        if(!aktPlayer)
        {
            aktPlayer=true;
            return player1;
        }
        else
        {
            aktPlayer=false;
            return player2;
        }
    }

    //Vrátí hrací desku.
    public Board getBoard()
    {
        return this.doska;
    }

    public boolean endOfGame() {
        for (int i = 1; i <= this.doska.getSize(); i++) {

            for (int j = 1; j <= this.doska.getSize(); j++) {

                if (this.currentPlayer().canPutDisk(this.doska.getField(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void resetBoard(){
        for (int i = 1; i <= this.doska.getSize(); i++) {

            for (int j = 1; j <= this.doska.getSize(); j++) {
                    this.doska.getField(i,j).eraseDisk();
                }
            }
    }

    public void saveMove() {
        this.lastPlay=this.doska;
    }

    public Board getLastMove() {

        return this.lastPlay;
    }

    public void setArr(ArrayList arr)
    {
        this.arr=arr;
    }
    public ArrayList getArr()
    {
        return this.arr;
    }

}

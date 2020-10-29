package game;

import board.Board;
import board.Field;

import java.util.ArrayList;

/**
 * Created by majkl on 05.05.2016.
 */
public interface User extends java.io.Serializable{
    boolean isWhite();
    boolean emptyPool();
    ArrayList putDisk(Field field);
    void init(Board board);
    boolean canPutDisk(Field field);   //Human returns 1/0 AI returns n/0
    ArrayList turnedDisks(Field field);
    Field searchBestMove(Board deska);
    ArrayList attack(Board deska);

    void setDisks(int Disks);
    int getDisks();
}

package board;
public interface Field extends java.io.Serializable
{
    Field nextField(Direction dirs);
    Disk getDisk();
    boolean putDisk(Disk disk);
    void addNextField(Direction dirs, Field field);
    enum Direction
    {
        L,LU,U,RU,R,RD,D,LD
    }
    boolean isEmpty();
    boolean canPutDisk(Disk disk);
    int getRow();

    void eraseDisk();

    int getCol();
}


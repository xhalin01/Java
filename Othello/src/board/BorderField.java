package board;
public class BorderField implements Field {

    public void addNextField(Field.Direction dirs, Field field)
    {
    }
    public Field nextField(Field.Direction dirs)
    {
        return null;
    }
    public boolean putDisk(Disk disk)
    {
        return false;
    }
    public Disk getDisk()
    {
        return null;
    }
    public boolean canPutDisk(Disk disk){return false;}

    @Override
    public int getRow() {
        return -1;
    }

    @Override
    public int getCol() {
        return -1;
    }

    public void eraseDisk()
    {

    }
    public boolean isEmpty(){ return true;}

}

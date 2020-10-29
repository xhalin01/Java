package board;
public class BoardField implements Field
{
    Disk disk;
    boolean bool_disk;
    int row;
    int col;
    Field LEFTUP;
    Field LEFT;
    Field LEFTDOWN;
    Field RIGHTUP;
    Field RIGHT;
    Field RIGHTDOWN;
    Field UP;
    Field DOWN;

    public BoardField(int row, int col)
    {
        this.row = row;
        this.col = col;
        bool_disk = false;

    }

    public int getRow()
    {
        return this.row;
    }

    public int getCol()
    {
        return this.col;
    }

    public void addNextField(Field.Direction dirs, Field f)
    {
        if(dirs == Field.Direction.L)
        {
            LEFT = f;
        }
        else if(dirs == Field.Direction.D)
        {
            DOWN = f;
        }
        else if(dirs == Field.Direction.LD)
        {
            LEFTDOWN = f;
        }
        else if(dirs == Field.Direction.LU)
        {
            LEFTUP = f;
        }
        else if(dirs == Field.Direction.U)
        {
            UP = f;
        }
        else if(dirs == Field.Direction.RU)
        {
            RIGHTUP = f;
        }
        else if(dirs == Field.Direction.R)
        {
            RIGHT = f;
        }
        else if(dirs == Field.Direction.RD)
        {
            RIGHTDOWN = f;
        }

    }

    public boolean putDisk(Disk disk)
    {
        if(!bool_disk)
        {
            this.disk=disk;
            this.bool_disk=true;
            return true;
        }
        else
        {
            return false;
        }
    }

    public void eraseDisk()
    {
        this.disk=null;
        this.bool_disk=false;
    }


    public Field nextField(Field.Direction dirs){
        if(dirs == Field.Direction.L)
        {
            return LEFT;
        }
        else if(dirs == Field.Direction.LU)
        {
            return LEFTUP;
        }
        else if(dirs == Field.Direction.U)
        {
            return UP;
        }
        else if(dirs == Field.Direction.LD)
        {
            return LEFTDOWN;
        }
        else if(dirs == Field.Direction.RU)
        {
            return RIGHTUP;
        }
        else if(dirs == Field.Direction.R)
        {
            return RIGHT;
        }
        else if(dirs == Field.Direction.RD)
        {
            return RIGHTDOWN;
        }
        else if(dirs == Field.Direction.D)
        {
            return DOWN;
        }
        else System.out.println("Wrong direction");
        return null;
    }


    public boolean isEmpty() {
        if (bool_disk==false)
            return true;
        else return false;
    }

    public boolean continueSearch(Field pole,Field.Direction smer)
    {
        if (pole.nextField(smer)!=null) {
            while (pole.nextField(smer)!=null) {
                if (pole.nextField(smer).getDisk() != null) {
                    if(pole.getDisk()!=null) {
                        if (pole.nextField(smer).getDisk().isWhite() != pole.getDisk().isWhite())
                            return true;
                    }
                }
                pole = pole.nextField(smer);
            }
        }
        return false;
    }

    public boolean canPutDisk(Disk disk) {
        if(!this.isEmpty())
            return false;

        for (Field.Direction c : Field.Direction.values())
        {
            if(this.nextField(c)!=null)
            {
                if (this.nextField(c).getDisk() != null)
                {
                    if (this.nextField(c).getDisk().isWhite() != disk.isWhite())
                    {
                        if (continueSearch(this.nextField(c), c))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public Disk getDisk()
    {
        if(bool_disk) {
            return this.disk;
        }
        else {
            return null;
        }
    }

    public boolean equals(java.lang.Object o)
    {
        BoardField board = (BoardField) o;
        if(board.row == this.row && board.col == this.col && board.bool_disk == this.bool_disk)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

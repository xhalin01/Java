package board;
public class Disk implements java.io.Serializable
{
    boolean color;

    public Disk(boolean isWhite)
    {
        this.color = isWhite;
    }

    public void turn()
    {
        if(color){
            color= false;
        }
        else{
            color = true;
        }
    }
    public boolean isWhite()
    {
        return color;
    }


    public boolean equals(Disk d)
    {

        if (d.isWhite()==this.isWhite())
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

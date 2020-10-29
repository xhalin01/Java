package board;
public class Board implements java.io.Serializable
{

    private Rules rules;
    private Field [][] field;

    public Board(Rules rules){
        this.rules = rules;
        this.field = new Field[rules.getSize()+2][rules.getSize()+2];

        //create field
        this.rules = rules;
        for (int i = 0; i < rules.getSize()+1; ++i){
            for (int j = 0; j < rules.getSize()+1; ++j){
                this.field[i][j] = this.rules.createField(i,j);
            }
        }

        //save the neighbourhood fields
        for(int x = 1; x <= rules.getSize(); x++)
        {
            for(int y = 1; y <= rules.getSize(); y++){
                this.field[x][y].addNextField(Field.Direction.RU, this.field[x-1][y+1]);
                this.field[x][y].addNextField(Field.Direction.R, this.field[x][y+1]);
                this.field[x][y].addNextField(Field.Direction.RD, this.field[x+1][y+1]);
                this.field[x][y].addNextField(Field.Direction.LD, this.field[x+1][y-1]);
                this.field[x][y].addNextField(Field.Direction.D, this.field[x+1][y]);
                this.field[x][y].addNextField(Field.Direction.L, this.field[x][y-1]);
                this.field[x][y].addNextField(Field.Direction.LU, this.field[x-1][y-1]);
                this.field[x][y].addNextField(Field.Direction.U, this.field[x-1][y]);
            }
        }
    }
    public int getSize()
    {
        return this.rules.getSize();
    }
    public Field getField(int row, int col)
    {
        return field[row][col];
    }
    public Rules getRules(){return this.rules; }
}

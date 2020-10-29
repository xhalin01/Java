package board;

/**
 * Created by majkl on 26.03.2016.
 */
public interface Rules extends java.io.Serializable{

    //Vrací velikost desky.
    int getSize();

    //Vrací počet kamenů jednotlivých hráčů.
    int numberDisks();

    //Vytvoří odpovídající pole na zadaných indexech.
    Field createField(int row, int col);
}

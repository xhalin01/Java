package game;

import board.Board;
import board.Disk;
import board.Field;

import java.util.ArrayList;


/**
 * Created by majkl on 26.03.2016.
 */
public class Player implements User {

    boolean isWhite;
    int disks;

    //Inicializace hráče.
    public Player(boolean isWhite) {

        this.isWhite = isWhite;
        this.disks = 0;
    }

    //Test barvy hráče.
    public boolean isWhite() {
        return this.isWhite;
    }

    // Test, zda je možné vložit nový kámen hráče na dané pole. Kámen se sady nevybírá ani nevkládá na pole.
    public boolean canPutDisk(Field field) {
        if (field.canPutDisk(new Disk(this.isWhite)) && disks != 0)
            return true;
        else
            return false;
    }

    public ArrayList continueSearch(Field pole, Field.Direction smer) {
        ArrayList<Field> arr = new ArrayList<Field>();
        while (!pole.nextField(smer).isEmpty()) {
            if (pole.nextField(smer).getDisk() != null) {
                if (pole.nextField(smer).getDisk().isWhite() == this.isWhite) {
                    arr.add(pole);
                } else {
                    arr.add(pole);
                    return arr;
                }
            }
            pole = pole.nextField(smer);
        }
        return arr;
    }

    public ArrayList turnedDisks(Field field) {
        ArrayList<Field> arr = new ArrayList<>();

        /*if (!field.isEmpty())
            return arr;*/
        for (Field.Direction c : Field.Direction.values()) {
            if (field.nextField(c) != null) {

                if (field.nextField(c).getDisk() != null) {
                    if (field.nextField(c).getDisk().isWhite() != this.isWhite()) {
                        arr.addAll(this.continueSearch(field.nextField(c), c));
                    }
                }
            }
        }
        return arr;
    }


    public Field searchBestMove(Board deska) {
        return null;
    }


    public ArrayList attack(Board deska) {
        ArrayList<Field> arr = new ArrayList<Field>();
        return arr;
    }

    //Test prázdnosti sady kamenů, které má hráč k dispozici.
    public boolean emptyPool() {
        if (disks == 0)
            return true;
        else
            return false;
    }

    // Vloží nový kámen hráče na dané pole, pokud to pravidla umožňují. Pokud lze vložit, vybere kámen ze sady a vloží na pole.
    public ArrayList putDisk(Field field) {
        ArrayList<Field> arr = new ArrayList<>();
        for (Field.Direction c : Field.Direction.values()) {
            if (hladaj(field.nextField(c), c)) {
                arr.addAll(otocDisky(field.nextField(c), c));
                field.putDisk(new Disk(this.isWhite));
                disks-=arr.size();
            }
        }
        return arr;
    }

    public ArrayList otocDisky(Field field, Field.Direction smer) {
        ArrayList<Field> arr = new ArrayList<>();
        Field temp = field;

        if (field.getDisk() != null) {
            if (temp.getDisk() != null) {
                do {
                    if (temp.getDisk() != null)
                        temp.getDisk().turn();
                    arr.add(temp);
                    temp = temp.nextField(smer);
                }
                while (field.getDisk().isWhite() != temp.getDisk().isWhite());
            }
        }
        return arr;
    }

    public boolean hladaj(Field pole, Field.Direction smer) {
        if (pole != null) {
            while (pole.nextField(smer) != null) {
                if (pole.getDisk() != null) {
                    if (pole.nextField(smer).getDisk() != null) {
                        if (pole.nextField(smer).getDisk().isWhite() != pole.getDisk().isWhite()) {
                            return true;
                        }
                    }
                }
                pole = pole.nextField(smer);
            }
        }
        return false;
    }

    //Inicializace hráče v rámci hrací desky. Vytvoří sadu kamenů o příslušné velikosti a umístí své počáteční kameny na desku.
    public void init(Board board) {
        int size = board.getSize();
        this.disks = (size * size) - 2;

        if (this.isWhite()) //ked je biely
        {
            board.getField(size / 2, size / 2).putDisk(new Disk(true));
            board.getField(size / 2 + 1, size / 2 + 1).putDisk(new Disk(true));
        }
        if (!this.isWhite()) //ked je cerny
        {
            board.getField(size / 2, size / 2 + 1).putDisk(new Disk(false));
            board.getField(size / 2 + 1, size / 2).putDisk(new Disk(false));
        }
    }

    @Override
    public String toString() {
        if (this.isWhite())
            return "white";
        else
            return "black";
    }

    public void setDisks(int disks){
        this.disks=disks;
    }

    public int getDisks()
    {
        return this.disks;
    }
}

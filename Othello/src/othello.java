import board.Board;
import board.Disk;
import board.Field;
import game.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.System.exit;

public class othello {

    private JButton[][] othelloBoard = new JButton[10][10];
    private JPanel otBoard;
    private final JLabel message = new JLabel(
            "Prepare to play!");
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    

    public void drawBoard(Board board) {
        ImageIcon icon1 = new ImageIcon("black.png");
        ImageIcon icon2 = new ImageIcon("white.png");
        int x = 1;
        int y = 1;
        for (x = 1; x <= 8; x++) {
            for (y = 1; y <= 8; y++) {
                othelloBoard[y - 1][x - 1].setIcon(null);
                if (board.getField(x, y).getDisk() != null) {
                    if (board.getField(x, y).getDisk().isWhite())
                        othelloBoard[y - 1][x - 1].setIcon(icon2);
                    else
                        othelloBoard[y - 1][x - 1].setIcon(icon1);
                }
            }
        }
    }

    public void clearBoard(Board board) {
        int x = 1;
        int y = 1;
        for (x = 1; x <= 8; x++) {
            for (y = 1; y <= 8; y++) {
                othelloBoard[y - 1][x - 1].setIcon(null);
            }
        }
    }

    othello() {
        initGui();
    }

    public final void initGui() {

        int size = 8;
        ReversiRules rules = new ReversiRules(size);
        Board board = new Board(rules);
        Game game = new Game(board);
        User p1 = new Player(true);
        User p2 = new Player(false);

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        final boolean[] lock = {false};
        Action newGameAction = new AbstractAction("Player vs player") {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard(game.getBoard());
                game.resetBoard();
                game.addPlayer(p1);
                game.addPlayer(p2);
                lock[0] = true;
                drawBoard(game.getBoard());
            }
        };
        tools.add(newGameAction);
        Action playWithAI = new AbstractAction("Player vs computer") {

            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard(game.getBoard());
                game.resetBoard();
                User p1 = new Player(true);
                User p2 = new MediumAI(false);
                game.addPlayer(p1);
                game.addPlayer(p2);
                drawBoard(game.getBoard());
                lock[0] = true;
            }
        };
        tools.add(playWithAI);
        Action undo = new AbstractAction("Undo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lock[0]) {

                    clearBoard(game.getBoard());
                    game.resetBoard();
                    copyFields(game, game.getArr());
                    drawBoard(game.getBoard());

                }
            }
        };
        tools.add(undo);
        tools.add(new JButton("Save"));
        tools.add(new JButton("Load"));
        tools.addSeparator();
        tools.addSeparator();
        Action put = new AbstractAction("Reset") {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.resetBoard();
                clearBoard(game.getBoard());
                game.getBoard().getField(4, 4).putDisk(new Disk(true));
                game.getBoard().getField(5, 5).putDisk(new Disk(true));
                game.getBoard().getField(4, 5).putDisk(new Disk(false));
                game.getBoard().getField(5, 4).putDisk(new Disk(false));
                drawBoard(game.getBoard());
                if (game.currentPlayer() != p1)
                    game.nextPlayer();
            }
        };
        tools.add(put);
        tools.add(message);
        otBoard = new JPanel(new GridLayout(0, 8)) {
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int) d.getWidth(), (int) d.getHeight());
                } else if (c != null &&
                        c.getWidth() > d.getWidth() &&
                        c.getHeight() > d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                int s = (w > h ? h : w);
                return new Dimension(s, s);
            }
        };
        otBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8, 8, 8, 8),
                new LineBorder(Color.BLACK)
        ));
        Color green = new Color(26, 148, 69);
        otBoard.setBackground(green);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(green);
        boardConstrain.add(otBoard);
        gui.add(boardConstrain);

        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int i = 0; i < othelloBoard.length; i++) {
            for (int j = 0; j < othelloBoard[i].length; j++) {
                JButton button = new JButton();
                button.setMargin(buttonMargin);
                ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                button.setIcon(icon);
                button.setBackground(green);
                final int finalIi = i;
                final int finalJj = j;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        User onTurn = game.currentPlayer();
                        if (onTurn.isWhite() && onTurn.canPutDisk(game.getBoard().getField(finalIi + 1, finalJj + 1)) && lock[0]) {
                            makeMove(game, onTurn, finalIi, finalJj);
                            /*if (!(game.currentPlayer() instanceof Player)) {
                                game.currentPlayer().attack(game.getBoard());
                                message.setText("Turn : White");
                                drawBoard(game.getBoard());
                                //game.nextPlayer();
                            }*/
                        } else if (!onTurn.isWhite() && onTurn.canPutDisk(game.getBoard().getField(finalIi + 1, finalJj + 1)) && lock[0]) {
                            makeMove(game, onTurn, finalIi, finalJj);
                        }
                    }
                });
                othelloBoard[j][i] = button;
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                otBoard.add(othelloBoard[j][i]);
            }
        }
    }

    public final JComponent getGui() {
        return gui;
    }

    public void otocKamene(ArrayList arr, boolean b) {
        ImageIcon icon1 = new ImageIcon("lib/black.png");
        ImageIcon icon2 = new ImageIcon("lib/white.png");

        for (Iterator<Field> i = arr.iterator(); i.hasNext(); ) {
            Field item = i.next();
            int x = item.getRow();
            int y = item.getCol();
            System.out.println("x:" + (x) + ",y:" + (y));
            if (b)
                othelloBoard[y - 1][x - 1].setIcon(icon2);
            else
                othelloBoard[y - 1][x - 1].setIcon(icon1);
        }
    }

    public void makeMove(Game game, User onTurn, int finalIi, int finalJj) {

        ArrayList<Field> arr = new ArrayList<>();
        if (onTurn.isWhite())
            message.setText("Turn : Black");
        else
            message.setText("Turn : White");

        game.setArr(saveFields(game));
        arr=onTurn.putDisk(game.getBoard().getField(finalIi + 1, finalJj + 1));
        game.nextPlayer().setDisks(game.currentPlayer().getDisks()-arr.size());
        drawBoard(game.getBoard());
        if (game.endOfGame()) {
            gameEnd(game,game.currentPlayer());
        }
    }

    public void gameEnd(Game game,User p) {

        int white;
        int black;
        String winner;
        int win;
        if(p.isWhite()) {
            white=countDisks(game,p);
            black=countDisks(game,game.nextPlayer());
        }
        else {
            black=countDisks(game,p);
            white=countDisks(game,game.nextPlayer());
        }
        if (white>black) {
            winner="White";
            win=white;
        }
        else {
            winner="Black";
            win=black;
        }
        Object[] options = {"New Game", "Quit"};
        int result = JOptionPane.showOptionDialog(null, "Winner: " + winner +"Score: "+win, "End",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);
        if (result == 0) {
            resetGame(game);
        }
        else{
            exit(0);
        }
    }

    public void resetGame(Game game) {
        game.resetBoard();
        clearBoard(game.getBoard());
        game.getBoard().getField(4, 4).putDisk(new Disk(true));
        game.getBoard().getField(5, 5).putDisk(new Disk(true));
        game.getBoard().getField(4, 5).putDisk(new Disk(false));
        game.getBoard().getField(5, 4).putDisk(new Disk(false));
        drawBoard(game.getBoard());
        if (!game.currentPlayer().isWhite())
            game.nextPlayer();
    }

    public ArrayList saveFields(Game game) {
        ArrayList<Field> arr = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (game.getBoard().getField(i, j).getDisk() == null) {
                    arr.add(game.getBoard().getField(i, j));
                }
            }
        }
        return arr;
    }

    public void copyFields(Game game, ArrayList arr) {
        int i = arr.size();
        Field temp;
        while (i != 0) {
            temp = (Field) arr.get(i - 1);
            i--;
            game.getBoard().getField(temp.getRow(), temp.getCol()).putDisk(new Disk(temp.getDisk().isWhite()));
        }
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                othello ot = new othello();

                JFrame frame = new JFrame("Othello");
                frame.add(ot.getGui());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocationByPlatform(true);
                frame.pack();
                frame.setMinimumSize(frame.getSize());
                frame.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }

    public int countDisks(Game game, User u) {
        int count = 0;
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (game.getBoard().getField(i, j).getDisk() != null) {
                    if (game.getBoard().getField(i, j).getDisk().isWhite() == u.isWhite()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
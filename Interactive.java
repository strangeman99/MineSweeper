import java.awt.Canvas;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;

public class Interactive extends Canvas
{
    private static int width;
    private static int height;

    public static void main(String[] args) throws FileNotFoundException {
        playGame();
    }

    public static void playGame() throws FileNotFoundException // to play the game
    {
        Board board = null;
        boolean used;
        used = false;

        if (loadFromSave()) // if they want to load a saved game
        {
            String name = pickName();

            if (name != null)
            {
                board = new Board();
                board.loadBoard(name);

                used = true;
            }
        }

        else if (!used) // if they want to make a new game
        {
            board = new Board(choseBoard()); // creating a board for the game
        }

        width = board.getWidth();
        height = board.getHeight();

        setUpScreen(); // creates a screen window
    }

    private static int choseBoard() // this allows the user to select a difficulty
    {
        JFrame frame = new JFrame();

        JDialog.setDefaultLookAndFeelDecorated(true);

        Object[] selectionValues = { "Easy", "Medium", "Hard" };
        String initialSelection = "Easy";
        Object selection = JOptionPane.showInputDialog(frame, "What difficulty do you want to play on?",
                "Difficulty select", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);

        if (selection.equals("Medium")) // if they chose medium
        {
            return 2;
        }

        else if (selection.equals("Hard"))
        {
            return 3;
        }

        else // if they chose easy or exit
        {
            return 1;
        }
    }

    public static void setUpScreen() // this will create a window to display the game
    {
        JFrame frame = new JFrame("Minesweeper");
        Canvas canvas = new Interactive();
        canvas.setSize(800, 800);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public static boolean loadFromSave() // loads a game from a save
    {
        JFrame frame = new JFrame();
        int response = JOptionPane.showConfirmDialog(frame, "Do you want to load from a save?", "Save", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        // if yes
        return response == JOptionPane.YES_OPTION;
    }

    public static String pickName() // for the user to pick the name of save file
    {
        JFrame frame = new JFrame();

        while(true)
        {
            // getting the name of the save
            Object tmp = JOptionPane.showInputDialog(frame, "What's the name of the save?(without .txt)");
            String name = tmp.toString();
            name += ".txt";

            File file = new File(name);

            if (file.exists()) // if the file exists
            {
                return name;
            }
            int response = JOptionPane.showConfirmDialog(frame, "Do you want to enter another name?", "File not found",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (response != JOptionPane.YES_OPTION) {
                return null;
            }
        }

    }

    @Override
    public void paint(Graphics g) // this will draw the board with graphics
    {

    }


    public static void gameLoop(Board board) // where the players movements will be tracked and the game will be updated
    {
         // drawing the board to the screen
    }

}

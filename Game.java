import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) throws FileNotFoundException {

        playGame(); //plays the game
        
    }

    public static void playGame() throws FileNotFoundException // where all the game play happens
    {
        Board board;

        if (loadGame()) // if they want to load a saved game
        {
            board = new Board();
            board.loadBoard(pickName());
        }

        else // if they want to make a new game
        {
            board = new Board(choseBoard()); // creating a board for the game
        }


        userDecisions(board); // so the user can start making decisions
    }

    public static int choseBoard() // this will return a difficulty based on user input
    {
        Scanner keyboard = new Scanner(System.in);
        String input;

        do {

            System.out.println("What difficulty do you want to play on?");
            System.out.println("Easy, Medium, or Hard? (e, m, h)");

            input = keyboard.nextLine();

            if (input.equalsIgnoreCase("e") || input.equalsIgnoreCase("easy")) // if they want easy
            {
                return 1;
            }

            else if (input.equalsIgnoreCase("m") || input.equalsIgnoreCase("medium")) // if they want medium
            {
                return 2;
            }

            else if (input.equalsIgnoreCase("h") || input.equalsIgnoreCase("hard")) // if they want hard
            {
                return 3;
            }

            else
            {
                System.out.println("I'm sorry that isn't an option.");
            }
        } while (true);
    }

    public static void userDecisions(Board board) throws FileNotFoundException // this is the game loop
    {
        Scanner keyboard = new Scanner(System.in);
        boolean exit = false;
        String input;

        do{
            board.printBoard(); // printing the board to the screen

            System.out.println("What do you want to do?");
            System.out.println("Exit, flag, or uncover? (e, f, u)");

            input = keyboard.nextLine();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("e")) // if they want to exit
            {
                exit = true;
            }

            else if (input.equalsIgnoreCase("flag") || input.equalsIgnoreCase("f")) // if they want to flag something
            {
                pickSquare(board, true);
            }

            else if (input.equalsIgnoreCase("uncover") || input.equalsIgnoreCase("u")) // if they want to uncover a block
            {
                pickSquare(board, false);
            }

            else // if input is invalid
            {
                System.out.println("That isn't a valid input. Please choose again.");
            }

        } while (!board.win() && !board.lost() && !exit); // loops until an end of game event

        endOfGameCheck(exit, board); // does the end of game checks
    }

    public static void endOfGameCheck(Boolean exit, Board board) throws FileNotFoundException // called after end of game and checks end cases
    {
        if (exit) // if the user exited
        {
            Scanner keyboard = new Scanner(System.in);
            String input;
            String name;
            boolean valid;

            System.out.println("Too bad you don't want to keep playing.");

            do {
                System.out.println("Do you want to save your progress?");
                input = keyboard.nextLine();

                if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) // if they want to
                {
                    System.out.println("What do you want the name of the file to be? (without .txt)");
                    name = keyboard.nextLine();
                    name += ".txt";

                    board.save(name);

                    valid = true;
                }

                else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) // if they don't
                {
                    System.out.println("Ok thanks for playing!");
                    valid = true;
                }

                else
                {
                    System.out.println("That isn't a valid option");
                    valid = false;
                }

            } while (!valid);
        }

        else if (board.win()) // if the user has won the game
        {
            System.out.println("You have won the game!!!");
        }

        else if (board.lost()) // if the user has lost the game
        {
            System.out.println("You have lost the game. Uncovering a mine isn't the objective.");
        }
    }

    public static void pickSquare(Board board, Boolean flag) // if the user wants to flag or uncover a square
    {
        Scanner keyboard = new Scanner(System.in);
        int x, y;
        boolean valid;

        do {
            System.out.println("What square do you want to pick?");
            System.out.print("X? ");
            x = (keyboard.nextInt()-1);
            System.out.println();

            System.out.print("Y? ");
            y = (keyboard.nextInt()-1);

            if (board.withinBoard(x, y)) // valid coordinates
            {
                valid = true;

                if (flag) // if its a flag
                {
                    board.flag(x, y); // flagging that square
                }

                else // if they want to uncover a square
                {
                    board.uncover(x, y); // uncovering that square

                    if (board.isZero(x, y)) // if the newly uncovered square zero
                    {
                        board.explosion(x, y); // checking for an explosion scenario
                    }
                }
            }

            else
            {
                System.out.println("Those coordinates aren't within the board.");
                valid = false;
            }

        } while (!valid); // loops until valid input
    }

    public static Boolean loadGame() // checks to see if the user wants to load from a save
    {
        Scanner keyboard = new Scanner(System.in);
        String input;

        while (true)
        {
            System.out.println("Do you want to load from a save?");
            input = keyboard.nextLine();

            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) // if they want to
            {
                return true;
            }

            else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) // if they don't
            {
                return false;
            }

            else
            {
                System.out.println("That isn't a valid response.");
            }
        }

    }

    public static String pickName() // pick a name of the file
    {
        Scanner keyboard = new Scanner(System.in);
        String name;

        while (true)
        {
            System.out.println("What is the name of the file? (without .txt)");
            name = keyboard.nextLine();
            name += ".txt";

            File file = new File(name);

            if (file.exists()) // if the file exists
            {
                return name;
            }

            System.out.println("There isn't a file with that name");
        }
    }

}

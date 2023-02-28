import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Board {

    private Square[][] board;
    private int mines;
    private int height;
    private int width;

    public Board(int difficulty) // creates a certain board based on the difficulty
    {
        difficultySelect(difficulty); // creating a board based on difficulty

        pickMines(); // picking the mines for the board

        calcNumMines(); // this calculates how many mines are within a radius
    }

    public Board() // a default constructor
    {

    }

    public Boolean withinBoard(int x, int y) // this check if the entered coordinates are within the board
    {
        // if it fits
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public void flag(int x, int y) // to flag a square
    {
        board[x][y].setFlagged(true); // flagging the spot on the board
    }

    public void uncover(int x, int y) // to uncover a specific square
    {
        board[x][y].setCovered(false); // uncovering that square
    }

    public Boolean isZero(int x, int y) // checking if the square is zero
    {
        return board[x][y].getNumClose() == 0 && !board[x][y].getMine();
    }

    private void difficultySelect(int difficulty) // sets the board based on difficulty
    {
        if (difficulty == 1) // easy
        {
            height = 9;
            width = 9;
            mines = 10;
        }

        else if (difficulty == 2) // medium
        {
            height = 16;
            width = 16;
            mines = 40;
        }

        else // hard
        {
            height = 16;
            width = 30;
            mines = 99;
        }

        board = new Square[width][height]; // creating the board with the difficulty level

        // looping through the whole array to initialize
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                board[i][j] = new Square(true, false, false,0);
            }
        }
    }

    private void pickMines() // this will assign random mines
    {
        Random random = new Random();
        int count = 0;

        do
        {
            // generating random coordinates
            int x = random.nextInt(width-1);
            int y = random.nextInt(height-1);

            if (!board[x][y].getMine()) // if this spot doesn't have a mine
            {
                board[x][y].setMine(true); // set the mine

                count++;
            }

        } while (count < mines); // loops until all mines have been placed
    }

    private void calcNumMines() // this calculates how many mines are within a radius
    {
        // loop through the board
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (!board[i][j].getMine()) // if it isn't a mine
                {
                    board[i][j].setNumClose(minesInRad(detPos(i, j))); // setting the amount close
                }
            }
        }
    }

    private int[] detPos(int x, int y) // determines the position of the square
    {
        int[] position = new int[4]; // since it will have 4 positions

        if ((x-1) < 0 && (y-1) < 0) // top left corner
        {
            position[0] = x;
            position[1] = (x+1);
            position[2] = y;
            position[3] = (y+1);
        }

        else if ((x-1) < 0 && (y+1) >= height) // bottom left corner
        {
            position[0] = x;
            position[1] = (x+1);
            position[2] = (y-1);
            position[3] = y;
        }

        else if ((x+1) >= width && (y-1) < 0) // top right corner
        {
            position[0] = (x-1);
            position[1] = x;
            position[2] = y;
            position[3] = (y+1);
        }

        else if ((x+1) >= width && (y+1) >= height) // bottom right corner
        {
            position[0] = (x-1);
            position[1] = x;
            position[2] = (y-1);
            position[3] = y;
        }

        else if ((x-1) < 0) // if its on the left
        {
            position[0] = x;
            position[1] = (x+1);
            position[2] = (y-1);
            position[3] = (y+1);
        }

        else if ((y-1) < 0) // if its at the top
        {
            position[0] = (x-1);
            position[1] = (x+1);
            position[2] = y;
            position[3] = (y+1);
        }

        else if ((x+1) >= width) // if its on the right
        {
            position[0] = (x-1);
            position[1] = x;
            position[2] = (y-1);
            position[3] = (y+1);
        }

        else if ((y+1) >= height) // if its on the bottom
        {
            position[0] = (x-1);
            position[1] = (x+1);
            position[2] = (y-1);
            position[3] = y;
        }

        else // if its a normal square
        {
            position[0] = (x-1);
            position[1] = (x+1);
            position[2] = (y-1);
            position[3] = (y+1);
        }

        return position;
    }

    private int minesInRad(int[] positions) // calculating the mines around the square
    {
        int count = 0;

        for (int i = positions[0]; i <= positions[1]; i++)
        {
            for (int j = positions[2]; j <= positions[3]; j++)
            {
                if (board[i][j].getMine()) // if its a mine
                {
                    count++;
                }
            }
        }

        return count;
    }

    public void printBoard() // to print the board to the screen
    {
        System.out.print("   ");
        for (int i = 1; i <= width; i++)
        {
            System.out.print(i + "|");
        }

        System.out.println();

        System.out.print("  ");

        for (int i = 0; i < ((width*2)+1); i++)
        {
            System.out.print("_");
        }

        System.out.println();

        for (int i = 1; i <= height; i++)
        {
            System.out.print(i + " ");
            System.out.print("|");

            for (int j = 0; j < width; j++)
            {
                board[j][i-1].print();

               System.out.print("|");
            }

            System.out.println();

            System.out.print("  ");

            for (int k = 0; k < ((width*2)+1); k++)
            {
                System.out.print("_");
            }

            System.out.println();
        }
    }

    public void coverSquares() // to cover all squares
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                board[i][j].setCovered(true);
            }
        }
    }

    public void uncoverSquares() // to uncover squares
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                board[i][j].setCovered(false);
            }
        }
    }

    public Boolean win() // checks to see if the user has won
    {
        int minesCovered = 0; // to keep track of the mines covered
        int nonMineSquares = (width*height) - mines; // the non mine squares on the board
        int squaresUncovered = 0; // the non-mine squares uncovered

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (board[i][j].getMine() && board[i][j].getFlagged() && board[i][j].getCovered()) // if its a flagged mine
                {
                    minesCovered++;
                }

                else if (!board[i][j].getMine() && !board[i][j].getCovered() && !board[i][j].getFlagged()) // if its a normal square not flagged
                {
                    squaresUncovered++;
                }
            }
        }

        // if the mines are all flagged and all other squares are uncovered return true
        return (minesCovered == mines) && (nonMineSquares == squaresUncovered);
    }

    public Boolean lost() // checks to see if the user has lost
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
               if (board[i][j].getMine() && !board[i][j].getCovered()) // if its an uncovered mine
               {
                   return true;
               }
            }
        }

        return false;
    }

    public void explosion(int x, int y) // runs through an explosion scenario staring from a point
    {
        int[] positions = detPos(x, y); // determining the position of the square

        for (int i = positions[0]; i <= positions[1]; i++)
        {
            for (int j = positions[2]; j <= positions[3]; j++)
            {
                if (board[i][j].getNumClose() == 0 && !board[i][j].getMine() && board[i][j].getCovered()) // if the square is 0
                {
                    board[i][j].setCovered(false); // uncover the square

                    explosion(i, j); // create a new explosion on that square
                }

                else if (!board[i][j].getMine() && board[i][j].getCovered()) // if its a non-zero square
                {
                    board[i][j].setCovered(false); // uncover the square
                }
            }
        }
    }

    public void findZero() // this will tell me where a zero is. For testing
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (board[i][j].getNumClose() == 0 && !board[i][j].getMine()) // if the value is zero
                {
                    System.out.println("Zero @ x: " + (i+1) + " y: " + (j+1));

                    return;
                }
            }
        }
    }

    public void save(String fileName) throws FileNotFoundException // saves the board in a file
    {
        PrintWriter file = new PrintWriter(fileName); // creating the file

        // printing board information
        file.println(width);
        file.println(height);
        file.println(mines);

        // printing the board contents
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                file.println(board[i][j].getCovered() + " " + board[i][j].getMine() + " " + board[i][j].getFlagged() + " " + board[i][j].getNumClose());
            }
        }

        file.close(); // closing the file
    }

    public void loadBoard(String fileName) throws FileNotFoundException // loading a already saved board
    {
        File file = new File(fileName); // opening the file

        if (file.exists()) // if the file exists
        {
            Scanner inputFile = new Scanner(file); // to scan the file

            width = inputFile.nextInt();
            height = inputFile.nextInt();
            mines = inputFile.nextInt();

            board = new Square[width][height]; // creating the board

            // looping through the whole array to initialize
            for (int i = 0; i < width; i++)
            {
                for (int j = 0; j < height; j++)
                {
                    board[i][j] = new Square(true, false, false,0);
                }
            }

            // printing the board contents
            for (int i = 0; i < width; i++)
            {
                for (int j = 0; j < height; j++)
                {
                    board[i][j].setCovered(inputFile.nextBoolean()); // if its covered or not

                    board[i][j].setMine(inputFile.nextBoolean()); // if its a mine or not

                    board[i][j].setFlagged(inputFile.nextBoolean()); // if its flagged or not

                    board[i][j].setNumClose(inputFile.nextInt());
                }
            }

            inputFile.close(); // closing the file
        }

        else
        {
            System.out.println("That file name doesn't exist. Try again.");
        }
    }

    public void solveBoard() // this will fully solve the board (For testing)
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (board[i][j].getMine()) // if its a mine flag it
                {
                    board[i][j].setFlagged(true);
                }

                else // if its not a mine uncover it
                {
                    board[i][j].setCovered(false);
                }
            }
        }
    }

    public int getHeight() // returns the height
    {
        return height;
    }

    public int getWidth() // returns the width
    {
        return width;
    }
}

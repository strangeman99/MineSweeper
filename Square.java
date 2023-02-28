public class Square {
    // variables for the class
    private Boolean covered;
    private Boolean mine;
    private Boolean flagged;
    private int numClose;

    public Square(Boolean covered, Boolean mine, Boolean flagged, int numClose) // constructor
    {
        this.covered = covered;
        this.mine = mine;
        this.flagged = flagged;
        this.numClose = numClose;
    }

    public void setCovered(Boolean covered) // to uncover or cover the block
    {
        this.covered = covered;
    }

    public void print() // printing the square to the screen
    {
        if (covered && !flagged)
        {
            System.out.print("?");
        }

        else if (covered)
        {
            System.out.print("!");
        }

        else
        {
            if (mine)
            {
                System.out.print("M");
            }

            else
            {
                if (numClose != 0)
                {
                    System.out.print(numClose);
                }

                else
                {
                    System.out.print(" ");
                }
            }
        }
    }

    public void setFlagged(Boolean flagged) // to flag or un-flag a possible mine
    {
        this.flagged = flagged;
    }

    public Boolean getCovered()   // to check if its covered
    {
        return covered;
    }

    public Boolean getMine()  // to check if its a mine
    {
        return mine;
    }

    public Boolean getFlagged() // to check if its flagged
    {
        return flagged;
    }

    public int getNumClose() // returns the number of mines close to the square
    {
        return numClose;
    }

    public void setMine(Boolean mine) // to make this square a mine
    {
        this.mine = mine;
    }

    public void setNumClose(int numClose) // to set the amount of mines close to this square
    {
        this.numClose = numClose;
    }

}


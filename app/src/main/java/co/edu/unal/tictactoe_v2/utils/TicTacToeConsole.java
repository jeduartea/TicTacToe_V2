package co.edu.unal.tictactoe_v2.utils;
import java.util.Random;

public class TicTacToeConsole {

    private char mBoard[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private final int BOARD_SIZE = 9;

    public static final char HUMAN_PLAYER = 'X';
    public static final char COMPUTER_PLAYER = 'O';
    public static final char OPEN_SPOT = ' ';


    private Random mRand = new Random();

    char turn = HUMAN_PLAYER;    // Human starts first
    int win = 0;                // Set to 1, 2, or 3 when game is over

    public int TicTacToeConsoleV2(int position) {
        win = checkForWinner();
        position--;
        displayBoard();
        mBoard[position] = HUMAN_PLAYER;
        turn = COMPUTER_PLAYER;
        win = checkForWinner();
        if (reportWinner() > 9) {
            win = checkForWinner();
            return reportWinner();
        } else {
            int move = getComputerMove();
            turn = HUMAN_PLAYER;
            win = checkForWinner();
            displayBoard();
            reportWinner();
            return move + 1;
        }
    }
    public int TicTacToeConsoleHuman(int position) {
        win = checkForWinner();
        position--;
        displayBoard();
        mBoard[position] = HUMAN_PLAYER;
        turn = COMPUTER_PLAYER;
        win = checkForWinner();
        if (reportWinner() > 9) {
            win = checkForWinner();
            return reportWinner();
        } else {
            int move = getComputerMove();
            turn = HUMAN_PLAYER;
            win = checkForWinner();
            displayBoard();
            reportWinner();
            return move + 1;
        }
    }

    public char[] getBoard() {
        return mBoard;
    }

    public void setBoard(char[] ticTacToe) {
        mBoard = ticTacToe;
        displayBoard();
    }

    public int reportWinner() {
        // Report the winner
        System.out.println();
        if (win == 1) {
            System.out.println("It's a tie.");
            return 10;
        } else if (win == 2) {
            System.out.println(HUMAN_PLAYER + " wins!");
            return 11;
        } else if (win == 3) {
            System.out.println(COMPUTER_PLAYER + " wins!");
            return 12;
        } else {
            return 0;
        }
    }

    public void reset() {
        mBoard = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        mRand = new Random();
        win = 0;
    }


    void displayBoard() {
        System.out.println();
        System.out.println(mBoard[0] + " | " + mBoard[1] + " | " + mBoard[2]);
        System.out.println("-----------");
        System.out.println(mBoard[3] + " | " + mBoard[4] + " | " + mBoard[5]);
        System.out.println("-----------");
        System.out.println(mBoard[6] + " | " + mBoard[7] + " | " + mBoard[8]);
        System.out.println();
    }

    // Check for a winner.  Return
    //  0 if no winner or tie yet
    //  1 if it's a tie
    //  2 if X won
    //  3 if O won
    private int checkForWinner() {

        // Check horizontal wins
        for (int i = 0; i <= 6; i += 3) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i + 1] == HUMAN_PLAYER &&
                    mBoard[i + 2] == HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i + 1] == COMPUTER_PLAYER &&
                    mBoard[i + 2] == COMPUTER_PLAYER)
                return 3;
        }

        // Check vertical wins
        for (int i = 0; i <= 2; i++) {
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i + 3] == HUMAN_PLAYER &&
                    mBoard[i + 6] == HUMAN_PLAYER)
                return 2;
            if (mBoard[i] == COMPUTER_PLAYER &&
                    mBoard[i + 3] == COMPUTER_PLAYER &&
                    mBoard[i + 6] == COMPUTER_PLAYER)
                return 3;
        }

        // Check for diagonal wins
        if ((mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER) ||
                (mBoard[2] == HUMAN_PLAYER &&
                        mBoard[4] == HUMAN_PLAYER &&
                        mBoard[6] == HUMAN_PLAYER))
            return 2;
        if ((mBoard[0] == COMPUTER_PLAYER &&
                mBoard[4] == COMPUTER_PLAYER &&
                mBoard[8] == COMPUTER_PLAYER) ||
                (mBoard[2] == COMPUTER_PLAYER &&
                        mBoard[4] == COMPUTER_PLAYER &&
                        mBoard[6] == COMPUTER_PLAYER))
            return 3;

        // Check for tie
        for (int i = 0; i < BOARD_SIZE; i++) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER)
                return 0;
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1;
    }

    private int getComputerMove() {
        int move;

        // First see if there's a move O can make to win
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];
                mBoard[i] = COMPUTER_PLAYER;
                if (checkForWinner() == 3) {
                    System.out.println("Computer is moving to " + (i + 1));
                    return i;
                } else
                    mBoard[i] = curr;
            }
        }

        // See if there's a move O can make to block X from winning
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                char curr = mBoard[i];   // Save the current number
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2) {
                    mBoard[i] = COMPUTER_PLAYER;
                    System.out.println("Computer is moving to " + (i + 1));
                    return i;
                } else
                    mBoard[i] = curr;
            }
        }

        // Generate random move
        do {
            move = mRand.nextInt(BOARD_SIZE);
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER);

        System.out.println("Computer is moving to " + (move + 1));

        mBoard[move] = COMPUTER_PLAYER;
        return move;
    }
}

package edu.unc.epeden.eightqueenspuzzlegame;

/**
 * Created by epeden on 1/24/16.
 */
public class EightQueens {

    private static int[] queens;
    private int col = 0;
    private int badx,bady;
    boolean boardstuck = false;

    EightQueens(){
        queens = new int[8];
        queens[0] = -1;
        col = 0;
    }
    public String move(int column, int toRow) {
        if (boardstuck == true) {
            if (column == col - 1 && toRow == queens[col - 1]) {
                boardstuck = false;
                 return move(column,toRow);
            } else {
                return "board_stuck";
            }
        } else {
            if (col == column) {
                if (toRow < 0 || toRow > 7) {
                    return "outside_board";               // clicking outside the board
                } else {
                    queens[col] = toRow;
                    if (!unsafe(col)) {
                        col++;
                        queens[col] = -1;
                        return "success";        // move made
                    } else {
                        col++;
                        queens[col] = -1;
                        return "bad_move";   // this row is not safe to move to
                    }
                }
            } else if (column == col - 1) {
                return "last_column";
            } else {
                return "wrong_column"; // clicking the wrong column
            }
        }
    }
    boolean unsafe(int y) {
        int x = queens[y];
        for (int i = 1; i <= y; i++) {
            int t = queens[y - i];
            if (t == x || t == x - i || t == x + i) {
                bady = t;
                badx = y - i;
                return true;
            }
        }
        return false;
    }


    public void backtrace() {
        col--;
    }
    public int[] solve() {
        int y = col;
        queens[y] = -1;
        while (y >= 0) {
            do {
                queens[y]++;
            } while ((queens[y] < 8) && unsafe(y));
            if (queens[y] < 8) {
                if (y < 7) {
                    queens[++y] = -1;
                } else {
                    return queens;
                }
            } else {
                y--;
            }
        }
        return queens;
    }
    public String solveAllBoards() {
        int y = col;
        String s;
        String out = "";
        queens[y] = -1;
        while (y >= 0) {
            do {
                queens[y]++;
            } while ((queens[y] < 8) && unsafe(y));
            if (queens[y] < 8) {
                if (y < 7) {
                    queens[++y] = -1;
                } else {
                    s = putBoard();
                    out = out + s;
                }
            } else {
                y--;
            }
        }
        return out;
    }
    public void setBoardStuck(boolean b) {
        boardstuck = b;
    }
    public boolean getBoardStuck() {
        return boardstuck;
    }
    public int getBadX(){
        return badx;
    }
    public int getBadY() {
        return bady;
    }
    
    static int s = 0;
    
    public String putBoard() {
        String st = "";
        st = st + "\n\nSolution " + (++s) + "\n";
        System.out.println("\n\nSolution " + (++s));
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                st = st + ((queens[y] == x) ? "|Q" : "|_");
                System.out.print((queens[y] == x) ? "|Q" : "|_");
            }
            st = st + "|\n";
            System.out.println("|");
        }
        return st;
    }
}

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;

public class Board {

    private final int[][] blocks;
    private final int n;

    public Board(int[][] blocks) {
        this.n = blocks.length;
        this.blocks = new int[n][n];
        // deep copy blocks to make it immutable
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return n;
    }

    private boolean isNotInPlace(int row, int col) {
        int actual = blocks[row][col];
        int expected = row * n + col + 1;
        return actual != 0 && actual != expected;
    }

    private int[][] copyBlocks() {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        return copy;
    }

    public int hamming() {
        int numOfWrongs = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isNotInPlace(i, j)) numOfWrongs++;
            }
        }
        return numOfWrongs;
    }

    public int manhattan() {
        int totalDistances = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int actual = blocks[i][j];
                int expectedRow = (actual - 1) / n;
                int expectedCol = (actual - 1) % n;
                int distance = Math.abs(i - expectedRow) + Math.abs(j - expectedCol);
                if (actual != 0) totalDistances += distance;
            }
        }
        return totalDistances;
    }

    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int actual = blocks[i][j];
                int expected = i * n + j + 1;
                if (actual != 0 && actual != expected) return false;
            }
        }
        return true;
    }

    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;

        Board other = (Board) y;
        if (n != other.n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != other.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    public Board twin() {
        int[][] twin  = copyBlocks();

        if (twin[0][0] != 0 && twin[0][1] != 0) {
            int tmp = twin[0][0];
            twin[0][0] = twin[0][1];
            twin[0][1] = tmp;
        } else {
            int tmp = twin[1][0];
            twin[1][0] = twin[1][1];
            twin[1][1] = tmp;
        }

        Board twinboard = new Board(twin);
        return twinboard;
    }


    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {

                    for (int t = 0; t < dx.length; t++) {
                        int nx = i + dx[t];
                        int ny = j + dy[t];
                        if (nx >= 0 && nx < n && ny >= 0 && ny < n) {
                            int[][] tmpblock = copyBlocks();
                            int toMove = blocks[nx][ny];
                            tmpblock[nx][ny] = 0;
                            tmpblock[i][j] = toMove;
                            Board nboard = new Board(tmpblock);
                            stack.push(nboard);
                        }
                    }

                }
            }
        }
        return stack;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }



    public static void main(String[] args) {

    }
}

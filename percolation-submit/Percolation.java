import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class Percolation {

    // 0 means blocked, 1 means open
    private boolean[] isOpen;
    private int numberOfOpenSites = 0;
    private final int n;
    private final WeightedQuickUnionUF unionUF;
    private final WeightedQuickUnionUF unionUpper;
    private final int upperDot;
    private final int lowerDot;

    public Percolation(int n) {
        if(n <= 0) throw new IllegalArgumentException();

        this.n = n;
        isOpen = new boolean[n*n];

        unionUF = new WeightedQuickUnionUF(n*n+2);
        unionUpper = new WeightedQuickUnionUF(n*n+1);
        upperDot = n*n;
        lowerDot = n*n+1;
    }

    private boolean isValid(int row, int col)
    {
        if (row < 1 || row > n || col < 1 || col > n)
            return false;
        return true;
    }

    private int getHash(int row, int col) {
        return (row-1)*n+col-1;
    }

    // hash: (row-1)*n+col-1
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();

        int index = getHash(row, col);
        if (isOpen[index]) return;

        numberOfOpenSites++;
        isOpen[index] = true;

        if (row == 1) {
            unionUF.union(index, upperDot);
            unionUpper.union(index, upperDot);
        }

        if (row == n) {
            unionUF.union(index, lowerDot);
        }


        // union up,down,left,right site if opened
        if (isValid(row-1, col) && isOpen(row-1, col)) {
            int index2 = getHash(row-1, col);
            unionUF.union(index, index2);
            unionUpper.union(index, index2);
        }

        if (isValid(row+1, col) && isOpen(row+1, col)) {
            int index2 = getHash(row+1, col);
            unionUF.union(index, index2);
            unionUpper.union(index, index2);
        }

        if (isValid(row, col-1) && isOpen(row, col-1)) {
            int index2 = getHash(row, col-1);
            unionUF.union(index, index2);
            unionUpper.union(index, index2);
        }

        if (isValid(row, col+1) && isOpen(row, col+1)) {
            int index2 = getHash(row, col+1);
            unionUF.union(index, index2);
            unionUpper.union(index, index2);
        }

    }


    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();

        int index = getHash(row, col);
        return isOpen[index];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();

        int index = getHash(row, col);
        return unionUpper.connected(index, upperDot);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return unionUF.connected(upperDot, lowerDot);
    }

}

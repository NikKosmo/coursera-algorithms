import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[] sites;
    private final int size;

    private final WeightedQuickUnionUF quf;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        int totalSites = n * n;
        this.sites = new boolean[totalSites];
        this.size = n;
        this.quf = new WeightedQuickUnionUF(totalSites);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int currentSite = getTargetPlace(row, col);
        if (!sites[currentSite]) {
            sites[currentSite] = true;
            if (col != 0) {
                linkLeft(currentSite);
            }
            if (col < size - 1) {
                linkRight(currentSite);
            }
            if (row != 0) {
                linkBottom(currentSite);
            }
            if (row < size - 1) {
                linkTop(currentSite);
            }
        }
    }

    private void linkLeft(int currentSite) {
        int targetSite = currentSite - 1;
        link(currentSite, targetSite);
    }

    private void linkRight(int currentSite) {
        int targetSite = currentSite + 1;
        link(currentSite, targetSite);
    }

    private void linkBottom(int currentSite) {
        int targetSite = currentSite - size;
        link(currentSite, targetSite);
    }

    private void linkTop(int currentSite) {
        int targetSite = currentSite + size;
        link(currentSite, targetSite);
    }

    private void link(int currentSite, int targetSite) {
        if (sites[targetSite]) {
            quf.union(currentSite, targetSite);
        }
    }

    public boolean isOpen(int row, int col) {
        return !isFull(row, col);
    }

    public boolean isFull(int row, int col) {
        return !sites[getTargetPlace(row, col)];
    }

    private int getTargetPlace(int row, int col) {
        return row * size + col;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int openSites = 0;
        for (boolean isOpen : sites) {
            if (isOpen) {
                openSites++;
            }
        }
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        int lastRow = size - 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int bottomRowSite = getTargetPlace(0, i);
                int topRowSite = getTargetPlace(lastRow, j);
                if (quf.connected(bottomRowSite, topRowSite)) {
                    System.out.println("---------");
                    System.out.println(bottomRowSite + " " + topRowSite);
                    print();
                    return true;
                }
            }
        }
        return false;
    }

    public void print() {
        int lastColumn = size - 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                String site = sites[getTargetPlace(i, j)] ? "\u25A2" : "\u25A6";
                if (j != lastColumn) {
                    System.out.print(site);
                } else {
                    System.out.println(site);
                }
            }
        }
    }

    // test client (optional)
    public static void main(String[] args) {
    }


}

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[] sites;
    //    private final boolean[] grounded;
    private final int size;
    private final int totalSites;
    private final WeightedQuickUnionUF qufWaterSource;
    private final WeightedQuickUnionUF qufRoot;
    private int openSites = 0;
    private boolean percolates = false;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.totalSites = n * n;
        this.sites = new boolean[totalSites];
//        this.grounded = new boolean[totalSites];
        this.size = n;
        this.qufWaterSource = new WeightedQuickUnionUF(totalSites + 1);
        this.qufRoot = new WeightedQuickUnionUF(totalSites + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkInput(row, col);
        int currentSite = getTargetPlace(row, col);
        if (!sites[currentSite]) {
            openSites++;
            sites[currentSite] = true;
            if (col != 1) {
                linkLeft(currentSite);
            }
            if (col < size) {
                linkRight(currentSite);
            }
            if (row != 1) {
                linkTop(currentSite);
            } else {
                linkWaterSource(currentSite);
            }
            if (row < size) {
                linkBottom(currentSite);
            } else {
                linkRoot(currentSite);
            }
            tryToPercolate(currentSite);
        }
    }

    private void linkWaterSource(int currentSite) {
        qufWaterSource.union(currentSite, totalSites);
    }

    private void linkRoot(int currentSite) {
        qufRoot.union(currentSite, totalSites);
    }

    private void checkInput(int row, int col) {
        if (row <= 0 || row > size) {
            throw new IllegalArgumentException();
        }
        if (col <= 0 || col > size) {
            throw new IllegalArgumentException();
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

    private void linkTop(int currentSite) {
        int targetSite = currentSite - size;
        link(currentSite, targetSite);
    }

    private void linkBottom(int currentSite) {
        int targetSite = currentSite + size;
        link(currentSite, targetSite);
    }

    private void link(int currentSite, int targetSite) {
        if (sites[targetSite]) {
            qufWaterSource.union(currentSite, targetSite);
            qufRoot.union(currentSite, targetSite);
        }
    }

    private void tryToPercolate(int currentSite) {
        if (!percolates && isFull(currentSite) && isConnectedToRoot(currentSite)) {
            percolates = true;
        }
    }

    public boolean isOpen(int row, int col) {
        checkInput(row, col);
        return sites[getTargetPlace(row, col)];
    }

    public boolean isFull(int row, int col) {
        checkInput(row, col);
        return isFull(getTargetPlace(row, col));
    }

    private boolean isFull(int currentSite) {
        return isConnectedToWaterSource(currentSite);
    }

    private int getTargetPlace(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return percolates;
    }

    private boolean isConnectedToWaterSource(int i) {
        return qufWaterSource.find(i) == qufWaterSource.find(totalSites);
    }

    private boolean isConnectedToRoot(int i) {
        return qufRoot.find(i) == qufRoot.find(totalSites);
    }

    public static void main(String[] args) {
        // Do nothing
    }


}

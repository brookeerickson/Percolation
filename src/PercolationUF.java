
/**
 * Simulate a system to see its Percolation Threshold, but use a UnionFind
 * implementation to determine whether simulation occurs. The main idea is that
 * initially all cells of a simulated grid are each part of their own set so
 * that there will be n^2 sets in an nxn simulated grid. Finding an open cell
 * will connect the cell being marked to its neighbors --- this means that the
 * set in which the open cell is 'found' will be unioned with the sets of each
 * neighboring cell. The union/find implementation supports the 'find' and
 * 'union' typical of UF algorithms.
 * <P>
 * 
 * @author Owen Astrachan
 * @author Jeff Forbes
 *
 */

public class PercolationUF implements IPercolate {
private final int OUT_BOUNDS = -1;
private boolean[][] myGrid;
private IUnionFind myFinder;
private int numOpen;
private final int VTOP;
private final int VBOTTOM;
/**
 * Constructs a Percolation object for a nxn grid that that creates
 * a IUnionFind object to determine whether cells are full
 */
public PercolationUF(int size, IUnionFind newFinder) {
	myGrid = new boolean[size][size];
	myFinder = newFinder;
	numOpen = 0;
	VBOTTOM = (size*size)+1;
	VTOP = size*size;
	for (int i=0;i<size;i++) {
		for (int j=0;j<size;j++) {
			myGrid[i][j] = false;}}
	myFinder.initialize((size*size)+2);
}
private void updateOnOpen(int row, int col) {
	int a = row-1;
	int b = row+1;
	int c = col-1;
	int d = col+1;
	int num = getIndex(row,col);
	myGrid[row][col] = true;
	if (row==0) myFinder.union(VTOP, num);
	if (row==myGrid.length-1) myFinder.union(VBOTTOM, num);
	if (inBounds(a,col) && isOpen(a,col)) myFinder.union(getIndex(a,col), num); 
	if (inBounds(b,col) && isOpen(b,col)) myFinder.union(getIndex(b,col), num);
	if (inBounds(row,c) && isOpen(row,c)) myFinder.union(getIndex(row,c), num);
	if (inBounds(row,d) && isOpen(row,d)) myFinder.union(getIndex(row,d), num);}

private boolean inBounds(int row, int col) {
	if (row < 0 || row >= myGrid.length) return false;
	if (col < 0 || col >= myGrid[0].length) return false;
	return true;
}
/**
 * Return an index that uniquely identifies (row,col), typically an index
 * based on row-major ordering of cells in a two-dimensional grid. However,
 * if (row,col) is out-of-bounds, return OUT_BOUNDS.
 */
private int getIndex(int row, int col) {
if (inBounds(row,col)) return (myGrid.length*row)+col;
return OUT_BOUNDS;
}

public void open(int i, int j) {
	if (!inBounds(i,j)) throw new IndexOutOfBoundsException("This is out of bounds!");
	if (myGrid[i][j]==false) {
		updateOnOpen(i,j);
		numOpen++;
	}
}

public boolean isOpen(int i, int j) {
	if (!inBounds(i,j)) throw new IndexOutOfBoundsException("This is out of bounds!");
	return myGrid[i][j];
}

public boolean isFull(int i, int j) {
	if (!inBounds(i,j)) throw new IndexOutOfBoundsException("This is out of bounds!");
	int num = getIndex(i,j);
	if (myFinder.connected(VTOP, num)) return true;
	return false;
}

public int numberOfOpenSites() {
	return numOpen;
}

public boolean percolates() {
	return myFinder.connected(VTOP, VBOTTOM);
}

/**
 * Connect new site (row, col) to all adjacent open sites
 */
private void connect(int row, int col) {
	int a = row-1;
	int b = row+1;
	int c = col-1;
	int d = col+1;
	int num = getIndex(row,col);
	if (row==0) myFinder.union(VTOP, num);
	if (row==myGrid.length) myFinder.union(VBOTTOM, num);
	myGrid[row][col] = true;
	if (isOpen(a,col) && inBounds(a,col)) myFinder.union(getIndex(a,col), num); 
	if (isOpen(b,col) && inBounds(b,col)) myFinder.union(getIndex(b,col), num);
	if (isOpen(row,c) && inBounds(row,c)) myFinder.union(getIndex(row,c), num);
	if (isOpen(row,d) && inBounds(row,d)) myFinder.union(getIndex(row,d), num);
}

}
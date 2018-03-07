
public class PercolationDFSFast extends PercolationDFS{
	public PercolationDFSFast(int n) {
		super(n);
		
	}
	@Override
	protected void updateOnOpen(int row, int col) {
		int a = row-1;
		int b = row+1;
		int c = col-1;
		int d = col+1;
		if (row==0 || inBounds(a,col) && isFull(a,col) || inBounds(b,col) && isFull(b,col) || 
				inBounds(row,c) && isFull(row,c) || inBounds(row,d) && isFull(row,d)) {
			myGrid[row][col] = FULL;
			dfs(a,col);
			dfs(b,col);
			dfs(row,c);
			dfs(row,d);
			}
	}
	@Override
	public boolean isOpen(int row, int col) {
		if (!inBounds(row,col)) throw new IndexOutOfBoundsException("This is out of bounds!");
		return super.isOpen(row, col);
	}
	@Override
	public boolean isFull(int row, int col) {
		if (!inBounds(row,col)) throw new IndexOutOfBoundsException("This is out of bounds!");
		return super.isFull(row,col);
	}
	@Override
	public void open(int row, int col) {
		if (!inBounds(row,col)) throw new IndexOutOfBoundsException("This is out of bounds!");
		super.open(row, col);
	}

}

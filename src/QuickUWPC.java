public class QuickUWPC implements IUnionFind {
    private int[] parent;
    private int[] size;
    private int count;

    public QuickUWPC() {
		this(10);
}
    public QuickUWPC(int n) {
        initialize(n);
    }
  
    //each node is its own parent and the length of each tree is initially 1
    public void initialize(int n) {
    		count = n;
        parent = new int[n];
        size = new int[n];
        for (int i=0; i<n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int components() {
        return count;
    }
  
    public int find(int p) {
        validate(p);
        int top = p;
        //find the top
        while (top != parent[top]) top = parent[top];
        //get to top by pointing through grandparents
        while (p != top) {
        	int up = parent[p];
        	int gparent = parent[up];
        	parent[p] = gparent;
        	p = gparent;
        }
        return top;    
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));  
        }
    }  

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
}
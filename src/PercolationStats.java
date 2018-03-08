import java.util.*;

/**
 * Compute statistics on Percolation after performing T independent experiments on an N-by-N grid.
 * Compute 95% confidence interval for the percolation threshold, and  mean and std. deviation
 * Compute and print timings
 * 
 * @author Kevin Wayne
 * @author Jeff Forbes
 * @author Josh Hug
 *  
 */
//Brooke Erickson

public class PercolationStats {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);
	private double myOpenNum;
	private double[] myOpen;
	private IUnionFind perc;
	
	private IPercolate getPercolator(int size) {
		IUnionFind perc = new QuickFind();
		perc.initialize(size);
		return new PercolationDFSFast(size);
	}
	
	public PercolationStats(int N, int T){
		if (N<=0 || T<=0) throw new IllegalArgumentException();
		ArrayList<int[]> myList = new ArrayList<int[]>();
		for (int i=0; i<N; i++) {
			for (int j=0; j<N; j++) {
				int[] point = {i,j};
				myList.add(point);}}
		myOpen = new double[T];
		Collections.shuffle(myList,ourRandom);
		for (int i=0; i<T; i++) {
			IPercolate percol = getPercolator(N);
			myOpenNum = 0.;
			int count =0;
			while (!percol.percolates()) {
				percol.open(myList.get(count)[0],myList.get(count)[1]);
				myOpenNum++;
				count++;
				}
				myOpen[i] = myOpenNum/(double)(N*N);}
		}
	
	public double mean() {
		return StdStats.mean(myOpen);
	}
	
	public double stddev() {
		return StdStats.stddev(myOpen);
	   }
	
	public double confidenceLow() {
		return mean() - (1.96*stddev())/Math.sqrt(myOpen.length);
	}
	
	public double confidenceHigh() {
		return mean() + (1.96*stddev())/Math.sqrt(myOpen.length);
	}
	
	public static void main(String[] args) {
//		PercolationStats ps = new PercolationStats(50,100);
//	    System.out.println(ps.mean());
//	    ps = new PercolationStats(200,100);
//	    System.out.println(ps.mean());
		double start =  System.nanoTime();
	    PercolationStats ps = new PercolationStats(200,100);
	    double end =  System.nanoTime();
	    double time =  (end-start)/1e9;
	    System.out.printf("mean: %1.4f, time: %1.4f\n",ps.mean(),time);
	}
}
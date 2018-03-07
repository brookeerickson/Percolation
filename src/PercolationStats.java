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

public class PercolationStats {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);
	public double myOpenNum;
	public double[] myOpen;
	public IUnionFind perc;
//	public double mean;
//	public double stddev;
//	public double confidenceLow;
//	public double confidenceHigh;
	
	private IPercolate getPercolator(int size) {
		IUnionFind perc = new QuickUWPC();
		perc.initialize(size);
		return new PercolationUF(size,perc);
	}
	
	PercolationStats(int N, int T){
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
		return (mean() - 1.96*stddev())/Math.sqrt(myOpen.length);
	}
	
	public double confidenceHigh() {
		return (mean() + 1.96*stddev())/Math.sqrt(myOpen.length);
	}
	
	public static void main(String[] args) {
		PercolationStats ps = new PercolationStats(50,100);
	    System.out.println(ps.mean());
//	    ps = new PercolationStats(200,100);
//	    System.out.println(ps.mean());
	    double start =  System.nanoTime();
	    PercolationStats psi = new PercolationStats(100,100);
	    double end =  System.nanoTime();
	    double time =  (end-start)/1e9;
	    System.out.printf("mean: %1.4f, time: %1.4f\n",psi.mean(),time);

	}
}
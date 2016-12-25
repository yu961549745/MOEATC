package moeatc.impl;

import java.util.List;

import org.moeaframework.core.Population;

public class Utils {
	public static double[][] getPoints(Population p) {
		int m = p.size();
		int n = p.get(0).getNumberOfObjectives();
		double[][] x = new double[m][n];
		for (int i = 0; i < m; i++) {
			double[] v = p.get(i).getObjectives();
			for (int j = 0; j < n; j++) {
				x[i][j] = v[j];
			}
		}
		return x;
	}

	public static double[][] pdist2(double[][] x, double[][] y) {
		int m = x.length;
		int n = y.length;
		double[][] d = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				d[i][j] = dist(x[i], y[j]);
			}
		}
		return d;
	}

	private static double dist(double[] x, double[] y) {
		double d = 0;
		for (int k = 0; k < x.length; k++) {
			d += (x[k] - y[k]) * (x[k] - y[k]);
		}
		return Math.sqrt(d);
	}

	public static void print(double[][] d) {
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[0].length; j++) {
				System.out.print(d[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static double meanMinDistance(double[][] prev, double[][] curr) {
		if (prev.length != curr.length) {
			throw new RuntimeException("Different Population Size");
		}
		int n = prev.length;
		double[][] d = Utils.pdist2(prev, curr);
		HungarianAlgorithm algorithm = new HungarianAlgorithm(d);
		int[] res = algorithm.execute();
		double r = 0;
		for (int k = 0; k < n; k++) {
			r += d[k][res[k]];
		}
		return r / n;
	}

	public static double[] list2Array(List<Double> list) {
		int n = list.size();
		double[] d = new double[n];
		for (int k = 0; k < n; k++) {
			d[k] = list.get(k);
		}
		return d;
	}
}

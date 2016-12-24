package moeatc;

import java.util.ArrayList;
import java.util.List;

import org.moeaframework.core.Algorithm;
import org.moeaframework.core.EvolutionaryAlgorithm;
import org.moeaframework.core.Population;
import org.moeaframework.core.TerminationCondition;

public class EATC implements TerminationCondition {

	private int ns;
	private int np;

	private List<Double> dList = new ArrayList<Double>();
	private List<Integer> meanBuffer = new ArrayList<Integer>();
	private List<Integer> stdBuffer = new ArrayList<Integer>();

	private double mean = 0, mean2 = 0, std = 0;

	private double[][] prev = null;// 上一个 population
	private double[][] curr = null;// 当前的 population

	public EATC(int numberOfSuccessiveGenerations, int numberOfDecimalPlaces) {
		ns = numberOfSuccessiveGenerations;
		np = numberOfDecimalPlaces;
	}

	@Override
	public boolean shouldTerminate(Algorithm algorithm) {
		return shouldTerminate((EvolutionaryAlgorithm) algorithm);
	}

	private int nGenerations = 0;

	private boolean shouldTerminate(EvolutionaryAlgorithm algorithm) {
		Population p = algorithm.getPopulation();
		if (p.size() > 0) {
			nGenerations++;
			if (prev == null) {
				prev = getPoints(p);
			} else {
				curr = getPoints(p);
				double d = dis(prev, curr);
				update(d);
				prev = curr;
			}
		}
		if (meanBuffer.size() == ns && allSame(meanBuffer)
				&& allSame(stdBuffer)) {
			return true;
		}
		return false;
	}

	private void update(double d) {
		int n = dList.size();

		mean = (n * mean + d) / (n + 1);
		mean2 = (n * mean2 + d * d) / (n + 1);
		std = Math.sqrt(mean2 - mean * mean);

		System.out.printf("%d %f %f %f\n", nGenerations, d, mean, std);

		dList.add(d);
		if (meanBuffer.size() >= ns) {
			meanBuffer.remove(0);
		}
		meanBuffer.add(pround(mean));
		if (stdBuffer.size() >= ns) {
			stdBuffer.remove(0);
		}
		stdBuffer.add(pround(std));
	}

	private int pround(double x) {
		double N = Math.pow(10, np);
		return (int) Math.round(x * N);
	}

	private boolean allSame(List<Integer> list) {
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) != list.get(i - 1)) {
				return false;
			}
		}
		return true;
	}

	private double dis(double[][] prev, double[][] curr) {
		if (prev.length != curr.length) {
			throw new RuntimeException("Different Population Size");
		}
		int n = prev.length;
		double[][] d = pdist2(prev, curr);
		HungarianAlgorithm algorithm = new HungarianAlgorithm(d);
		int[] res = algorithm.execute();
		double r = 0;
		for (int k = 0; k < n; k++) {
			r += d[k][res[k]];
		}
		return r / n;
	}

	private double[][] getPoints(Population p) {
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

	private double[][] pdist2(double[][] x, double[][] y) {
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

	private double dist(double[] x, double[] y) {
		double d = 0;
		for (int k = 0; k < x.length; k++) {
			d += (x[k] - y[k]) * (x[k] - y[k]);
		}
		return Math.sqrt(d);
	}

	private void print(double[][] d) {
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[0].length; j++) {
				System.out.print(d[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printPrev() {
		print(prev);
	}

	public void printCurr() {
		print(curr);
	}

	public int getNumberOfGenerations() {
		return nGenerations;
	}

	@Override
	public void initialize(Algorithm algorithm) {
		// do nothing
	}
}

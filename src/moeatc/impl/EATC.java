package moeatc.impl;

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
				prev = Utils.getPoints(p);
			} else {
				curr = Utils.getPoints(p);
				double d = Utils.meanMinDistance(prev, curr);
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

	public int getNumberOfGenerations() {
		return nGenerations;
	}

	@Override
	public void initialize(Algorithm algorithm) {
		// do nothing
	}
}

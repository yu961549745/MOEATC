package moeatc.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.moeaframework.core.Algorithm;
import org.moeaframework.core.EvolutionaryAlgorithm;
import org.moeaframework.core.Population;
import org.moeaframework.core.TerminationCondition;

public class MoeaTC implements TerminationCondition {

	private int nCalSize;// 计算均值和标准差的长度
	private int nCheckSize;// 检查均值和标准差相同的长度
	private int nPrecision;// 精度位数

	private double nScale;

	private List<Double> dList = new ArrayList<Double>();// 距离
	private List<Double> mList = new ArrayList<Double>();// 均值
	private List<Double> sList = new ArrayList<Double>();// 标准差

	private ArrayList<Integer> mBuffer = new ArrayList<Integer>();// 近似均值
	private ArrayList<Integer> sBuffer = new ArrayList<Integer>();// 近似标准差

	private int nGenerations = 0; // 代数
	private List<Integer> recordGens = new ArrayList<Integer>();
	private ArrayList<double[][]> pfs = new ArrayList<double[][]>();

	public MoeaTC(int nCalSize, int nCheckSize, int nPrecision, int[] recordGens) {
		this.nCalSize = nCalSize;
		this.nCheckSize = nCheckSize;
		this.nPrecision = nPrecision;
		if (recordGens != null) {
			for (int k = 0; k < recordGens.length; k++) {
				this.recordGens.add(recordGens[k]);
			}
			Collections.sort(this.recordGens);
		}

		nScale = Math.pow(10, this.nPrecision);
	}

	@Override
	public void initialize(Algorithm algorithm) {
	}

	private double[][] prev = null;
	private double[][] curr = null;

	@Override
	public boolean shouldTerminate(Algorithm algorithm) {
		EvolutionaryAlgorithm algo = (EvolutionaryAlgorithm) algorithm;
		Population p = algo.getPopulation();
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
		if (recordGens.contains(nGenerations)) {
			pfs.add(Utils.getPoints(algo.getPopulation()));
		}
		if (mBuffer.size() == nCheckSize && allSame(mBuffer)
				&& allSame(sBuffer)) {
			return true;
		}
		return false;
	}

	private double sum = 0, sum2 = 0;

	private void update(double d) {
		dList.add(d);
		int n = dList.size();
		if (n > nCalSize) {
			double rd = dList.get(n - 1 - nCalSize);
			sum -= rd;
			sum2 -= rd * rd;
			n = nCalSize;
		}
		sum += d;
		sum2 += d * d;

		double mean = sum / n;
		double mean2 = sum2 / n;
		double std = Math.sqrt(mean2 - mean * mean);
		mList.add(mean);
		sList.add(std);

		if (mBuffer.size() >= nCheckSize) {
			mBuffer.remove(0);
		}
		mBuffer.add(pround(mean));
		if (sBuffer.size() >= nCheckSize) {
			sBuffer.remove(0);
		}
		sBuffer.add(pround(std));
	}

	private int pround(double x) {
		return (int) Math.round(x * nScale);
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

	public double[] getDistances() {
		return Utils.list2Array(dList);
	}

	public double[] getMeans() {
		return Utils.list2Array(mList);
	}

	public double[] getStds() {
		return Utils.list2Array(sList);
	}

	public ArrayList<double[][]> getPfs() {
		return pfs;
	}
}

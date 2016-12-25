package moeatc.impl;

import java.util.ArrayList;
import java.util.List;

import moeatc.IMoeaTC;

import org.moeaframework.core.Algorithm;
import org.moeaframework.core.EvolutionaryAlgorithm;
import org.moeaframework.core.Population;

public class EATC2 implements IMoeaTC {

	private static final long serialVersionUID = -4710688773015766885L;
	private int nCalSize;// 计算均值和标准差的长度
	private int nCheckSize;// 检查均值和标准差相同的长度
	private int nPrecision;// 精度位数

	private List<Double> dList = new ArrayList<Double>();// 距离
	private List<Double> mList = new ArrayList<Double>();// 均值
	private List<Double> sList = new ArrayList<Double>();// 标准差

	private List<Integer> mBuffer = new ArrayList<Integer>();// 近似均值
	private List<Integer> sBuffer = new ArrayList<Integer>();// 近似标准差

	private int nGenerations = 0; // 代数

	public EATC2(int nCalSize, int nCheckSize, int nPrecision) {
		this.nCalSize = nCalSize;
		this.nCheckSize = nCheckSize;
		this.nPrecision = nPrecision;
	}

	@Override
	public void initialize(Algorithm algorithm) {
	}

	private double[][] prev = null;
	private double[][] curr = null;
	private double[][] pf = null;

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
		if (mBuffer.size() == nCheckSize && allSame(mBuffer)
				&& allSame(sBuffer)) {
			pf = Utils.getPoints(algo.getResult());
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
		double N = Math.pow(10, nPrecision);
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

	@Override
	public int getNumberOfGenerations() {
		return nGenerations;
	}

	@Override
	public double[] getDistances() {
		return Utils.list2Array(dList);
	}

	@Override
	public double[] getMeans() {
		return Utils.list2Array(mList);
	}

	@Override
	public double[] getStds() {
		return Utils.list2Array(sList);
	}

	@Override
	public double[][] getParetoFront() {
		return pf;
	}
}

package moeatc.rmi;

import java.io.Serializable;

public class MoeaResult implements Serializable {
	private static final long serialVersionUID = -4452238774245766940L;
	private double[] distances;
	private double[] means;
	private double[] stds;
	private double[][] paretoFront;
	private int numberOfGenerations;

	public double[] getDistances() {
		return distances;
	}

	public void setDistances(double[] distances) {
		this.distances = distances;
	}

	public double[] getMeans() {
		return means;
	}

	public void setMeans(double[] means) {
		this.means = means;
	}

	public double[] getStds() {
		return stds;
	}

	public void setStds(double[] stds) {
		this.stds = stds;
	}

	public double[][] getParetoFront() {
		return paretoFront;
	}

	public void setParetoFront(double[][] paretoFront) {
		this.paretoFront = paretoFront;
	}

	public int getNumberOfGenerations() {
		return numberOfGenerations;
	}

	public void setNumberOfGenerations(int numberOfGenerations) {
		this.numberOfGenerations = numberOfGenerations;
	}

}

package moeatc;

import java.io.Serializable;

import org.moeaframework.core.TerminationCondition;

public interface IMoeaTC extends TerminationCondition, Serializable {
	public double[][] getParetoFront();

	public double[] getDistances();

	public double[] getMeans();

	public double[] getStds();

	public int getNumberOfGenerations();
}

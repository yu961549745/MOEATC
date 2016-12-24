package moeatc;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

public class Test {
	public static void main(String[] args) {
		Executor executor = new Executor().withAlgorithm("NSGA-II")
				.withProblemClass(TestProblem.class)
				.withTerminationCondition(new EATC(5, 2))
				.withProperty("populationSize", 100);
		NondominatedPopulation reslut = executor.run();
		for (Solution s : reslut) {
			double[] v = s.getObjectives();
			for (int k = 0; k < v.length; k++) {
				System.out.print(v[k] + " ");
			}
			System.out.println();
		}
	}
}

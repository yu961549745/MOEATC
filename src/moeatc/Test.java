package moeatc;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;

public class Test {
	public static void main(String[] args) {
		Utils.print(getPF());
	}

	public static double[][] getPF() {
		Executor executor = new Executor().withAlgorithm("NSGA-II")
				.withProblemClass(TestProblem.class)
				.withTerminationCondition(new EATC(5, 2))
				.withProperty("populationSize", 50);
		NondominatedPopulation reslut = executor.run();
		return Utils.getPoints(reslut);
	}
}

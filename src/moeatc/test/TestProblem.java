package moeatc.test;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class TestProblem extends AbstractProblem {

	public TestProblem() {
		super(1, 2);
	}

	@Override
	public void evaluate(Solution s) {
		double x = EncodingUtils.getReal(s.getVariable(0));
		s.setObjective(0, x * x);
		s.setObjective(1, (x - 2) * (x - 2));
	}

	@Override
	public Solution newSolution() {
		Solution s = new Solution(1, 2);
		s.setVariable(0, EncodingUtils.newReal(-10, 10));
		return s;
	}

}

package moeatc.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import moeatc.impl.MoeaTC;
import moeatc.impl.Utils;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;

public class MoeaRmiServer extends UnicastRemoteObject implements
		IMoeaRmiServer {
	private static final long serialVersionUID = -6757203045386050823L;

	public MoeaRmiServer() throws RemoteException {
		super();
	}

	@Override
	public MoeaResult runMOEA(String algorithmName, String problemName,
			int populationSize, int maxGenerations, int nCalSize,
			int nCheckSize, int nPrecision) throws RemoteException {
		MoeaTC tc = new MoeaTC(nCalSize, nCheckSize, nPrecision);
		NondominatedPopulation res = new Executor()
				.withAlgorithm(algorithmName).withProblem(problemName)
				.withTerminationCondition(tc)
				.withMaxEvaluations(populationSize * maxGenerations)
				.withProperty("populationSize", populationSize).run();
		MoeaResult result = new MoeaResult();
		result.setDistances(tc.getDistances());
		result.setMeans(tc.getMeans());
		result.setStds(tc.getStds());
		result.setNumberOfGenerations(tc.getNumberOfGenerations());
		result.setParetoFront(Utils.getPoints(res));
		result.setmBuffer(tc.getmBuffer());
		result.setsBuffer(tc.getsBuffer());
		return result;
	}

}

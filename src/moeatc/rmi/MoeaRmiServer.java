package moeatc.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import moeatc.IMoeaTC;
import moeatc.impl.MoeaTC;

import org.moeaframework.Executor;

public class MoeaRmiServer extends UnicastRemoteObject implements
		IMoeaRmiServer {
	private static final long serialVersionUID = -6757203045386050823L;

	public MoeaRmiServer() throws RemoteException {
		super();
	}

	@Override
	public IMoeaTC runMOEA(String algorithmName, String problemName,
			int populationSize, int maxGenerations, int nCalSize,
			int nCheckSize, int nPrecision) throws RemoteException {
		IMoeaTC tc = new MoeaTC(nCalSize, nCheckSize, nPrecision);
		new Executor().withAlgorithm(algorithmName).withProblem(problemName)
				.withTerminationCondition(tc)
				.withMaxEvaluations(populationSize * maxGenerations)
				.withProperty("populationSize", populationSize).run();
		return tc;
	}

}

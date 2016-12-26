package moeatc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMoeaRmiServer extends Remote {
	public MoeaResult runMOEA(String algorithmName, String problemName,
			int populationSize, int maxGenerations, int nCalSize,
			int nCheckSize, int nPrecision, int[] recordGens)
			throws RemoteException;
}

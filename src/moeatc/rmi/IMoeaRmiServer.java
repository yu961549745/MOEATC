package moeatc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import moeatc.IMoeaTC;

public interface IMoeaRmiServer extends Remote {
	public IMoeaTC runMOEA(String algorithmName, String problemName,
			int populationSize, int maxGenerations, int nCalSize,
			int nCheckSize, int nPrecision) throws RemoteException;
}

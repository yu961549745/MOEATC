package moeatc;

import java.io.File;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import moeatc.rmi.Constants;
import moeatc.rmi.MoeaRmiServer;

public class RunMoeaRmiServer {
	public static void main(String[] args) throws Exception {
		MoeaRmiServer server = new MoeaRmiServer();
		LocateRegistry.createRegistry(Constants.RMI_PORT);
		Naming.rebind(Constants.RMI_NAME, server);
		System.out.println("RMI Server Loaded");
		File notifyFile = new File(Constants.RMI_NOTIFY_FILE);
		notifyFile.createNewFile();
	}
}

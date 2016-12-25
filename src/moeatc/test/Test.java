package moeatc.test;

import moeatc.IMoeaTC;
import moeatc.impl.Utils;
import moeatc.rmi.IMoeaRmiServer;
import moeatc.rmi.MoeaRmiFactory;

public class Test {
	public static void main(String[] args) throws Exception {
		IMoeaRmiServer server = MoeaRmiFactory.getServer();
		IMoeaTC tc = server.runMOEA("NSGA-II", "Lis", 50, 2000, 200, 5, 2);
		Utils.print(tc.getParetoFront());
	}
}

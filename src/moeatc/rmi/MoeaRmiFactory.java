package moeatc.rmi;

import java.rmi.Naming;

public class MoeaRmiFactory {
	public static IMoeaRmiServer getServer() throws Exception {
		return (IMoeaRmiServer) Naming.lookup(Constants.RMI_NAME);
	}
}

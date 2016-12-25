package moeatc.rmi;

public class Constants {
	public static final int RMI_PORT = 10086;
	public static final String RMI_NAME = "rmi://localhost:" + RMI_PORT
			+ "/MOEA";
	public static final String RMI_NOTIFY_FILE = System
			.getProperty("java.io.tmpdir") + "MoeaRmiNotify";
}

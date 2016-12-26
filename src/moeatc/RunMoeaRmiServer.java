package moeatc;

import java.io.File;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import moeatc.rmi.Constants;
import moeatc.rmi.MoeaRmiServer;
import moeatc.ui.MoeaUI;

public class RunMoeaRmiServer {
	public static void main(String[] args) throws Exception {
		MoeaRmiServer server = new MoeaRmiServer();
		LocateRegistry.createRegistry(Constants.RMI_PORT);
		Naming.rebind(Constants.RMI_NAME, server);

		// 直接运行会产生一个托盘图标，传入参数则不显示托盘图标
		if (args.length == 0) {
			new MoeaUI();
		}

		// 生成标记文件表示服务加载完成
		File notifyFile = new File(Constants.RMI_NOTIFY_FILE);
		notifyFile.createNewFile();
	}
}

package moeatc;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import moeatc.rmi.Constants;
import moeatc.rmi.MoeaRmiServer;
import moeatc.ui.IconProvider;

public class RunMoeaRmiServer {
	public static void main(String[] args) throws Exception {
		// Start RMI Server
		MoeaRmiServer server = new MoeaRmiServer();
		LocateRegistry.createRegistry(Constants.RMI_PORT);
		Naming.rebind(Constants.RMI_NAME, server);
		System.out.println("RMI Server Loaded");

		// Add Tray Icon
		TrayIcon trayIcon = new TrayIcon(IconProvider.getIcon(), "MOEA Server");
		PopupMenu menu = new PopupMenu();
		MenuItem close = new MenuItem("close");
		menu.add(close);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		trayIcon.setPopupMenu(menu);
		trayIcon.setImageAutoSize(true);
		SystemTray.getSystemTray().add(trayIcon);
	}
}

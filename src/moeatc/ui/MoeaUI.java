package moeatc.ui;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class MoeaUI {
	public MoeaUI() throws Exception {
		// Add Tray Icon
		BufferedImage icon = ImageIO.read(MoeaUI.class.getResource("logo.png"));
		TrayIcon trayIcon = new TrayIcon(icon, "MOEA Server");
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

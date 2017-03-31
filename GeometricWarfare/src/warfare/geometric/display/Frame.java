package warfare.geometric.display;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gwl.entity.Player;
import gwl.entity.World;
import gwl.math.Vector2;
import gwl.net.Command;
import warfare.geometric.event.Listener;
import warfare.geometric.net.ConnectionThread;
import warfare.geometric.net.InfoFlushThread;
import warfare.geometric.thread.FrameRate;
import warfare.geometric.thread.Updater;


public class Frame extends JFrame {

	public Panel p;
	public Listener listener = new Listener();
	private FrameRate frameRate;
	public Updater update;
	public ConnectionThread cth;
	public World world;
	public Player player;
	
	public Frame(String username) {
		world = new World();
		player = new Player(new Vector2(100.0f, 100.0f), Player.DUAL_MACHINE, username, 10, 100.0f, 10, 200, 1, 5, 5.0f, 2.0f, 3.0f, 0.0f, 100.0f);
		try {
		cth = new ConnectionThread(new Socket("localhost", 25700));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage()+"\nLe serveur n'est peut-être pas démarré ou vérifiez votre connexion internet");
			System.exit(1);
		}
		update = new Updater();
		new Thread(new InfoFlushThread()).start();
		cth.start();
		p = new Panel();

		this.setSize(1280, 720);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.addWindowListener(listener);
		this.addKeyListener(listener);
		this.addMouseListener(listener);
		this.setContentPane(p);
		this.setCursor((Cursor) Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(32, 32, BufferedImage.TRANSLUCENT), new Point(0,0), "curseurInvisble"));
		update.start();
		frameRate = new FrameRate(p);
		frameRate.start();
		cth.sendRequest(new Command(Command.GET_PLAYER_INDEX, player));
	}
	
	public void stopFrameRate() {
		frameRate.stop();
	}

}

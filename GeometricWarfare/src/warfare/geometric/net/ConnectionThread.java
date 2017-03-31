package warfare.geometric.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import gwl.entity.Bullet;
import gwl.entity.Food;
import gwl.entity.FoodStat;
import gwl.entity.Player;
import gwl.net.Command;
import warfare.geometric.main.Main;


public class ConnectionThread implements Runnable{

	private Socket s;
	private boolean running = false;
	private boolean closed = true;
	private Thread th;
	private ObjectInputStream ois;
	private ObjectOutputStream ous;
	
	public ConnectionThread(Socket s) {
		this.s = s;
		try {
			ois = new ObjectInputStream(s.getInputStream());
			ous = new ObjectOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		th = new Thread(this);
	}

	public void sendRequest(Command c) {
		if(!closed) {
		try {
			synchronized(ous){
			ous.writeObject(c);
			ous.reset();
			ous.notify();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while(running) {
			
			try {
				if(running){
				Command c = null;
				synchronized(ois) {
				c = (Command)ois.readObject();
				ois.notify();
				if(c.getType() == Command.DIE) {
					List<Long> l = Collections.synchronizedList((List<Long>) c.getValue());
					Main.display.world.removePlayers(l);
				}else if(c.getType() == Command.DIED_FOOD) {
					List<Long> l = Collections.synchronizedList((List<Long>) c.getValue());
					Main.display.world.removeFoods(l);
				}else if(c.getType() == Command.GET_PLAYER_INDEX) {
					
					Player p = Main.display.player.clone();
					
					p.setId((long) c.getValue());
					
					Main.display.player.reset(p);
					
						sendRequest(new Command(Command.PLAYERS, Main.display.player.getId()));
					
					
				}else if(c.getType() == Command.NEW_BULLET) {
					List<Bullet> b = Collections.synchronizedList((List<Bullet>) c.getValue());
					Main.display.world.addBullet(b);
				}else if(c.getType() == Command.NEW_FOOD) {
					List<Food> f = Collections.synchronizedList((List<Food>) c.getValue());
					Main.display.world.addFood(f);
				}else if(c.getType() == Command.NEW_PLAYER) {
					List<Player> pl = Collections.synchronizedList((List<Player>) c.getValue());
					Main.display.world.addPlayer(pl);
				}else if(c.getType() == Command.PLAYER_STAT) {
					List<Player> l = Collections.synchronizedList((List<Player>)c.getValue());
					for(Player p : l){
						if(Main.display.world.getPlayerFromId(p.getId()) != null && p != null)Main.display.world.getPlayerFromId(p.getId()).reset(p);	
					}
				}else if(c.getType() == Command.WORLD) {
					synchronized(Main.display.world.foods) {
					Main.display.world.foods = Collections.synchronizedList((List<Food>) c.getValue());
					}
				}else if(c.getType() == Command.DIED_BULLET) {
					
					List<Long> l = Collections.synchronizedList((List<Long>) c.getValue());
					Main.display.world.removeBullets(l);
				}else if(c.getType() == Command.DISCONNECT) {
					stop();
					break;
				}else if(c.getType() == Command.PLAYERS) {
					synchronized(Main.display.world.players) {
					Main.display.world.players = Collections.synchronizedList((List<Player>) c.getValue());
					if(Main.display.world.players.size() != 0)System.out.println(Main.display.world.players.get(0).getName());
					}
					sendRequest(new Command(Command.WORLD, null));
				}else if(c.getType() == Command.FOOD_STAT) {
					synchronized(Main.display.world.statFoods) {
						Main.display.world.statFoods.addAll((Collection<? extends FoodStat>) c.getValue());
					}
					
				}else if(c.getType() == Command.BULLET_LIFE) {
					Main.display.world.addBulletLifeChange((Bullet) c.getValue());
				}
				}
				}else break;

			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
				
		}
		System.exit(0);
		try {
			ous.close();
			ois.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void start() {
		closed = false;
		running = true;
		th.start();
	}
	
	public void stop() {
		running = false;
	}

}

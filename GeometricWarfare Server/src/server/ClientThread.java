package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import gwl.entity.Bullet;
import gwl.entity.FoodStat;
import gwl.entity.Player;
import gwl.net.Command;


public class ClientThread implements Runnable{

	private Thread th;
	private Socket s;
	private boolean running = false;
	private boolean closed = true;
	private ObjectInputStream ois;
	private ObjectOutputStream ous;
	public long playerID = -1;
	
	public ClientThread(Socket s) {
		this.s = s;
		
		System.out.print("New attempt to connect, initializing streams ...");
		try {
			ous = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		System.out.println(" done");
		th = new Thread(this);
	}

	@Override
	public void run() {
		while(running) {
			
			Command c = null;
			
			
			
			try {
				synchronized(ois){
					c = (Command) ois.readObject();
					ois.notify();
					
					if(c.getType() == Command.DIE) {
						Main.i.world.playersRemoval.add(playerID);
						
						
					}else if(c.getType() == Command.DIED_FOOD) {
						List<Long> tr = (List<Long>) c.getValue();
						Main.i.world.removeFoods(tr);
						tr = null;
					}else if(c.getType() == Command.GET_PLAYER_INDEX) {
						
						Player p = (Player)c.getValue();
						if(playerID == -1){
						p = Main.i.world.addNewPlayer(p);
						playerID = p.getId();
						sendRequest(new Command(Command.GET_PLAYER_INDEX, p.getId()));
						System.out.println("Player ID "+p.getId()+", name "+p.getName()+" connected");
						}else {
							Main.i.world.removePlayer(playerID);
							p = Main.i.world.addNewPlayer(p);
							playerID = p.getId();
							sendRequest(new Command(Command.GET_PLAYER_INDEX, p.getId()));
							System.out.println("Player ID "+p.getId()+", name "+p.getName()+" connected");
						}
					}else if(c.getType() == Command.NEW_BULLET) {
						List<Bullet> b = Collections.synchronizedList((List<Bullet>) c.getValue());
						Main.i.world.addBullet(b);
					}else if(c.getType() == Command.PLAYER_STAT) {
						Player p = (Player)c.getValue();
						if(Main.i.world.players.size() != 0){
							synchronized(Main.i.world.statPlayers){
							Main.i.world.statPlayers.add(p);
							Main.i.world.statPlayers.notify();
							}
						}
					}else if(c.getType() == Command.WORLD) {
						synchronized(Main.i.world.foods){
						sendRequest(new Command(Command.WORLD, Main.i.world.foods));
						Main.i.world.foods.notify();
						}
					}else if(c.getType() == Command.DISCONNECT) {
						closed = true;
						stop();
						break;
					}else if(c.getType() == Command.PLAYERS) {
						
						List<Player> pl = Collections.synchronizedList(new ArrayList<Player>());
						
						synchronized(Main.i.world.players){
						for(Player player : Main.i.world.players) {
							if(player.getId() != (long)c.getValue())pl.add(player);
						}
						Main.i.world.players.notify();
						}
						sendRequest(new Command(Command.PLAYERS, pl));
						
					}else
					if(c.getType() == Command.FOOD_STAT) {
						List<FoodStat> foods = Collections.synchronizedList((List<FoodStat>)c.getValue());
						synchronized(Main.i.world.statFoods) {
						Main.i.world.statFoods.addAll(foods);
						Main.i.world.statFoods.notify();
						}
					}else if(c.getType() == Command.DIED_BULLET) {
						List<Long> l = new ArrayList<Long>();
						l.addAll((Collection<? extends Long>) c.getValue());
						
						Main.i.world.removeBullets(l);
					}
				}
				
				
				if(!running)break;
			} catch (Exception e) {
				System.out.print("Error : ");
				e.printStackTrace();
				break;
			} 
			
			
		}
		System.out.println("Player ID "+playerID+", name "+Main.i.world.getPlayerFromId(playerID).getName()+" disconnected");
		Main.i.world.removePlayer(playerID);
		try {
			ois.close();
			ous.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Main.removeTh.add(this);
		Thread.currentThread().stop();
		
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
			stop();
		}
		}
	}
	
	public void start() {
		closed = false;
		running = true;
		th.start();
	}
	
	public void stop() {
		closed = true;
		running = false;
	}

}

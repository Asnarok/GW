package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import gwl.entity.Bullet;
import gwl.entity.FoodStat;
import gwl.entity.Player;
import gwl.net.Command;


public class Main {

	public static ServerSocket srv;
	public static List<ClientThread> th = new ArrayList<ClientThread>();
	public static List<ClientThread> removeTh = new ArrayList<ClientThread>();
	public static Instance i;
	
	public static void main(String[] args) {
		SettingsLoader.init();
		i = new Instance(10000, 10000);
		try {
			srv = new ServerSocket(SettingsLoader.serverPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Thread(){
			
			public void run() {
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
				
				while(true) {
					if(sc.nextLine().equals("stop")){
						for(ClientThread ct : th) {
							ct.sendRequest(new Command(Command.DISCONNECT, null));
							ct.stop();
						}
						System.exit(0);
					}
				}
			}
			
		}.start();
		
		new Thread() {
			
			public void run() {
				while(true) {

					
					for(ClientThread ct : removeTh) {
						th.remove(ct);
					}
					
					synchronized(i.world.getStatPlayers()) {
						if(i.world.statPlayers.size() != 0){
							for(ClientThread ct : th){
								
								List<Player> l = Collections.synchronizedList(new ArrayList<Player>());
								for(Player p : i.world.statPlayers) {
									if(p.getId() != ct.playerID)l.add(p);
								}
								
								ct.sendRequest(new Command(Command.PLAYER_STAT, l));
							}
							i.world.statPlayers.clear();
						}
					}
					
					synchronized(i.world.getStatFoods()) {
						if(i.world.statFoods.size() != 0){ 
						for(ClientThread ct : th) {
							List<FoodStat> fs = Collections.synchronizedList(new ArrayList<FoodStat>());
							for(FoodStat f : i.world.statFoods) {
								if(f.getActorID() != ct.playerID) {
									fs.add(f);
								}
							}
							ct.sendRequest(new Command(Command.FOOD_STAT, fs));
						}
						}
					}
					
					synchronized(i.world.playersRemoval){
					if(i.world.playersRemoval.size() != 0) {
						for(ClientThread ct : th) {
							
							List<Long> tosend = Collections.synchronizedList(new ArrayList<Long>());
							
							for(Long l : i.world.playersRemoval) {
								if(l != ct.playerID) {
									tosend.add(l);
								}
							}
							ct.sendRequest(new Command(Command.DIE, tosend));
							
						}
					}
					}
					synchronized(i.world.foodsRemoval){
					if(i.world.foodsRemoval.size() != 0) {
						for(ClientThread ct : th) {
							
							ct.sendRequest(new Command(Command.DIED_FOOD, i.world.foodsRemoval));
							
						}
					}
					}
					
					synchronized(i.world.statBullets) {
						if(i.world.statBullets.size() != 0) 
						{
							for(ClientThread ct : th) {
								List<Bullet> tosend = Collections.synchronizedList(new ArrayList<Bullet>());
								
								for(Bullet b : i.world.statBullets) {
									if(b.getOwner().getId() != ct.playerID) {
										tosend.add(b);
									}
								}
								ct.sendRequest(new Command(Command.BULLET_LIFE, tosend));
							}
						}
					}

					synchronized(i.world.bulletsRemoval) {
					if(i.world.bulletsRemoval.size() != 0) {
						for(ClientThread ct : th) {
							
							List<Long> tosend = Collections.synchronizedList(new ArrayList<Long>());
							
							for(Bullet b : i.world.bulletsRemoval) {
									tosend.add(b.getId());
							}
							
							ct.sendRequest(new Command(Command.DIED_BULLET, tosend));
							
						}
					}
					
					}
					i.world.update();
					
					synchronized(i.world.newFoods) {
					if(i.world.newFoods.size() != 0){
						for(ClientThread ct : th) {
							ct.sendRequest(new Command(Command.NEW_FOOD, i.world.newFoods));
						}
						i.world.newFoods.clear();
					}
					}
					synchronized(i.world.newBullets) {
					if(i.world.newBullets.size() != 0){
						for(ClientThread ct : th) {
							
							List<Bullet> bl = Collections.synchronizedList(new ArrayList<Bullet>());
							for(Bullet b : i.world.newBullets){
								if(b.getOwner().getId() != ct.playerID && b.getOwner().isKnownID()){
									bl.add(b);
								}
								
							}
							
							ct.sendRequest(new Command(Command.NEW_BULLET, bl));
							
						}
						i.world.newBullets.clear();
					}
					}
					synchronized(i.world.newPlayers) {
					if(i.world.newPlayers.size() != 0){
						for(ClientThread ct : th) {
							
							List<Player> pl = Collections.synchronizedList(new ArrayList<Player>());
							
							for(Player p : i.world.newPlayers) {
								if(p.getId() != ct.playerID)pl.add(p);
							}
							
							ct.sendRequest(new Command(Command.NEW_PLAYER, pl));
						}
						i.world.newPlayers.clear();
					}
					}
					
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		}.start();
		
		new Thread() {
		
			public void run() {
			
		while(true) {
			
			System.out.println("waiting for ...");
			try {
				if(th.size() < SettingsLoader.maxPlayers){
				Socket s = srv.accept();
				th.add(new ClientThread(s));
				th.get(th.size()-1).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		}
		
	
	}.start();;

}
}

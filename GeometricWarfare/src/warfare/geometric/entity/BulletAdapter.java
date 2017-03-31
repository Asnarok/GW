package warfare.geometric.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gwl.entity.Bullet;
import gwl.entity.BulletDrone;
import gwl.entity.Food;
import gwl.entity.FoodStat;
import gwl.entity.Player;
import gwl.net.Command;
import warfare.geometric.event.Listener;
import warfare.geometric.main.Main;
import warfare.geometric.resource.ResourceLoader;


public class BulletAdapter {
	
	public static List<Long> diedFood = Collections.synchronizedList(new ArrayList<Long>());
	public static List<Bullet> bulletStat = Collections.synchronizedList(new ArrayList<Bullet>());
	public static List<Food> foodStat = Collections.synchronizedList(new ArrayList<Food>());
	public static List<Player> playerStat = Collections.synchronizedList(new ArrayList<Player>());

	public static void update(Bullet b) {
		if(b.getTicks() < b.getDuration()){
			b.update();
			for(Food f : Main.display.world.getFoodList()) {
				if(f.getHitBox().intersects(b.getHitBox())){
					
					f.setHealth(f.getHealth()-b.getDamage());
					if(f.getHealth() <= 0.0){
						Main.display.world.removeFood(f.getId());
						if(b.getOwner().getId() == Main.display.player.getId()){
						synchronized(diedFood){
							diedFood.add(f.getId());
							diedFood.notify();
						}
						}
					}else {
						if(b.getOwner().getId() == Main.display.player.getId()){
						synchronized(foodStat) {
							foodStat.add(f);
							
							synchronized(Main.display.world.statFoods){
								Main.display.world.statFoods.add(new FoodStat(f, b.getOwner().getId()));
							}
							
							}
						
						}
					}
					if(b.getLife() > 0)b.setLife(b.getLife()-1);
					if(b.getLife() <= 0) {
						Main.display.world.removeBullet(b.getId());
						synchronized(PlayerAdapter.diedBullets) {
						PlayerAdapter.diedBullets.add(b.getId());
						PlayerAdapter.diedBullets.notify();
						}
					}
						
				}
			}
			
			if(b.getHitBox().intersects(Main.display.player.getHitBox()) && b.getOwner().getId() != Main.display.player.getId()) {
				
				if(b.getLife() > 0){
					b.setLife(b.getLife()-1);
					Main.display.player.setHealth(Main.display.player.getHealth()-b.getDamage());
					if(Main.display.player.getHealth() <= 0.0){
						Main.display.cth.sendRequest(new Command(Command.DIE, null));
					}
				}
				if(b.getLife() <= 0) {
					if(!Main.display.world.bulletsRemoval.contains(Main.display.world.getBulletFromId(b.getId()))){
						Main.display.world.removeBullet(b.getId());
					}
					
					synchronized(PlayerAdapter.diedBullets) {
					PlayerAdapter.diedBullets.add(b.getId());
					PlayerAdapter.diedBullets.notify();
					}
					return;
				}
				if(b.getLife() > 0) {
					synchronized(bulletStat) {
						bulletStat.add(b);	
					}
					
				}
				
			}
			b.setX(b.getX()+b.getDirection().getX()*b.getVelocity());
			b.setY(b.getY()+b.getDirection().getY()*b.getVelocity());
		
			b.setTicks(b.getTicks() + 1);
		}else if(b.getTicks() >= b.getDuration()){
			Main.display.world.removeBullet(b.getId());
			synchronized(PlayerAdapter.diedBullets) {
			PlayerAdapter.diedBullets.add(b.getId());
			PlayerAdapter.diedBullets.notify();
			}
		}
	}
	
	public static void update(BulletDrone b) {
		b.update(Main.display.world);
		
		if(b.isTargetLocked()) {
			if(System.currentTimeMillis()-b.getMillis() >= 1000/b.getSPS()) {
				
				
				
			}
			
			
		}
		
	}
	
	public static void flushInfo() {

		if(foodStat.size() != 0) {
			
			
			List<FoodStat> fs = Collections.synchronizedList(new ArrayList<FoodStat>());
			synchronized(foodStat){
			for(Food f : foodStat) {
				if(!diedFood.contains(f.getId()))fs.add(new FoodStat(f, Main.display.player.getId()));
			}
			}
			Main.display.cth.sendRequest(new Command(Command.FOOD_STAT, fs));
			synchronized(foodStat) {
				foodStat.clear();
			}
		}
		
		if(bulletStat.size() != 0) {
			Main.display.cth.sendRequest(new Command(Command.BULLET_LIFE, bulletStat));
			synchronized(bulletStat) {
				bulletStat.clear();
			}
		}
		
		if(diedFood.size() != 0) {
			Main.display.cth.sendRequest(new Command(Command.DIED_FOOD, diedFood));
			synchronized(diedFood) {
				diedFood.clear();
			}
		}
	}
	
	public static void render(Graphics2D g, Bullet b) {
		
		if(b.getX()-Main.display.player.getPos().getX() > -32 && b.getX()-Main.display.player.getPos().getX() < Main.display.p.width && b.getY()-Main.display.player.getPos().getY() > -32 && b.getY()-Main.display.player.getPos().getY() < Main.display.p.height){
			if(b.getOwner().getName().equals(Main.display.player.getName()))g.drawImage(ResourceLoader.bullet, (int)(b.getX()-Main.display.player.getPos().getX()), (int)(b.getY()-Main.display.player.getPos().getY()), 24, 24, null);
			else g.drawImage(ResourceLoader.bullet, (int)(b.getX()-Main.display.player.getPos().getX()+100), (int)(b.getY()-Main.display.player.getPos().getY()+100), 24, 24, null);
		}
		
	}
}

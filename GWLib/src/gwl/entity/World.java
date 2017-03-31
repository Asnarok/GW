package gwl.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gwl.math.Vector2;


public class World implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2981896032772874159L;
	public int mapWidth = 13000, mapHeight = 10000;
	public List<Player> players = Collections.synchronizedList(new ArrayList<Player>());
	public List<Player> tempPlayers = Collections.synchronizedList(new ArrayList<Player>());
	public List<Player> newPlayers = Collections.synchronizedList(new ArrayList<Player>());
	public List<Player> statPlayers = Collections.synchronizedList(new ArrayList<Player>());
	public List<Long> playersRemoval = Collections.synchronizedList(new ArrayList<Long>());
	
	public List<Bullet> bulletsRemoval = Collections.synchronizedList(new ArrayList<Bullet>());
	public List<Bullet> newBullets = Collections.synchronizedList(new ArrayList<Bullet>());
	public List<Bullet> statBullets = Collections.synchronizedList(new ArrayList<Bullet>());
	public List<Bullet> bullets = Collections.synchronizedList(new ArrayList<Bullet>());
	public List<Bullet> tempBullets = Collections.synchronizedList(new ArrayList<Bullet>());
	
	public List<BulletDrone> tempBulletDrones = Collections.synchronizedList(new ArrayList<BulletDrone>());
	public List<BulletDrone> newBulletDrones = Collections.synchronizedList(new ArrayList<BulletDrone>());
	public List<BulletDrone> statBulletDrones = Collections.synchronizedList(new ArrayList<BulletDrone>());
	public List<BulletDrone> bulletDrones = Collections.synchronizedList(new ArrayList<BulletDrone>());
	
	public List<BulletDrone> getTempBulletDrones() {
		synchronized(bulletDrones){
		return tempBulletDrones;
		}
	}


	public List<BulletDrone> getNewBulletDrones() {
		synchronized(bulletDrones){
		return newBulletDrones;
		}
	}


	public List<BulletDrone> getStatBulletDrones() {
		synchronized(bulletDrones){
		return statBulletDrones;
	}
	}

	public List<BulletDrone> getBulletDrones() {
		synchronized(bulletDrones){
		return bulletDrones;
		}
	}
	
	public void addBulletDrone(BulletDrone b) {
		synchronized(tempBulletDrones) {
			tempBulletDrones.add(b);
		}
	}
	

	public void removeBulletDrone(BulletDrone b) {
		synchronized(tempBulletDrones) {
			tempBulletDrones.add(b);
		}
	}

	public List<Food> foods = Collections.synchronizedList(new ArrayList<Food>());
	public List<Food> tempFood = Collections.synchronizedList(new ArrayList<Food>());
	public List<Long> foodsRemoval = Collections.synchronizedList(new ArrayList<Long>());
	public List<Food> newFoods = Collections.synchronizedList(new ArrayList<Food>());
	public List<FoodStat> statFoods = Collections.synchronizedList(new ArrayList<FoodStat>());
	public int maxFood1 = 2000, maxFood2 = 50, maxFood3 = 10, totalMaxFood = maxFood1+maxFood2+maxFood3;
	public boolean mustRegenerate = false, ALWAYS_REGENERATE = true, canRender = true, canUpdate = true;
	public long nextFoodId = 0;
	public long nextBulletId = 0;
	public long nextPlayerId = 0;
	
	public World() {
	}
	
	public List<Food> getNewFoods() {
		synchronized(newFoods){
		return newFoods;
		}
	}

	public List<Player> getNewPlayers() {
		synchronized(newPlayers){
		return newPlayers;
		}
	}

	public List<Bullet> getNewBullets() {
		synchronized(newBullets){
		return newBullets;
		}
	}

	public List<FoodStat> getStatFoods() {
		synchronized(statFoods){
		return statFoods;
		}
	}

	public List<Player> getStatPlayers() {
		synchronized(statPlayers){
		return statPlayers;
		}
	}

	public List<Bullet> getStatBullets() {
		synchronized(statBullets){
		return statBullets;
		}
	}

	public void addBullet(Bullet b) {
		synchronized(tempBullets){
			b.setId(nextBulletId);
			tempBullets.add(b);
			nextBulletId++;
		}
	}
	public void removeBullet(Long id) {
		synchronized(bulletsRemoval){
			if(bullets.contains(getBulletFromId(id)))bulletsRemoval.add(getBulletFromId(id));
		}
	}
	
	public void addFood(Food f) {
		synchronized(tempFood){
		tempFood.add(f);
		}
	}
	
	public void removeFood(long f){
		synchronized(foodsRemoval){
		foodsRemoval.add(f);
		}
	}
	
	public Player addNewPlayer(Player p) {
		Player pp = p.clone();
		pp.setId(nextPlayerId);
		nextPlayerId++;
		addPlayer(pp);
		return pp;
	}
	
	public void addPlayer(Player p) {
		synchronized(tempPlayers){
		tempPlayers.add(p);
		}
	}
	
	public void addPlayer(List<Player> l) {
		synchronized(tempPlayers){
		tempPlayers.addAll(l);
		}
		
	}
	
	public void addBullet(List<Bullet> b) {
		synchronized(tempBullets){
			
			for(Bullet bl : b) {
				bl.setId(nextBulletId);
				nextBulletId++;
			}
		tempBullets.addAll(b);
		}
		
	}
	
	public void addFood(List<Food> f) {
		synchronized(tempFood){
		tempFood.addAll(f);
		}
	}
	
	public void removePlayer(long p){
		synchronized(playersRemoval) {
		playersRemoval.add(p);
		}
	}
	
	public void removePlayers(List<Long> l) {
		synchronized(playersRemoval) {
		playersRemoval.addAll(l);
		}
	}
	
	public void removeFoods(List<Long> l) {
		synchronized(foodsRemoval) {
		foodsRemoval.addAll(l);
		}
	}
	
	public void removeBullets(List<Long> ids){
		List<Bullet> b = new ArrayList<Bullet>();
		for(long l : ids) {
			if(bullets.contains(getBulletFromId(l)))b.add(getBulletFromId(l));
		}
		synchronized(bulletsRemoval) {
		bulletsRemoval.addAll(b);
		}
	}
	
	public List<Player> getPlayerList() {
		synchronized(players){
			return players;
		}
	}
	
	public List<Bullet> getBulletList() {
		synchronized(bullets){
		return bullets;
		}
	}
	
	public List<Food> getFoodList() {
		synchronized(foods){
		return foods;
		}
	}
	
	public void addBulletLifeChange(Bullet b) {
		synchronized(statBullets) {
		statBullets.add(b);
		}
	}
	
	public void generate() {
		System.out.print("Generating world ... ");
		synchronized(foods){
		for(int i = 0; i < maxFood1; i++) {
			foods.add(new Food((int)(Math.random()*2), (int)(Math.random()*mapWidth-50), (int)(Math.random()*mapHeight-150), nextFoodId));
			nextFoodId++;
		}
		for(int i = 0; i < maxFood2; i++) {
			foods.add(new Food(2, (int)(Math.random()*mapWidth-50), (int)(Math.random()*mapHeight-150), nextFoodId));
			nextFoodId++;
		}
		for(int i = 0; i < maxFood3; i++) {
			foods.add(new Food((int)(Math.random()*2)+3, (int)(Math.random()*mapWidth-50), (int)(Math.random()*mapHeight-150), nextFoodId));
			nextFoodId++;
		}
		}
		System.out.println("done");
	}
	
	public void update() {
		if(canUpdate){
		canRender = false;
			synchronized(foods){
				
		List<Food> ff = new ArrayList<Food>();
		for(Food f : foods){
			if(f.getHealth() <=0){
				ff.add(f);
			}
		}
		foods.removeAll(ff);
			}
			
			synchronized(statBullets) {
				synchronized(bullets) {
					for(Bullet b : statBullets) {
						getBulletFromId(b.getId()).setLife(b.getLife());
					}
				}

				statBullets.clear();
			}
		
		synchronized(foods){
			synchronized(statFoods){
		for(FoodStat f : statFoods) {
			
			if(getFoodFromId(f.getFood().getId()) != null){
				updateFood(f.getFood());
			}
		}
		statFoods.clear();
			}
		}
		
		synchronized(players) {
			synchronized(statPlayers) {
				for(Player p : statPlayers){
					if(p.isKnownID() && getPlayerFromId(p.getId()) != null){
						updatePlayer(p);
					}
				}
				statPlayers.clear();
			}
		}
		
		
		synchronized(bulletsRemoval){
			synchronized(bullets){
				
		for(Bullet l : bulletsRemoval) {
				bullets.remove(getBulletFromId(l.getId()));
		}
			}
		}
		synchronized(playersRemoval){
			synchronized(players){
		for(long l : playersRemoval) {
			players.remove(getPlayerFromId(l));
		}
			}
		}
		synchronized(foodsRemoval){
			synchronized(foods) {
		for(long l : foodsRemoval) {
			if(foods.contains(getFoodFromId(l))){
				foods.remove(getFoodFromId(l));
				mustRegenerate = true;
			}
			
		}
			}
		}
		
		synchronized(bulletsRemoval){
		bulletsRemoval.clear();
		}
		synchronized(foodsRemoval){
		foodsRemoval.clear();
		}
		synchronized(playersRemoval) {
		playersRemoval.clear();
		}
		
		if(mustRegenerate && ALWAYS_REGENERATE){
			regenerate();
			mustRegenerate = false;
		}
		synchronized(tempFood) {
			synchronized(foods){
		for(Food f : tempFood) {
			foods.add(f);
			newFoods.add(f);
		}
		tempFood.clear();
			}
		}
		
		synchronized(tempBullets){
			synchronized(bullets){
		for(Bullet b : tempBullets) {
			bullets.add(b);
			newBullets.add(b);
		}
		tempBullets.clear();
			}
		}
		synchronized(tempPlayers){
			synchronized(players){
		for(Player p : tempPlayers) {
			players.add(p);
			newPlayers.add(p);
		}
		tempPlayers.clear();
			}
		}

		canRender = true;
		}
	}
	
	public void regenerate() {
			
		int r = (int) (Math.random()*1);
		
		synchronized(foods){
			synchronized(newFoods){
		if(r == 0){
			for(int i = 0; i < totalMaxFood-foods.size(); i++) {
				Food f = new Food((int)(Math.random()*2), (int)(Math.random()*mapWidth-50), (int)(Math.random()*mapHeight-150), nextFoodId);
				foods.add(f);
				newFoods.add(f);
				nextFoodId++;
			}
		}
		else if(r == 1) {
			for(int i = 0; i < totalMaxFood-foods.size(); i++) {
				Food f = new Food(2, (int)(Math.random()*mapWidth-50), (int)(Math.random()*mapHeight-150), nextFoodId);
				foods.add(f);
				newFoods.add(f);
				nextFoodId++;
			}
		}
		}
		}
	}
	
	public boolean canRender() {
		return canRender;
	}
	
	public World clone() {
		try {
			return (World) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Food getFoodFromId(long id) {
		synchronized(foods){
		for(Food f : foods){
			if(f.getId() == id){
				return f;
			}
		}
		
		}
		return null;
	}
	
	public Bullet getBulletFromId(long id) {
		synchronized(bullets){
		for(Bullet b : bullets){
			if(b.getId() == id){
				return b;
			}
		}
		}
		return null;
	}
	
	public void updateFood(Food f){
		synchronized(foods){
			
			getFoodFromId(f.getId()).reset(f);
			
		}
	}
	
	public void updatePlayer(Player p){
		synchronized(players){
			getPlayerFromId(p.getId()).reset(p);
		}
	}
	
	public Player getPlayerFromId(long id) {
		synchronized(players){
		for(Player p : players){
			if(p.getId() == id){
				return p;
			}
		}
		}
		return null;
	}
	
	public Player getNearestPlayerInRange(Player from, long range) {
		synchronized(players) {
		Player p = null;
		
		for(Player pl : players) {
			float unX = Vector2.unsignedFloat(from.getPos().getX()-pl.getPos().getX());
			float unY = Vector2.unsignedFloat(from.getPos().getY()-pl.getPos().getY());
			
			if(unX < range && unY < range) {
				if(p == null) {
					p = pl.clone();
				}else {
					p = Player.getNearest(p, pl, from);
				}
			}
			
				
		}
		return p;
		}
		
		
	}
	
	public Food getNearestFoodInRange(Player from, long range) {
		synchronized(players) {
		Food f = null;
		
		for(Food fd : foods) {
			float unX = Vector2.unsignedFloat(from.getPos().getX()-fd.getPos().getX());
			float unY = Vector2.unsignedFloat(from.getPos().getY()-fd.getPos().getY());
			
			if(unX < range && unY < range) {
				if(f == null) {
					f = fd.clone();
				}else {
					f = Food.getNearest(f, fd, from);
				}
			}
			
				
		}
		return f;
		}
		
		
	}
	
	
}


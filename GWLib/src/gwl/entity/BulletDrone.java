package gwl.entity;

import java.awt.Rectangle;
import java.io.Serializable;

import gwl.math.Vector2;

public class BulletDrone implements Serializable{

	
	private static final long serialVersionUID = 4474473918622030661L;
	private Vector2 direction, pos;
	private float velocity, damage, mass, SPS;
	private int life, range, ticks;
	private long id, millis;
	private Rectangle hitBox;
	private Player owner;
	private boolean targetLocked = false;
	
	public BulletDrone(Player owner, int life, long id, float velocity, float x, float y, float damage, float mass, int range, Vector2 direction, int SPS) {
		this.direction = direction;
		this.owner = owner;
		this.life = life;
		this.id = id;
		this.pos = new Vector2(x, y);
		this.velocity = velocity;
		this.setSPS(SPS);
		ticks = 0;
		millis = System.currentTimeMillis();
		this.damage = damage;
		this.mass = mass;
		this.range = range;
	}

	public synchronized Vector2 getPos() {
		return pos;
	}

	public synchronized void setPos(Vector2 pos) {
		this.pos = pos;
	}

	public synchronized Vector2 getDirection() {
		return direction;
	}

	public synchronized void setDirection(Vector2 direction) {
		this.direction = direction;
	}

	public synchronized float getVelocity() {
		return velocity;
	}

	public synchronized void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public synchronized float getDamage() {
		return damage;
	}

	public synchronized void setDamage(float damage) {
		this.damage = damage;
	}

	public synchronized float getMass() {
		return mass;
	}

	public synchronized void setMass(float mass) {
		this.mass = mass;
	}


	public synchronized int getLife() {
		return life;
	}
	
	public void update(World world) {
		
		synchronized(world) {
			Player p = world.getNearestPlayerInRange(owner, range);
			if(p != null) {
				Vector2 pos = p.getPos();
				float ab = pos.getX()-owner.getPos().getX();
				float bc = owner.getPos().getY()-pos.getY();
				
				float angle = (float) Math.atan(bc/ab);
				direction = new Vector2((float)Math.cos(angle), (float)Math.sin(angle));
				targetLocked = true;
			}else {
				Food f = world.getNearestFoodInRange(owner, range);
				if(f != null) {
					Vector2 pos = f.getPos();
					float ab = pos.getX()-owner.getPos().getX();
					float bc = owner.getPos().getY()-pos.getY();
					
					float angle = (float) Math.atan(bc/ab);
					direction = new Vector2((float)Math.cos(angle), (float)Math.sin(angle));
					targetLocked = true;
				}else {
					float ab = pos.getX()-owner.getPos().getX();
					float bc = owner.getPos().getY()-pos.getY();
					
					float angle = (float) Math.atan(bc/ab);
					direction = new Vector2((float)Math.cos(angle), (float)Math.sin(angle));
					targetLocked = false;
				}
			}
			
			Vector2 newPos = this.getPos();
			newPos.setX(this.getPos().getX()+direction.getX());
			newPos.setY(this.getPos().getY()+direction.getY());
			this.setPos(newPos);
		}
		
	}
	
	
	
	public void updateHitBox() {
		hitBox.setBounds((int)pos.getX()-600, (int)pos.getY()-290, 50, 50);
	}

	public synchronized void setLife(int life) {
		this.life = life;
	}

	public synchronized long getId() {
		return id;
	}

	public synchronized void setId(long id) {
		this.id = id;
	}

	public synchronized Rectangle getHitBox() {
		return hitBox;
	}

	public synchronized void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}

	public synchronized Player getOwner() {
		return owner;
	}

	public synchronized void setOwner(Player owner) {
		this.owner = owner;
	}

	public boolean isTargetLocked() {
		return targetLocked;
	}

	public void setTargetLocked(boolean targetLocked) {
		this.targetLocked = targetLocked;
	}

	public float getSPS() {
		return SPS;
	}

	public void setSPS(float sPS) {
		SPS = sPS;
	}

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

	public long getMillis() {
		return millis;
	}

	public void setMillis(int millis) {
		this.millis = millis;
	}

}

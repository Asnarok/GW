package gwl.entity;

import java.awt.Rectangle;
import java.io.Serializable;

import gwl.math.Vector2;

public class Bullet implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8627698274043435016L;
	private Vector2 direction;
	private float velocity, x, y, damage, mass;
	private int ticks = 0, duration, life, size;
	private long id;
	private Rectangle hitBox;
	private Player owner;
	
	
	public Bullet(float angleDirection, float x, float y, float velocity, int duration, int penetration, float damage, Player owner, int id, float mass, int size){
		direction = new Vector2((float)(Math.sin(angleDirection)), (float)(-Math.cos(angleDirection)));
		this.setDuration(duration);
		this.setX(x);
		this.setY(y);
		this.setVelocity(velocity);
		this.setDamage(damage);
		this.setLife(penetration);
		this.mass = mass;
		this.setSize(size);
		this.setId(id);
		this.setOwner(owner);
		hitBox = new Rectangle((int)x, (int)y, (int)size, (int)size);

	}
	
	public Bullet(float angleDirection, float x, float y, float velocity, int duration, int penetration, float damage, Player owner, float mass, int size){
		direction = new Vector2((float)(Math.sin(angleDirection)), (float)(-Math.cos(angleDirection)));
		this.setDuration(duration);
		this.setX(x);
		this.setY(y);
		this.setVelocity(velocity);
		this.setDamage(damage);
		this.setLife(penetration);
		this.mass = mass;
		this.setSize(size);
		this.setOwner(owner);
		hitBox = new Rectangle((int)x, (int)y, (int)size, (int)size);
		
	}
	public Vector2 getDirection() {
		return direction;
	}
	public void setDirection(Vector2 direction) {
		this.direction = direction;
	}
	
	public Vector2 getVector() {
		return direction;
	}
	
	
	public float getVelocity() {
		return velocity;
	}
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getDamage() {
		return damage;
	}
	public void setDamage(float damage) {
		this.damage = damage;
	}
	public int getTicks() {
		return ticks;
	}
	public void setTicks(int ticks) {
		this.ticks = ticks;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public float getMass() {
		return mass;
	}
	public void setMass(float mass) {
		this.mass = mass;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}
	
	public void update() {
		hitBox.setBounds((int)x-600, (int)y-290, (int)size, (int)size);
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}

package gwl.entity;

import java.awt.Rectangle;
import java.io.Serializable;

import gwl.math.Vector2;

public class Player implements Serializable, Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8998555904041291492L;

	public static final int NOOB = 0, SNIPER = 1, SMASHER = 2, DUAL = 3, PUMP = 4, MACHINE_GUN = 5,
			LONG_RANGE_SNIPER = 6, OVERSEER = 7, HARD_SMASHER = 8, QUAD = 9, TRIPUMP = 10, DUAL_MACHINE = 11,
			TRIPLE_LONG_RANGE_SNIPER = 12, OVERLORD = 13, GRINDER = 14, OCTA = 15, ROCKET_GUNNER = 16, GUNNER = 17,
			THORNS_TRIPLE_LONG_RANGE_SNIPER = 18, HIDDEN_OVERLORD = 19, AUTO_GRINDER = 20, HEXAUTO = 21, ROCKET_SHOOTER = 22, DUAL_GUNNER = 23,
			STREAM_SNIPER = 24, FACTORY = 25, MOTHERSHIP = 26, PLASMA_DISRUPTOR = 27;
	
	private int CLASS;
	private float maxSpeed = 10f, health, bulletdamage, bulletspeed, bodydamage, regen, maxHealth;
	private Vector2 speed, pos;
	private long bulletMillis = System.currentTimeMillis();
	private String name;
	private int SPS, bulletduration, bulletpenetration;
	private Rectangle hitBox;
	private boolean knownID = false;
	private float angle;
	private long id;

	public Player(Vector2 pos, int CLASS, String name, int sps, float health, float speed, int bulletduration, int bulletpenetration, float regen, float bulletdamage, float bulletspeed, float bodydamage, float angle, float maxHealth) {
		this.pos = pos; 
		this.CLASS = CLASS;
		this.speed = new Vector2(0f, 0f);
		this.maxSpeed = speed;
		this.name = name;
		this.setHealth(health);
		this.setMaxHealth(maxHealth);
		this.setBulletdamage(bulletdamage);
		this.setBulletspeed(bulletspeed);
		this.setBodydamage(bodydamage);
		this.setRegen(regen);
		this.setSPS(sps);
		this.setBulletduration(bulletduration);
		this.setBulletpenetration(bulletpenetration);
		this.setAngle(angle);
		if(this.CLASS == NOOB) {
			hitBox = new Rectangle((int)getPos().getX(), (int)getPos().getY(), 62, 75);
		}
		else if(this.CLASS == DUAL) {
			hitBox = new Rectangle((int)getPos().getX(), (int)getPos().getY(), 62, 75);
		}
		else if(this.CLASS == DUAL_MACHINE) {
			hitBox = new Rectangle((int)getPos().getX(), (int)getPos().getY(), 100, 100);
		}else if(this.CLASS == FACTORY) {
			hitBox = new Rectangle((int)getPos().getX(), (int)getPos().getY(), 100, 100);
		}
	}
	
	public Player(Vector2 pos, int CLASS, String name, int sps, float health, float speed, int bulletduration, int bulletpenetration, float regen, float bulletdamage, float bulletspeed, float bodydamage, int id, float angle, float maxHealth) {
		this.pos = pos; 
		this.CLASS = CLASS;
		this.speed = new Vector2(0f, 0f);
		this.maxSpeed = speed;
		this.setHealth(health);
		this.setMaxHealth(maxHealth);
		this.setBulletdamage(bulletdamage);
		this.setBulletspeed(bulletspeed);
		this.setBodydamage(bodydamage);
		this.setRegen(regen);
		this.setSPS(sps);
		this.setBulletduration(bulletduration);
		this.setBulletpenetration(bulletpenetration);
		this.setId(id);
		this.setKnownID(true);
		this.setAngle(angle);
		if(this.CLASS == NOOB) {
			hitBox = new Rectangle((int)getPos().getX(), (int)getPos().getY(), 62, 75);
		}
		else if(this.CLASS == DUAL) {
			hitBox = new Rectangle((int)getPos().getX(), (int)getPos().getY(), 62, 75);
		}
		else if(this.CLASS == DUAL_MACHINE) {
			hitBox = new Rectangle((int)getPos().getX(), (int)getPos().getY(), 100, 100);
		}else if(this.CLASS == FACTORY) {
			hitBox = new Rectangle((int)getPos().getX(), (int)getPos().getY(), 100, 100);
		}
	}
	
	public int getClassType() {
		return CLASS;
	}
	
	public Vector2 getPos() {
		return pos;
	}
	
	public Vector2 getSpeed() {
		return speed;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isDifferent(Player p) {
		Vector2 pos = p.getPos().clone();
		Vector2 speed = p.getSpeed().clone();
		
		if(p.getAngle() == this.getAngle()){
			if(p.getBodydamage() == this.getBodydamage()) {
				if(p.getBulletdamage() == this.getBulletdamage()) {
					if(p.getBulletduration() == this.getBulletduration()) {
						if(p.getBulletpenetration() == this.getBulletpenetration()) {
							if(p.getClassType() == this.getClassType()) {
								if(p.getHealth() == this.getHealth()) {
										if(pos.getX() == this.getPos().getX()) {
											if(pos.getY() == this.getPos().getY()) {
												if(p.getRegen() == this.getRegen()) {
													if(speed.getX() == this.getSpeed().getX()) {
														if(speed.getY() == this.getSpeed().getY()) {
															if(p.getSPS() == this.getSPS()) {
																if(p.getHitBox() == this.getHitBox()){
																return false;
																}
															}
														}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return true;
	}

	public void setCLASS(int CLASS) {
		this.CLASS = CLASS;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public void setSpeed(Vector2 speed) {
		this.speed = speed;
	}

	public void setPos(Vector2 pos) {
		this.pos = pos;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
	
	public void updateHitBox() {
		hitBox.setBounds((int)this.getPos().getX(), (int)this.getPos().getY(), (int)hitBox.getWidth(), (int)hitBox.getHeight());
	}

	public float getBulletspeed() {
		return bulletspeed;
	}

	public void setBulletspeed(float bulletspeed) {
		this.bulletspeed = bulletspeed;
	}

	public float getBodydamage() {
		return bodydamage;
	}

	public void setBodydamage(float bodydamage) {
		this.bodydamage = bodydamage;
	}

	public float getRegen() {
		return regen;
	}

	public void setRegen(float regen) {
		this.regen = regen;
	}

	public int getBulletduration() {
		return bulletduration;
	}

	public void setBulletduration(int bulletduration) {
		this.bulletduration = bulletduration;
	}

	public float getBulletdamage() {
		return bulletdamage;
	}

	public void setBulletdamage(float bulletdamage) {
		this.bulletdamage = bulletdamage;
	}

	public long getBulletMillis() {
		return bulletMillis;
	}

	public void setBulletMillis(long bulletMillis) {
		this.bulletMillis = bulletMillis;
	}

	public int getBulletpenetration() {
		return bulletpenetration;
	}

	public void setBulletpenetration(int bulletpenetration) {
		this.bulletpenetration = bulletpenetration;
	}

	public int getSPS() {
		return SPS;
	}

	public void setSPS(int sPS) {
		SPS = sPS;
	}
	
	public float getMaxSpeed() {
		return maxSpeed;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	
	public Player clone() {
		try {
			return (Player)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
		this.knownID = true;
	}
	
	public void reset(Player p) {
		this.setAngle(p.getAngle());
		this.setBodydamage(p.getBodydamage());
		this.setBulletdamage(p.getBulletdamage());
		this.setBulletduration(p.getBulletduration());
		this.setBulletMillis(p.getBulletMillis());
		this.setBulletpenetration(p.getBulletpenetration());
		this.setBulletspeed(p.getBulletspeed());
		this.setCLASS(p.getClassType());
		this.setHealth(p.getHealth());
		this.setId(p.getId());
		this.setMaxSpeed(p.getMaxSpeed());
		this.setName(p.getName());
		this.setPos(p.getPos());
		this.setRegen(p.getRegen());
		this.setSpeed(p.getSpeed());
		this.setSPS(p.getSPS());
	}

	public boolean isKnownID() {
		return knownID;
	}

	public void setKnownID(boolean knownID) {
		this.knownID = knownID;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	
	public static Player getNearest(Player vec1, Player vec2, Player from) {
		Player vec;
		
		float unX1, unX2, unY1, unY2;
		unX1 = Vector2.unsignedFloat(from.getPos().getX()-vec1.getPos().getX());
		unY1 = Vector2.unsignedFloat(from.getPos().getY()-vec1.getPos().getY());
		unX2 = Vector2.unsignedFloat(from.getPos().getX()-vec2.getPos().getX());
		unY2 = Vector2.unsignedFloat(from.getPos().getY()-vec2.getPos().getY());
		
		if(unX1+unY1 < unX2+unY2) vec = vec1.clone();
		else vec = vec2.clone();
		
		return vec;
	}
	
	
	
	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}

}

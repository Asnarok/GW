package gwl.entity;

import java.awt.Rectangle;
import java.io.Serializable;

import gwl.math.Vector2;


public class Food implements Serializable, Cloneable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8284294722681078866L;
	public static final int LOS_LEVEL_1 = 0, LOS_LEVEL_2 = 1, TRIANGLE = 2, PENTAGON = 3, HEXAGON = 4;
	private int type, size, xpgain;
	private float maxhealth, health;
	private float randomAngle;
	private long id;
	private Rectangle hitBox;
	private Vector2 pos;
	
	public Food(int type, float x, float y, long id) {
		
		setRandomAngle((float)(Math.toRadians(Math.random()*360)));
		this.setId(id);
		if(type == 0){
			size = 48;
			setMaxhealth(20.0f);
			health = 20.0f;
		}
		if(type == 1){
			size = 48;
			setMaxhealth(35.0f);
			health = 35.0f;
		}
		if(type == 2){
			size = 128;
			setMaxhealth(512.0f);
			health = 512.0f;
		}
		if(type == 3){
			size = 200;
			setMaxhealth(5000.0f);
			health = 5000.0f;

		}
		if(type == 4) {
			size = 256;
			setMaxhealth(10000.0f);
			health = 10000.0f;

		}
		

		hitBox = new Rectangle((int)x, (int)y, (int)size, (int)size);
		this.type = type;
		this.pos = new Vector2(x, y);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public int getXpgain() {
		return xpgain;
	}

	public void setXpgain(int xpgain) {
		this.xpgain = xpgain;
	}

	public float getMaxhealth() {
		return maxhealth;
	}

	public void setMaxhealth(float maxhealth) {
		this.maxhealth = maxhealth;
	}

	public float getRandomAngle() {
		return randomAngle;
	}

	public void setRandomAngle(float randomAngle) {
		this.randomAngle = randomAngle;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
	}
	
	public void update() {
		hitBox.setBounds((int)pos.getX(), (int)pos.getY(), (int)size, (int)size);
	}
	
	public boolean isSame(Food f) {
		return f.getHealth() == getHealth() && f.getMaxhealth() == getMaxhealth() && f.getRandomAngle() == getRandomAngle() && f.getSize() == getSize() && f.getType() == getType() && f.getPos().getX() == getPos().getX() && f.getXpgain() == getXpgain() && f.getPos().getY() == getPos().getY();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public Food clone() {
		try {
			return (Food) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Food getNearest(Food vec1, Food vec2, Player from) {
		Food vec;
		
		float unX1, unX2, unY1, unY2;
		unX1 = Vector2.unsignedFloat(from.getPos().getX()-vec1.getPos().getX());
		unY1 = Vector2.unsignedFloat(from.getPos().getY()-vec1.getPos().getY());
		unX2 = Vector2.unsignedFloat(from.getPos().getX()-vec2.getPos().getX());
		unY2 = Vector2.unsignedFloat(from.getPos().getY()-vec2.getPos().getY());
		
		if(unX1+unY1 < unX2+unY2) vec = vec1.clone();
		else vec = vec2.clone();
		
		return vec;
	}
	
	public void reset(Food f) {
		this.setHealth(f.getHealth());
		this.setHitBox(f.getHitBox());
		this.setId(f.getId());
		this.setMaxhealth(f.getMaxhealth());
		this.setRandomAngle(f.getRandomAngle());
		this.setSize(f.getSize());
		this.setType(f.getType());
		this.setXpgain(f.getXpgain());
		this.setPos(f.getPos());
	}

	public Vector2 getPos() {
		return pos;
	}

	public void setPos(Vector2 pos) {
		this.pos = pos;
	}

}

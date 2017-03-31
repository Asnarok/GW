package gwl.math;

import java.io.Serializable;

public class Vector2 implements Serializable, Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3787095259592513353L;
	private float x, y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
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
	
	public void add(Vector2 vec) {
		this.setX(this.getX()+vec.getX());
		this.setY(this.getY()+vec.getY());
	}
	
	public void sub(Vector2 vec) {
		this.setX(this.getX()-vec.getX());
		this.setY(this.getY()-vec.getY());
	}
	
	public void mult(Vector2 vec) {
		this.setX(this.getX()*vec.getX());
		this.setY(this.getY()*vec.getY());
	}
	
	public void mult(float toMult) {
		this.setX(this.getX()*toMult);
		this.setY(this.getY()*toMult);
	}
	
	

	public static float unsignedFloat(float f) {
		if(f < 0.0) return -f;
		else return f;
	}
	public Vector2 clone() {
		try {
			return (Vector2) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}

package gwl.entity;

import java.io.Serializable;

public class FoodStat implements Cloneable, Serializable{

	private static final long serialVersionUID = 5909558782426141019L;
	private Food food;
	private long actorID;
	
	public FoodStat(Food f, long actorID) {
		this.food = f;
		this.actorID = actorID;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public long getActorID() {
		return actorID;
	}

	public void setActorID(long actorID) {
		this.actorID = actorID;
	}

}

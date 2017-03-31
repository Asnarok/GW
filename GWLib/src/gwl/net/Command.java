package gwl.net;

import java.io.Serializable;

public class Command implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7412690971359886617L;
	public static final int NEW_PLAYER = 0, PLAYER_STAT = 1, NEW_BULLET = 2, DIE = 3, GET_PLAYER_INDEX = 4, WORLD = 5, NEW_FOOD = 6, DIED_FOOD = 7, DIED_BULLET = 8, DISCONNECT = 9, PLAYERS = 10, FOOD_STAT = 11, POS_CHANGE = 12, ANGLE_CHANGE = 13, BULLET_LIFE = 14, DRONE_BULLET_STAT = 15, DRONE_BULLET_DIED = 16, NEW_DRONE_BULLET = 17;
	private int type;
	private Object value;
	
	public Command(int type, Object value) {
		this.type = type;
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

}

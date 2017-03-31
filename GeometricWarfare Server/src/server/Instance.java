package server;

import gwl.entity.World;
import gwl.net.Command;

public class Instance {

	private int mapWidth, mapHeight;
	public World world;
	
	public Instance(int mapWidth, int mapHeight) {
		world = new World();
		world.maxFood1 = SettingsLoader.maxFood1;
		world.maxFood2 = SettingsLoader.maxFood2;
		world.maxFood3 = SettingsLoader.maxFood3;
		world.generate();
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;
	}
	public void updateBullets() {
		for(ClientThread c : Main.th) {
			c.sendRequest(new Command(0, world.bullets));
		}
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

}

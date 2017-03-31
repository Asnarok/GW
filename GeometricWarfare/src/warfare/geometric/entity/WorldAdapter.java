package warfare.geometric.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ConcurrentModificationException;

import gwl.entity.Food;
import gwl.entity.World;
import warfare.geometric.main.Main;


public class WorldAdapter {

	
	public static void render(Graphics2D g, World w) {
		if(Main.display.player.isKnownID()){

		Main.display.p.fillBackground(new Color(214, 214, 214), g);
		
		g.setColor(new Color(128, 128, 128));
		
		
		for(int x = -(int)Main.display.player.getPos().getX()+Main.display.p.middleWidth; x < -Main.display.player.getPos().getX()+Main.display.world.mapWidth; x+= 32) {
			for(int y = -(int)Main.display.player.getPos().getY()+Main.display.p.middleHeight; y < -Main.display.player.getPos().getY()+Main.display.world.mapHeight; y+= 32) {
				if(x > -32 && x < Main.display.p.width && y > -32 && y < Main.display.p.height)g.drawRect(x, y, 32, 32);
			}
		}

		if(Main.display.player != null)PlayerAdapter.render(g, Main.display.player);
		
		if(Main.display.world.players.size() != 0){
			PlayerAdapter.renderOtherPlayers(g);
		}

		try{
				w.canUpdate = false;
		for(Food f : w.getFoodList()) {
			FoodAdapter.render(g, f);
		}
				w.canUpdate = true;
		}catch(ConcurrentModificationException e) {
			e.printStackTrace();
		}
		}
	}
	
	
	
}


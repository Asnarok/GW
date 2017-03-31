package warfare.geometric.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import gwl.entity.Food;
import warfare.geometric.main.Main;
import warfare.geometric.resource.ResourceLoader;

public class FoodAdapter {

	
	public static void render(Graphics2D g, Food f) {
		int xoffset = 600, yoffset = 290;
		if(f.getPos().getX()-Main.display.player.getPos().getX()+xoffset+f.getSize() > -32 && f.getPos().getX()-Main.display.player.getPos().getX()+f.getSize() < Main.display.p.width && f.getPos().getY()-Main.display.player.getPos().getY()+yoffset+f.getSize() > -32 && f.getPos().getY()-Main.display.player.getPos().getY()+f.getSize() < Main.display.p.height){
			AffineTransform at = g.getTransform();
			g.rotate(f.getRandomAngle(), f.getPos().getX()-Main.display.player.getPos().getX()+f.getSize()/2+xoffset, f.getPos().getY()-Main.display.player.getPos().getY()+f.getSize()/2+yoffset);
			if(f.getType() == 0)g.drawImage(ResourceLoader.los1, (int)(f.getPos().getX()-Main.display.player.getPos().getX())+xoffset, (int)(f.getPos().getY()-Main.display.player.getPos().getY())+yoffset, f.getSize(), f.getSize(), null);
			if(f.getType() == 1)g.drawImage(ResourceLoader.los2, (int)(f.getPos().getX()-Main.display.player.getPos().getX())+xoffset, (int)(f.getPos().getY()-Main.display.player.getPos().getY())+yoffset, f.getSize(), f.getSize(), null);
			if(f.getType() == 2)g.drawImage(ResourceLoader.triangle, (int)(f.getPos().getX()-Main.display.player.getPos().getX())+xoffset, (int)(f.getPos().getY()-Main.display.player.getPos().getY())+yoffset, f.getSize(), f.getSize(), null);
			if(f.getType() == 3)g.drawImage(ResourceLoader.pentagon, (int)(f.getPos().getX()-Main.display.player.getPos().getX())+xoffset, (int)(f.getPos().getY()-Main.display.player.getPos().getY())+yoffset, f.getSize(), f.getSize(), null);
			if(f.getType() == 4)g.drawImage(ResourceLoader.hexagon, (int)(f.getPos().getX()-Main.display.player.getPos().getX())+xoffset, (int)(f.getPos().getY()-Main.display.player.getPos().getY())+yoffset, f.getSize(), f.getSize(), null);

			double percent = f.getHealth()/f.getMaxhealth()*100.0;
			if(percent != 100.0){
			Color c = null;
			if(percent <= 33)c = new Color(255, 0, 26);
			else if(percent > 33 && percent <= 66)c = new Color(255, 152, 0);
			else if(percent > 66)c = new Color(0, 204, 51);
			
			double dwidth =  (f.getSize()-f.getSize()/3)*percent/100;
			int width = (int) dwidth;
			double xx = f.getPos().getX()-Main.display.player.getPos().getX()+xoffset+f.getSize()/5.5;
			g.setColor(new Color(230, 230, 230));
			double twidth = (f.getSize()-f.getSize()/3);
			if(f.getType() != 2){
			g.fillRoundRect((int)(xx-1), (int)(f.getPos().getY()-Main.display.player.getPos().getY())+yoffset+f.getSize()/2-6, (int)twidth+2, 11, 11, 11);
			g.setColor(c);
			g.fillRoundRect((int)xx, (int)(f.getPos().getY()-Main.display.player.getPos().getY())+yoffset+f.getSize()/2-5, width, 9, 9, 9);
			}else{
				g.fillRoundRect((int)(xx-1), (int)(f.getPos().getY()-Main.display.player.getPos().getY())+yoffset+f.getSize()/2+14, (int)twidth+2, 11, 11, 11);
				g.setColor(c);
				g.fillRoundRect((int)xx, (int)(f.getPos().getY()-Main.display.player.getPos().getY())+yoffset+f.getSize()/2+15, width, 9, 9, 9);
			}
			}
			g.setTransform(at);
		}
		
	}

}

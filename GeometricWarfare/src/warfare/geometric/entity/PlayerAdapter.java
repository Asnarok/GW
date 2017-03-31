package warfare.geometric.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;

import gwl.entity.Bullet;
import gwl.entity.Player;
import gwl.math.Vector2;
import gwl.net.Command;
import warfare.geometric.main.Main;
import warfare.geometric.resource.ResourceLoader;

public class PlayerAdapter {
	

	public static long lastMillis = System.currentTimeMillis();
	public static List<Bullet> newBullets = Collections.synchronizedList(new ArrayList<Bullet>());
	public static List<Long> diedBullets = Collections.synchronizedList(new ArrayList<Long>());
	public static int maxXoffset = 40;
	public static int maxYoffset = 60;
	public static int minXoffset = 0;
	public static int minYoffset = -26;
	 
	
	public static void update(Player p) {
		
		
		if(p.isKnownID()){
		Player last = p.clone();
		last.setSpeed(p.getSpeed().clone());
		last.setPos(p.getPos().clone());
		
		try{
		for(Bullet b : Main.display.world.getBulletList()) {
			BulletAdapter.update(b);
		}
		}catch(ConcurrentModificationException e) {
			e.printStackTrace();
		}
		p.setAngle((float)Main.display.p.angle);
		
		if(Main.display.listener.isClicking || Main.display.listener.autoFire) {
			if(System.currentTimeMillis()-p.getBulletMillis() >= 1000/p.getSPS()) {
				float offset = 0.0f;
				float angle = (float)Main.display.p.calcAngle();
				int xcenter = -11;
				int ycenter = 3;
				
				if(p.getClassType() == Player.NOOB) {
					if(Math.random() > 0.5) {
						angle += Math.toRadians(Math.random()*10);
					}else {
						angle -= Math.toRadians(Math.random()*10);
					}
				}
				
				if(p.getClassType() == Player.DUAL){
					//offset = 10.0;
					xcenter = -11;
					ycenter = -10;
				}else if(p.getClassType() == Player.DUAL_MACHINE) {
				}
				
				offset = (float)Math.toRadians(offset);
				List<Bullet> bullets = new ArrayList<Bullet>();
				if(p.getClassType() == Player.NOOB){
				Bullet b = new Bullet(angle, (float)((Main.display.p.width/2+xcenter)+55*Math.cos(Main.display.p.calcAngle()-Math.toRadians(90.0)+offset))+p.getPos().getX(), (float)((Main.display.p.height/2+ycenter)+55*Math.sin(Main.display.p.calcAngle()-Math.toRadians(90.0)+offset))+p.getPos().getY(), 3.0f, 200, p.getBulletpenetration(), p.getBulletdamage(), p, 0.3f, 24);
				bullets.add(b);
				}else if(p.getClassType() == Player.DUAL) {
					
					Bullet b = new Bullet(angle, (float)((Main.display.p.width/2+xcenter)+55*Math.cos(Main.display.p.calcAngle()-Math.toRadians(90.0)+offset))+p.getPos().getX(), (float)((Main.display.p.height/2+ycenter)+55*Math.sin(Main.display.p.calcAngle()-Math.toRadians(90.0)+offset))+p.getPos().getY(), 3.0f, 200, p.getBulletpenetration(), p.getBulletdamage(), p, 0.3f, 24);
					bullets.add(b);
					
					Vector2 v = new Vector2((float)(Math.cos(angle)), (float)(Math.sin(angle)));
					v.setX(-v.getX());
					v.setY(-v.getY());
					
					Bullet bb = new Bullet((float)Main.display.p.convertVector2ToAngle(v), (float)((Main.display.p.width/2+xcenter)+55*Math.cos(Main.display.p.convertVector2ToAngle(v)-Math.toRadians(90.0)+offset))+p.getPos().getX(), (float)((Main.display.p.height/2+ycenter)+55*Math.sin(Main.display.p.convertVector2ToAngle(v)-Math.toRadians(90.0)+offset))+p.getPos().getY(), 3.0f, 200, p.getBulletpenetration(), p.getBulletdamage(), p, 0.3f, 24);
					bullets.add(bb);
				}else if(p.getClassType() == Player.DUAL_MACHINE) {
					
					float angle1 = (float)Main.display.p.calcAngle();
					float angle2 = (float)Main.display.p.calcAngle();
					float angle3 = (float)Main.display.p.calcAngle();
					float d1 = (float)Math.random()*20;
					float d2 = (float)Math.random()*20;
					float d3 =(float) Math.random()*20;
					if(Math.random() > 0.5) {
						angle1 += (float)Math.toRadians(d1);
						angle2 += (float)Math.toRadians(d2);
						angle3 += (float)Math.toRadians(d3);
					}else {
						angle1 -= (float)Math.toRadians(d1);
						angle2 -= (float)Math.toRadians(d2);
						angle3 -= (float)Math.toRadians(d3);
					}
					
					
					Bullet b = new Bullet(angle1, (float)((Main.display.p.width/2+xcenter)+55*Math.cos(Main.display.p.calcAngle()-Math.toRadians(90.0)+offset))+p.getPos().getX(), (float)((Main.display.p.height/2+ycenter)+55*Math.sin(Main.display.p.calcAngle()-Math.toRadians(90.0)+offset))+p.getPos().getY(), 3.0f, 200, p.getBulletpenetration(), p.getBulletdamage(), p, 0.2f, 24);
					Bullet b1 = new Bullet(angle2, (float)((Main.display.p.width/2+xcenter)+50*Math.cos(Main.display.p.calcAngle()-Math.toRadians(90.0)+offset-Math.toRadians(30)))+p.getPos().getX(), (float)((Main.display.p.height/2+ycenter)+55*Math.sin(Main.display.p.calcAngle()-Math.toRadians(90.0)+offset-Math.toRadians(30)))+p.getPos().getY(), 3.0f, 200, p.getBulletpenetration(), p.getBulletdamage(), p, 0.2f, 24);
					Bullet b2 = new Bullet(angle3, (float)((Main.display.p.width/2+xcenter)+50*Math.cos(Main.display.p.calcAngle()-Math.toRadians(90.0)+offset+Math.toRadians(30)))+p.getPos().getX(), (float)((Main.display.p.height/2+ycenter)+55*Math.sin(Main.display.p.calcAngle()-Math.toRadians(90.0)+offset+Math.toRadians(30)))+p.getPos().getY(), 3.0f, 200, p.getBulletpenetration(), p.getBulletdamage(), p, 0.2f, 24);
					bullets.add(b);
					bullets.add(b1);
					bullets.add(b2);
				}
				
				Main.display.world.addBullet(bullets);
				synchronized(newBullets) {
					newBullets.addAll(bullets);
				}
				
				if(p.getSpeed().getX() <= 0.0f){
					if(p.getSpeed().getX() > -p.getMaxSpeed()){
						
						float f = (float)(p.getSpeed().getX());
						
						for(Bullet bb : bullets){
							f-=bb.getDirection().getX()*bb.getMass();
						}
						p.getSpeed().setX(f);
						
					}
				}
				if(p.getSpeed().getX() > 0.0f){
					if(p.getSpeed().getX() < p.getMaxSpeed()){
						float f = (float)(p.getSpeed().getX());
						
						for(Bullet bb : bullets){
							f-=bb.getDirection().getX()*bb.getMass();
						}
						p.getSpeed().setX(f);
						
					}
				}
				if(p.getSpeed().getY() <= 0.0f){
					if(p.getSpeed().getY() > -p.getMaxSpeed()){
						float f = (float)(p.getSpeed().getY());
						
						for(Bullet bb : bullets){
							f-=bb.getDirection().getY()*bb.getMass();
						}
						p.getSpeed().setY(f);
						
					}
				}
				if(p.getSpeed().getY() > 0.0f){
					if(p.getSpeed().getY() < p.getMaxSpeed()){
						float f = (float)(p.getSpeed().getY());
						
						for(Bullet bb : bullets){
							f-=bb.getDirection().getY()*bb.getMass();
						}
						p.getSpeed().setY(f);
					}
				}
				
				
				p.setBulletMillis(System.currentTimeMillis());
			}
		}
		if(Main.display.listener.LEFT && !Main.display.listener.RIGHT) {
				if(p.getSpeed().getX() > -p.getMaxSpeed())p.getSpeed().add(new Vector2(-0.1f, 0.0f));
		}
		
		if(Main.display.listener.RIGHT && !Main.display.listener.LEFT) {
				if(p.getSpeed().getX() < p.getMaxSpeed())p.getSpeed().add(new Vector2(0.1f, 0.0f));
		}
		
		
		if(Main.display.listener.UP && !Main.display.listener.DOWN) {
			if(p.getSpeed().getY() > -p.getMaxSpeed())p.getSpeed().add(new Vector2(0.0f, -0.1f));
	}
	
	if(Main.display.listener.DOWN && !Main.display.listener.UP) {
			if(p.getSpeed().getY() < p.getMaxSpeed())p.getSpeed().add(new Vector2(0.0f, 0.1f));
	}
	
	if(!Main.display.listener.RIGHT && !Main.display.listener.LEFT) {
		
		if(p.getSpeed().getX() >= 0.0f) {
			if(p.getSpeed().getX() > 0.01f)p.getSpeed().sub(new Vector2(p.getSpeed().getX()/10.0f, 0.0f));
			else p.getSpeed().setX(0.0f); 
		}
		
		if(p.getSpeed().getX() <= 0.0f) {
			if(p.getSpeed().getX() < -0.01f)p.getSpeed().add(new Vector2(-(p.getSpeed().getX()/10.0f), 0.0f));
			else p.getSpeed().setX(0.0f);
		}
	}
	if(!Main.display.listener.UP && !Main.display.listener.DOWN) {
		
		if(p.getSpeed().getY() >= 0.0f) {
			if(p.getSpeed().getY() > 0.01f)p.getSpeed().sub(new Vector2(0.0f, p.getSpeed().getY()/10.0f));
			else p.getSpeed().setY(0.0f); 
		}
		
		if(p.getSpeed().getY() <= 0.0f) {
			if(p.getSpeed().getY() < -0.01f)p.getSpeed().add(new Vector2(0.0f, -p.getSpeed().getY()/10.0f));
			else p.getSpeed().setY(0.0f);
		}
		
	}
	
	
	
	
	
	if(p.getClassType() == Player.DUAL_MACHINE) {

		maxXoffset = 80;
		maxYoffset = 79;
		minXoffset = -15;
		minYoffset = -29;
	}
	
		if(p.getSpeed().getX() > 0.0f) {
			if(p.getPos().getX() < Main.display.world.mapWidth-Main.display.p.middleWidth-maxXoffset){
				
				p.getPos().setX(p.getPos().getX()+p.getSpeed().getX());
				
			}
			
		}
		

		if(p.getSpeed().getX() <= 0.0f) {
			
			if(p.getPos().getX() >= minXoffset){
				
				p.getPos().setX(p.getPos().getX()+p.getSpeed().getX());
				
			}
			
		}
		
		if(p.getSpeed().getY() > 0.0f) {
			if(p.getPos().getY() < Main.display.world.mapHeight-Main.display.p.middleHeight-maxYoffset){
				
				p.getPos().setY(p.getPos().getY()+p.getSpeed().getY());
				
			}
			
		}
		

		if(p.getSpeed().getY() <= 0.0f) {
			
			if(p.getPos().getY() >= minYoffset){
				
				p.getPos().setY(p.getPos().getY()+p.getSpeed().getY());
				
			}
			
			
		}
			Main.display.player.updateHitBox();
				if(last.isDifferent(p) && Main.display.player.isKnownID()){
					Main.display.cth.sendRequest(new Command(Command.PLAYER_STAT, Main.display.player));
				}
		}
	}
	
	public static void render(Graphics2D g, Player p) {
		
		try{
				Main.display.world.canUpdate = false;
				if(Main.display.world.canRender){
				for(Bullet b : Main.display.world.getBulletList()) {
					BulletAdapter.render(g, b);
				}
				}
			Main.display.world.canUpdate = true;
		}catch(ConcurrentModificationException e) {
			e.printStackTrace();
		}
		AffineTransform at = g.getTransform();
		Main.display.p.angle = Main.display.p.calcAngle();
		int yoffset = 14; 
		if(p.getClassType() == Player.DUAL)yoffset = 0;
		else if(p.getClassType() == Player.DUAL_MACHINE)yoffset = 16;
		g.rotate(Main.display.p.calcAngle(), Main.display.p.width/2, Main.display.p.height/2+yoffset);
		if(p.getClassType() == Player.NOOB)g.drawImage(ResourceLoader.level_0_Green, Main.display.p.middleWidth, Main.display.p.middleHeight, 62, 89, null);
		else if(p.getClassType() == Player.DUAL)g.drawImage(ResourceLoader.dual_green, Main.display.p.middleWidth, Main.display.p.middleHeight, 62, 104, null);
		else if(p.getClassType() == Player.DUAL_MACHINE)g.drawImage(ResourceLoader.dual_machine, Main.display.p.middleWidth, Main.display.p.middleHeight, 100, 100, null);
		else if(p.getClassType() == Player.FACTORY)g.drawImage(ResourceLoader.factory, Main.display.p.middleWidth, Main.display.p.middleHeight, 100, 100, null);
		g.setTransform(at);
		
		float percent = p.getHealth()/p.getMaxHealth()*100.0f;
		if(percent != 100.0){
			
			float dwidth = (60*percent)/100; 
			Color c = null;
			if(percent <= 33)c = new Color(255, 0, 26);
			else if(percent > 33 && percent <= 66)c = new Color(255, 152, 0);
			else if(percent > 66)c = new Color(0, 204, 51);

			g.setColor(new Color(230, 230, 230));
			g.fillRoundRect(Main.display.p.getWidth()/2-30-1, Main.display.p.getHeight()/2-1+10, 61, 11, 11, 11);
			g.setColor(c);
			g.fillRoundRect(Main.display.p.getWidth()/2-30, Main.display.p.getHeight()/2+10, (int)dwidth, 9, 9, 9);
			
		}
		
		g.setColor(new Color(255, 102, 0));
		int offset = g.getFontMetrics().stringWidth(p.getName())/2;
		g.drawString(p.getName(), Main.display.p.getWidth()/2-offset, Main.display.p.getHeight()/2);
	}
	
	public static void flushInfo() {
		if(newBullets.size() != 0){
			synchronized(newBullets) {
				Main.display.cth.sendRequest(new Command(Command.NEW_BULLET, newBullets));
				newBullets.clear();
				newBullets.notify();
			}
		}
		if(diedBullets.size() != 0){
			synchronized(diedBullets) {
				Main.display.cth.sendRequest(new Command(Command.DIED_BULLET, diedBullets));
				diedBullets.clear();
				diedBullets.notify();
			}
		}
	}
	
	public static void renderOtherPlayers(Graphics2D g) {
		try{
		for(Player p : Main.display.world.getPlayerList()) {
//			if(p.getPos().getX()-Main.display.player.getPos().getX() > -124 && p.getPos().getX()-Main.display.player.getPos().getX() < Main.display.p.width && p.getPos().getY()-Main.display.player.getPos().getY() > -178 && p.getPos().getY()-Main.display.player.getPos().getY() < Main.display.p.height){
			AffineTransform at = g.getTransform();
			int xoffset = 606, yoffset = 301;
			
			if(p.getClassType() == Player.DUAL) {
				yoffset = 293;
			}
			
			
			
			float x = (p.getPos().getX()-Main.display.player.getPos().getX())+xoffset;
			float y = (p.getPos().getY()-Main.display.player.getPos().getY())+yoffset;
			int angleYoffset = 58;
			int angleXoffset = 31;
			
			
			if(p.getClassType() == Player.DUAL) {
				angleYoffset = 52;
				angleXoffset = 31;
			}else if(p.getClassType() == Player.DUAL_MACHINE) {
				angleXoffset = 50;
				angleYoffset = 65;
				
			}
			g.rotate(p.getAngle(), (int)x+angleXoffset, (int)y+angleYoffset);
			
			if(p.getClassType() == Player.NOOB)g.drawImage(ResourceLoader.level_0_Green, (int)x, (int)y, 62, 89, null);
			else if(p.getClassType() == Player.DUAL)g.drawImage(ResourceLoader.dual_green, (int)x, (int)y, 62, 104, null);
			else if(p.getClassType() == Player.DUAL_MACHINE)g.drawImage(ResourceLoader.dual_machine, (int)x, (int)y, 100, 100, null);
			
			g.setTransform(at);

			float percent = p.getHealth()/p.getMaxHealth()*100.0f;
			if(percent != 100.0){
				
				float dwidth = (60*percent)/100; 
				Color c = null;
				if(percent <= 33)c = new Color(255, 0, 26);
				else if(percent > 33 && percent <= 66)c = new Color(255, 152, 0);
				else if(percent > 66)c = new Color(0, 204, 51);

				g.setColor(new Color(230, 230, 230));
				g.fillRoundRect((int)x-1+20, (int)y-1+61, 61, 11, 11, 11);
				g.setColor(c);
				g.fillRoundRect((int)x+20, (int)y+61, (int)dwidth, 9, 9, 9);
				
			}
			
			
			g.setColor(new Color(255, 102, 0));
			int offset = g.getFontMetrics().stringWidth(p.getName())/2;
			g.drawString(p.getName(), (int)x-offset, (int)y-offset);
//			}
		}
		}catch(ConcurrentModificationException e) {
			e.printStackTrace();
		}
	}
	
	

}

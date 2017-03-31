package warfare.geometric.display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import gwl.entity.Player;
import gwl.math.Vector2;
import warfare.geometric.entity.WorldAdapter;
import warfare.geometric.main.Main;
import warfare.geometric.resource.ResourceLoader;

public class Panel extends JPanel{
	
	public int width, height, middleWidth = 0, middleHeight = 0, mouseX, mouseY;
	public double angle;

	public Panel() {
	}
	
	
	public void paintComponent(Graphics g1) {
		
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		RenderingHints rh1 = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		if(Main.display.listener.isInWindow){
		width = this.getWidth();
		height = this.getHeight();
		
		if(Main.display.player.getClassType() == Player.NOOB){
		middleWidth = width/2-31;
		middleHeight = height/2-44;
		}else if(Main.display.player.getClassType() == Player.DUAL){
			middleWidth = width/2-31;
			middleHeight = height/2-52;
		}else if(Main.display.player.getClassType() == Player.DUAL_MACHINE){
			middleWidth = width/2-50;
			middleHeight = height/2-50;
			}else if(Main.display.player.getClassType() == Player.FACTORY) {
				middleWidth = width/2-50;
				middleHeight = height/2-40;
			}
		try{
		mouseX = (int)this.getMousePosition().getX();
		mouseY = (int)this.getMousePosition().getY();
		}catch(NullPointerException e) {
			
		}
		Graphics2D g = (Graphics2D)g1;
		g.addRenderingHints(rh);
		g.addRenderingHints(rh1);
		g.setFont(new Font("Arial", 25, 25));
		WorldAdapter.render(g, Main.display.world);
		
		AffineTransform t = g.getTransform();
		g.rotate(calcAngle(), mouseX+11, mouseY+16);
		g.drawImage(ResourceLoader.cursor, mouseX, mouseY, 22, 32, null);
		g.setTransform(t);
		}
	}
	
	public void fillBackground(Color color, Graphics g) {
		
		g.setColor(color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
	}
	
	public double calcAngle() {
		double res = 0.0;
		int ab = 0;
		if(mouseX > width/2){
		ab = mouseX-(width/2);
		}else {
			ab = width/2-mouseX;
		}
		int bc = 0;
		if(mouseY > height/2)bc = mouseY-(height/2);
		else bc = height/2-mouseY;
		
		
		double ac = 0.0;
		ac = Math.sqrt(ab*ab+bc*bc);
		
		
		if(mouseY < height/2)res = Math.asin(ab/ac);
		else res = Math.asin(bc/ac)+Math.toRadians(90.0);
		
		if(mouseX < width/2)res = -res;
		return res;
	}
	
	
	public double convertVector2ToAngle(Vector2 v) {
		double d;
		
		d = 2*Math.atan(v.getY() / (v.getX() + 1));
		
		return d;
	}

}

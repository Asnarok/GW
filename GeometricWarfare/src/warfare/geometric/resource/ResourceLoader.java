package warfare.geometric.resource;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;

import unilib.sound.Audio;

public class ResourceLoader {
	
	public static final Image level_0_Green = loadImage("l0g.png");
	public static final Image tile = loadImage("tile.png");
	public static final Image dual_green = loadImage("dual.png");
	public static final Image dual_machine = loadImage("dual_machine.png");
	public static final Image grinder_body = loadImage("grinder_body.png");
	public static final Image grinder_wheel = loadImage("grinder_wheel.png");
	public static final Image gunner = loadImage("gunner.png");
	public static final Image hard_smasher = loadImage("hard_smasher.png");
	public static final Image lg_sniper = loadImage("lg_sniper.png");
	public static final Image machine_gun = loadImage("machine_gun.png");
	public static final Image octa = loadImage("octa.png");
	public static final Image overlord = loadImage("overlord.png");
	public static final Image overseer = loadImage("overseer.png");
	public static final Image pump = loadImage("pump.png");
	public static final Image quad = loadImage("quad.png");
	public static final Image rocket_gunner = loadImage("rocket_gunner.png");
	public static final Image smasher = loadImage("smasher.png");
	public static final Image sniper = loadImage("sniper.png");
	public static final Image tlg_sniper = loadImage("tlg_sniper.png");
	public static final Image tri_pump = loadImage("tri_pump.png");
	public static final Image factory = loadImage("factory.png");
	
	
	public static final Image bullet = loadImage("bullet.png");
	public static final Image los1 = loadImage("los1.png");
	public static final Image los2 = loadImage("los2.png");
	public static final Image pentagon = loadImage("pent.png");
	public static final Image triangle = loadImage("tr.png");
	public static final Image hexagon = loadImage("hxb.png");
	
	public static final Image cursor = loadImage("cursor.png");
	
	public static final Audio shoot = new Audio(getAudioPath("shoot.wav"));
	
	public static Image loadImage(String name) {
		Image image = null;
		
		try {
			image = ImageIO.read(Toolkit.getDefaultToolkit().getClass().getResourceAsStream("/warfare/geometric/resource/img/"+name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	public static String getAudioPath(String fileName) {
		return Toolkit.getDefaultToolkit().getClass().getResource("/warfare/geometric/resource/sound/"+fileName).getPath();
	}

}

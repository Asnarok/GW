package warfare.geometric.thread;

import warfare.geometric.display.Panel;
import warfare.geometric.entity.PlayerAdapter;
import warfare.geometric.main.Main;

public class FrameRate implements Runnable{

	private Panel pane;
	private boolean running = false;
	private Thread thread = new Thread(this);
	private int fps = 60;
	
	public FrameRate(Panel pane) {
		this.pane = pane;
	}

	@Override
	public void run() {
		
		long millis = System.currentTimeMillis();
		
		long Fm = System.currentTimeMillis();
		int tempFPS = 0;
		
		while(running) {
			
			if(System.currentTimeMillis()-millis >= 1000/fps) {
				
				if(Main.display.world.canRender())pane.repaint();
				tempFPS++;
				millis+=1000/fps;
			}else {
				
				try {
					Thread.sleep(1000/fps-(System.currentTimeMillis()-millis));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			if(System.currentTimeMillis()-Fm >= 1000) {
				Main.display.setTitle("Geometric Warfare "+tempFPS+" FPS");
				tempFPS = 0;
				System.gc();
				Fm+=1000;
			}
			
		
			
		}
		Main.display.update.stop();
		System.out.println("exit");
		Thread.currentThread().stop();
	}
	
	public void start() {
		running = true;
		thread.start();
	}
	
	public void stop() {
		running = false;
	}

}

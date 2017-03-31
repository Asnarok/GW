package warfare.geometric.thread;

import warfare.geometric.entity.PlayerAdapter;
import warfare.geometric.main.Main;

public class Updater implements Runnable {

	private boolean running = false;
	private Thread th;
	
	public Updater() {
		th = new Thread(this);
	}
	
	public void start() {
		running = true;
		th.start();
	}
	
	public void stop() {
		running = false;
	}

	@Override
	public void run() {
		long millis = System.currentTimeMillis();
		
		
		while(running) {
			
			if(System.currentTimeMillis()-millis >= 1000/60) {
				
				if(Main.display.world != null)Main.display.world.update();
				if(Main.display.player != null){
				PlayerAdapter.update(Main.display.player);
				PlayerAdapter.update(Main.display.player);
				}
				millis+=1000/60;
			}else {
				
				try {
					long m = 1000/60-(System.currentTimeMillis()-millis);
					if(m > 0)Thread.sleep(m);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		Thread.currentThread().stop();
	}

}

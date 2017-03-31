package warfare.geometric.net;

import warfare.geometric.entity.BulletAdapter;
import warfare.geometric.entity.PlayerAdapter;

public class InfoFlushThread implements Runnable {

	@Override
	public void run() {
		while(true) {
			
			
			BulletAdapter.flushInfo();
			PlayerAdapter.flushInfo();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

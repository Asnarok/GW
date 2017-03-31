package warfare.geometric.event;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import gwl.net.Command;
import warfare.geometric.main.Main;

public class Listener implements WindowListener, KeyListener, MouseListener {
	
	public boolean RIGHT = false, LEFT = false, UP = false, DOWN = false;
	public boolean isClicking = false, autoFire = false, isInWindow = false;

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		Main.display.cth.sendRequest(new Command(Command.DISCONNECT, null));
		Main.display.cth.stop();
		Main.display.stopFrameRate();
		
		System.exit(0);
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_Q && !RIGHT)LEFT = true;
		if(e.getKeyCode() == KeyEvent.VK_D && !LEFT)RIGHT = true;
		if(e.getKeyCode() == KeyEvent.VK_Z && !DOWN)UP = true;
		if(e.getKeyCode() == KeyEvent.VK_S && !UP)DOWN = true;
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Q)LEFT = false;
		if(e.getKeyCode() == KeyEvent.VK_D)RIGHT = false;
		if(e.getKeyCode() == KeyEvent.VK_Z)UP = false;
		if(e.getKeyCode() == KeyEvent.VK_S)DOWN = false;
		if(e.getKeyCode() == KeyEvent.VK_F)autoFire = !autoFire;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		isInWindow = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		isInWindow = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1)isClicking = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == 1)isClicking = false;
	}

}

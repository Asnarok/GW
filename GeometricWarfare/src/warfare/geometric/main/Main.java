package warfare.geometric.main;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import warfare.geometric.display.Frame;

public class Main {
	
	public static Frame display;

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				String i = JOptionPane.showInputDialog("Se connecter à G. W. | Pseudo");
				
				display = new Frame(i);
				
			}
		});
		
	}

}

package main;

import javax.swing.SwingUtilities;

public class Main {
	public static boolean DEBUG = true;
	
	public static void main(String []args){
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				Simulator s = new Simulator();
				s.start();
			}
			
		});
	}

}

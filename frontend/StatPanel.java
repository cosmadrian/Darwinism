package frontend;

import java.awt.Color;

import javax.swing.JPanel;

import entities.Entity;

public class StatPanel extends JPanel{
	
	private static final long serialVersionUID = -7815061674620254354L;

	public static final int WIDTH = 200;
	public static final int HEIGHT = 480;
	
	private Entity entity;

	public StatPanel(Entity e){
		this.entity = e;
		init();
	}
	
	private void init(){
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(MainFrame.WIDTH - WIDTH, 0);
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	private void printStats(){
		System.out.println(entity.toString());
	}
	
}
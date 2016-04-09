package frontend;

import java.awt.Color;

import javax.swing.JFrame;

import entities.Entity;
import input.UserInput;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = -854357705960199720L;
	
	public static final int HEIGHT = 480;
	public static final int WIDTH = 880;
	public static final String TITLE = "Darwinism Simulator V1.0[pre-alpha]";
	
	private UserInput input;
	private StatPanel stat;
	
	public MainFrame(){
		input = new UserInput(this);
		init();
		this.addMouseListener(input);
	}
	
	private void init(){
		this.setTitle(TITLE);
		this.setSize(WIDTH, HEIGHT);
		this.getContentPane().setBackground(Color.RED);;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public void showStat(Entity e){
		System.out.println("showing stat");
		this.stat = new StatPanel(e);
		this.add(stat);
		stat.addMouseListener(input);
		repaint();
	}
	
	public void removeStat(){
		System.out.println("removing stat");
		this.remove(stat);
		this.stat = null;
		repaint();
	}
}

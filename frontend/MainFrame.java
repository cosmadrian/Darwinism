package frontend;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import input.UserInput;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -854357705960199720L;

	public static final int HEIGHT = 480;
	public static final int WIDTH = 880;
	public static final String TITLE = "Darwinism Simulator V1.0[pre-alpha]";

	private UserInput input;
	private StatPanel stat;

	public MainFrame() {
		init();
	}

	private void init() {
		this.setTitle(TITLE);
		this.setSize(WIDTH, HEIGHT);
		this.setBackground(Color.WHITE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(true);
	}

	public void setInputHandler(UserInput i) {
		this.input = i;
		this.addMouseListener(i);
		this.addKeyListener(i);
		this.addMouseMotionListener(i);
	}

	public void addPanel(StatPanel j) {
		this.stat = j;

		this.add(stat);
		stat.addMouseListener(input);
		stat.setVisible(true);
	}
	
	public void render(Graphics g){
		stat.render(g);
	}
}

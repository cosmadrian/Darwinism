package frontend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -854357705960199720L;

	public static final int HEIGHT = 480;
	public static final int WIDTH = 880;
	public static final String TITLE = "Darwinism Simulator V1.0[pre-alpha]";

	private MouseListener input;
	private StatPanel stat;

	public MainFrame() {
		init();
	}

	private void init() {
		this.setTitle(TITLE);
		this.setSize(WIDTH, HEIGHT);
		this.setBackground(Color.GRAY);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
	}

	public void setInputHandler(MouseListener i) {
		this.input = i;
		this.addMouseListener(i);
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

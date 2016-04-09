package frontend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.Entity;
import main.Aggregator;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -854357705960199720L;

	public static final int HEIGHT = 480;
	public static final int WIDTH = 880;
	public static final String TITLE = "Darwinism Simulator V1.0[pre-alpha]";

	private MouseListener input;
	private JPanel stat;

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

	public void showStat(Entity e) {
		stat.setVisible(true);
		repaint();
	}

	public void removeStat() {
		stat.setVisible(false);
		repaint();
	}

	public void setInputHandler(MouseListener i) {
		this.input = i;
	}

	public void addPanel(JPanel j) {
		this.stat = j;

		this.add(stat);
		stat.addMouseListener(input);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		Aggregator.getInstance().renderMap(g2d);
		Aggregator.getInstance().renderEntities(g2d);
	}
}

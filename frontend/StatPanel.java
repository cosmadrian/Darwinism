package frontend;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import entities.Entity;
import main.Aggregator;

public class StatPanel extends JPanel {

	private static final long serialVersionUID = -7815061674620254354L;

	public static final int WIDTH = 200;
	public static final int HEIGHT = 480;

	public StatPanel() {
		init();
	}

	private void init() {
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(MainFrame.WIDTH - WIDTH, 0);
		this.setBackground(Color.WHITE);
		this.setLayout(null);
	}

	public String getStats() {
		Entity entity = Aggregator.getInstance().selectedEntity;
		
		if (entity != null)
			return entity.toString();
		return "";
	}

	@Override
	public void paint(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		
		int x = 10;
		int y = 20;
		for(String line : getStats().split("\n")){
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
		}
	}
	
}

package frontend;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import entities.Entity;
import main.Aggregator;

public class StatPanel extends JPanel {

	private static final long serialVersionUID = -7815061674620254354L;

	public static final int WIDTH = 200;
	public static final int HEIGHT = 480;

	private JLabel text = new JLabel();

	public StatPanel() {
		init();
	}

	private void init() {
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(MainFrame.WIDTH - WIDTH, 0);
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		
		this.add(text);
		text.setVisible(true);
		text.setBounds(0, 0, WIDTH, HEIGHT);
	}

	public String getStats() {
		Entity entity = Aggregator.getInstance().selectedEntity;

		if (entity != null)
			return entity.toString();
		return "";
	}

	public void render(Graphics g) {
		String formatted = "<html>" + getStats().replace("\n", "<br>") + "</html>";
		
		text.setText(formatted);
	}
	
}

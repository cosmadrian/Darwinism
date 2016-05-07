package frontend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entities.Entity;
import main.Aggregator;

public class StatPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = -7815061674620254354L;

	public static final int WIDTH = 200;
	public static final int HEIGHT = 480;

	private JLabel text = new JLabel();
	private JButton killButton = new JButton("Kill");

	public StatPanel() {
		init();
	}

	private void init() {
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(MainFrame.WIDTH - WIDTH, 0);
		this.setBackground(Color.WHITE);
		this.setLayout(null);

		this.add(killButton);
		killButton.setVisible(true);
		killButton.setBounds(20, HEIGHT - 50, 80, 30);
		killButton.addActionListener(this);

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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(Aggregator.getInstance().selectedEntity != null){
			Aggregator.getInstance().kill(Aggregator.getInstance().selectedEntity);
		}
	}

}

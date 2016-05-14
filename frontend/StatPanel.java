package frontend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entities.DNA;
import entities.Entity;
import entities.FemaleIndividual;
import exceptions.AlreadyPregnantException;
import main.Aggregator;
import main.Main;

public class StatPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -7815061674620254354L;
	private static final String FILE_NAME = "src/bg.png";
	private BufferedImage bg;

	public static final int WIDTH = 200;
	public static final int HEIGHT = 480;

	private JLabel text = new JLabel();
	private JButton killButton = new JButton("Kill");
	private JButton impregnateButton = new JButton("Impregnate");

	public StatPanel() {
		init();
		try {
			bg = Main.toBufferedImage(
					ImageIO.read(new File(FILE_NAME)).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() {
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(MainFrame.WIDTH - WIDTH, 0);
		this.setBackground(Color.WHITE);
		this.setLayout(null);

		this.add(killButton);
		killButton.setVisible(false);
		killButton.setBounds(20, HEIGHT - 50, 100, 30);
		killButton.addActionListener(this);

		this.add(impregnateButton);
		impregnateButton.setVisible(false);
		impregnateButton.setBounds(20, HEIGHT - 50 - 40, 100, 30);
		impregnateButton.addActionListener(this);

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
		g.drawImage(bg, MainFrame.WIDTH - WIDTH, 0, WIDTH, HEIGHT, null);
		String formatted = "<html>" + getStats().replace("\n", "<br>") + "</html>";
		text.setText(formatted);

		if (Aggregator.getInstance().selectedEntity != null) {
			this.killButton.setVisible(true);
			if (Aggregator.getInstance().selectedEntity instanceof FemaleIndividual) {
				this.impregnateButton.setVisible(true);
			} else {
				this.impregnateButton.setVisible(false);
			}
		} else {
			this.killButton.setVisible(false);
			this.impregnateButton.setVisible(false);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(killButton)) {
			if (Aggregator.getInstance().selectedEntity != null) {
				Aggregator.getInstance().kill(Aggregator.getInstance().selectedEntity);
			}
		} else if (e.getSource().equals(impregnateButton)) {
			if (Aggregator.getInstance().selectedEntity != null) {
				try {
					((FemaleIndividual) Aggregator.getInstance().selectedEntity)
							.impregnate(new DNA(DNA.generateStrand()));
				} catch (AlreadyPregnantException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}

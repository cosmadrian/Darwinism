package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import entities.Entity;
import entities.EntityBuilder;
import frontend.MainFrame;
import frontend.StatPanel;
import main.Main;

public class Map implements ActionListener {
	private BufferedImage bg = null;
	public Timer timer;

	public Map() {
		timer = new Timer(400, this);
		timer.start();
	}

	public void render(Graphics g) {
		if (bg == null) {
			try {
				bg = Main
						.toBufferedImage(Main.TransformColorToTransparency(
								ImageIO.read(new File("src/bg.png")).getScaledInstance(
										MainFrame.WIDTH - StatPanel.WIDTH, MainFrame.HEIGHT, Image.SCALE_DEFAULT),
						Color.green));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		g.drawImage(bg, 0, 0, bg.getWidth(), bg.getHeight(), null);
	}

	public void update() {
		
	}

	/* TODO: based on seed */
	public void spawnFood() {
		System.out.println("food spawned");
		EntityBuilder.getInstance().make(Entity.Type.FOOD, null, null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		double chance = new Random().nextDouble();
		double foodPerCent = (double) EntityBuilder.getInstance().foodCount
				/ (double) (EntityBuilder.getInstance().individualCount / 200.0
						+ EntityBuilder.getInstance().foodCount);
		double threshold = 1 - foodPerCent;

		if (chance < threshold) {
			spawnFood();
		}

	}

}

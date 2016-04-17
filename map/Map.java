package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import frontend.MainFrame;
import frontend.StatPanel;
import main.Main;

public class Map {
	private BufferedImage bg = null;

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
	
	public void update(){
		
	}

}

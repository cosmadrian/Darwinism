package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Main;
import screen.Screen;

public class Map {
	private BufferedImage bg = null;
	public static final int WIDTH = 4000;
	public static final int HEIGHT = 3000;

	private static final String BG_IMAGE = "src/bg.png";
	private Screen screen;

	public Map(Screen s) {
		setScreen(s);
	}

	public Map() {
		try {
			bg = Main.toBufferedImage(Main.TransformColorToTransparency(
					ImageIO.read(new File(BG_IMAGE)).getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT),
					Color.green));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setScreen(Screen s) {
		this.screen = s;
	}

	public void render(Graphics g) {

		try {
			BufferedImage cropped = bg.getSubimage(screen.getX(), screen.getY(), screen.getWidth(), screen.getHeight());
			g.drawImage(cropped, 0, 0, cropped.getWidth(), cropped.getHeight(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {

	}

	public int getWidth() {
		return bg.getWidth();
	}

	public int getHeight() {
		return bg.getHeight();
	}

}

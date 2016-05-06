package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

	public static final boolean DEBUG = false;

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Simulator s = new Simulator();
				s.start();
			}
		});
	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		return bimage;
	}

	public static Image TransformColorToTransparency(Image image, final Color color) {

		ImageFilter filter = new RGBImageFilter() {

			public int markerRGB = color.getRGB() | 0xFF000000;

			@Override
			public int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) { // alpha bits opaque
					return 0x00FFFFFF & rgb; // make alpha bits transparent
				} else {
					return rgb;
				}
			}
		};
		ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}

	public static Image TransformColorToColor(Image image, final Color targetColor, final Color endColor) {

		ImageFilter filter = new RGBImageFilter() {

			public int markerRGB = targetColor.getRGB();

			@Override
			public int filterRGB(int x, int y, int rgb) {
				if (rgb == markerRGB) { // alpha bits opaque
					return endColor.getRGB(); // make alpha bits transparent
				} else {
					return rgb;
				}
			}
		};
		ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
		return Toolkit.getDefaultToolkit().createImage(ip);
	}

}

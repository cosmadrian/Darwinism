package screen;

import java.awt.Point;

import frontend.MainFrame;
import frontend.StatPanel;

public class Screen {

	private int xOffset = 0;
	private int yOffset = 0;
	private int maxWidth, maxHeight;

	public Screen(int maxWidth, int maxHeight) {
		this.maxHeight = maxHeight;
		this.maxWidth = maxWidth;
	}

	public int getX() {
		return xOffset;
	}

	public final int getHeight() {
		return MainFrame.HEIGHT;
	}

	public int getWidth() {
		return MainFrame.WIDTH - StatPanel.WIDTH;
	}

	public int getY() {
		return yOffset;
	}

	public void addXOffset(int x) {
		xOffset += x;
		if (xOffset < 0)
			xOffset = 0;
		if (xOffset > maxWidth - getWidth())
			xOffset = maxWidth - getWidth();
	}

	public void addYOffset(int y) {
		yOffset += y;
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > maxHeight - getHeight())
			yOffset = maxHeight - getHeight();
	}

	public boolean contains(Point point) {
		if ((point.x > xOffset && point.x < xOffset + getWidth())
				&& (point.y > yOffset && point.y < yOffset + getHeight())) {
			return true;
		}
		return false;
	}

}

package input;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Aggregator;

public class UserInput implements MouseListener, KeyListener, MouseMotionListener {

	private Point pressedPoint;
	private Point dragStart, dragEnd;

	public UserInput() {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Aggregator.getInstance().selectedEntity = Aggregator.getInstance().getEntityByPosition(e.getPoint());
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressedPoint = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressedPoint = null;
		dragStart = null;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_DOWN:
			break;
		case KeyEvent.VK_LEFT:
			break;
		case KeyEvent.VK_RIGHT:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (dragStart == null) {
			dragStart = pressedPoint;
		}

		dragEnd = e.getPoint();

		double x = dragEnd.getX() - dragStart.getX();
		double y = dragEnd.getY() - dragStart.getY();

		dragStart = dragEnd;

		Aggregator.getInstance().getScreen().addXOffset((int) -x);
		Aggregator.getInstance().getScreen().addYOffset((int) -y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}

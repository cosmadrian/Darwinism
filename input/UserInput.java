package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.Aggregator;

public class UserInput implements MouseListener {

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

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}

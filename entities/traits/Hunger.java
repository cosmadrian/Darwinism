package entities.traits;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Hunger extends Trait implements ActionListener{

	private Timer timer;
	private int rate = 1000; //ms
	
	
	public Hunger(){
		timer = new Timer(rate,this);
		timer.start();
	}
	
	@Override
	public String getName() {
		return "Hunger";
	}

	@Override
	public void update() {
		if(value == 0)
			timer.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//this.value --;
	}
}

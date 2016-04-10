package main;

import entities.EntityBuilder;
import frontend.MainFrame;
import frontend.StatPanel;
import input.UserInput;

public class Simulator implements Runnable {

	private boolean running = false;
	private MainFrame frame;

	public Simulator() {
		init();
	}

	public void start() {
		this.running = true;

		Thread t = new Thread(this);
		t.start();
	}

	public void stop() {
		this.running = false;
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
			while (delta >= 1) {
				ticks++;
				update();
				delta--;
				shouldRender = true;
			}
			try {
				Thread.sleep(15);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				render();
				frames++;
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				// System.out.println(frames + " frames ; " + ticks + " ticks");
				lastTimer += 1000;
				frames = 0;
				ticks = 0;
			}

		}
	}

	public void update() {
		Aggregator.getInstance().updateEntities();
	}

	public void render() {
		frame.repaint();
	}

	public void init() {
		frame = new MainFrame();
		frame.setVisible(true);
		frame.setInputHandler(new UserInput());
		frame.addPanel(new StatPanel());

		Aggregator.getInstance().setEntities(EntityBuilder.getInstance().populate(50));
		Aggregator.getInstance().addEntities(EntityBuilder.getInstance().cookFood(50));

	}

}

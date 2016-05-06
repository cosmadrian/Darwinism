package entities.states;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import javax.swing.Timer;

import entities.Food;
import entities.Individual;
import entities.traits.Trait;

public class IdleState extends State implements ActionListener {

	public static final Color MALE = new Color(0x4A4AFF);
	public static final Color FEMALE = new Color(0xFF5757);

	public IdleState(Individual e) {
		super(e);
		timer = new Timer(1000, this);

	}

	@Override
	public void update() {
		if (!timer.isRunning()) {
			timer.start();
		} else {
			source.setVx(0);
			source.setVy(0);
		}

		Tuple<Double, Object> eating, moving;
		eating = getEatingTuple();
		moving = getMovingTuple();

		weightedStates.put(new Tuple<StateType, Object>(StateType.EATING, eating.second), eating.first);
		weightedStates.put(new Tuple<StateType, Object>(StateType.MOVING, moving.second), moving.first);
		weightedStates.put(new Tuple<StateType, Object>(StateType.IDLE, null), 1 - eating.first - moving.first);

		nextState = super.getNextState(weightedStates);
		
	}

	@Override
	public String getName() {
		return "IDLE";
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (nextState.first != StateType.IDLE) {
			source.setState(nextState.first, nextState.second);
			timer.stop();
		}
	}

	private Tuple<Double, Object> getEatingTuple() {
		Tuple<Double, Object> eating;

		ArrayList<Food> foods = source.getNearbyFood();

		if (foods.size() == 0) {
			return new Tuple<Double, Object>(0.0, null);
		}

		PriorityQueue<Tuple<Double, Food>> distances = new PriorityQueue<Tuple<Double, Food>>(1,
				new Comparator<Tuple<Double, Food>>() {

					@Override
					public int compare(Tuple<Double, Food> t1, Tuple<Double, Food> t2) {
						if (t1.first - t2.first < 0) {
							return -1;
						} else if (t1.first - t2.first > 0) {
							return 1;
						}

						return 0;
					}

				});
		for (Food f : foods) {
			distances.add(new Tuple<Double, Food>(
					Math.sqrt(Math.pow(source.getX() - f.getX(), 2) + Math.pow(source.getY() - f.getY(), 2)), f));
		}

		if (distances.peek().first <= Individual.RANGE) {

			eating = new Tuple<Double, Object>((double) (100 - source.getTrait(Trait.Type.HUNGER).getValue()) / 100.0,
					distances.peek().second);

			return eating;

		} else {
			return new Tuple<Double, Object>(0.0, null);
		}
	}

	private Tuple<Double, Object> getMovingTuple() {
		Tuple<Double, Object> moving = new Tuple<Double, Object>(0.0, null);

		return moving;
	}

	@Override
	public void clean() {

	}

	@Override
	public State withOption(Object option) {
		return this;
	}

}

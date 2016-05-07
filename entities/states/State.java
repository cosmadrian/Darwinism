package entities.states;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

import javax.swing.Timer;

import entities.Food;
import entities.Individual;
import entities.traits.Trait;

public abstract class State {

	public static final Color MALE = Color.BLUE;
	public static final Color FEMALE = Color.RED;

	protected Individual source;
	protected Tuple<StateType, Object> nextState;
	protected Timer timer;
	protected Object option;

	protected PriorityQueue<Tuple<Double, Food>> foodDistances = new PriorityQueue<Tuple<Double, Food>>(1,
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

	protected HashMap<Tuple<StateType, Object>, Double> weightedStates;

	public State(Individual e) {
		this.source = e;
		weightedStates = new HashMap<Tuple<StateType, Object>, Double>();
	}

	public abstract void update();

	public abstract void clean();

	public abstract State withOption(Object option);

	public abstract String getName();

	public static Tuple<StateType, Object> getNextState(HashMap<Tuple<StateType, Object>, Double> weightedStates) {
		Random r = new Random();
		double chance;
		double max = 0;

		for (Double c : weightedStates.values()) {
			max += c;
		}
		chance = r.nextDouble() * max;

		for (Tuple<StateType, Object> t : weightedStates.keySet()) {
			if (chance < weightedStates.get(t)) {
				return t;
			}
			chance -= weightedStates.get(t);
		}

		System.out.println("YOU CANT REACH ME");
		return null;
	}

	protected void getFoodDistances() {
		ArrayList<Food> foods = source.getNearbyFood(Individual.LOS);
		for (Food f : foods) {
			foodDistances.add(new Tuple<Double, Food>(
					Math.sqrt(Math.pow(source.getX() - f.getX(), 2) + Math.pow(source.getY() - f.getY(), 2)), f));
		}
	}

	protected Tuple<Double, Object> getEatingTuple() {
		Tuple<Double, Object> eating;

		if (foodDistances.size() == 0) {
			return new Tuple<Double, Object>(0.0, null);
		}

		if (foodDistances.peek().first <= Individual.RANGE) {

			eating = new Tuple<Double, Object>((double) (100 - source.getTrait(Trait.Type.HUNGER).getValue()) / 100.0,
					foodDistances.peek().second);

			return eating;

		} else {
			return new Tuple<Double, Object>(0.0, null);
		}
	}

	protected Tuple<Double, Object> getMovingTuple() {
		Tuple<Double, Object> moving;

		Random r = new Random();

		int p1 = source.getX() + r.nextInt(10) - 5;
		int p2 = source.getY() + r.nextInt(10) - 5;
		double direction = getAngleBetween(new Point(source.getX(), source.getY()), new Point(p1, p2));

		double staminaChance = (double) source.getTrait(Trait.Type.STAMINA).getValue() / 100.0;
		moving = new Tuple<Double, Object>(staminaChance, direction);

		return moving;
	}

	protected Tuple<Double, Object> getMovingToEatTuple() {
		Tuple<Double, Object> movingToEat;

		if (foodDistances.size() == 0) {
			return new Tuple<Double, Object>(0.0, null);
		}

		Food closestFood = foodDistances.peek().second;

		if (foodDistances.peek().first <= Individual.RANGE) {
			return new Tuple<Double, Object>(0.0, null);
		}

		double hungerChance = (double) (100 - source.getTrait(Trait.Type.HUNGER).getValue()) / 100.0;
		movingToEat = new Tuple<Double, Object>(hungerChance, closestFood);

		return movingToEat;
	}

	protected Tuple<Double, Object> getCallingTuple() {
		double callingChance = (double) (source.getTrait(Trait.Type.FERTILITY).getValue()) / 150.0;

		return new Tuple<Double, Object>(callingChance, null);
	}

	protected Tuple<Double, Object> getMovingToMateTuple() {
		double matingChance = (double) (source.getTrait(Trait.Type.FERTILITY).getValue()) / 100.0;

		ArrayList<Individual> mates = source.getPotentialMates();
		int maxFertility = Integer.MIN_VALUE;
		Individual target = null;

		if (mates.size() == 0) {
			return new Tuple<Double, Object>(0.0, null);
		}

		for (Individual i : mates) {
			if (maxFertility < i.getTrait(Trait.Type.FERTILITY).getValue()) {
				maxFertility = i.getTrait(Trait.Type.FERTILITY).getValue();
				target = i;
			}
		}
		return new Tuple<Double, Object>(matingChance, target);
	}

	protected double getAngleBetween(Point p1, Point p2) {
		double angle = (double) Math.atan2(p1.x - p2.x, p2.y - p1.y) + Math.PI / 2;
		return angle;
	}

}

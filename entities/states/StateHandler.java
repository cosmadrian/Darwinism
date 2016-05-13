package entities.states;

import entities.FemaleIndividual;
import entities.Individual;

public class StateHandler {

	private Individual source;
	private State idleState, movingState, eatingState, matingState, callingState, fightingState, fleeingState,
			movingWithGoal;

	private State state;
	private StateType stateType;

	public StateHandler(Individual individual) {
		this.source = individual;
		initStates();

		this.state = idleState;
		this.stateType = StateType.IDLE;
	}

	private void initStates() {
		idleState = new IdleState(source);
		movingState = new MovingState(source);
		eatingState = new EatingState(source);
		matingState = new MatingState(source);
		callingState = new CallingState(source);
		fightingState = new FightingState(source);
		fleeingState = new FleeingState(source);
		movingWithGoal = new GoalMoveState(source);
	}

	public void update() {
		state.update();
	}

	public StateType getStateType() {
		return stateType;
	}

	public void setState(StateType t, Object option) {
		if (source instanceof FemaleIndividual) {
			if (((FemaleIndividual) source).isPregnant()) {
				if (t == StateType.CALLING || t == StateType.MATING) {
					this.setState(StateType.IDLE, null);
					return;
				}
			}
		}

		this.stateType = t;
		this.state.clean();

		switch (t) {
		case MOVING:
			this.state = movingState;
			break;
		case EATING:
			this.state = eatingState;
			break;
		case MATING:
			this.state = matingState;
			break;
		case CALLING:
			this.state = callingState;
			break;
		case FIGHTING:
			this.state = fightingState;
			break;
		case IDLE:
			this.state = idleState;
			break;
		case FLEEING:
			this.state = fleeingState;
			break;
		case MOVING_WITH_GOAL:
			this.state = movingWithGoal;
			break;
		}

		state.withOption(option);

	}

	public State getState() {
		return state;
	}

}

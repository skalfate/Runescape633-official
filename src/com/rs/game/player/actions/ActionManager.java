package com.rs.game.player.actions;

import java.util.Optional;

import com.rs.game.player.Player;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Handles an Action that a Player creates
 * @author Dennis
 *
 */
@Data
public final class ActionManager {
	
	private final transient Player player;
	
	public ActionManager(Player player) {
		action = Optional.empty();
		this.player = player;
	}
	
	/**
	 * The Action the Player creates
	 */
	@Getter
	private Optional<Action> action;
	
	/**
	 * The Action delay
	 */
	@Getter
	@Setter
	private int actionDelay;

	/**
	 * Handles the processing of an Action
	 */
	public void process() {
		if (getAction().isPresent() && !getAction().get().process(player)) {
			forceStop();	
		}
		if (!getAction().isPresent()) {
			return;
		}
		if (actionDelay > 0) {
			actionDelay--;
			return;
		}
		int delay = getAction().get().processWithDelay(player);
		if (delay == -1) {
			forceStop();
			return;
		}
		addActionDelay(actionDelay += delay);
	}

	/**
	 * Sets the Action that the Player has requested
	 * @param actionEvent
	 * @return action
	 */
	public void setAction(Action actionEvent) {
		if (!actionEvent.start(player))
			return;
		action = Optional.of(actionEvent);
	}

	/**
	 * Forcivly stops the Players current Action
	 */
	public void forceStop() {
		if (getAction().isPresent() && getAction().get() instanceof WineTask){
			return;
		}
		getAction().ifPresent(presentAction -> presentAction.stop(player));
		action = Optional.empty();
	}

	/**
	 * Adds an additional delay to the action delay
	 * @param delay
	 */
	public void addActionDelay(int delay) {
		this.actionDelay += delay;
	}
	
	public boolean getPresentAction(Class<?> action, Runnable doFunction) {
		if (getAction().isPresent() && action.isInstance(getAction().get())) {
			doFunction.run();
			return true;
		}
		return false;
	}
}
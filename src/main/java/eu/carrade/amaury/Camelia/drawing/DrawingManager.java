package eu.carrade.amaury.Camelia.drawing;

import eu.carrade.amaury.Camelia.Camelia;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;


public class DrawingManager {

	/**
	 * The players currently right-clicking
	 */
	private Set<UUID> rightClickingPlayers = new CopyOnWriteArraySet<>();


	public DrawingManager() {

		new FollowDrawerCursorTask().runTaskTimer(Camelia.getInstance(), 10l, 1l);

	}


	public void setRightClicking(UUID id, boolean rightClicking) {
		if(rightClicking) {
			rightClickingPlayers.add(id);
		} else {
			rightClickingPlayers.remove(id);
		}
	}

	public boolean isRightClicking(UUID id) {
		return rightClickingPlayers.contains(id);
	}

	public Set<UUID> getRightClickingPlayers() {
		return rightClickingPlayers;
	}

}

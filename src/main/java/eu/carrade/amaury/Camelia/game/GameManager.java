package eu.carrade.amaury.Camelia.game;



import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameManager {

	Map<UUID,Drawer> drawers = new HashMap<>();

	public GameManager() {
		// Something very useful here. Soon™.
	}



	/** *** Players management *** **/


	/**
	 * Registers a new player in the game.
	 *
	 * @param id The UUID of the player.
	 *
	 * @return The new Drawer object just created.
	 */
	public Drawer registerNewDrawer(UUID id) {
		if(!drawers.containsKey(id)) {
			Drawer drawer = new Drawer(id);

			drawers.put(id, drawer);
			return drawer;
		}
		else {
			return getDrawer(id);
		}
	}

	/**
	 * Returns the drawer with that UUID.
	 *
	 * @param id The ID.
	 *
	 * @return The drawer.
	 */
	public Drawer getDrawer(UUID id) {
		return drawers.get(id);
	}
}

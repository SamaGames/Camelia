package eu.carrade.amaury.Camelia.game;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.carrade.amaury.Camelia.Camelia;
import net.samagames.api.games.IManagedGame;
import net.samagames.api.games.Status;
import net.samagames.api.games.StatusEnum;

public class GameManager implements IManagedGame {

	private final Map<UUID,Drawer> drawers = new HashMap<>();
	private Status status = Status.WAITING_FOR_PLAYERS;

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
	
	public void unregisterDrawer(UUID id) {
		drawers.remove(id);
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

	

	@Override
	public int getMaxPlayers() {
		return Camelia.getInstance().getArenaConfig().getInt("game.maxPlayers");
	}

	// @Override
	// VIP thing ???
	
	@Override
	public int getConnectedPlayers() {
		return drawers.size();
	}

	@Override
	public String getMapName() {
		return Camelia.getInstance().getArenaConfig().getString("game.name");
	}

	@Override
	public String getGameName() {
		return Camelia.NAME_WHITE;
	}

	@Override
	public void playerJoin(final Player player) {
		registerNewDrawer(player.getUniqueId()).fillInventory();

		Bukkit.getScheduler().runTaskLaterAsynchronously(Camelia.getInstance(), new Runnable() {
			@Override
			public void run() {
				Camelia.getInstance().getWhiteboard().sendAllWhitebord(player);
			}
		}, 20l);
	}

	@Override
	public void playerDisconnect(Player player) {
		unregisterDrawer(player.getUniqueId());
	}

	@Override
	public Status getStatus() {
		return status;
	}
	
	@Override
	public void setStatus(Status arg) {
		status = arg;
	}

	@Override
	public void startGame() {
	}
}

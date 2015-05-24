package eu.carrade.amaury.Camelia.game;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Drawer {

	private UUID playerID;

	private boolean drawing = false;

	public Drawer(UUID playerID) {
		this.playerID = playerID;
	}

	/**
	 * The player's UUID
	 *
	 * @return The UUID
	 */
	public UUID getPlayerID() {
		return playerID;
	}

	/**
	 * The Player object
	 *
	 * @return The object. May be null.
	 */
	public Player getPlayer() {
		return Bukkit.getPlayer(playerID);
	}

	/**
	 * Is this player online?
	 *
	 * @return True if online
	 */
	public boolean isOnline() {
		return Bukkit.getOfflinePlayer(playerID).isOnline();
	}

	/**
	 * Is this player currently drawing?
	 *
	 * @return True if he is drawing
	 */
	public boolean isDrawing() {
		//return drawing;
		return true; // TODO testing purposes only.
	}

	/**
	 * Set the drawing status
	 *
	 * @param drawing True if he is drawing
	 */
	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}
}

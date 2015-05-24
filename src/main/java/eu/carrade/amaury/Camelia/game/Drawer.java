package eu.carrade.amaury.Camelia.game;


import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.DrawTool;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

	/**
	 * Called when a player use a tool. Calls the good methods of the tool.
	 *
	 * @param target The targeted location on the screen.
	 */
	public void drawABlock(Location target) {
		DrawTool tool = Camelia.getInstance().getDrawingManager().getActivePlayerTool(this);

		if(tool != null) {
			tool.onRightClick(target, this);
		}
	}
}

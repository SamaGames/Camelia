package eu.carrade.amaury.Camelia.game;


import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorYellow;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.DrawTool;

public class Drawer {

	private final UUID playerID;

	private boolean drawing = false;
	
	private PixelColor color = new ColorYellow(ColorType.BASIC);

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
	 * Returns the current color of this drawer
	 *
	 * @return The color
	 */
	public PixelColor getColor() {
		return this.color;
	}

	/**
	 * Updates the current color of this drawer
	 *
	 * @param color The new color
	 */
	public void setColor(PixelColor color) {
		this.color = color;
	}

	/**
	 * Returns the currently active tool of this drawer.
	 *
	 * @return The tool, or {@code null} if there is no active tool,
	 * the player is null, not currently drawing, or disconnected.
	 */
	public DrawTool getActiveTool() {
		if(!isOnline() || !isDrawing()) return null;

		Player player = getPlayer();
		if(player == null) return null; // Just to be sure

		return Camelia.getInstance().getDrawingManager().getDrawTools().get(getPlayer().getInventory().getHeldItemSlot());
	}

	/**
	 * Updates the inventory of this player with the good content (draw tools if
	 * drawing; empty else).
	 */
	public void fillInventory() {
		Player player = getPlayer();

		player.getInventory().clear();

		if(isDrawing()) {
			for(int i = 0; i < 9; i++) {
				DrawTool tool = Camelia.getInstance().getDrawingManager().getDrawTools().get(i);
				if(tool != null)
					player.getInventory().setItem(i, tool.constructIcon(this));
			}
		}
	}
}

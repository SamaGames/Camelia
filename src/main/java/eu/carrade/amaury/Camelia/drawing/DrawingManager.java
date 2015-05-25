package eu.carrade.amaury.Camelia.drawing;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.DrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.UndoTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.*;
import eu.carrade.amaury.Camelia.game.Drawer;
import eu.carrade.amaury.Camelia.utils.Utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;


public class DrawingManager {

	/**
	 * The players currently right-clicking
	 */
	private Set<UUID> rightClickingPlayers = new CopyOnWriteArraySet<>();

	/**
	 * The draw tools.
	 *
	 * Map: slot -> tool
	 */
	private Map<Integer,DrawTool> drawTools = new HashMap<>();



	public DrawingManager() {

		registerDrawTool(new BrushTool());
		registerDrawTool(new SprayTool());
		registerDrawTool(new FillRegionTool());
		registerDrawTool(new PaintingsTools());
		registerDrawTool(new ColorChooserTool());
		registerDrawTool(new UndoTool());
		registerDrawTool(new ClearTool());

		new FollowDrawerCursorTask().runTaskTimer(Camelia.getInstance(), 10l, 1l);
	}


	/* *** Tools management *** */

	/**
	 * Registers a draw tool.
	 *
	 * @param tool The tool.
	 */
	private void registerDrawTool(DrawTool tool) {
		drawTools.put(Utils.getDrawToolRealSlot(tool), tool);
	}

	/**
	 * Returns the player's active draw tool.
	 *
	 * Warning - this method will NOT check if a player is currently in a
	 * drawing state, for performance reasons. Use {@link #getActivePlayerTool(Drawer)}
	 * if you want this check.
	 *
	 * @param player The player.
	 *
	 * @return The tool, or {@code null} if there is no active tool,
	 * the player is null, or disconnected.
	 */
	public DrawTool getActivePlayerTool(Player player) {
		if(player == null || !player.isOnline()) return null;

		return drawTools.get(player.getInventory().getHeldItemSlot());
	}

	/**
	 * Returns the player's active draw tool.
	 *
	 * @param drawer The drawer.
	 *
	 * @return The tool, or {@code null} if there is no active tool,
	 * the player is null, not currently drawing, or disconnected.
	 */
	public DrawTool getActivePlayerTool(Drawer drawer) {
		if(drawer == null || !drawer.isOnline() || !drawer.isDrawing()) return null;

		return getActivePlayerTool(drawer.getPlayer());
	}


	/* ***  Right-clicking players tracking  *** */

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

	public Map<Integer, DrawTool> getDrawTools() {
		return drawTools;
	}
}

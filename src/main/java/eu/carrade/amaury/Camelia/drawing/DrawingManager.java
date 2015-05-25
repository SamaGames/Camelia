package eu.carrade.amaury.Camelia.drawing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

import org.bukkit.entity.Player;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.DrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.BrushTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.ClearTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.ColorChooserTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.FillRegionTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.PaintingsTools;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.SprayTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.UndoTool;
import eu.carrade.amaury.Camelia.game.Drawer;
import eu.carrade.amaury.Camelia.utils.Utils;


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

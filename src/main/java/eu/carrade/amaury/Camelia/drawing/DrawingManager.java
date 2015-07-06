package eu.carrade.amaury.Camelia.drawing;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.DrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ToolLocator;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.BrushTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.ClearTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.ColorChooserTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.FillRegionTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.PaintingsTools;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.SprayTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.UndoTool;
import org.apache.commons.lang.Validate;

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
	 * The draw tools. <p/> Map: slot -> tool
	 */
	private Map<Integer, Class<? extends DrawTool>> drawTools = new HashMap<>();


	public DrawingManager() {

		registerDrawTool(BrushTool.class);
		registerDrawTool(SprayTool.class);
		registerDrawTool(FillRegionTool.class);
		registerDrawTool(PaintingsTools.class);
		registerDrawTool(ColorChooserTool.class);
		registerDrawTool(UndoTool.class);
		registerDrawTool(ClearTool.class);

		new FollowDrawerCursorTask().runTaskTimer(Camelia.getInstance(), 10l, 1l);
	}


	/* *** Tools management *** */

	/**
	 * Registers a draw tool.
	 *
	 * @param tool The tool.
	 */
	private void registerDrawTool(Class<? extends DrawTool> tool) {
		ToolLocator locator = tool.getAnnotation(ToolLocator.class);

		Validate.notNull(locator, "Cannot register the tool " + tool.getSimpleName() + ": @ToolLocator annotation not found!");

		drawTools.put(locator.slot(), tool);
	}


	/* ***  Right-clicking players tracking  *** */

	public void setRightClicking(UUID id, boolean rightClicking) {
		if (rightClicking) {
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

	public Map<Integer, Class<? extends DrawTool>> getDrawTools() {
		return drawTools;
	}
}

package eu.carrade.amaury.Camelia.drawing;

import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.*;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.*;
import org.apache.commons.lang.*;

import java.util.*;
import java.util.concurrent.*;

/*
 * This file is part of Camelia.
 *
 * Camelia is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Camelia is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Camelia.  If not, see <http://www.gnu.org/licenses/>.
 */
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

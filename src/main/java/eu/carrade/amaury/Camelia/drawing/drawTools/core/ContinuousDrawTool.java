package eu.carrade.amaury.Camelia.drawing.drawTools.core;

import eu.carrade.amaury.Camelia.game.Drawer;

/**
 * Represents a tool that needs to be applied on every block targeted by the player.
 *
 * The “right-click” method of this tool will be called for points explicitly targeted,
 * and for each points on a line between the last known position and the current one,
 * to get a continuous line (only when the player is continuously right-clicking, of course).
 */
public abstract class ContinuousDrawTool extends DrawTool {

	public ContinuousDrawTool(Drawer drawer) {
		super(drawer);
	}

}

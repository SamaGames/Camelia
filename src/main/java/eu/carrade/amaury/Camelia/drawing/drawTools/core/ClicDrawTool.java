package eu.carrade.amaury.Camelia.drawing.drawTools.core;

import eu.carrade.amaury.Camelia.game.Drawer;

/**
 * Represents a tool that will be executed only when the player explicitly right-clicks.
 */
public abstract class ClicDrawTool extends DrawTool {

	public ClicDrawTool(Drawer drawer) {
		super(drawer);
	}

}

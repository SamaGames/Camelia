package eu.carrade.amaury.Camelia.drawing.drawTools.core;

import eu.carrade.amaury.Camelia.game.Drawer;


/**
 * Represents a tool that needs to be applied on every block targeted by the player. <p/> The “right-click” method of
 * this tool will be called for points explicitly targeted, and for each points on a line between the last known
 * position and the current one, to get a continuous line (only when the player is continuously right-clicking, of
 * course).
 */
public abstract class ContinuousDrawTool extends DrawTool {

	protected int size = 1;
	protected boolean mixColors = false;

	public ContinuousDrawTool(Drawer drawer) {
		super(drawer);
	}

	/**
	 * Sets the size of the brush
	 *
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the size of the brush
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return true if the colors should be mixed
	 */
	public boolean isMixColors() {
		return mixColors;
	}

	/**
	 * If the colors should be mixed
	 *
	 * @param mixColors
	 */
	public void setMixColors(boolean mixColors) {
		this.mixColors = mixColors;
	}
}

package eu.carrade.amaury.Camelia.drawing.drawTools.core;

import eu.carrade.amaury.Camelia.game.*;

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

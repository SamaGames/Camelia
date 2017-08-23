package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.*;
import org.bukkit.*;

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
public class ColorGray extends PixelColor {

	public ColorGray(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.GRAY), new GameBlock(Material.STAINED_CLAY, DyeColor.GRAY), new GameBlock(Material.BEDROCK));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Gris";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Gris";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Gris Terrestre";
	}
}

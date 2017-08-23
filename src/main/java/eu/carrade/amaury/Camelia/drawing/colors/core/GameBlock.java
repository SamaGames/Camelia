package eu.carrade.amaury.Camelia.drawing.colors.core;

import org.bukkit.*;
import org.bukkit.inventory.*;

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
public class GameBlock {

	private final Material type;
	private final byte data;

	public GameBlock(Material type) {
		this(type, (byte) 0);
	}

	public GameBlock(Material type, DyeColor color) {
		this(type, type == Material.WOOL ? color.getWoolData() : color.getData());
	}

	public GameBlock(Material type, byte data) {
		this.type = type;
		this.data = data;
	}

	public Material getType() {
		return type;
	}

	public byte getData() {
		return data;
	}

	public ItemStack toItemStack(int amount) {
		return new ItemStack(type, amount, data);
	}
}

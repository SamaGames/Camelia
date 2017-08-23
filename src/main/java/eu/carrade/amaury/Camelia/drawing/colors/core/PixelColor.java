package eu.carrade.amaury.Camelia.drawing.colors.core;

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
public abstract class PixelColor {

	protected final DyeColor color;
	protected final GameBlock basicBlock;
	protected final GameBlock betterBlock;
	protected final GameBlock roughBlock;
	protected ColorType type = ColorType.BASIC;

	public PixelColor(GameBlock type1, GameBlock type2, GameBlock type3) {
		this.basicBlock = type1;
		this.color = DyeColor.getByData(type1.getData());
		this.betterBlock = type2;
		this.roughBlock = type3;
	}

	public PixelColor(GameBlock type1, GameBlock type2, GameBlock type3, DyeColor color) {
		this.basicBlock = type1;
		this.betterBlock = type2;
		this.roughBlock = type3;
		this.color = color;
	}

	public GameBlock getBasicBlock() {
		return basicBlock;
	}

	public abstract String getBasicDisplayName();

	public GameBlock getBetterBlock() {
		return betterBlock;
	}

	public abstract String getBetterDisplayName();

	public GameBlock getRoughBlock() {
		return roughBlock;
	}

	public abstract String getRoughDisplayName();

	public DyeColor getDyeColor() {
		return DyeColor.getByData(this.basicBlock.getData());
	}

	public String getDisplayName() {
		if (type == ColorType.BASIC) {
			return getBasicDisplayName();
		} else if (type == ColorType.BETTER) {
			return getBetterDisplayName();
		} else if (type == ColorType.ROUGH) {
			return getRoughDisplayName();
		}
		return getBasicDisplayName();
	}

	public GameBlock getBlock() {
		if (type == ColorType.BASIC) {
			return getBasicBlock();
		} else if (type == ColorType.BETTER) {
			return getBetterBlock();
		} else if (type == ColorType.ROUGH) {
			return getRoughBlock();
		}
		return getBasicBlock();
	}

	public ColorType getType() {
		return type;
	}

	public void setColorType(ColorType type) {
		this.type = type;
	}
}

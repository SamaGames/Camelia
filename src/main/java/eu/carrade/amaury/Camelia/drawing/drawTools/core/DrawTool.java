package eu.carrade.amaury.Camelia.drawing.drawTools.core;

import eu.carrade.amaury.Camelia.drawing.whiteboard.*;
import eu.carrade.amaury.Camelia.game.*;
import eu.carrade.amaury.Camelia.utils.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

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
public abstract class DrawTool {

	protected final Drawer drawer;


	public DrawTool(Drawer drawer) {
		this.drawer = drawer;
	}


	/**
	 * Returns the display name of the tool. Used as the display name of the icon in the hotbar.
	 *
	 * @return The name.
	 */
	public abstract String getDisplayName();

	/**
	 * Returns the description of the tool. Used as the lore (with automatic new lines) of the icon in the hotbar.
	 *
	 * @return The description.
	 */
	public abstract String getDescription();

	/**
	 * Returns the object used as the representation of this tool in the hotbar. <p/> You don't have to set the display
	 * name, lore, etc.: this will be done automatically from the content returned by {@link #getDisplayName} and {@link
	 * #getDescription}.
	 *
	 * @param drawer This icon will be displayed to this drawer.
	 *
	 * @return The icon, as an ItemStack.
	 */
	public abstract ItemStack getIcon(Drawer drawer);


	/**
	 * Executed when the player right-clicks with the tool.
	 *
	 * @param targetOnScreen The block the drawer is currently targeting on his screen. CAN BE NULL, if the drawer isn't
	 *                       targeting the screen.
	 * @param drawer         The drawer.
	 */
	public abstract void onRightClick(WhiteboardLocation targetOnScreen, Drawer drawer);

	/**
	 * Executed when the player left-clicks with the tool.
	 *
	 * @param targetOnScreen The block the drawer is currently targeting on his screen. CAN BE NULL, if the drawer isn't
	 *                       targeting the screen.
	 * @param drawer         The drawer.
	 */
	public abstract void onLeftClick(WhiteboardLocation targetOnScreen, Drawer drawer);


	/**
	 * Returns a ready-to-use ItemStack to represents this tool in the player's hotbar.
	 *
	 * @param drawer This icon will be displayed to this drawner.
	 *
	 * @return The ItemStack, with display names and lores correctly set.
	 */
	public ItemStack constructIcon(Drawer drawer) {
		ItemStack icon = getIcon(drawer).clone();
		ItemMeta meta = icon.getItemMeta();

		meta.setDisplayName(getDisplayName());
		meta.setLore(Utils.stringToLore(getDescription()));

		icon.setItemMeta(meta);

		return icon;
	}

	/**
	 * Returns the drawer associated with this tool.
	 *
	 * @return The drawer.
	 */
	public Drawer getDrawer() {
		return drawer;
	}
}

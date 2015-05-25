package eu.carrade.amaury.Camelia.drawing.drawTools.core;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.carrade.amaury.Camelia.game.Drawer;
import eu.carrade.amaury.Camelia.utils.Utils;

/**
 * Represents a tool used to draw.
 */
public abstract class DrawTool {

	/**
	 * Returns the display name of the tool.
	 * Used as the display name of the icon in the hotbar.
	 *
	 * @return The name.
	 */
	public abstract String getDisplayName();

	/**
	 * Returns the description of the tool.
	 * Used as the lore (with automatic new lines) of the icon in the hotbar.
	 *
	 * @return The description.
	 */
	public abstract String getDescription();

	/**
	 * Returns the object used as the representation of this tool in the hotbar.
	 *
	 * You don't have to set the display name, lore, etc.: this will be done automatically
	 * from the content returned by {@link #getDisplayName} and {@link #getDescription}.
	 *
	 * @return The icon, as an ItemStack.
	 * @param drawer The drawer
	 */
	public abstract ItemStack getIcon(Drawer drawer);


	/**
	 * Returns the slot this tool will reside on.
	 *
	 * @return The slot. From 0 to 8; another value will be interpreted as 8 (don't do that!).
	 */
	public abstract int getSlot();


	/**
	 * Executed when the player right-clicks with the tool.
	 *
	 * @param targetOnScreen The block the drawer is currently targeting on his screen.
	 * @param drawer The drawer.
	 */
	public abstract void onRightClick(Location targetOnScreen, Drawer drawer);

	/**
	 * Executed when the player left-clicks with the tool.
	 *
	 * @param targetOnScreen The block the drawer is currently targeting on his screen.
	 * @param drawer The drawer.
	 */
	public abstract void onLeftClick(Location targetOnScreen, Drawer drawer);


	/**
	 * Returns a ready-to-use ItemStack to represents this tool in the player's hotbar.
	 *
	 * @return The ItemStack, with display names and lores correctly set.
	 * @param drawer
	 */
	public ItemStack constructIcon(Drawer drawer) {
		ItemStack icon = getIcon(drawer).clone();
		ItemMeta  meta = icon.getItemMeta();

		meta.setDisplayName(getDisplayName() + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Clic droit (outil) ou gauche (couleurs)");
		meta.setLore(Utils.stringToLore(getDescription()));

		icon.setItemMeta(meta);

		return icon;
	}

}

package eu.carrade.amaury.Camelia.utils;

import org.bukkit.*;
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
public class GuiUtils {

	public static ItemStack getBackItem() {
		ItemStack stack = new ItemStack(Material.EMERALD, 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "« Retour");
		stack.setItemMeta(meta);

		return stack;
	}

	public static String getBooleanTitle(String title, Boolean value) {
		return (value ? ChatColor.GREEN : ChatColor.RED) + "" + ChatColor.BOLD + title;
	}

}

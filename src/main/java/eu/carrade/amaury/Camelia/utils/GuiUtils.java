package eu.carrade.amaury.Camelia.utils;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;


public class GuiUtils {

	public static ItemStack getBackItem() {
		ItemStack stack = new ItemStack(Material.EMERALD, 1);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "« Retour");
		stack.setItemMeta(meta);

		return stack;
	}

}

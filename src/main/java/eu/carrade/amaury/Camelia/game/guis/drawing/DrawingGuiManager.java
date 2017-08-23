package eu.carrade.amaury.Camelia.game.guis.drawing;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorUtils;
import eu.carrade.amaury.Camelia.drawing.colors.core.*;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.*;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.*;
import eu.carrade.amaury.Camelia.game.*;
import eu.carrade.amaury.Camelia.utils.Utils;
import net.samagames.tools.*;
import org.bukkit.*;
import org.bukkit.block.banner.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;

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
public class DrawingGuiManager {

	public final static String COLOR_GUI = "Choix de la couleur";
	public final static String BRUSH_GUI = "Paramètres du pinceau";
	public final static String SPRAY_GUI = "Paramètres de l'aérographe";
	public final static String BACKGROUND_GUI = "Fonds prédéfinis";


	/* ** Drawing inventories ** */


	public Inventory getColorInventory(Drawer drawer) {
		Inventory inventory = Bukkit.createInventory(drawer.getPlayer(), 45, COLOR_GUI);

		int slot = 0;
		List<PixelColor> colors = ColorUtils.basicColors;
		if (drawer.getPage() == 1) {
			colors = ColorUtils.betterColors;
		} else if (drawer.getPage() == 2) {
			colors = ColorUtils.roughColors;
		}

		for (PixelColor color : colors) {
			inventory.setItem(slot, constructColorItem(drawer, color));
			slot++;
		}

		ItemStack basicStone = Utils.quickItemStack(Material.STONE, 1, (byte) 0, ChatColor.WHITE + "Textures Basiques", Arrays.asList(ChatColor.GRAY + "Tous les types de laine"));
		ItemStack betterStone = Utils.quickItemStack(Material.STONE, 1, (byte) 6, ChatColor.WHITE + "Textures Polies", Arrays.asList(ChatColor.GRAY + "Tous les types d'argile cuite"));
		ItemStack roughStone = Utils.quickItemStack(Material.STONE, 1, (byte) 5, ChatColor.WHITE + "Textures Rugeuses", Arrays.asList(ChatColor.GRAY + "D'autres types de blocs"));

		ItemStack page1 = Utils.quickItemStack(Material.GHAST_TEAR, 1, (byte) 0, basicStone.getItemMeta().getDisplayName(), basicStone.getItemMeta().getLore());
		ItemStack page2 = Utils.quickItemStack(Material.GHAST_TEAR, 1, (byte) 0, betterStone.getItemMeta().getDisplayName(), betterStone.getItemMeta().getLore());
		ItemStack page3 = Utils.quickItemStack(Material.GHAST_TEAR, 1, (byte) 0, roughStone.getItemMeta().getDisplayName(), roughStone.getItemMeta().getLore());

		if (drawer.getPage() == 0) {
			Utils.setName(basicStone, ChatColor.GREEN + "" + ChatColor.BOLD + "Textures Basiques");
			GlowEffect.addGlow(basicStone);
			Utils.setName(page1, basicStone.getItemMeta().getDisplayName());
			page1.setType(Material.SUGAR);
		} else if (drawer.getPage() == 1) {
			Utils.setName(betterStone, ChatColor.GREEN + "" + ChatColor.BOLD + "Textures Polies");
			GlowEffect.addGlow(betterStone);
			Utils.setName(page2, betterStone.getItemMeta().getDisplayName());
			page2.setType(Material.SUGAR);
		} else if (drawer.getPage() == 2) {
			Utils.setName(roughStone, ChatColor.GREEN + "" + ChatColor.BOLD + "Textures Rugueuses");
			GlowEffect.addGlow(roughStone);
			Utils.setName(page3, roughStone.getItemMeta().getDisplayName());
			page3.setType(Material.SUGAR);
		}

		inventory.setItem(30, basicStone);
		inventory.setItem(31, betterStone);
		inventory.setItem(32, roughStone);

		inventory.setItem(39, page1);
		inventory.setItem(40, page2);
		inventory.setItem(41, page3);

		return inventory;
	}

	private ItemStack constructColorItem(Drawer drawer, PixelColor color) {
		if (color != null) {
			ItemStack item = new ItemStack(color.getBlock().getType(), 1, color.getBlock().getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(color.getDisplayName());
			meta.setLore(Arrays.asList(ChatColor.GRAY + "Applicable sur tous les outils"));
			item.setItemMeta(meta);

			if (drawer.getColor().getBlock().getType().equals(color.getBlock().getType()) && drawer.getColor().getBlock().getData() == color.getBlock().getData()) {
				GlowEffect.addGlow(item);
			}
			return item;
		} else {
			return new ItemStack(Material.AIR);
		}
	}

	public Inventory getBrushInventory(Drawer drawer) {
		Inventory inventory = Bukkit.createInventory(drawer.getPlayer(), 9, BRUSH_GUI);

		inventory.setItem(0, getColorPicker(drawer));

		inventory.setItem(3, Utils.quickItemStack(Material.FIREWORK_CHARGE, 1, (byte) 0, ChatColor.WHITE + "Taille 1", null, true));
		inventory.setItem(4, Utils.quickItemStack(Material.FIREWORK_CHARGE, 2, (byte) 0, ChatColor.WHITE + "Taille 2", null, true));
		inventory.setItem(5, Utils.quickItemStack(Material.FIREWORK_CHARGE, 3, (byte) 0, ChatColor.WHITE + "Taille 3", null, true));

		ItemStack selected = inventory.getItem(((ContinuousDrawTool) drawer.getTool(0)).getSize() + 2);
		GlowEffect.addGlow(selected);
		Utils.setName(selected, ChatColor.GREEN + "" + ChatColor.BOLD + "Taille " + ((ContinuousDrawTool) drawer.getTool(0)).getSize());

		if (((ContinuousDrawTool) drawer.getTool(0)).isMixColors()) {
			inventory.setItem(8, Utils.quickItemStack(Material.INK_SACK, 1, DyeColor.LIME.getDyeData(), ChatColor.GREEN + "" + ChatColor.BOLD + "Mélange des couleurs : activé", Arrays.asList(ChatColor.GRAY + "Option de mélange des couleurs")));
		} else {
			inventory.setItem(8, Utils.quickItemStack(Material.INK_SACK, 1, DyeColor.GRAY.getDyeData(), ChatColor.RED + "" + ChatColor.BOLD + "Mélange des couleurs : désactivé", Arrays.asList(ChatColor.GRAY + "Option de mélange des couleurs")));
		}

		return inventory;
	}

	public Inventory getSprayInventory(Drawer drawer) {
		Inventory inventory = Bukkit.createInventory(drawer.getPlayer(), 27, SPRAY_GUI);

		inventory.setItem(9, getColorPicker(drawer));

		inventory.setItem(3, Utils.quickItemStack(Material.FIREWORK_CHARGE, 1, (byte) 0, ChatColor.WHITE + "Taille 1", null, true));
		inventory.setItem(4, Utils.quickItemStack(Material.FIREWORK_CHARGE, 2, (byte) 0, ChatColor.WHITE + "Taille 2", null, true));
		inventory.setItem(5, Utils.quickItemStack(Material.FIREWORK_CHARGE, 3, (byte) 0, ChatColor.WHITE + "Taille 3", null, true));

		ItemStack selectedSize = inventory.getItem(((ContinuousDrawTool) drawer.getTool(1)).getSize() + 2);
		GlowEffect.addGlow(selectedSize);
		Utils.setName(selectedSize, ChatColor.GREEN + "" + ChatColor.BOLD + "Taille " + ((ContinuousDrawTool) drawer.getTool(1)).getSize());

		inventory.setItem(21, Utils.quickItemStack(Material.ANVIL, 1, (byte) 0, ChatColor.WHITE + "Dûreté 1", null));
		inventory.setItem(22, Utils.quickItemStack(Material.ANVIL, 1, (byte) 1, ChatColor.WHITE + "Dûreté 2", null));
		inventory.setItem(23, Utils.quickItemStack(Material.ANVIL, 1, (byte) 2, ChatColor.WHITE + "Dûreté 3", null));

		ItemStack selectedSharp = inventory.getItem(((SprayTool) drawer.getTool(1)).getStrength() + 20);
		GlowEffect.addGlow(selectedSharp);
		Utils.setName(selectedSharp, ChatColor.GREEN + "" + ChatColor.BOLD + "Dûreté " + ((SprayTool) drawer.getTool(1)).getStrength());

		if (((ContinuousDrawTool) drawer.getTool(1)).isMixColors()) {
			inventory.setItem(17, Utils.quickItemStack(Material.INK_SACK, 1, DyeColor.LIME.getDyeData(), ChatColor.GREEN + "" + ChatColor.BOLD + "Mélange des couleurs : activé", Arrays.asList(ChatColor.GRAY + "Option de mélange des couleurs")));
		} else {
			inventory.setItem(17, Utils.quickItemStack(Material.INK_SACK, 1, DyeColor.GRAY.getDyeData(), ChatColor.RED + "" + ChatColor.BOLD + "Mélange des couleurs : désactivé", Arrays.asList(ChatColor.GRAY + "Option de mélange des couleurs")));
		}

		return inventory;
	}

	public Inventory getBackgroundInventory(Drawer drawer) {
		List<ItemStack> items = new ArrayList<ItemStack>();

		// Examples
		items.add(getBackgroundItem(Material.DOUBLE_PLANT, (byte) 0, ChatColor.YELLOW + "" + ChatColor.BOLD + "Coucher de soleil"));
		items.add(getBackgroundItem(Material.CACTUS, (byte) 0, ChatColor.GOLD + "" + ChatColor.BOLD + "Désert"));
		items.add(getBackgroundItem(Material.STONE, (byte) 0, ChatColor.GRAY + "" + ChatColor.BOLD + "Caverne"));

		double add = (double) items.size() / 7;
		Inventory inventory = Bukkit.createInventory(drawer.getPlayer(), (int) (18 + Math.ceil(add) * 9), BACKGROUND_GUI);

		int slot = 10;

		for (ItemStack item : items) {
			inventory.setItem(slot, item);

			if (slot % 9 == 7) {
				slot += 3;
			} else {
				slot++;
			}
		}

		return inventory;
	}

	private ItemStack getBackgroundItem(Material type, byte data, String name) {
		return Utils.quickItemStack(type, 1, data, name, Arrays.asList(ChatColor.RED + "Attention " + ChatColor.GRAY + "l'application d'un fond", ChatColor.GRAY + "remplacera le dessin actuel"));
	}
	
	/* COMMON */

	private ItemStack getColorPicker(Drawer drawer) {
		return Utils.setName(drawer.getColor().getBlock().toItemStack(1), ChatColor.WHITE + "" + ChatColor.BOLD + "Changer de couleur");
	}
}

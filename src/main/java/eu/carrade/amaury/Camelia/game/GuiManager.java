package eu.carrade.amaury.Camelia.game;

import java.util.Arrays;
import java.util.List;

import net.samagames.utils.GlowEffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorUtils;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import eu.carrade.amaury.Camelia.listeners.InventoryListener;
import eu.carrade.amaury.Camelia.utils.Utils;

public class GuiManager {

	
	public Inventory getColorInventory(Drawer drawer) {
		Inventory inventory = Bukkit.createInventory(drawer.getPlayer(), 45, InventoryListener.COLOR_GUI);
		
		int slot = 0;
		List<PixelColor> colors = ColorUtils.basicColors;
		if(drawer.getPage() == 1) {
			colors = ColorUtils.betterColors;
		} else if(drawer.getPage() == 2) {
			colors = ColorUtils.roughColors;
		}
		
		for(PixelColor color : colors) {
			inventory.setItem(slot, constructItemStack(drawer, color));
			slot++;
		}
		
		ItemStack basicStone = Utils.quickItemStack(Material.STONE, 1, (byte) 0, ChatColor.WHITE + "Textures Basiques", Arrays.asList(ChatColor.GRAY + "Tous les types de laine"));
		ItemStack betterStone = Utils.quickItemStack(Material.STONE, 1, (byte) 6, ChatColor.WHITE + "Textures Polies", Arrays.asList(ChatColor.GRAY + "Tous les types d'argile cuite"));
		ItemStack roughStone = Utils.quickItemStack(Material.STONE, 1, (byte) 5, ChatColor.WHITE + "Textures Rugeuses", Arrays.asList(ChatColor.GRAY + "D'autres types de blocs"));
		
		ItemStack page1 = Utils.quickItemStack(Material.GHAST_TEAR, 1, (byte) 0, basicStone.getItemMeta().getDisplayName(), basicStone.getItemMeta().getLore());
		ItemStack page2 = Utils.quickItemStack(Material.GHAST_TEAR, 1, (byte) 0, betterStone.getItemMeta().getDisplayName(), betterStone.getItemMeta().getLore());
		ItemStack page3 = Utils.quickItemStack(Material.GHAST_TEAR, 1, (byte) 0, roughStone.getItemMeta().getDisplayName(), roughStone.getItemMeta().getLore());
		
		if(drawer.getPage() == 0) {
			Utils.setName(basicStone, ChatColor.GREEN + "" + ChatColor.BOLD + "Textures Basiques");
			GlowEffect.addGlow(basicStone);
			Utils.setName(page1, basicStone.getItemMeta().getDisplayName());
			page1.setType(Material.SUGAR);
		} else if(drawer.getPage() == 1) {
			Utils.setName(betterStone, ChatColor.GREEN + "" + ChatColor.BOLD + "Textures Polies");
			GlowEffect.addGlow(betterStone);
			Utils.setName(page2, betterStone.getItemMeta().getDisplayName());
			page2.setType(Material.SUGAR);
		} else if(drawer.getPage() == 2) {
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
	
	private ItemStack constructItemStack(Drawer drawer, PixelColor color) {
		if(color != null) {
			ItemStack item = new ItemStack(color.getBlock().getType(), 1, color.getBlock().getData());
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(color.getDisplayName());
			meta.setLore(Arrays.asList(ChatColor.GRAY + "Applicable sur tous les outils"));
			item.setItemMeta(meta);
		
			if(drawer.getColor().getBlock().getType().equals(color.getBlock().getType()) && drawer.getColor().getBlock().getData() == color.getBlock().getData()) {
				GlowEffect.addGlow(item);
			}
			return item;
		} else {
			return new ItemStack(Material.AIR);
		}
	}
}

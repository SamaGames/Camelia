package eu.carrade.amaury.Camelia.game;

import java.util.Arrays;
import java.util.List;

import net.samagames.tools.GlowEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorUtils;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ContinuousDrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.SprayTool;
import eu.carrade.amaury.Camelia.utils.Utils;

public class GuiManager {
	
	public final static String COLOR_GUI = "Choix de la couleur";
	public final static String BRUSH_GUI = "Paramètres du pinceau";
	public final static String SPRAY_GUI = "Paramètres de l'aérographe";
	
	public Inventory getColorInventory(Drawer drawer) {
		Inventory inventory = Bukkit.createInventory(drawer.getPlayer(), 45, COLOR_GUI);
		
		int slot = 0;
		List<PixelColor> colors = ColorUtils.basicColors;
		if(drawer.getPage() == 1) {
			colors = ColorUtils.betterColors;
		} else if(drawer.getPage() == 2) {
			colors = ColorUtils.roughColors;
		}
		
		for(PixelColor color : colors) {
			inventory.setItem(slot, constructColorItem(drawer, color));
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
	
	private ItemStack constructColorItem(Drawer drawer, PixelColor color) {
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
	
	public Inventory getBrushInventory(Drawer drawer) {
		Inventory inventory = Bukkit.createInventory(drawer.getPlayer(), 9, BRUSH_GUI);
		
		inventory.setItem(0, getColorPicker(drawer));
		
		inventory.setItem(3, Utils.quickItemStack(Material.FIREWORK_CHARGE, 1, (byte) 0, ChatColor.WHITE + "Taille 1", null));
		inventory.setItem(4, Utils.quickItemStack(Material.FIREWORK_CHARGE, 2, (byte) 0, ChatColor.WHITE + "Taille 2", null));
		inventory.setItem(5, Utils.quickItemStack(Material.FIREWORK_CHARGE, 3, (byte) 0, ChatColor.WHITE + "Taille 3", null));
		
		ItemStack selected = inventory.getItem(((ContinuousDrawTool) drawer.getTool(0)).getSize() + 2);
		GlowEffect.addGlow(selected);
		Utils.setName(selected, ChatColor.GREEN + "" + ChatColor.BOLD + "Taille " + ((ContinuousDrawTool) drawer.getTool(0)).getSize());
		
		if(((ContinuousDrawTool) drawer.getTool(0)).isMixColors()) {
			inventory.setItem(8, Utils.quickItemStack(Material.INK_SACK, 1, DyeColor.LIME.getDyeData(), ChatColor.GREEN + "" + ChatColor.BOLD + "Mélange des couleurs : activé", Arrays.asList(ChatColor.GRAY + "Option de mélange des couleurs")));
		} else {
			inventory.setItem(8, Utils.quickItemStack(Material.INK_SACK, 1, DyeColor.GRAY.getDyeData(), ChatColor.RED + "" + ChatColor.BOLD + "Mélange des couleurs : désactivé", Arrays.asList(ChatColor.GRAY + "Option de mélange des couleurs")));
		}
		
		return inventory;
	}
	
	public Inventory getSprayInventory(Drawer drawer) {
		Inventory inventory = Bukkit.createInventory(drawer.getPlayer(), 27, SPRAY_GUI);
		
		inventory.setItem(9, getColorPicker(drawer));
		
		inventory.setItem(3, Utils.quickItemStack(Material.FIREWORK_CHARGE, 1, (byte) 0, ChatColor.WHITE + "Taille 1", null));
		inventory.setItem(4, Utils.quickItemStack(Material.FIREWORK_CHARGE, 2, (byte) 0, ChatColor.WHITE + "Taille 2", null));
		inventory.setItem(5, Utils.quickItemStack(Material.FIREWORK_CHARGE, 3, (byte) 0, ChatColor.WHITE + "Taille 3", null));
		
		ItemStack selectedSize = inventory.getItem(((ContinuousDrawTool) drawer.getTool(1)).getSize() + 2);
		GlowEffect.addGlow(selectedSize);
		Utils.setName(selectedSize, ChatColor.GREEN + "" + ChatColor.BOLD + "Taille " + ((ContinuousDrawTool) drawer.getTool(1)).getSize());
		
		inventory.setItem(21, Utils.quickItemStack(Material.ANVIL, 1, (byte) 0, ChatColor.WHITE + "Dûreté 1", null));
		inventory.setItem(22, Utils.quickItemStack(Material.ANVIL, 1, (byte) 1, ChatColor.WHITE + "Dûreté 2", null));
		inventory.setItem(23, Utils.quickItemStack(Material.ANVIL, 1, (byte) 2, ChatColor.WHITE + "Dûreté 3", null));
		
		ItemStack selectedSharp = inventory.getItem(((SprayTool) drawer.getTool(1)).getStrength() + 20);
		GlowEffect.addGlow(selectedSharp);
		Utils.setName(selectedSharp, ChatColor.GREEN + "" + ChatColor.BOLD + "Dûreté " + ((SprayTool) drawer.getTool(1)).getStrength());
		
		if(((ContinuousDrawTool) drawer.getTool(1)).isMixColors()) {
			inventory.setItem(17, Utils.quickItemStack(Material.INK_SACK, 1, DyeColor.LIME.getDyeData(), ChatColor.GREEN + "" + ChatColor.BOLD + "Mélange des couleurs : activé", Arrays.asList(ChatColor.GRAY + "Option de mélange des couleurs")));
		} else {
			inventory.setItem(17, Utils.quickItemStack(Material.INK_SACK, 1, DyeColor.GRAY.getDyeData(), ChatColor.RED + "" + ChatColor.BOLD + "Mélange des couleurs : désactivé", Arrays.asList(ChatColor.GRAY + "Option de mélange des couleurs")));
		}
		
		return inventory;
	}
	
	
	/* COMMON */
	
	private ItemStack getColorPicker(Drawer drawer) {
		return Utils.setName(drawer.getColor().getBlock().toItemStack(1), ChatColor.WHITE + "" + ChatColor.BOLD + "Changer de couleur");
	}
}

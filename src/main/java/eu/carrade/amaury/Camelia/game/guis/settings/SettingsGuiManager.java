package eu.carrade.amaury.Camelia.game.guis.settings;

import eu.carrade.amaury.Camelia.game.*;
import eu.carrade.amaury.Camelia.utils.*;
import org.bukkit.*;
import org.bukkit.block.banner.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;



public class SettingsGuiManager {

	public final static String TIP_LOCATION_GUI = "Position de l'indice";


		/* ** Option inventories ** */

	public Inventory getTipLocationOptionInventory(Drawer drawer) {
		Inventory inventory = Bukkit.createInventory(drawer.getPlayer(), 3 * 9, TIP_LOCATION_GUI);

		Drawer.DisplayType display = drawer.getWordDisplay();


		ItemStack bannerLocationActionBar = Utils.quickItemStack(
				Material.BANNER, 1, (byte) 0,
				ChatColor.GREEN + "" + (display == Drawer.DisplayType.ACTION_BAR ? ChatColor.BOLD : "") + "Indice en bas",
				Utils.stringToLore(ChatColor.GRAY + "Affiche l'indice juste au dessus de l'inventaire. Discret, mais possiblement recouvert par le chat."),
				true);

		BannerMeta meta = (BannerMeta) bannerLocationActionBar.getItemMeta();
		meta.setBaseColor((display == Drawer.DisplayType.ACTION_BAR ? DyeColor.LIME : DyeColor.WHITE));
		meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM));
		bannerLocationActionBar.setItemMeta(meta);


		ItemStack bannerLocationTitle = Utils.quickItemStack(
				Material.BANNER, 1, (byte) 0,
				ChatColor.GREEN + "" + (display == Drawer.DisplayType.TITLE ? ChatColor.BOLD : "") + "Indice au centre",
				Utils.stringToLore(ChatColor.GRAY + "Affiche l'indice au centre de l'écran. Bien visible, potentiellement trop selon les configurations."),
				true);

		meta = (BannerMeta) bannerLocationTitle.getItemMeta();
		meta.setBaseColor((display == Drawer.DisplayType.TITLE ? DyeColor.LIME : DyeColor.WHITE));
		meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_MIDDLE));
		bannerLocationTitle.setItemMeta(meta);


		ItemStack bannerLocationBossBar = Utils.quickItemStack(
				Material.BANNER, 1, (byte) 0,
				ChatColor.GREEN + "" + (display == Drawer.DisplayType.BOSS_BAR ? ChatColor.BOLD : "") + "Indice en haut",
				Utils.stringToLore(ChatColor.GRAY + "Affiche l'indice en haut de l'écran, dans la BossBar. Pratique, encore faut-il savoir qu'il est là."),
				true);

		meta = (BannerMeta) bannerLocationBossBar.getItemMeta();
		meta.setBaseColor((display == Drawer.DisplayType.BOSS_BAR ? DyeColor.LIME : DyeColor.WHITE));
		meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_TOP));
		bannerLocationBossBar.setItemMeta(meta);


		inventory.setItem(11, bannerLocationActionBar);
		inventory.setItem(13, bannerLocationTitle);
		inventory.setItem(15, bannerLocationBossBar);


		return inventory;
	}
}

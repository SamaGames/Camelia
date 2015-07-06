package eu.carrade.amaury.Camelia.game.guis.settings;


import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.game.*;
import eu.carrade.amaury.Camelia.game.guis.*;
import eu.carrade.amaury.Camelia.utils.*;
import org.bukkit.*;
import org.bukkit.block.banner.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;


public class TipLocationSettingsGui extends AbstractGui {

	@Override
	public void display(Player player) {

		this.inventory = Bukkit.createInventory(player, 4 * 9, "Position de l'indice");

		setSlotData(GuiUtils.getBackItem(), this.inventory.getSize() - 5, "back");

		update(player);

		player.openInventory(inventory);
	}

	@Override
	public void update(Player player) {
		Drawer drawer = Camelia.getInstance().getGameManager().getDrawer(player.getUniqueId());
		if(drawer == null) return;


		Drawer.DisplayType display = drawer.getWordDisplay();


		ItemStack bannerLocationActionBar = Utils.quickItemStack(
				Material.BANNER, 1, (byte) 0,
				GuiUtils.getBooleanTitle("Indice en bas", display == Drawer.DisplayType.ACTION_BAR),
				Utils.stringToLore(
						ChatColor.GRAY + "Affiche l'indice juste au dessus de l'inventaire. Discret, mais possiblement recouvert par le chat."
						+ (display != Drawer.DisplayType.ACTION_BAR ? ChatColor.GREEN + "{bl}Cliquez pour sélectionner" : "")
				),
				true);

		BannerMeta meta = (BannerMeta) bannerLocationActionBar.getItemMeta();
		meta.setBaseColor((display == Drawer.DisplayType.ACTION_BAR ? DyeColor.LIME : DyeColor.WHITE));
		meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM));
		bannerLocationActionBar.setItemMeta(meta);


		ItemStack bannerLocationTitle = Utils.quickItemStack(
				Material.BANNER, 1, (byte) 0,
				GuiUtils.getBooleanTitle("Indice au centre", display == Drawer.DisplayType.TITLE),
				Utils.stringToLore(
						ChatColor.GRAY + "Affiche l'indice au centre de l'écran. Bien visible, potentiellement trop selon les configurations."
						+ (display != Drawer.DisplayType.TITLE ? ChatColor.GREEN + "{bl}Cliquez pour sélectionner" : "")
				),
				true);

		meta = (BannerMeta) bannerLocationTitle.getItemMeta();
		meta.setBaseColor((display == Drawer.DisplayType.TITLE ? DyeColor.LIME : DyeColor.WHITE));
		meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_MIDDLE));
		bannerLocationTitle.setItemMeta(meta);


		ItemStack bannerLocationBossBar = Utils.quickItemStack(
				Material.BANNER, 1, (byte) 0,
				GuiUtils.getBooleanTitle("Indice en haut", display == Drawer.DisplayType.BOSS_BAR),
				Utils.stringToLore(
						ChatColor.GRAY + "Affiche l'indice en haut de l'écran, dans la BossBar. Pratique, encore faut-il savoir qu'il est là."
						+ (display != Drawer.DisplayType.BOSS_BAR ? ChatColor.GREEN + "{bl}Cliquez pour sélectionner" : "")
				),
				true);

		meta = (BannerMeta) bannerLocationBossBar.getItemMeta();
		meta.setBaseColor((display == Drawer.DisplayType.BOSS_BAR ? DyeColor.LIME : DyeColor.WHITE));
		meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_TOP));
		bannerLocationBossBar.setItemMeta(meta);


		setSlotData(bannerLocationActionBar, 11, "actionBar");
		setSlotData(bannerLocationTitle,     13, "title");
		setSlotData(bannerLocationBossBar,   15, "bossBar");
	}

	@Override
	public void onClick(Player player, ItemStack stack, String action) {

		Drawer drawer = Camelia.getInstance().getGameManager().getDrawer(player.getUniqueId());
		if(drawer == null) return;

		Drawer.DisplayType newDisplayType = null;

		switch (action) {
			case "actionBar":
				newDisplayType = Drawer.DisplayType.ACTION_BAR;
				break;

			case "title":
				newDisplayType = Drawer.DisplayType.TITLE;
				break;

			case "bossBar":
				newDisplayType = Drawer.DisplayType.BOSS_BAR;
				break;

			case "back":
				Camelia.getInstance().getGuiManager().openGui(player, new SettingsGui());
				break;
		}

		if (newDisplayType != null) {
			drawer.setWordDisplay(newDisplayType);
			update(player);
		}
	}
}

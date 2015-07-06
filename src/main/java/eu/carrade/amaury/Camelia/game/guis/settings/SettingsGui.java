package eu.carrade.amaury.Camelia.game.guis.settings;

import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.game.*;
import eu.carrade.amaury.Camelia.game.guis.*;
import eu.carrade.amaury.Camelia.utils.*;
import eu.carrade.amaury.Camelia.utils.Utils;
import net.samagames.tools.*;
import org.bukkit.*;
import org.bukkit.block.banner.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import java.util.*;


public class SettingsGui extends AbstractGui {

	@Override
	public void display(Player player) {

		this.inventory = Bukkit.createInventory(player, 4 * 9, "Options de jeu");

		setSlotData(GuiUtils.getBackItem(), this.inventory.getSize() - 5, "back");

		update(player);

		player.openInventory(inventory);
	}

	@Override
	public void update(Player player) {
		Drawer drawer = Camelia.getInstance().getGameManager().getDrawer(player.getUniqueId());
		if(drawer == null) return;


		/* ** Difficulty ** */

		ItemStack difficulty = Utils.quickItemStack(
				Material.SKULL_ITEM, 1,
				drawer.getHardWordsEnabled() ? (byte) 1 : (byte) 0,
				ChatColor.RED + "" + ChatColor.BOLD + "Difficulté",
				Utils.stringToLore(
						ChatColor.GRAY + "Si activé, il sera possible d'obtenir des mots plus compliqués à dessiner. Ces mots donneront plus de points s'ils sont devinés.{bl}"
						+ (drawer.getHardWordsEnabled() ? ChatColor.GREEN + "Actuellement actif." : ChatColor.RED + "Actuellement désactivé.")
				),
				true
		);


		/* ** Tip location ** */

		ItemStack tipLocation = Utils.quickItemStack(
				Material.BANNER, 1, (byte) 0,
				ChatColor.GREEN + "" + ChatColor.BOLD + "Position de l'indice",
				Utils.stringToLore(ChatColor.GRAY + "Détermine où l'indice est affiché sur l'écran."),
				true);

		BannerMeta meta = (BannerMeta) tipLocation.getItemMeta();
		meta.setBaseColor(DyeColor.LIME);

		List<String> lore = meta.getLore();
		lore.add("");

		switch (drawer.getWordDisplay()) {

			case ACTION_BAR:
				meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_BOTTOM));
				lore.add(ChatColor.GREEN + "Actuellement : en bas.");
				break;

			case TITLE:
				meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_MIDDLE));
				lore.add(ChatColor.GREEN + "Actuellement : au centre.");
				break;

			case BOSS_BAR:
				meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.STRIPE_TOP));
				lore.add(ChatColor.GREEN + "Actuellement : en haut.");
				break;
		}

		meta.setLore(lore);
		tipLocation.setItemMeta(meta);


		/* ** Sounds ** */

		ItemStack sounds = Utils.quickItemStack(
				Material.NOTE_BLOCK, 1, (byte) 0,
				ChatColor.RED + "" + ChatColor.BOLD + "Sons",
				Utils.stringToLore(
						ChatColor.GRAY + "Si activé, divers sons seront joués lorsque les joueurs dessinent.{bl}"
								+ (drawer.getSoundsEnabled() ? ChatColor.GREEN + "Actuellement actif." : ChatColor.RED + "Actuellement désactivé.")
				),
				true
		);

		if(drawer.getSoundsEnabled()) {
			GlowEffect.addGlow(sounds);
		}


		setSlotData(difficulty, 11, "difficulty");
		setSlotData(tipLocation, 13, "tipLocation");
		setSlotData(sounds, 15, "sounds");
	}

	@Override
	public void onClick(Player player, ItemStack stack, String action) {

		Drawer drawer = Camelia.getInstance().getGameManager().getDrawer(player.getUniqueId());
		if(drawer == null) return;


		switch (action) {
			case "tipLocation":
				Camelia.getInstance().getGuiManager().openGui(player, new TipLocationSettingsGui());
				return;

			case "difficulty":
				drawer.setHardWordsEnabled(!drawer.getHardWordsEnabled());
				break;

			case "sounds":
				drawer.setSoundsEnabled(!drawer.getSoundsEnabled());
				break;

			case "back":
				player.closeInventory();
				return;
		}

		update(player);
	}
}

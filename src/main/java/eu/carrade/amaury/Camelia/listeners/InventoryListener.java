package eu.carrade.amaury.Camelia.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorUtils;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import eu.carrade.amaury.Camelia.game.Drawer;

public class InventoryListener implements Listener {

	public final static String COLOR_GUI = "Choix de la couleur";
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Drawer drawer = Camelia.getInstance().getGameManager().getDrawer(e.getWhoClicked().getUniqueId());
		if(drawer == null)
			return;
		if(e.getInventory().getTitle().equals(COLOR_GUI)) {
			e.setCancelled(true);
			if(e.getSlot() < 0 || e.getInventory().getItem(e.getSlot()) == null || e.getInventory().getItem(e.getSlot()).getType().equals(Material.AIR))
				return;
			
			if(e.getSlot() < 18) {
				List<Integer> list = Arrays.asList(14, 12, 1, 4, 5, 13, -1, 0, 8, 9, 3, 11, 10, 2, 6, -1, 7, 15);
				ColorType color = ColorType.BASIC;
				if(drawer.getPage() == 1) {
					color = ColorType.BETTER;
				} else if(drawer.getPage() == 2) {
					color = ColorType.ROUGH;
				}
				
				PixelColor pixelColor = ColorUtils.getPixelFromDye(DyeColor.getByData(list.get(e.getSlot()).byteValue()), color);
				
				drawer.setColor(pixelColor);
				
				e.getWhoClicked().closeInventory();
				
				if(e.getWhoClicked() instanceof Player) {
					((Player) e.getWhoClicked()).sendMessage(ChatColor.GREEN + "Vous avez sélectionné la couleur " + pixelColor.getDisplayName());
				}
			} else if(e.getSlot() >= 30 && e.getSlot() <= 32 || e.getSlot() >= 39 && e.getSlot() <= 41) {
				int page = (e.getSlot() - 3) % 9;
				drawer.setPage(page);
				e.getWhoClicked().openInventory(Camelia.getInstance().getGuiManager().getColorInventory(drawer));
			}
			
		}
	}
	
}

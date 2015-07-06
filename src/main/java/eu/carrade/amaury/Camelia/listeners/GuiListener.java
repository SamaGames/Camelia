package eu.carrade.amaury.Camelia.listeners;

import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.game.guis.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;


public class GuiListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			AbstractGui gui = Camelia.getInstance().getGuiManager().getPlayerGui(player);

			if (gui != null) {
				if (event.getInventory() instanceof PlayerInventory)
					return;

				String action = gui.getAction(event.getSlot());

				if (action != null)
					gui.onClick(player, event.getCurrentItem(), action, event.getClick());

				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (Camelia.getInstance().getGuiManager().getPlayerGui(event.getPlayer()) != null)
			Camelia.getInstance().getGuiManager().removeClosedGui((Player) event.getPlayer());
	}
}

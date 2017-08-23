package eu.carrade.amaury.Camelia.listeners;

import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.game.guis.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;

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

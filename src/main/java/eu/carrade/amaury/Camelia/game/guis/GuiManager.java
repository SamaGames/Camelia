package eu.carrade.amaury.Camelia.game.guis;

import org.bukkit.entity.*;

import java.util.*;
import java.util.concurrent.*;

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
public class GuiManager {
	protected ConcurrentHashMap<UUID, AbstractGui> currentGUIs;

	public GuiManager() {
		this.currentGUIs = new ConcurrentHashMap<>();
	}

	public void openGui(Player player, AbstractGui gui) {
		if (this.currentGUIs.containsKey(player.getUniqueId()))
			this.closeGui(player);

		this.currentGUIs.put(player.getUniqueId(), gui);
		gui.display(player);
	}

	public void closeGui(Player player) {
		player.closeInventory();
		this.removeClosedGui(player);
	}

	public void removeClosedGui(Player player) {
		if (this.currentGUIs.containsKey(player.getUniqueId())) {
			this.getPlayerGui(player).onClose(player);
			this.currentGUIs.remove(player.getUniqueId());
		}
	}

	public AbstractGui getPlayerGui(HumanEntity player) {
		if (this.currentGUIs.containsKey(player.getUniqueId()))
			return this.currentGUIs.get(player.getUniqueId());

		return null;
	}

	public ConcurrentHashMap<UUID, AbstractGui> getPlayersGui() {
		return this.currentGUIs;
	}
}
package eu.carrade.amaury.Camelia.listeners;


import eu.carrade.amaury.Camelia.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

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
public class PlayersConnectionListener implements Listener {


	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(final PlayerJoinEvent ev) {
		Camelia.getInstance().getGameManager().registerNewDrawer(ev.getPlayer().getUniqueId()).fillInventory();


		// TODO check for concurrent mod exceptions
		Bukkit.getScheduler().runTaskLaterAsynchronously(Camelia.getInstance(), () -> Camelia.getInstance().getWhiteboard().sendAllWhitebord(ev.getPlayer()), 20l);
	}
}

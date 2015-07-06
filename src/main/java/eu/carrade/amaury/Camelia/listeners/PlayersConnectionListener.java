package eu.carrade.amaury.Camelia.listeners;


import eu.carrade.amaury.Camelia.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;


public class PlayersConnectionListener implements Listener {


	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(final PlayerJoinEvent ev) {
		Camelia.getInstance().getGameManager().registerNewDrawer(ev.getPlayer().getUniqueId()).fillInventory();


		// TODO check for concurrent mod exceptions
		Bukkit.getScheduler().runTaskLaterAsynchronously(Camelia.getInstance(), () -> Camelia.getInstance().getWhiteboard().sendAllWhitebord(ev.getPlayer()), 20l);
	}
}

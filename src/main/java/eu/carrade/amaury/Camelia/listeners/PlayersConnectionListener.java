package eu.carrade.amaury.Camelia.listeners;


import eu.carrade.amaury.Camelia.Camelia;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayersConnectionListener implements Listener {


	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent ev) {
		Camelia.getInstance().getGameManager().registerNewDrawer(ev.getPlayer().getUniqueId()).fillInventory();
	}
}

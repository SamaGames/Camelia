package eu.carrade.amaury.Camelia.listeners;

import eu.carrade.amaury.Camelia.Camelia;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class DrawListener implements Listener {


	Map<UUID,BukkitTask> watchRightClickTasks = new ConcurrentHashMap<>();


	/**
	 * This event is called every 200ms (4 ticks) when a player is right-clicking continuously.
	 * (Experimental, the value is noticeably stable).
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev) {
		if(!ev.getPlayer().isOp()) ev.setCancelled(true);

		final UUID id = ev.getPlayer().getUniqueId();

		if(watchRightClickTasks.containsKey(id)) {
			watchRightClickTasks.get(id).cancel();
		}

		Camelia.getInstance().getDrawingManager().setRightClicking(id, true);

		watchRightClickTasks.put(id, Bukkit.getScheduler().runTaskLaterAsynchronously(Camelia.getInstance(), new Runnable() {
			@Override
			public void run() {
				Camelia.getInstance().getDrawingManager().setRightClicking(id, false);
			}
		}, 4l));
	}

}

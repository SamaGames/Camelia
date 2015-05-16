package eu.carrade.amaury.Camelia.listeners;

import eu.carrade.amaury.Camelia.Camelia;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class DrawListener implements Listener {

	Long lastCallInteract = System.currentTimeMillis();

	Set<UUID> rightClickingPlayers = new HashSet<>();
	Map<UUID,BukkitTask> removeLastKnownLocationTasks = new ConcurrentHashMap<>();
	Map<UUID,Location> previousLocation = new ConcurrentHashMap<>();


	/**
	 * This event is called every 200ms (4 ticks) when a player is right-clicking continuously.
	 * (Experimental, the value is noticeably stable).
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev) {
		if(!ev.getPlayer().isOp()) ev.setCancelled(true);


		final UUID id = ev.getPlayer().getUniqueId();

		if(removeLastKnownLocationTasks.containsKey(id)) {
			removeLastKnownLocationTasks.get(id).cancel();
		}

		Location target = Camelia.getInstance().getWhiteboard().getTargetBlock(ev.getPlayer());

		if(target != null) {
			Camelia.getInstance().getWhiteboard().setBlock(target, Material.COAL_BLOCK);

			// We simulate the call of this event for each block between the last known location and this one
			// following a line
			if(previousLocation.containsKey(id)) {
				Location start = previousLocation.get(id);
				Vector direction = target.toVector().subtract(start.toVector());
				Double distance = start.distance(target);

				BlockIterator blockIterator = new BlockIterator(start.getWorld(), start.toVector(), direction, 1, (int) Math.ceil(distance));

				while (blockIterator.hasNext()) {
					Camelia.getInstance().getWhiteboard().setBlock(blockIterator.next().getLocation(), Material.COAL_BLOCK);
				}
			}
		}

		previousLocation.put(id, target);

		removeLastKnownLocationTasks.put(id, Bukkit.getScheduler().runTaskLaterAsynchronously(Camelia.getInstance(), new Runnable() {
			@Override
			public void run() {
				previousLocation.remove(id);
			}
		}, 5l));
	}

}

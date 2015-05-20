package eu.carrade.amaury.Camelia.drawing;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class FollowDrawerCursorTask extends BukkitRunnable {

	/**
	 * Stores the previous known target location, used to draw a line between this new location
	 * and the previous one.
	 */
	private Map<UUID,Location> previousLocation = new ConcurrentHashMap<>();

	/**
	 * Stores the milli-timestamp of the last known target location
	 */
	private Map<UUID,Long> previousLocationTime = new ConcurrentHashMap<>();

	@Override
	public void run() {
		for(UUID rightClickingPlayerID : Camelia.getInstance().getDrawingManager().getRightClickingPlayers()) {

			Drawer drawer = Camelia.getInstance().getGameManager().getDrawer(rightClickingPlayerID);

			//if(drawer == null || !drawer.isDrawing()) continue;

			Location target = Camelia.getInstance().getWhiteboard().getTargetBlock(drawer.getPlayer());

			if(target != null) {

				// We draw a line between the last known target and this one
				// (only if the last known target location is not too old and not too close).
				if(previousLocation.containsKey(rightClickingPlayerID)
						&& System.currentTimeMillis() - previousLocationTime.get(rightClickingPlayerID) <= 230l) {

					Location start = previousLocation.get(rightClickingPlayerID);
					Vector direction = target.toVector().subtract(start.toVector());
					Double distance = start.distance(target);

					if(distance > 1) {
						BlockIterator blockIterator = new BlockIterator(start.getWorld(), start.toVector(), direction, 1, (int) Math.ceil(distance));

						while (blockIterator.hasNext()) {
							Camelia.getInstance().getWhiteboard().setBlock(blockIterator.next().getLocation(), Material.COAL_BLOCK);
						}
					}

					else {
						Camelia.getInstance().getWhiteboard().setBlock(target, Material.COAL_BLOCK);
					}
				}

				previousLocation.put(rightClickingPlayerID, target);
				previousLocationTime.put(rightClickingPlayerID, System.currentTimeMillis());
			}
		}
	}
}

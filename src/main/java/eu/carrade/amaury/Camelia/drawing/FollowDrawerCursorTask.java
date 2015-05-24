package eu.carrade.amaury.Camelia.drawing;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ContinuousDrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.DrawTool;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

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

			if(drawer == null || !drawer.isDrawing()) continue;

			DrawTool tool = Camelia.getInstance().getDrawingManager().getActivePlayerTool(drawer);
			if(tool == null || !(tool instanceof ContinuousDrawTool)) return;

			
			Location target = Camelia.getInstance().getWhiteboard().getTargetBlock(drawer.getPlayer());

			if(target != null) {

				// We draw a line between the last known target and this one
				// (only if the last known target location is not too old and not too close).
				if(previousLocation.containsKey(rightClickingPlayerID)
						&& System.currentTimeMillis() - previousLocationTime.get(rightClickingPlayerID) <= 230l) {

					Location start = previousLocation.get(rightClickingPlayerID);
					Location end = target;
					
					drawLine(drawer, start.getBlockX(), start.getBlockY(), end.getBlockX(), end.getBlockY(), end.getBlockZ());

				}

				previousLocation.put(rightClickingPlayerID, target);
				previousLocationTime.put(rightClickingPlayerID, System.currentTimeMillis());
			}
		}
	}

	private void drawLine(Drawer drawer, int x1, int y1, int x2, int y2, int plan) {

		// Draw a perfect line from pos1 to pos2 on a surface
		// Source: http://java.developpez.com/telecharger/detail/id/1268/Algorithme-de-Bresenham
		int dx, dy, i, xinc, yinc, cumul, x, y ;
	 
		x = x1;
		y = y1;
		dx = x2 - x1;
		dy = y2 - y1;
		xinc = ( dx > 0 ) ? 1 : -1 ;
		yinc = ( dy > 0 ) ? 1 : -1 ;
		dx = Math.abs(dx);
		dy = Math.abs(dy);

		drawer.drawABlock(new Location(Bukkit.getServer().getWorlds().get(0), x, y, plan));

		if (dx > dy) {
			cumul = dx / 2;
			for (i=1 ; i <= dx ; i++) {
				x += xinc;
				cumul += dy;
				if (cumul >= dx) {
					cumul -= dx;
					y += yinc;
				}

				drawer.drawABlock(new Location(Bukkit.getServer().getWorlds().get(0), x, y, plan));
			}
		}

		else {
			cumul = dy / 2;
			for (i=1 ; i <= dy ; i++) {
				y += yinc;
				cumul += dx;
				if ( cumul >= dy ) {
					cumul -= dy;
					x += xinc;
				}

				drawer.drawABlock(new Location(Bukkit.getServer().getWorlds().get(0), x, y, plan));
			}
		}
	}
}

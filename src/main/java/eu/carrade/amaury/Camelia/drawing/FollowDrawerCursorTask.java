package eu.carrade.amaury.Camelia.drawing;

import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.*;
import eu.carrade.amaury.Camelia.drawing.whiteboard.*;
import eu.carrade.amaury.Camelia.game.*;
import org.bukkit.scheduler.*;

import java.util.*;
import java.util.concurrent.*;


public class FollowDrawerCursorTask extends BukkitRunnable {

	/**
	 * Stores the previous known target location, used to draw a line between this new location and the previous one.
	 */
	private Map<UUID, WhiteboardLocation> previousLocation = new ConcurrentHashMap<>();

	/**
	 * Stores the milli-timestamp of the last known target location
	 */
	private Map<UUID, Long> previousLocationTime = new ConcurrentHashMap<>();

	@Override
	public void run() {
		for (UUID rightClickingPlayerID : Camelia.getInstance().getDrawingManager().getRightClickingPlayers()) {

			Drawer drawer = Camelia.getInstance().getGameManager().getDrawer(rightClickingPlayerID);

			if (drawer == null || !drawer.isDrawing()) continue;

			DrawTool tool = drawer.getActiveTool();
			if (tool == null || !(tool instanceof ContinuousDrawTool)) return;


			WhiteboardLocation target = WhiteboardLocation.fromBukkitLocation(Camelia.getInstance().getWhiteboard().getTargetBlock(drawer.getPlayer()));

			if (target != null) {

				// We draw a line between the last known target and this one
				// (only if the last known target location is not too old and not too close).
				if (previousLocation.containsKey(rightClickingPlayerID)
						&& System.currentTimeMillis() - previousLocationTime.get(rightClickingPlayerID) <= 230l) {

					WhiteboardLocation start = previousLocation.get(rightClickingPlayerID);
					WhiteboardLocation end = target;

					drawLine(drawer, start, end);

				}

				previousLocation.put(rightClickingPlayerID, target);
				previousLocationTime.put(rightClickingPlayerID, System.currentTimeMillis());
			}
		}
	}

	// Drawer drawer, int x1, int y1, int x2, int y2, int plan
	private void drawLine(Drawer drawer, WhiteboardLocation start, WhiteboardLocation end) {

		DrawTool tool = drawer.getActiveTool();

		if (tool == null) return;


		// Draw a perfect line from pos1 to pos2 on a surface
		// Source: http://java.developpez.com/telecharger/detail/id/1268/Algorithme-de-Bresenham
		int dx, dy, i, xinc, yinc, cumul, x, y;

		x = start.getX();
		y = start.getY();
		dx = end.getX() - start.getX();
		dy = end.getY() - start.getY();

		xinc = (dx > 0) ? 1 : -1;
		yinc = (dy > 0) ? 1 : -1;
		dx = Math.abs(dx);
		dy = Math.abs(dy);

		tool.onRightClick(new WhiteboardLocation(x, y), drawer);

		if (dx > dy) {
			cumul = dx / 2;
			for (i = 1; i <= dx; i++) {
				x += xinc;
				cumul += dy;
				if (cumul >= dx) {
					cumul -= dx;
					y += yinc;
				}

				tool.onRightClick(new WhiteboardLocation(x, y), drawer);
			}
		} else {
			cumul = dy / 2;
			for (i = 1; i <= dy; i++) {
				y += yinc;
				cumul += dx;
				if (cumul >= dy) {
					cumul -= dy;
					x += xinc;
				}

				tool.onRightClick(new WhiteboardLocation(x, y), drawer);
			}
		}
	}
}

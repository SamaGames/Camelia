package eu.carrade.amaury.Camelia.listeners;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ClicDrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.DrawTool;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

import javax.tools.Tool;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class DrawListener implements Listener {


	Map<UUID,BukkitTask> watchRightClickTasks = new ConcurrentHashMap<>();


	/**
	 * This event is called every 200ms (4 ticks) when a player is right-clicking continuously.
	 * (Experimental, the value is noticeably stable).
	 */
	@EventHandler
	public void onPlayerRightClicks(PlayerInteractEvent ev) {

		if(ev.getAction() != Action.RIGHT_CLICK_BLOCK && ev.getAction() != Action.RIGHT_CLICK_AIR) {
			return;
		}


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

	/**
	 * Handles the tools used on click
	 */
	@EventHandler
	public void onPlayerInteracts(PlayerInteractEvent ev) {
		ev.setCancelled(true);

		Drawer drawer = Camelia.getInstance().getGameManager().getDrawer(ev.getPlayer().getUniqueId());

		if(drawer == null) return; // Moderator maybe

		if(/* TODO game started and */ drawer.isDrawing()) {
			DrawTool tool = drawer.getActiveTool();
			Location target = Camelia.getInstance().getWhiteboard().getTargetBlock(ev.getPlayer());

			if(target == null) return;

			switch(ev.getAction()) {

				case LEFT_CLICK_BLOCK:
				case LEFT_CLICK_AIR:

					tool.onLeftClick(target, drawer);

					break;


				case RIGHT_CLICK_BLOCK:
				case RIGHT_CLICK_AIR:

					if(tool instanceof ClicDrawTool) {
						tool.onRightClick(target, drawer);
					}

					break;


				default:
					break;
			}
		}
	}
}

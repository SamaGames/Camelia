package eu.carrade.amaury.Camelia.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GameListener implements Listener {

	
	@EventHandler
	public boolean onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setFoodLevel(100);
		return true;
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if (e.getPlayer().getGameMode() == GameMode.ADVENTURE)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityRegainHealthEvent(EntityRegainHealthEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onInventoryMoveItemEvent(InventoryMoveItemEvent e) {
		if (!(e.getSource().getHolder() instanceof Player && ((HumanEntity) e.getSource().getHolder()).getGameMode() == GameMode.CREATIVE)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		if (e.toWeatherState()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
			e.setCancelled(true);
	}

	@EventHandler
	public void onAchievementAwarded(PlayerAchievementAwardedEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockSpread(BlockSpreadEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}
}

package eu.carrade.amaury.Camelia.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import eu.carrade.amaury.Camelia.Camelia;

public class DrawTimer implements Runnable {

	public final static int SECONDS = 30;
	private int seconds = SECONDS;
	private BukkitTask task = null;
	
	public void startTimer() {
		seconds = SECONDS;
		
		for(Player player : Camelia.getInstance().getServer().getOnlinePlayers()) {
			player.setLevel(seconds);
		}
		
		task = Bukkit.getScheduler().runTaskTimer(Camelia.getInstance(), new Runnable() {
			@Override
			public void run() {
				seconds--;
				
				for(Player player : Camelia.getInstance().getServer().getOnlinePlayers()) {
					player.setLevel(seconds);
				}
				
				if(seconds <= 3 && seconds != 0) {
					for(Player player : Camelia.getInstance().getServer().getOnlinePlayers()) {
						player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1.5F);
					}
				}
				
				if(seconds == 0) {
					task.cancel();
				}
			}
		}, 20L, 20L);
	}
	
	@Override
	public void run() {
		
	}

	public int getSeconds() {
		return seconds;
	}
}

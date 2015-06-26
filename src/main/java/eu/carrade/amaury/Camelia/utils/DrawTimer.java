package eu.carrade.amaury.Camelia.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import eu.carrade.amaury.Camelia.Camelia;

public class DrawTimer implements Runnable {

	public final static int SECONDS = 30;
	private int seconds = SECONDS * 10;
	private BukkitTask task = null;
	
	public void startTimer() {
		seconds = SECONDS * 10;
		
		for(Player player : Camelia.getInstance().getServer().getOnlinePlayers()) {
			player.setExp(0);
		}
		
		task = Bukkit.getScheduler().runTaskTimer(Camelia.getInstance(), new Runnable() {
			@Override
			public void run() {
				seconds--;
				
				Camelia.getInstance().getScoreManager().updateTime((int) Math.ceil((float) seconds / 10));
				
				for(Player player : Camelia.getInstance().getServer().getOnlinePlayers()) {
					player.setTotalExperience(0);
					player.setExp((float) seconds / (float) (SECONDS * 10));
				}
				
				if(seconds == 30 || seconds == 20 || seconds == 10) {
					for(Player player : Camelia.getInstance().getServer().getOnlinePlayers()) {
						player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1.5F);
					}
				}
				
				if(seconds <= 0) {
					task.cancel();
				}
			}
		}, 2L, 2L);
	}
	
	@Override
	public void run() {
		// Wut ?!
	}

	public int getSeconds() {
		return seconds;
	}
}

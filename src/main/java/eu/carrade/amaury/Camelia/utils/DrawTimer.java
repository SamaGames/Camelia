package eu.carrade.amaury.Camelia.utils;

import eu.carrade.amaury.Camelia.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;


public class DrawTimer {

	public final static int SECONDS = 59;
	private int tenthsOfSeconds = SECONDS * 10;
	private BukkitTask task = null;

	/**
	 * Starts the timer.
	 */
	public void start() {
		tenthsOfSeconds = SECONDS * 10;

		for (Player player : Camelia.getInstance().getServer().getOnlinePlayers()) {
			player.setExp(0);
		}

		task = Bukkit.getScheduler().runTaskTimer(Camelia.getInstance(), () -> {
			tenthsOfSeconds--;

			Camelia.getInstance().getScoreManager().updateTime((int) Math.ceil((float) tenthsOfSeconds / 10));

			for (Player player : Camelia.getInstance().getServer().getOnlinePlayers()) {
				player.setTotalExperience(0);
				player.setExp((float) tenthsOfSeconds / (float) (SECONDS * 10));
			}

			if (tenthsOfSeconds == 30 || tenthsOfSeconds == 20 || tenthsOfSeconds == 10) {
				for (Player player : Camelia.getInstance().getServer().getOnlinePlayers()) {
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1.5F);
				}
			}

			if (tenthsOfSeconds <= 0) {
				stop();
			}
		}, 2L, 2L);
	}

	/**
	 * Stops the timer.
	 */
	public void stop() {
		task.cancel();
	}

	/**
	 * Returns the 1/10th of seconds left.
	 *
	 * @return The tenths of seconds left.
	 */
	public int getTenthsOfSecondsLeft() {
		return tenthsOfSeconds;
	}
}

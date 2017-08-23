package eu.carrade.amaury.Camelia.utils;

import eu.carrade.amaury.Camelia.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scheduler.*;

/*
 * This file is part of Camelia.
 *
 * Camelia is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Camelia is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Camelia.  If not, see <http://www.gnu.org/licenses/>.
 */
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

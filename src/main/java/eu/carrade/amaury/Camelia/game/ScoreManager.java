package eu.carrade.amaury.Camelia.game;

import eu.carrade.amaury.Camelia.*;
import org.bukkit.*;
import org.bukkit.scoreboard.*;

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
public class ScoreManager {

	private final ScoreboardManager manager = Bukkit.getScoreboardManager();
	private final Scoreboard board = manager.getNewScoreboard();
	private final Objective objective = board.registerNewObjective("points", "dummy");

	public ScoreManager() {
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(Camelia.NAME_COLORED_BOLD + ChatColor.DARK_GRAY + " │ " + ChatColor.GRAY + "0:00");
	}

	public void updatePlayer(Drawer drawer) {
		objective.getScore(ChatColor.GOLD + drawer.getPlayer().getName()).setScore(drawer.getPoints());
	}

	public void updateTime(int seconds) {
		objective.setDisplayName(Camelia.NAME_COLORED_BOLD + ChatColor.DARK_GRAY + " │ " + getFormattedTime(seconds));
	}

	private String getFormattedTime(int seconds) {
		String str = seconds + "";
		ChatColor color = ChatColor.DARK_GREEN;
		if (str.length() == 1)
			str = "0" + str;

		if (seconds <= 45)
			color = ChatColor.GREEN;
		if (seconds <= 30)
			color = ChatColor.YELLOW;
		if (seconds <= 20)
			color = ChatColor.GOLD;
		if (seconds <= 10)
			color = ChatColor.RED;
		if (seconds <= 3)
			color = ChatColor.DARK_RED;

		return color + "0:" + str;
	}

	public void displayTo(Drawer drawer) {
		drawer.getPlayer().setScoreboard(board);
		updatePlayer(drawer);
	}

}

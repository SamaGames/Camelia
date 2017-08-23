/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, you can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.carrade.amaury.Camelia;

import eu.carrade.amaury.Camelia.drawing.*;
import eu.carrade.amaury.Camelia.drawing.whiteboard.*;
import eu.carrade.amaury.Camelia.game.*;
import eu.carrade.amaury.Camelia.game.guis.*;
import eu.carrade.amaury.Camelia.game.guis.drawing.*;
import eu.carrade.amaury.Camelia.game.turns.*;
import eu.carrade.amaury.Camelia.listeners.*;
import eu.carrade.amaury.Camelia.utils.*;
import net.samagames.api.*;
import net.samagames.api.games.themachine.*;
import org.bukkit.*;
import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.*;

import java.io.*;

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
public final class Camelia extends JavaPlugin {

	private static Camelia instance;

	public static final String NAME_WHITE = "Camelia";
	public static final String NAME_COLORED = ChatColor.AQUA + "Camelia";
	public static final String NAME_COLORED_BOLD = ChatColor.AQUA + "" + ChatColor.BOLD + "Camelia";

	private Configuration arenaConfig;

	private GameManager gameManager;
	private DrawTurnsManager drawTurnsManager;
	private DrawingManager drawingManager;
	private GuiManager guiManager;
	private Whiteboard whiteboard;
	private CountdownTimer timer;
	private ScoreManager scoreManager;

	private DrawingGuiManager drawingGuiManager;

	private CoherenceMachine machine;

	@Override
	public void onEnable() {

		instance = this;

		/** *** Arena config *** **/
		File arenaFile = new File(getServer().getWorlds().get(0).getWorldFolder(), "arena.yml");
		if (!arenaFile.exists()) {
			getLogger().severe("#==================[Fatal exception report]==================#");
			getLogger().severe("# The arena.yml description file was NOT FOUND.              #");
			getLogger().severe("# The plugin cannot load without it, please create it.       #");
			getLogger().severe("# The file path is the following :                           #");
			getLogger().severe(arenaFile.getAbsolutePath());
			getLogger().severe("#============================================================#");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
		arenaConfig.setDefaults(YamlConfiguration.loadConfiguration(new File(getDataFolder(), "arena.yml")));


		/** *** Managers *** **/
		gameManager = new GameManager();
		drawTurnsManager = new DrawTurnsManager();
		drawingManager = new DrawingManager();
		guiManager = new GuiManager();
		whiteboard = new Whiteboard();
		timer = new CountdownTimer();
		scoreManager = new ScoreManager();

		drawingGuiManager = new DrawingGuiManager();


		SamaGamesAPI.get().getGameManager().registerGame(gameManager);

		machine = SamaGamesAPI.get().getGameManager().getCoherenceMachine();

		/** *** Listeners *** **/
		//getServer().getPluginManager().registerEvents(new PlayersConnectionListener(), this);
		getServer().getPluginManager().registerEvents(new DrawListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new GameListener(), this);
		getServer().getPluginManager().registerEvents(new GuiListener(), this);

		CommandListener command = new CommandListener();

		getCommand("mot").setExecutor(command);
		getCommand("word").setExecutor(command);
		getCommand("options").setExecutor(command);
		getCommand("hint").setExecutor(command);


		/** *** Reload handling *** **/
		getServer().getOnlinePlayers().forEach(gameManager::playerJoin);

		World world = getServer().getWorlds().get(0);
		world.setGameRuleValue("doDaylightCycle", "false");
		world.setFullTime(arenaConfig.getLong("map.hubDayTime", 6000));
	}

	public static Camelia getInstance() {
		return instance;
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public DrawTurnsManager getDrawTurnsManager() {
		return drawTurnsManager;
	}

	public DrawingManager getDrawingManager() {
		return drawingManager;
	}

	public GuiManager getGuiManager() {
		return guiManager;
	}

	public Whiteboard getWhiteboard() {
		return whiteboard;
	}

	public Configuration getArenaConfig() {
		return arenaConfig;
	}

	public DrawingGuiManager getDrawingGuiManager() {
		return drawingGuiManager;
	}

	public CoherenceMachine getCoherenceMachine() {
		return machine;
	}

	public CountdownTimer getCountdownTimer() {
		return timer;
	}

	public ScoreManager getScoreManager() {
		return scoreManager;
	}

	public void disable() {
		setEnabled(false);
	}
}

/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, you can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.carrade.amaury.Camelia;

import eu.carrade.amaury.Camelia.drawing.DrawingManager;
import eu.carrade.amaury.Camelia.drawing.whiteboard.Whiteboard;
import eu.carrade.amaury.Camelia.game.GameManager;
import eu.carrade.amaury.Camelia.game.GuiManager;
import eu.carrade.amaury.Camelia.game.ScoreManager;
import eu.carrade.amaury.Camelia.game.turns.DrawTurnsManager;
import eu.carrade.amaury.Camelia.listeners.CommandListener;
import eu.carrade.amaury.Camelia.listeners.DrawListener;
import eu.carrade.amaury.Camelia.listeners.GameListener;
import eu.carrade.amaury.Camelia.listeners.InventoryListener;
import eu.carrade.amaury.Camelia.utils.CountdownTimer;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.themachine.CoherenceMachine;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public final class Camelia extends JavaPlugin {

	private static Camelia instance;

	public static final String NAME_WHITE = "Camelia";
	public static final String NAME_COLORED = ChatColor.AQUA + "Camelia";
	public static final String NAME_COLORED_BOLD = ChatColor.AQUA + "" + ChatColor.BOLD + "Camelia";

	private Configuration arenaConfig;

	private GameManager gameManager;
	private DrawTurnsManager drawTurnsManager;
	private DrawingManager drawingManager;
	private Whiteboard whiteboard;
	private GuiManager guiManager;
	private CountdownTimer timer;
	private ScoreManager scoreManager;

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
		whiteboard = new Whiteboard();
		guiManager = new GuiManager();
		timer = new CountdownTimer();
		scoreManager = new ScoreManager();


		SamaGamesAPI.get().getGameManager().registerGame(gameManager);

		machine = SamaGamesAPI.get().getGameManager().getCoherenceMachine();

		/** *** Listeners *** **/
		//getServer().getPluginManager().registerEvents(new PlayersConnectionListener(), this);
		getServer().getPluginManager().registerEvents(new DrawListener(), this);
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new GameListener(), this);

		CommandListener command = new CommandListener();

		getCommand("mot").setExecutor(command);
		getCommand("word").setExecutor(command);
		getCommand("indice").setExecutor(command);
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

	public Whiteboard getWhiteboard() {
		return whiteboard;
	}

	public Configuration getArenaConfig() {
		return arenaConfig;
	}

	public GuiManager getGuiManager() {
		return guiManager;
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

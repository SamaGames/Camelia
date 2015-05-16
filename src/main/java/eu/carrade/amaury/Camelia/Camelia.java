/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.carrade.amaury.Camelia;

import eu.carrade.amaury.Camelia.game.GameManager;
import eu.carrade.amaury.Camelia.game.Whiteboard;
import eu.carrade.amaury.Camelia.listeners.DrawListener;
import eu.carrade.amaury.Camelia.listeners.PlayersConnectionListener;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Camelia extends JavaPlugin {

	private static Camelia instance;

	private Configuration arenaConfig;

	private GameManager gameManager;
	private Whiteboard whiteboard;
	
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
		whiteboard = new Whiteboard();


		/** *** Listeners *** **/
		getServer().getPluginManager().registerEvents(new PlayersConnectionListener(), this);
		getServer().getPluginManager().registerEvents(new DrawListener(), this);

	}

	public static Camelia getInstance() {
		return instance;
	}

	public GameManager getGameManager() {
		return gameManager;
	}

	public Whiteboard getWhiteboard() {
		return whiteboard;
	}

	public Configuration getArenaConfig() {
		return arenaConfig;
	}


	public void disable() {
		setEnabled(false);
	}
}

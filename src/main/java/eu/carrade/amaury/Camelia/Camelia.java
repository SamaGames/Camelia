/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, you can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.carrade.amaury.Camelia;

import eu.carrade.amaury.Camelia.game.GameManager;
import eu.carrade.amaury.Camelia.listeners.PlayersConnectionListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Camelia extends JavaPlugin {

	private static Camelia instance;

	private GameManager gameManager;
	
	@Override
	public void onEnable() {

		instance = this;


		/** *** Managers *** **/
		gameManager = new GameManager();


		/** *** Listeners *** **/
		getServer().getPluginManager().registerEvents(new PlayersConnectionListener(), this);

	}

	public static Camelia getInstance() {
		return instance;
	}

	public GameManager getGameManager() {
		return gameManager;
	}
}

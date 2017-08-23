package eu.carrade.amaury.Camelia.game;


import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.drawing.colors.colors.*;
import eu.carrade.amaury.Camelia.drawing.colors.core.*;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.*;
import eu.carrade.amaury.Camelia.game.turns.*;
import eu.carrade.amaury.Camelia.utils.*;
import net.samagames.api.*;
import net.samagames.tools.BarAPI.*;
import net.samagames.tools.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.logging.*;

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
public class Drawer {


	private final String STORAGE_KEY_OPTION_HARD = "camelia.hard";
	private final String STORAGE_KEY_OPTION_SOUNDS = "camelia.sounds";
	private final String STORAGE_KEY_OPTION_WORD_DISPLAY = "camelia.wordDisplayType";


	private final UUID playerID;

	private boolean drawing = false;

	private Map<Integer, DrawTool> drawTools = new HashMap<>();
	private PixelColor color = new ColorGreen(ColorType.BASIC);

	private int page = 0;

	private boolean foundCurrentWord = false;
	private int points = 0;

	private DisplayType wordDisplay = DisplayType.ACTION_BAR;
	private Boolean hardWordsEnabled = false;
	private Boolean soundsEnabled = true;


	public Drawer(UUID playerID) {
		this.playerID = playerID;

		// Loading the tools
		for (Map.Entry<Integer, Class<? extends DrawTool>> toolClass : Camelia.getInstance().getDrawingManager().getDrawTools().entrySet()) {
			try {
				DrawTool tool = toolClass.getValue().getConstructor(this.getClass()).newInstance(this);

				drawTools.put(toolClass.getKey(), tool);

			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				Camelia.getInstance().getLogger().log(Level.SEVERE, "Cannot register the tool " + toolClass.getValue().getSimpleName() + " to the drawer " + playerID, e);
			}
		}

		Bukkit.getScheduler().runTaskAsynchronously(Camelia.getInstance(), () -> {
			try {
				wordDisplay = DisplayType.valueOf(SamaGamesAPI.get().getSettingsManager().getSetting(playerID, STORAGE_KEY_OPTION_WORD_DISPLAY));
			} catch(IllegalArgumentException ignored) {} // Default value

			hardWordsEnabled = SamaGamesAPI.get().getSettingsManager().isEnabled(playerID, STORAGE_KEY_OPTION_HARD,  false);
			soundsEnabled    = SamaGamesAPI.get().getSettingsManager().isEnabled(playerID, STORAGE_KEY_OPTION_SOUNDS, true);
		});
	}


	/**
	 * The player's UUID
	 *
	 * @return The UUID
	 */
	public UUID getPlayerID() {
		return playerID;
	}

	/**
	 * The Player object
	 *
	 * @return The object. May be null.
	 */
	public Player getPlayer() {
		return Bukkit.getPlayer(playerID);
	}

	/**
	 * Is this player online?
	 *
	 * @return True if online
	 */
	public boolean isOnline() {
		return Bukkit.getOfflinePlayer(playerID).isOnline();
	}

	/**
	 * Is this player currently drawing?
	 *
	 * @return True if he is drawing
	 */
	public boolean isDrawing() {
		return drawing;
	}

	/**
	 * Set the drawing status
	 *
	 * @param drawing True if he is drawing
	 */
	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}


	/**
	 * Returns the current color of this drawer
	 *
	 * @return The color
	 */
	public PixelColor getColor() {
		return this.color;
	}

	/**
	 * Updates the current color of this drawer
	 *
	 * @param color The new color
	 */
	public void setColor(PixelColor color) {
		this.color = color;
	}

	/**
	 * Returns the currently active tool of this drawer.
	 *
	 * @return The tool, or {@code null} if there is no active tool, the player is null, not currently drawing, or
	 * disconnected.
	 */
	public DrawTool getActiveTool() {
		if (!isOnline() || !isDrawing()) return null;

		Player player = getPlayer();
		if (player == null) return null; // Just to be sure

		return drawTools.get(getPlayer().getInventory().getHeldItemSlot());
	}

	/**
	 * Updates the inventory of this player with the good content (draw tools if drawing; empty else).
	 */
	public void fillInventory() {
		Player player = getPlayer();

		player.getInventory().clear();

		if (isDrawing()) {
			for (int i = 0; i < 9; i++) {
				DrawTool tool = drawTools.get(i);
				if (tool != null)
					player.getInventory().setItem(i, tool.constructIcon(this));
			}
		}
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public DrawTool getTool(int slot) {
		return drawTools.get(slot);
	}

	public boolean hasFoundCurrentWord() {
		return foundCurrentWord;
	}

	public void setFoundCurrentWord(boolean foundCurrentWord) {
		this.foundCurrentWord = foundCurrentWord;
	}

	public int getPoints() {
		return points;
	}

	public void increasePoints(int points) {
		this.points += points;
		getPlayer().setLevel(this.points);
		Camelia.getInstance().getScoreManager().updatePlayer(this);
	}


	/**
	 * Clears the display for this drawer.
	 */
	public void clearWordDisplay() {
		ActionBar.removeMessage(playerID, true);

		Player player = getPlayer();
		if (player != null) {
			Titles.sendTitle(getPlayer(), 0, 0, 0, "", "");
			BarAPI.removeBar(getPlayer());
		}
	}

	/**
	 * Displays a word to this player
	 *
	 * @param word The word to display.
	 */
	public void displayWord(String word) {
		Player player = getPlayer();
		if (player == null) return;

		switch (wordDisplay) {
			case ACTION_BAR:
				ActionBar.sendPermanentMessage(playerID, word);
				break;

			case TITLE:
				Titles.sendTitle(player, 0, 100000, 0, "", word);
				break;

			case BOSS_BAR:
				BarAPI.setMessage(player, word);
				break;
		}
	}

	public DisplayType getWordDisplay() {
		return wordDisplay;
	}

	public void setWordDisplay(DisplayType wordDisplay) {
		this.wordDisplay = wordDisplay;

		clearWordDisplay();

		Turn currentTurn = Camelia.getInstance().getDrawTurnsManager().getCurrentTurn();
		if(currentTurn != null) {
			currentTurn.displayWord(getPlayer());
		}

		Bukkit.getScheduler().runTaskAsynchronously(Camelia.getInstance(), () -> {
			SamaGamesAPI.get().getSettingsManager().setSetting(playerID, STORAGE_KEY_OPTION_WORD_DISPLAY, wordDisplay.toString());
		});
	}


	public Boolean getHardWordsEnabled() {
		return hardWordsEnabled;
	}

	public void setHardWordsEnabled(Boolean hardWordsEnabled) {
		this.hardWordsEnabled = hardWordsEnabled;

		Bukkit.getScheduler().runTaskAsynchronously(Camelia.getInstance(), () -> {
			SamaGamesAPI.get().getSettingsManager().setSetting(playerID, STORAGE_KEY_OPTION_HARD, hardWordsEnabled.toString());
		});
	}

	public Boolean getSoundsEnabled() {
		return soundsEnabled;
	}

	public void setSoundsEnabled(Boolean soundsEnabled) {
		this.soundsEnabled = soundsEnabled;

		Bukkit.getScheduler().runTaskAsynchronously(Camelia.getInstance(), () -> {
			SamaGamesAPI.get().getSettingsManager().setSetting(playerID, STORAGE_KEY_OPTION_SOUNDS, soundsEnabled.toString());
		});
	}

	/**
	 * Where the word to guess is displayed?
	 */
	public enum DisplayType {

		/**
		 * Displayed in the action bar, just above the inventory.
		 */
		ACTION_BAR,

		/**
		 * Displayed as a /title (subtitle in fact).
		 */
		TITLE,

		/**
		 * Displayed in the boss bar
		 */
		BOSS_BAR
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Drawer drawer = (Drawer) o;

		return !(playerID != null ? !playerID.equals(drawer.playerID) : drawer.playerID != null);
	}

	@Override
	public int hashCode() {
		return playerID != null ? playerID.hashCode() : 0;
	}
}

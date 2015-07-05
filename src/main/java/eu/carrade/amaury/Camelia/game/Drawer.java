package eu.carrade.amaury.Camelia.game;


import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorGreen;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorLime;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.DrawTool;
import eu.carrade.amaury.Camelia.utils.ActionBar;
import net.samagames.tools.BarAPI.BarAPI;
import net.samagames.tools.Titles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class Drawer {

	private final UUID playerID;

	private boolean drawing = false;

	private Map<Integer, DrawTool> drawTools = new HashMap<>();
	private PixelColor color = new ColorGreen(ColorType.BASIC);

	private int page = 0;

	private boolean foundCurrentWord = false;
	private int points = 0;

    private DisplayType wordDisplay = DisplayType.ACTION_BAR;

	public Drawer(UUID playerID) {
		this.playerID = playerID;

		// Loading the tools
		for(Map.Entry<Integer, Class<? extends DrawTool>> toolClass : Camelia.getInstance().getDrawingManager().getDrawTools().entrySet()) {
			try {
				DrawTool tool = toolClass.getValue().getConstructor(this.getClass()).newInstance(this);

				drawTools.put(toolClass.getKey(), tool);

			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				Camelia.getInstance().getLogger().log(Level.SEVERE, "Cannot register the tool " + toolClass.getValue().getSimpleName() + " to the drawer " + playerID, e);
			}
		}
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
	 * @return The tool, or {@code null} if there is no active tool,
	 * the player is null, not currently drawing, or disconnected.
	 */
	public DrawTool getActiveTool() {
		if(!isOnline() || !isDrawing()) return null;

		Player player = getPlayer();
		if(player == null) return null; // Just to be sure

		return drawTools.get(getPlayer().getInventory().getHeldItemSlot());
	}

	/**
	 * Updates the inventory of this player with the good content (draw tools if
	 * drawing; empty else).
	 */
	public void fillInventory() {
		Player player = getPlayer();

		player.getInventory().clear();

		if(isDrawing()) {
			for(int i = 0; i < 9; i++) {
				DrawTool tool = drawTools.get(i);
				if(tool != null)
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
        if(player != null) {
            Titles.sendTitle(getPlayer(), 0, 0, 0, "", "");
            BarAPI.removeBar(getPlayer());
        }
    }

    /**
     * Displays a word to this player
     * @param word The word to display.
     */
    public void displayWord(String word) {
        Player player = getPlayer();
        if(player == null) return;

        switch(wordDisplay) {
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
		Camelia.getInstance().getDrawTurnsManager().getCurrentTurn().displayWord(getPlayer());
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

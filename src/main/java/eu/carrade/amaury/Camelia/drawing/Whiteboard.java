package eu.carrade.amaury.Camelia.drawing;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorWhite;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorUtils;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import eu.carrade.amaury.Camelia.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.logging.Level;


public class Whiteboard {

	/**
	 * An angle under the whiteboard, with the smallest two coordinates (x and z).
	 */
	private Location bottomAngle = null;

	/**
	 * An angle above the whiteboard, opposed to the first one, so with the biggest
	 * two coordinates (x and z).
	 */
	private Location topAngle = null;
	
	private final int width;
	private final int height;
	
	private final PixelColor[][] board;
	private final Boolean[][] locked;

	public Whiteboard() {
		try {

			Location configCorner1 = Utils.stringToLocation(Camelia.getInstance().getArenaConfig().getString("whiteboard.corner1"));
			Location configCorner2 = Utils.stringToLocation(Camelia.getInstance().getArenaConfig().getString("whiteboard.corner2"));

			bottomAngle = new Location(configCorner1.getWorld(),
					Math.min(configCorner1.getBlockX(), configCorner2.getBlockX()),
					Math.min(configCorner1.getBlockY(), configCorner2.getBlockY()),
					Math.min(configCorner1.getBlockZ(), configCorner2.getBlockZ()));

			topAngle = new Location(configCorner1.getWorld(),
					Math.max(configCorner1.getBlockX(), configCorner2.getBlockX()),
					Math.max(configCorner1.getBlockY(), configCorner2.getBlockY()),
					Math.max(configCorner1.getBlockZ(), configCorner2.getBlockZ()));


		} catch (IllegalArgumentException e) {
			Camelia.getInstance().getLogger().log(Level.SEVERE, "Invalid whiteboard configuration!!!", e);
			Camelia.getInstance().getLogger().log(Level.SEVERE, "Disabling the plugin.");
			Camelia.getInstance().disable();
		}

		
		if(bottomAngle != null && topAngle != null) {
			width = bottomAngle.getBlockX() == topAngle.getBlockX() ? topAngle.getBlockZ() - bottomAngle.getBlockZ() : topAngle.getBlockX() - bottomAngle.getBlockX() + 1;
			height = topAngle.getBlockY() - bottomAngle.getBlockY() + 1;

			board = new PixelColor[width][height];

			locked = new Boolean[width][height];
			
			clearBoard(); // To remove all null values
		}
		else {
			width = height = 0;
			board = null;
			locked = null;
		}
	}


	/**
	 * Returns true if this location is on the whiteboard
	 *
	 * @param location The location
	 *
	 * @return True if on the whiteboard.
	 */
	public boolean isOnTheWhiteboard(Location location) {

		return location != null
				&& location.getBlockX() >= bottomAngle.getBlockX() && location.getBlockY() >= bottomAngle.getY() && location.getBlockZ() >= bottomAngle.getBlockZ()
				&& location.getBlockX() <= topAngle.getBlockX() && location.getBlockY() <= topAngle.getBlockY() && location.getBlockZ() <= topAngle.getBlockZ();

	}

	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param color The block to set.
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard)
	 */
	public boolean setBlock(Location location, PixelColor color) {
		return setBlock(location, color, true);
	}

	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param color The block to set.
	 * @param mix If true, mix this color with the old one.
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard)
	 */
	public boolean setBlock(final Location location, PixelColor color, boolean mix) {
		if(!isOnTheWhiteboard(location)) {
			return false;
		}
		
		final int unit = bottomAngle.getBlockX() == topAngle.getBlockX() ? location.getBlockZ() - bottomAngle.getBlockZ() : location.getBlockX() - bottomAngle.getBlockX();
		PixelColor baseColor = board[unit][location.getBlockY() - bottomAngle.getBlockY()];
		PixelColor finalColor = mix ? ColorUtils.getMix(color, baseColor) : color;
		
		if(!locked[unit][location.getBlockY() - bottomAngle.getBlockY()]) {
			Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), new Runnable() {
				@Override
				public void run() {
					locked[unit][location.getBlockY() - bottomAngle.getBlockY()] = false;
				}
			}, 10L);
			
			locked[unit][location.getBlockY() - bottomAngle.getBlockY()] = true;
		
			board[unit][location.getBlockY() - bottomAngle.getBlockY()] = finalColor;

			location.getBlock().setType(finalColor.getBlock().getType());
			location.getBlock().setData(finalColor.getBlock().getData());
			return true;
		}

		return false;
	}


	/**
	 * Returns the block of the whiteboard targeted by the given player.
	 *
	 * @param player The player.
	 *
	 * @return The block. {@code null} if the target is out of the screen, or too far (200 blocks).
	 */
	public Location getTargetBlock(Player player) {
		Block targetBlock = Utils.getTargetBlock(player, 200);

		if(targetBlock == null) return null;

		Location target = targetBlock.getLocation();

		if(isOnTheWhiteboard(target)) {
			return target;
		}

		return null;
	}
	
	public void clearBoard() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				locked[x][y] = false;
				if(bottomAngle.getBlockX() == topAngle.getBlockX()) {
					setBlock(new Location(Bukkit.getWorlds().get(0), bottomAngle.getBlockX(), bottomAngle.getBlockY() + y, bottomAngle.getBlockZ() + x), new ColorWhite(ColorType.BASIC), false);
				} else {
					setBlock(new Location(Bukkit.getWorlds().get(0), bottomAngle.getBlockX() + x, bottomAngle.getBlockY() + y, bottomAngle.getBlockZ()), new ColorWhite(ColorType.BASIC), false);
				}
			}
		}
	}
}

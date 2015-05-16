package eu.carrade.amaury.Camelia.game;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.utils.Utils;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.logging.Level;


public class Whiteboard {

	/**
	 * An angle under the whiteboard, with the smallest two coordinates (x and z).
	 */
	private Location bottomAngle;

	/**
	 * An angle above the whiteboard, opposed to the first one, so with the biggest
	 * two coordinates (x and z).
	 */
	private Location topAngle;


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
	 * @param type The block's type
	 * @param data The block's data value
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard)
	 */
	public boolean setBlock(Location location, Material type, byte data) {
		if(!isOnTheWhiteboard(location)) {
			return false;
		}

		location.getBlock().setType(type);
		location.getBlock().setData(data);

		return true;
	}

	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param type The block's type
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard)
	 */
	public boolean setBlock(Location location, Material type) {
		return setBlock(location, type, (byte) 0);
	}

	/**
	 * Sets a block of the whiteboard
	 *
	 * @param location The location
	 * @param type The block's type
	 * @param color The block's color
	 *
	 * @return True if the block was set (i.e. the location is in the whiteboard)
	 */
	public boolean setBlock(Location location, Material type, DyeColor color) {
		return setBlock(location, type, type == Material.WOOL ? color.getWoolData() : color.getData());
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
}

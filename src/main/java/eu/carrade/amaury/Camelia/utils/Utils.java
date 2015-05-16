package eu.carrade.amaury.Camelia.utils;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Utils {


	/**
	 * Converts a string (in the config file) to a Location object.
	 *
	 * @param locationInConfig
	 *            A string; format "x;y;z" or "x;y;z;yaw" or "x;y;z;yaw;pitch".
	 * @return The Location object, for the main world (first one).
	 *
	 * @throws IllegalArgumentException
	 *             if the format is not good.
	 */
	public static Location stringToLocation(String locationInConfig) {
		String[] coords = locationInConfig.split(";");
		if (coords.length < 3) {
			throw new IllegalArgumentException("Invalid location: " + locationInConfig);
		}

		try {
			Location location = new Location(Bukkit.getServer().getWorlds().get(0), Double.valueOf(coords[0]) + 0.5,
					Double.valueOf(coords[1]), Double.valueOf(coords[2]) + 0.5);

			if (coords.length >= 4) {
				location.setYaw(Float.valueOf(coords[3]));

				if (coords.length >= 5) {
					location.setPitch(Float.valueOf(coords[4]));
				}
			}

			return location;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid location (NaN!): " + locationInConfig);
		}
	}


	/**
	 * Returns the block targeted by the given player.
	 *
	 * @param player The player
	 * @param maxRange The maximal search distance, in blocks.
	 *
	 * @return The block, or null if no block found.
	 */
	public static Block getTargetBlock(Player player, int maxRange) {
		Block block;
		Location location = player.getEyeLocation().clone();
		Vector progress = location.getDirection().normalize().clone().multiply(0.70);

		maxRange = (100 * maxRange / 70);

		int loop = 0;
		while (loop < maxRange) {
			loop++;
			location.add(progress);
			block = location.getWorld().getBlockAt(location);

			if (!block.getType().equals(Material.AIR)) {
				return location.getBlock();
			}
		}

		return null;
	}

}

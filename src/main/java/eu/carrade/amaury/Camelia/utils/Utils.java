package eu.carrade.amaury.Camelia.utils;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * Cuts a string in lines to be inserted in a lore.
	 *
	 * @param text The original text.
	 * @return A list of lines.
	 */
	public static List<String> stringToLore(String text) {
		List<String> lines = new ArrayList<>();
		String[] words = text.split(" ");
		int line = 0;
		lines.add(line, "");

		String previousWords = "";

		for (String word : words) {
			previousWords += word;

			int chars = (lines.get(line) + " " + word).length() - countColors(lines.get(line) + " " + word);

			if (chars >= 45) {
				line++;
				lines.add(line, ChatColor.getLastColors(previousWords) + word);
			}

			else {
				if (lines.get(line).equals("")) {
					lines.set(line, ChatColor.getLastColors(previousWords) + word);
				} else {
					lines.set(line, lines.get(line) + " " + word);
				}
			}
		}

		for(int k = 0; k < lines.size(); k++) {
			lines.set(k, lines.get(k).trim());
		}

		return lines;
	}

	private static int countColors(String str) {
		int count = 0;
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == ChatColor.COLOR_CHAR && ChatColor.getByChar(str.charAt(i + 1)) != null) {
				count++;
			}
		}
		return count;
	}


	/**
	 * Returns a valid slot to use for this tool.
	 *
	 * @param toolRawSlot The tool' slot
	 * @return The slot to use: the declared slot if below or equals to 8; 8 else.
	 */
	public static Integer getDrawToolRealSlot(int toolRawSlot) {
		return Math.min(Math.abs(toolRawSlot), 8);
	}

	public static ItemStack quickItemStack(Material type, int amount, byte data, String name, List<String> lore) {
		return quickItemStack(type, amount, data, name, lore, false);
	}

	public static ItemStack quickItemStack(Material type, int amount, byte data, String name, List<String> lore, boolean removeVanillaInfos) {
		ItemStack item = new ItemStack(type, amount, data);
		setNameLore(item, name, lore);

		if(removeVanillaInfos) {
			ItemMeta meta = item.getItemMeta();
			meta.addItemFlags(ItemFlag.values()); // All is hidden
			item.setItemMeta(meta);
		}

		return item;
	}
	
	public static ItemStack setNameLore(ItemStack item, String str, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(str);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack setName(ItemStack item, String str) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(str);
		item.setItemMeta(meta);
		return item;
	}


}

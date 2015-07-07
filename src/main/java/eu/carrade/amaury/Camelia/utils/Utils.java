package eu.carrade.amaury.Camelia.utils;


import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.util.Vector;

import java.text.*;
import java.util.*;


public class Utils {


	/**
	 * Converts a string (in the config file) to a Location object.
	 *
	 * @param locationInConfig A string; format "x;y;z" or "x;y;z;yaw" or "x;y;z;yaw;pitch".
	 *
	 * @return The Location object, for the main world (first one).
	 * @throws IllegalArgumentException if the format is not good.
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
	 * @param player   The player
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
	 * Use {@code \{bl\}} everywhere to insert a blank line.
	 *
	 * @param text The original text.
	 *
	 * @return A list of lines.
	 */
	public static List<String> stringToLore(String text) {

		// Ensures all "{bl}" are a word.
		text = text.replace("{bl}", " {bl} ");


		List<String> lines = new ArrayList<>();
		String[] words = text.split(" ");
		int line = 0;
		lines.add(line, "");

		String previousWords = "";

		for (String word : words) {
			previousWords += word;

			if(word.equalsIgnoreCase("{bl}")) {
				line++;
				lines.add(line, ""); // Empty line.
				line++;
				lines.add(line, ""); // Beginning of the next line.
				continue;
			}

			int chars = (lines.get(line) + " " + word).length() - countColors(lines.get(line) + " " + word);

			if (chars >= 45) {
				line++;
				lines.add(line, ChatColor.getLastColors(previousWords) + word);
			} else {
				if (lines.get(line).equals("")) {
					lines.set(line, ChatColor.getLastColors(previousWords) + word);
				} else {
					lines.set(line, lines.get(line) + " " + word);
				}
			}
		}

		for (int k = 0; k < lines.size(); k++) {
			lines.set(k, lines.get(k).trim());
		}

		return lines;
	}

	private static int countColors(String str) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ChatColor.COLOR_CHAR && ChatColor.getByChar(str.charAt(i + 1)) != null) {
				count++;
			}
		}
		return count;
	}


	/**
	 * Returns a valid slot to use for this tool.
	 *
	 * @param toolRawSlot The tool' slot
	 *
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

		if (removeVanillaInfos) {
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

	public static String getFormattedWord(String word) {
		return ChatColor.GOLD + "" + ChatColor.BOLD + word.replace("", " ").trim().toUpperCase();
	}

	public static String getFormattedBlank(String word) {
		String str = "";
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == ' ') {
				str += "   " + word.charAt(i);
			} else {
				str += " " + word.charAt(i);
			}
		}
		return ChatColor.GOLD + "" + ChatColor.BOLD + str.trim();
	}

	public static String getNewWordBlank(String word) {
		String str = "";
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == ' ') {
				str += " ";
			} else {
				str += "_";
			}
		}
		return str.trim();
	}

	/**
	 * @author http://stackoverflow.com/questions/3322152/is-there-a-way-to-get-rid-of-accents-and-convert-a-whole-string-to-regular-lette
	 */
	public static String removeAccents(String string) {
		StringBuilder sb = new StringBuilder(string.length());
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		for (char c : string.toCharArray()) {
			if (c <= '\u007F') sb.append(c);
		}
		return sb.toString();
	}

	public static boolean wideComparison(String str1, String str2) {
		if (str1 == null && str2 == null) return true;
		if (str1 == null || str2 == null) return false;

		return removeAccents(str1.toLowerCase()).equals(removeAccents(str2.toLowerCase()));
	}
}

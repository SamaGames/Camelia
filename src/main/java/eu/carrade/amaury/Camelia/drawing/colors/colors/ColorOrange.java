package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.*;
import org.bukkit.*;


public class ColorOrange extends PixelColor {

	public ColorOrange(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.ORANGE), new GameBlock(Material.STAINED_CLAY, DyeColor.ORANGE), new GameBlock(Material.SAND, (byte) 1));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.GOLD + "" + ChatColor.BOLD + "Orange";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.GOLD + "" + ChatColor.BOLD + "Orange";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.GOLD + "" + ChatColor.BOLD + "Orange Désert";
	}
}
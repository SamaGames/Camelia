package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.*;
import org.bukkit.*;


public class ColorLightGray extends PixelColor {

	public ColorLightGray(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.SILVER), new GameBlock(Material.STAINED_CLAY, DyeColor.SILVER), new GameBlock(Material.STONE, (byte) 5));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.GRAY + "" + ChatColor.BOLD + "Gris Clair";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.GRAY + "" + ChatColor.BOLD + "Gris Clair";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.GRAY + "" + ChatColor.BOLD + "Gris Ardoise";
	}
}

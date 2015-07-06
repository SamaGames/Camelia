package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.*;
import org.bukkit.*;


public class ColorBrown extends PixelColor {

	public ColorBrown(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.BROWN), new GameBlock(Material.STAINED_CLAY, DyeColor.BROWN), new GameBlock(Material.DIRT, (byte) 1));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Brun";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Brun";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Brun Terreux";
	}
}

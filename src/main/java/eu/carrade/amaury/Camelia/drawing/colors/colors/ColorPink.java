package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.*;
import org.bukkit.*;


public class ColorPink extends PixelColor {

	public ColorPink(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.PINK), new GameBlock(Material.STAINED_CLAY, DyeColor.PINK), new GameBlock(Material.STONE, (byte) 1));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Rose";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Rose";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Rose Granit";
	}
}

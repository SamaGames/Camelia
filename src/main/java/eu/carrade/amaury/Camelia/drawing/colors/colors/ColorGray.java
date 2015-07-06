package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.*;
import org.bukkit.*;


public class ColorGray extends PixelColor {

	public ColorGray(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.GRAY), new GameBlock(Material.STAINED_CLAY, DyeColor.GRAY), new GameBlock(Material.BEDROCK));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Gris";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Gris";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Gris Terrestre";
	}
}

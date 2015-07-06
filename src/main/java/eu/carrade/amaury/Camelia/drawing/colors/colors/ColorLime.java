package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.*;
import org.bukkit.*;


public class ColorLime extends PixelColor {

	public ColorLime(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.LIME), new GameBlock(Material.STAINED_CLAY, DyeColor.LIME), new GameBlock(Material.EMERALD_BLOCK));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.GREEN + "" + ChatColor.BOLD + "Vert Clair";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.GREEN + "" + ChatColor.BOLD + "Vert Clair";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.GREEN + "" + ChatColor.BOLD + "Vert Emeraude";
	}
}

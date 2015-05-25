package eu.carrade.amaury.Camelia.drawing.colors.colors;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;

public class ColorGray extends PixelColor {

	public ColorGray(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.GRAY), new GameBlock(Material.STAINED_CLAY, DyeColor.GRAY), new GameBlock(Material.BEDROCK));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.GRAY + "" + ChatColor.BOLD + "Gris";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.GRAY + "" + ChatColor.BOLD + "Gris";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.GRAY + "" + ChatColor.BOLD + "Gris";
	}
}

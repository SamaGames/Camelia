package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

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

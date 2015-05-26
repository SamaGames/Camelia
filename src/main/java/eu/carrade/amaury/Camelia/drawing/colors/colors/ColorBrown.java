package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class ColorBrown extends PixelColor {

	public ColorBrown(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.BROWN), new GameBlock(Material.STAINED_CLAY, DyeColor.BROWN), new GameBlock(Material.DIRT, (byte) 1));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Marron";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Marron";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Marron";
	}
}

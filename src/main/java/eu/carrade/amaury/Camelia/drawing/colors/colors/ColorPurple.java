package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class ColorPurple extends PixelColor {

	public ColorPurple(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.PURPLE), new GameBlock(Material.STAINED_CLAY, DyeColor.PURPLE), new GameBlock(Material.DIAMOND_BLOCK));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Violet";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Violet";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Violet";
	}
}

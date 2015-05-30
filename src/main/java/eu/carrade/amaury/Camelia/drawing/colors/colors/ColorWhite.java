package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class ColorWhite extends PixelColor {

	public ColorWhite(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.WHITE), new GameBlock(Material.STAINED_CLAY, DyeColor.WHITE), new GameBlock(Material.STONE, (byte) 3));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.WHITE + "" + ChatColor.BOLD + "Blanc";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.WHITE + "" + ChatColor.BOLD + "Blanc";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.WHITE + "" + ChatColor.BOLD + "Blanc Ardoise Claire";
	}
}

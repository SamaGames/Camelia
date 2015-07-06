package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;


public class ColorGreen extends PixelColor {

	public ColorGreen(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.GREEN), new GameBlock(Material.STAINED_CLAY, DyeColor.GREEN), new GameBlock(Material.PRISMARINE, (byte) 2));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Vert";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Vert";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Vert Moussu";
	}
}

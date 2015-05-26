package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class ColorCyan extends PixelColor {

	public ColorCyan(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.CYAN), new GameBlock(Material.STAINED_CLAY, DyeColor.CYAN), new GameBlock(Material.PRISMARINE));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.AQUA + "" + ChatColor.BOLD + "Cyan";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.AQUA + "" + ChatColor.BOLD + "Cyan";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.AQUA + "" + ChatColor.BOLD + "Cyan";
	}
}

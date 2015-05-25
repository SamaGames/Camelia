package eu.carrade.amaury.Camelia.drawing.colors.colors;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;

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
		return ChatColor.GREEN + "" + ChatColor.BOLD + "Vert Clair";
	}
}

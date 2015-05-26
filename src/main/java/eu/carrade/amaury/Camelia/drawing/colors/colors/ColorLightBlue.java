package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class ColorLightBlue extends PixelColor {

	public ColorLightBlue(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.LIGHT_BLUE), new GameBlock(Material.STAINED_CLAY, DyeColor.LIGHT_BLUE), new GameBlock(Material.PACKED_ICE));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.AQUA + "" + ChatColor.BOLD + "Bleu Clair";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.AQUA + "" + ChatColor.BOLD + "Bleu Clair";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.AQUA + "" + ChatColor.BOLD + "Bleu Clair";
	}
}

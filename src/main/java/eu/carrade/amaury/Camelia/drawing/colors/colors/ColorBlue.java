package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class ColorBlue extends PixelColor {

	public ColorBlue(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.BLUE), new GameBlock(Material.STAINED_CLAY, DyeColor.BLUE), new GameBlock(Material.LAPIS_BLOCK));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.BLUE + "" + ChatColor.BOLD + "Bleu";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.BLUE + "" + ChatColor.BOLD + "Bleu";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.BLUE + "" + ChatColor.BOLD + "Bleu Outremer";
	}
}

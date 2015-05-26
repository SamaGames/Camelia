package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class ColorYellow extends PixelColor {

	public ColorYellow(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.YELLOW), new GameBlock(Material.STAINED_CLAY, DyeColor.YELLOW), new GameBlock(Material.SPONGE));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.YELLOW + "" + ChatColor.BOLD + "Jaune";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.YELLOW + "" + ChatColor.BOLD + "Jaune";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.YELLOW + "" + ChatColor.BOLD + "Jaune";
	}
}

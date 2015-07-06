package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.*;
import org.bukkit.*;


public class ColorBlack extends PixelColor {

	public ColorBlack(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.BLACK), new GameBlock(Material.STAINED_CLAY, DyeColor.BLACK), new GameBlock(Material.COAL_BLOCK));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.BLACK + "" + ChatColor.BOLD + "Noir";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.BLACK + "" + ChatColor.BOLD + "Noir";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.BLACK + "" + ChatColor.BOLD + "Noir Fusain";
	}
}

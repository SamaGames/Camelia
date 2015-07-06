package eu.carrade.amaury.Camelia.drawing.colors.colors;

import eu.carrade.amaury.Camelia.drawing.colors.core.*;
import org.bukkit.*;


public class ColorRed extends PixelColor {

	public ColorRed(ColorType type) {
		super(new GameBlock(Material.WOOL, DyeColor.RED), new GameBlock(Material.STAINED_CLAY, DyeColor.RED), new GameBlock(Material.NETHERRACK));
		this.type = type;
	}

	@Override
	public String getBasicDisplayName() {
		return ChatColor.RED + "" + ChatColor.BOLD + "Rouge";
	}

	@Override
	public String getBetterDisplayName() {
		return ChatColor.RED + "" + ChatColor.BOLD + "Rouge";
	}

	@Override
	public String getRoughDisplayName() {
		return ChatColor.RED + "" + ChatColor.BOLD + "Rouge Enfers";
	}
}

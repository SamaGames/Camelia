package eu.carrade.amaury.Camelia.drawing.colors.core;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.DyeColor;

import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorBlack;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorBlue;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorBrown;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorCyan;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorGray;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorGreen;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorLightBlue;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorLightGray;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorLime;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorMagenta;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorOrange;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorPink;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorPurple;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorRed;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorWhite;
import eu.carrade.amaury.Camelia.drawing.colors.colors.ColorYellow;

public final class ColorUtils {
	
	private static Set<BasicMix> mixes = new HashSet<BasicMix>();
	
	private ColorUtils() {
		// http://minecraft.gamepedia.com/File:Minecraft-DyeGuide-1.7.2.png
		mixes.add(new BasicMix(DyeColor.BLUE, DyeColor.GREEN, DyeColor.CYAN));
		mixes.add(new BasicMix(DyeColor.BLUE, DyeColor.RED, DyeColor.MAGENTA));
		mixes.add(new BasicMix(DyeColor.WHITE, DyeColor.RED, DyeColor.PINK));
		mixes.add(new BasicMix(DyeColor.WHITE, DyeColor.GREEN, DyeColor.LIME));
		mixes.add(new BasicMix(DyeColor.WHITE, DyeColor.BLUE, DyeColor.LIGHT_BLUE));
		mixes.add(new BasicMix(DyeColor.WHITE, DyeColor.BLACK, DyeColor.GRAY));
		mixes.add(new BasicMix(DyeColor.WHITE, DyeColor.GRAY, DyeColor.SILVER));
		mixes.add(new BasicMix(DyeColor.RED, DyeColor.YELLOW, DyeColor.ORANGE));
	}
	
	public static void init() {
		new ColorUtils();
	}
	
	/**
	 * Mixes two colors together
	 * @param The color the player tries to paint
	 * @param The color already on the board
	 * @return The new color
	 */
	public static PixelColor getMix(PixelColor color1, PixelColor color2) {
		if(color2 == null || color1 == null) return color1;
		if(color1.getType() == ColorType.ROUGH && color2.getType() != ColorType.ROUGH || color1.getType() != ColorType.ROUGH && color2.getType() == ColorType.ROUGH)
			return color1;
		DyeColor newColor = null;
		for(BasicMix mix : mixes) {
			if(mix.isMix(color1.getDyeColor(), color2.getDyeColor())) {
				newColor = mix.getMix(color1.getDyeColor(), color2.getDyeColor());
				break;
			}
		}
		if(newColor != null) {
			ColorType newType = ColorType.BASIC;
			if(color1.getType() == color2.getType()) {
				newType = color1.getType();
			} else if(color1.getType() == ColorType.BASIC && color2.getType() == ColorType.BETTER || color1.getType() == ColorType.BETTER && color2.getType() == ColorType.BASIC) {
				newType = ColorType.ROUGH;
			}
			return getPixelFromDye(newColor, newType);
		} else {
			return color1;
		}
	}
	
	/**
	 * Create a new instance with parameters
	 * @param color
	 * @param type
	 * @return A new PixelColor object
	 */
	public static PixelColor getPixelFromDye(DyeColor color, ColorType type) {
		switch(color) {
		case BLACK:
			return new ColorBlack(type);
		case BLUE:
			return new ColorBlue(type);
		case BROWN:
			return new ColorBrown(type);
		case CYAN:
			return new ColorCyan(type);
		case GRAY:
			return new ColorGray(type);
		case GREEN:
			return new ColorGreen(type);
		case LIGHT_BLUE:
			return new ColorLightBlue(type);
		case SILVER:
			return new ColorLightGray(type);
		case LIME:
			return new ColorLime(type);
		case MAGENTA:
			return new ColorMagenta(type);	
		case ORANGE:
			return new ColorOrange(type);
		case PINK:
			return new ColorPink(type);
		case PURPLE:
			return new ColorPurple(type);
		case RED:
			return new ColorRed(type);
		case WHITE:
			return new ColorWhite(type);
		case YELLOW:
			return new ColorYellow(type);
		default:
			return new ColorWhite(type);
		}
	}
}

class BasicMix {
	
	private final DyeColor color1;
	private final DyeColor color2;
	private final DyeColor result;
	
	public BasicMix(DyeColor color1, DyeColor color2, DyeColor result) {
		this.color1 = color1;
		this.color2 = color2;
		this.result = result;
	}
	
	public boolean isMix(DyeColor c1, DyeColor c2) {
		if(color1.equals(c1) && color2.equals(c2) || color1.equals(c2) && color2.equals(c1))
			return true;
		return false;
	}
	
	public DyeColor getMix(DyeColor c1, DyeColor c2) {
		if(color1.equals(c1) && color2.equals(c2) || color1.equals(c2) && color2.equals(c1))
			return this.result;
		return null;
	}
}

package eu.carrade.amaury.Camelia.drawing.colors.core;

import eu.carrade.amaury.Camelia.drawing.colors.colors.*;

import org.bukkit.DyeColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ColorUtils {
	
	private static final Set<BasicMix> mixes = new HashSet<BasicMix>();
	public static final List<PixelColor> basicColors = constructList(ColorType.BASIC);
	public static final List<PixelColor> betterColors = constructList(ColorType.BETTER);
	public static final List<PixelColor> roughColors = constructList(ColorType.ROUGH);
	
	static {

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
	
	private static List<PixelColor> constructList(ColorType type) {
		List<Integer> list = Arrays.asList(14, 12, 1, 4, 5, 13, -1, 0, 8, 9, 3, 11, 10, 2, 6, -1, 7, 15);
		List<PixelColor> result = new ArrayList<PixelColor>();
		for(int i : list) {
			if(i == -1) {
				result.add(null);
			} else {
				result.add(getPixelFromDye(DyeColor.getByData((byte) i), type));
			}
		}
		return result;
	}
	
	/**
	 * Mixes two colors together
	 * @param paintedColor The color the player tries to paint
	 * @param onBoardColor The color already on the board
	 * @return The new color
	 */
	public static PixelColor getMix(PixelColor paintedColor, PixelColor onBoardColor) {
		if(onBoardColor == null || paintedColor == null) return paintedColor;

		if(paintedColor.getType() == ColorType.ROUGH && onBoardColor.getType() != ColorType.ROUGH || paintedColor.getType() != ColorType.ROUGH && onBoardColor.getType() == ColorType.ROUGH)
			return paintedColor;


		DyeColor newColor = null;

		for(BasicMix mix : mixes) {
			if(mix.isMix(paintedColor.getDyeColor(), onBoardColor.getDyeColor())) {
				newColor = mix.getMix(paintedColor.getDyeColor(), onBoardColor.getDyeColor());
				break;
			}
		}


		if(newColor != null) {
			ColorType newType = ColorType.BASIC;

			if(paintedColor.getType() == onBoardColor.getType()) {
				newType = paintedColor.getType();
			}

			else if(paintedColor.getType() == ColorType.BASIC && onBoardColor.getType() == ColorType.BETTER || paintedColor.getType() == ColorType.BETTER && onBoardColor.getType() == ColorType.BASIC) {
				newType = ColorType.ROUGH;
			}

			return getPixelFromDye(newColor, newType);
		}

		else {
			return paintedColor;
		}
	}
	
	/**
	 * Create a new instance of {@link eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor}
	 * with parameters.
	 *
	 * @param color The color
	 * @param type The color's type
	 *
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
		return color1.equals(c1) && color2.equals(c2) || color1.equals(c2) && color2.equals(c1);
	}
	
	public DyeColor getMix(DyeColor c1, DyeColor c2) {
		return isMix(c1, c2) ? this.result : null;
	}
}

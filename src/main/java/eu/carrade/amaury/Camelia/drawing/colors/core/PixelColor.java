package eu.carrade.amaury.Camelia.drawing.colors.core;

import org.bukkit.DyeColor;


public abstract class PixelColor {

	protected final DyeColor color;
	protected final GameBlock basicBlock;
	protected final GameBlock betterBlock;
	protected final GameBlock roughBlock;
	protected ColorType type = ColorType.BASIC;

	public PixelColor(GameBlock type1, GameBlock type2, GameBlock type3) {
		this.basicBlock = type1;
		this.color = DyeColor.getByData(type1.getData());
		this.betterBlock = type2;
		this.roughBlock = type3;
	}
	
	public PixelColor(GameBlock type1, GameBlock type2, GameBlock type3, DyeColor color) {
		this.basicBlock = type1;
		this.betterBlock = type2;
		this.roughBlock = type3;
		this.color = color;
	}

	public GameBlock getBasicBlock() {
		return basicBlock;
	}
	
	public abstract String getBasicDisplayName();
	
	public GameBlock getBetterBlock() {
		return betterBlock;
	}
	
	public abstract String getBetterDisplayName();
	
	public GameBlock getRoughBlock() {
		return roughBlock;
	}
	
	public abstract String getRoughDisplayName();
	
	public DyeColor getDyeColor() {
		return DyeColor.getByData(this.basicBlock.getData());
	}
	
	public String getDisplayName() {
		if(type == ColorType.BASIC) {
			return getBasicDisplayName();
		} else if(type == ColorType.BETTER) {
			return getBetterDisplayName();
		} else if(type == ColorType.ROUGH) {
			return getRoughDisplayName();
		}
		return getBasicDisplayName();
	}
	
	public GameBlock getBlock() {
		if(type == ColorType.BASIC) {
			return getBasicBlock();
		} else if(type == ColorType.BETTER) {
			return getBetterBlock();
		} else if(type == ColorType.ROUGH) {
			return getRoughBlock();
		}
		return getBasicBlock();
	}
}

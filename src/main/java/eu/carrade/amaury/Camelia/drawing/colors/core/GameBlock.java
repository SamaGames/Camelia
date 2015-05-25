package eu.carrade.amaury.Camelia.drawing.colors.core;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public class GameBlock {

	private final Material type;
	private final byte data;
	
	public GameBlock(Material type) {
		this(type, (byte) 0);
	}
	
	public GameBlock(Material type, DyeColor color) {
		this(type, type == Material.WOOL ? color.getWoolData() : color.getData());
	}
	
	public GameBlock(Material type, byte data) {
		this.type = type;
		this.data = data;
	}

	public Material getType() {
		return type;
	}

	public byte getData() {
		return data;
	}
}

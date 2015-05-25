package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.colors.core.GameBlock;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ContinuousDrawTool;
import eu.carrade.amaury.Camelia.game.Drawer;

public class SampleTool extends ContinuousDrawTool {

	@Override
	public String getDisplayName() {
		return ChatColor.AQUA + "" + ChatColor.BOLD + "Pinceau stylé";
	}

	@Override
	public String getDescription() {
		return ChatColor.GRAY + "Description";
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.DIAMOND_SPADE);
	}

	@Override
	public int getSlot() {
		return 0;
	}

	@Override
	public void onRightClick(Location targetOnScreen, Drawer drawer) {
		GameBlock block = drawer.getColor().getBlock();
		Camelia.getInstance().getWhiteboard().setBlock(targetOnScreen, block.getType(), block.getData());
	}

	@Override
	public void onLeftClick(Location targetOnScreen, Drawer drawer) {
	}

}
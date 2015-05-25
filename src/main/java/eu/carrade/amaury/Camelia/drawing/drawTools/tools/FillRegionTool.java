package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.drawing.drawTools.core.ClicDrawTool;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class FillRegionTool extends ClicDrawTool {
	@Override
	public String getDisplayName() {
		return ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Remplissage";
	}

	@Override
	public String getDescription() {
		return ChatColor.GRAY + "Remplit une zone de couleur avec la couleur active";
	}

	@Override
	public ItemStack getIcon(Drawer drawer) {
		return new ItemStack(Material.BUCKET);
	}

	@Override
	public int getSlot() {
		return 2;
	}

	@Override
	public void onRightClick(Location targetOnScreen, Drawer drawer) {
		drawer.getPlayer().sendMessage("TODO remplissage");
	}

	@Override
	public void onLeftClick(Location targetOnScreen, Drawer drawer) {

	}
}

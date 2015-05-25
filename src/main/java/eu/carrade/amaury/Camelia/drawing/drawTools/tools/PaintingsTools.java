package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.drawing.drawTools.core.ClicDrawTool;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class PaintingsTools extends ClicDrawTool {
	@Override
	public String getDisplayName() {
		return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Fonds pré-définis";
	}

	@Override
	public String getDescription() {
		return ChatColor.GRAY + "Vous permet de changer le fond avec une sélection de belles illustrations";
	}

	@Override
	public ItemStack getIcon(Drawer drawer) {
		return new ItemStack(Material.PAINTING);
	}

	@Override
	public int getSlot() {
		return 4;
	}

	@Override
	public void onRightClick(Location targetOnScreen, Drawer drawer) {
		drawer.getPlayer().sendMessage("TODO présets");
	}

	@Override
	public void onLeftClick(Location targetOnScreen, Drawer drawer) {

	}
}

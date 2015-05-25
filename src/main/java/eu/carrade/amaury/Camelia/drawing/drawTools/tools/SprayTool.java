package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.drawing.drawTools.core.ClicDrawTool;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class SprayTool extends ClicDrawTool {

	@Override
	public String getDisplayName() {
		return ChatColor.GREEN + "" + ChatColor.BOLD + "Spray";
	}

	@Override
	public String getDescription() {
		return ChatColor.GRAY + "Applique de la couleur aléatoirement dans une petite région, tel une bombe de peinture";
	}

	@Override
	public ItemStack getIcon(Drawer drawer) {
		return new ItemStack(Material.DEAD_BUSH);
	}

	@Override
	public int getSlot() {
		return 1;
	}

	@Override
	public void onRightClick(Location targetOnScreen, Drawer drawer) {
		drawer.getPlayer().sendMessage("TODO Brush");
	}

	@Override
	public void onLeftClick(Location targetOnScreen, Drawer drawer) {

	}
}

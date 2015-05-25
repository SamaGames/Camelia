package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.drawing.drawTools.core.ClicDrawTool;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;


public class ColorChooserTool extends ClicDrawTool {
	@Override
	public String getDisplayName() {
		return ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Sélecteur de couleurs";
	}

	@Override
	public String getDescription() {
		return ChatColor.GRAY + "Vous permet de choisir une couleur pour dessiner";
	}

	@Override
	public ItemStack getIcon(Drawer drawer) {
		Dye icon = new Dye(Material.INK_SACK);
		icon.setColor(drawer.getColor().getDyeColor());

		return icon.toItemStack(1);
	}

	@Override
	public int getSlot() {
		return 6;
	}

	@Override
	public void onRightClick(Location targetOnScreen, Drawer drawer) {
		drawer.getPlayer().sendMessage("TODO colors selector");
	}

	@Override
	public void onLeftClick(Location targetOnScreen, Drawer drawer) {

	}
}

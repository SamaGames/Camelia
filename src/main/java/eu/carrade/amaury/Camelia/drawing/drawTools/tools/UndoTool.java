package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.drawing.drawTools.core.ClicDrawTool;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class UndoTool extends ClicDrawTool {
	@Override
	public String getDisplayName() {
		return ChatColor.RED + "" + ChatColor.BOLD + "Annuler la dernière action"
				+ ChatColor.GRAY + " (clic gauche : refaire)";
	}

	@Override
	public String getDescription() {
		return ChatColor.GRAY + "Annule la dernière action effectuée. Appuyez plusieurs fois pour remonter dans l'historique. Cliquez-gauche pour revenir en avant.";
	}

	@Override
	public ItemStack getIcon(Drawer drawer) {
		return new ItemStack(Material.BARRIER);
	}

	@Override
	public int getSlot() {
		return 7;
	}

	@Override
	public void onRightClick(Location targetOnScreen, Drawer drawer) {
		drawer.getPlayer().sendMessage("TODO Undo");
	}

	@Override
	public void onLeftClick(Location targetOnScreen, Drawer drawer) {
		drawer.getPlayer().sendMessage("TODO Redo");
	}
}

package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.carrade.amaury.Camelia.drawing.drawTools.core.ClicDrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ToolLocator;
import eu.carrade.amaury.Camelia.drawing.whiteboard.WhiteboardLocation;
import eu.carrade.amaury.Camelia.game.Drawer;


@ToolLocator(slot = 7)
public class UndoTool extends ClicDrawTool {

	public UndoTool(Drawer drawer) {
		super(drawer);
	}

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
	public void onRightClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		drawer.getPlayer().sendMessage("TODO Undo");
	}

	@Override
	public void onLeftClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		drawer.getPlayer().sendMessage("TODO Redo");
	}
}

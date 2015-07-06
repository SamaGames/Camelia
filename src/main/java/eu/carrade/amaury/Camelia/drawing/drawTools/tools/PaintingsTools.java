package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.*;
import eu.carrade.amaury.Camelia.drawing.whiteboard.*;
import eu.carrade.amaury.Camelia.game.*;
import org.bukkit.*;
import org.bukkit.inventory.*;


@ToolLocator(slot = 4)
public class PaintingsTools extends ClicDrawTool {

	public PaintingsTools(Drawer drawer) {
		super(drawer);
	}

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
	public void onRightClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		drawer.getPlayer().openInventory(Camelia.getInstance().getGuiManager().getBackgroundInventory(drawer));
	}

	@Override
	public void onLeftClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		onRightClick(targetOnScreen, drawer);
	}
}

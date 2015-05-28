package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.drawing.drawTools.core.ClicDrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ToolLocator;
import eu.carrade.amaury.Camelia.drawing.whiteboard.WhiteboardLocation;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


@ToolLocator(slot = 1)
public class SprayTool extends ClicDrawTool {

	public SprayTool(Drawer drawer) {
		super(drawer);
	}

	@Override
	public String getDisplayName() {
		return ChatColor.GREEN + "" + ChatColor.BOLD + "Aérographe";
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
	public void onRightClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		drawer.getPlayer().sendMessage("TODO Brush");
	}

	@Override
	public void onLeftClick(WhiteboardLocation targetOnScreen, Drawer drawer) {

	}
}

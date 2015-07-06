package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.*;
import eu.carrade.amaury.Camelia.drawing.whiteboard.*;
import eu.carrade.amaury.Camelia.game.*;
import org.bukkit.*;
import org.bukkit.inventory.*;


@ToolLocator(slot = 0)
public class BrushTool extends ContinuousDrawTool {

	private int i = 0;

	public BrushTool(Drawer drawer) {
		super(drawer);
	}

	@Override
	public String getDisplayName() {
		return ChatColor.AQUA + "" + ChatColor.BOLD + "Pinceau";
	}

	@Override
	public String getDescription() {
		return ChatColor.GRAY + "Peint une ligne d'épaisseur variable sur le tableau";
	}

	@Override
	public ItemStack getIcon(Drawer drawer) {
		return new ItemStack(Material.DIAMOND_SPADE);
	}

	@Override
	public void onRightClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		if (targetOnScreen == null) return;

		Camelia.getInstance().getWhiteboard().drawCircle(targetOnScreen, 2 * size - 1, drawer.getColor(), this.mixColors);

		if (i < 5) {
			i++;
		} else {
			drawer.getPlayer().playSound(drawer.getPlayer().getLocation(), Sound.ENDERDRAGON_WINGS, 0.1F, 2);
			i = 0;
		}

	}

	@Override
	public void onLeftClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		drawer.getPlayer().openInventory(Camelia.getInstance().getGuiManager().getBrushInventory(drawer));
	}
}
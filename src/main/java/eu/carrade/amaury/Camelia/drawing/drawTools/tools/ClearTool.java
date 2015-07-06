package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.*;
import eu.carrade.amaury.Camelia.drawing.whiteboard.*;
import eu.carrade.amaury.Camelia.game.*;
import org.bukkit.*;
import org.bukkit.inventory.*;


@ToolLocator(slot = 8)
public class ClearTool extends ClicDrawTool {

	public ClearTool(Drawer drawer) {
		super(drawer);
	}

	@Override
	public String getDisplayName() {
		return ChatColor.DARK_RED + "" + ChatColor.BOLD + "Tout effacer";
	}

	@Override
	public String getDescription() {
		return ChatColor.GRAY + "Efface tout l'écran, restaurant ainsi l'écran d'origine. Peut être annulé avec l'outil d'annulation";
	}

	@Override
	public ItemStack getIcon(Drawer drawer) {
		return new ItemStack(Material.SPONGE, 1, (byte) 1);
	}

	@Override
	public void onRightClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		Camelia.getInstance().getWhiteboard().clearBoard();

		drawer.getPlayer().playSound(drawer.getPlayer().getLocation(), Sound.SPLASH, 0.25F, 2);
	}

	@Override
	public void onLeftClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		onRightClick(targetOnScreen, drawer);
	}
}

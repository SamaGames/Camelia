package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ClicDrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ToolLocator;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


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
	public void onRightClick(Location targetOnScreen, Drawer drawer) {
		Camelia.getInstance().getWhiteboard().clearBoard();
	}

	@Override
	public void onLeftClick(Location targetOnScreen, Drawer drawer) {
		onRightClick(targetOnScreen, drawer);
	}
}

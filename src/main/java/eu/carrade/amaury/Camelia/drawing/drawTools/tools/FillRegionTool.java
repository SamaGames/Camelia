package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ClicDrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ToolLocator;
import eu.carrade.amaury.Camelia.drawing.whiteboard.WhiteboardLocation;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;


@ToolLocator(slot = 2)
public class FillRegionTool extends ClicDrawTool {

	public FillRegionTool(Drawer drawer) {
		super(drawer);
	}

	@Override
	public String getDisplayName() {
		return ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Remplissage";
	}

	@Override
	public String getDescription() {
		return ChatColor.GRAY + "Remplit une zone de couleur avec la couleur active";
	}

	@Override
	public ItemStack getIcon(Drawer drawer) {
		return new ItemStack(Material.BUCKET);
	}

	@Override
	public void onRightClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		Camelia.getInstance().getWhiteboard().fillArea(targetOnScreen, drawer.getColor());

		drawer.getPlayer().playSound(drawer.getPlayer().getLocation(), Sound.SPLASH2, 0.5F, 2);
	}

	@Override
	public void onLeftClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		drawer.getPlayer().openInventory(Camelia.getInstance().getGuiManager().getColorInventory(drawer));
	}
}

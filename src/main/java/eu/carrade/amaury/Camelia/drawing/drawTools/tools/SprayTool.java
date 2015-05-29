package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ContinuousDrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ToolLocator;
import eu.carrade.amaury.Camelia.drawing.whiteboard.WhiteboardLocation;
import eu.carrade.amaury.Camelia.game.Drawer;


@ToolLocator(slot = 1)
public class SprayTool extends ContinuousDrawTool {
	
	private int strength = 1;

	public SprayTool(Drawer drawer) {
		super(drawer);
		
		this.size = 3;
	}

	@Override
	public String getDisplayName() {
		return ChatColor.GREEN + "" + ChatColor.BOLD + "Aérographe";
	}

	@Override
	public String getDescription() {
		return ChatColor.GRAY + "Applique de la couleur aléatoirement dans une petite région, telle une bombe de peinture";
	}

	@Override
	public ItemStack getIcon(Drawer drawer) {
		return new ItemStack(Material.DEAD_BUSH);
	}

	@Override
	public void onRightClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		if(targetOnScreen == null) return;
		
		Camelia.getInstance().getWhiteboard().fillRandomly(targetOnScreen, 2 * size + 1, strength * 0.05, drawer.getColor(), this.mixColors);
	}

	@Override
	public void onLeftClick(WhiteboardLocation targetOnScreen, Drawer drawer) {
		drawer.getPlayer().openInventory(Camelia.getInstance().getGuiManager().getSprayInventory(drawer));
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}
}

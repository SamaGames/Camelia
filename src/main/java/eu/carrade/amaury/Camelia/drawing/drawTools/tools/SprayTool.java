package eu.carrade.amaury.Camelia.drawing.drawTools.tools;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ContinuousDrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ToolLocator;
import eu.carrade.amaury.Camelia.drawing.whiteboard.WhiteboardLocation;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;


@ToolLocator(slot = 1)
public class SprayTool extends ContinuousDrawTool {

	private int strength = 1;

	private int i = 0;

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
		if (targetOnScreen == null) return;

		Camelia.getInstance().getWhiteboard().fillRandomly(targetOnScreen, 2 * size + 1, strength * 0.05, drawer.getColor(), this.mixColors);

		if (i < 5) {
			i++;
		} else {
			drawer.getPlayer().playSound(drawer.getPlayer().getLocation(), Sound.CAT_HISS, 0.05F, 2);
			i = 0;
		}
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

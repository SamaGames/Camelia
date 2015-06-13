package eu.carrade.amaury.Camelia.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorType;
import eu.carrade.amaury.Camelia.drawing.colors.core.ColorUtils;
import eu.carrade.amaury.Camelia.drawing.colors.core.PixelColor;
import eu.carrade.amaury.Camelia.drawing.drawTools.core.ContinuousDrawTool;
import eu.carrade.amaury.Camelia.drawing.drawTools.tools.SprayTool;
import eu.carrade.amaury.Camelia.game.Drawer;
import eu.carrade.amaury.Camelia.game.GuiManager;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Drawer drawer = Camelia.getInstance().getGameManager().getDrawer(e.getWhoClicked().getUniqueId());
		if(drawer == null || e.getRawSlot() != e.getSlot())
			return;
		
		e.setCancelled(true);
		
		if(e.getInventory().getTitle().equals(GuiManager.COLOR_GUI)) {
			if(e.getSlot() < 0 || e.getInventory().getItem(e.getSlot()) == null || e.getInventory().getItem(e.getSlot()).getType().equals(Material.AIR))
				return;
			
			if(e.getSlot() < 18) {
				List<Integer> list = Arrays.asList(14, 12, 1, 4, 5, 13, -1, 0, 8, 9, 3, 11, 10, 2, 6, -1, 7, 15);
				ColorType color = ColorType.BASIC;
				if(drawer.getPage() == 1) {
					color = ColorType.BETTER;
				} else if(drawer.getPage() == 2) {
					color = ColorType.ROUGH;
				}
				
				PixelColor pixelColor = ColorUtils.getPixelFromDye(DyeColor.getByData(list.get(e.getSlot()).byteValue()), color);
				
				drawer.setColor(pixelColor);
				
				e.getWhoClicked().closeInventory();
				
				drawer.fillInventory();
				
				if(e.getWhoClicked() instanceof Player) {
					((Player) e.getWhoClicked()).playSound(((Player) e.getWhoClicked()).getLocation(), Sound.CHICKEN_EGG_POP, 0.5F, 2);
					((Player) e.getWhoClicked()).sendMessage(ChatColor.GREEN + "Vous avez sélectionné la couleur " + pixelColor.getDisplayName());
				}
			} else if(e.getSlot() >= 30 && e.getSlot() <= 32 || e.getSlot() >= 39 && e.getSlot() <= 41) {
				int page = (e.getSlot() - 3) % 9;
				drawer.setPage(page);
				((Player) e.getWhoClicked()).playSound(((Player) e.getWhoClicked()).getLocation(), Sound.CLICK, 0.25F, 2);
				e.getWhoClicked().openInventory(Camelia.getInstance().getGuiManager().getColorInventory(drawer));
			}
			
		} else if(e.getInventory().getTitle().equals(GuiManager.BRUSH_GUI)) {
			if(e.getSlot() < 0 || e.getInventory().getItem(e.getSlot()) == null || e.getInventory().getItem(e.getSlot()).getType().equals(Material.AIR))
				return;
			
			if(e.getSlot() == 0) {
				drawer.getPlayer().openInventory(Camelia.getInstance().getGuiManager().getColorInventory(drawer));
			} else if(e.getSlot() >= 3 && e.getSlot() <= 5) {
				((Player) e.getWhoClicked()).playSound(((Player) e.getWhoClicked()).getLocation(), Sound.CLICK, 0.25F, 2);
				((ContinuousDrawTool) drawer.getTool(0)).setSize(e.getSlot() - 2);
				drawer.getPlayer().openInventory(Camelia.getInstance().getGuiManager().getBrushInventory(drawer));
			} else if(e.getSlot() == 8) {
				((Player) e.getWhoClicked()).playSound(((Player) e.getWhoClicked()).getLocation(), Sound.CLICK, 0.25F, 1.5F);
				((ContinuousDrawTool) drawer.getTool(0)).setMixColors(!((ContinuousDrawTool) drawer.getTool(0)).isMixColors());
				drawer.getPlayer().openInventory(Camelia.getInstance().getGuiManager().getBrushInventory(drawer));
			}
			
		} else if(e.getInventory().getTitle().equals(GuiManager.SPRAY_GUI)) {
			if(e.getSlot() < 0 || e.getInventory().getItem(e.getSlot()) == null || e.getInventory().getItem(e.getSlot()).getType().equals(Material.AIR))
				return;
			
			if(e.getSlot() == 9) {
				e.getWhoClicked().openInventory(Camelia.getInstance().getGuiManager().getColorInventory(drawer));
			} else if(e.getSlot() == 17) {
				((Player) e.getWhoClicked()).playSound(((Player) e.getWhoClicked()).getLocation(), Sound.CLICK, 0.25F, 1.5F);
				((ContinuousDrawTool) drawer.getTool(1)).setMixColors(!((ContinuousDrawTool) drawer.getTool(1)).isMixColors());;
				e.getWhoClicked().openInventory(Camelia.getInstance().getGuiManager().getSprayInventory(drawer));
			} else if(e.getSlot() >= 3 && e.getSlot() <= 5) {
				((Player) e.getWhoClicked()).playSound(((Player) e.getWhoClicked()).getLocation(), Sound.CLICK, 0.25F, 2);
				((ContinuousDrawTool) drawer.getTool(1)).setSize((e.getSlot() - 2) % 9);
				e.getWhoClicked().openInventory(Camelia.getInstance().getGuiManager().getSprayInventory(drawer));
			} else if(e.getSlot() >= 21 && e.getSlot() <= 23) {
				((Player) e.getWhoClicked()).playSound(((Player) e.getWhoClicked()).getLocation(), Sound.CLICK, 0.25F, 2);
				((SprayTool) drawer.getTool(1)).setStrength((e.getSlot() - 2) % 9);
				e.getWhoClicked().openInventory(Camelia.getInstance().getGuiManager().getSprayInventory(drawer));
			}
		} else if(e.getInventory().getTitle().equals(GuiManager.BACKGROUND_GUI)) {
			if(e.getSlot() < 0 || e.getInventory().getItem(e.getSlot()) == null || e.getInventory().getItem(e.getSlot()).getType().equals(Material.AIR))
				return;
			
			boolean found = true;
			
			switch(e.getInventory().getItem(e.getSlot()).getType()) {
			case DOUBLE_PLANT:
				
				break;
			case CACTUS:
				
				break;
			case STONE:
				
				break;
				
			default:
				found = false;
				e.getWhoClicked().sendMessage(ChatColor.RED + "Désolé, ce fond n'est pas disponible !");
				System.out.println("Warning ! No background for item " + e.getInventory().getItem(e.getSlot()).getType().toString().toLowerCase());
				break;
			}
			
			e.getWhoClicked().closeInventory();
			
			if(found) {
				((Player) e.getWhoClicked()).playSound(((Player) e.getWhoClicked()).getLocation(), Sound.CHICKEN_EGG_POP, 0.5F, 1F);
				e.getWhoClicked().sendMessage(ChatColor.GREEN + "Vous avez choisi le fond " + e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName());
			}
			
		} else {
			e.setCancelled(false);
		}
	}
	
}

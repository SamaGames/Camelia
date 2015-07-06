package eu.carrade.amaury.Camelia.game.guis;

import org.bukkit.entity.*;

import java.util.*;
import java.util.concurrent.*;


public class GuiManager {
	protected ConcurrentHashMap<UUID, AbstractGui> currentGUIs;

	public GuiManager() {
		this.currentGUIs = new ConcurrentHashMap<>();
	}

	public void openGui(Player player, AbstractGui gui) {
		if (this.currentGUIs.containsKey(player.getUniqueId()))
			this.closeGui(player);

		this.currentGUIs.put(player.getUniqueId(), gui);
		gui.display(player);
	}

	public void closeGui(Player player) {
		player.closeInventory();
		this.removeClosedGui(player);
	}

	public void removeClosedGui(Player player) {
		if (this.currentGUIs.containsKey(player.getUniqueId())) {
			this.getPlayerGui(player).onClose(player);
			this.currentGUIs.remove(player.getUniqueId());
		}
	}

	public AbstractGui getPlayerGui(HumanEntity player) {
		if (this.currentGUIs.containsKey(player.getUniqueId()))
			return this.currentGUIs.get(player.getUniqueId());

		return null;
	}

	public ConcurrentHashMap<UUID, AbstractGui> getPlayersGui() {
		return this.currentGUIs;
	}
}
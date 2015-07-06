package eu.carrade.amaury.Camelia.listeners;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandListener implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String main, String[] args) {
		Player player;
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Player-only. Sorry :c");
			return true;
		}

		player = (Player) sender;

		String commandName = command.getName();

		if (commandName.equalsIgnoreCase("mot")) {
			if (args == null || args.length == 0) {
				player.sendMessage(ChatColor.GREEN + "Pour proposer un mot, utilisez la commande: " + ChatColor.RED + "/mot <mot>");
			} else {
				player.sendMessage(ChatColor.GREEN + "Votre mot a bien été pris en compte, il sera peut-être ajouté !");
				// TODO Envoi
			}
			return true;
		} else if (commandName.equals("indice")) {
			Drawer drawer = Camelia.getInstance().getGameManager().getDrawer(player.getUniqueId());

			if (drawer == null) {
				player.sendMessage(ChatColor.RED + "Vous ne pouvez configurer ceci sans être un joueur actif de la partie.");
				return true;
			}

			player.openInventory(Camelia.getInstance().getGuiManager().getTipLocationOptionInventory(drawer));

			return true;
		} else if (commandName.equals("hint")) {

			if (!player.isOp()) {
				player.sendMessage(ChatColor.RED + "Tricheur ! :>");
				return true;
			}

			Camelia.getInstance().getDrawTurnsManager().getCurrentTurn().throwTip();
			player.sendMessage(ChatColor.GREEN + "Un indice miraculeux apparait !");
			return true;
		}

		return false;
	}
}

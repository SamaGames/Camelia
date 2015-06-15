package eu.carrade.amaury.Camelia.listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String main, String[] args) {
		Player player = null;
		if(sender instanceof Player) {
			player = (Player) sender;
		} else {
			return false;
		}
		if(main.equalsIgnoreCase("mot") || main.equalsIgnoreCase("word")) {
			if(args == null || args.length == 0) {
				player.sendMessage(ChatColor.GREEN + "Pour proposer un mot, utilisez la commande: " + ChatColor.RED + "/mot <mot>");
			} else {
				player.sendMessage(ChatColor.GREEN + "Votre mot a bien été pris en compte, il sera peut-être ajouté !");
			}
			return true;
		}
		return false;
	}

}

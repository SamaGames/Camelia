package eu.carrade.amaury.Camelia.listeners;

import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.game.*;
import eu.carrade.amaury.Camelia.game.guis.settings.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

/*
 * This file is part of Camelia.
 *
 * Camelia is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Camelia is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Camelia.  If not, see <http://www.gnu.org/licenses/>.
 */
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
		}

		else if (commandName.equals("options")) {
			Drawer drawer = Camelia.getInstance().getGameManager().getDrawer(player.getUniqueId());

			if (drawer == null) {
				player.sendMessage(ChatColor.RED + "Vous ne pouvez configurer ceci sans être un joueur actif de la partie.");
				return true;
			}

			Camelia.getInstance().getGuiManager().openGui(player, new SettingsGui());

			return true;
		}

		else if (commandName.equals("hint")) {

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

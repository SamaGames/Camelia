package eu.carrade.amaury.Camelia.game;


import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.game.turns.*;
import eu.carrade.amaury.Camelia.utils.Utils;
import net.samagames.api.games.*;
import net.samagames.tools.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;


public class GameManager extends IManagedGame {

	private final Map<UUID, Drawer> drawers = new HashMap<>();
	private Status status = Status.WAITING_FOR_PLAYERS;


	/** *** Players management *** **/


	/**
	 * Registers a new player in the game.
	 *
	 * @param id The UUID of the player.
	 *
	 * @return The new Drawer object just created.
	 */
	public Drawer registerNewDrawer(UUID id) {
		if (!drawers.containsKey(id)) {
			Drawer drawer = new Drawer(id);

			drawers.put(id, drawer);
			return drawer;
		} else {
			return getDrawer(id);
		}
	}

	public void unregisterDrawer(UUID id) {
		drawers.remove(id);
	}

	/**
	 * Returns the drawer with that UUID.
	 *
	 * @param id The ID.
	 *
	 * @return The drawer.
	 */
	public Drawer getDrawer(UUID id) {
		return drawers.get(id);
	}

	public List<Drawer> getDrawers() {
		return new ArrayList<>(drawers.values());
	}


	@Override
	public int getMaxPlayers() {
		return Camelia.getInstance().getArenaConfig().getInt("game.maxPlayers");
	}

	// @Override
	// VIP thing ???

	@Override
	public int getConnectedPlayers() {
		return drawers.size();
	}

	@Override
	public String getMapName() {
		return Camelia.getInstance().getArenaConfig().getString("game.name");
	}

	@Override
	public String getGameName() {
		return Camelia.NAME_WHITE;
	}

	@Override
	public void playerJoin(final Player player) {
		player.setGameMode(GameMode.ADVENTURE);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setExp(0);
		player.setLevel(0);
		teleportLobby(player);

		registerNewDrawer(player.getUniqueId());

		Camelia.getInstance().getCoherenceMachine().getMessageManager().writePlayerJoinToAll(player);

		Camelia.getInstance().getCoherenceMachine().getMessageManager().writeWelcomeInGameToPlayer(player);
		player.sendMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + " Le jeu dans lequel vous êtes l'artiste !");

		Bukkit.getScheduler().runTaskLaterAsynchronously(Camelia.getInstance(), () -> Camelia.getInstance().getWhiteboard().sendAllWhitebord(player), 20l);

		Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), () -> Titles.sendTitle(player, 10, 80, 10, Camelia.NAME_COLORED, ChatColor.WHITE + "Bienvenue en "
				+ Camelia.NAME_COLORED), 40l);

		// TODO use a standard format for these tips
		if (Math.random() < 0.2) {
			Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), () -> player.sendMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "Vous pouvez proposer des mots grâce à la commande " + ChatColor.RED + "/mot <mot>"), 40l);
		}

		if (getConnectedPlayers() == getMinPlayers() && (status == Status.WAITING_FOR_PLAYERS || status == Status.STARTING || status == Status.READY_TO_START)) {
			Camelia.getInstance().getCountdownTimer().restartTimer();
		}
	}

	@Override
	public void playerDisconnect(Player player) {
		unregisterDrawer(player.getUniqueId());

		if (getConnectedPlayers() < getMinPlayers()
				&& getStatus() != Status.IN_GAME && getStatus() != Status.FINISHED) {
			Camelia.getInstance().getCountdownTimer().cancelTimer();
			Camelia.getInstance().getCoherenceMachine().getMessageManager().writeNotEnougthPlayersToStart();
		}

		// Rechecks if everyone left found the word.
		if (getStatus() == Status.IN_GAME) {
			Camelia.getInstance().getDrawTurnsManager().getCurrentTurn().checkIfEverybodyFoundTheWord();
		}

		// Workaround for a Minecraft bug (titles times not reset when the server changes)
		Titles.sendTitle(player, 10, 60, 10, "", "");
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public void setStatus(Status arg) {
		status = arg;
	}

	@Override
	public void startGame() {

		if (getStatus() == Status.IN_GAME) return;

		Camelia.getInstance().getCountdownTimer().cancelTimer();

		Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "A vos pinceaux... C'est parti !");

		// Start of the game

		Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), () -> {
			for (Player player : Camelia.getInstance().getServer().getOnlinePlayers()) {
				player.playSound(player.getLocation(), Sound.SPLASH2, 1, 1);
			}
		}, 1L);

		for (Drawer drawer : drawers.values()) {
			Camelia.getInstance().getScoreManager().displayTo(drawer);
		}

		Camelia.getInstance().getDrawTurnsManager().startTurns();

		// Tips

		/* New player or ? */
		Bukkit.getOnlinePlayers().stream().filter(player -> Math.random() < 0.1).forEach(player -> {
			Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), () -> {
				player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");

				player.sendMessage(ChatColor.YELLOW + "La position de l'indice ne vous plaît pas ? (Visibilité, goût...)");
				player.sendMessage(ChatColor.YELLOW + "Vous pouvez le mettre en haut ou au centre de l'écran !");
				player.sendMessage(ChatColor.YELLOW + "Tapez simplement " + ChatColor.GOLD + "/indice" + ChatColor.YELLOW + " à tout moment.");

				player.sendMessage(ChatColor.GOLD + "-----------------------------------------------------");
			}, 200L);
		});

		setStatus(Status.IN_GAME);
	}

	public int getMinPlayers() {
		return Camelia.getInstance().getArenaConfig().getInt("game.minPlayers");
	}

	public int getCountdownTime() {
		return Camelia.getInstance().getArenaConfig().getInt("game.waiting");
	}

	public void onEnd() {
		Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "Les " + ChatColor.BOLD + DrawTurnsManager.getWavesCount() + ChatColor.AQUA + " manches ont été jouées, la partie est terminée. Place aux résultats !");

		Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), () -> {
			Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "Le grand gagnant est...");

			Drawer drawer = null;

			for (Drawer d : drawers.values()) {
				if (drawer == null || d.getPoints() > drawer.getPoints())
					drawer = d;
			}

			if (drawer != null) {
				final Player player = drawer.getPlayer();

				Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "..." + player.getName() + " !");

				Bukkit.getScheduler().runTaskAsynchronously(Camelia.getInstance(), () -> {
					try {
						Camelia.getInstance().getWhiteboard().drawPlayerHead(player);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		}, 20L);
	}

	public void teleportLobby(Player player) {
		player.teleport(Utils.stringToLocation(Camelia.getInstance().getArenaConfig().getString("game.hub")));
	}

	public void teleportDrawing(Player player) {
		player.teleport(Utils.stringToLocation(Camelia.getInstance().getArenaConfig().getString("game.drawing")));
	}
}

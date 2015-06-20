package eu.carrade.amaury.Camelia.game;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.utils.ActionBar;
import eu.carrade.amaury.Camelia.utils.DrawTimer;
import eu.carrade.amaury.Camelia.utils.Utils;
import net.samagames.api.games.IManagedGame;
import net.samagames.api.games.Status;
import net.samagames.api.games.StatusEnum;
import net.samagames.tools.GameUtils;
import net.samagames.tools.Titles;

public class GameManager implements IManagedGame {

	private final Map<UUID,Drawer> drawers = new HashMap<>();
	private Status status = Status.WAITING_FOR_PLAYERS;
	
	private final List<String> words = new ArrayList<String>(Arrays.asList("maison", "voiture", "arbre", "pomme"));
	
	private final List<Drawer> turns = new ArrayList<Drawer>();
	private List<Drawer> wave = new ArrayList<Drawer>();
	private int waveId = 0;
	
	private Drawer drawing = null;
	private String wordToFind = null;
	
	private DrawTimer timer = new DrawTimer();
	
	private Drawer whoIsDrawing = null;

	public GameManager() {
		// Something very useful here. Soon™.
	}



	/** *** Players management *** **/


	/**
	 * Registers a new player in the game.
	 *
	 * @param id The UUID of the player.
	 *
	 * @return The new Drawer object just created.
	 */
	public Drawer registerNewDrawer(UUID id) {
		if(!drawers.containsKey(id)) {
			Drawer drawer = new Drawer(id);

			drawers.put(id, drawer);
			return drawer;
		}
		else {
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
		
		Drawer drawer = registerNewDrawer(player.getUniqueId());
		
		Camelia.getInstance().getCoherenceMachine().getMessageManager().writePlayerJoinToAll(player);
		
		Camelia.getInstance().getCoherenceMachine().getMessageManager().writeWelcomeInGameToPlayer(player);
		player.sendMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + " Le jeu dans lequel vous êtes l'artiste !");

		Bukkit.getScheduler().runTaskLaterAsynchronously(Camelia.getInstance(), new Runnable() {
			@Override
			public void run() {
				Camelia.getInstance().getWhiteboard().sendAllWhitebord(player);
			}
		}, 20l);
		
		Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), new Runnable() {
			@Override
			public void run() {
					Titles.sendTitle(player, 10, 80, 10, Camelia.NAME_COLORED, ChatColor.WHITE + "Bienvenue en "
							+ Camelia.NAME_COLORED);
			}
		}, 40l);
		
		if(Math.random() < 0.2) {
			Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), new Runnable() {
				@Override
				public void run() {
					player.sendMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "Vous pouvez proposer des mots grâce à la commande " + ChatColor.RED + "/mot <mot>");
				}
			}, 40l);
		}
		
		if(getConnectedPlayers() == getMinPlayers() && (status == Status.WAITING_FOR_PLAYERS || status == Status.STARTING || status == Status.READY_TO_START)) {
			Camelia.getInstance().getCountdownTimer().restartTimer();
		}
	}

	@Override
	public void playerDisconnect(Player player) {
		unregisterDrawer(player.getUniqueId());
		if(getConnectedPlayers() < getMinPlayers()) {
			Camelia.getInstance().getCountdownTimer().cancelTimer();
			Camelia.getInstance().getCoherenceMachine().getMessageManager().writeNotEnougthPlayersToStart();
		}
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
		Camelia.getInstance().getCountdownTimer().cancelTimer();
		
		Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "A vos pinceaux... C'est parti !");
		
		// TP
		
		Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), new Runnable() {
			@Override
			public void run() {
				for (Player player : Camelia.getInstance().getServer().getOnlinePlayers()) {
					player.playSound(player.getLocation(), Sound.SPLASH2, 1, 1);
				}
			}
		}, 1L);
		
		for(Drawer drawer : drawers.values()) {
			turns.add(drawer);
			Camelia.getInstance().getScoreManager().displayTo(drawer);
		}
		
		Collections.shuffle(turns);
		
		Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), new Runnable() {
			@Override
			public void run() {
				nextWave();
			}
		}, 20L);
		
		
	}
	
	public int getMinPlayers() {
		return Camelia.getInstance().getArenaConfig().getInt("game.minPlayers");
	}
	
	public int getCountdownTime() {
		return Camelia.getInstance().getArenaConfig().getInt("game.waiting");
	}
	
	public int getWavesCount() {
		return Camelia.getInstance().getArenaConfig().getInt("game.drawings");
	}
	
	public void createWave() {
		wave.clear();
		for(Drawer drawer : turns) {
			if(drawer.getPlayer().isOnline()) wave.add(drawer);
		}
	}
	
	public void nextTurn() {
		if(wave.size() > 0 && wave.get(0).getPlayer().isOnline()) {
			final Drawer drawer = wave.get(0);
			wave.remove(0);
			final Player player = drawer.getPlayer();
			
			Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "C'est au tour de " + ChatColor.GOLD + player.getName());
			
			teleportDrawing(player);
			
			for(Drawer d : drawers.values()) {
				ActionBar.removeMessage(d.getPlayer());
			}
			
			Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), new Runnable() {
				@Override
				public void run() {
					if(words.size() == 0) {
						Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.RED + "Erreur critique, nous n'avons aucun mot à vous proposer !");
						return;
					}
					whoIsDrawing = drawer;
					
					wordToFind = words.get(0);
					
					words.remove(wordToFind);
					
					timer.startTimer();
					
					drawer.setDrawing(true);
					drawer.fillInventory();
					ActionBar.sendPermanentMessage(player, Utils.getFormattedWord(wordToFind));
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
					player.sendMessage(ChatColor.GREEN + "Vous devrez dessiner " + ChatColor.GOLD + "" + ChatColor.BOLD + wordToFind.toUpperCase());
					
					String blank = Utils.getFormattedBlank(wordToFind);
					
					for(Drawer d : drawers.values()) {
						if(d.equals(drawer)) continue;
						ActionBar.sendPermanentMessage(d.getPlayer(), blank);
					}
				}
			}, 2 * 20L);
			
			Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), new Runnable() {
				@Override
				public void run() {
					String word = Utils.getFormattedWord(wordToFind);
					
					Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "Le temps est écoulé !");
					Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "Le mot était " + ChatColor.GOLD + "" + ChatColor.BOLD + wordToFind.toUpperCase());
					
					wordToFind = null;
					whoIsDrawing = null;
					
					for(Drawer drawer : drawers.values()) {
						ActionBar.sendPermanentMessage(drawer.getPlayer(), word);
					}
					
					drawer.setDrawing(false);
					player.getInventory().clear();
					teleportLobby(player);
					
					Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), new Runnable() {
						@Override
						public void run() {
							Camelia.getInstance().getWhiteboard().clearBoard();
							
							nextTurn();
						}
					}, 5 * 20L);
					
					for(Drawer drawer : drawers.values()) {
						drawer.setFoundCurrentWord(false);
					}
					
				}
			}, 2 * 20L + DrawTimer.SECONDS * 20L);
		} else {
			nextWave();
		}
	}
	
	public void nextWave() {
		if(waveId < getWavesCount()) {
			waveId++;
			Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "Début de la manche " + ChatColor.BOLD + waveId);
		
			createWave();
		
			Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), new Runnable() {
				@Override
				public void run() {
					nextTurn();
				}
			}, 40L);
		} else {
			onEnd();
		}
	}
	
	public void onEnd() {
		Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "Les " + ChatColor.BOLD + waveId + ChatColor.AQUA + " manches ont été jouées, la partie est terminée. Place aux résultats !");
	}
	
	public void teleportLobby(Player player) {
		player.teleport(Utils.stringToLocation(Camelia.getInstance().getArenaConfig().getString("game.hub")));
	}
	
	public void teleportDrawing(Player player) {
		player.teleport(Utils.stringToLocation(Camelia.getInstance().getArenaConfig().getString("game.drawing")));
	}
	
	public void playerFoundWord(Drawer drawer) {
		drawer.getPlayer().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "" + ChatColor.BOLD + drawer.getPlayer().getName() + ChatColor.GREEN + "" + ChatColor.BOLD + " a trouvé !");
		GameUtils.broadcastSound(Sound.LEVEL_UP);
		
		int found = getTotalFound();
		int points = 2;
		
		if(found <= 2) {
			points = 8 - 2 * found;
		}
		
		drawer.getPlayer().sendMessage(ChatColor.GREEN + "Vous gagnez " + ChatColor.AQUA + "" + ChatColor.BOLD + points + ChatColor.GREEN + " points !");
		drawer.increasePoints(points);
		
		whoIsDrawing.getPlayer().sendMessage(ChatColor.GREEN + "Vous gagnez " + ChatColor.AQUA + "" + ChatColor.BOLD + "3" + ChatColor.GREEN + " points !");
		whoIsDrawing.increasePoints(3);
		
		drawer.setFoundCurrentWord(true);
	}
	
	public String getWordToFind() {
		return wordToFind;
	}
	
	public int getTotalFound() {
		int n = 0;
		for(Drawer drawer : drawers.values()) {
			if(drawer.hasFoundCurrentWord()) n++;
		}
		return n;
	}

	public Drawer getWhoIsDrawing() {
		return whoIsDrawing;
	}
}

package eu.carrade.amaury.Camelia.game.turns;

import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.game.*;
import eu.carrade.amaury.Camelia.utils.*;
import eu.carrade.amaury.Camelia.utils.Utils;
import net.samagames.tools.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.*;


public class Turn {

	/**
	 * The current drawer.
	 */
	private Drawer drawer;

	/**
	 * Stored if the drawer logs out.
	 */
	private String drawerName;

	/**
	 * The word to draw.
	 */
	private Word word;

	/**
	 * The current tip: the word, with _ where the letter is not displayed.
	 */
	private String tip;


	/**
	 * Is this wave active?
	 */
	private boolean active = false;

	/**
	 * The timer used to check the time.
	 */
	private DrawTimer timer = new DrawTimer();


	/**
	 * The players who guessed the word.
	 */
	private Set<UUID> winnersOfThisTurn = new HashSet<>();

	private Random random = new Random();


	/**
	 * @param drawer The drawer of this turn. A word will be generated according to his preferences.
	 */
	public Turn(Drawer drawer) {
		this.drawer = drawer;
		this.word = Camelia.getInstance().getDrawTurnsManager().getRandomWord(drawer);
		this.tip = Utils.getNewWordBlank(word.getWord());
	}

	/**
	 * Starts the new turn.
	 */
	public void startTurn() {
		final Player drawPlayer = drawer.getPlayer();
		drawerName = drawPlayer.getName();

		active = true;


		// Beginning

		Camelia.getInstance().getServer().broadcastMessage(""); // Separator between turns.

		Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "C'est au tour de " + ChatColor.GOLD + drawPlayer.getName());

		Camelia.getInstance().getGameManager().getDrawers().forEach(Drawer::clearWordDisplay);

		Camelia.getInstance().getGameManager().teleportDrawing(drawPlayer);

		// Word given, timer starts.

		Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), () -> {
			timer.start();

			drawer.setDrawing(true);
			drawer.fillInventory();

			ActionBar.sendPermanentMessage(drawPlayer, Utils.getFormattedWord(word.getWord()));
			drawPlayer.playSound(drawPlayer.getLocation(), Sound.NOTE_PLING, 1, 1);
			drawPlayer.sendMessage(ChatColor.GREEN + "Vous devrez dessiner " + ChatColor.GOLD + "" + ChatColor.BOLD + word.getWord().toUpperCase());


			String blank = Utils.getFormattedBlank(tip);

			for (Drawer d : Camelia.getInstance().getGameManager().getDrawers()) {
				if (d.equals(drawer)) continue;
				d.displayWord(blank);
			}
		}, 2 * 20L);


		// Next tip

		Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), Turn.this::throwTip, random.nextInt(20 * 20) + 5 * 20);


		// End of the turn

		Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), () -> endTurn(EndReason.TIMEOUT), 2 * 20L + DrawTimer.SECONDS * 20L);
	}

	/**
	 * Adds a new tip to the partial word displayed.
	 */
	public void throwTip() {
		boolean full = true;
		int blanks = 0;

		for (int i = 0; i < word.length(); i++) {
			if (tip.charAt(i) == '_') {  // Arrête de me regarder comme ça, toi
				blanks++;
				full = false;
			}
		}

		if (full) return;
		if (blanks <= 2) return;

		int letter = random.nextInt(blanks);
		int n = 0;

		for (int i = 0; i < word.length(); i++) {
			if (tip.charAt(i) == '_') {
				if (letter == n) {
					char[] chars = tip.toCharArray();

					chars[i] = word.getWord().charAt(i);
					tip = String.valueOf(chars).toUpperCase();

					break;
				}

				n++;
			}
		}

		for (Drawer d : Camelia.getInstance().getGameManager().getDrawers()) {
			if (d.equals(drawer)) continue;

			d.getPlayer().playSound(d.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
			d.displayWord(Utils.getFormattedBlank(tip));
		}

		int rnd = random.nextInt(2000 / word.length()) + 4 * 20;
		if (timer.getTenthsOfSecondsLeft() > rnd) {
			Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), Turn.this::throwTip, rnd * 2);
		}
	}

	/**
	 * Ends the turn.
	 */
	public void endTurn(EndReason reason) {
		if(!active) return;


		final Player drawPlayer = drawer.getPlayer();

		active = false;
		timer.stop();


		// Broadcast

		String endMessage = "";

		if (reason == null) reason = EndReason.UNKNOWN;

		switch (reason) {
			case TIMEOUT:
				endMessage = "Le temps est écoulé !";
				break;

			case SKIPPED:
				endMessage = drawerName + " a décidé de passer son tour !";
				break;

			case DISCONNECTED:
				endMessage = drawerName + " s'est déconnecté.";
				break;

			case EVERYONE_FOUND:
				endMessage = "Tout le monde a trouvé le mot !";
				break;

			case UNKNOWN:
				endMessage = "Ce tour est terminé.";
				break;
		}

		Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + endMessage);
		Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "Le mot était " + ChatColor.GOLD + "" + ChatColor.BOLD + word.getWord().toUpperCase() + ChatColor.AQUA + ".");

		if (winnersOfThisTurn.size() > 0)
			Camelia.getInstance().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "Il a été trouvé par " + winnersOfThisTurn.size() + " personne" + (winnersOfThisTurn.size() > 1 ? "s" : "") + ".");

		displayWord();


		drawer.setDrawing(false);
		drawer.fillInventory();

		if (drawPlayer != null) Camelia.getInstance().getGameManager().teleportLobby(drawPlayer);


		Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), () -> {
			Camelia.getInstance().getWhiteboard().clearBoard();

			// TODO Save the whiteboard somewhere (inside this object?).

			Camelia.getInstance().getGameManager().getDrawers().forEach(Drawer::clearWordDisplay);

			Camelia.getInstance().getDrawTurnsManager().nextTurn();
		}, 5 * 20L);


		for (Drawer d : Camelia.getInstance().getGameManager().getDrawers()) {
			d.setFoundCurrentWord(false);
		}
	}

	/**
	 * Checks if the input of the player is a good one.
	 *
	 * TODO Notice the player if he's near the solution (like iSketch).
	 *
	 * @param player The player.
	 * @param input  The input.
	 *
	 * @return {@code true} if the player found the word.
	 */
	public Word.FoundState checkIfFound(Player player, String input) {
		Drawer checkedDrawer = Camelia.getInstance().getGameManager().getDrawer(player.getUniqueId());

		if (checkedDrawer == null)               return Word.FoundState.NOT_FOUND;
		if (checkedDrawer.hasFoundCurrentWord()) return Word.FoundState.FOUND;

		Word.FoundState found = word.checkInput(input);

		if (found == Word.FoundState.FOUND) {
			checkedDrawer.getPlayer().getServer().broadcastMessage(Camelia.getInstance().getCoherenceMachine().getGameTag() + ChatColor.AQUA + "" + ChatColor.BOLD + checkedDrawer.getPlayer().getName() + ChatColor.GREEN + "" + ChatColor.BOLD + " a trouvé le mot !");
			GameUtils.broadcastSound(Sound.LEVEL_UP);

			int foundCount = winnersOfThisTurn.size();
			int guesserPoints = 2;

			if (foundCount <= 2) {
				guesserPoints = 8 - 2 * foundCount;
			}

			int drawerPoints = word.isHard() ? 5 : 3;


			checkedDrawer.getPlayer().sendMessage(ChatColor.GREEN + "Vous gagnez " + ChatColor.AQUA + "" + ChatColor.BOLD + guesserPoints + ChatColor.GREEN + " points !");
			checkedDrawer.increasePoints(guesserPoints);

			drawer.getPlayer().sendMessage(ChatColor.GREEN + "Vous gagnez " + ChatColor.AQUA + "" + ChatColor.BOLD + drawerPoints + ChatColor.GREEN + " points !");
			drawer.increasePoints(drawerPoints);

			checkedDrawer.setFoundCurrentWord(true);
			winnersOfThisTurn.add(checkedDrawer.getPlayerID());

			checkIfEverybodyFoundTheWord();
		}

		return found;
	}

	/**
	 * Checks if everyone found the word: in this case, the turn ends.
	 */
	public void checkIfEverybodyFoundTheWord() {

		Boolean everyoneFound = true;

		for (Drawer d : Camelia.getInstance().getGameManager().getDrawers()) {
			Player dPlayer = d.getPlayer();
			if (!d.equals(drawer) && dPlayer != null && dPlayer.isOnline() && !d.hasFoundCurrentWord()) {
				everyoneFound = false;
				break;
			}
		}

		if (everyoneFound) {
			endTurn(EndReason.EVERYONE_FOUND);
		}
	}

	/**
	 * Display the word to everyone: complete display to the drawer, partial display to every other player and moderator
	 * in BTP.
	 */
	public void displayWord() {
		Bukkit.getOnlinePlayers().forEach(this::displayWord);
	}

	/**
	 * Displays the word to this specific player. Complete display to the drawer, partial display to every other player
	 * and moderator in BTP.
	 *
	 * @param player The player.
	 */
	public void displayWord(Player player) {
		if (player == null || !player.isOnline()) return;

		Drawer pDrawer = Camelia.getInstance().getGameManager().getDrawer(player.getUniqueId());

		// First case: after the end of the turn. The whole word is displayed to everyone.
		if (!isActive()) {
			String displayedWord = Utils.getFormattedWord(word.getWord());

			if (pDrawer != null) pDrawer.displayWord(displayedWord);
			else                 ActionBar.sendPermanentMessage(player, displayedWord);
		}

		// Other case: during the turn. The whole word is displayed to the drawer.
		// The partial one, to everyone else.
		else {

			// Moderator or spectator
			if (pDrawer == null || !pDrawer.isDrawing()) {
				String displayedWord = Utils.getFormattedWord(tip);

				if (pDrawer != null) pDrawer.displayWord(displayedWord);
				else                 ActionBar.sendPermanentMessage(player, displayedWord);
			}

			// Drawer
			else {
				pDrawer.displayWord(Utils.getFormattedWord(word.getWord()));
			}
		}
	}


	public Drawer getDrawer() {
		return drawer;
	}

	public Word getWord() {
		return word;
	}

	public String getTip() {
		return tip;
	}

	public boolean isActive() {
		return active;
	}

	public Set<UUID> getWinnersOfThisTurn() {
		return winnersOfThisTurn;
	}


	/**
	 * Represents the reason of the end of this turn.
	 */
	public enum EndReason {
		/**
		 * The time was over.
		 */
		TIMEOUT,

		/**
		 * The drawer skipped his turn.
		 */
		SKIPPED,

		/**
		 * the drawer disconnected from the game.
		 */
		DISCONNECTED,

		/**
		 * Everyone found the word.
		 */
		EVERYONE_FOUND,

		/**
		 * Unknown reason
		 */
		UNKNOWN
	}
}

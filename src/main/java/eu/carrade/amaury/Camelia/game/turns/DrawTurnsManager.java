package eu.carrade.amaury.Camelia.game.turns;

import eu.carrade.amaury.Camelia.Camelia;
import eu.carrade.amaury.Camelia.game.Drawer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;


public class DrawTurnsManager {

	private static final String API_URL = "http://lnfinity.net/tasks/camelia-getwords.php";
	private static final String API_KEY = "jmgqygafryrq0dnqcm2ys6ubvauop24sx5z7uz2c36pxq4vf5nn1rbnjd6qsnt8s";


	/**
	 * The number of times each player will draw.
	 */
	private static Integer WAVES_COUNT;


	/**
	 * The words used in this game.
	 */
	private Deque<String> simpleWords = new ConcurrentLinkedDeque<>();
	private Deque<String> hardWords = new ConcurrentLinkedDeque<>();

	/**
	 * The list of draws
	 */
	private Deque<Turn> draws = new ConcurrentLinkedDeque<>();

	private Turn currentTurn = null;


	public DrawTurnsManager() {
		// Very important to run as soon as possible !
		loadWords();

		WAVES_COUNT = Camelia.getInstance().getArenaConfig().getInt("game.drawings");
		Camelia.getInstance().getLogger().info("Waves: " + WAVES_COUNT);
	}


	/**
	 * Loads the words from the server. <p/> TODO Fallback local list of words, just in case. TODO Fallback server if
	 * the main one is down (mirror).
	 */
	private void loadWords() {
		Bukkit.getScheduler().runTaskAsynchronously(Camelia.getInstance(), () -> {
			InputStream is = null;

			try {

				Integer wordCount = (WAVES_COUNT + 1) * Camelia.getInstance().getGameManager().getMaxPlayers();
				Camelia.getInstance().getLogger().info("Loading " + wordCount + " words...");

				URL url = new URL(API_URL + "?pass=" + API_KEY + "&words=" + wordCount);

				is = url.openStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				String rawWords = br.readLine();
				Camelia.getInstance().getLogger().info("Got reply " + rawWords);

				List<String> words = Arrays.asList(rawWords.split(","));
				Collections.shuffle(words);

				simpleWords.clear();
				simpleWords.addAll(words);

				Camelia.getInstance().getLogger().info("Succefully loaded " + simpleWords.size() + " words !");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} finally {
				try { if (is != null) is.close(); } catch (IOException ignored) {}
			}
		});
	}

	/**
	 * Returns a random word for the given drawer. If there isn't any word left, stops the game and returns an empty
	 * string.
	 *
	 * @param drawer The drawer who will have to draw this.
	 *
	 * @return The word, or {@code ""} (empty string) if the words list is empty.
	 */
	public String getRandomWord(Drawer drawer) {
		if (simpleWords.size() == 0) {
			Bukkit.broadcastMessage(
					ChatColor.DARK_RED + "" + ChatColor.BOLD + "[!] "
							+ ChatColor.RED + "" + ChatColor.BOLD + "Erreur critique : il n'y a plus de mots disponible ! La partie est interrompue."
			);

			Camelia.getInstance().getGameManager().onEnd();

			return "";
		}


		// TODO Take difficulty into account
		return simpleWords.pop();
	}


	/**
	 * Generates the waves of draws: the players who will draw, ordered, with words.
	 */
	private void generateWaves() {

		Camelia.getInstance().getLogger().info("Generating " + WAVES_COUNT + " waves...");

		List<Drawer> drawers = Camelia.getInstance().getGameManager().getDrawers();
		Collections.shuffle(drawers);

		for (Integer i = 0; i < WAVES_COUNT; i++) {
			Camelia.getInstance().getLogger().info(" » Wave #" + i);
			for (Drawer drawer : drawers) {
				Camelia.getInstance().getLogger().info(" » »  Added drawer " + drawer.getPlayer().getName());
				draws.push(new Turn(drawer));
			}
		}
	}

	/**
	 * Starts the cycle of turns.
	 */
	public void startTurns() {
		generateWaves();

		Bukkit.getScheduler().runTaskLater(Camelia.getInstance(), DrawTurnsManager.this::nextTurn, 20L);
	}

	/**
	 * Starts the next turn of draw.
	 */
	public void nextTurn() {
		if (currentTurn != null && currentTurn.isActive()) {
			currentTurn.endTurn(Turn.EndReason.UNKNOWN);
		}

		Camelia.getInstance().getLogger().info("Next turn, " + draws.size() + " draws left.");


		// We need an online drawer.
		do {
			try {
				Camelia.getInstance().getLogger().info("Trying a drawer.");
				currentTurn = draws.pop();
				Camelia.getInstance().getLogger().info(" » Player " + currentTurn.getDrawer().getPlayer());
				Camelia.getInstance().getLogger().info(" » Online " + currentTurn.getDrawer().getPlayer().isOnline());

			} catch (NoSuchElementException e) {
				Camelia.getInstance().getGameManager().onEnd();
				return;
			}

		} while (currentTurn.getDrawer().getPlayer() == null || !currentTurn.getDrawer().getPlayer().isOnline());

		currentTurn.startTurn();
	}


	/**
	 * Returns the turn currently active. <p/> Before the game, returns {@code null}. After, too.
	 *
	 * @return The current turn, or {@code null}.
	 */
	public Turn getCurrentTurn() {
		return currentTurn;
	}

	public static Integer getWavesCount() {
		return WAVES_COUNT;
	}
}

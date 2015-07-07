package eu.carrade.amaury.Camelia.game.turns;

import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.game.*;
import org.bukkit.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;


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
	}


	/**
	 * Loads the words from the server.
	 */
	private void loadWords() {
		Bukkit.getScheduler().runTaskAsynchronously(Camelia.getInstance(), () -> {
			Integer wordCount = (WAVES_COUNT + 1) * Camelia.getInstance().getGameManager().getMaxPlayers();

			simpleWords.clear();
			simpleWords.addAll(getWords(wordCount, false));

			hardWords.clear();
			hardWords.addAll(getWords(wordCount, true));
		});
	}

	/**
	 * Returns a randomized list of {@code wordCount} words, all hard or not following the {@code hard}
	 * parameter.
	 *
	 * Run this async. Please.
	 *
	 * TODO Fallback server if the main one is down (mirror).
	 *
	 * @param wordCount The number of words to get from the server.
	 * @param hard If true, this will returns only hard words. Else, “easy” ones.
	 * @return The words (randomized list).
	 */
	private List<String> getWords(int wordCount, boolean hard) {
		InputStream is = null;

		try {
			Camelia.getInstance().getLogger().info("Loading " + wordCount + " words... (hard: " + hard + ")");

			URL url = new URL(API_URL + "?pass=" + API_KEY + "&words=" + wordCount + (hard ? "&hard" : ""));

			is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String rawWords = br.readLine();
			Camelia.getInstance().getLogger().info("Got reply " + rawWords);

			List<String> words = Arrays.asList(rawWords.split(","));

			if(words.size() < wordCount) {
				Camelia.getInstance().getLogger().severe("Cannot load " + wordCount + " words, only " + words.size() + " received from the server! Using local words to complete.");
				words.addAll(getFallbackLocalWordsList(wordCount - words.size(), hard));
			}

			Collections.shuffle(words);

			Camelia.getInstance().getLogger().info("Successfully loaded " + words.size() + " words!");

			return words;

		} catch (IOException e) {
			Camelia.getInstance().getLogger().log(Level.SEVERE, "Cannot load " + wordCount + " words, I/O exception received! Using local words instead.", e);

			return getFallbackLocalWordsList(wordCount, hard);
		} finally {
			try { if (is != null) is.close(); } catch (IOException ignored) {}
		}
	}

	/**
	 * Returns a local fallback list of words, used if the server is not responding.
	 *
	 * TODO Add some words here (or, better, in a configuration file).
	 *
	 * @param wordCount The number of words to get.
	 * @param hard If true, this should return hard words.
	 *
	 * @return The words.
	 */
	private List<String> getFallbackLocalWordsList(int wordCount, boolean hard) {
		return new ArrayList<>();
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

		List<Drawer> drawers = Camelia.getInstance().getGameManager().getDrawers();
		Collections.shuffle(drawers);

		for (Integer i = 0; i < WAVES_COUNT; i++) {
			for (Drawer drawer : drawers) {
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

		// We need an online drawer.
		do {
			try {
				currentTurn = draws.pop();
			} catch (NoSuchElementException e) {
				Camelia.getInstance().getGameManager().onEnd();
				return;
			}

		} while (currentTurn.getDrawer().getPlayer() == null || !currentTurn.getDrawer().getPlayer().isOnline());

		currentTurn.startTurn();
	}


	/**
	 * Returns the turn currently active.
	 *
	 * Before the game, returns {@code null}. After, too.
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

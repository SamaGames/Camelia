package eu.carrade.amaury.Camelia.game.turns;


import eu.carrade.amaury.Camelia.*;
import eu.carrade.amaury.Camelia.game.*;
import eu.carrade.amaury.Camelia.utils.*;
import eu.carrade.amaury.Camelia.utils.Utils;
import net.samagames.tools.*;
import org.bukkit.*;
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
public class Word {

	/**
	 * The word.
	 */
	private String word;

	/**
	 * Is this word a hard one?
	 */
	private Boolean hard;


	/**
	 *
	 * @param word The word.
	 * @param hard {@code true} if hard.
	 */
	public Word(String word, Boolean hard) {
		this.word = word;
		this.hard = hard;
	}

	/**
	 * Returns the word.
	 *
	 * @return The word.
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Returns {@code true} if this word is marked as hard to draw.
	 *
	 * @return {@code true} if hard.
	 */
	public Boolean isHard() {
		return hard;
	}


	/**
	 * Returns {@code true} if the given input match this word.
	 *
	 * TODO Notice the player if he's near the solution (like iSketch).
	 *
	 * @param input The input.
	 *
	 * @return {@code true} if the input is matching.
	 */
	public FoundState checkInput(String input) {
		if (input != null && Utils.wideComparison(input, word)) {
			return FoundState.FOUND;
		}

		return FoundState.NOT_FOUND;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Word word1 = (Word) o;

		return !(word != null ? !word.equals(word1.word) : word1.word != null) && !(hard != null ? !hard.equals(word1.hard) : word1.hard != null);
	}

	/**
	 * Returns {@code true} if this word is empty.
	 *
	 * @return {@code true} if empty.
	 */
	public boolean isEmpty() {
		return word == null || word.isEmpty();
	}

	/**
	 * Returns the length of this word.
	 *
	 * @return The length.
	 *
	 * @see {@link String#length()}.
	 */
	public int length() {
		return word.length();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		int result = word != null ? word.hashCode() : 0;
		result = 31 * result + (hard != null ? hard.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return word;
	}

	/**
	 * Does the player found the word?
	 *
	 * Used as an answer of the {@link #checkInput(String)} method.
	 */
	public enum FoundState {
		/**
		 * The player didn't found the word.
		 */
		NOT_FOUND,

		/**
		 * The player didn't found the word, but he is near.
		 */
		NEAR,

		/**
		 * The player found the word.
		 */
		FOUND
	}
}

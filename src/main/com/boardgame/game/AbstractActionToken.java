package com.boardgame.game;

/**
 * Represents a general action token.
 *
 */
abstract class AbstractActionToken {
	private final boolean isSpecial;
	
	/**
	 * Creates a new action token that is special if isSpecial is true
	 * @param isSpecial whether or not this action token is special
	 */
	AbstractActionToken(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}
}

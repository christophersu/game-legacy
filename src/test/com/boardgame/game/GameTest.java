package com.boardgame.game;

import org.junit.Test;

public class GameTest {
	@Test(expected=IllegalArgumentException.class)
	public void testNegativePlayers() {
		new Game(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testZeroPlayers() {
		new Game(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testTooFewPlayers() {
		new Game(Game.MIN_PLAYERS - 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testTooManyPlayers() {
		new Game(Game.MAX_PLAYERS + 1);
	}
}

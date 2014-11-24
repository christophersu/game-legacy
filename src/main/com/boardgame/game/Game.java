package com.boardgame.game;

import java.util.Set;

/**
 * A game object that holds the state of the game and has methods that can 
 * manipulate that state when used appropriately.
 *
 */
public final class Game {
	//the minimum number of players for a game
	public static final int MIN_PLAYERS = InternalGame.MIN_PLAYERS;
	//the maximum number of players for a game
	public static final int MAX_PLAYERS = InternalGame.MAX_PLAYERS;
	
	private InternalGame internalGame;
	
	/**
	 * Creates a new game with the given number of players
	 * @param numPlayers  the number of players in this game. between 
	 * MIN_PLAYERS (inclusive) and MAX_PLAYERS (inclusive)
	 * @throws IllegalArgumentException if numPlayers is less than MIN_PLAYERS
	 * or greater than MAX_PLAYERS
	 */
	public Game(int numPlayers) {
		if (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
			throw new IllegalArgumentException("Invalid number of players: " + 
					numPlayers + " not in [" + MIN_PLAYERS + "," + MAX_PLAYERS +
					"]");
		}
		
		internalGame = new InternalGame(numPlayers);
	}
	
	//TODO
	public void addPlayer(int player, int faction) {
		throw new UnsupportedOperationException();
	}
	
	public void removePlayer(int player) {
		throw new UnsupportedOperationException();
	}
	
	public void startGame() {
		throw new UnsupportedOperationException();
	}
	
	public void placeToken(int player, int token, int location) {
		throw new UnsupportedOperationException();
	}
	
	public void removeToken(int player, int location) {
		throw new UnsupportedOperationException();
	}
	
	public void switchToken(int player, int location, int nextToken) {
		throw new UnsupportedOperationException();
	}
	
	public int getCurrentPlayer() {
		throw new UnsupportedOperationException();
	}
	
	public Set<Integer> getTokenChoices(int player) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Integer> getValidLocationTargets(int user, int token) {
		throw new UnsupportedOperationException();
	}
	
	public void chooseUnitforAction(int unit) {
		throw new UnsupportedOperationException();
	}
	
	public void useToken(int player, int token, int tokenLocation, int target) {
		throw new UnsupportedOperationException();
	}
	
	public void resetToken(int player, int token) {
		throw new UnsupportedOperationException();
	}
	
	public void support(int supportingPlayer, int supportingLocation, int supportedPlayer) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatCard(int player, int combatCard) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatBonus(int player) {
		throw new UnsupportedOperationException();
	}
	
	public void retreat(int defeatedPlayer, int retreatingUnit, int target) {
		throw new UnsupportedOperationException();
	}
	
	public void spawnUnit(int player, int location, int unit) {
		throw new UnsupportedOperationException();
	}
	
	public void bid(int player, int bid) {
		throw new UnsupportedOperationException();
	}
	
	public void breakTie(int player, int winner) {
		throw new UnsupportedOperationException();
	}
	
	public void setUserReady(int player, boolean isReady) {
		throw new UnsupportedOperationException();
	}
	
	public int getNumPlayers() {
		return internalGame.getNumPlayers();
	}
	
	public int getState() {
		throw new UnsupportedOperationException();
	}

	public String getSnapshotForUser(int player) {
		throw new UnsupportedOperationException();
	}
}

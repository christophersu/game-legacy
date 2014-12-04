package com.boardgame.game;

import java.util.Set;

/**
 * A game object that holds the state of the game and has methods that can 
 * manipulate that state when used appropriately.
 *
 */
public final class Game {
	private final IntegersToObjects integersToObjects;
	private final GameState gameState;
	
	/**
	 * Creates a new game with the given initial game state
	 * @param gameState  the initial game state, not null
	 * @throws IllegalArgumentException if gameState is null
	 */
	public Game(GameState gameState) {
		if (gameState == null) {
			throw new IllegalArgumentException("Game state was null.");
		}
		
		this.integersToObjects = new IntegersToObjects(gameState);
		this.gameState = gameState;
	}
	
	/**
	 * Puts the player with the given playerId in the game (if not already), and
	 * associates the player with the given faction. The association between 
	 * playerId and faction is 1 to 1. 
	 * @param playerId  an identifier for a particular player
	 * @param faction  the faction that the player will be associated with, not
	 * null
	 * @throws IllegalArgumentException if faction is null
	 * @throws IllegalStateException if the game is full and playerId is not 
	 * already in the game
	 * @return true if the player was successfully associated with the given 
	 * faction, false if another player is already associated with the faction
	 */
	public boolean putPlayer(int playerId, Faction faction) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Removes the player with the given id from the game.
	 * @param playerId  an identifier for a particular player
	 * @return whether the player existed previously
	 */
	public boolean removePlayer(int playerId) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @return whether the game is full
	 */
	public boolean isGameFull() {
		throw new UnsupportedOperationException();
	}
	
	public void startGame() {
		throw new UnsupportedOperationException();
	}
	
	public void placeToken(Faction faction, AbstractActionToken token, 
			Location location) {
		throw new UnsupportedOperationException();
	}
	
	public void removeToken(Faction faction, Location location) {
		throw new UnsupportedOperationException();
	}
	
	public void switchToken(Faction faction, Location location, 
			AbstractActionToken nextToken) {
		throw new UnsupportedOperationException();
	}
	
	public Player getCurrentPlayer() {
		throw new UnsupportedOperationException();
	}
	
	public Set<AbstractActionToken> getTokenChoices(Faction faction) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Location> getValidLocationTargets(Faction faction, 
			AbstractActionToken token) {
		throw new UnsupportedOperationException();
	}
	
	public void chooseUnitforAction(AbstractUnit unit) {
		throw new UnsupportedOperationException();
	}
	
	public void useToken(Faction faction, AbstractActionToken token, 
			Location tokenLocation, Location target) {
		throw new UnsupportedOperationException();
	}
	
	public void resetToken(Faction faction, AbstractActionToken token) {
		throw new UnsupportedOperationException();
	}
	
	public void support(Faction supportingFaction, Location supportingLocation, 
			Faction supportedFaction) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatCard(Faction faction, AbstractCombatCard combatCard) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatBonus(Faction faction) {
		throw new UnsupportedOperationException();
	}
	
	public void retreat(Faction defeatedFaction, AbstractUnit retreatingUnit, 
			Location target) {
		throw new UnsupportedOperationException();
	}
	
	public void spawnUnit(Faction faction, Location location, AbstractUnit unit) {
		throw new UnsupportedOperationException();
	}
	
	public void bid(Faction faction, int bid) {
		throw new UnsupportedOperationException();
	}
	
	public void breakTie(Faction loser, Faction winner) {
		throw new UnsupportedOperationException();
	}
	
	public void setUserReady(Faction faction, boolean isReady) {
		throw new UnsupportedOperationException();
	}
	
	public int getNumPlayers() {
		throw new UnsupportedOperationException();
	}
	
	public int getState() {
		throw new UnsupportedOperationException();
	}

	public String getSnapshotForPlayer(Faction faction) {
		throw new UnsupportedOperationException();
	}
}

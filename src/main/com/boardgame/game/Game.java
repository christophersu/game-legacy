package com.boardgame.game;

import java.io.IOException;
import java.util.Set;

import org.json.simple.parser.ParseException;

/**
 * A game object that holds the state of the game and has methods that can 
 * manipulate that state when used appropriately.
 *
 */
public final class Game {
	private GameState gameState;
	
	public Game(GameState gameState) {
		if (gameState == null) {
			throw new IllegalArgumentException("Game state was null.");
		}
		
		this.gameState = gameState;
	}
	
	//TODO
	public void addPlayer(int playerId, Faction faction) {
		throw new UnsupportedOperationException();
	}
	
	public void removePlayer(int playerId) {
		throw new UnsupportedOperationException();
	}
	
	public void startGame() {
		throw new UnsupportedOperationException();
	}
	
	public void placeToken(Player player, AbstractActionToken token, 
			Location location) {
		throw new UnsupportedOperationException();
	}
	
	public void removeToken(Player player, Location location) {
		throw new UnsupportedOperationException();
	}
	
	public void switchToken(Player player, Location location, 
			AbstractActionToken nextToken) {
		throw new UnsupportedOperationException();
	}
	
	public Player getCurrentPlayer() {
		throw new UnsupportedOperationException();
	}
	
	public Set<AbstractActionToken> getTokenChoices(Player player) {
		throw new UnsupportedOperationException();
	}
	
	public Set<Location> getValidLocationTargets(Player player, 
			AbstractActionToken token) {
		throw new UnsupportedOperationException();
	}
	
	public void chooseUnitforAction(AbstractUnit unit) {
		throw new UnsupportedOperationException();
	}
	
	public void useToken(Player player, AbstractActionToken token, 
			Location tokenLocation, Location target) {
		throw new UnsupportedOperationException();
	}
	
	public void resetToken(Player player, AbstractActionToken token) {
		throw new UnsupportedOperationException();
	}
	
	public void support(Player supportingPlayer, Location supportingLocation, 
			Player supportedPlayer) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatCard(Player player, AbstractCombatCard combatCard) {
		throw new UnsupportedOperationException();
	}
	
	public void useCombatBonus(Player player) {
		throw new UnsupportedOperationException();
	}
	
	public void retreat(int defeatedPlayer, int retreatingUnit, int target) {
		throw new UnsupportedOperationException();
	}
	
	public void spawnUnit(Player player, Location location, AbstractUnit unit) {
		throw new UnsupportedOperationException();
	}
	
	public void bid(Player player, int bid) {
		throw new UnsupportedOperationException();
	}
	
	public void breakTie(Player loser, Player winner) {
		throw new UnsupportedOperationException();
	}
	
	public void setUserReady(Player player, boolean isReady) {
		throw new UnsupportedOperationException();
	}
	
	public int getNumPlayers() {
		throw new UnsupportedOperationException();
	}
	
	public int getState() {
		throw new UnsupportedOperationException();
	}

	public String getSnapshotForPlayer(Player player) {
		throw new UnsupportedOperationException();
	}
}
